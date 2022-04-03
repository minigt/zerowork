package ar.com.minigt.zerowork.todoapi.exceptions;

import static org.assertj.core.api.Assertions.*;

import ar.com.minigt.zerowork.todoapi.TodoApplicationTest;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

public class TodoErrorCodeTest extends TodoApplicationTest {

    @Test
    public void noDuplicateErrorCode() {
        TodoErrorCode[] todoErrorCodes = TodoErrorCode.values();
        Set<Integer> todoErrorCodeSet = new HashSet<>();

        for (TodoErrorCode todoErrorCode : todoErrorCodes) {
            if (todoErrorCodeSet.contains(todoErrorCode.getExtendedCode())) {
                fail("Duplicate extended error code: " + todoErrorCode.getExtendedCode());
            }

            todoErrorCodeSet.add(todoErrorCode.getExtendedCode());
        }
    }
}