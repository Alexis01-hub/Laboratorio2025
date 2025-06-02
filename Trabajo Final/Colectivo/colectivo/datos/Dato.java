package colectivo.datos;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import net.datastructures.*;
import colectivo.modelo.Parada;
import colectivo.modelo.Linea;

/**
 * Clase utilitaria para cargar datos de líneas y paradas desde archivos de texto.
 */
public class Dato {

    /**
     * Carga las líneas de colectivos desde un archivo de texto.
     * Cada línea del archivo debe tener el formato:
     * <pre>
     * codigoLinea;idParada1;idParada2;...;idParadaN;
     * </pre>
     * donde {@code codigoLinea} es el identificador de la línea y cada {@code idParada} corresponde a una parada existente.
     *
     * @param fileName nombre del archivo de líneas
     * @param paradas mapa de paradas ya cargadas, para asociar a cada línea
     * @return un TreeMap con las líneas cargadas, donde la clave es el código de línea
     * @throws FileNotFoundException si el archivo no existe
     */
    public static TreeMap<String, Linea> cargarLineas(String fileName, TreeMap<Integer, Parada> paradas) throws FileNotFoundException {
        Scanner read = new Scanner(new File(fileName));
        TreeMap<String, Linea> lineas = new TreeMap<>();
        while (read.hasNextLine()) {
            String lineaTxt = read.nextLine().trim();
            if (lineaTxt.isEmpty() || lineaTxt.startsWith("#")) continue;
            Scanner lineaScanner = new Scanner(lineaTxt);
            lineaScanner.useDelimiter("\\s*;\\s*");
            String codigo = lineaScanner.next();
            Linea linea = new Linea(codigo);
            // Agregar paradas a la línea
            while (lineaScanner.hasNextInt()) {
                int idParada = lineaScanner.nextInt();
                Parada parada = paradas.get(idParada);
                if (parada != null) {
                    linea.agregarParada(parada);
                }
            }
            lineas.put(codigo, linea);
            lineaScanner.close();
        }
        read.close();
        return lineas;
    }

    /**
     * Carga las paradas de colectivos desde un archivo de texto.
     * Cada línea del archivo debe tener el formato:
     * <pre>
     * idParada;direccion;
     * </pre>
     * donde {@code idParada} es el identificador numérico de la parada y {@code direccion} es su dirección.
     * Las líneas vacías o que comienzan con '#' se ignoran.
     *
     * @param fileName nombre del archivo de paradas
     * @return un TreeMap con las paradas cargadas, donde la clave es el id de la parada
     * @throws FileNotFoundException si el archivo no existe
     */
    public static TreeMap<Integer, Parada> cargarParadas(String fileName) throws FileNotFoundException {
        Scanner read;
        TreeMap<Integer, Parada> paradas = new TreeMap<>();
        read = new Scanner(new File(fileName));
        while (read.hasNextLine()) {
            String lineaTxt = read.nextLine().trim();
            if (lineaTxt.isEmpty() || lineaTxt.startsWith("#")) continue; // Ignora comentarios y líneas vacías
            Scanner lineaScanner = new Scanner(lineaTxt);
            lineaScanner.useDelimiter("\\s*;\\s*");
            int id = Integer.parseInt(lineaScanner.next());
            String direccion = lineaScanner.next();
            // No se asocia a ninguna línea aquí
            Parada parada = new Parada(id, direccion);
            paradas.put(id, parada);
            lineaScanner.close();
        }
        read.close();
        return paradas;
    }

}
