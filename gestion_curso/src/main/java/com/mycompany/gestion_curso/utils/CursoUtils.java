package com.mycompany.gestion_curso.utils;

import com.mycompany.gestion_curso.model.Curso;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

/**
 * Utilidades de persistencia para Curso usando RandomAccessFile.
 * Se conserva la apertura/cierre explícito del archivo en cada método.
 */
public class CursoUtils {

    public static final String RUTA_ARCHIVO = "data//curso.txt";
    
    /**/
    public static final String RUTA_ARCHIVO_TEMPORAL = "data//archivo_temporal.txt";
    public static final String RUTA_ARCHIVO_BACKUP = "data//curso_backup.txt";

    
    // 4 + (2+31) + 1 + 4 + 8 + (2+8) + 4 = 64 bytes
    public static void agregarCurso(Curso curso) throws Exception {
        RandomAccessFile archivo = new RandomAccessFile(RUTA_ARCHIVO, "rw");
        archivo.seek(archivo.length());

        String nombre = StringUtils.formatearCadenaPorBytes(curso.getNombre(), 31);
        String estado = StringUtils.formatearCadenaPorBytes(curso.getEstado(), 8);

        archivo.writeInt(curso.getCodigo());
        archivo.writeUTF(nombre);
        archivo.writeBoolean(curso.isDisponibilidad());
        archivo.writeInt(curso.getCreditos());
        archivo.writeDouble(curso.getCosto());
        archivo.writeUTF(estado);
        archivo.writeInt(curso.getCodigoDocente());

        archivo.close();
    }

    public static List<Curso> leerCursos() throws Exception {
        List<Curso> cursos = new ArrayList<>();
        RandomAccessFile archivo = new RandomAccessFile(RUTA_ARCHIVO, "rw");

        while (archivo.getFilePointer() < archivo.length()) {
            int codigo = archivo.readInt();
            String nombre = archivo.readUTF().trim();
            boolean disponibilidad = archivo.readBoolean();
            int creditos = archivo.readInt();
            double costo = archivo.readDouble();
            String estado = archivo.readUTF().trim();
            int codigoDocente = archivo.readInt();

            cursos.add(new Curso(codigo, nombre, disponibilidad, creditos,
                    costo, estado, codigoDocente));
        }

        archivo.close();
        return cursos;
    }

    public static Curso buscarCursoPorCodigo(int pCodigo) throws Exception {
        RandomAccessFile archivo = new RandomAccessFile(RUTA_ARCHIVO, "rw");

        while (archivo.getFilePointer() < archivo.length()) {
            int codigo = archivo.readInt();
            String nombre = archivo.readUTF().trim();
            boolean disponibilidad = archivo.readBoolean();
            int creditos = archivo.readInt();
            double costo = archivo.readDouble();
            String estado = archivo.readUTF().trim();
            int codigoDocente = archivo.readInt();

            if (pCodigo == codigo) {
                archivo.close();
                return new Curso(codigo, nombre, disponibilidad, creditos,
                        costo, estado, codigoDocente);
            }
        }

        archivo.close();
        return null;
    }

    public static Curso buscarCursoPorNombreIgnorandoMayusculas(String pNombre) throws Exception {
        if (pNombre == null || pNombre.trim().isEmpty()) {
            return null;
        }

        RandomAccessFile archivo = new RandomAccessFile(RUTA_ARCHIVO, "rw");

        while (archivo.getFilePointer() < archivo.length()) {
            int codigo = archivo.readInt();
            String nombre = archivo.readUTF().trim();
            boolean disponibilidad = archivo.readBoolean();
            int creditos = archivo.readInt();
            double costo = archivo.readDouble();
            String estado = archivo.readUTF().trim();
            int codigoDocente = archivo.readInt();

            if (StringUtils.nombresIguales(pNombre, nombre)
                    && "ACTIVO".equalsIgnoreCase(estado)) {
                archivo.close();
                return new Curso(codigo, nombre, disponibilidad, creditos,
                        costo, estado, codigoDocente);
            }
        }

        archivo.close();
        return null;
    }

