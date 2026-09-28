package etl;

import estructuras.Cola;
import estructuras.ListaEnlazada;
import estructuras.Pila;
import model.Asignatura;
import model.ErrorETL;
import model.Estudiante;
import model.Matricula;
import model.Prerequisito;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Fase TRANSFORM del ETL.
 *
 * Desencola cada registro extraído, lo valida y normaliza.
 * Los registros válidos se insertan en listas enlazadas y
 * los inválidos se apilan en la pila de errores con su motivo.
 */
public class Transform {

    // Nombres de los archivos, usados para identificar el origen de cada error
    private static final String ARCHIVO_ESTUDIANTES = "estudiantes.csv";
    private static final String ARCHIVO_ASIGNATURAS = "asignaturas.csv";
    private static final String ARCHIVO_MATRICULAS = "matriculas.csv";
    private static final String ARCHIVO_PRERREQUISITOS = "prerrequisitos.csv";

    /** Registros válidos de cada archivo. */
    public ListaEnlazada<Estudiante> estudiantes = new ListaEnlazada<>();
    public ListaEnlazada<Asignatura> asignaturas = new ListaEnlazada<>();
    public ListaEnlazada<Matricula> matriculas = new ListaEnlazada<>();
    public ListaEnlazada<Prerequisito> prerequisitos = new ListaEnlazada<>();

    /** Pila de errores: el último error detectado queda en la cima. */
    public Pila<ErrorETL> errores = new Pila<>();

    /** Cantidad de errores por motivo (ej. DUPLICADO -> 1). */
    public Map<String, Integer> stats = new HashMap<>();

    /** IDs de estudiantes y códigos de asignaturas ya vistos, para detectar duplicados. */
    private Set<String> idsRegistrados = new HashSet<>();
    private Set<String> codigosRegistrados = new HashSet<>();

    // ------------------------------------------------------------------
    // Métodos auxiliares
    // ------------------------------------------------------------------

    /** Apila un error y suma uno al contador de su motivo. */
    private void registrarError(String archivo, String identificador, String motivo, String detalle) {
        errores.apilar(new ErrorETL(archivo, identificador, motivo, detalle));
        stats.put(motivo, stats.getOrDefault(motivo, 0) + 1);
    }

    /** Quita espacios al inicio y al final, y reduce espacios repetidos a uno solo. */
    private String normalizar(String texto) {
        return texto.trim().replaceAll("\\s+", " ");
    }

    /** Busca secuencialmente si existe un estudiante válido con ese ID. */
    private boolean existeEstudiante(String id) {
        for (int i = 0; i < estudiantes.tamanio(); i++) {
            if (estudiantes.obtener(i).getId().equals(id)) {
                return true;
            }
        }
        return false;
    }

    /** Busca secuencialmente si existe una asignatura válida con ese código. */
    private boolean existeAsignatura(String codigo) {
        for (int i = 0; i < asignaturas.tamanio(); i++) {
            if (asignaturas.obtener(i).getCodigo().equals(codigo)) {
                return true;
            }
        }
        return false;
    }

    // ------------------------------------------------------------------
    // Transformación por archivo
    // ------------------------------------------------------------------

    /**
     * Valida estudiantes. Formato esperado:
     * id, nombres, apellidos, edad, carrera, semestre, promedio
     */
    public void estudiantes(Cola<String[]> colaRegistros) {
        while (!colaRegistros.estaVacia()) {
            String[] campos = colaRegistros.desencolar();

            if (campos.length < 7) {
                registrarError(ARCHIVO_ESTUDIANTES, "?", "DATOS_INCOMPLETOS", "7 campos requeridos");
                continue;
            }

            String id = normalizar(campos[0]);
            try {
                int edad = Integer.parseInt(campos[3].trim());
                int semestre = Integer.parseInt(campos[5].trim());
                double promedio = Double.parseDouble(campos[6].trim());

                if (!id.matches("\\d{10}")) {
                    registrarError(ARCHIVO_ESTUDIANTES, id, "IDENTIFICADOR_INCORRECTO", "10 digitos requeridos");
                    continue;
                }
                // add() devuelve false si el ID ya estaba registrado
                if (!idsRegistrados.add(id)) {
                    registrarError(ARCHIVO_ESTUDIANTES, id, "DUPLICADO", "ID repetido");
                    continue;
                }
                if (edad < 16 || edad > 80) {
                    registrarError(ARCHIVO_ESTUDIANTES, id, "RANGO_EDAD", "Edad permitida: 16..80");
                    continue;
                }
                if (semestre < 1 || semestre > 12) {
                    registrarError(ARCHIVO_ESTUDIANTES, id, "RANGO_SEMESTRE", "Semestre permitido: 1..12");
                    continue;
                }
                if (promedio < 0 || promedio > 10) {
                    registrarError(ARCHIVO_ESTUDIANTES, id, "RANGO_PROMEDIO", "Promedio permitido: 0..10");
                    continue;
                }

                String nombres = normalizar(campos[1]);
                String apellidos = normalizar(campos[2]);
                String carrera = normalizar(campos[4]);
                if (nombres.isEmpty() || apellidos.isEmpty() || carrera.isEmpty()) {
                    registrarError(ARCHIVO_ESTUDIANTES, id, "CAMPO_OBLIGATORIO", "Campo vacio");
                    continue;
                }

                estudiantes.insertarFinal(
                        new Estudiante(id, nombres, apellidos, edad, carrera, semestre, promedio));
            } catch (NumberFormatException e) {
                registrarError(ARCHIVO_ESTUDIANTES, id, "FORMATO_NUMERICO", "Dato numerico invalido");
            }
        }
    }

