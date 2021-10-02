import Models.CompanyReports.RootCompany;
import com.fasterxml.jackson.databind.ObjectMapper;
import kong.unirest.Unirest;
import kong.unirest.json.JSONObject;

import java.io.IOException;

public class main {
    public static void main(String[] args) throws IOException {
        Unirest.config().enableCookieManagement(false);

/*        HttpResponse<String> response1 = Unirest.post("https://www.cse.lk/api/allSecurityCode")
                .header("content-type", "application/x-www-form-urlencoded; charset=UTF-8")
                .body("")
                .asString();
        JSONObject json1 = new JSONObject(response1.getBody());
        ObjectMapper om1 = new ObjectMapper();*/

        Boolean success = false;
        String[] out = {"", ""};

        //Take user input
        String securityCode = Functions.receiveUserInput();

        //make http request for reports
        JSONObject reportsJson = Functions.responseJson("https://www.cse.lk/api/financials", "symbol=" + securityCode + "\"");

        try { //Generate outout
            ObjectMapper om = new ObjectMapper();
            RootCompany rootCompany = om.readValue(reportsJson.toString(), RootCompany.class); //Deserialize json to pojo
            out = Functions.downloadreports(rootCompany, securityCode);
            success = true;
        } catch (Exception e) {
            System.out.println("Unfamilliar Json format");
            e.printStackTrace();
        }

        //write output files
        if (success) {
            Functions.writeoutput(out, securityCode);
        }

    }
}
