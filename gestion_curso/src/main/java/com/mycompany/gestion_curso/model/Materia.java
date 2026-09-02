/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gestion_curso.model;

public class Materia {

    private int codigo; //PK
    private String nombre;
    private String facultad;
    private double intensidad;
    private boolean obligatoria;
    private String estado;

    // Constructor vacío
    public Materia() {
        this.codigo = 0;
        this.nombre = "";
        this.facultad = "";
        this.intensidad = 0.0;
        this.obligatoria = false;
        this.estado = "AC";
    }

    // Constructor con parámetros
    public Materia(int codigo, String nombre, String facultad,
                   double intensidad, boolean obligatoria, String estado) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.facultad = facultad;
        this.intensidad = intensidad;
        this.obligatoria = obligatoria;
        this.estado = estado;
    }

    // Getters y Setters

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

    public String getFacultad() {
        return facultad;
    }

    public void setFacultad(String facultad) {
        this.facultad = facultad;
    }

    public double getIntensidad() {
        return intensidad;
    }

    public void setIntensidad(double intensidad) {
        this.intensidad = intensidad;
    }

    public boolean isObligatoria() {
        return obligatoria;
    }

    public void setObligatoria(boolean obligatoria) {
        this.obligatoria = obligatoria;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}