package PROYECTO_FINAL.Servicios;

import PROYECTO_FINAL.Modelos.*;
import PROYECTO_FINAL.excepciones.*;
import PROYECTO_FINAL.estructuras.ListaEnlazada;
import PROYECTO_FINAL.estructuras.PilaOperaciones;

import java.util.HashMap;

/**
 * Servicio central del sistema académico.
 *
 * Estructuras usadas:
 *   - HashMap<String, Estudiante> : índice rápido de estudiantes por ID
 *   - HashMap<String, Materia>    : índice rápido de materias por código
 *   - PilaOperaciones (manual)    : historial para Deshacer
 *   - PilaOperaciones (manual)    : historial para Rehacer
 */
public class InscripcionManager {

    // Búsquedas O(1) por ID / código
    private HashMap<String, Estudiante> estudiantes;
    private HashMap<String, Materia>    materias;

    // Pilas de deshacer y rehacer (implementación propia)
    private PilaOperaciones<String> pilaDeshacer;
    private PilaOperaciones<String> pilaRehacer;

    // ── Constructor ──────────────────────────────────────────────────────────

    public InscripcionManager() {
        this.estudiantes   = new HashMap<>();
        this.materias      = new HashMap<>();
        this.pilaDeshacer  = new PilaOperaciones<>();
        this.pilaRehacer   = new PilaOperaciones<>();
    }

    // ── Gestión de Estudiantes ───────────────────────────────────────────────

    /**
     * Registra un nuevo estudiante en el sistema.
     * Si el ID ya existe, informa sin sobreescribir.
     */
    public void registrarEstudiante(Estudiante e) {
        if (estudiantes.containsKey(e.getId())) {
            System.out.println("Aviso: El ID " + e.getId() + " ya está registrado.");
            return;
        }
        estudiantes.put(e.getId(), e);
        System.out.println("Estudiante registrado exitosamente: " + e.getNombre());
    }

    /**
     * Busca un estudiante por su ID usando el HashMap.
     * @throws EstudianteNoEncontradoException si el ID no existe.
     */
    public Estudiante buscarEstudiante(String id) throws EstudianteNoEncontradoException {
        Estudiante est = estudiantes.get(id);
        if (est == null) {
            throw new EstudianteNoEncontradoException(
                "EstudianteNoEncontradoException - No existe estudiante con ID: " + id);
        }
        return est;
    }

    /** Lista todos los estudiantes registrados. */
    public void listarEstudiantes() {
        if (estudiantes.isEmpty()) {
            System.out.println("No hay estudiantes registrados.");
            return;
        }
        System.out.println("\n--- LISTA DE ESTUDIANTES ---");
        for (Estudiante e : estudiantes.values()) {
            e.mostrarInformacion();
        }
    }

    /**
     * Elimina un estudiante del sistema.
     * La operación queda registrada en la pilaDeshacer para poder revertirla.
     * @throws EstudianteNoEncontradoException si el ID no existe.
     */
    public void eliminarEstudiante(String id) throws EstudianteNoEncontradoException {
        Estudiante est = buscarEstudiante(id);   // lanza excepción si no existe
        estudiantes.remove(id);
        // Guardamos los datos del estudiante para poder rehacerlo
        pilaDeshacer.apilar("ELIMINAR_ESTUDIANTE:" + id + ":"
                            + est.getNombre() + ":" + est.getEmail()
                            + ":" + est.getSemestre());
        pilaRehacer  = new PilaOperaciones<>();  // limpiar rehacer al hacer nueva acción
        System.out.println("Estudiante " + est.getNombre() + " eliminado del sistema.");
    }

    // ── Gestión de Materias ──────────────────────────────────────────────────

    /** Registra una nueva materia en el sistema. */
    public void registrarMateria(Materia m) {
        if (materias.containsKey(m.getCodigo())) {
            System.out.println("Aviso: El código " + m.getCodigo() + " ya está registrado.");
            return;
        }
        materias.put(m.getCodigo(), m);
        System.out.println("Materia registrada exitosamente: " + m.getNombre());
    }

