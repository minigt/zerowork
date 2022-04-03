package ar.com.minigt.zerowork.todoapi;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureDataMongo
@TestPropertySource(properties = "spring.mongodb.embedded.version=3.5.5")
public class TodoApplicationTest {

    @Test
    public void contextLoads() {
    }

}
