/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import org.apache.commons.lang3.math.NumberUtils;
/**
 *
 * @author frani
 */
public class Validation {
    
    public static boolean validarCedula(String numero) {   
        try {
            validarInicial(numero, 10);
            validarCodigoProvincia(numero.substring(0, 2));
            algoritmoModulo10(numero, Integer.parseInt(String.valueOf(numero.charAt(9))));
        } catch (Exception e) {
            e.printStackTrace();
            return false; 
        }

        return true;
    }
    
    private static boolean validarInicial(String numero, int caracteres) throws Exception {   
        if (! NumberUtils.isDigits(numero)) {
            throw new Exception("Valor ingresado solo puede tener dígitos");
        }
        
        if (numero.length() != caracteres) {
            throw new Exception("Valor ingresado debe tener "+ caracteres + " caracteres");
        }

        return true;
    }
    
    private static boolean validarCodigoProvincia(String numero) throws Exception {
        if (Integer.parseInt(numero) < 0 || Integer.parseInt(numero) > 24) {
            throw new Exception("Codigo de Provincia (dos primeros dígitos) no deben ser mayor a 24 ni menores a 0");
        }
        
        return true;
    }
    
    private static boolean algoritmoModulo10(String digitosIniciales, int digitoVerificador) throws Exception {
        Integer [] arrayCoeficientes = new Integer[]{2,1,2,1,2,1,2,1,2};
        
        Integer [] digitosInicialesTMP = new Integer[digitosIniciales.length()];
        int indice=0;
        for(char valorPosicion: digitosIniciales.toCharArray()) {
            digitosInicialesTMP[indice] = NumberUtils.createInteger(String.valueOf(valorPosicion));
            indice++;
        }
        
        int total = 0;
        int key = 0;
        for(Integer valorPosicion: digitosInicialesTMP) {
            if(key<arrayCoeficientes.length) {
        	valorPosicion = (digitosInicialesTMP[key] * arrayCoeficientes[key] );	
        	if (valorPosicion >= 10) {
                    char[] valorPosicionSplit = String.valueOf(valorPosicion).toCharArray();
                    valorPosicion = (Integer.parseInt(String.valueOf(valorPosicionSplit[0]))) + (Integer.parseInt(String.valueOf(valorPosicionSplit[1])));
                }
                total = total + valorPosicion;
            }
            key++;
        }
        
        int residuo =  total % 10;
        int resultado ;

        if (residuo == 0) {
            resultado = 0;        
        } else {
            resultado = 10 - residuo;
        }

        if (resultado != digitoVerificador) {
            throw new Exception("Dígitos iniciales no validan contra Dígito Idenficador");
        }

        return true;
    }
    
}
