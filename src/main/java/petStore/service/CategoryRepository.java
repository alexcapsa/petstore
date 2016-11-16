package petStore.service;

import org.springframework.data.mongodb.repository.MongoRepository;
import petStore.entity.Category;

/**
 * Mongo repository for pet categories
 */
public interface CategoryRepository extends MongoRepository<Category, String>{
    public Category findById(String id);
}
