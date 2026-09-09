package com.mycompany.gestion_curso.utils;

import com.mycompany.gestion_curso.model.Docente;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

/**
 * Utilidades de persistencia para Docente usando RandomAccessFile.
 */
public class DocenteUtils {

    public static final String RUTA_ARCHIVO = "data//docente.txt";

    // 4 + (2+35) + 8 + 1 + (2+8) = 60 bytes
    public static void agregarDocente(Docente docente) throws Exception {
        RandomAccessFile archivo = new RandomAccessFile(RUTA_ARCHIVO, "rw");
        archivo.seek(archivo.length());

        String nombre = StringUtils.formatearCadenaPorBytes(docente.getNombre(), 35);
        String estado = StringUtils.formatearCadenaPorBytes(docente.getEstado(), 8);

        archivo.writeInt(docente.getCodigoDocente());
        archivo.writeUTF(nombre);
        archivo.writeDouble(docente.getSalario());
        archivo.writeBoolean(docente.isPlanta());
        archivo.writeUTF(estado);

        archivo.close();
    }

    public static Docente buscarDocentePorCodigo(int pCodigo) throws Exception {
        RandomAccessFile archivo = new RandomAccessFile(RUTA_ARCHIVO, "rw");

        while (archivo.getFilePointer() < archivo.length()) {
            int codigoDocente = archivo.readInt();
            String nombre = archivo.readUTF().trim();
            double salario = archivo.readDouble();
            boolean planta = archivo.readBoolean();
            String estado = archivo.readUTF().trim();

            if (pCodigo == codigoDocente) {
                archivo.close();
                return new Docente(codigoDocente, nombre, salario, planta, estado);
            }
        }

        archivo.close();
        return null;
    }

    public static List<Docente> leerDocentes() throws Exception {
        List<Docente> docentes = new ArrayList<>();
        RandomAccessFile archivo = new RandomAccessFile(RUTA_ARCHIVO, "rw");

        while (archivo.getFilePointer() < archivo.length()) {
            int codigoDocente = archivo.readInt();
            String nombre = archivo.readUTF().trim();
            double salario = archivo.readDouble();
            boolean planta = archivo.readBoolean();
            String estado = archivo.readUTF().trim();

            docentes.add(new Docente(codigoDocente, nombre, salario, planta, estado));
        }

        archivo.close();
        return docentes;
    }
   
    public static void actualizarDocentePorCodigo(int pCodigo, String nuevoNombre, 
            double nuevoSalario) throws Exception {

        RandomAccessFile archivo = new RandomAccessFile(RUTA_ARCHIVO, "rw");

        while (archivo.getFilePointer() < archivo.length()) {
            // Guardala posición justo antes de empezar a leer este docente
            long posicion = archivo.getFilePointer();

            //Leemos todos los datos (el puntero avanza)
            int codigoDocente = archivo.readInt();
            String nombre = archivo.readUTF().trim();
            double salario = archivo.readDouble();
            boolean planta = archivo.readBoolean();
            String estado = archivo.readUTF().trim();

            // Verificamos si es el docente que buscamos
            if (pCodigo == codigoDocente) {
                // Formateamos las cadenas a sus bytes exactos (Docente usa 35 y 8)
                nuevoNombre = StringUtils.formatearCadenaPorBytes(nuevoNombre, 35);
                estado = StringUtils.formatearCadenaPorBytes(estado, 8);

                // Nos devolvemos al inicio de este registro
                archivo.seek(posicion);
                
                // Reescribimos todo (con el nuevo nombre y el nuevo salario)
                archivo.writeInt(codigoDocente);
                archivo.writeUTF(nuevoNombre);
                archivo.writeDouble(nuevoSalario);
                archivo.writeBoolean(planta);
                archivo.writeUTF(estado);

                archivo.close();
                return; // Terminamos y salimos con éxito
            }
        }

        archivo.close();
        throw new Exception("No existe un docente con el código " + pCodigo);
    }
    // Borrado lógico. Cambia el estado a INACTIVO
    public static void eliminarDocentePorCodigo(int pCodigo) throws Exception {
        RandomAccessFile archivo = new RandomAccessFile(RUTA_ARCHIVO, "rw");

        while (archivo.getFilePointer() < archivo.length()) {
            long posicion = archivo.getFilePointer(); // Guardamos la posición inicial

            int codigoDocente = archivo.readInt();
            String nombre = archivo.readUTF().trim();
            double salario = archivo.readDouble();
            boolean planta = archivo.readBoolean();
            String estado = archivo.readUTF().trim();

            // Si encontramos al docente
            if (pCodigo == codigoDocente) {
                // Formateamos las cadenas (nombre mantiene sus 35 bytes, estado cambia a INACTIVO con 8 bytes)
                nombre = StringUtils.formatearCadenaPorBytes(nombre, 35);
                estado = StringUtils.formatearCadenaPorBytes("INACTIVO", 8);

                // Nos devolvemos al inicio del registro
                archivo.seek(posicion);
                
                // Sobrescribimos todo el registro con el nuevo estado
                archivo.writeInt(codigoDocente);
                archivo.writeUTF(nombre);
                archivo.writeDouble(salario);
                archivo.writeBoolean(planta);
                archivo.writeUTF(estado);

                archivo.close();
                return; // Salimos con éxito
            }
        }

        archivo.close();
        throw new Exception("No existe un docente con el código " + pCodigo);
    }
    }
    

