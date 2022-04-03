package ar.com.minigt.zerowork.todoapi.repositories;

import ar.com.minigt.zerowork.todoapi.entities.UserDocument;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<UserDocument, Long> {

    Optional<UserDocument> findByUsername(String username);

}