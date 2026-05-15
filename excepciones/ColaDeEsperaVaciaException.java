package PROYECTO_FINAL.excepciones;

/**
 * Excepción lanzada al intentar procesar un cupo liberado pero la estructura 
 * de cola de espera de la materia no contiene ningún estudiante.
 */
public class ColaDeEsperaVaciaException extends Exception {

    // Constructor que recibe un mensaje personalizado
    public ColaDeEsperaVaciaException(String mensaje) {
        super(mensaje);
    }
}