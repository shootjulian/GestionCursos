/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gestion_curso.utils;

import com.mycompany.gestion_curso.model.Curso;
import java.io.RandomAccessFile;

/**
 *
 * @author Julim
 */
public class CursoUtils {

    public static void agregarCurso(Curso curso) {

        try {
            RandomAccessFile archivo = new RandomAccessFile("data//curso.txt", "rw");

            archivo.seek(archivo.length());

            archivo.writeInt(curso.getCodigo());
            archivo.writeUTF(curso.getNombre());
            archivo.writeBoolean(curso.isDisponibilidad());
            archivo.writeInt(curso.getCreditos());
            archivo.writeDouble(curso.getCosto());
            archivo.writeUTF(curso.getEstado());

            archivo.close();
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }

    public static Curso buscarCurso(int pcodigo) {
        //strcodigo, String nombre, strdisponibilidad, strcreditos, strcosto, String estado;
        int codigo;
        String nombre;
        boolean disponibilidad;
        int creditos;
        double costo;
        String estado;
        Curso curso;
        try {
            RandomAccessFile archivo = new RandomAccessFile("data//curso.txt", "rw");
            while (archivo.getFilePointer() < archivo.length()) {
                codigo = archivo.readInt();
                nombre = archivo.readUTF();
                disponibilidad = archivo.readBoolean();
                creditos = archivo.readInt();
                costo = archivo.readDouble();
                estado = archivo.readUTF();
                if (pcodigo == codigo) {
                    curso = new Curso(codigo, nombre, disponibilidad, creditos, costo, estado);
                    archivo.close();
                    return curso;
                }

            }
            archivo.close();
        } catch (Exception e) {
            System.out.println("ERROR" + e);
        }

        return null;

    }
}
