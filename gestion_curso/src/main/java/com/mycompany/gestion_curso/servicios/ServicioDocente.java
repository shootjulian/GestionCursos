package com.mycompany.gestion_curso.servicios;

import com.mycompany.gestion_curso.model.Docente;
import com.mycompany.gestion_curso.utils.DocenteUtils;
import java.util.List;

public class ServicioDocente {

    public static void agregarDocente(Docente docente) throws Exception {
        if (docente == null) {
            throw new Exception("El docente no puede ser nulo");
        }
        if (docente.getCodigoDocente() <= 0) {
            throw new Exception("El código del docente debe ser mayor que cero");
        }
        if (DocenteUtils.buscarDocentePorCodigo(docente.getCodigoDocente()) != null) {
            throw new Exception("Ya existe un docente con ese código");
        }
        if (docente.getNombre() == null || docente.getNombre().trim().isEmpty()) {
            throw new Exception("El nombre del docente es obligatorio");
        }
        if (docente.getSalario() < 0) {
            throw new Exception("El salario del docente no puede ser negativo");
        }

        DocenteUtils.agregarDocente(docente);
    }

    public static Docente buscarDocentePorCodigo(int codigo) throws Exception {
        if (codigo <= 0) {
            throw new Exception("El código del docente debe ser mayor que cero");
        }
        return DocenteUtils.buscarDocentePorCodigo(codigo);
    }

    public static List<Docente> listarDocentes() throws Exception {
        return DocenteUtils.leerDocentes();
    }

    public static int contarDocentes() throws Exception {
        return DocenteUtils.leerDocentes().size();
    }
}
