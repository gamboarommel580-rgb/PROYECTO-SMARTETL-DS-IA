package model;

/** Error detectado durante la fase Transform; se almacena en la pila de errores. */
public class ErrorETL {

    private String fuente;
    private String identificador;
    private String motivo;
    private String detalle;

    /**
     * @param fuente        archivo donde ocurrió el error (ej. estudiantes.csv)
     * @param identificador ID o código del registro con error
     * @param motivo        categoría del error (ej. DUPLICADO, RANGO_EDAD)
     * @param detalle       explicación legible del error
     */
    public ErrorETL(String fuente, String identificador, String motivo, String detalle) {
        this.fuente = fuente;
        this.identificador = identificador;
        this.motivo = motivo;
        this.detalle = detalle;
    }

    public String getFuente() {
        return fuente;
    }

    public String getIdentificador() {
        return identificador;
    }

    public String getMotivo() {
        return motivo;
    }

    public String getDetalle() {
        return detalle;
    }

    @Override
    public String toString() {
        return "[" + motivo + "] " + fuente + " | " + identificador + " | " + detalle;
    }
}
