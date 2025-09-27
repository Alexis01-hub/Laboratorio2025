package colectivo.aplicacion;

import java.util.List;
import java.util.ArrayList;
import java.util.Properties;
import java.io.FileInputStream;

import colectivo.modelo.Colectivo;
import colectivo.datos.Dato;
import colectivo.modelo.Linea;
import colectivo.modelo.Parada;
import colectivo.modelo.Pasajero;
import colectivo.utils.GeneradorPasajeros;
import net.datastructures.TreeMap;

/**
 * Clase principal que ejecuta la simulación del sistema de colectivos.
 * Lee la configuración, genera pasajeros aleatorios, crea colectivos por línea
 * y simula el recorrido de cada colectivo mostrando subidas y bajadas de
 * pasajeros.
 */
public class Simulador {
    /**
     * Punto de entrada de la aplicación.
     * 
     * @param args argumentos de línea de comandos (no se utilizan)
     * @throws Exception si ocurre un error al leer archivos o procesar datos
     */
    public static void main(String[] args) throws Exception {
        // Leer configuración desde el archivo config.properties
        Properties config = new Properties();
        config.load(new FileInputStream("Colectivo/config.properties"));
        int cantidadPasajeros = Integer.parseInt(config.getProperty("cantidadPasajeros", "100")); // Lee la cantidad de
                                                                                                  // psasajeros
        String archivoParadas = config.getProperty("parada", "Colectivo/parada.txt"); // lee la ruta del archivo de paradas
        String archivoLineas = config.getProperty("linea", "Colectivo/linea.txt"); // lee la ruta del archivo de líneas

        // Cargar datos de paradas y líneas
        TreeMap<Integer, Parada> paradas = Dato.cargarParadas(archivoParadas); // Carga las paradas desde el archivo
        TreeMap<String, Linea> lineas = Dato.cargarLineas(archivoLineas, paradas); // Carga las líneas desde el archivo,
                                                                                   // asociando las paradas

        // Generar pasajeros aleatorios usando la cantidad definida en la configuración
        List<Pasajero> pasajeros = GeneradorPasajeros.generar(cantidadPasajeros, paradas);

        // Crear un colectivo por cada línea
        List<Colectivo> colectivos = new ArrayList<>(); // Lista para almacenar los colectivos creados
        for (Linea l : lineas.values()) {
            colectivos.add(new Colectivo(l)); // Crea un nuevo colectivo para cada línea y lo agrega a la lista
        }

        // Asignar pasajeros a colectivos solo si la línea contiene origen y destino
        for (Colectivo c : colectivos) {
            for (Pasajero p : pasajeros) {
                List<Parada> paradasLinea = c.getLinea().getParadas(); // Obtiene las paradas de la línea del colectivo
                if (paradasLinea.contains(p.getOrigen()) && paradasLinea.contains(p.getDestino())) {
                    c.subirPasajero(p); // Si el origen y destino del pasajero están en las paradas de la línea, lo sube
                                        // al colectivo
                }
            }
        }

        // Simular el recorrido de cada colectivo por su línea
        for (Colectivo c : colectivos) {
            System.out.println("\n============================================================");
            System.out.println("Recorrido colectivo línea: " + c.getLinea().getCodigo() +
                    " (paradas: " + c.getLinea().getParadas().size() + ")");
            List<Parada> paradasLinea = c.getLinea().getParadas(); // Obtiene las paradas de la línea del colectivo
            List<Pasajero> pasajerosEnColectivo = new ArrayList<>(); // Lista para llevar el registro de los pasajeros a
                                                                     // bordo del colectivo
            for (Parada parada : paradasLinea) {
                List<Pasajero> suben = new ArrayList<>(); // Lista para almacenar los pasajeros que suben en la parada
                                                          // actual
                // Pasajeros que suben en esta parada
                for (Pasajero p : new ArrayList<>(c.getPasajeros())) {
                    if (p.getOrigen().equals(parada)) {
                        suben.add(p); // Agrega el pasajero a la lista de suben si su origen coincide con la parada
                                      // actual
                        pasajerosEnColectivo.add(p); // Añade el pasajero a la lista de pasajeros a bordo del colectivo
                    }
                }
                List<Pasajero> bajan = new ArrayList<>(); // Lista para almacenar los pasajeros que bajan en la parada
                                                          // actual
                // Pasajeros que bajan en esta parada
                for (Pasajero p : new ArrayList<>(pasajerosEnColectivo)) {
                    if (p.getDestino().equals(parada)) {
                        bajan.add(p); // Agrega el pasajero a la lista de bajan si su destino coincide con la parada
                                      // actual
                        pasajerosEnColectivo.remove(p); // Elimina el pasajero de la lista de pasajeros a bordo del
                                                        // colectivo
                    }
                }
                System.out.println("------------------------------------------------------------");
                System.out.println("  Parada: " + parada.getDireccion());
                System.out.println("    Suben: " + suben.size());
                System.out.println("    Bajan: " + bajan.size());
                System.out.println("    Pasajeros a bordo: " + pasajerosEnColectivo.size());
            }
            // Al finalizar el recorrido, verificar si quedan pasajeros a bordo
            if (!pasajerosEnColectivo.isEmpty()) {
                System.out.println(">>> Atención: Quedaron " + pasajerosEnColectivo.size()
                        + " pasajeros a bordo al finalizar el recorrido. Fueron bajados forzosamente.");
                pasajerosEnColectivo.clear();
            }
            System.out.println("============================================================\n");
        }
    }
}