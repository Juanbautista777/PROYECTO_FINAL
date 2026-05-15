package PROYECTO_FINAL.Modelos;

public abstract class Evaluacion {
    private String nombre;
    private double porcentaje; 
    private double nota;

    public Evaluacion(String nombre, double porcentaje) {
        this.nombre = nombre;
        this.porcentaje = porcentaje;
        this.nota = 0.0;
    }

    public abstract void realizarEvaluacion();

    // Getters y Setters
    public String getNombre() { return nombre; }
    public double getPorcentaje() { return porcentaje; }
    public double getNota() { return nota; }
    public void setNota(double nota) { this.nota = nota; }
}