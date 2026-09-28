package model;

/** Registro de una asignatura leído desde asignaturas.csv. */
public class Asignatura {

    private String codigo;
    private String nombre;
    private int nivel;
    private int creditos;

    /** Crea una asignatura con sus datos ya validados. */
    public Asignatura(String codigo, String nombre, int nivel, int creditos) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.nivel = nivel;
        this.creditos = creditos;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public int getNivel() {
        return nivel;
    }

    public int getCreditos() {
        return creditos;
    }

    /** Devuelve el registro en formato CSV, usado en la fase Load. */
    public String toCsv() {
        return codigo + "," + nombre + "," + nivel + "," + creditos;
    }

    @Override
    public String toString() {
        return codigo + " | " + nombre + " | nivel=" + nivel + " | creditos=" + creditos;
    }
}
