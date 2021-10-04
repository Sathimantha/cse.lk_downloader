package Functions;

import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import kong.unirest.json.JSONObject;

public class WEBFunctions {
    public static JSONObject responseJson(String path, String body) {
        HttpResponse<String> response2 = Unirest.post(path)
                .header("content-type", "application/x-www-form-urlencoded; charset=UTF-8")
                .body(body)
                .asString();
        JSONObject reportsJson = new JSONObject(response2.getBody());
        return reportsJson;
    }
}
