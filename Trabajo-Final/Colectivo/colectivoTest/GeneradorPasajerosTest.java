package colectivoTest;

import colectivo.modelo.Parada;
import colectivo.modelo.Pasajero;
import colectivo.utils.GeneradorPasajeros;
import net.datastructures.ChainHashMap;
import net.datastructures.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests de GeneradorPasajeros")
class GeneradorPasajerosTest {

    private Map<Integer, Parada> paradas;

    @BeforeEach
    void setUp() {
        paradas = new ChainHashMap<>();
        paradas.put(1, new Parada(1, "Parada A"));
        paradas.put(2, new Parada(2, "Parada B"));
        paradas.put(3, new Parada(3, "Parada C"));
        paradas.put(4, new Parada(4, "Parada D"));
        paradas.put(5, new Parada(5, "Parada E"));
    }

    @Test
    @DisplayName("Genera la cantidad exacta de pasajeros solicitada")
    void generaLaCantidadCorrectaDePasajeros() {
        List<Pasajero> resultado = GeneradorPasajeros.generar(10, paradas);
        assertEquals(10, resultado.size()); // Se espera que se generen exactamente 10 pasajeros
    }

    @Test
    @DisplayName("Genera cero pasajeros si se pide cantidad 0")
    void generaCeroPasajeros() {
        List<Pasajero> resultado = GeneradorPasajeros.generar(0, paradas);
        assertTrue(resultado.isEmpty()); // Se espera que la lista de pasajeros esté vacía si se solicita generar 0 pasajeros
    }

    @Test
    @DisplayName("Ningún pasajero tiene origen igual a destino")
    void ningunPasajeroTieneOrigenIgualADestino() {
        List<Pasajero> resultado = GeneradorPasajeros.generar(50, paradas);
        for (Pasajero p : resultado) {
            assertNotEquals(p.getOrigen(), p.getDestino(),
                    "El pasajero " + p.getId() + " tiene origen igual al destino"); // Se espera que ningún pasajero tenga el mismo origen y destino, si se encuentra uno así, se muestra un mensaje de error indicando el ID del pasajero
        }
    }

    @Test
    @DisplayName("Los pasajeros tienen ids secuenciales desde 0")
    void pasajerosConIdsSecuenciales() {
        List<Pasajero> resultado = GeneradorPasajeros.generar(5, paradas);
        for (int i = 0; i < resultado.size(); i++) {
            assertEquals(i, resultado.get(i).getId()); // Se espera que los pasajeros tengan IDs secuenciales comenzando desde 0, si no se cumple, se muestra un mensaje de error indicando el índice esperado y el ID real del pasajero
        }
    }

    @Test
    @DisplayName("Ningún pasajero tiene origen o destino nulo")
    void ningunPasajeroTieneOrigenODestinoNulo() {
        List<Pasajero> resultado = GeneradorPasajeros.generar(20, paradas);
        for (Pasajero p : resultado) {
            assertNotNull(p.getOrigen()); // Se espera que ningún pasajero tenga un origen nulo, si se encuentra uno así, se muestra un mensaje de error indicando el ID del pasajero
            assertNotNull(p.getDestino()); // Se espera que ningún pasajero tenga un destino nulo, si se encuentra uno así, se muestra un mensaje de error indicando el ID del pasajero
        }
    }
}

