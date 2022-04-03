package ar.com.minigt.zerowork.todoapi.repositories;

import ar.com.minigt.zerowork.todoapi.entities.TagDocument;
import java.util.List;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends MongoRepository<TagDocument, String> {

    List<TagDocument> findByUsername(String username);

    Optional<TagDocument> deleteByIdAndUsername(String id, String username);

    Optional<TagDocument> findByIdAndUsername(String id, String username);
}
