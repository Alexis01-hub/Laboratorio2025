package colectivo.utils;

import colectivo.modelo.Parada;
import colectivo.modelo.Pasajero;
import net.datastructures.TreeMap;

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
    public static List<Pasajero> generar(int cantidad, TreeMap<Integer, Parada> paradas) {
        List<Pasajero> pasajeros = new ArrayList<>();
        List<Parada> listaParadas = new ArrayList<>();
        for (Parada p : paradas.values()) {
            listaParadas.add(p);
        }
        Random rand = new Random();
        for (int i = 0; i < cantidad; i++) {
            Parada origen = listaParadas.get(rand.nextInt(listaParadas.size()));
            Parada destino;
            do {
                destino = listaParadas.get(rand.nextInt(listaParadas.size()));
            } while (destino.equals(origen));
            pasajeros.add(new Pasajero(i, origen, destino));
        }
        return pasajeros;
    }
}
