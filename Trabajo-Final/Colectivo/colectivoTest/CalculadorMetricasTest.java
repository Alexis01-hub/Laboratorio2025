package colectivoTest;

import colectivo.modelo.Colectivo;
import colectivo.modelo.Linea;
import colectivo.modelo.Parada;
import colectivo.modelo.Pasajero;
import colectivo.utils.CalculadorMetricas;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests de CalculadorMetricas")
class CalculadorMetricasTest {

    private CalculadorMetricas calculador;
    private Linea linea;
    private Colectivo colectivo;
    private Parada p1;
    private Parada p2;
    private Parada p3;

    @BeforeEach
    void setUp() {
        calculador = new CalculadorMetricas();

        p1 = new Parada(1, "Inicio");
        p2 = new Parada(2, "Medio");
        p3 = new Parada(3, "Fin");

        linea = new Linea("L1");
        linea.agregarParada(p1);
        linea.agregarParada(p2);
        linea.agregarParada(p3);

        colectivo = new Colectivo(linea, 10);
    }

    @Test
    @DisplayName("calcularIndiceSatisfaccion retorna 0.0 con lista vacía")
    void indiceSatisfaccionListaVacia() {
        double indice = calculador.calcularIndiceSatisfaccion(new ArrayList<>());
        assertEquals(0.0, indice, 1e-9);
    }

    @Test
    @DisplayName("calcularIndiceSatisfaccion con caso controlado")
    void indiceSatisfaccionCasoControlado() {
        List<Pasajero> pasajeros = new ArrayList<>();

        // Clasificación 5
        Pasajero c5 = new Pasajero(1, p1, p3, false);
        c5.registrarSubida(true);
        pasajeros.add(c5);

        // Clasificación 4
        Pasajero c4 = new Pasajero(2, p1, p3, false);
        c4.registrarSubida(false);
        pasajeros.add(c4);

        // Clasificación 3 (esperó 1)
        Pasajero c3 = new Pasajero(3, p1, p3, false);
        c3.incrementarColectivosEsperados();
        c3.registrarSubida(false);
        pasajeros.add(c3);

        // Clasificación 2 (más de dos)
        Pasajero c2 = new Pasajero(4, p1, p3, false);
        c2.incrementarColectivosEsperados();
        c2.incrementarColectivosEsperados();
        c2.incrementarColectivosEsperados();
        c2.registrarSubida(false);
        pasajeros.add(c2);

        // Clasificación 1 (no subió)
        Pasajero c1 = new Pasajero(5, p1, p3, false);
        pasajeros.add(c1);

        // (5 + 4 + 3 + 2 + 1) / (5 * 5) = 15/25 = 0.6
        double indice = calculador.calcularIndiceSatisfaccion(pasajeros);
        assertEquals(0.6, indice, 1e-9);
    }

    @Test
    @DisplayName("calcularOcupacionPromedio retorna 0.0 si colectivo o línea son nulos")
    void ocupacionPromedioConNulos() {
        assertEquals(0.0, calculador.calcularOcupacionPromedio(null, linea), 1e-9);
        assertEquals(0.0, calculador.calcularOcupacionPromedio(colectivo, null), 1e-9);
    }

    @Test
    @DisplayName("calcularOcupacionPromedio retorna 0.0 si hay menos de 2 paradas")
    void ocupacionPromedioLineaSinTramos() {
        Linea lineaSinTramos = new Linea("L0");
        lineaSinTramos.agregarParada(new Parada(99, "Unica"));
        double ocupacion = calculador.calcularOcupacionPromedio(colectivo, lineaSinTramos);
        assertEquals(0.0, ocupacion, 1e-9);
    }

    @Test
    @DisplayName("calcularOcupacionPromedio con tramos controlados")
    void ocupacionPromedioControlada() {
        // 2 tramos: p1->p2 y p2->p3
        colectivo.registrarPasajerosEnTramo(0, 5);  // 5/10 = 0.5
        colectivo.registrarPasajerosEnTramo(1, 10); // 10/10 = 1.0

        double ocupacion = calculador.calcularOcupacionPromedio(colectivo, linea);
        assertEquals(0.75, ocupacion, 1e-9);
    }
}
