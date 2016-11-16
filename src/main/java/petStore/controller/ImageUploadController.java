package petStore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import petStore.service.fileStorage.StorageFileNotFoundException;
import petStore.service.fileStorage.StorageService;

/**
 * We handle image uploads here
 */
@RestController
public class ImageUploadController {

    private final StorageService storageService;

    @Autowired
    public ImageUploadController(StorageService storageService) {
        this.storageService = storageService;
    }


    @RequestMapping(value = "/images/{filename:.+}", method = RequestMethod.GET)
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + (file != null ? file.getFilename() : null) + "\"")
                .body(file);
    }

    @RequestMapping(value = "/image", method = RequestMethod.POST)
    public StringResponseWrapper handleFileUpload(@RequestParam("file") MultipartFile file) {
        storageService.store(file);
        return new StringResponseWrapper(file.getOriginalFilename());
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }
}
