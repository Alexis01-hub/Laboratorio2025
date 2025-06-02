package colectivo.modelo;

import java.util.List;
import java.util.ArrayList;

public class Colectivo {
    private Linea linea;
    private List<Pasajero> pasajeros;

    public Colectivo(Linea linea) {
        this.linea = linea;
        this.pasajeros = new ArrayList<>();
    }

    public Linea getLinea() { return linea; }
    public List<Pasajero> getPasajeros() { return pasajeros; }

    public void subirPasajero(Pasajero p) { pasajeros.add(p); }
    public void bajarPasajero(Pasajero p) { pasajeros.remove(p); }
}