package colectivoTest;

import colectivo.modelo.Parada;
import colectivo.modelo.Pasajero;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests de Pasajero")
class PasajeroTest {

    private Parada origen;
    private Parada destino;
    private Pasajero pasajero;

    @BeforeEach
    void setUp() {
        origen  = new Parada(1, "Av. Siempre Viva, 123");
        destino = new Parada(2, "Calle Falsa, 456");
        pasajero = new Pasajero(7, origen, destino);
    }

    @Test
    @DisplayName("getId devuelve el id correcto")
    void getIdDevuelveIdCorrecto() {
        assertEquals(7, pasajero.getId()); // El metodo getId debería devolver el id asignado al pasajero, en este caso 7
    }

    @Test
    @DisplayName("getOrigen devuelve la parada de origen correcta")
    void getOrigenDevuelveLaParadaDeOrigen() {
        assertEquals(origen, pasajero.getOrigen()); // El metodo getOrigen debería devolver la parada de origen asignada al pasajero, en este caso la parada con id 1 y dirección "Av. Siempre Viva, 123"
    }

    @Test
    @DisplayName("getDestino devuelve la parada de destino correcta")
    void getDestinoDevuelveLaParadaDeDestino() {
        assertEquals(destino, pasajero.getDestino()); // El metodo getDestino debería devolver la parada de destino asignada al pasajero, en este caso la parada con id 2 y dirección "Calle Falsa, 456"
    }

    @Test
    @DisplayName("setDestino modifica el destino correctamente")
    void setDestinoModificaElDestino() {
        Parada nuevoDestino = new Parada(3, "Belgrano, 800");
        pasajero.setDestino(nuevoDestino);
        assertEquals(nuevoDestino, pasajero.getDestino()); // Después de modificar el destino con setDestino, el metodo getDestino debería devolver la nueva parada de destino asignada, en este caso la parada con id 3 y dirección "Belgrano, 800"
    }

    @Test
    @DisplayName("toString incluye el id, el origen y el destino")
    void toStringIncluyeDatos() {
        String resultado = pasajero.toString();
        assertTrue(resultado.contains("7")); // El resultado del metodo toString debería incluir el id del pasajero, en este caso "7"
        assertTrue(resultado.contains("Av. Siempre Viva, 123")); // El resultado del metodo toString debería incluir la dirección de la parada de origen, en este caso "Av. Siempre Viva, 123"
        assertTrue(resultado.contains("Calle Falsa, 456")); // El resultado del metodo toString debería incluir la dirección de la parada de destino, en este caso "Calle Falsa, 456"
    }
}

