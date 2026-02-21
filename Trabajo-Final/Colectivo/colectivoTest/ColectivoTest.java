package colectivoTest;

import colectivo.modelo.Colectivo;
import colectivo.modelo.Linea;
import colectivo.modelo.Parada;
import colectivo.modelo.Pasajero;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests de Colectivo")
class ColectivoTest {

    private Linea linea;
    private Colectivo colectivo;
    private Parada parada1;
    private Parada parada2;
    private Pasajero pasajero;

    @BeforeEach
    void setUp() {
        parada1 = new Parada(1, "Av. Siempre Viva, 123");
        parada2 = new Parada(2, "Calle Falsa, 456");
        linea = new Linea("L1");
        linea.agregarParada(parada1);
        linea.agregarParada(parada2);
        colectivo = new Colectivo(linea);
        pasajero = new Pasajero(1, parada1, parada2);
    }

    @Test
    @DisplayName("Un colectivo nuevo no tiene pasajeros")
    void colectivoNuevoSinPasajeros() {
        assertTrue(colectivo.getPasajeros().isEmpty()); // Un colectivo recién creado no debería tener pasajeros a bordo
    }

    @Test
    @DisplayName("getLinea devuelve la línea correcta")
    void getLineaDevuelveLaLineaCorrecta() {
        assertEquals(linea, colectivo.getLinea()); // El metodo getLinea debería devolver la línea asociada al colectivo
    }

    @Test
    @DisplayName("subirPasajero agrega el pasajero a la lista")
    void subirPasajeroAgregaAlPasajero() {
        colectivo.subirPasajero(pasajero);
        assertTrue(colectivo.getPasajeros().contains(pasajero)); // Después de subir un pasajero, la lista de pasajeros del colectivo debería contenerlo
        assertEquals(1, colectivo.getPasajeros().size()); // La cantidad de pasajeros a bordo debería ser 1 después de subir un pasajero
    }

    @Test
    @DisplayName("bajarPasajero elimina el pasajero de la lista")
    void bajarPasajeroEliminaAlPasajero() {
        colectivo.subirPasajero(pasajero);
        colectivo.bajarPasajero(pasajero);
        assertFalse(colectivo.getPasajeros().contains(pasajero)); // Después de bajar un pasajero, la lista de pasajeros del colectivo no debería contenerlo
        assertTrue(colectivo.getPasajeros().isEmpty()); // La cantidad de pasajeros a bordo debería ser 0 después de bajar el único pasajero
    }

    @Test
    @DisplayName("Se pueden subir múltiples pasajeros")
    void subirMultiplesPasajeros() {
        Pasajero p2 = new Pasajero(2, parada1, parada2);
        Pasajero p3 = new Pasajero(3, parada1, parada2);
        colectivo.subirPasajero(pasajero);
        colectivo.subirPasajero(p2);
        colectivo.subirPasajero(p3);
        assertEquals(3, colectivo.getPasajeros().size()); // Después de subir tres pasajeros, la cantidad de pasajeros a bordo debería ser 3
    }

    @Test
    @DisplayName("Bajar un pasajero que no está a bordo no lanza excepción")
    void bajarPasajeroQueNoEsta() {
        Pasajero pAjeno = new Pasajero(99, parada1, parada2);
        assertDoesNotThrow(() -> colectivo.bajarPasajero(pAjeno)); // Bajar un pasajero que no está a bordo no debería lanzar una excepción
        assertTrue(colectivo.getPasajeros().isEmpty()); // La lista de pasajeros debería seguir vacía después de intentar bajar un pasajero que no está a bordo
    }

    @Test
    @DisplayName("Subir y bajar al mismo pasajero deja la lista vacía")
    void subirYBajarMismoPasajero() {
        colectivo.subirPasajero(pasajero);
        colectivo.bajarPasajero(pasajero);
        assertEquals(0, colectivo.getPasajeros().size()); // Después de subir y bajar al mismo pasajero, la cantidad de pasajeros a bordo debería ser 0
    }
}