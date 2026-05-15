package PROYECTO_FINAL.estructuras;

/**
 * Estructura de datos LIFO (Last In, First Out).
 * Implementación manual mediante nodos.
 */
public class Pila<T> {
    protected Nodo<T> cima;
    public int tamaño;

    private static class Nodo<T> {
        T dato;
        Nodo<T> siguiente;
        Nodo(T dato) { this.dato = dato; }
    }

    // Agrega un elemento a la cima de la pila
    public void apilar(T dato) {
        Nodo<T> nuevoNodo = new Nodo<>(dato);
        nuevoNodo.siguiente = cima;
        cima = nuevoNodo;
        tamaño++;
    }

    // Retira y devuelve el elemento de la cima
    public T desapilar() {
        if (estaVacia()) return null;
        T dato = cima.dato;
        cima = cima.siguiente;
        tamaño--;
        return dato;
    }

    // Solo mira lo que hay en la cima sin quitarlo
    public T verCima() {
        return estaVacia() ? null : cima.dato;
    }

    public boolean estaVacia() {
        return cima == null;
    }

    public int getTamaño() {
        return tamaño;
    }
}