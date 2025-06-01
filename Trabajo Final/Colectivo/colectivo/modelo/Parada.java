package Colectivo.colectivo.modelo;

/**
 * Representa una parada del colectivo, identificada por un id y una dirección.
 */
public class Parada {
    private int id;
    private String direccion;

    /**
     * Crea una nueva parada con el id y la dirección especificados.
     *
     * @param id        Identificador único de la parada.
     * @param direccion Dirección de la parada.
     */
    public Parada(int id, String direccion) {
        this.id = id;
        this.direccion = direccion;
    }

    /**
     * Obtiene el identificador de la parada.
     *
     * @return id de la parada.
     */
    public int getId() {
        return id;
    }

    /**
     * Establece el identificador de la parada.
     *
     * @param id Nuevo identificador de la parada.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtiene la dirección de la parada.
     *
     * @return Dirección de la parada.
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * Establece la dirección de la parada.
     *
     * @param direccion Nueva dirección de la parada.
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     * Devuelve una representación en cadena de la parada.
     *
     * @return Cadena con los datos de la parada.
     */
    @Override
    public String toString() {
        return "Parada [id=" + id + ", direccion=" + direccion + "]";
    }
}
