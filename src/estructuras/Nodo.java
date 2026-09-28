package estructuras;

/**
 * Nodo genérico usado por la lista enlazada, la pila y la cola.
 * Guarda un dato y la referencia al siguiente nodo de la cadena.
 */
public class Nodo<T> {

    /** Valor almacenado en el nodo. */
    public T dato;

    /** Referencia al siguiente nodo; es null si este es el último. */
    public Nodo<T> siguiente;

    /** Crea un nodo con el dato indicado y sin siguiente. */
    public Nodo(T dato) {
        this.dato = dato;
        this.siguiente = null;
    }
}
