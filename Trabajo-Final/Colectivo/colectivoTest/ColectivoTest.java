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
        colectivo = new Colectivo(30, linea); // ACTUALIZADO: agregar capacidadMaxima
        pasajero = new Pasajero(1, parada1, parada2, false); // ACTUALIZADO: agregar viajoSentado
    }

    @Test
    @DisplayName("Un colectivo nuevo no tiene pasajeros")
    void colectivoNuevoSinPasajeros() {
        assertTrue(colectivo.getPasajeros().isEmpty());
    }

    @Test
    @DisplayName("getLinea devuelve la línea correcta")
    void getLineaDevuelveLaLineaCorrecta() {
        assertEquals(linea, colectivo.getLinea());
    }

    @Test
    @DisplayName("subirPasajero agrega el pasajero a la lista")
    void subirPasajeroAgregaAlPasajero() {
        boolean resultado = colectivo.subirPasajero(pasajero);
        assertTrue(resultado); // ACTUALIZADO: verificar que subirPasajero retorna true
        assertTrue(colectivo.getPasajeros().contains(pasajero));
        assertEquals(1, colectivo.getPasajeros().size());
    }

    @Test
    @DisplayName("bajarPasajero elimina el pasajero de la lista")
    void bajarPasajeroEliminaAlPasajero() {
        colectivo.subirPasajero(pasajero);
        colectivo.bajarPasajero(pasajero);
        assertFalse(colectivo.getPasajeros().contains(pasajero));
        assertTrue(colectivo.getPasajeros().isEmpty());
    }

    @Test
    @DisplayName("Se pueden subir múltiples pasajeros")
    void subirMultiplesPasajeros() {
        Pasajero p2 = new Pasajero(2, parada1, parada2, false); // ACTUALIZADO
        Pasajero p3 = new Pasajero(3, parada1, parada2, false); // ACTUALIZADO
        colectivo.subirPasajero(pasajero);
        colectivo.subirPasajero(p2);
        colectivo.subirPasajero(p3);
        assertEquals(3, colectivo.getPasajeros().size());
    }

    @Test
    @DisplayName("Bajar un pasajero que no está a bordo no lanza excepción")
    void bajarPasajeroQueNoEsta() {
        Pasajero pAjeno = new Pasajero(99, parada1, parada2, false); // ACTUALIZADO
        assertDoesNotThrow(() -> colectivo.bajarPasajero(pAjeno));
        assertTrue(colectivo.getPasajeros().isEmpty());
    }

    @Test
    @DisplayName("Subir y bajar al mismo pasajero deja la lista vacía")
    void subirYBajarMismoPasajero() {
        colectivo.subirPasajero(pasajero);
        colectivo.bajarPasajero(pasajero);
        assertEquals(0, colectivo.getPasajeros().size());
    }

    @Test
    @DisplayName("estaLleno retorna true cuando capacidad es alcanzada")
    void estaLlenoRetornaTrue() {
        Colectivo colectivoChico = new Colectivo(2, linea); // NUEVO: crear colectivo con capacidad de 2
        Pasajero p1 = new Pasajero(1, parada1, parada2, false);
        Pasajero p2 = new Pasajero(2, parada1, parada2, false);
        Pasajero p3 = new Pasajero(3, parada1, parada2, false);

        colectivoChico.subirPasajero(p1);
        colectivoChico.subirPasajero(p2);
        assertTrue(colectivoChico.estaLleno()); // NUEVO: verificar que está lleno

        boolean pudoSubir = colectivoChico.subirPasajero(p3);
        assertFalse(pudoSubir); // NUEVO: no debe poder subir el 3er pasajero
    }

    @Test
    @DisplayName("getCantidadMaxima retorna la capacidad correcta")
    void getCantidadMaximaCorrecta() {
        assertEquals(30, colectivo.getCantidadMaxima()); // NUEVO: verificar capacidad
    }

    @Test
    @DisplayName("Simular múltiples recorridos cuando hay más pasajeros que capacidad")
    void verificarMultiplesRecorridos() {
        // Crear una línea con 3 paradas
        Parada p1 = new Parada(1, "Inicio");
        Parada p2 = new Parada(2, "Medio");
        Parada p3 = new Parada(3, "Fin");

        Linea linea = new Linea("L1");
        linea.agregarParada(p1);
        linea.agregarParada(p2);
        linea.agregarParada(p3);

        // Crear 10 pasajeros que viajan de p1 a p3
        java.util.List<Pasajero> pasajeros = new java.util.ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            pasajeros.add(new Pasajero(i, p1, p3, false));
        }

        // Simular múltiples recorridos (capacidad máxima = 3)
        int numeroRecorrido = 1;
        int pasajerosRestantes = pasajeros.size();
        java.util.List<Colectivo> colectivosUtilizados = new java.util.ArrayList<>();

        while (pasajerosRestantes > 0 && numeroRecorrido <= 10) {
            // Crear nuevo colectivo para este recorrido
            Colectivo colectivo = new Colectivo(3, linea); // Capacidad de 3

            // Simular el recorrido por cada parada
            for (Parada parada : linea.getParadas()) {
                // Subir pasajeros en esta parada
                for (Pasajero p : pasajeros) {
                    if (!p.subio() && p.getOrigen().equals(parada)) {
                        if (!colectivo.estaLleno()) {
                            colectivo.subirPasajero(p);
                            p.registrarSubida(false); // Simulamos que suben parados
                        }
                    }
                }

                // Bajar pasajeros en esta parada
                java.util.List<Pasajero> pasajerosABajar = new java.util.ArrayList<>();
                for (Pasajero p : colectivo.getPasajeros()) {
                    if (p.getDestino().equals(parada)) {
                        pasajerosABajar.add(p);
                    }
                }
                for (Pasajero p : pasajerosABajar) {
                    colectivo.bajarPasajero(p);
                    p.marcarComoLlegado(); // Marcar que llegó a su destino
                }
            }

            colectivosUtilizados.add(colectivo);

            // Contar pasajeros que aún no llegaron a su destino
            pasajerosRestantes = 0;
            for (Pasajero p : pasajeros) {
                if (!p.llego()) {
                    pasajerosRestantes++;
                }
            }

            numeroRecorrido++;
        }

        // Verificaciones
        System.out.println("Recorridos realizados: " + (numeroRecorrido - 1));
        System.out.println("Colectivos utilizados: " + colectivosUtilizados.size());

        // Con 10 pasajeros y capacidad de 3, se necesitan al menos 4 recorridos
        assertTrue(colectivosUtilizados.size() >= 4,
                "Se esperaban al menos 4 colectivos para transportar 10 pasajeros con capacidad de 3");

        // Verificar que todos los pasajeros llegaron a su destino
        for (Pasajero p : pasajeros) {
            assertTrue(p.llego(), "El pasajero " + p.getId() + " no llegó a su destino");
        }
    }
}
