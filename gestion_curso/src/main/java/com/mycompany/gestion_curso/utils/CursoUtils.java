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

    public static Curso buscarCursoPorCodigo(int pCodigo) {
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
                if (pCodigo == codigo) {
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
    
    public static boolean actulizarCursoPorCodigo(int pCodigo, String nuevoNombre, double nuevoCosto){
        
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
                long posicion = archivo.getFilePointer();
                
                codigo = archivo.readInt();
                nombre = archivo.readUTF().trim();
                disponibilidad = archivo.readBoolean();
                creditos = archivo.readInt();
                costo = archivo.readDouble();
                estado = archivo.readUTF().trim();
                
                if (pCodigo == codigo){
                    
                    nuevoNombre = StringUtils.formatearCadena(nuevoNombre, 31);
                    estado = StringUtils.formatearCadena(estado, 8);
                    
                    // Regresamos al inicio del registro
                    posicion = archivo.getFilePointer();
                    
                    // Sobrescribimos TODO el curso
                    archivo.writeInt(codigo);
                    archivo.writeUTF(nuevoNombre); //Escribimos nuevo nombre
                    archivo.writeBoolean(disponibilidad);
                    archivo.writeInt(creditos);
                    archivo.writeDouble(nuevoCosto); //Escribimos nuevo costo
                    archivo.writeUTF(estado);
                    
                    archivo.close();
                    
                    return true;
                }    
                
            }
           
            archivo.close();
            
        } catch(Exception e){
            System.out.println("Error: " + e);
        }
        
        return false;
    } 
    
    public static boolean eliminarCursoPorCodigo(int pCodigo){
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
                long posicion = archivo.getFilePointer();
                
                codigo = archivo.readInt();
                nombre = archivo.readUTF().trim();
                disponibilidad = archivo.readBoolean();
                creditos = archivo.readInt();
                costo = archivo.readDouble();
                estado = archivo.readUTF().trim();
                
                if (pCodigo == codigo){
                    
                    // Cambio de estado = eliminación lógica
                    estado = "INACTIVO";
                    
                    nombre = StringUtils.formatearCadena(nombre, 31);
                    estado = StringUtils.formatearCadena(estado, 8);
                    
                    // Regresamos al inicio del registro
                    posicion = archivo.getFilePointer();
                    
                    // Sobrescribimos TODO el curso
                    archivo.writeInt(codigo);
                    archivo.writeUTF(nombre);
                    archivo.writeBoolean(disponibilidad);
                    archivo.writeInt(creditos);
                    archivo.writeDouble(costo);
                    archivo.writeUTF(estado); //Escribimos el nuevo estado, que sera INACTIVO
                    
                    archivo.close();
                    
                    return true;
                }
            }
        } catch (Exception e){
            System.out.println("Error: " + e);
        }
        
        return false;
    }
    
    
}
