package colectivo.aplicacion;

import java.util.List;
import java.util.ArrayList;
import java.util.Properties;


import colectivo.modelo.Colectivo;
import colectivo.datos.CargadorDatos;
import colectivo.modelo.Linea;
import colectivo.modelo.Parada;
import colectivo.modelo.Pasajero;
import colectivo.utils.GeneradorPasajeros;
import colectivo.datos.CargarParametros;
import net.datastructures.Map;
import colectivo.utils.ImprimirRecorrido;

/**
 * Clase principal que ejecuta la simulación del sistema de colectivos.
 * Lee la configuración, genera pasajeros aleatorios, crea colectivos por línea
 * y simula el recorrido de cada colectivo mostrando subidas y bajadas de
 * pasajeros.
 */
public class Simulador {
    public static void main(String[] args) throws Exception {

        // Leer configuración desde el archivo config.properties
        Properties config = CargarParametros.cargar();
        int cantidadPasajeros = Integer.parseInt(config.getProperty("cantidadPasajeros", "100"));
        String archivoParadas = config.getProperty("parada", "Colectivo/parada.txt");
        String archivoLineas = config.getProperty("linea", "Colectivo/linea.txt");

        // Cargar datos de paradas y líneas
        Map<Integer, Parada> paradas = CargadorDatos.cargarParadas(archivoParadas); // Carga las paradas desde el archivo
        Map<String, Linea> lineas = CargadorDatos.cargarLineas(archivoLineas, paradas); // Carga las líneas desde el archivo, asociando las paradas

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
                List<Parada> paradasDeLaLinea = c.getLinea().getParadas(); // Obtiene las paradas de la línea del colectivo
                if (paradasDeLaLinea.contains(p.getOrigen()) && paradasDeLaLinea.contains(p.getDestino())) {
                    c.subirPasajero(p); // Si el origen y destino del pasajero están en las paradas de la línea, lo sube
                }
            }
        }

        // Mostrar las líneas disponibles y pedir al usuario que seleccione una
        ImprimirRecorrido.imprimirRecorrido(colectivos, lineas);
        System.out.println("Simulación finalizada.");
    }
}