/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gestion_curso.utils;

import com.mycompany.gestion_curso.model.Docente;
import java.io.RandomAccessFile;

/**
 *
 * @author Julim
 */
public class DocenteUtils {

    public static final String RUTA_ARCHIVO = "data//departamento.txt";

    public static void agregarDocente(Docente docente) {

        try {
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

        } catch (Exception e) {
            System.out.println("Error: " + e);
        }

    }

    // Busca un docente por su código dentro del archivo
    public static Docente buscarDocentePorCodigo(int pCodigo) {

        int codigoDocente;
        String nombre;
        double salario;
        boolean planta;
        String estado;
        Docente docente;

        try {
            RandomAccessFile archivo = new RandomAccessFile(RUTA_ARCHIVO, "rw");

            // Recorremos el archivo registro por registro
            while (archivo.getFilePointer() < archivo.length()) {

                codigoDocente = archivo.readInt();
                nombre = archivo.readUTF().trim();
                salario = archivo.readDouble();
                planta = archivo.readBoolean();
                estado = archivo.readUTF().trim();

                // Si encontramos el código buscado, creamos el docente
                // y lo retornamos de inmediato
                if (pCodigo == codigoDocente) {

                    docente = new Docente(
                            codigoDocente,
                            nombre,
                            salario,
                            planta,
                            estado
                    );

                    archivo.close();
                    return docente;
                }
            }

            archivo.close();

        } catch (Exception e) {
            System.out.println("ERROR " + e);
        }

        return null;
    }
}
