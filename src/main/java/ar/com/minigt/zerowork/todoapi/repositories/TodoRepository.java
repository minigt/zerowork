package ar.com.minigt.zerowork.todoapi.repositories;

import ar.com.minigt.zerowork.todoapi.entities.TodoDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoRepository extends MongoRepository<TodoDocument, String> {
}
