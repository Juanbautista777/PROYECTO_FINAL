package PROYECTO_FINAL.Modelos;

public class ExamenFinal extends Evaluacion {
    public ExamenFinal(String nombre, double porcentaje) {
        super(nombre, porcentaje);
    }

    @Override
    public void realizarEvaluacion() {
        System.out.println("Presentando Examen Final de semestre.");
    }
}