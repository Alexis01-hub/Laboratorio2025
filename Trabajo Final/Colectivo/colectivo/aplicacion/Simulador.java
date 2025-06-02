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
 * y simula el recorrido de cada colectivo mostrando subidas y bajadas de pasajeros.
 */
public class Simulador {
    /**
     * Punto de entrada de la aplicación.
     * @param args argumentos de línea de comandos (no se utilizan)
     * @throws Exception si ocurre un error al leer archivos o procesar datos
     */
    public static void main(String[] args) throws Exception {
        // Leer configuración desde el archivo config.properties
        Properties config = new Properties();
        config.load(new FileInputStream("config.properties"));
        int cantidadPasajeros = Integer.parseInt(config.getProperty("cantidadPasajeros", "100"));
        String archivoParadas = config.getProperty("parada", "parada.txt");
        String archivoLineas = config.getProperty("linea", "linea.txt");

        // Cargar datos de paradas y líneas
        TreeMap<Integer, Parada> paradas = Dato.cargarParadas(archivoParadas);
        TreeMap<String, Linea> lineas = Dato.cargarLineas(archivoLineas, paradas);

        // Generar pasajeros aleatorios usando la cantidad definida en la configuración
        List<Pasajero> pasajeros = GeneradorPasajeros.generar(cantidadPasajeros, paradas);

        // Crear un colectivo por cada línea
        List<Colectivo> colectivos = new ArrayList<>();
        for (Linea l : lineas.values()) {
            colectivos.add(new Colectivo(l));
        }

        // Asignar pasajeros a colectivos solo si la línea contiene origen y destino
        for (Colectivo c : colectivos) {
            for (Pasajero p : pasajeros) {
                List<Parada> paradasLinea = c.getLinea().getParadas();
                if (paradasLinea.contains(p.getOrigen()) && paradasLinea.contains(p.getDestino())) {
                    c.subirPasajero(p);
                }
            }
        }

        // Simular el recorrido de cada colectivo por su línea
        for (Colectivo c : colectivos) {
            System.out.println("\n============================================================");
            System.out.println("Recorrido colectivo línea: " + c.getLinea().getCodigo() +
                " (paradas: " + c.getLinea().getParadas().size() + ")");
            List<Parada> paradasLinea = c.getLinea().getParadas();
            List<Pasajero> pasajerosEnColectivo = new ArrayList<>();
            for (Parada parada : paradasLinea) {
                List<Pasajero> suben = new ArrayList<>();
                // Pasajeros que suben en esta parada
                for (Pasajero p : new ArrayList<>(c.getPasajeros())) {
                    if (p.getOrigen().equals(parada)) {
                        suben.add(p);
                        pasajerosEnColectivo.add(p);
                    }
                }
                List<Pasajero> bajan = new ArrayList<>();
                // Pasajeros que bajan en esta parada
                for (Pasajero p : new ArrayList<>(pasajerosEnColectivo)) {
                    if (p.getDestino().equals(parada)) {
                        bajan.add(p);
                        pasajerosEnColectivo.remove(p);
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
                System.out.println(">>> Atención: Quedaron " + pasajerosEnColectivo.size() + " pasajeros a bordo al finalizar el recorrido. Fueron bajados forzosamente.");
                pasajerosEnColectivo.clear();
            }
            System.out.println("============================================================\n");
        }
    }
}