/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.gestion_curso;

import com.mycompany.gestion_curso.utils.StringUtils;

/**
 *
 * @author Julim
 * 
 * //
 */

//Clase principal
public class GestionCurso {

    public static void main(String[] args) {
        String nombre = "pepe el grillo akd askdnsakdaskdmaskmdakdmsakdmaskd julian";
        
        nombre = StringUtils.formatearCadena(nombre, 31);
        
        System.out.println(nombre);
        System.out.println(nombre.length());
    }
}
