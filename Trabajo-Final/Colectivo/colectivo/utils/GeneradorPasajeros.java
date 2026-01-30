package colectivo.utils;

import colectivo.modelo.Parada;
import colectivo.modelo.Pasajero;
import net.datastructures.Map;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Clase utilitaria para generar pasajeros aleatorios para la simulación.
 */
public class GeneradorPasajeros {

    /**
     * Genera una lista de pasajeros con orígenes y destinos aleatorios.
     * El origen y el destino de cada pasajero serán diferentes y seleccionados
     * de entre las paradas disponibles.
     *
     * @param cantidad la cantidad de pasajeros a generar
     * @param paradas  el mapa de paradas disponibles
     * @return una lista de pasajeros generados aleatoriamente
     */
    public static List<Pasajero> generar(int cantidad, Map<Integer, Parada> paradas) {
        List<Pasajero> pasajeros = new ArrayList<>(); // Lista para almacenar los pasajeros generados
        List<Parada> listaParadas = new ArrayList<>(); // Lista auxiliar para seleccionar orígenes y destinos aleatorios
        for (Parada p : paradas.values()) { // Itera sobre las paradas del mapa
            listaParadas.add(p); // Agrega cada parada a la lista auxiliar
        }
        Random rand = new Random();
        for (int i = 0; i < cantidad; i++) {    // Genera la cantidad de pasajeros especificada
            Parada origen = listaParadas.get(rand.nextInt(listaParadas.size())); // Selecciona un origen aleatorio de la lista de paradas
            Parada destino; 
            do {
                destino = listaParadas.get(rand.nextInt(listaParadas.size())); // Selecciona un destino aleatorio de la lista de paradas
            } while (destino.equals(origen)); // repite hasta que el destino sea diferente del origen
            pasajeros.add(new Pasajero(i, origen, destino)); // Crea un nuevo pasajero con el id, origen y destino seleccionados
        }
        return pasajeros; // Devuelve la lista de pasajeros generados
    }
}
