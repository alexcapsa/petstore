package petStore.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import petStore.entity.Category;
import petStore.entity.Pet;
import petStore.service.CategoryRepository;
import petStore.service.PetRepository;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * We test the pet controller
 */
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
public class PetControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private PetRepository petRepository;

    @MockBean
    private CategoryRepository categoryRepository;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void shouldAddPet() throws Exception {
        Pet pet = buildTestPet();
        mvc.perform(post("/pet").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(pet)))
                .andExpect(jsonPath("$.name", is(pet.getName())));
        then(this.petRepository).should().save(pet);
    }

    @Test
    public void shouldAddCategory() throws Exception {
        Category category = buildTestCategory();
        mvc.perform(post("/category").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(category)))
                .andExpect(jsonPath("$.name", is(category.getName())));
        then(this.categoryRepository).should().save(category);
    }

    @Test
    public void shouldDeletePet() throws Exception {
        Pet pet = buildTestPet();
        mvc.perform(delete("/pet/" + pet.getId()))
                .andExpect(jsonPath("$.response", is(pet.getId())));
        then(this.petRepository).should().delete(pet.getId());
    }

    @Test
    public void shouldDeleteCategory() throws Exception {
        Category category = buildTestCategory();
        mvc.perform(delete("/category/" + category.getId()))
                .andExpect(jsonPath("$.response", is(category.getId())));
        then(this.categoryRepository).should().delete(category.getId());
    }

    @Test
    public void shouldFindBetById() throws Exception {
        Pet pet = buildTestPet();
        mvc.perform(get("/pet/" + pet.getId())).andExpect(status().isOk());
        then(this.petRepository).should().findById(pet.getId());
    }

    @Test
    public void shouldFindAllPets() throws Exception {
        mvc.perform(get("/getPets")).andExpect(status().isOk());
        then(this.petRepository).should().findAll();
    }

    @Test
    public void shouldFindAllCategories() throws Exception {
        mvc.perform(get("/getCategories")).andExpect(status().isOk());
        then(this.categoryRepository).should().findAll();
    }

    private Category buildTestCategory() {
        Category category = new Category();
        category.setId("321");
        category.setName("Cats");
        return category;
    }

    private Pet buildTestPet() {
        Pet pet = new Pet();
        pet.setId("123");
        pet.setName("Fluffy");
        return pet;
    }
}
