package colectivo.modelo;

/**
 * Representa un pasajero que viaja en el colectivo, con un identificador,
 * una parada de origen y una parada de destino.
 */
public class Pasajero {
    private int id;
    private Parada origen;
    private Parada destino;

    /**
     * Crea un nuevo pasajero con el id, la parada de origen y la parada de destino especificados.
     *
     * @param id      Identificador único del pasajero.
     * @param origen  Parada de origen del pasajero.
     * @param destino Parada de destino del pasajero.
     */
    public Pasajero(int id, Parada origen, Parada destino) {
        this.id = id; 
        this.origen = origen;
        this.destino = destino;
    }

    /**
     * Obtiene el identificador del pasajero.
     *
     * @return id del pasajero.
     */
    public int getId() {
        return id;
    }

    /**
     * Obtiene la parada de origen del pasajero.
     *
     * @return Parada de origen.
     */
    public Parada getOrigen() {
        return origen;
    }

    /**
     * Obtiene la parada de destino del pasajero.
     *
     * @return Parada de destino.
     */
    public Parada getDestino() {
        return destino;
    }

    /**
     * Establece la parada de destino del pasajero.
     *
     * @param destino Nueva parada de destino.
     */
    public void setDestino(Parada destino) {
        this.destino = destino;
    }

    /**
     * Devuelve una representación en cadena del pasajero.
     *
     * @return Cadena con los datos del pasajero.
     */
    @Override
    public String toString() {
        return "Pasajero [id=" + id + ", origen=" + origen + ", destino=" + destino + "]";
    }
}
