package petStore.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import petStore.service.fileStorage.StorageService;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * We test the image upload process
 */
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
public class ImageUploadTests {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private StorageService storageService;

    @Test
    public void shouldServeFile() throws Exception {
        String requestedFileName = "test.txt";
        this.mvc.perform(get("/images/" + requestedFileName))
                .andExpect(status().isOk());
        then(this.storageService).should().loadAsResource(requestedFileName);
    }

    @Test
    public void shouldSaveUploadedFile() throws Exception {
        String filename = "test.txt";
        MockMultipartFile multipartFile =
                new MockMultipartFile("file", filename, "text/plain", "Spring Framework".getBytes());
        this.mvc.perform(fileUpload("/image").file(multipartFile))
                .andExpect(jsonPath("$.response", is(filename)));

        then(this.storageService).should().store(multipartFile);
    }
}
