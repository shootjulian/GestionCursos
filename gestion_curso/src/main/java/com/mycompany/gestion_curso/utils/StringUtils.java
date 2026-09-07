package com.mycompany.gestion_curso.utils;

import java.nio.charset.StandardCharsets;

/**
 * Clase de utilidad para el manejo, formateo y comparación de texto.
 * Ayuda a controlar el tamaño exacto en bytes al guardar datos en el archivo plano.
 */
public class StringUtils {
    
    // Ajusta un texto a una cantidad fija de caracteres (recorta si es más largo o rellena con espacios si es más corto)
    public static String formatearCadena(String cadena, int n){
        int faltan;
        
        if (cadena == null){
            cadena = "";
        }
        
        if (cadena.length() > n) { // Si el texto supera el límite, recortamos
            return cadena.substring(0, n);
            
        } else { // Si es más corto, calculamos cuántos espacios faltan para completar
            faltan = n - cadena.length();
            
            return cadena + " ".repeat(faltan);
        }
    }

    /**
     * Cuenta cuántos bytes ocupa la cadena en UTF-8.
     * Ej: "a" = 1 byte, "ñ" = 2 bytes, "€" = 3 bytes.
     */
    public static int longitudEnBytes(String cadena) {
        if (cadena == null) {
            return 0;
        }
        return cadena.getBytes(StandardCharsets.UTF_8).length;
    }

    /**
     * Formatea la cadena para que ocupe exactamente maxBytes en UTF-8
     * (el mismo formato que usa writeUTF para letras como á, é, ñ).
     * Trunca sin partir un carácter a la mitad y rellena con espacios.
     */
    public static String formatearCadenaPorBytes(String cadena, int maxBytes) {
        if (cadena == null) {
            cadena = "";
        }
        if (maxBytes < 0) {
            throw new IllegalArgumentException("maxBytes no puede ser negativo");
        }

        StringBuilder resultado = new StringBuilder();
        int bytesUsados = 0;

        // Recorremos el texto agregando letra por letra sin sobrepasar el límite de bytes permitidos
        for (int i = 0; i < cadena.length(); ) {
            int codePoint = cadena.codePointAt(i);
            String caracter = new String(Character.toChars(codePoint));
            int bytesCaracter = longitudEnBytes(caracter);

            // Si agregar este carácter supera los bytes permitidos, detenemos el recorrido
            if (bytesUsados + bytesCaracter > maxBytes) {
                break;
            }

            resultado.append(caracter);
            bytesUsados += bytesCaracter;
            i += Character.charCount(codePoint);
        }

        // Rellenamos los bytes faltantes con espacios en blanco (cada espacio equivale a 1 byte en UTF-8)
        while (bytesUsados < maxBytes) {
            resultado.append(' ');
            bytesUsados++;
        }

        return resultado.toString();
    }

    /**
     * Compara dos nombres ignorando mayúsculas/minúsculas y espacios extremos.
     * Ej: "mAtematicas" y "MatematiCas" se consideran iguales.
     */
    public static boolean nombresIguales(String nombre1, String nombre2) {
        if (nombre1 == null || nombre2 == null) {
            return false;
        }
        return nombre1.trim().equalsIgnoreCase(nombre2.trim());
    }
}
