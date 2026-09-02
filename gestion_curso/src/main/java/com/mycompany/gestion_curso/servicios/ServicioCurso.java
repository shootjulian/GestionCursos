/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.mycompany.gestion_curso.servicios;

import com.mycompany.gestion_curso.model.Curso;
import com.mycompany.gestion_curso.utils.CursoUtils;
import java.util.List;

/**
 * Capa de servicio para la gestión de Cursos.
 * Se encarga de validar las reglas del sistema antes de guardar, modificar o consultar datos.
 */
public class ServicioCurso {
    
    // Agrega un curso nuevo verificando que sus datos sean válidos y no estén duplicados
    public static boolean agregarCurso(Curso curso) {

        // Validamos que el objeto recibido no sea nulo
        if (curso == null) {
            return false;
        }

        // El código debe ser un número positivo
        if (curso.getCodigo() <= 0) {
            return false;
        }

        // El nombre no puede estar vacío ni contener solo espacios
        if (curso.getNombre() == null || curso.getNombre().trim().isEmpty()) {
            return false;
        }

        // Los créditos deben ser mayores a cero
        if (curso.getCreditos() <= 0) {
            return false;
        }

        // El costo no puede ser un valor negativo
        if (curso.getCosto() < 0) {
            return false;
        }

        // Verificamos que no exista un curso registrado con el mismo código
        Curso cursoExistente = CursoUtils.buscarCursoPorCodigo(curso.getCodigo());
        if (cursoExistente != null) {
            return false;
        }

        // Evitamos nombres duplicados sin importar mayúsculas o minúsculas (ej: "Matemáticas" y "matematicas")
        Curso mismoNombre = CursoUtils.buscarCursoPorNombreIgnorandoMayusculas(curso.getNombre());
        if (mismoNombre != null) {
            return false;
        }

        // Si pasa todas las validaciones, se guarda mediante la capa de utilidades
        CursoUtils.agregarCurso(curso);
        return true;
    }
    
    // Busca un curso por su código, validando primero que el código ingresado sea válido
    public static Curso buscarCursoPorCodigo(int pCodigo){
        if (pCodigo <= 0){
            return null;
        }
        
        return CursoUtils.buscarCursoPorCodigo(pCodigo);
    }
    
    // Obtiene la lista completa de cursos almacenados
    public static List<Curso> listarCursos(){
        return CursoUtils.leerCursos();
    }
    
    // Devuelve la cantidad total de cursos registrados en el sistema
    public static int contarCursos(){
        List<Curso> cursos = CursoUtils.leerCursos();
        return cursos.size();
    }
    
    // Recorre todos los cursos guardados para ir acumulando y calculando el costo total
    public static double sumarCostos(){
        List<Curso> cursos = CursoUtils.leerCursos();
        
        double total = 0;
        for(Curso curso: cursos){
            total += curso.getCosto();
        }
        
        
        return total;
    }
    
    // Actualiza los datos de un curso existente comprobando las reglas de validación
    public static boolean actulizarCursoPorCodigo(int pCodigo, String nuevoNombre, double nuevoCosto){
        
        if (pCodigo <= 0){
            return false;
        }
        
        if (nuevoNombre == null || nuevoNombre.trim().isEmpty()){
            return false;
        }
        
        if (nuevoCosto < 0){
            return false;
        }
        
        // Verificamos que el curso a actualizar realmente exista
        Curso cursoExistente = CursoUtils.buscarCursoPorCodigo(pCodigo);
        if (cursoExistente == null){
            return false;
        }

        // Verificamos que el nuevo nombre no le pertenezca a OTRO curso diferente
        Curso mismoNombre = CursoUtils.buscarCursoPorNombreIgnorandoMayusculas(nuevoNombre);
        if (mismoNombre != null && mismoNombre.getCodigo() != pCodigo) {
            return false;
        }
        
        return CursoUtils.actualizarCursoPorCodigo(pCodigo, nuevoNombre, nuevoCosto);
    }
    
    // Cambia el estado del curso a inactivo para simular la eliminación (borrado lógico)
    public static boolean eliminarCursoPorCodigo (int pCodigo){
        
        if (pCodigo <= 0){
            return false;
        }
        
        Curso cursoExistente = CursoUtils.buscarCursoPorCodigo(pCodigo);
        if (cursoExistente == null){
            return false;
        }
        
        // Si el curso ya se encuentra deshabilitado, no se vuelve a procesar
        if ("INACTIVO".equalsIgnoreCase(cursoExistente.getEstado())){
            return false;
        }
        
        return CursoUtils.eliminarCursoPorCodigo(pCodigo);
    }
}
