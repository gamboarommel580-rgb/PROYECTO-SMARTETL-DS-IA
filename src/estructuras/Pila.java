package estructuras;

/** Pila genérica LIFO implementada con los nodos propios del proyecto. */
public class Pila<T> {
    private Nodo<T> cima;
    private int cantidad;

    /** Coloca un dato sobre la cima. */
    public void apilar(T dato) {
        Nodo<T> nuevo = new Nodo<>(dato);
        nuevo.siguiente = cima;
        cima = nuevo;
        cantidad++;
    }

    /** Retira y devuelve la cima; devuelve null si la pila está vacía. */
    public T desapilar() {
        if (estaVacia()) {
            return null;
        }

        T dato = cima.dato;
        cima = cima.siguiente;
        cantidad--;
        return dato;
    }

    /** Consulta la cima sin retirarla. */
    public T cima() {
        return estaVacia() ? null : cima.dato;
    }

    /** Indica si no hay elementos. */
    public boolean estaVacia() {
        return cima == null;
    }

    /** Devuelve el número de elementos. */
    public int tamanio() {
        return cantidad;
    }

    /** Muestra los datos desde la cima hacia la base sin modificar la pila. */
    public void mostrar() {
        for (Nodo<T> actual = cima; actual != null; actual = actual.siguiente) {
            System.out.println(actual.dato);
        }
    }
}
