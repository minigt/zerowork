package ar.com.minigt.zerowork.todoapi;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureDataMongo
@TestPropertySource(properties = "spring.mongodb.embedded.version=3.5.5")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class TodoApplicationTest {

    protected static final String TEST_USERNAME = "zerowork";

    @Test
    public void contextLoads() {
    }

}
