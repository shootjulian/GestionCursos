/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gestion_curso.utils;

import com.mycompany.gestion_curso.model.Curso;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Julim
 */
public class CursoUtils {

    public static void agregarCurso(Curso curso) {

        try {
            RandomAccessFile archivo = new RandomAccessFile("data//curso.txt", "rw");

            archivo.seek(archivo.length());
            
            String nombre = StringUtils.formatearCadena(curso.getNombre(), 31);
            String estado = StringUtils.formatearCadena(curso.getEstado(), 8);

            archivo.writeInt(curso.getCodigo());
            archivo.writeUTF(nombre);
            archivo.writeBoolean(curso.isDisponibilidad());
            archivo.writeInt(curso.getCreditos());
            archivo.writeDouble(curso.getCosto());
            archivo.writeUTF(estado);

            archivo.close();
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }
    
    public static List<Curso> leerCursos(){
        List<Curso> cursos = new ArrayList();
        
        int codigo;
        String nombre;
        boolean disponibilidad;
        int creditos;
        double costo;
        String estado;
        Curso curso;
        
        try{
            RandomAccessFile archivo = new RandomAccessFile("data//curso.txt", "rw");
            
            while (archivo.getFilePointer() < archivo.length()){
                
                codigo = archivo.readInt();
                nombre = archivo.readUTF().trim();
                disponibilidad = archivo.readBoolean();
                creditos = archivo.readInt();
                costo = archivo.readDouble();
                estado = archivo.readUTF().trim();
                
                curso = new Curso(codigo, nombre, disponibilidad, creditos, costo, estado);
                
                cursos.add(curso);
            }
            
            archivo.close();
            
        } catch (Exception e){
            System.out.println("Error: " + e);
        }
        
        return cursos;
    }

    public static Curso buscarCursoPorCodigo(int pcodigo) {
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
                nombre = archivo.readUTF().trim();
                disponibilidad = archivo.readBoolean();
                creditos = archivo.readInt();
                costo = archivo.readDouble();
                estado = archivo.readUTF().trim();
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
