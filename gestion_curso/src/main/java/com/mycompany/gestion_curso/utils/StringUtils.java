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

    // Calcula cuántos bytes reales ocupa un texto en codificación UTF-8
    // (Útil porque caracteres especiales como la 'ñ' o tildes ocupan 2 bytes en lugar de 1)
    public static int longitudEnBytes(String cadena) {
        if (cadena == null) {
            return 0;
        }
        return cadena.getBytes(StandardCharsets.UTF_8).length;
    }

    // Ajusta un texto a un tamaño exacto de bytes para escribir en el archivo sin romper letras con tilde o 'ñ'
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

    // Compara si dos textos son iguales ignorando mayúsculas, minúsculas y espacios al inicio o al final
    public static boolean nombresIguales(String nombre1, String nombre2) {
        if (nombre1 == null || nombre2 == null) {
            return false;
        }
        return nombre1.trim().equalsIgnoreCase(nombre2.trim());
    }
}