package colectivo.modelo;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import net.datastructures.ChainHashMap;

/**
 * Representa un colectivo que realiza un recorrido en una línea específica.
 * Contiene información sobre su capacidad, ubicación actual, y pasajeros a bordo.
 */
public class Colectivo {
    private Linea linea;
    private List<Pasajero> pasajeros;
    private int cantidadMaxima;
    private String id;
    private ChainHashMap<Integer, List<Integer>> subidasPorParada;
    private ChainHashMap<Integer, List<Integer>> bajadasPorParada;

    // clave: indice de tramo (0 = P1-P2, 1 = P2-P3, ...)
    private ChainHashMap<Integer, Integer> pasajerosPorTramo;

    /**
     * Constructor que inicializa un colectivo con los datos básicos.
     *
     * @param capacidad Capacidad máxima de pasajeros del colectivo.
     * @param linea Línea a la que pertenece el colectivo.
     */
    public Colectivo(int capacidad, Linea linea) {
        this.linea = linea;
        this.pasajeros = new ArrayList<>();
        this.cantidadMaxima = capacidad;
        this.id = null;
        this.pasajerosPorTramo = new ChainHashMap<>();
        this.subidasPorParada = new ChainHashMap<>();
        this.bajadasPorParada = new ChainHashMap<>();
    }

    public Linea getLinea() {
        return linea;
    }

    public List<Pasajero> getPasajeros() {
        return pasajeros;
    }

    public boolean subirPasajero(Pasajero pasajero) {
        if (estaLleno()) {
            System.out.println("No se pudo subir al pasajero " + pasajero.getId() + " porque el colectivo está lleno.");
            return false;
        } else {
            // Añade el pasajero a la lista de pasajeros del colectivo a bordo
            pasajeros.add(pasajero);
            return true;
        }
    }

    /**
     * Baja un pasajero del colectivo en la parada actual.
     *
     * @param pasajero Pasajero que desea bajar del colectivo.
     */
    public void bajarPasajero(Pasajero pasajero) {
        // baja el pasajero de la lista de pasajeros del colectivo a bordo
        pasajeros.remove(pasajero);
        pasajeros.remove(pasajero);
    }

    /**
     * Devuelve una representación en cadena del colectivo.
     *
     * @return Cadena con los datos del colectivo.
     */
    @Override
    public String toString() {
        return "Colectivo{" +
                "linea=" + linea +
                ", pasajeros=" + pasajeros +
                ", cantidadMaxima=" + cantidadMaxima +
                ", id='" + id + '\'' +
                '}';
    }

    /**
     * Verifica si el colectivo está lleno.
     *
     * @return true si el colectivo está lleno, false en caso contrario.
     */
    public boolean estaLleno() {
        return pasajeros.size() >= cantidadMaxima;
    }

    public int getCantidadMaxima() {
        return cantidadMaxima;
    }

    /**
     * Registra la cantidad de pasajeros que estuvieron a bordo durante un tramo específico del recorrido.
     *
     * @param tramoIndex Índice del tramo en el recorrido.
     * @param cantidadPasajeros Cantidad de pasajeros en el tramo.
     */
    public void registrarPasajerosEnTramo(int tramoIndex, int cantidadPasajeros) {
        if (tramoIndex < 0) return;
        if (cantidadPasajeros < 0) cantidadPasajeros = 0;
        pasajerosPorTramo.put(tramoIndex, cantidadPasajeros);
    }

    /**
     * Obtiene la cantidad de pasajeros que estuvieron a bordo durante un tramo específico del recorrido.
     *
     * @param tramoIndex Índice del tramo en el recorrido.
     * @return Cantidad de pasajeros en el tramo.
     */
    public int getPasajerosEnTramo(int tramoIndex) {
        Integer valor = pasajerosPorTramo.get(tramoIndex);
        return (valor == null) ? 0 : valor;
    }

    /**
     * Establece el identificador del colectivo.
     *
     * @param id Identificador del colectivo.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Obtiene el identificador del colectivo.
     *
     * @return Identificador del colectivo.
     */
    public String getId() {
        return id;
    }

    /**
     * Registra la subida de un pasajero en una parada específica.
     *
     * @param paradaIndex Índice de la parada.
     * @param pasajero Pasajero que subió.
     */
    public void registrarSubidaEnParada(int paradaIndex, Pasajero pasajero) {
        List<Integer> ids = subidasPorParada.get(paradaIndex);
        if (ids == null) {
            ids = new ArrayList<>();
            subidasPorParada.put(paradaIndex, ids);
        }
        ids.add(pasajero.getId());
    }

    /**
     * Registra la bajada de un pasajero en una parada específica.
     *
     * @param paradaIndex Índice de la parada.
     * @param pasajero Pasajero que bajó.
     */
    public void registrarBajadaEnParada(int paradaIndex, Pasajero pasajero) {
        List<Integer> ids = bajadasPorParada.get(paradaIndex);
        if (ids == null) {
            ids = new ArrayList<>();
            bajadasPorParada.put(paradaIndex, ids);
        }
        ids.add(pasajero.getId());
    }

    /**
     * Obtiene la lista de IDs de pasajeros que subieron en una parada específica.
     *
     * @param paradaIndex Índice de la parada.
     * @return Lista de IDs de pasajeros que subieron.
     */
    public List<Integer> getSubidasEnParada(int paradaIndex) {
        List<Integer> ids = subidasPorParada.get(paradaIndex);
        return ids == null ? Collections.emptyList() : ids;
    }

    /**
     * Obtiene la lista de IDs de pasajeros que bajaron en una parada específica.
     *
     * @param paradaIndex Índice de la parada.
     * @return Lista de IDs de pasajeros que bajaron.
     */
    public List<Integer> getBajadasEnParada(int paradaIndex) {
        List<Integer> ids = bajadasPorParada.get(paradaIndex);
        return ids == null ? Collections.emptyList() : ids;
    }

}
