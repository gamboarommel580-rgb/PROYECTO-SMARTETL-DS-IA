package etl;

import estructuras.Cola;
import java.io.*;

public class Extract {
    public static Cola<String[]> leerCSV(String p) {
        Cola<String[]> q = new Cola<>();
        try (BufferedReader b = new BufferedReader(new FileReader(p))) {
            String l;
            boolean h = true;
            while ((l = b.readLine()) != null) {
                if (l.isBlank())
                    continue;
                if (h) {
                    h = false;
                    continue;
                }
                q.encolar(l.split(",", -1));
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return q;
    }
}
