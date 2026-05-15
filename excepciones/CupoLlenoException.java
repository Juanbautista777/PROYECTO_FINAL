package PROYECTO_FINAL.excepciones;

/**
 * Excepción lanzada cuando una materia ha alcanzado su límite máximo de
 * estudiantes inscritos y no es posible agregar más registros directos.
 */
public class CupoLlenoException extends Exception {

    public CupoLlenoException(String mensaje) {
        super(mensaje);
    }
}