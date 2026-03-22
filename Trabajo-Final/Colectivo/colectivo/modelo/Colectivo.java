package colectivo.modelo;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import net.datastructures.ChainHashMap;

/**
 * Representa un colectivo que pertenece a una línea y transporta pasajeros.
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

    public Colectivo(Linea linea, int cantidadMaxima) {
        this.linea = linea;
        this.pasajeros = new ArrayList<>();
        this.cantidadMaxima = cantidadMaxima;
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

    public boolean subirPasajero(Pasajero p) {
        if (estaLleno()) {
            System.out.println("No se pudo subir al pasajero " + p.getId() + " porque el colectivo está lleno.");
            return false;
        } else {
            pasajeros.add(p);
            return true;
        }
    }

    public void bajarPasajero(Pasajero p) {
        pasajeros.remove(p);
    }

    public boolean estaLleno() {
        return pasajeros.size() >= cantidadMaxima;
    }

    public int getCantidadMaxima() {
        return cantidadMaxima;
    }

    public void registrarPasajerosEnTramo(int tramoIndex, int cantidadPasajeros) {
        if (tramoIndex < 0) return;
        if (cantidadPasajeros < 0) cantidadPasajeros = 0;
        pasajerosPorTramo.put(tramoIndex, cantidadPasajeros);
    }

    public int getPasajerosEnTramo(int tramoIndex) {
        Integer valor = pasajerosPorTramo.get(tramoIndex);
        return (valor == null) ? 0 : valor;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void registrarSubidaEnParada(int paradaIndex, Pasajero pasajero) {
        List<Integer> ids = subidasPorParada.get(paradaIndex);
        if (ids == null) {
            ids = new ArrayList<>();
            subidasPorParada.put(paradaIndex, ids);
        }
        ids.add(pasajero.getId());
    }

    public void registrarBajadaEnParada(int paradaIndex, Pasajero pasajero) {
        List<Integer> ids = bajadasPorParada.get(paradaIndex);
        if (ids == null) {
            ids = new ArrayList<>();
            bajadasPorParada.put(paradaIndex, ids);
        }
        ids.add(pasajero.getId());
    }

    public List<Integer> getSubidasEnParada(int paradaIndex) {
        List<Integer> ids = subidasPorParada.get(paradaIndex);
        return ids == null ? Collections.emptyList() : ids;
    }

    public List<Integer> getBajadasEnParada(int paradaIndex) {
        List<Integer> ids = bajadasPorParada.get(paradaIndex);
        return ids == null ? Collections.emptyList() : ids;
    }

}
