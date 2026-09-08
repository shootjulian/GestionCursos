package com.mycompany.gestion_curso.model;

/**
 * Clase Modelo que representa la estructura de datos de un Docente.
 * Define sus atributos, constructor y métodos de acceso (Getters y Setters).
 */
public class Docente {

    // Tamaño fijo en bytes reservado para guardar cada registro de docente
    // en el archivo plano.
    // Lo ajustaremos cuando hagamos DocenteUtils.
    public static final int TAMANO_REGISTRO = 60;

    // Atributos principales del docente

    private int codigoDocente; // PK
    private String nombre;
    private double salario;
    private boolean planta;
    private String estado;

    // Constructor para inicializar todos los datos del docente
    // al momento de crearlo
    public Docente(int codigoDocente, String nombre, double salario,
                   boolean planta, String estado) {

        this.codigoDocente = codigoDocente;
        this.nombre = nombre;
        this.salario = salario;
        this.planta = planta;
        this.estado = estado;
    }

    // --- MÉTODOS DE ACCESO (GETTERS Y SETTERS) ---

    public int getCodigoDocente() {
        return codigoDocente;
    }

    public void setCodigoDocente(int codigoDocente) {
        this.codigoDocente = codigoDocente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    public boolean isPlanta() {
        return planta;
    }

    public void setPlanta(boolean planta) {
        this.planta = planta;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}