import estructuras.Cola;
import estructuras.ListaEnlazada;
import etl.Extract;
import etl.Transform;
import model.Estudiante;
 
import java.util.Map;
import java.util.Scanner;
 
/**
 * Punto de entrada de SmartETL-DS + IA.
 *
 * Versión del Hito 1 (25 %): implementa el flujo mínimo
 * CSV -> EXTRACT (Cola) -> TRANSFORM -> LISTA -> mostrar / consultar.
 * En los siguientes hitos se agregarán Load, ordenamiento, BST, grafos e IA.
 */
public class Main {
 
    /** Rutas de los archivos de entrada (relativas a la raíz del proyecto). */
    private static final String RUTA_ESTUDIANTES = "data/estudiantes.csv";
    private static final String RUTA_ASIGNATURAS = "data/asignaturas.csv";
    private static final String RUTA_MATRICULAS = "data/matriculas.csv";
    private static final String RUTA_PRERREQUISITOS = "data/prerrequisitos.csv";
 
    /** Resultado del proceso ETL; es null hasta ejecutar la opción 1. */
    private static Transform transformacion;
 
    public static void main(String[] args) {
        Scanner lector = new Scanner(System.in);
        int opcion;
 
        do {
            mostrarMenu();
            opcion = leerOpcion(lector);
 
            switch (opcion) {
                case 1 -> ejecutarEtl();
                case 2 -> mostrarEstudiantesValidos();
                case 3 -> mostrarErrores();
                case 4 -> buscarEstudiantePorId(lector);
                case 5 -> mostrarResumen();
                case 0 -> System.out.println("Saliendo del sistema...");
                default -> System.out.println("Opción no válida. Intente de nuevo.");
            }
        } while (opcion != 0);
 
        lector.close();
    }
 
    /** Imprime el menú del Hito 1. */
    private static void mostrarMenu() {
        System.out.println();
        System.out.println("==============================================");
        System.out.println("     SMARTETL-DS + IA  |  Hito 1");
        System.out.println("==============================================");
        System.out.println(" 1. Ejecutar ETL (Extract + Transform)");
        System.out.println(" 2. Mostrar estudiantes válidos (lista)");
        System.out.println(" 3. Mostrar pila de errores");
        System.out.println(" 4. Buscar estudiante por ID");
        System.out.println(" 5. Resumen del proceso");
        System.out.println(" 0. Salir");
        System.out.print("Seleccione una opción: ");
    }
 
    /** Lee una opción numérica; devuelve -1 si el usuario escribe algo no numérico. */
    private static int leerOpcion(Scanner lector) {
        try {
            return Integer.parseInt(lector.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
 
    /**
     * EXTRACT: cada archivo se lee y sus registros se encolan (FIFO).
     * TRANSFORM: se desencola cada registro, se valida y se envía
     * a la lista de válidos o a la pila de errores.
     */
    private static void ejecutarEtl() {
        transformacion = new Transform();
 
        Cola<String[]> colaEstudiantes = Extract.leerCSV(RUTA_ESTUDIANTES);
        System.out.println("Registros encolados de estudiantes: " + colaEstudiantes.tamanio());
        transformacion.estudiantes(colaEstudiantes);
 
        // Asignaturas antes que matrículas y prerrequisitos,
        // porque estos se validan contra los códigos existentes.
        transformacion.asignaturas(Extract.leerCSV(RUTA_ASIGNATURAS));
        transformacion.matriculas(Extract.leerCSV(RUTA_MATRICULAS));
        transformacion.prerequisitos(Extract.leerCSV(RUTA_PRERREQUISITOS));
 
        System.out.println("ETL ejecutado correctamente.");
        mostrarResumen();
    }
 
    /** Recorre la lista enlazada de estudiantes válidos. */
    private static void mostrarEstudiantesValidos() {
        if (!etlEjecutado()) {
            return;
        }
        System.out.println("--- Estudiantes válidos (" + transformacion.estudiantes.tamanio() + ") ---");
        transformacion.estudiantes.mostrar();
    }
 
    /** Muestra la pila de errores desde la cima (el último error detectado aparece primero). */
    private static void mostrarErrores() {
        if (!etlEjecutado()) {
            return;
        }
        System.out.println("--- Pila de errores (" + transformacion.errores.tamanio() + ") ---");
        transformacion.errores.mostrar();
    }
 
    /** Búsqueda secuencial en la lista de estudiantes válidos. */
    private static void buscarEstudiantePorId(Scanner lector) {
        if (!etlEjecutado()) {
            return;
        }
        System.out.print("Ingrese el ID del estudiante: ");
        String idBuscado = lector.nextLine().trim();
 
        ListaEnlazada<Estudiante> lista = transformacion.estudiantes;
        for (int i = 0; i < lista.tamanio(); i++) {
            Estudiante actual = lista.obtener(i);
            if (actual.getId().equals(idBuscado)) {
                System.out.println("Encontrado en la posición " + i + ": " + actual);
                return;
            }
        }
        System.out.println("No existe un estudiante válido con ID " + idBuscado + ".");
    }
 
    /** Muestra cantidades de registros válidos y errores por categoría. */
    private static void mostrarResumen() {
        if (!etlEjecutado()) {
            return;
        }
        System.out.println("--- Resumen del ETL ---");
        System.out.println("Estudiantes válidos:  " + transformacion.estudiantes.tamanio());
        System.out.println("Asignaturas válidas:  " + transformacion.asignaturas.tamanio());
        System.out.println("Matrículas válidas:   " + transformacion.matriculas.tamanio());
        System.out.println("Prerrequisitos:       " + transformacion.prerequisitos.tamanio());
        System.out.println("Errores detectados:   " + transformacion.errores.tamanio());
        for (Map.Entry<String, Integer> categoria : transformacion.stats.entrySet()) {
            System.out.println("  - " + categoria.getKey() + ": " + categoria.getValue());
        }
    }
 
    /** Evita NullPointerException si el usuario consulta antes de ejecutar el ETL. */
    private static boolean etlEjecutado() {
        if (transformacion == null) {
            System.out.println("Primero ejecute la opción 1 (Ejecutar ETL).");
            return false;
        }
        return true;
    }
}