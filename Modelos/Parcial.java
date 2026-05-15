package PROYECTO_FINAL.Modelos;

public class Parcial extends Evaluacion {
    private int numeroParcial; // 1, 2 o 3

    public Parcial(String nombre, double porcentaje, int numeroParcial) {
        super(nombre, porcentaje);
        this.numeroParcial = numeroParcial;
    }

    @Override
    public void realizarEvaluacion() {
        System.out.println("Iniciando Parcial #" + numeroParcial);
    }
}