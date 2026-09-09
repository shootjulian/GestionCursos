/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gestion_curso.servicios;

import com.mycompany.gestion_curso.model.Docente;
import com.mycompany.gestion_curso.utils.DocenteUtils;
import java.util.List;

public class ServicioDocente {

    public static void agregarDocente(Docente docente) throws Exception {

        // Validar que exista el objeto
        if (docente == null) {
            throw new Exception("El docente no puede ser nulo");
        }

        // Validar código
        if (docente.getCodigoDocente() <= 0) {
            throw new Exception("El código del docente debe ser mayor que cero");
        }

        // Validar nombre
        if (docente.getNombre() == null || docente.getNombre().trim().isEmpty()) {
            throw new Exception("El nombre del docente es obligatorio");
        }

        // Validar salario
        if (docente.getSalario() < 0) {
            throw new Exception("El salario del docente no puede ser negativo");
        }

        // Si todo está correcto, se manda a guardar
        DocenteUtils.agregarDocente(docente);
    }

    // Obtiene la lista completa de docentes almacenados
    public static List<Docente> listarDocentes() {
        return DocenteUtils.leerDocentes();
    }

    public static int contarDocentes() {
        List<Docente> docentes = DocenteUtils.leerDocentes();
        return docentes.size();
    }

    // Recorre todos los docentes guardados para acumular el total de salarios
    public static double sumarSalarios() {
        List<Docente> docentes = DocenteUtils.leerDocentes();

        double total = 0;
        for (Docente docente : docentes) {
            total += docente.getSalario();
        }

        return total;
    }
}
