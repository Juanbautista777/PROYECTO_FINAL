package PROYECTO_FINAL.Modelos;

import PROYECTO_FINAL.estructuras.Cola;
import PROYECTO_FINAL.estructuras.ListaEnlazada;
import java.util.TreeMap;

/**
 * Representa una asignatura dentro del sistema académico.
 * Gestiona cupos, prerequisitos, lista de espera y el docente asignado.
 *
 * Estructuras usadas:
 *   - ListaEnlazada (manual): prerequisitos e inscritos
 *   - Cola (manual): lista de espera
 *   - TreeMap (Java): aulas asignadas ordenadas por nombre
 */
public class Materia {

    private String codigo;
    private String nombre;
    private int cuposMaximos;
    private int cuposOcupados;
    private int creditos;
    private Profesor docente;

    // Estructuras manuales
    private ListaEnlazada<String> preRequisitos;
    private ListaEnlazada<String> estudiantesInscritos;
    private Cola<String> colaEspera;

    // TreeMap obligatorio: aulas asignadas ordenadas alfabéticamente por nombre
    private TreeMap<String, Aula> aulasAsignadas;

    public Materia(String codigo, String nombre, int cuposMaximos, int creditos) {
        this.codigo       = codigo;
        this.nombre       = nombre;
        this.cuposMaximos = cuposMaximos;
        this.cuposOcupados = 0;
        this.creditos     = creditos;
        this.preRequisitos        = new ListaEnlazada<>();
        this.estudiantesInscritos = new ListaEnlazada<>();
        this.colaEspera           = new Cola<>();
        this.aulasAsignadas       = new TreeMap<>();
    }

    // ── Getters y Setters ────────────────────────────────────────────────────

    public String getCodigo()      { return codigo; }
    public String getNombre()      { return nombre; }
    public int getCuposMaximos()   { return cuposMaximos; }
    public int getCuposOcupados()  { return cuposOcupados; }
    public int getCreditos()       { return creditos; }
    public Profesor getDocente()   { return docente; }
    public void setDocente(Profesor docente) { this.docente = docente; }

    public ListaEnlazada<String> getPreRequisitos()       { return preRequisitos; }
    public ListaEnlazada<String> getEstudiantesInscritos(){ return estudiantesInscritos; }
    public Cola<String>          getColaEspera()          { return colaEspera; }
    public TreeMap<String, Aula> getAulasAsignadas()      { return aulasAsignadas; }

    // ── Prerequisitos ────────────────────────────────────────────────────────

    /** Agrega un prerequisito si todavía no existe en la lista. */
    public void agregarPreRequisitos(String codigoMateria) {
        if (!preRequisitos.contiene(codigoMateria)) {
            preRequisitos.agregar(codigoMateria);
        }
    }

    // ── Control de cupos ─────────────────────────────────────────────────────

    public boolean hayCupo() {
        return cuposOcupados < cuposMaximos;
    }

    /** Inscribe directamente al estudiante (ya se validó que hay cupo). */
    public void inscribirEstudiante(String idEstudiante) {
        estudiantesInscritos.agregar(idEstudiante);
        cuposOcupados++;
    }

    public void agregarAColaEspera(String idEstudiante) {
        colaEspera.encolar(idEstudiante);
    }

    /**
     * Cancela la inscripción y asigna el cupo al primero de la cola (si existe).
     * @return ID del estudiante promovido desde la cola, o null si la cola estaba vacía.
     */
    public String cancelarInscripcion(String idEstudiante) {
        boolean removido = estudiantesInscritos.eliminar(idEstudiante);
        if (!removido) return null;

        cuposOcupados--;

        if (!colaEspera.estaVacia()) {
            String siguiente = colaEspera.desencolar();
            estudiantesInscritos.agregar(siguiente);
            cuposOcupados++;
            return siguiente;
        }
        return null;
    }

    /**
     * Elimina al estudiante de la lista de inscritos sin activar la cola.
     * Usado exclusivamente por el sistema de deshacer para revertir una inscripción.
     */
    public void eliminarEstudiante(String idEstudiante) {
        boolean removido = estudiantesInscritos.eliminar(idEstudiante);
        if (removido) {
            cuposOcupados--;
        }
    }

    public boolean estaInscrito(String idEstudiante) {
        return estudiantesInscritos.contiene(idEstudiante);
    }

    // ── Aulas (TreeMap) ──────────────────────────────────────────────────────

    /** Asigna un aula a esta materia. El TreeMap la ordena por nombre automáticamente. */
    public void asignarAula(Aula aula) {
        aulasAsignadas.put(aula.getNombre(), aula);
        System.out.println("Aula " + aula.getNombre() + " asignada a " + nombre);
    }

    /** Muestra las aulas asignadas en orden alfabético (aprovecha el TreeMap). */
    public void mostrarAulas() {
        if (aulasAsignadas.isEmpty()) {
            System.out.println("  Sin aulas asignadas.");
            return;
        }
        for (Aula a : aulasAsignadas.values()) {
            System.out.println("  " + a);
        }
    }

    @Override
    public String toString() {
        String infoDocente = (docente != null) ? docente.getNombre() : "Sin asignar";
        return codigo + " - " + nombre
               + " | Docente: " + infoDocente
               + " (Cupos: " + cuposOcupados + "/" + cuposMaximos + ")"
               + " | Creditos: " + creditos;
    }
}