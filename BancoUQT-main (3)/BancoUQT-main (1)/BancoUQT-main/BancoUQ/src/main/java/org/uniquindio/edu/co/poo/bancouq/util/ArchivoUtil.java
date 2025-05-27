package org.uniquindio.edu.co.poo.bancouq.util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ArchivoUtil {

    public static void escribirArchivo(String ruta, List<String> lineas) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ruta))) {
            for (String linea : lineas) {
                writer.write(linea);
                writer.newLine();
            }
        }
    }

    public static List<String> leerArchivo(String ruta) throws IOException {
        List<String> lineas = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(ruta))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                lineas.add(linea);
            }
        }
        return lineas;
    }
}