    /**
     * Busca una materia por su código.
     * @return la Materia encontrada o null si no existe.
     */
    public Materia buscarMateria(String codigo) {
        return materias.get(codigo);
    }

    /**
     * Agrega un prerequisito a una materia.
     * @throws EstudianteNoEncontradoException reutilizada como "MateriaNoEncontrada"
     *         (no hay MateriaNoEncontradaException en el PDF, así que usamos mensaje claro).
     */
    public void agregarPreRequisito(String codigoMateria, String codigoPreReq) {
        Materia mat = materias.get(codigoMateria);
        if (mat == null) {
            System.out.println("Error: Materia " + codigoMateria + " no encontrada.");
            return;
        }
        mat.agregarPreRequisitos(codigoPreReq);
        System.out.println("Prerequisito " + codigoPreReq + " agregado a " + mat.getNombre());
    }

    /** Muestra los prerequisitos de una materia usando la lista enlazada. */
    public void mostrarPreRequisitos(String codigoMateria) {
        Materia mat = materias.get(codigoMateria);
        if (mat == null) {
            System.out.println("Error: Materia no encontrada.");
            return;
        }
        ListaEnlazada<String> reqs = mat.getPreRequisitos();
        System.out.println("\n--- PREREQUISITOS DE " + mat.getNombre() + " ---");
        if (reqs.estaVacia()) {
            System.out.println("  Sin prerequisitos.");
        } else {
            for (int i = 0; i < reqs.getTamaño(); i++) {
                System.out.println("  " + (i + 1) + ". " + reqs.obtener(i));
            }
        }
    }

    /**
     * Muestra la cola de espera de una materia.
     * @throws ColaDeEsperaVaciaException si la cola está vacía.
     */
    public void mostrarColaEspera(String codigoMateria) throws ColaDeEsperaVaciaException {
        Materia mat = materias.get(codigoMateria);
        if (mat == null) {
            System.out.println("Error: Materia no encontrada.");
            return;
        }
        if (mat.getColaEspera().estaVacia()) {
            throw new ColaDeEsperaVaciaException(
                "ColaDeEsperaVaciaException - No hay estudiantes en espera para: "
                + mat.getNombre());
        }
        System.out.println("\n--- COLA DE ESPERA: " + mat.getNombre() + " ---");
        System.out.println("Total en espera: " + mat.getColaEspera().getTamaño());
        // Mostramos sin destruir la cola
        System.out.println("(Use la opcion 9 para liberar cupos y promover estudiantes)");
    }

    // ── Inscripción ──────────────────────────────────────────────────────────

    /**
     * Inscribe un estudiante en una materia con todas las validaciones:
     *  1. Existencia del estudiante y la materia
     *  2. Prerequisitos aprobados (historial)
     *  3. Disponibilidad de cupos
     *
     * La operación se guarda en pilaDeshacer.
     */
    public void inscribirEstudianteEnMateria(String idEstudiante, String codigoMateria)
            throws EstudianteNoEncontradoException,
                   CupoLlenoException,
                   PreRequisitoNoAprobadoException {

        Estudiante est = buscarEstudiante(idEstudiante);

        Materia mat = materias.get(codigoMateria);
        if (mat == null) {
            System.out.println("Error: Materia " + codigoMateria + " no encontrada.");
            return;
        }

        // Validar prerequisitos usando la lista enlazada
        ListaEnlazada<String> requisitos = mat.getPreRequisitos();
        for (int i = 0; i < requisitos.getTamaño(); i++) {
            String codigoPre = requisitos.obtener(i);
            if (!est.getHistorialMaterias().contiene(codigoPre)) {
                throw new PreRequisitoNoAprobadoException(
                    "PreRequisitoNoAprobadoException - Falta aprobar: " + codigoPre);
            }
        }

        // Validar cupo
        if (mat.hayCupo()) {
            mat.inscribirEstudiante(idEstudiante);
            pilaDeshacer.apilar("INSCRIPCION:" + idEstudiante + ":" + codigoMateria);
            pilaRehacer = new PilaOperaciones<>();
            System.out.println("Inscripcion exitosa: " + est.getNombre()
                               + " en " + mat.getNombre()
                               + " (Cupos restantes: " + (mat.getCuposMaximos() - mat.getCuposOcupados()) + ")");
        } else {
            mat.agregarAColaEspera(idEstudiante);
            throw new CupoLlenoException(
                "CupoLlenoException - Materia llena. " + est.getNombre()
                + " agregado a COLA DE ESPERA.");
        }
    }

