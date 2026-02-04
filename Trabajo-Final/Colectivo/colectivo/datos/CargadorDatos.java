package colectivo.datos;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import colectivo.modelo.Parada;
import colectivo.modelo.Linea;
import net.datastructures.Map;
import net.datastructures.ChainHashMap; // chainhashmap es necesario porque en la simulacion insertamos y eliminamos pasajeros frecuentemente

/**
 * Clase utilitaria para cargar datos de líneas y paradas desde archivos de texto.
 */
public class CargadorDatos {

    /**
     * Carga las líneas de colectivos desde un archivo de texto.
     *
     * @param fileName nombre del archivo de líneas
     * @param paradas mapa de paradas ya cargadas, para asociar a cada línea
     * @return un HashMap con las líneas cargadas, donde la clave es el código de la línea
     * @throws FileNotFoundException si el archivo no existe
     */
    public static Map<String, Linea> cargarLineas(String fileName, Map<Integer, Parada> paradas) throws FileNotFoundException {
        Scanner read = new Scanner(new File(fileName));
        Map<String, Linea> lineas = new ChainHashMap<>(); // Mapa para almacenar las líneas cargadas
        // Itera sobre cada línea del archivo
        while (read.hasNextLine()) {
            String lineaTxt = read.nextLine().trim(); // Lee la línea y elimina espacios en blanco al inicio y al final
            if (lineaTxt.isEmpty() || lineaTxt.startsWith("#")) continue; // Ignora comentarios y líneas vacías
            Scanner lineaScanner = new Scanner(lineaTxt);
            lineaScanner.useDelimiter("\\s*;\\s*"); // Usa ´;´ como delimitador
            if (!lineaScanner.hasNext()) {
                lineaScanner.close(); // Cierra el scanner si no hay más paradas que cargar
                continue;
            }
            String codigo = lineaScanner.next(); // obtiene el código de la línea 
            Linea linea = new Linea(codigo); // Crea una nueva línea con ese código

            // Agregar paradas a la línea
            while (lineaScanner.hasNextInt()) {
                int idParada = lineaScanner.nextInt(); // Obtiene el id de la parada
                Parada parada = paradas.get(idParada); // Busca la parada en el mapa de paradas
                if (parada != null) {
                    linea.agregarParada(parada); // Si la parada existe, la agrega a la línea
                }
            }
            lineas.put(codigo, linea); // Agrega la línea al mapa de líneas
            lineaScanner.close(); 
        }
        read.close();
        return lineas; // Devuelve el mapa de líneas cargadas
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
    public static Map<Integer, Parada> cargarParadas(String fileName) throws FileNotFoundException {
        Scanner read;
        Map<Integer, Parada> paradas = new ChainHashMap<>(); // Mapa para almacenar las paradas cargadas
        read = new Scanner(new File(fileName)); // Crea un scanner para leer el archivo de paradas
        
        // Itera sobre cada línea del archivo
        while (read.hasNextLine()) {
            String lineaTxt = read.nextLine().trim(); // Lee la línea y elimina espacios en blanco al inicio y al final
            if (lineaTxt.isEmpty() || lineaTxt.startsWith("#")) continue; // Ignora comentarios y líneas vacías
            Scanner lineaScanner = new Scanner(lineaTxt); // Crea un scanner para procesar la línea actual
            lineaScanner.useDelimiter("\\s*;\\s*"); // Usa ´;´ como delimitador
            if (!lineaScanner.hasNext()) {
                lineaScanner.close();
                continue;
            }
            String idToken = lineaScanner.next(); // Obtiene el token del id de la parada
            try {
                int id = Integer.parseInt(idToken); // Convierte el token a un entero
                String direccion = lineaScanner.hasNext() ? lineaScanner.next() : ""; // Obtiene la dirección de la parada
                Parada parada = new Parada(id, direccion);
                paradas.put(id, parada); // Agrega la parada al mapa de paradas
            } catch (NumberFormatException e) {
                // Línea con id inválido: ignorar
            }
            lineaScanner.close();
        }
        read.close();
        return paradas; // Devuelve el mapa de paradas cargadas
    }

}
