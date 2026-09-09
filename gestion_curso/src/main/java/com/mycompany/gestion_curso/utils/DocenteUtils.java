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
    
    
    
    
}
