package PROYECTO_FINAL.Modelos;

/**
 * Clase abstracta que define la estructura base para cualquier persona en el sistema.
 * Sirve como plantilla para subclases específicas.
 */
public abstract class Persona {
    private String nombre;
    private String id;
    private String email;
    
    // Constructor para inicializar los atributos básicos de una persona
    public Persona(String nombre, String id, String email) {
        this.nombre = nombre;
        this.id = id;
        this.email = email;
    }

    // Métodos de acceso (Getters y Setters)
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Define el comportamiento para visualizar los datos del usuario.
     * La implementación específica depende de la subclase que la herede.
     */
    public abstract void mostrarInformacion();
    
}