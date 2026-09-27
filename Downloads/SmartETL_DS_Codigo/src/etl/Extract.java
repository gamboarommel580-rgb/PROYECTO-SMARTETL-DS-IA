package etl;

import estructuras.Cola;
import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * Clase encargada de la extracción e importación de datos desde archivos CSV.
 */
public class Extract {

    // Lee un archivo CSV garantizando la codificación UTF-8
    public static Cola<String[]> leerCSV(String rutaArchivo) {
        Cola<String[]> colaDatos = new Cola<>();

        try (BufferedReader lector = new BufferedReader(
                new InputStreamReader(new FileInputStream(rutaArchivo), StandardCharsets.UTF_8))) {

            String linea;
            boolean esCabecera = true;

            while ((linea = lector.readLine()) != null) {
                if (linea.isBlank()) {
                    continue;
                }
                if (esCabecera) {
                    esCabecera = false;
                    continue; // Omite la primera línea (encabezados del CSV)
                }
                colaDatos.encolar(linea.split(",", -1));
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo CSV: " + e.getMessage());
        }

        return colaDatos;
    }
}