/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gestion_curso.servicios;

import com.mycompany.gestion_curso.model.Curso;
import com.mycompany.gestion_curso.utils.CursoUtils;
import java.util.List;

/**
 *
 * @author Julim
 */
public class ServicioCurso {
    
    
    //Agregar un curso
    public static boolean agregarCurso(Curso curso) {

        if (curso == null) {
            return false;
        }

        if (curso.getCodigo() <= 0) {
            return false;
        }

        if (curso.getNombre() == null || curso.getNombre().trim().isEmpty()) {
            return false;
        }

        if (curso.getCreditos() <= 0) {
            return false;
        }

        if (curso.getCosto() < 0) {
            return false;
        }

        Curso cursoExistente = CursoUtils.buscarCursoPorCodigo(curso.getCodigo());

        if (cursoExistente != null) {
            return false;
        }

        CursoUtils.agregarCurso(curso);

        return true;
    }
    
    //Buscar curso por codigo
    public static Curso buscarCursoPorCodigo(int pCodigo){
        if (pcodigo<=0){
            return null;
        }
        
        return CursoUtils.buscarCursoPorCodigo(pCodigo);
    }
    
    //Listar cursos
    public static List<Curso> listarCursos(){
        
        return CursoUtils.leerCursos();
    }
    
    //Contar cursos, cuantos cursos hay
    public static int contarCursos(){
        List<Curso> cursos = CursoUtils.leerCursos();
        return cursos.size();
    }
    
    public static double sumarCostos(){
        List<Curso> cursos = CursoUtils.leerCursos();
        
        double total = 0;
        for(Curso curso: cursos){
            total += curso.getCosto();
        }
        
        return total;
    }
}
