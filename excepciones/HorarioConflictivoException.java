package PROYECTO_FINAL.excepciones;

/**
 * Indica que se ha intentado reservar un aula en un día y hora que ya 
 * se encuentran ocupados por otra actividad académica.
 */
public class HorarioConflictivoException extends Exception {

    // Constructor que recibe un mensaje personalizado
    public HorarioConflictivoException(String mensaje) {
        super(mensaje);
    }
}