package com.jonathan.delimitadores.test;

import com.jonathan.delimitadores.Delimitador;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Test {
    public static void main(String[] args) {
        File entrada = new File("C:\\Users\\Jonathan Hernandez\\Dropbox\\CAPACITACION_BUSIEASE\\datos.txt");
        File salida = new File("C:/Users/Jonathan Hernandez/Desktop/");
        Delimitador del = new Delimitador(entrada, salida);
        try {
            del.delimitarConIntervalos(",", true ,8,6,6,12,2,1,12,19,4,2,6,8,11,4,3,1,4,9,15,95);
        } catch (NullPointerException | IOException ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, "ocurrió un error al aprsear el archivo", ex.getMessage());
        }
        System.out.println("                                                            j".matches("([\\w]*|[\\s]*)*\\w+|(([\\w]*|[\\s]*)*\\w+\\s*)+$"));
    }

}