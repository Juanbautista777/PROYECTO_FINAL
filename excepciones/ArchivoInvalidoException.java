package PROYECTO_FINAL.excepciones;

/**
 * Error técnico disparado durante el procesamiento batch si el archivo CSV 
 * tiene un formato incorrecto, está corrupto o no puede ser leído por el sistema.
 */
public class ArchivoInvalidoException extends Exception {

    // Constructor que recibe un mensaje personalizado
    public ArchivoInvalidoException(String mensaje) {
        super(mensaje);
    }
}