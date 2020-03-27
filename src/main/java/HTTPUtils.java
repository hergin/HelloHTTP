import com.google.api.client.http.*;
import com.google.api.client.http.javanet.NetHttpTransport;


import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.*;

import static java.lang.Integer.parseInt;


public class HTTPUtils {

    HttpRequestFactory requestFactory;
    String baseURL = "https://todoserver222.herokuapp.com/";
    String todosURL = baseURL + "todos/";

    public HTTPUtils() {
        requestFactory = new NetHttpTransport().createRequestFactory();
    }

    /**
     * @param id
     * @return JSON string of the todoItem with id
     * @throws IOException
     */
    public String getTodoItemJsonString(int id) throws IOException {
        HttpRequest getRequest = requestFactory.buildGetRequest(
                new GenericUrl(todosURL + id));
        String getItem = getRequest.execute().parseAsString();
        return getItem;
    }

    /**
     * @param note  whatever should be in the todoItem
     * @param owner whoever is the owner of the todoItem
     * @return the ID of the recently added todoItem
     * @throws IOException
     */
    public int addTodoItem(String note, String owner) throws IOException {
        Map<String, Object> data = new LinkedHashMap<>();
        data.put("title", note);
        data.put("owner", owner);
        HttpContent content = new UrlEncodedContent(data);
        HttpRequest postRequest = requestFactory.buildPostRequest(
                new GenericUrl("https://todoserver222.herokuapp.com/todos"),content);
        String response = postRequest.execute().parseAsString();
        Integer One = response.indexOf("}") - 4;
        Integer Two = response.indexOf("}") - 1;
        var rawID = response.substring(One,Two);
        rawID = rawID.trim();
        int id = parseInt(rawID);
        return id;

    }

    /**
     * @param id of the todoItem to delete
     * @return true if succesfully deleted. Otherwise false.
     * @throws IOException
     */
    public boolean deleteTodoItem(int id) throws IOException {
        try {
            HttpRequest deleteRequest = requestFactory.buildDeleteRequest(
                    new GenericUrl("https://todoserver222.herokuapp.com/todos/" + id));
            String response = deleteRequest.execute().parseAsString();
            return true;
        }
        catch (IOException e) {
            return false;
        }
    }
    public List<String> getAllTodoItems(String owner) throws IOException {
        HttpRequest getRequest = requestFactory.buildGetRequest(
                new GenericUrl("https://todoserver222.herokuapp.com/" + owner + "/todos/"));
        String rawResponse = getRequest.execute().parseAsString();
        String str[] = rawResponse.split("},");
        List<String> toDoItemsList = new ArrayList<>();
        toDoItemsList = Arrays.asList(str);
        return toDoItemsList;
    }

}
