package PROYECTO_FINAL.excepciones;

/**
 * Se activa cuando el usuario intenta realizar la acción "Deshacer", pero 
 * el historial de operaciones está vacío y no hay nada que revertir.
 */
public class PilaDeshacerVaciaException extends Exception {

    // Constructor que recibe un mensaje personalizado
    public PilaDeshacerVaciaException(String mensaje) {
        super(mensaje);
    }
}