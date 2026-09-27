package estructuras;

/**
 * Estructura de datos dinámica de tipo Cola (FIFO - First In, First Out).
 */
public class Cola<T> {
    private Nodo<T> frente;
    private Nodo<T> fin;
    private int tamanio;

    // Constructor por defecto
    public Cola() {
        this.frente = null;
        this.fin = null;
        this.tamanio = 0;
    }

    // Inserta un elemento al final de la cola
    public void encolar(T datoNuevo) {
        Nodo<T> nuevoNodo = new Nodo<>(datoNuevo);
        if (fin == null) {
            frente = fin = nuevoNodo;
        } else {
            fin.siguiente = nuevoNodo;
            fin = nuevoNodo;
        }
        tamanio++;
    }

    // Retira y retorna el primer elemento de la cola
    public T desencolar() {
        if (frente == null) {
            return null;
        }
        T datoExtraido = frente.dato;
        frente = frente.siguiente;
        if (frente == null) {
            fin = null;
        }
        tamanio--;
        return datoExtraido;
    }
    // Retorna el primer elemento sin retirarlo de la cola
    public T frente() {
        if (frente == null) {
            return null;
        }
        return frente.dato;
    }
    // Retorna la cantidad de elementos en la cola
    public int tamanio() {
        return tamanio;
    }

    // Verifica si la cola no contiene elementos
    public boolean estaVacia() {
        return frente == null;
    }

    // Muestra los elementos de la cola en consola
    public void mostrar() {
        Nodo<T> aux = frente;
        while (aux != null) {
            System.out.println(aux.dato);
            aux = aux.siguiente;
        }
    }
}