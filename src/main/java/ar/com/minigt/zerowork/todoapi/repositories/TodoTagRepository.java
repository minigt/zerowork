package ar.com.minigt.zerowork.todoapi.repositories;

import ar.com.minigt.zerowork.todoapi.entities.TodoTagDocument;
import java.util.List;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoTagRepository extends MongoRepository<TodoTagDocument, String> {

    List<TodoTagDocument> findByUsername(String username);

    Optional<TodoTagDocument> deleteByIdAndUsername(String id, String username);

    Optional<TodoTagDocument> findByIdAndUsername(String id, String username);

    Optional<TodoTagDocument> findByTodoIdAndTagId(String todoId, String tagId);
}
