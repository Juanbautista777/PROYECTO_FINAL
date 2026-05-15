package PROYECTO_FINAL.Modelos;

/**
 * Representa una facultad dentro de la universidad.
 * El sistema maneja exactamente 5 facultades fijas almacenadas
 * en un arreglo estático nativo (requerimiento del proyecto).
 */
public class Facultad {

    private String nombre;
    private String codigo;

    public Facultad(String nombre, String codigo) {
        this.nombre = nombre;
        this.codigo = codigo;
    }

    // ── Arreglo estático de 5 facultades (arreglo nativo obligatorio) ────────
    public static final Facultad[] FACULTADES = {
        new Facultad("Ingenieria de Sistemas",     "FSIS"),
        new Facultad("Ingenieria Civil",            "FCIV"),
        new Facultad("Administracion de Empresas",  "FADM"),
        new Facultad("Ciencias Basicas",            "FCBA"),
        new Facultad("Derecho",                     "FDER")
    };

    // ── Getters ──────────────────────────────────────────────────────────────

    public String getNombre() { return nombre; }
    public String getCodigo() { return codigo; }

    /** Muestra por consola todas las facultades del arreglo estático. */
    public static void listarFacultades() {
        System.out.println("\n--- FACULTADES REGISTRADAS ---");
        for (int i = 0; i < FACULTADES.length; i++) {
            System.out.println("  [" + i + "] " + FACULTADES[i].getCodigo()
                               + " - " + FACULTADES[i].getNombre());
        }
    }

    @Override
    public String toString() {
        return codigo + " - " + nombre;
    }
}