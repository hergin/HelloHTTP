import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class HTTPUtilsTest {

    HTTPUtils httpUtils;

    @BeforeEach
    void setup() {
        httpUtils = new HTTPUtils();
    }

    @Test
    void getTodoItemJsonString() throws IOException {
        var result = httpUtils.getTodoItemJsonString(1);
        assertEquals("{\n" +
                "  \"id\": 1,\n" +
                "  \"title\": \"Explain the project\",\n" +
                "  \"owner\": \"hergin\"\n" +
                "}", result);
    }

    @Test
    void addTodoItem() throws IOException {
        var resultingID = httpUtils.addTodoItem("Hello", "hergin");
        var expected = "{\n" +
                "  \"title\": \"Hello\",\n" +
                "  \"owner\": \"hergin\",\n" +
                "  \"id\": " + resultingID + "\n" +
                "}";
        var actual = httpUtils.getTodoItemJsonString(resultingID);
        assertEquals(expected, actual);
    }

    @Test
    void deleteExistingTodoItem() throws IOException {
        var resultingID = httpUtils.addTodoItem("Hello", "hergin");
        var deleteResult = httpUtils.deleteTodoItem(resultingID);
        assertTrue(deleteResult);
    }

    @Test
    void deleteNotExistingTodoItem() throws IOException {
        var nonExistingIDdeleteResult = httpUtils.deleteTodoItem(152434354);
        assertFalse(nonExistingIDdeleteResult);
    }

    @Test
    void getAllTodoItems() throws IOException {
        var item1 = httpUtils.addTodoItem("Thing1", "ChillGuy");
        var item2 = httpUtils.addTodoItem("Thing2", "ChillGuy");
        var expected = 2;
        var actual = httpUtils.getAllTodoItems("ChillGuy").size();
        assertEquals(expected, actual);
    }



}