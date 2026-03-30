package colectivo.utils;

import colectivo.modelo.Linea;
import colectivo.modelo.Parada;
import colectivo.modelo.Pasajero;
import net.datastructures.Map;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

/**
 * Clase utilitaria para generar pasajeros aleatorios para la simulación.
 * Permite crear pasajeros con orígenes y destinos válidos basados en las líneas y paradas disponibles.
 */
public class GeneradorPasajeros {

    /**
     * Genera una lista de pasajeros con orígenes y destinos aleatorios.
     * IMPORTANTE: El destino siempre será DESPUÉS del origen en la lista de paradas.
     * Esto asegura que todos los pasajeros generados sean válidos para alguna línea.
     *
     * @param cantidad la cantidad de pasajeros a generar.
     * @param lineas el mapa de líneas disponibles, donde la clave es el código de la línea y el valor es la línea.
     * @return una lista de pasajeros generados aleatoriamente.
     */
    public static List<Pasajero> generarDesdeLineas(int cantidad, Map<String, Linea> lineas) {
        List<Pasajero> pasajeros = new ArrayList<>();
        if (cantidad <= 0 || lineas == null) {
            return pasajeros;
        }

        List<Linea> lineasValidas = new ArrayList<>();
        for (Linea l : lineas.values()) {
            if (l != null && l.getParadas() != null && l.getParadas().size() >= 2) {
                lineasValidas.add(l);
            }
        }

        if (lineasValidas.isEmpty()) {
            return pasajeros;
        }

        Random rand = new Random();

        for (int i = 0; i < cantidad; i++) {
            Linea lineaElegida = lineasValidas.get(rand.nextInt(lineasValidas.size()));
            List<Parada> paradasLinea = lineaElegida.getParadas();

            int indexOrigen = rand.nextInt(paradasLinea.size() - 1);
            int indexDestino = indexOrigen + 1 + rand.nextInt(paradasLinea.size() - indexOrigen - 1);

            Parada origen = paradasLinea.get(indexOrigen);
            Parada destino = paradasLinea.get(indexDestino);

            pasajeros.add(new Pasajero(i, origen, destino, false, lineaElegida.getCodigo()));
        }

        return pasajeros;
    }

    /**
     * Metodo de compatibilidad para tests previos.
     * Genera pasajeros utilizando una lista de paradas ordenada por ID.
     *
     * @param cantidad la cantidad de pasajeros a generar.
     * @param paradas el mapa de paradas disponibles, donde la clave es el ID de la parada y el valor es la parada.
     * @return una lista de pasajeros generados aleatoriamente.
     */
    public static List<Pasajero> generar(int cantidad, Map<Integer, Parada> paradas) {
        List<Pasajero> pasajeros = new ArrayList<>();
        if (cantidad <= 0 || paradas == null) {
            return pasajeros;
        }

        List<Parada> listaParadas = new ArrayList<>();
        for (Parada p : paradas.values()) {
            if (p != null) {
                listaParadas.add(p);
            }
        }

        if (listaParadas.size() < 2) {
            return pasajeros;
        }

        listaParadas.sort(Comparator.comparingInt(Parada::getId));

        Random rand = new Random();
        for (int i = 0; i < cantidad; i++) {
            int indexOrigen = rand.nextInt(listaParadas.size() - 1);
            int indexDestino = indexOrigen + 1 + rand.nextInt(listaParadas.size() - indexOrigen - 1);

            Parada origen = listaParadas.get(indexOrigen);
            Parada destino = listaParadas.get(indexDestino);

            pasajeros.add(new Pasajero(i, origen, destino, false));
        }

        return pasajeros;
    }
}
