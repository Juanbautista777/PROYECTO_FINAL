package PROYECTO_FINAL.excepciones;

/**
 * Excepción de validación académica: el estudiante no ha ganado la materia 
 * necesaria (pre-requisito) para poder inscribirse en la asignatura solicitada.
 */
public class PreRequisitoNoAprobadoException extends Exception {

    // Constructor que recibe un mensaje personalizado
    public PreRequisitoNoAprobadoException(String mensaje) {
        super(mensaje);
    }
}