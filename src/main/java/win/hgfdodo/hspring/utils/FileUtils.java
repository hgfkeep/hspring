package win.hgfdodo.hspring.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FileUtils {
    public static void print(InputStream inputStream) throws IOException {
        System.out.println("-----------------file start---------------------");
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        String line = null;
        while ((line = br.readLine())!=null){
            System.out.println(line);
        }
        System.out.println("-----------------file end---------------------");
    }
}
