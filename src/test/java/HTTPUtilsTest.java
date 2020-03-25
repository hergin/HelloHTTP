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
        var resultingID = httpUtils.addTodoItem("Awesome", "real one");
        var expected = "{\n" +
                "  \"title\": \"Awesome\",\n" +
                "  \"owner\": \"real one\",\n" +
                "  \"id\": " + resultingID + "\n" +
                "}";
        var actual = httpUtils.getTodoItemJsonString(resultingID);
        assertEquals(expected, actual);
    }

    @Test
    void deleteExistingTodoItem() throws IOException {
        var resultingID = httpUtils.addTodoItem("Task2", "CoolGuy");
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
        var todoItem1 = httpUtils.addTodoItem("Thing1", "BestGuy");
        var todoItem2 = httpUtils.addTodoItem("Thing2", "BestGuy");
        var expected = 2;
        var actual = httpUtils.getAllTodoItemsJSON("BestGuy").size();
        assertEquals(expected, actual);
    }


}