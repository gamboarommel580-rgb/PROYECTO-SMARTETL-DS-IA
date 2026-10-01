import etl.Extract;
import etl.Load;
import etl.Transform;
import estructuras.Pila;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

/** Prueba sin dependencias externas del recorrido CSV → ETL → archivos. */
public class ETLIntegrationTest {
    public static void main(String[] args) throws Exception {
        Pila<String> pila = new Pila<>();
        pila.apilar("primero");
        pila.apilar("segundo");
        comprobar(pila.tamanio() == 2 && "segundo".equals(pila.cima()), "cima LIFO");
        comprobar("segundo".equals(pila.desapilar()), "desapilar LIFO");
        comprobar("primero".equals(pila.desapilar()) && pila.estaVacia(), "pila vacía");

                Transform transformacion = new Transform();
        transformacion.estudiantes(Extract.leerCSV("data/estudiantes.csv"));
        transformacion.asignaturas(Extract.leerCSV("data/asignaturas.csv"));
        transformacion.matriculas(Extract.leerCSV("data/matriculas.csv"));
        transformacion.prerequisitos(Extract.leerCSV("data/prerrequisitos.csv"));



        comprobar(transformacion.estudiantes.tamanio() == 5, "estudiantes válidos");
        comprobar(transformacion.errores.tamanio() == 8, "errores de entrada");
        Load.all(transformacion);
        comprobar(transformacion.errores.tamanio() == 8, "Load conserva la pila");
        comprobar(Files.readAllLines(Path.of("output/estudiantes_limpios.csv"),
                StandardCharsets.UTF_8).size() == 6, "CSV exportado");
        comprobar(Files.readAllLines(Path.of("output/errores_etl.txt"),
                StandardCharsets.UTF_8).size() == 8, "errores exportados");
        comprobar(Files.exists(Path.of("output/asignaturas_limpias.csv"))
                && Files.exists(Path.of("output/matriculas_limpias.csv"))
                && Files.exists(Path.of("output/prerrequisitos_limpios.csv")), "salidas completas");

        System.out.println("OK: Pila, 5 estudiantes, 8 errores y 5 archivos de salida");
    }

    private static void comprobar(boolean condicion, String caso) {
        if (!condicion) {
            throw new AssertionError(caso);
        }
    }
}
