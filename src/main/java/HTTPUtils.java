import com.google.api.client.http.*;
import com.google.api.client.http.javanet.NetHttpTransport;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

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
        String rawResponse = getRequest.execute().parseAsString();
        return rawResponse;
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
                new GenericUrl(todosURL), content);
        String rawResponse = postRequest.execute().parseAsString();

        var resultID = "";
        for (var ch : rawResponse.toCharArray()) {
            if (Character.isDigit(ch)) {
                resultID += ch;
            }
        }
        return Integer.parseInt(resultID);
    }

    /**
     * @param id of the todoItem to delete
     * @return true if succesfully deleted. Otherwise false.
     * @throws IOException
     */
    public boolean deleteTodoItem(int id) throws IOException {
        HttpRequest deleteRequest = requestFactory.buildDeleteRequest(
                new GenericUrl(todosURL + id));
        try {
            String rawResponse = deleteRequest.execute().parseAsString();
        } catch (HttpResponseException hre) {
            return false;
        }
        return true;
    }

    /**
     * Update an existing todoItem.
     *
     * @param id    of the existing todoItem
     * @param note  new note
     * @param owner new owner
     * @return true if it succesfully updates. false if ID doesn't exist!
     */
    public boolean updateTodoItem(int id, String note, String owner) throws IOException {
        Map<String, Object> data = new LinkedHashMap<>();
        data.put("id", id);
        data.put("title", note);
        data.put("owner", owner);
        HttpContent content = new UrlEncodedContent(data);
        HttpRequest putRequest = requestFactory.buildPutRequest(
                new GenericUrl(todosURL + id), content);
        try {
            String rawResponse = putRequest.execute().parseAsString();
        } catch (HttpResponseException hre) {
            return false;
        }

        return true;
    }
}
