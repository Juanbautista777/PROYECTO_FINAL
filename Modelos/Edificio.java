package PROYECTO_FINAL.Modelos;

import PROYECTO_FINAL.estructuras.ListaEnlazada;

public class Edificio {
    private String nombre;
    private String ubicacion;
    private ListaEnlazada<Aula> aulas;

    public Edificio(String nombre, String ubicacion) {
        this.nombre = nombre;
        this.ubicacion = ubicacion;
        this.aulas = new ListaEnlazada<>();
    }

    public void agregarAula(Aula aula) {
        this.aulas.agregar(aula);
    }

    // Getters y Setters
    public String getNombre() { return nombre; }
    public String getUbicacion() { return ubicacion; }
    public ListaEnlazada<Aula> getAulas() { return aulas; }
}