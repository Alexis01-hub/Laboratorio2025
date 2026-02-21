package colectivoTest;

import colectivo.modelo.Parada;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests de Parada")
class ParadaTest {

    private Parada parada;

    @BeforeEach
    void setUp() {
        parada = new Parada(5, "Av. Siempre Viva, 742");
    }

    @Test
    @DisplayName("getId devuelve el id correcto")
    void getIdDevuelveIdCorrecto() {
        assertEquals(5, parada.getId()); // El metodo getId debería devolver el id asignado a la parada, en este caso 5
    }

    @Test
    @DisplayName("getDireccion devuelve la dirección correcta")
    void getDireccionDevuelveDireccionCorrecta() {
        assertEquals("Av. Siempre Viva, 742", parada.getDireccion()); // El metodo getDireccion debería devolver la dirección asignada a la parada, en este caso "Av. Siempre Viva, 742"
    }

    @Test
    @DisplayName("setId modifica el id correctamente")
    void setIdModificaId() {
        parada.setId(99);
        assertEquals(99, parada.getId()); // Después de modificar el id con setId, el metodo getId debería devolver el nuevo id asignado, en este caso 99
    }

    @Test
    @DisplayName("setDireccion modifica la dirección correctamente")
    void setDireccionModificaDireccion() {
        parada.setDireccion("Calle Nueva, 100");
        assertEquals("Calle Nueva, 100", parada.getDireccion()); // Después de modificar la dirección con setDireccion, el metodo getDireccion debería devolver la nueva dirección asignada, en este caso "Calle Nueva, 100"
    }

    @Test
    @DisplayName("toString incluye el id y la dirección")
    void toStringIncluyeIdYDireccion() {
        String resultado = parada.toString();
        assertTrue(resultado.contains("5")); // El resultado del metodo toString debería incluir el id de la parada, en este caso "5"
        assertTrue(resultado.contains("Av. Siempre Viva, 742")); // El resultado del metodo toString debería incluir la dirección de la parada, en este caso "Av. Siempre Viva, 742"
    }

    @Test
    @DisplayName("Dos referencias al mismo objeto son iguales")
    void mismaPararaEsIgualPorReferencia() {
        Parada mismaParada = parada;
        assertEquals(parada, mismaParada); // Dos referencias al mismo objeto deberían ser consideradas iguales, el metodo equals debería devolver true
    }
}

