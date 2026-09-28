package estructuras;
public class ListaSecuencial<T>{private Object[] a=new Object[10];private int n;private void crecer(){Object[]b=new Object[a.length*2];System.arraycopy(a,0,b,0,n);a=b;}public void insertar(T d){if(n==a.length)crecer();a[n++]=d;}@SuppressWarnings("unchecked")public T obtener(int i){return (T)a[i];}public int tamanio(){return n;}public boolean estaVacia(){return n==0;}}
