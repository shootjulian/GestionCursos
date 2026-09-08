package com.mycompany.gestion_curso.servicios;

import com.mycompany.gestion_curso.model.Curso;
import com.mycompany.gestion_curso.model.Docente;
import com.mycompany.gestion_curso.utils.CursoUtils;
import com.mycompany.gestion_curso.utils.DocenteUtils;
import java.util.List;

/**
 * Capa de servicio: contiene validaciones y reglas antes de acceder a persistencia.
 */
public class ServicioCurso {

    public static void agregarCurso(Curso curso) throws Exception {
        if (curso == null) {
            throw new Exception("El curso no puede ser nulo");
        }
        if (curso.getCodigo() <= 0) {
            throw new Exception("El código del curso debe ser mayor que cero");
        }
        if (curso.getNombre() == null || curso.getNombre().trim().isEmpty()) {
            throw new Exception("El nombre del curso es obligatorio");
        }
        if (curso.getCreditos() <= 0) {
            throw new Exception("Los créditos deben ser mayores que cero");
        }
        if (curso.getCosto() < 0) {
            throw new Exception("El costo del curso no puede ser negativo");
        }
        if (CursoUtils.buscarCursoPorCodigo(curso.getCodigo()) != null) {
            throw new Exception("Ya existe un curso con ese código");
        }
        Curso mismoNombre = CursoUtils.buscarCursoPorNombreIgnorandoMayusculas(curso.getNombre());
        if (mismoNombre != null) {
            throw new Exception("Ya existe un curso activo con ese nombre");
        }

        // La FK se valida solo cuando venga asignada. Esto mantiene compatibles
        // las GUI actuales, que todavía no capturan el código del docente.
        if (curso.getCodigoDocente() > 0) {
            Docente docente = DocenteUtils.buscarDocentePorCodigo(curso.getCodigoDocente());
            if (docente == null) {
                throw new Exception("No existe un docente con el código " + curso.getCodigoDocente());
            }
        }

        CursoUtils.agregarCurso(curso);
    }

    public static Curso buscarCursoPorCodigo(int pCodigo) throws Exception {
        if (pCodigo <= 0) {
            throw new Exception("El código del curso debe ser mayor que cero");
        }
        return CursoUtils.buscarCursoPorCodigo(pCodigo);
    }

    public static List<Curso> listarCursos() throws Exception {
        return CursoUtils.leerCursos();
    }

    public static int contarCursos() throws Exception {
        return CursoUtils.leerCursos().size();
    }

    public static double sumarCostos() throws Exception {
        List<Curso> cursos = CursoUtils.leerCursos();
        double total = 0;
        for (Curso curso : cursos) {
            total += curso.getCosto();
        }
        return total;
    }

    public static void actualizarCursoPorCodigo(int pCodigo, String nuevoNombre,
            double nuevoCosto) throws Exception {
        if (pCodigo <= 0) {
            throw new Exception("El código del curso debe ser mayor que cero");
        }
        if (nuevoNombre == null || nuevoNombre.trim().isEmpty()) {
            throw new Exception("El nuevo nombre es obligatorio");
        }
        if (nuevoCosto < 0) {
            throw new Exception("El nuevo costo no puede ser negativo");
        }

        Curso cursoExistente = CursoUtils.buscarCursoPorCodigo(pCodigo);
        if (cursoExistente == null) {
            throw new Exception("No existe un curso con el código " + pCodigo);
        }

        Curso mismoNombre = CursoUtils.buscarCursoPorNombreIgnorandoMayusculas(nuevoNombre);
        if (mismoNombre != null && mismoNombre.getCodigo() != pCodigo) {
            throw new Exception("Ya existe otro curso activo con ese nombre");
        }

        CursoUtils.actualizarCursoPorCodigo(pCodigo, nuevoNombre, nuevoCosto);
    }

    public static void eliminarCursoPorCodigo(int pCodigo) throws Exception {
        if (pCodigo <= 0) {
            throw new Exception("El código del curso debe ser mayor que cero");
        }

        Curso cursoExistente = CursoUtils.buscarCursoPorCodigo(pCodigo);
        if (cursoExistente == null) {
            throw new Exception("No existe un curso con el código " + pCodigo);
        }
        if ("INACTIVO".equalsIgnoreCase(cursoExistente.getEstado())) {
            throw new Exception("El curso ya se encuentra INACTIVO");
        }

        CursoUtils.eliminarCursoPorCodigo(pCodigo);
    }
}
