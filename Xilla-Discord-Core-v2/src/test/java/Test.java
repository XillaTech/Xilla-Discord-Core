import java.util.*;
import java.io.*;
public class Test {
    public static void main(String [] args) throws IOException {
        HashMap<Integer, String> products = new HashMap<Integer, String>();

        String filename = "hi.txt";
        File file = new File(filename);
        FileReader fr = new FileReader(file);

        String builder = "";
        int i;
        while ((i=fr.read()) != -1)
            builder = builder + (char)i;

        String finalResult = builder.toString();

        String[] results = finalResult.substring(1, finalResult.length() - 1).split(", ");
        for(String result : results) {
            String[] data = result.split("=");

            products.put(Integer.parseInt(data[0]), data[1]);
        }

    }
}