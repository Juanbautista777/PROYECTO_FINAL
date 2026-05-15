package PROYECTO_FINAL.Modelos;

import PROYECTO_FINAL.estructuras.ListaEnlazada;

/**
 * Representa a un alumno en el sistema.
 * Hereda de Persona y gestiona el rendimiento académico
 * mediante matrices de notas y un historial enlazado.
 *
 * Estructuras usadas (arreglos nativos obligatorios):
 *   - double[10][20]  : notas por semestre
 *   - String[10][20]  : códigos de materia correspondientes
 *   - int[10]         : conteo de materias por semestre
 *   - ListaEnlazada   : historial de materias cursadas (estructura propia)
 */
public class Estudiante extends Persona {

    private int semestre;

    /** Matriz de calificaciones: [10 semestres][20 materias por semestre] */
    private double[][] notas;

    /** Matriz de códigos: nombre de cada materia en la posición de su nota */
    private String[][] nombresMaterias;

    /** Cuántas materias lleva registradas en cada semestre */
    private int[] conteoMaterias;

    /** Historial cronológico de materias cursadas (lista enlazada propia) */
    private ListaEnlazada<String> historialMaterias;

    // ── Constructor ──────────────────────────────────────────────────────────

    public Estudiante(String nombre, String id, String email, int semestre) {
        super(nombre, id, email);
        this.semestre         = semestre;
        this.notas            = new double[10][20];
        this.nombresMaterias  = new String[10][20];
        this.conteoMaterias   = new int[10];
        this.historialMaterias = new ListaEnlazada<>();

        // Inicialización con -1 (valor centinela = "sin nota")
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 20; j++) {
                notas[i][j] = -1;
            }
        }
    }

    // ── Getters y Setters ────────────────────────────────────────────────────

    public int       getSemestre()          { return semestre; }
    public double[][] getNotas()            { return notas; }
    public String[][] getNombresMaterias()  { return nombresMaterias; }
    public int[]     getConteoMaterias()    { return conteoMaterias; }
    public ListaEnlazada<String> getHistorialMaterias() { return historialMaterias; }
    public void setSemestre(int semestre)   { this.semestre = semestre; }

    // ── Lógica de notas ──────────────────────────────────────────────────────

    /**
     * Registra una nota en el arreglo bidimensional y actualiza el historial.
     * @param numSemestre  Número de semestre (1–10)
     * @param codigoMateria Código de la materia
     * @param nota         Calificación (0.0–5.0)
     * @return true si se registró correctamente; false si los límites se superaron.
     */
    public boolean registrarNota(int numSemestre, String codigoMateria, double nota) {
        int idx = numSemestre - 1;
        if (idx < 0 || idx >= 10 || conteoMaterias[idx] >= 20) return false;

        int posicion = conteoMaterias[idx];
        notas[idx][posicion]           = nota;
        nombresMaterias[idx][posicion] = codigoMateria;
        conteoMaterias[idx]++;

        // Agrega al historial solo si no estaba ya (para validar prerequisitos)
        if (!historialMaterias.contiene(codigoMateria)) {
            historialMaterias.agregar(codigoMateria);
        }
        return true;
    }

    /**
     * Revierte la última nota registrada para una materia en un semestre dado.
     * Usado por el sistema de Deshacer.
     *
     * @param numSemestre  Número de semestre (1–10)
     * @param codigoMateria Código de la materia a revertir
     */
    public void revertirNota(int numSemestre, String codigoMateria) {
        int idx = numSemestre - 1;
        if (idx < 0 || idx >= 10) return;

        // Buscar la posición de la materia en ese semestre
        for (int j = 0; j < conteoMaterias[idx]; j++) {
            if (codigoMateria.equals(nombresMaterias[idx][j])) {
                // Poner centinela y comprimir el arreglo
                for (int k = j; k < conteoMaterias[idx] - 1; k++) {
                    notas[idx][k]           = notas[idx][k + 1];
                    nombresMaterias[idx][k] = nombresMaterias[idx][k + 1];
                }
                conteoMaterias[idx]--;
                notas[idx][conteoMaterias[idx]]           = -1;
                nombresMaterias[idx][conteoMaterias[idx]] = null;
                // También quitamos del historial si ya no hay nota en ningún semestre
                historialMaterias.eliminar(codigoMateria);
                return;
            }
        }
    }

    // ── Polimorfismo ─────────────────────────────────────────────────────────

    @Override
    public void mostrarInformacion() {
        System.out.println("ESTUDIANTE: " + getNombre()
                           + " | ID: " + getId()
                           + " | Email: " + getEmail()
                           + " | Semestre: " + semestre);
    }
}