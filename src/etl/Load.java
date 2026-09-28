package etl;

import estructuras.ListaEnlazada;
import estructuras.Nodo;
import estructuras.Pila;
import model.Asignatura;
import model.ErrorETL;
import model.Estudiante;
import model.Matricula;
import model.Prerequisito;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Function;

/** Exporta los registros válidos y los errores generados por Transform. */
public class Load {
    /** Guarda una lista con su encabezado en un archivo CSV UTF-8. */
    static <T> void save(String ruta, String encabezado, ListaEnlazada<T> lista,
                         Function<T, String> convertir) throws IOException {
        Path archivo = Path.of(ruta);
        Files.createDirectories(archivo.getParent());

        try (BufferedWriter escritor = Files.newBufferedWriter(archivo, StandardCharsets.UTF_8)) {
            escritor.write(encabezado);
            escritor.newLine();

            for (Nodo<T> actual = lista.getCabeza(); actual != null; actual = actual.siguiente) {
                escritor.write(convertir.apply(actual.dato));
                escritor.newLine();
            }
        }
    }

    /** Genera todos los archivos de salida sin consumir la pila de errores. */
    public static void all(Transform transformacion) {
        try {
            save("output/estudiantes_limpios.csv",
                    "id,nombres,apellidos,edad,carrera,semestre,promedio",
                    transformacion.estudiantes, Estudiante::toCsv);
            save("output/asignaturas_limpias.csv",
                    "codigo,nombre,nivel,creditos",
                    transformacion.asignaturas, Asignatura::toCsv);
            save("output/matriculas_limpias.csv",
                    "estudiante,asignatura,periodo,estado",
                    transformacion.matriculas, Matricula::toCsv);
            save("output/prerrequisitos_limpios.csv",
                    "origen,dependiente",
                    transformacion.prerequisitos, Prerequisito::toCsv);

            guardarErrores(transformacion.errores);
        } catch (IOException excepcion) {
            throw new UncheckedIOException("No se pudieron exportar los archivos ETL", excepcion);
        }
    }

    /** Escribe los errores desde la cima y restaura el orden original de la pila. */
    private static void guardarErrores(Pila<ErrorETL> errores) throws IOException {
        Path archivo = Path.of("output/errores_etl.txt");
        Files.createDirectories(archivo.getParent());
        Pila<ErrorETL> temporal = new Pila<>();

        try (BufferedWriter escritor = Files.newBufferedWriter(archivo, StandardCharsets.UTF_8)) {
            while (!errores.estaVacia()) {
                ErrorETL error = errores.desapilar();
                temporal.apilar(error);
                escritor.write(error.toString());
                escritor.newLine();
            }
        } finally {
            while (!temporal.estaVacia()) {
                errores.apilar(temporal.desapilar());
            }
        }
    }
}
