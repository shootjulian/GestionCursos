/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gestion_curso.utils;

/**
 *
 * @author Julim
 */
public class StringUtils {
    
    public static String formatearCadena(String cadena, int n){
        int faltan;
        
        if (cadena == null){
            cadena = "";
        }
        
        if (cadena.length() > n) { //Si la cadena es mayor a n
            return cadena.substring(0, n);
            
        } else{ //Si la cadena es menor a n
            faltan = n - cadena.length();
            
            return cadena + " ".repeat(faltan);
        }
    }
}
