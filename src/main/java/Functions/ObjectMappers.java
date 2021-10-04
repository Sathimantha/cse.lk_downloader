package Functions;

import Models.AnnouncementByCompany.RootAnnouncementCompany;
import Models.CompanyReports.RootCompany;
import com.fasterxml.jackson.databind.ObjectMapper;
import kong.unirest.json.JSONObject;

public class ObjectMappers {

    public static RootCompany rootCompanyMapper(JSONObject reportsJson, String securityCode) {
        RootCompany rootCompany = new RootCompany();
        try {
            ObjectMapper om = new ObjectMapper();
            rootCompany = om.readValue(reportsJson.toString(), RootCompany.class);
            rootCompany.callSuccess=true;
            return rootCompany;
        } catch (Exception e) {
            System.out.println("Unfamiliar Json format");
            e.printStackTrace();
            return rootCompany;
        }
    }


    public static RootAnnouncementCompany rootAnnouncementByCompanyMapper(JSONObject reportsJson, String securityCode) {
        RootAnnouncementCompany rootAnnouncementCompany = new RootAnnouncementCompany();
        try {
            ObjectMapper om = new ObjectMapper();
            rootAnnouncementCompany = om.readValue(reportsJson.toString(), RootAnnouncementCompany.class);
            return rootAnnouncementCompany;
        } catch (Exception e) {
            System.out.println("Unfamiliar Json format");
            e.printStackTrace();
            return rootAnnouncementCompany;
        }
    }


}
