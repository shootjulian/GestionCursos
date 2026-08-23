/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gestion_curso.servicios;

import com.mycompany.gestion_curso.model.Curso;
import com.mycompany.gestion_curso.utils.CursoUtils;

/**
 *
 * @author Julim
 */
public class ServicioCurso {
    
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
    
    
    
}
