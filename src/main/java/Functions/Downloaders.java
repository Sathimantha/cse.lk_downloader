package Functions;

import Models.CompanyReports.InfoData;
import Models.CompanyReports.RootCompany;
import Models.reportType;

import java.util.List;

public class Downloaders {
    public static String[] downloadReports(RootCompany rootCompany, String securityCode) {
        List<InfoData> infoDataList = rootCompany.infoAnnualData;
        String indexHtml = HTMLFunctions.htmlHeader(securityCode);
        String wget = "";

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
            indexHtml += HTMLFunctions.htmlFooter();
            wget += "cd ..;";
        }
        indexHtml += "</body></html>";
        String results[] = {indexHtml, wget};
        return results;
    }
}

