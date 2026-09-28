package model;

/**
 * Relación de prerrequisito: la asignatura "origen" debe aprobarse
 * antes de la asignatura "dependiente". Será una arista del grafo.
 */
public class Prerequisito {

    private String origen;
    private String dependiente;

    /** Crea la relación origen -> dependiente. */
    public Prerequisito(String origen, String dependiente) {
        this.origen = origen;
        this.dependiente = dependiente;
    }

    public String getOrigen() {
        return origen;
    }

    public String getDependiente() {
        return dependiente;
    }

    /** Devuelve el registro en formato CSV, usado en la fase Load. */
    public String toCsv() {
        return origen + "," + dependiente;
    }

    @Override
    public String toString() {
        return origen + " -> " + dependiente;
    }
}
