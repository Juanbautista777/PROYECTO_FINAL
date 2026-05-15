package PROYECTO_FINAL.excepciones;

/**
 * Se dispara cuando se realiza una búsqueda o una operación (como inscripción 
 * o reporte) sobre un ID de estudiante que no existe en el sistema.
 */
public class EstudianteNoEncontradoException extends Exception {

    // Constructor que recibe un mensaje personalizado
    public EstudianteNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}