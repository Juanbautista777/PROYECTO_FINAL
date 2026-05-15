package PROYECTO_FINAL.estructuras;

public class ListaEnlazada<T> {
    private Nodo<T> cabeza;
    private int tamaño;

    private static class Nodo<T> {
        T dato;
        Nodo<T> siguiente;
        Nodo(T dato) { this.dato = dato; }
    }

    public void agregar(T dato) {
        Nodo<T> nuevoNodo = new Nodo<>(dato);
        if (cabeza == null) {
            cabeza = nuevoNodo;
        } else {
            Nodo<T> temp = cabeza;
            while (temp.siguiente != null) {
                temp = temp.siguiente;
            }
            temp.siguiente = nuevoNodo;
        }
        tamaño++;
    }

    public boolean contiene(T dato) {
        Nodo<T> temp = cabeza;
        while (temp != null) {
            if (temp.dato.equals(dato)) return true;
            temp = temp.siguiente;
        }
        return false;
    }
/**
 * Devuelve el dato almacenado en la posición indicada.
 * Es fundamental para poder recorrer la lista en los servicios.
 */
public T obtener(int indice) {
    if (indice < 0 || indice >= tamaño) {
        throw new IndexOutOfBoundsException("Índice fuera de rango");
    }

    Nodo<T> temp = cabeza;
    for (int i = 0; i < indice; i++) {
        temp = temp.siguiente;
    }
    return temp.dato;
}
    public boolean eliminar(T dato) {
        if (cabeza == null) return false;
        if (cabeza.dato.equals(dato)) {
            cabeza = cabeza.siguiente;
            tamaño--;
            return true;
        }
        Nodo<T> temp = cabeza;
        while (temp.siguiente != null) {
            if (temp.siguiente.dato.equals(dato)) {
                temp.siguiente = temp.siguiente.siguiente;
                tamaño--;
                return true;
            }
            temp = temp.siguiente;
        }
        return false;
    }

    public int getTamaño() { return tamaño; }
    
    public boolean estaVacia() { return cabeza == null; }
}