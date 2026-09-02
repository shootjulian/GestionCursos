package com.mycompany.gestion_curso.utils;

import com.mycompany.gestion_curso.model.Curso;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase de utilidad para gestionar la lectura y escritura de cursos 
 * directamente en el archivo plano usando RandomAccessFile.
 */
public class CursoUtils {

    // Guarda un nuevo curso al final del archivo
    public static void agregarCurso(Curso curso) {

        try {
            // Abrimos el archivo en modo lectura y escritura ("rw")
            RandomAccessFile archivo = new RandomAccessFile("data//curso.txt", "rw");

            // Movemos el puntero al final del archivo para agregar el registro sin sobrescribir nada
            archivo.seek(archivo.length());
            
            // Ajustamos el tamaño del nombre y del estado a un número fijo de bytes
            String nombre = StringUtils.formatearCadenaPorBytes(curso.getNombre(), 31);
            String estado = StringUtils.formatearCadenaPorBytes(curso.getEstado(), 8);

            // Escribimos cada atributo del curso en el orden correspondiente
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
    
    // Lee todos los registros guardados en el archivo y los devuelve en una lista
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
            
            // Mientras el puntero no haya llegado al final del archivo, seguimos leyendo registros
            while (archivo.getFilePointer() < archivo.length()){
                
                codigo = archivo.readInt();
                // .trim() elimina los espacios sobrantes agregados al formatear la cadena
                nombre = archivo.readUTF().trim();
                disponibilidad = archivo.readBoolean();
                creditos = archivo.readInt();
                costo = archivo.readDouble();
                estado = archivo.readUTF().trim();
                
                // Creamos el objeto con los datos leídos y lo agregamos a la lista
                curso = new Curso(codigo, nombre, disponibilidad, creditos, costo, estado);
                cursos.add(curso);
            }
            
            archivo.close();
            
        } catch (Exception e){
            System.out.println("Error: " + e);
        }
        
        return cursos;
    }

    // Busca un curso por su código dentro del archivo
    public static Curso buscarCursoPorCodigo(int pCodigo) {
        int codigo;
        String nombre;
        boolean disponibilidad;
        int creditos;
        double costo;
        String estado;
        Curso curso;
        
        try {
            RandomAccessFile archivo = new RandomAccessFile("data//curso.txt", "rw");
            
            // Recorremos el archivo registro por registro
            while (archivo.getFilePointer() < archivo.length()) {
                codigo = archivo.readInt();
                nombre = archivo.readUTF().trim();
                disponibilidad = archivo.readBoolean();
                creditos = archivo.readInt();
                costo = archivo.readDouble();
                estado = archivo.readUTF().trim();
                
                // Si encontramos el código buscado, creamos el curso y lo retornamos de inmediato
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

    // Busca un curso ACTIVO cuyo nombre sea igual, ignorando mayúsculas y minúsculas
    public static Curso buscarCursoPorNombreIgnorandoMayusculas(String pNombre) {
        if (pNombre == null || pNombre.trim().isEmpty()) {
            return null;
        }

        try {
            RandomAccessFile archivo = new RandomAccessFile("data//curso.txt", "rw");

            while (archivo.getFilePointer() < archivo.length()) {
                int codigo = archivo.readInt();
                String nombre = archivo.readUTF().trim();
                boolean disponibilidad = archivo.readBoolean();
                int creditos = archivo.readInt();
                double costo = archivo.readDouble();
                String estado = archivo.readUTF().trim();

                // Comparamos el nombre y confirmamos que el estado sea ACTIVO
                if (StringUtils.nombresIguales(pNombre, nombre) && "ACTIVO".equalsIgnoreCase(estado)) {
                    archivo.close();
                    return new Curso(codigo, nombre, disponibilidad, creditos, costo, estado);
                }
            }

            archivo.close();
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }

        return null;
    }
    
    // Busca un curso por su código y actualiza únicamente el nombre y el costo
    public static boolean actualizarCursoPorCodigo(int pCodigo, String nuevoNombre, double nuevoCosto){
        int codigo;
        String nombre;
        boolean disponibilidad;
        int creditos;
        double costo;
        String estado;
        
        try{
            RandomAccessFile archivo = new RandomAccessFile("data//curso.txt", "rw");
            
            while (archivo.getFilePointer() < archivo.length()){
                // Guardamos la posición exacta en la que inicia este registro antes de leerlo
                long posicion = archivo.getFilePointer();
                
                codigo = archivo.readInt();
                nombre = archivo.readUTF().trim();
                disponibilidad = archivo.readBoolean();
                creditos = archivo.readInt();
                costo = archivo.readDouble();
                estado = archivo.readUTF().trim();
                
                if (pCodigo == codigo){
                    // Formateamos los textos actualizados a sus tamaños fijos
                    nuevoNombre = StringUtils.formatearCadenaPorBytes(nuevoNombre, 31);
                    estado = StringUtils.formatearCadenaPorBytes(estado, 8);
                    
                    // Regresamos el puntero al inicio de este registro para sobrescribirlo
                    archivo.seek(posicion);
                    
                    // Escribimos los datos actualizados encima del registro anterior
                    archivo.writeInt(codigo);
                    archivo.writeUTF(nuevoNombre);
                    archivo.writeBoolean(disponibilidad);
                    archivo.writeInt(creditos);
                    archivo.writeDouble(nuevoCosto);
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
    
    // Deshabilita un curso cambiando su estado a "INACTIVO" (borrado lógico)
    public static boolean eliminarCursoPorCodigo(int pCodigo){
        int codigo;
        String nombre;
        boolean disponibilidad;
        int creditos;
        double costo;
        String estado;
        
        try{
            RandomAccessFile archivo = new RandomAccessFile("data//curso.txt", "rw");
            
            while (archivo.getFilePointer() < archivo.length()){
                // Guardamos la posición donde empieza el registro
                long posicion = archivo.getFilePointer();
                
                codigo = archivo.readInt();
                nombre = archivo.readUTF().trim();
                disponibilidad = archivo.readBoolean();
                creditos = archivo.readInt();
                costo = archivo.readDouble();
                estado = archivo.readUTF().trim();
                
                if (pCodigo == codigo){
                    // Cambiamos el estado a INACTIVO en lugar de borrar el registro del archivo
                    estado = "INACTIVO";
                    
                    nombre = StringUtils.formatearCadenaPorBytes(nombre, 31);
                    estado = StringUtils.formatearCadenaPorBytes(estado, 8);
                    
                    // Volvemos al inicio del registro para aplicar los cambios
                    archivo.seek(posicion);
                    
                    archivo.writeInt(codigo);
                    archivo.writeUTF(nombre);
                    archivo.writeBoolean(disponibilidad);
                    archivo.writeInt(creditos);
                    archivo.writeDouble(costo);
                    archivo.writeUTF(estado);
                    
                    archivo.close();
                    return true;
                }
            }
            archivo.close();
        } catch (Exception e){
            System.out.println("Error: " + e);
        }
        
        return false;
    }
}
