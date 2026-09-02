package com.mycompany.gestion_curso.model;

/**
 * Clase Modelo que representa la estructura de datos de un Curso.
 * Define sus atributos, constructor y métodos de acceso (Getters y Setters).
 */
public class Curso {
    
    // Tamaño fijo en bytes reservado para guardar cada registro de curso en el archivo plano
    public static final int TAMANO_REGISTRO = 60;

    // Atributos principales del curso
    
    private int codigo; //PK
    private String nombre;
    private boolean disponibilidad;
    private int creditos;
    private double costo;
    private String estado;
    
     private int codigoMateria;  //FK

    // Constructor para inicializar todos los datos del curso al momento de crearlo
    public Curso(int codigo, String nombre, boolean disponibilidad,
                 int creditos, double costo, String estado) {

        this.codigo = codigo;
        this.nombre = nombre;
        this.disponibilidad = disponibilidad;
        this.creditos = creditos;
        this.costo = costo;
        this.estado = estado;
    }

    // --- MÉTODOS DE ACCESO (GETTERS Y SETTERS) ---

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isDisponibilidad() {
        return disponibilidad;
    }

    public void setDisponibilidad(boolean disponibilidad) {
        this.disponibilidad = disponibilidad;
    }

    public int getCreditos() {
        return creditos;
    }

    public void setCreditos(int creditos) {
        this.creditos = creditos;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}