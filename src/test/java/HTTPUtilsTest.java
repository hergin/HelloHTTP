
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HTTPUtilsTest {

    HTTPUtils httpUtils;

    @BeforeEach
    void setup() {
        httpUtils = new HTTPUtils();
    }

    @Test
    void getTodoItemJsonString() throws IOException {
        var result = httpUtils.getTodoItemJsonString(2);
        assertEquals("{\n" +
                "  \"id\": 2,\n" +
                "  \"title\": \"Create teams\",\n" +
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
    void getAllTodoItemsJsonTest() throws IOException {
        String owner = "todoItemGuy";
        var resultingID = httpUtils.addTodoItem("Todo Item 1", owner);
        var resultingID2 = httpUtils.addTodoItem("Todo Item 2", owner);
        List<String> list = httpUtils.getAllTodoItemsJSON(owner);
        httpUtils.deleteTodoItem(resultingID);
        httpUtils.deleteTodoItem(resultingID2);
        assertEquals(2, list.size());
    }

}