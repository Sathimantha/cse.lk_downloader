package Functions;

public class HTMLFunctions {
    public static String htmlHeader(String securityCode) {
        String indexHtmlHeader = "";
        indexHtmlHeader += "<!DOCTYPE html><html lang=\"en\"><style>table, th, td {  border:1px solid black;}</style><head><meta charset=\"UTF-8\">";
        indexHtmlHeader += "<title>" + securityCode + "</title></head><body><h1>" + securityCode + " files</h1>";
        return indexHtmlHeader;
    }

    public static String htmlFooter() {
        String htmlFooter = "";
        htmlFooter += "</body></html>";
        return htmlFooter;
    }

    private static String htmlOpenTable(String reportTypeEnum) {
        String htmlFooter = "";
        htmlFooter += "</body></html>";
        return htmlFooter;
    }

    private static String htmlCloseTable() {
        String htmlFooter = "";
        htmlFooter += "</body></html>";
        return htmlFooter;
    }
}
