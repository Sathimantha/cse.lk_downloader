import Functions.Downloaders;
import Functions.IOFunctions;
import Functions.WEBFunctions;
import Models.CompanyReports.RootCompany;
import kong.unirest.Unirest;
import kong.unirest.json.JSONObject;

import java.io.IOException;

import static Functions.ObjectMappers.rootCompanyMapper;


public class main {
    public static void main(String[] args) throws IOException {
        boolean programSuccess = false;
        Unirest.config().enableCookieManagement(false);
        String[] reportsOutput = {"", ""};

        //Take user input
        String securityCode = IOFunctions.receiveUserInput();

        //make http request for reports
        JSONObject reportsJson = WEBFunctions.responseJson("https://www.cse.lk/api/financials", "symbol=" + securityCode + "\"");

        //make http request for Announcements
        JSONObject announcementJson = WEBFunctions.responseJson("https://www.cse.lk/api/getAnnouncementByCompany", "symbol=" + securityCode + "\"");

        RootCompany rootCompany = rootCompanyMapper(reportsJson, securityCode);

        if (rootCompany.callSuccess) {
            reportsOutput = Downloaders.downloadReports(rootCompany, securityCode);
            programSuccess = true;
        }

        //write output files
        if (programSuccess) {
            IOFunctions.writeOutputFiles(reportsOutput, securityCode);
        }

    }
}
