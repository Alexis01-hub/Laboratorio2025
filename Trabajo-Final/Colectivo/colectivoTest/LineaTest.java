package colectivoTest;

import colectivo.modelo.Linea;
import colectivo.modelo.Parada;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests de Linea")
class LineaTest {

    private Linea linea;
    private Parada parada1;
    private Parada parada2;
    private Parada parada3;

    @BeforeEach
    void setUp() {
        linea = new Linea("L1I");
        parada1 = new Parada(1, "Av. Siempre Viva, 123");
        parada2 = new Parada(2, "Calle Falsa, 456");
        parada3 = new Parada(3, "Belgrano, 800");
    }

    @Test
    @DisplayName("getCodigo devuelve el código correcto")
    void getCodigoDevuelveElCodigoCorrecto() {
        assertEquals("L1I", linea.getCodigo()); // El método getCodigo debería devolver el código asignado a la línea, en este caso "L1I"
    }

    @Test
    @DisplayName("Una línea nueva no tiene paradas")
    void lineaNuevaSinParadas() {
        assertTrue(linea.getParadas().isEmpty()); // Una línea recién creada no debería tener paradas asociadas, la lista de paradas debería estar vacía
    }

    @Test
    @DisplayName("agregarParada agrega la parada a la lista")
    void agregarParadaAgregaParada() {
        linea.agregarParada(parada1);
        assertEquals(1, linea.getParadas().size()); // Después de agregar una parada, la cantidad de paradas en la línea debería ser 1
        assertTrue(linea.getParadas().contains(parada1)); // La lista de paradas de la línea debería contener la parada recién agregada
    }

    @Test
    @DisplayName("Las paradas mantienen el orden de inserción")
    void paradasMantenenOrdenDeInsercion() {
        linea.agregarParada(parada1);
        linea.agregarParada(parada2);
        linea.agregarParada(parada3);
        assertEquals(parada1, linea.getParadas().get(0)); // La primera parada agregada debería ser la primera en la lista de paradas
        assertEquals(parada2, linea.getParadas().get(1)); // La segunda parada agregada debería ser la segunda en la lista de paradas
        assertEquals(parada3, linea.getParadas().get(2)); // La tercera parada agregada debería ser la tercera en la lista de paradas
    }

    @Test
    @DisplayName("Se pueden agregar múltiples paradas")
    void agregarMultiplesParadas() {
        linea.agregarParada(parada1);
        linea.agregarParada(parada2);
        linea.agregarParada(parada3);
        assertEquals(3, linea.getParadas().size()); // Después de agregar tres paradas, la cantidad de paradas en la línea debería ser 3
    }

    @Test
    @DisplayName("toString incluye el código de la línea")
    void toStringIncluyeCodigo() {
        assertTrue(linea.toString().contains("L1I")); // El metodo toString de la línea debería incluir el codigo de la linea, si no lo incluye, el test fallara
    }

    @Test
    @DisplayName("getParadas contiene la parada recién agregada")
    void getParadasContieneParadaAgregada() {
        linea.agregarParada(parada2);
        assertTrue(linea.getParadas().contains(parada2)); // Después de agregar una parada, el metodo getParadas debería devolver una lista que contiene esa parada, si no la contiene, el test fallara
    }
}