    // Ya no retorna boolean: si falla, informa la causa mediante excepción.
    public static void actualizarCursoPorCodigo(int pCodigo, String nuevoNombre,
            double nuevoCosto) throws Exception {

        RandomAccessFile archivo = new RandomAccessFile(RUTA_ARCHIVO, "rw");

        while (archivo.getFilePointer() < archivo.length()) {
            long posicion = archivo.getFilePointer();

            int codigo = archivo.readInt();
            String nombre = archivo.readUTF().trim();
            boolean disponibilidad = archivo.readBoolean();
            int creditos = archivo.readInt();
            double costo = archivo.readDouble();
            String estado = archivo.readUTF().trim();
            int codigoDocente = archivo.readInt();

            if (pCodigo == codigo) {
                nuevoNombre = StringUtils.formatearCadenaPorBytes(nuevoNombre, 31);
                estado = StringUtils.formatearCadenaPorBytes(estado, 8);

                archivo.seek(posicion);
                archivo.writeInt(codigo);
                archivo.writeUTF(nuevoNombre);
                archivo.writeBoolean(disponibilidad);
                archivo.writeInt(creditos);
                archivo.writeDouble(nuevoCosto);
                archivo.writeUTF(estado);
                archivo.writeInt(codigoDocente);

                archivo.close();
                return;
            }
        }

        archivo.close();
        throw new Exception("No existe un curso con el código " + pCodigo);
    }

    // Borrado lógico. Ya no retorna boolean.
    public static void eliminarCursoPorCodigo(int pCodigo) throws Exception {
        RandomAccessFile archivo = new RandomAccessFile(RUTA_ARCHIVO, "rw");

        while (archivo.getFilePointer() < archivo.length()) {
            long posicion = archivo.getFilePointer();

            int codigo = archivo.readInt();
            String nombre = archivo.readUTF().trim();
            boolean disponibilidad = archivo.readBoolean();
            int creditos = archivo.readInt();
            double costo = archivo.readDouble();
            String estado = archivo.readUTF().trim();
            int codigoDocente = archivo.readInt();

            if (pCodigo == codigo) {
                nombre = StringUtils.formatearCadenaPorBytes(nombre, 31);
                estado = StringUtils.formatearCadenaPorBytes("INACTIVO", 8);

                archivo.seek(posicion);
                archivo.writeInt(codigo);
                archivo.writeUTF(nombre);
                archivo.writeBoolean(disponibilidad);
                archivo.writeInt(creditos);
                archivo.writeDouble(costo);
                archivo.writeUTF(estado);
                archivo.writeInt(codigoDocente);

                archivo.close();
                return;
            }
        }

        archivo.close();
        throw new Exception("No existe un curso con el código " + pCodigo);
    }
    
    
    public static List<Curso> buscarCursosPorDocente(int pCodigoDocente) throws Exception{
        
        List<Curso> cursos = new ArrayList<>();
        
        RandomAccessFile archivo = new RandomAccessFile(RUTA_ARCHIVO, "rw");
        
        while(archivo.getFilePointer() < archivo.length()){
            
            int codigo = archivo.readInt();
            String nombre = archivo.readUTF().trim();
            boolean disponibilidad = archivo.readBoolean();
            int creditos = archivo.readInt();
            double costo = archivo.readDouble();
            String estado = archivo.readUTF().trim();
            int codigoDocente = archivo.readInt();
            
            if (pCodigoDocente == codigoDocente){
                Curso curso = new Curso(codigo, nombre, disponibilidad, creditos, costo, estado, codigoDocente);
                
                cursos.add(curso);
            }
        }
        archivo.close();
        
        return cursos;
        
    }

    public static void encontrarEstadosActivos() throws Exception {
        RandomAccessFile archivo = new RandomAccessFile(RUTA_ARCHIVO, "rw");
        RandomAccessFile archivoTemporal = new RandomAccessFile(RUTA_ARCHIVO_TEMPORAL, "rw");
        archivoTemporal.setLength(0);

        while (archivo.getFilePointer() < archivo.length()) {
            int codigo = archivo.readInt();
            String nombre = archivo.readUTF().trim();
            boolean disponibilidad = archivo.readBoolean();
            int creditos = archivo.readInt();
            double costo = archivo.readDouble();
            String estado = archivo.readUTF().trim();
            int codigoDocente = archivo.readInt();

            if (estado.equalsIgnoreCase("ACTIVO")) {
                nombre = StringUtils.formatearCadenaPorBytes(nombre, 31);
                estado = StringUtils.formatearCadenaPorBytes(estado, 8);

                archivoTemporal.writeInt(codigo);
                archivoTemporal.writeUTF(nombre);
                archivoTemporal.writeBoolean(disponibilidad);
                archivoTemporal.writeInt(creditos);
                archivoTemporal.writeDouble(costo);
                archivoTemporal.writeUTF(estado);
                archivoTemporal.writeInt(codigoDocente);
            }
        }

        archivo.close();
        archivoTemporal.close();

        Path original = Path.of(RUTA_ARCHIVO);
        Path temporal = Path.of(RUTA_ARCHIVO_TEMPORAL);
        Path backup = Path.of(RUTA_ARCHIVO_BACKUP);

        Files.move(original, backup, StandardCopyOption.REPLACE_EXISTING);
        Files.move(temporal, original, StandardCopyOption.REPLACE_EXISTING);
    }
    
}
