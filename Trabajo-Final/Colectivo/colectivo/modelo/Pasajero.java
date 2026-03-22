package colectivo.modelo;

/**
 * Representa un pasajero que viaja en el colectivo, con un identificador,
 * una parada de origen y una parada de destino.
 */
public class Pasajero {
    private final int id;
    private final Parada origen;
    private Parada destino;
    private int colectivosEsperados = 0; // Contador de colectivos que el pasajero espera tomar
    private boolean viajoSentado = false;
    private boolean subio = false; // Indica si el pasajero subió al colectivo
    private boolean llego = false; // Indica si el pasajero llegó a su destino final
    private final String codigoLineaAsignada;


    public Pasajero(int id, Parada origen, Parada destino, boolean viajoSentado) {
        this(id, origen, destino, viajoSentado, null);
    }

    public Pasajero(int id, Parada origen, Parada destino, boolean viajoSentado, String codigoLineaAsignada) {
        this.id = id;
        this.origen = origen;
        this.destino = destino;
        this.viajoSentado = viajoSentado;
        this.codigoLineaAsignada = codigoLineaAsignada;
    }

    public String getCodigoLineaAsignada() {
        return codigoLineaAsignada;
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

    public void incrementarColectivosEsperados() {
        colectivosEsperados++;
    }

    public void registrarSubida(boolean viajoSentado) {
        this.subio = true;
        this.viajoSentado = viajoSentado;
    }

    public boolean subio() {
        return subio; // Devuelve si el pasajero subió al colectivo
    }


    /**
     * Marca que el pasajero llegó a su destino final.
     */
    public void marcarComoLlegado() {
        this.llego = true;
    }

    /**
     * Verifica si el pasajero llegó a su destino final.
     *
     * @return true si el pasajero llegó a su destino, false en caso contrario
     */
    public boolean llego() {
        return llego;
    }

    public int getClasificacion() {
        if (!subio) {
            return 1; // no subió
        } else if (colectivosEsperados == 0 && viajoSentado) {
            return 5; // primero + sentado
        } else if (colectivosEsperados == 0) {
            return 4; // primero + parado
        } else if (colectivosEsperados == 1) {
            return 3; // segundo
        } else if (colectivosEsperados > 2){
            return 2; // más de dos colectivos
        }else {
            return 3; //caso borde: espero exactamente 2
        }
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