    /**
     * Cancela la inscripción de un estudiante.
     * Promueve automáticamente al primero de la cola de espera.
     */
    public void cancelarInscripcion(String idEstudiante, String codigoMateria)
            throws EstudianteNoEncontradoException {

        buscarEstudiante(idEstudiante);  // valida existencia

        Materia mat = materias.get(codigoMateria);
        if (mat == null) {
            System.out.println("Error: Materia no encontrada.");
            return;
        }

        if (!mat.estaInscrito(idEstudiante)) {
            System.out.println("El estudiante no está inscrito en esa materia.");
            return;
        }

        String promovido = mat.cancelarInscripcion(idEstudiante);
        pilaDeshacer.apilar("CANCELACION:" + idEstudiante + ":" + codigoMateria);
        pilaRehacer = new PilaOperaciones<>();

        System.out.println("Cancelacion exitosa. Cupo liberado.");
        if (promovido != null) {
            Estudiante estPromovido = estudiantes.get(promovido);
            String nombre = (estPromovido != null) ? estPromovido.getNombre() : promovido;
            System.out.println("Asignando cupo a " + nombre + " (primer estudiante en cola).");
        }
    }

    // ── Notas ────────────────────────────────────────────────────────────────

    /**
     * Registra una nota en el arreglo Double[10][20] del estudiante.
     * La operación se guarda en pilaDeshacer.
     */
    public void registrarNota(String idEstudiante, int semestre,
                              String codigoMateria, double nota)
            throws EstudianteNoEncontradoException {

        Estudiante est = buscarEstudiante(idEstudiante);
        boolean ok = est.registrarNota(semestre, codigoMateria, nota);
        if (ok) {
            pilaDeshacer.apilar("NOTA:" + idEstudiante + ":" + semestre
                                + ":" + codigoMateria + ":" + nota);
            pilaRehacer = new PilaOperaciones<>();
            System.out.println("Nota " + nota + " registrada para " + codigoMateria
                               + " (Semestre " + semestre + ").");
        } else {
            System.out.println("Error: Semestre fuera de rango o semestre lleno (max 20 materias).");
        }
    }

    // ── Deshacer / Rehacer ───────────────────────────────────────────────────

