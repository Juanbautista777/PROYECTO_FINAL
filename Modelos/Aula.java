package PROYECTO_FINAL.Modelos;

import PROYECTO_FINAL.excepciones.HorarioConflictivoException;

/**
 * Representa un espacio físico o aula dentro del sistema.
 * Gestiona el control de aforo y la asignación del espacio mediante una
 * matriz de disponibilidad temporal.
 *
 * Estructura usada (arreglo nativo obligatorio):
 *   - boolean[7][24] horario : disponibilidad por día y hora
 */
public class Aula {

    private String nombre;
    private int capacidad;

    /**
     * Matriz de disponibilidad: [7 días][24 horas].
     * true  = ocupado
     * false = libre
     */
    private boolean[][] horario;

    public Aula(String nombre, int capacidad) {
        this.nombre    = nombre;
        this.capacidad = capacidad;
        this.horario   = new boolean[7][24];
    }

    // ── Getters ──────────────────────────────────────────────────────────────

    public String getNombre()      { return nombre; }
    public int getCapacidad()      { return capacidad; }
    public boolean[][] getHorario(){ return horario; }

    // ── Utilidad interna ─────────────────────────────────────────────────────

    private String nombreDia(int dia) {
        String[] dias = {"Domingo","Lunes","Martes","Miercoles",
                         "Jueves","Viernes","Sabado"};
        return dias[dia];
    }

    // ── Métodos requeridos por el PDF ────────────────────────────────────────

    /**
     * Evalúa si un bloque horario está libre.
     * @return true si la hora está libre; false si está ocupada o fuera de rango.
     */
    public boolean consultarDisponibilidad(int dia, int hora) {
        if (dia < 0 || dia > 6 || hora < 0 || hora > 23) return false;
        return !horario[dia][hora];
    }

    /**
     * Reserva un bloque continuo de horas en un día.
     * Valida todo el rango antes de modificar cualquier celda.
     *
     * @throws HorarioConflictivoException si alguna hora del bloque ya está ocupada.
     */
    public void reservar(int dia, int hora, int duracion)
            throws HorarioConflictivoException {

        // Fase 1: validar sin modificar nada
        for (int h = hora; h < hora + duracion && h < 24; h++) {
            if (horario[dia][h]) {
                throw new HorarioConflictivoException(
                    "HorarioConflictivoException - " + nombreDia(dia)
                    + " " + h + ":00 ya esta reservado");
            }
        }

        // Fase 2: marcar como ocupado
        for (int h = hora; h < hora + duracion && h < 24; h++) {
            horario[dia][h] = true;
            System.out.println("  " + nombreDia(dia) + " " + h + ":00 -> LIBRE (reservando)");
        }
        System.out.println("Reserva exitosa.");
    }

    /**
     * Libera un bloque continuo de horas, marcándolas como disponibles.
     */
    public void liberar(int dia, int hora, int duracion) {
        for (int h = hora; h < hora + duracion && h < 24; h++) {
            horario[dia][h] = false;
        }
        System.out.println("Horario liberado correctamente.");
    }

    @Override
    public String toString() {
        return nombre + " (Capacidad: " + capacidad + ")";
    }
}