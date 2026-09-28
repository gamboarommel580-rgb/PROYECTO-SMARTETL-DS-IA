package model;

/** Registro de una matrícula: qué estudiante cursa qué asignatura y en qué período. */
public class Matricula {

    private String estudiante;
    private String asignatura;
    private String periodo;
    private String estado;

    /** Crea una matrícula con sus datos ya validados. */
    public Matricula(String estudiante, String asignatura, String periodo, String estado) {
        this.estudiante = estudiante;
        this.asignatura = asignatura;
        this.periodo = periodo;
        this.estado = estado;
    }

    public String getEstudiante() {
        return estudiante;
    }

    public String getAsignatura() {
        return asignatura;
    }

    public String getPeriodo() {
        return periodo;
    }

    public String getEstado() {
        return estado;
    }

    /** Devuelve el registro en formato CSV, usado en la fase Load. */
    public String toCsv() {
        return estudiante + "," + asignatura + "," + periodo + "," + estado;
    }

    @Override
    public String toString() {
        return estudiante + " -> " + asignatura + " | " + periodo + " | " + estado;
    }
}
