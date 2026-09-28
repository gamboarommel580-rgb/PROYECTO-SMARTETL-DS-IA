package estructuras;

import java.util.Objects;

/**
 * Lista simplemente enlazada genérica.
 * Se usa para guardar los registros válidos del ETL
 * y, más adelante, las listas de adyacencia del grafo.
 */
public class ListaEnlazada<T> {

    /** Primer nodo de la lista; es null cuando la lista está vacía. */
    private Nodo<T> cabeza;

    /** Cantidad de elementos almacenados. */
    private int tamanio;

    /** Inserta un elemento al inicio de la lista. Complejidad O(1). */
    public void insertarInicio(T dato) {
        Nodo<T> nuevoNodo = new Nodo<>(dato);
        nuevoNodo.siguiente = cabeza;
        cabeza = nuevoNodo;
        tamanio++;
    }

    /** Inserta un elemento al final de la lista. Recorre hasta el último nodo: O(n). */
    public void insertarFinal(T dato) {
        Nodo<T> nuevoNodo = new Nodo<>(dato);
        if (cabeza == null) {
            cabeza = nuevoNodo;
        } else {
            Nodo<T> actual = cabeza;
            while (actual.siguiente != null) {
                actual = actual.siguiente;
            }
            actual.siguiente = nuevoNodo;
        }
        tamanio++;
    }

    /** Elimina y retorna el primer elemento; retorna null si la lista está vacía. */
    public T eliminarInicio() {
        if (cabeza == null) {
            return null;
        }
        T datoEliminado = cabeza.dato;
        cabeza = cabeza.siguiente;
        tamanio--;
        return datoEliminado;
    }

    /**
     * Retorna el elemento de la posición indicada (empezando en 0).
     * Debe recorrer los nodos desde la cabeza: O(n).
     */
    public T obtener(int posicion) {
        if (posicion < 0 || posicion >= tamanio) {
            throw new IndexOutOfBoundsException("Posición fuera de rango: " + posicion);
        }
        Nodo<T> actual = cabeza;
        for (int i = 0; i < posicion; i++) {
            actual = actual.siguiente;
        }
        return actual.dato;
    }

    /** Indica si la lista contiene el dato buscado (búsqueda secuencial). */
    public boolean contiene(T datoBuscado) {
        for (Nodo<T> actual = cabeza; actual != null; actual = actual.siguiente) {
            if (Objects.equals(actual.dato, datoBuscado)) {
                return true;
            }
        }
        return false;
    }

    /** Retorna la cantidad de elementos. */
    public int tamanio() {
        return tamanio;
    }

    /** Indica si la lista no tiene elementos. */
    public boolean estaVacia() {
        return cabeza == null;
    }

    /** Retorna el primer nodo, para recorrer la lista desde otras clases. */
    public Nodo<T> getCabeza() {
        return cabeza;
    }

    /** Imprime todos los elementos, uno por línea, desde la cabeza. */
    public void mostrar() {
        for (Nodo<T> actual = cabeza; actual != null; actual = actual.siguiente) {
            System.out.println(actual.dato);
        }
    }
}
