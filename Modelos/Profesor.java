package PROYECTO_FINAL.Modelos;

/**
 * Representa a un docente en el sistema. Hereda de Persona y
 * puede gestionar atributos específicos como su especialidad.
 */
public class Profesor extends Persona {
    private String especialidad;

    public Profesor(String nombre, String id, String email, String especialidad) {
        super(nombre, id, email);
        this.especialidad = especialidad;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    @Override
    public void mostrarInformacion() {
        System.out.println("PROFESOR: " + getNombre() + " | Especialidad: " + especialidad);
    }
}