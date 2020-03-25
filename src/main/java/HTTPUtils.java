import com.google.api.client.http.*;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.gson.*;

import java.io.IOException;
import java.util.*;

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
                new GenericUrl(todosURL),content);
        String rawResponse = postRequest.execute().parseAsString();
        JsonParser jsonParser = new JsonParser();
        JsonElement rootElement = jsonParser.parse(rawResponse);
        JsonObject rootObject = rootElement.getAsJsonObject();
        var id = rootObject.getAsJsonPrimitive("id").getAsInt();
        return id;
    }

    /**
     * @param id of the todoItem to delete
     * @return true if successfully deleted. Otherwise false.
     * @throws IOException
     */
    public boolean deleteTodoItem(int id) throws IOException {
        try {
            HttpRequest deleteRequest = requestFactory.buildDeleteRequest(
                    new GenericUrl(todosURL + id));
            String rawResponse = deleteRequest.execute().parseAsString();
            return true;
        } catch (IOException e){
            return false;
        }
    }

    public List<String> getAllTodoItemsJSON(String owner) throws IOException {
        HttpRequest getRequest = requestFactory.buildGetRequest(
                new GenericUrl(baseURL + owner + "/todos/"));
        String rawResponse = getRequest.execute().parseAsString();

        JsonParser jsonParser = new JsonParser();
        JsonElement rootElement = jsonParser.parse(rawResponse);
        JsonArray rootObjectArray = rootElement.getAsJsonArray();

        List<String> jsonList = new ArrayList<>();
        for (int i = 0; i < rootObjectArray.size(); i++) {
            JsonObject jsonObject = rootObjectArray.get(i).getAsJsonObject();
            String jsonObjectString = jsonObject.toString();
            jsonList.add(jsonObjectString);
        }
        return jsonList;
    }

}
