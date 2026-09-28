package model;

/** Describe un registro rechazado durante la transformación de datos. */
public class ErrorETL {
    private final String fuente;
    private final String id;
    private final String motivo;
    private final String detalle;

    /** Conserva el origen, identificador y explicación del error. */
    public ErrorETL(String fuente, String id, String motivo, String detalle) {
        this.fuente = fuente;
        this.id = id;
        this.motivo = motivo;
        this.detalle = detalle;
    }

    /** Devuelve el tipo de error usado en las estadísticas del ETL. */
    public String getMotivo() {
        return motivo;
    }

    /** Produce una línea legible para pantalla y para errores_etl.txt. */
    @Override
    public String toString() {
        return "[" + motivo + "] " + fuente + " | " + id + " | " + detalle;
    }
}
