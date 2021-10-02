import Models.Company.RootCompany;
import com.fasterxml.jackson.databind.ObjectMapper;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import kong.unirest.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

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
        Scanner sc = new Scanner(System.in); //System.in is a standard input stream
        System.out.print("Enter the security code of the company(eg: JKH) : ");
        String str = sc.nextLine();              //reads string
        System.out.println("Downloading files for: " + str);

        String securityCode = str.toUpperCase();

        String currentPath = new java.io.File(".").getCanonicalPath();
        String indexHtml = "<!DOCTYPE html><html lang=\"en\"><style>table, th, td {  border:1px solid black;}</style><head><meta charset=\"UTF-8\">";
        String wget = "";
        HttpResponse<String> response2 = Unirest.post("https://www.cse.lk/api/financials")
                .header("content-type", "application/x-www-form-urlencoded; charset=UTF-8")
                .body("symbol=" + securityCode + "\"")
                .asString();
        indexHtml += "<title>" + securityCode + "</title></head><body><h1>" + securityCode + " files</h1>";
        JSONObject json2 = new JSONObject(response2.getBody());
        ObjectMapper om = new ObjectMapper();
        indexHtml += "<table><tr><th>AnnualReports</th></tr>";
        try {
            RootCompany rootCompany = om.readValue(json2.toString(), RootCompany.class);
            //Download annual reports
            wget += "mkdir AnnualReports; cd AnnualReports;\n";
            for (int i = 0; i < rootCompany.infoAnnualData.size(); i++) {
                String path = rootCompany.infoAnnualData.get(i).path;
                String fileText = rootCompany.infoAnnualData.get(i).fileText;
                fileText = fileText.replace('/', '-');
                if (path.startsWith("cmt")) {
                    path = "https://cdn.cse.lk/" + path;
                } else {
                    path = "https://cdn.cse.lk/cmt/" + path;
                }
                wget += "wget -O \"" + Integer.toString(rootCompany.infoAnnualData.size() - i) + "-" + fileText + ".pdf\" \"" + path + "\";\n";
                indexHtml += "<tr><td><a href=\"AnnualReports/" + Integer.toString(rootCompany.infoAnnualData.size() - i) + "-" + fileText + ".pdf" + "\" target=\"_blank\">" + fileText + "</a></td></tr>";
            }
            indexHtml += "</table>";
            ////
            //Download Quarterly reports
            indexHtml += "<table><tr><th>QuarterlyReports</th></tr>";
            wget += "cd ..; mkdir QuarterlyReports; cd QuarterlyReports;\n";
            for (int i = 0; i < rootCompany.infoQuarterlyData.size(); i++) {
                String path = rootCompany.infoQuarterlyData.get(i).path;
                String fileText = rootCompany.infoQuarterlyData.get(i).fileText;
                fileText = fileText.replace('/', '-');
                if (path.startsWith("cmt")) {
                    path = "https://cdn.cse.lk/" + path;
                } else {
                    path = "https://cdn.cse.lk/cmt/" + path;
                }
                wget += "wget -O \"" + Integer.toString(rootCompany.infoQuarterlyData.size() - i) + "-" + fileText + ".pdf\" \"" + path + "\";\n"
                ;
                indexHtml += "<tr><td><a href=\"QuarterlyReports/" + Integer.toString(rootCompany.infoQuarterlyData.size() - i) + "-" + fileText + ".pdf" + "\" target=\"_blank\">" + fileText + "</a></td></tr>";
            }
            indexHtml += "</table>";
            ////
            //Download Other reports
            indexHtml += "<table><tr><th>OtherReports</th></tr>";
            wget += "cd ..; mkdir OtherReports; cd OtherReports;\n";
            for (int i = 0; i < rootCompany.infoOtherData.size(); i++) {
                String path = rootCompany.infoOtherData.get(i).path;
                String fileText = rootCompany.infoOtherData.get(i).fileText;
                fileText = fileText.replace('/', '-');
                if (path.startsWith("cmt")) {
                    path = "https://cdn.cse.lk/" + path;
                } else {
                    path = "https://cdn.cse.lk/cmt/" + path;
                }
                wget += "wget -O \"" + Integer.toString(rootCompany.infoOtherData.size() - i) + "-" + fileText + ".pdf\" \"" + path + "\";\n";
                indexHtml += "<tr><td><a href=\"OtherReports/" + Integer.toString(rootCompany.infoOtherData.size() - i) + "-" + fileText + ".pdf" + "\" target=\"_blank\">" + fileText + "</a></td></tr>";
            }
            indexHtml += "</table>";
            ////
            wget += "cd ..;\n";
            success = true;
        } catch (Exception e) {
            System.out.println("Unfamilliar Json format");
            e.printStackTrace();
        }
        indexHtml += "</body></html>";


        //write output files
        if (success) {
            try {
                new File(securityCode).mkdirs();
                FileWriter myWriter = new FileWriter(securityCode + "/index.html");
                myWriter.write(indexHtml);
                myWriter.close();
                System.out.println("Successfully wrote index.html.");
            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
            try {
                FileWriter myWriter = new FileWriter(securityCode + "/wget.bash");
                myWriter.write(wget);
                myWriter.close();
                System.out.println("Successfully wrote wget.bash.");
            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
        }

    }

}
