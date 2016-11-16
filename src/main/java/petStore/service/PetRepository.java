package petStore.service;

import org.springframework.data.mongodb.repository.MongoRepository;
import petStore.entity.Pet;

/**
 * Mongo repository for pets
 */
public interface PetRepository extends MongoRepository<Pet, String>{
    public Pet findById(String id);
}