    /** Valida asignaturas. Formato esperado: codigo, nombre, nivel, creditos */
    public void asignaturas(Cola<String[]> colaRegistros) {
        while (!colaRegistros.estaVacia()) {
            String[] campos = colaRegistros.desencolar();

            if (campos.length < 4) {
                registrarError(ARCHIVO_ASIGNATURAS, "?", "DATOS_INCOMPLETOS", "4 campos requeridos");
                continue;
            }

            String codigo = normalizar(campos[0]);
            try {
                int nivel = Integer.parseInt(campos[2].trim());
                int creditos = Integer.parseInt(campos[3].trim());

                if (!codigosRegistrados.add(codigo)) {
                    registrarError(ARCHIVO_ASIGNATURAS, codigo, "DUPLICADO", "Codigo repetido");
                    continue;
                }
                asignaturas.insertarFinal(new Asignatura(codigo, normalizar(campos[1]), nivel, creditos));
            } catch (NumberFormatException e) {
                registrarError(ARCHIVO_ASIGNATURAS, codigo, "FORMATO_NUMERICO", "Dato numerico invalido");
            }
        }
    }

    /**
     * Valida matrículas. Formato esperado: estudiante, asignatura, periodo, estado.
     * Requiere que estudiantes y asignaturas ya estén transformados.
     */
    public void matriculas(Cola<String[]> colaRegistros) {
        while (!colaRegistros.estaVacia()) {
            String[] campos = colaRegistros.desencolar();

            if (campos.length < 4) {
                registrarError(ARCHIVO_MATRICULAS, "?", "DATOS_INCOMPLETOS", "4 campos requeridos");
                continue;
            }

            String idEstudiante = campos[0];
            String codigoAsignatura = campos[1];
            String periodo = campos[2];
            String estado = campos[3];

            if (!existeEstudiante(idEstudiante) || !existeAsignatura(codigoAsignatura)) {
                registrarError(ARCHIVO_MATRICULAS, idEstudiante, "REFERENCIA_INCONSISTENTE",
                        "Estudiante o asignatura inexistente");
                continue;
            }
            if (!estado.equalsIgnoreCase("ACTIVA") && !estado.equalsIgnoreCase("RETIRADA")) {
                registrarError(ARCHIVO_MATRICULAS, idEstudiante, "ESTADO_INVALIDO", "Estado no reconocido");
                continue;
            }
            matriculas.insertarFinal(
                    new Matricula(idEstudiante, codigoAsignatura, periodo, estado.toUpperCase()));
        }
    }

    /**
     * Valida prerrequisitos. Formato esperado: asignatura_origen, asignatura_dependiente.
     * Requiere que las asignaturas ya estén transformadas.
     */
    public void prerequisitos(Cola<String[]> colaRegistros) {
        while (!colaRegistros.estaVacia()) {
            String[] campos = colaRegistros.desencolar();

            boolean referenciaValida = campos.length >= 2
                    && existeAsignatura(campos[0])
                    && existeAsignatura(campos[1]);

            if (!referenciaValida) {
                String identificador = campos.length > 0 ? campos[0] : "?";
                registrarError(ARCHIVO_PRERREQUISITOS, identificador, "REFERENCIA_INCONSISTENTE",
                        "Asignatura inexistente");
                continue;
            }
            prerequisitos.insertarFinal(new Prerequisito(campos[0], campos[1]));
        }
    }
}
