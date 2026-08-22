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
    
    public static void agregarCurso(Curso curso){
        
        try{
            RandomAccessFile archivo = new RandomAccessFile("data//curso.txt", "rw");
            
            archivo.seek(archivo.length());
            
            archivo.writeInt(curso.getCodigo());
            archivo.writeUTF(curso.getNombre());
            archivo.writeBoolean(curso.isDisponibilidad());
            archivo.writeInt(curso.getCreditos());
            archivo.writeDouble(curso.getCosto());
            archivo.writeUTF(curso.getEstado());
            
            archivo.close();
        } catch (Exception e){
            System.out.println("Error: " + e);
        }
    }
    
}
