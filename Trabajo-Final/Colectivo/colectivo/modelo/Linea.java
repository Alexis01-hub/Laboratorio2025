package colectivo.modelo;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa una línea de colectivo, identificada por un código y una lista de paradas.
 */
public class Linea {
    private String codigo;
    List<Parada> paradas;

    /**
     * Crea una nueva línea con el código especificado.
     *
     * @param codigo Código identificador de la línea.
     */
    public Linea(String codigo){
        this.codigo = codigo;
        this.paradas = new ArrayList<>();
    }

    /**
     * Agrega una parada a la lista de paradas de la línea.
     *
     * @param parada Parada a agregar.
     */
    public void agregarParada(Parada parada) {
        this.paradas.add(parada);
    }

    /**
     * Obtiene el código de la línea.
     *
     * @return Código de la línea.
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * Obtiene la lista de paradas de la línea.
     *
     * @return Lista de paradas.
     */
    public List<Parada> getParadas() {
        return paradas;
    }

    /**
     * Devuelve una representación en cadena de la línea.
     *
     * @return Cadena con los datos de la línea.
     */
    @Override
    public String toString() {
        return "Linea [codigo=" + codigo + ", paradas=" + paradas + "]";
    }
}
