package com.jonathan.delimitadores;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

//comment
public class Delimitador {

    /* Declaring vars */
    private File archivoEntrada;
    private File archivoSalida;
    private StringBuilder sbLinea;
    private StringBuilder sbSalida;

    private FileInputStream fileInputStream;
    private FileWriter fileWriter;
    private BufferedWriter bufferedWriter;
    private Scanner scanner;
    
    /*acceso privado para obligar que se use el segundo constructor*/
    private Delimitador() {
    }
    
    /* constructor utilizado para agragar los archivos de entrada y salida */
    public Delimitador(File archivoEntrada, File archivoSalida) {
        this.archivoEntrada = archivoEntrada;
        this.archivoSalida = archivoSalida;
    }
    
    /* delimita cada linea con los intervalos agregados y un delimitador en especifico */
    public void delimitarConIntervalos(final String delimitador, boolean ignoreFirstLine, int... intervalos) throws NullPointerException, IOException, FileNotFoundException {

        if (delimitador != null && intervalos != null && archivoEntrada != null && archivoSalida != null) {

            fileInputStream = new FileInputStream(archivoEntrada);
            scanner = new Scanner(fileInputStream, "UTF-8");
            sbLinea = new StringBuilder();
            sbSalida = new StringBuilder();

            if (ignoreFirstLine) {
                scanner.nextLine();
            }

            int sumaIndice;
            int sumaLongitud;
            //int contador = 0;
            String storeDay = getStoreDay();
            int sumaIntervalos = sumaIntervalos(intervalos);

            while (scanner.hasNext()) {
                sumaIndice = 0;
                sumaLongitud = intervalos[0];

                sbLinea.delete(0, sbLinea.length());
                sbLinea.append(scanner.nextLine());
                completarString(sbLinea, sumaIntervalos);

                for (int i = 0; i < intervalos.length; i++) {
                    sbSalida.append(sbLinea.substring(sumaIndice, sumaLongitud)).append(delimitador);

                    sumaIndice += intervalos[i];
                    if ((i + 1) < intervalos.length) {
                        sumaLongitud += intervalos[i + 1];
                    }
                }

                sbSalida.append(storeDay).append(delimitador);

                if (scanner.hasNextLine()) {
                    sbSalida.append("\n");
                }

                //System.out.println(contador+"--"+sbLinea);
                //contador++;
            }
            System.out.println(sbSalida.toString());
            scanner.close();

            fileWriter = new FileWriter(archivoSalida);
            bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(sbSalida.toString());
            bufferedWriter.close();
            fileWriter.close();

        } else {
            throw new NullPointerException("Argumentos sin referencia");
        }
    }
    
    
    
    /* suma los intervalos que es la longitud de la cadena */
    private int sumaIntervalos(int... intervalos) {
        int suma = 0;
        for (int intervalo : intervalos) {
            suma += intervalo;
        }
        return suma;
    }
    
    /*obtiene la fecha parseada a un formato en especifico, para agregarla al archivo de salida*/
    public static String getStoreDay() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String fechaFormat = sdf.format(new Date());

        return fechaFormat;
    }
    
    /*hace la cadena al tamaño total de la suma de los intervalos */
    private void completarString(StringBuilder sb, int max) {
        int total = max - sb.length();
        for (int i = 0; i < total; i++) {
            sb.append(" ");
        }
    }
    
    public File getArchivoEntrada() {
        return archivoEntrada;
    }
    public void setArchivoEntrada(File archivoEntrada) {
        this.archivoEntrada = archivoEntrada;
    }
    public File getArchivoSalida() {
        return archivoSalida;
    }
    public void setArchivoSalida(File archivoSalida) {
        this.archivoSalida = archivoSalida;
    }
}