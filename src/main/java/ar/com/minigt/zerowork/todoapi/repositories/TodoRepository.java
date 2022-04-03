package ar.com.minigt.zerowork.todoapi.repositories;

import ar.com.minigt.zerowork.todoapi.entities.TodoDocument;
import java.util.List;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoRepository extends MongoRepository<TodoDocument, String> {

    List<TodoDocument> findByUsername(String username);

    Optional<TodoDocument> deleteByIdAndUsername(String id, String username);

    Optional<TodoDocument> findByIdAndUsername(String id, String username);
}
