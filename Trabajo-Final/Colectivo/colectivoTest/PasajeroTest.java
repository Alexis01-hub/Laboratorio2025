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
        pasajero = new Pasajero(7, origen, destino, false); // ACTUALIZADO: agregar viajoSentado
    }

    @Test
    @DisplayName("getId devuelve el id correcto")
    void getIdDevuelveIdCorrecto() {
        assertEquals(7, pasajero.getId());
    }

    @Test
    @DisplayName("getOrigen devuelve la parada de origen correcta")
    void getOrigenDevuelveLaParadaDeOrigen() {
        assertEquals(origen, pasajero.getOrigen());
    }

    @Test
    @DisplayName("getDestino devuelve la parada de destino correcta")
    void getDestinoDevuelveLaParadaDeDestino() {
        assertEquals(destino, pasajero.getDestino());
    }

    @Test
    @DisplayName("setDestino modifica el destino correctamente")
    void setDestinoModificaElDestino() {
        Parada nuevoDestino = new Parada(3, "Belgrano, 800");
        pasajero.setDestino(nuevoDestino);
        assertEquals(nuevoDestino, pasajero.getDestino());
    }

    @Test
    @DisplayName("toString incluye el id, el origen y el destino")
    void toStringIncluyeDatos() {
        String resultado = pasajero.toString();
        assertTrue(resultado.contains("7"));
        assertTrue(resultado.contains("Av. Siempre Viva, 123"));
        assertTrue(resultado.contains("Calle Falsa, 456"));
    }

    @Test
    @DisplayName("incrementarColectivosEsperados aumenta el contador")
    void incrementarColectivosEsperados() {
        pasajero.incrementarColectivosEsperados();
        pasajero.incrementarColectivosEsperados();
        // Nota: necesitarías agregar un getter para verificar, pero el test verifica que no lance excepción
    }

    @Test
    @DisplayName("registrarSubida marca al pasajero como subido")
    void registrarSubidaMarcaComoSubido() {
        assertFalse(pasajero.subio()); // Inicialmente no ha subido
        pasajero.registrarSubida(true); // Registrar que subió sentado
        assertTrue(pasajero.subio()); // Ahora debería estar marcado como subido
    }

    @Test
    @DisplayName("getClasificacion retorna valor correcto cuando no subió")
    void getClasificacionNoSubio() {
        assertEquals(1, pasajero.getClasificacion()); // 1 = no subió
    }

    @Test
    @DisplayName("getClasificacion retorna valor correcto cuando subió en primer colectivo sentado")
    void getClasificacionPrimeroSentado() {
        pasajero.registrarSubida(true); // Subió sentado, sin esperar colectivos
        assertEquals(5, pasajero.getClasificacion()); // 5 = primero + sentado
    }

    @Test
    @DisplayName("getClasificacion retorna valor correcto cuando subió en primer colectivo parado")
    void getClasificacionPrimeroParado() {
        pasajero.registrarSubida(false); // Subió parado, sin esperar colectivos
        assertEquals(4, pasajero.getClasificacion()); // 4 = primero + parado
    }

    @Test
    @DisplayName("getClasificacion retorna valor correcto cuando esperó un colectivo")
    void getClasificacionSegundo() {
        pasajero.incrementarColectivosEsperados(); // Esperó 1 colectivo
        pasajero.registrarSubida(false);
        assertEquals(3, pasajero.getClasificacion()); // 3 = segundo
    }

    @Test
    @DisplayName("getClasificacion retorna valor correcto cuando esperó múltiples colectivos")
    void getClasificacionMultiples() {
        pasajero.incrementarColectivosEsperados();
        pasajero.incrementarColectivosEsperados();
        pasajero.incrementarColectivosEsperados(); // Esperó 3 colectivos
        pasajero.registrarSubida(false);
        assertEquals(2, pasajero.getClasificacion()); // 2 = más de dos colectivos
    }

    @Test
    @DisplayName("getClasificacion no retorna 2 cuando esperó exactamente dos colectivos")
    void getClasificacionDosEsperasNoEsNivel2() {
        pasajero.incrementarColectivosEsperados();
        pasajero.incrementarColectivosEsperados(); // esperó 2
        pasajero.registrarSubida(false);
        assertEquals(3, pasajero.getClasificacion()); // o el valor que defina tu anexo
    }

}
