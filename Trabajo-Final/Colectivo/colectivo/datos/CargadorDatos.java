package colectivo.datos;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import colectivo.modelo.Parada;
import colectivo.modelo.Linea;
import net.datastructures.Map;
import net.datastructures.ChainHashMap; // chainhashmap es necesario porque en la simulacion insertamos y eliminamos pasajeros frecuentemente

/**
 * Clase encargada de cargar los datos necesarios para la simulación del sistema de transporte público.
 * Proporciona métodos para cargar líneas, paradas y otros datos relevantes desde archivos o fuentes externas.
 */
public class CargadorDatos {

    /**
     * Carga las líneas de transporte público desde un archivo de texto.
     *
     * @param archivo El archivo de texto que contiene los datos de las líneas.
     * @param paradas Mapa de paradas ya cargadas, para asociar a cada línea.
     * @return Un HashMap con las líneas cargadas, donde la clave es el código de la línea.
     * @throws FileNotFoundException Si el archivo no existe o no se puede leer.
     */
    public static Map<String, Linea> cargarLineas(String archivo, Map<Integer, Parada> paradas) throws FileNotFoundException {
        Scanner read = new Scanner(new File(archivo));
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
     * Carga las paradas de transporte público desde un archivo de texto.
     *
     * @param archivo El archivo de texto que contiene los datos de las paradas.
     * @return Un HashMap con las paradas cargadas, donde la clave es el ID de la parada.
     * @throws FileNotFoundException Si el archivo no existe o no se puede leer.
     */
    public static Map<Integer, Parada> cargarParadas(String archivo) throws FileNotFoundException {
        Scanner read = new Scanner(new File(archivo)); // Crea un scanner para leer el archivo de paradas;
        Map<Integer, Parada> paradas = new ChainHashMap<>(); // Mapa para almacenar las paradas cargadas
        
        // Itera sobre cada línea del archivo
        while (read.hasNextLine()) {
            String lineaTxt = read.nextLine().trim(); // Lee la línea y elimina espacios en blanco al inicio y al final
            if (lineaTxt.isEmpty() || lineaTxt.startsWith("#")) continue; // Ignora comentarios y líneas vacías
            Scanner lineaScanner = new Scanner(lineaTxt); // Crea un scanner para procesar la línea actual
            lineaScanner.useDelimiter("\\s*;\\s*"); // Usa ´;´ como delimitador
            if (!lineaScanner.hasNext()) {
                lineaScanner.close(); // Cierra el scanner si no hay más datos que cargar
                continue;
            }
            String idParada = lineaScanner.next(); // Obtiene el token del id de la parada
            try {
                int id = Integer.parseInt(idParada); // Convierte el id de la parada a un entero
                String direccion = lineaScanner.hasNext() ? lineaScanner.next() : ""; // Obtiene la dirección de la parada y maneja el caso donde no hay dirección
                Parada parada = new Parada(id, direccion);
                paradas.put(id, parada); // Agrega la parada al mapa de paradas
            } catch (NumberFormatException e) {
                // Línea con id inválido, se puede registrar un error o ignorar
                System.out.println("ID de parada inválido: " + idParada);
            }
            lineaScanner.close();
        }
        read.close();
        return paradas; // Devuelve el mapa de paradas cargadas
    }

}
