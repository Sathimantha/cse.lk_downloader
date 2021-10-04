package Functions;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class IOFunctions {
    public static void writeOutputFiles(String[] out, String securityCode) {
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

    public static String receiveUserInput() {
        Scanner sc = new Scanner(System.in); //System.in is a standard input stream
        System.out.print("Enter the security code of the company(eg: JKH.N0000) : ");
        String str = sc.nextLine();  //reads string input
        System.out.println("Downloading files for: " + str);
        String securityCode = str.toUpperCase();
        return securityCode;
    }
}