    /**
     * Deshace la última operación registrada en pilaDeshacer y la mueve a pilaRehacer.
     * Operaciones deshacibles: INSCRIPCION, CANCELACION, NOTA, ELIMINAR_ESTUDIANTE.
     *
     * @throws PilaDeshacerVaciaException si no hay operaciones que deshacer.
     */
    public void deshacerUltimaOperacion() throws PilaDeshacerVaciaException {
        if (pilaDeshacer.estaVacia()) {
            throw new PilaDeshacerVaciaException(
                "PilaDeshacerVaciaException - No hay operaciones para deshacer.");
        }

        String ultima = pilaDeshacer.desapilar();
        String[] partes = ultima.split(":");

        switch (partes[0]) {

            case "INSCRIPCION": {
                // Revertir: sacar al estudiante de la materia
                String idEst  = partes[1];
                String codMat = partes[2];
                Materia mat = materias.get(codMat);
                if (mat != null) {
                    mat.eliminarEstudiante(idEst);
                    System.out.println("Deshacer - " + idEst
                                       + " ya NO esta inscrito en " + codMat);
                }
                break;
            }

            case "CANCELACION": {
                // Revertir: volver a inscribir al estudiante
                String idEst  = partes[1];
                String codMat = partes[2];
                Materia mat = materias.get(codMat);
                if (mat != null && mat.hayCupo()) {
                    mat.inscribirEstudiante(idEst);
                    System.out.println("Deshacer - " + idEst
                                       + " vuelve a estar inscrito en " + codMat);
                }
                break;
            }

            case "NOTA": {
                // Revertir: poner la nota a -1 (centinela de "sin nota")
                String idEst   = partes[1];
                int semestre   = Integer.parseInt(partes[2]);
                String codMat  = partes[3];
                Estudiante est = estudiantes.get(idEst);
                if (est != null) {
                    est.revertirNota(semestre, codMat);
                    System.out.println("Deshacer - nota de " + codMat
                                       + " en semestre " + semestre + " eliminada.");
                }
                break;
            }

            case "ELIMINAR_ESTUDIANTE": {
                // Revertir: restaurar el estudiante con sus datos guardados
                String id      = partes[1];
                String nombre  = partes[2];
                String email   = partes[3];
                int semestre   = Integer.parseInt(partes[4]);
                Estudiante restaurado = new Estudiante(nombre, id, email, semestre);
                estudiantes.put(id, restaurado);
                System.out.println("Deshacer - Estudiante " + nombre + " restaurado.");
                break;
            }

            default:
                System.out.println("Deshacer: tipo de operacion desconocido.");
        }

        // Mover a pilaRehacer para poder rehacerla
        pilaRehacer.apilar(ultima);
    }

    /**
     * Rehace la última operación deshecha, tomándola de pilaRehacer.
     *
     * @throws PilaDeshacerVaciaException reutilizada para "no hay nada que rehacer".
     */
    public void rehacerUltimaOperacion() throws PilaDeshacerVaciaException {
        if (pilaRehacer.estaVacia()) {
            throw new PilaDeshacerVaciaException(
                "PilaDeshacerVaciaException - No hay operaciones para rehacer.");
        }

        String accion = pilaRehacer.desapilar();
        String[] partes = accion.split(":");

        switch (partes[0]) {

            case "INSCRIPCION": {
                String idEst  = partes[1];
                String codMat = partes[2];
                Materia mat = materias.get(codMat);
                if (mat != null && mat.hayCupo()) {
                    mat.inscribirEstudiante(idEst);
                    System.out.println("Rehacer - " + idEst
                                       + " inscrito nuevamente en " + codMat);
                }
                break;
            }

            case "CANCELACION": {
                String idEst  = partes[1];
                String codMat = partes[2];
                Materia mat = materias.get(codMat);
                if (mat != null) {
                    mat.eliminarEstudiante(idEst);
                    System.out.println("Rehacer - cancelacion de " + idEst
                                       + " en " + codMat + " reejecutada.");
                }
                break;
            }

            case "NOTA": {
                String idEst  = partes[1];
                int semestre  = Integer.parseInt(partes[2]);
                String codMat = partes[3];
                double nota   = Double.parseDouble(partes[4]);
                Estudiante est = estudiantes.get(idEst);
                if (est != null) {
                    est.registrarNota(semestre, codMat, nota);
                    System.out.println("Rehacer - nota " + nota + " de " + codMat + " restaurada.");
                }
                break;
            }

            case "ELIMINAR_ESTUDIANTE": {
                String id = partes[1];
                estudiantes.remove(id);
                System.out.println("Rehacer - Estudiante " + id + " eliminado nuevamente.");
                break;
            }

            default:
                System.out.println("Rehacer: tipo de operacion desconocido.");
        }

        // Devolver a pilaDeshacer para que pueda volver a deshacerse
        pilaDeshacer.apilar(accion);
    }
}