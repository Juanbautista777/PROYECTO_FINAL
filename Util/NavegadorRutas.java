package PROYECTO_FINAL.Util;

/**
 * Gestiona la navegación física en el campus usando un grafo ponderado.
 *
 * Estructura usada (arreglo nativo obligatorio):
 *   - int[N][N] matrizAdyacencia : distancias entre edificios (grafo no dirigido)
 *   - String[N] nombresEdificios : nombres indexados de cada vértice
 *
 * Algoritmo: Dijkstra clásico con arreglos de distancias y visitados.
 */
public class NavegadorRutas {

    private int[][] matrizAdyacencia;
    private String[] nombresEdificios;
    private int cantidadActual;
    private int capacidadMax;

    // Sentinel: sin conexión directa entre dos nodos
    private static final int SIN_CONEXION = -1;
    // Sentinel: distancia infinita (Dijkstra)
    private static final int INFINITO = Integer.MAX_VALUE / 2;

    // ── Constructor ──────────────────────────────────────────────────────────

    public NavegadorRutas(int capacidadMax) {
        this.capacidadMax      = capacidadMax;
        this.matrizAdyacencia  = new int[capacidadMax][capacidadMax];
        this.nombresEdificios  = new String[capacidadMax];
        this.cantidadActual    = 0;

        // Diagonal = 0 (un edificio a sí mismo); resto = -1 (sin conexión)
        for (int i = 0; i < capacidadMax; i++) {
            for (int j = 0; j < capacidadMax; j++) {
                matrizAdyacencia[i][j] = (i == j) ? 0 : SIN_CONEXION;
            }
        }
    }

    // ── Gestión de edificios ─────────────────────────────────────────────────

    /** Registra un nuevo nodo (edificio) en el grafo. */
    public void registrarEdificioEnGrafo(String nombre) {
        if (cantidadActual >= capacidadMax) {
            System.out.println("Error: capacidad maxima de edificios alcanzada.");
            return;
        }
        nombresEdificios[cantidadActual] = nombre;
        cantidadActual++;
        System.out.println("Edificio registrado: " + nombre
                           + " (indice " + (cantidadActual - 1) + ")");
    }

    /** Muestra todos los edificios registrados con su índice. */
    public void listarEdificios() {
        System.out.println("\n--- EDIFICIOS REGISTRADOS ---");
        for (int i = 0; i < cantidadActual; i++) {
            System.out.println("  " + i + ": " + nombresEdificios[i]);
        }
    }

    // ── Gestión de conexiones ────────────────────────────────────────────────

    /**
     * Agrega una arista bidireccional (grafo no dirigido) con su peso en metros.
     * Opción 14 del menú.
     */
    public void agregarConexion(String ed1, String ed2, int distancia) {
        int idx1 = obtenerIndice(ed1);
        int idx2 = obtenerIndice(ed2);

        if (idx1 == -1 || idx2 == -1) {
            System.out.println("Error: Uno o ambos edificios no estan registrados.");
            return;
        }
        matrizAdyacencia[idx1][idx2] = distancia;
        matrizAdyacencia[idx2][idx1] = distancia;
        System.out.println("Conexion establecida: " + ed1 + " <-> " + ed2
                           + " (" + distancia + "m)");
    }

    // ── Dijkstra ─────────────────────────────────────────────────────────────

    /**
     * Calcula la ruta más corta entre dos edificios usando el algoritmo de Dijkstra.
     * Muestra el camino completo y la distancia total en metros.
     * Opción 15 del menú.
     */
    public void calcularRutaMasCorta(String origen, String destino) {
        int src = obtenerIndice(origen);
        int dst = obtenerIndice(destino);

        if (src == -1 || dst == -1) {
            System.out.println("Error: Edificio(s) no encontrados. Use la opcion 14 primero.");
            return;
        }

        int n = cantidadActual;

        // Arreglo de distancias mínimas desde src a cada vértice
        int[]     dist     = new int[n];
        // Arreglo de visitados
        boolean[] visitado = new boolean[n];
        // Arreglo de predecesores para reconstruir el camino
        int[]     anterior = new int[n];

        // Inicialización
        for (int i = 0; i < n; i++) {
            dist[i]     = INFINITO;
            visitado[i] = false;
            anterior[i] = -1;
        }
        dist[src] = 0;

        // Bucle principal de Dijkstra: n iteraciones
        for (int iter = 0; iter < n; iter++) {

            // Seleccionar el vértice no visitado con menor distancia acumulada
            int u = -1;
            for (int i = 0; i < n; i++) {
                if (!visitado[i] && (u == -1 || dist[i] < dist[u])) {
                    u = i;
                }
            }

            if (u == -1 || dist[u] == INFINITO) break; // Nodos restantes inaccesibles
            visitado[u] = true;

            // Relajar aristas del vértice u
            for (int v = 0; v < n; v++) {
                int peso = matrizAdyacencia[u][v];
                if (!visitado[v] && peso != SIN_CONEXION && peso > 0) {
                    int nuevaDist = dist[u] + peso;
                    if (nuevaDist < dist[v]) {
                        dist[v]     = nuevaDist;
                        anterior[v] = u;
                    }
                }
            }
        }

        // ── Mostrar resultado ────────────────────────────────────────────────
        System.out.println("\n--- RESULTADO DIJKSTRA ---");
        if (dist[dst] == INFINITO) {
            System.out.println("No existe ruta entre " + origen + " y " + destino + ".");
            return;
        }

        // Reconstruir el camino recorriendo los predecesores al revés
        // Usamos un arreglo temporal (sin colecciones de Java)
        int[] camino = new int[n];
        int   largo  = 0;
        int   actual = dst;
        while (actual != -1) {
            camino[largo] = actual;
            largo++;
            actual = anterior[actual];
        }

        // Invertir el arreglo del camino (estaba de destino a origen)
        System.out.print("Ruta mas corta:\n  ");
        for (int i = largo - 1; i >= 0; i--) {
            System.out.print(nombresEdificios[camino[i]]);
            if (i > 0) {
                int desde = camino[i];
                int hacia = camino[i - 1];
                System.out.print(" -> " + nombresEdificios[hacia]
                                 + " (" + matrizAdyacencia[desde][hacia] + "m)");
                if (i - 1 > 0) System.out.print(" -> ");
            }
        }
        System.out.println("\nDistancia TOTAL: " + dist[dst] + " metros");
    }

    // ── Utilidades ───────────────────────────────────────────────────────────

    private int obtenerIndice(String nombre) {
        for (int i = 0; i < cantidadActual; i++) {
            if (nombresEdificios[i].equalsIgnoreCase(nombre)) return i;
        }
        return -1;
    }

    /**
     * Devuelve la ruta absoluta para un archivo en el directorio de datos.
     * Usado por LectorCSV y el Main.
     */
    public static String getRutaData(String nombreArchivo) {
        return System.getProperty("user.dir") + "/" + nombreArchivo;
    }
}