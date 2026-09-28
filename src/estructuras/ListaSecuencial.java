package estructuras;

/**
 * Lista secuencial genérica basada en un arreglo.
 * Los elementos se guardan en posiciones contiguas y el arreglo
 * duplica su capacidad cuando se llena.
 */
public class ListaSecuencial<T> {

    /** Capacidad con la que se crea el arreglo. */
    private static final int CAPACIDAD_INICIAL = 10;

    /** Arreglo donde se almacenan los elementos. */
    private Object[] elementos = new Object[CAPACIDAD_INICIAL];

    /** Cantidad de elementos ocupados en el arreglo. */
    private int tamanio;

    /** Crea un arreglo del doble de tamaño y copia los elementos existentes. */
    private void crecer() {
        Object[] nuevoArreglo = new Object[elementos.length * 2];
        System.arraycopy(elementos, 0, nuevoArreglo, 0, tamanio);
        elementos = nuevoArreglo;
    }

    /** Inserta un elemento al final. O(1), salvo cuando el arreglo debe crecer. */
    public void insertar(T dato) {
        if (tamanio == elementos.length) {
            crecer();
        }
        elementos[tamanio] = dato;
        tamanio++;
    }

    /** Retorna el elemento de la posición indicada con acceso directo: O(1). */
    @SuppressWarnings("unchecked")
    public T obtener(int posicion) {
        if (posicion < 0 || posicion >= tamanio) {
            throw new IndexOutOfBoundsException("Posicion fuera de rango: " + posicion);
        }
        return (T) elementos[posicion];
    }

    /** Retorna la cantidad de elementos. */
    public int tamanio() {
        return tamanio;
    }

    /** Indica si la lista no tiene elementos. */
    public boolean estaVacia() {
        return tamanio == 0;
    }
}
