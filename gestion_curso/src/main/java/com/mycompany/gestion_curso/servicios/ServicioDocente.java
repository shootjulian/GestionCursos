/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gestion_curso.servicios;

import com.mycompany.gestion_curso.model.Docente;
import com.mycompany.gestion_curso.utils.DocenteUtils;


public class ServicioDocente {

    public static void agregarDocente(Docente docente) throws Exception {

        // Validar que el objeto exista
        if (docente == null) {
            throw new Exception("El docente no puede ser nulo");
        }

        // Validar código
        if (docente.getCodigoDocente() <= 0) {
            throw new Exception("El código del docente debe ser mayor que cero");
        }

        // Validar que la PK no esté repetida
        if (DocenteUtils.buscarDocentePorCodigo(docente.getCodigoDocente()) != null) {
            throw new Exception("Ya existe un docente con ese código");
        }

        // Validar nombre
        if (docente.getNombre() == null || docente.getNombre().trim().isEmpty()) {
            throw new Exception("El nombre del docente es obligatorio");
        }

        // Validar salario
        if (docente.getSalario() < 0) {
            throw new Exception("El salario del docente no puede ser negativo");
        }

        // Si pasó todas las validaciones, se guarda
        DocenteUtils.agregarDocente(docente);
    }
}
