/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gestion_curso.model;

/**
 *
 * @author Julim
 */
public class Curso {
    
    public static final int TAMANO_REGISTRO = 60;

    private int codigo;
    private String nombre;
    private boolean disponibilidad;
    private int creditos;
    private double costo;
    private String estado;

    public Curso(int codigo, String nombre, boolean disponibilidad,
                 int creditos, double costo, String estado) {

        this.codigo = codigo;
        this.nombre = nombre;
        this.disponibilidad = disponibilidad;
        this.creditos = creditos;
        this.costo = costo;
        this.estado = estado;
    }

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
