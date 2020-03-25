import com.google.api.client.http.*;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
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
    public String getTodoItemJsonString(int id) throws IOException
    {
        String rawResponse;
        try
        {
            HttpRequest getRequest = requestFactory.buildGetRequest(
                    new GenericUrl("https://todoserver222.herokuapp.com/todos/" + id));
            rawResponse = getRequest.execute().parseAsString();
        }
        catch (Exception e)
        {
            return null;
        }

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
                new GenericUrl("https://todoserver222.herokuapp.com/todos"),content);
        String rawResponse = postRequest.execute().parseAsString();

        int indexOfID = rawResponse.indexOf("id");
        String IDWithEnding = rawResponse.substring(indexOfID + 5);
        String IDWithoutEnding = IDWithEnding.substring(0, IDWithEnding.length() - 2);
        return(Integer.valueOf(IDWithoutEnding));
    }

    /**
     * @param id of the todoItem to delete
     * @return true if succesfully deleted. Otherwise false.
     * @throws IOException
     */
    public boolean deleteTodoItem(int id) throws IOException
    {
        try
        {
            HttpRequest deleteRequest = requestFactory.buildDeleteRequest(
                    new GenericUrl("https://todoserver222.herokuapp.com/todos/" + id));
            String rawResponse = deleteRequest.execute().parseAsString();
            //rawResponse.equals("{}")
        }
        catch (Exception e)
        {
            return false;
        }

        return true;
    }


    // get all todo items by an owner
    public List<String> getAllTodoItemsJSON(String owner) throws IOException
    {
        HttpRequest getRequest = requestFactory.buildGetRequest(
                new GenericUrl("https://todoserver222.herokuapp.com/" + owner + "/todos/"));
        String rawResponse = getRequest.execute().parseAsString();

        //int count = 1 + StringUtils.countMatches(rawResponse, "},");

        //for (int i = 0; i < ; i++)
        {
            //add to list, every entry is an item
        }

        //List<String> changeNameLater = new ArrayList<String>();

        return(null);
    }


}
