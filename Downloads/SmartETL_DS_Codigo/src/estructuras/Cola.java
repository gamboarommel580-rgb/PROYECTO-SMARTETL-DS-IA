package estructuras;

public class Cola<T> {
    private Nodo<T> frente, fin;
    private int n;

    public void encolar(T d) {
        Nodo<T> x = new Nodo<>(d);
        if (fin == null)
            frente = fin = x;
        else {
            fin.siguiente = x;
            fin = x;
        }
        n++;
    }

    public T desencolar() {
        if (frente == null)
            return null;
        T d = frente.dato;
        frente = frente.siguiente;
        if (frente == null)
            fin = null;
        n--;
        return d;
    }

    public T frente() {
        return frente == null ? null : frente.dato;
    }

    public T finalDato() {
        return fin == null ? null : fin.dato;
    }

    public boolean estaVacia() {
        return frente == null;
    }

    public int tamanio() {
        return n;
    }

    public void mostrar() {
        for (Nodo<T> a = frente; a != null; a = a.siguiente)
            System.out.println(a.dato);
    }
}
