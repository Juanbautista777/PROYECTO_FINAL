package PROYECTO_FINAL.Modelos;

public class Quiz extends Evaluacion {
    private String tema;

    public Quiz(String nombre, double porcentaje, String tema) {
        super(nombre, porcentaje);
        this.tema = tema;
    }

    @Override
    public void realizarEvaluacion() {
        System.out.println("Realizando Quiz sobre: " + tema);
    }
}