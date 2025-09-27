package colectivo.modelo;

import java.util.List;
import java.util.ArrayList;

/**
 * Representa un colectivo que pertenece a una línea y transporta pasajeros.
 */
public class Colectivo {
    /** Línea a la que pertenece el colectivo. */
    private Linea linea;
    /** Lista de pasajeros que se encuentran actualmente en el colectivo. */
    private List<Pasajero> pasajeros;

    /**
     * Crea un nuevo colectivo asociado a una línea.
     * @param linea la línea a la que pertenece el colectivo
     */
    public Colectivo(Linea linea) {
        this.linea = linea;
        this.pasajeros = new ArrayList<>();
    }

    /**
     * Obtiene la línea a la que pertenece el colectivo.
     * @return la línea del colectivo
     */
    public Linea getLinea() { 
        return linea; 
    }

    /**
     * Obtiene la lista de pasajeros que están en el colectivo.
     * @return lista de pasajeros
     */
    public List<Pasajero> getPasajeros() {
        return pasajeros; 
    }

    /**
     * Sube un pasajero al colectivo.
     * @param p el pasajero a subir
     */
    public void subirPasajero(Pasajero p) { 
        pasajeros.add(p); 
    }

    /**
     * Baja un pasajero del colectivo.
     * @param p el pasajero a bajar
     */
    public void bajarPasajero(Pasajero p) { 
        pasajeros.remove(p); 
    }
}