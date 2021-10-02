import Models.CompanyReports.InfoData;
import Models.CompanyReports.RootCompany;
import Models.reportType;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import kong.unirest.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Functions {

    public static String[] downloadreports(RootCompany rootCompany, String securityCode) {
        List<InfoData> infoDataList = rootCompany.infoAnnualData;
        String indexHtml = "<!DOCTYPE html><html lang=\"en\"><style>table, th, td {  border:1px solid black;}</style><head><meta charset=\"UTF-8\">";
        String wget = "";
        indexHtml += "<title>" + securityCode + "</title></head><body><h1>" + securityCode + " files</h1>";

        for (reportType reportTypeEnum : reportType.values()) {
            if (reportTypeEnum == reportType.ANNUAL_REPORTS) {
                infoDataList = rootCompany.infoAnnualData;
            } else if (reportTypeEnum == reportType.QUARTERLY_REPORTS) {
                infoDataList = rootCompany.infoQuarterlyData;
            } else if (reportTypeEnum == reportType.OTHER_REPORTS) {
                infoDataList = rootCompany.infoOtherData;
            }

            indexHtml += "<table><tr><th>" + reportTypeEnum + "</th></tr>";

            wget += "mkdir " + reportTypeEnum + "; cd " + reportTypeEnum + ";\n";

            for (int i = 0; i < infoDataList.size(); i++) {
                String path = infoDataList.get(i).path;
                String fileText = infoDataList.get(i).fileText;
                fileText = fileText.replace('/', '-');
                if (path.startsWith("cmt")) {
                    path = "https://cdn.cse.lk/" + path;
                } else {
                    path = "https://cdn.cse.lk/cmt/" + path;
                }
                indexHtml += "<tr><td><a href=\"" + reportTypeEnum + "/" + Integer.toString(infoDataList.size() - i) + "-" + fileText + ".pdf" + "\" target=\"_blank\">" + fileText + "</a></td></tr>";
                wget += "wget -O \"" + Integer.toString(infoDataList.size() - i) + "-" + fileText + ".pdf\" \"" + path + "\";\n";
            }
            indexHtml += "</table>";
            wget += "cd ..;";
        }
        indexHtml += "</body></html>";
        String results[] = {indexHtml, wget};
        return results;
    }

    public static void writeoutput(String[] out, String securityCode) {
        try {
            new File(securityCode).mkdirs();
            FileWriter myWriter = new FileWriter(securityCode + "/index.html");
            myWriter.write(out[0]);
            myWriter.close();
            System.out.println("Successfully wrote index.html.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        try {
            FileWriter myWriter = new FileWriter(securityCode + "/wget.bash");
            myWriter.write(out[1]);
            myWriter.close();
            System.out.println("Successfully wrote wget.bash.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static JSONObject responseJson(String path, String body) {
        HttpResponse<String> response2 = Unirest.post(path)
                .header("content-type", "application/x-www-form-urlencoded; charset=UTF-8")
                .body(body)
                .asString();
        JSONObject reportsJson = new JSONObject(response2.getBody());
        return reportsJson;
    }

    public static String receiveUserInput() {
        Scanner sc = new Scanner(System.in); //System.in is a standard input stream
        System.out.print("Enter the security code of the company(eg: JKH.N0000) : ");
        String str = sc.nextLine();  //reads string input
        System.out.println("Downloading files for: " + str);
        String securityCode = str.toUpperCase();
        return securityCode;
    }
}


