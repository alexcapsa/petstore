package petStore.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import petStore.entity.Category;
import petStore.entity.Pet;
import petStore.service.CategoryRepository;
import petStore.service.PetRepository;

import java.util.List;

/**
 * Controller class for the PetStore app
 */
@RestController
public class PetController {

    private static final Logger logger = LoggerFactory.getLogger(PetController.class);

    private final PetRepository petRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public PetController(PetRepository petRepository, CategoryRepository categoryRepository) {
        this.petRepository = petRepository;
        this.categoryRepository = categoryRepository;
    }

    @RequestMapping(value = "/pet", method = RequestMethod.POST)
    public Pet addPet(@RequestBody Pet pet) {
        logger.debug("Saving pet...");
        petRepository.save(pet);
        return pet;
    }

    @RequestMapping(value = "/category", method = RequestMethod.POST)
    public Category addCategory(@RequestBody Category category) {
        logger.debug("Saving category...");
        categoryRepository.save(category);
        return category;
    }

    @RequestMapping(value = "/pet/{petId:.+}", method = RequestMethod.DELETE)
    public StringResponseWrapper deletePet(@PathVariable String petId) {
        if(petId == null) {
            throw new IllegalArgumentException("Invalid ID supplied");
        }
        logger.debug("deleting pet...");
        petRepository.delete(petId);
        return new StringResponseWrapper(petId);
    }

    @RequestMapping(value = "/category/{categoryId:.+}", method = RequestMethod.DELETE)
    public StringResponseWrapper deleteCategory(@PathVariable String categoryId) {
        if(categoryId == null) {
            throw new IllegalArgumentException("Invalid ID supplied");
        }
        logger.debug("deleting category...");
        categoryRepository.delete(categoryId);
        return new StringResponseWrapper(categoryId);
    }

    @RequestMapping(value = "/pet/{petId:.+}", method = RequestMethod.GET)
    public Pet findPetById(@PathVariable String petId) {
        if(petId == null) {
            throw new IllegalArgumentException("Invalid ID supplied");
        }
        logger.debug("trying to find pet by id...");
        return petRepository.findById(petId);
    }

    @RequestMapping(value = "/getPets", method = RequestMethod.GET)
    public List<Pet> getPets() {
        logger.debug("retrieving all pets...");
        return petRepository.findAll();
    }

    @RequestMapping(value = "/getCategories", method = RequestMethod.GET)
    public List<Category> getCategories() {
        logger.debug("retrieving all categories...");
        return categoryRepository.findAll();
    }

}