package model;

import java.util.Locale;

/** Registro de un estudiante leído desde estudiantes.csv. */
public class Estudiante {

    private String id;
    private String nombres;
    private String apellidos;
    private int edad;
    private String carrera;
    private int semestre;
    private double promedio;

    /** Crea un estudiante con todos sus datos ya validados. */
    public Estudiante(String id, String nombres, String apellidos, int edad,
                      String carrera, int semestre, double promedio) {
        this.id = id;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.edad = edad;
        this.carrera = carrera;
        this.semestre = semestre;
        this.promedio = promedio;
    }

    public String getId() {
        return id;
    }

    public String getNombres() {
        return nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public int getEdad() {
        return edad;
    }

    public String getCarrera() {
        return carrera;
    }

    public int getSemestre() {
        return semestre;
    }

    public double getPromedio() {
        return promedio;
    }

    /** Devuelve el registro en formato CSV, usado en la fase Load. */
    public String toCsv() {
        return String.join(",", id, nombres, apellidos, String.valueOf(edad), carrera,
                String.valueOf(semestre), String.format(Locale.US, "%.2f", promedio));
    }

    @Override
    public String toString() {
        return id + " | " + nombres + " " + apellidos + " | " + carrera
                + " | sem=" + semestre + " | promedio=" + promedio;
    }
}
