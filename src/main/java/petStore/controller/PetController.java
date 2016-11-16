package petStore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import petStore.entity.Category;
import petStore.entity.Pet;
import petStore.service.CategoryRepository;
import petStore.service.PetRepository;

import java.io.IOException;
import java.util.List;

/**
 * Controller class for the PetStore app
 */
@RestController
public class PetController {

    private final PetRepository petRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public PetController(PetRepository petRepository, CategoryRepository categoryRepository) {
        this.petRepository = petRepository;
        this.categoryRepository = categoryRepository;
    }

    @RequestMapping(value = "/pet", method = RequestMethod.POST)
    public Pet addPet(@RequestBody Pet pet) {
        petRepository.save(pet);
        return pet;
    }

    @RequestMapping(value = "/category", method = RequestMethod.POST)
    public Category addCategory(@RequestBody Category category) {
        categoryRepository.save(category);
        return category;
    }

    @RequestMapping(value = "/pet/{petId:.+}", method = RequestMethod.DELETE)
    public StringResponseWrapper deletePet(@PathVariable String petId) throws IOException {
        if(petId == null) {
            throw new IllegalArgumentException("Invalid ID supplied");
        }
        petRepository.delete(petId);
        return new StringResponseWrapper(petId);
    }

    @RequestMapping(value = "/category/{categoryId:.+}", method = RequestMethod.DELETE)
    public StringResponseWrapper deleteCategory(@PathVariable String categoryId) throws IOException {
        if(categoryId == null) {
            throw new IllegalArgumentException("Invalid ID supplied");
        }
        categoryRepository.delete(categoryId);
        return new StringResponseWrapper(categoryId);
    }

    @RequestMapping(value = "/pet/{petId:.+}", method = RequestMethod.GET)
    public Pet findPetById(@PathVariable String petId) {
        if(petId == null) {
            throw new IllegalArgumentException("Invalid ID supplied");
        }
        return petRepository.findById(petId);
    }

    @RequestMapping(value = "/getPets", method = RequestMethod.GET)
    public List<Pet> getPets() {
        return petRepository.findAll();
    }

    @RequestMapping(value = "/getCategories", method = RequestMethod.GET)
    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

}