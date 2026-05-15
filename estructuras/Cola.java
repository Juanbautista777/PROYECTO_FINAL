package PROYECTO_FINAL.estructuras;

/**
 * Estructura de datos FIFO (First In, First Out).
 * Implementación propia con nodos enlazados.
 * Usada para: cola de espera de materias y procesamiento batch.
 */
public class Cola<T> {

    private Nodo<T> frente;
    private Nodo<T> finalCola;
    private int tamaño;

    private static class Nodo<T> {
        T dato;
        Nodo<T> siguiente;
        Nodo(T dato) { this.dato = dato; }
    }

    /** Agrega un elemento al final de la cola. */
    public void encolar(T dato) {
        Nodo<T> nuevoNodo = new Nodo<>(dato);
        if (estaVacia()) {
            frente = nuevoNodo;
        } else {
            finalCola.siguiente = nuevoNodo;
        }
        finalCola = nuevoNodo;
        tamaño++;
    }

    /** Retira y devuelve el elemento del frente de la cola. */
    public T desencolar() {
        if (estaVacia()) return null;
        T dato = frente.dato;
        frente = frente.siguiente;
        if (frente == null) finalCola = null;
        tamaño--;
        return dato;
    }

    /** Consulta el frente sin retirarlo. */
    public T verFrente() {
        return estaVacia() ? null : frente.dato;
    }

    public boolean estaVacia() { return frente == null; }

    public int getTamaño() { return tamaño; }
}