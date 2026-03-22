package colectivoTest;

import colectivo.modelo.Colectivo;
import colectivo.modelo.Linea;
import colectivo.modelo.Parada;
import colectivo.modelo.Pasajero;
import colectivo.utils.CalculadorMetricas;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Test de integración de simulación")
class SimuladorIntegrationTest {

    @Test
    @DisplayName("Con capacidad limitada, se completan múltiples recorridos y todos llegan")
    void simulacionChicaMultiplesRecorridos() {
        // Datos chicos y controlados
        Parada p1 = new Parada(1, "Inicio");
        Parada p2 = new Parada(2, "Medio");
        Parada p3 = new Parada(3, "Fin");

        Linea linea = new Linea("L1");
        linea.agregarParada(p1);
        linea.agregarParada(p2);
        linea.agregarParada(p3);

        int capacidadMaxima = 2; // fuerza más de un recorrido
        List<Pasajero> pasajeros = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            pasajeros.add(new Pasajero(i, p1, p3, false));
        }

        List<Colectivo> recorridosEjecutados = new ArrayList<>();
        int numeroRecorrido = 1;
        int pasajerosRestantes = pasajeros.size();

        while (pasajerosRestantes > 0 && numeroRecorrido <= 20) {
            Colectivo colectivo = new Colectivo(linea, capacidadMaxima);
            colectivo.setId(linea.getCodigo() + "_" + numeroRecorrido);

            for (int i = 0; i < linea.getParadas().size(); i++) {
                Parada paradaActual = linea.getParadas().get(i);

                List<Pasajero> candidatosSubida = new ArrayList<>();
                for (Pasajero p : pasajeros) {
                    if (!p.subio() && p.getOrigen().equals(paradaActual)) {
                        candidatosSubida.add(p);
                    }
                }

                for (Pasajero p : candidatosSubida) {
                    if (!colectivo.estaLleno()) {
                        boolean pudoSubir = colectivo.subirPasajero(p);
                        if (pudoSubir) {
                            boolean viajoSentado = true; // en este test no importa diferenciar
                            p.registrarSubida(viajoSentado);
                            colectivo.registrarSubidaEnParada(i, p);
                        }
                    } else {
                        p.incrementarColectivosEsperados();
                    }
                }

                if (i < linea.getParadas().size() - 1) {
                    colectivo.registrarPasajerosEnTramo(i, colectivo.getPasajeros().size());
                }

                List<Pasajero> bajan = new ArrayList<>();
                for (Pasajero p : colectivo.getPasajeros()) {
                    if (p.getDestino().equals(paradaActual)) {
                        bajan.add(p);
                    }
                }
                for (Pasajero p : bajan) {
                    colectivo.bajarPasajero(p);
                    colectivo.registrarBajadaEnParada(i, p);
                    p.marcarComoLlegado();
                }
            }

            recorridosEjecutados.add(colectivo);

            pasajerosRestantes = 0;
            for (Pasajero p : pasajeros) {
                if (!p.llego()) {
                    pasajerosRestantes++;
                }
            }

            numeroRecorrido++;
        }

        // Verificaciones de integración
        assertTrue(recorridosEjecutados.size() >= 3,
                "Con 5 pasajeros y capacidad 2, deben ejecutarse al menos 3 recorridos");

        for (Pasajero p : pasajeros) {
            assertTrue(p.llego(), "Todos los pasajeros deben llegar a destino");
        }

        // Verificación básica de métricas sobre resultado integrado
        CalculadorMetricas calculador = new CalculadorMetricas();
        double indice = calculador.calcularIndiceSatisfaccion(pasajeros);
        assertTrue(indice >= 0.0 && indice <= 1.0, "El índice debe estar en [0,1]");

        for (Colectivo c : recorridosEjecutados) {
            double ocupacion = calculador.calcularOcupacionPromedio(c, linea);
            assertTrue(ocupacion >= 0.0 && ocupacion <= 1.0, "La ocupación debe estar en [0,1]");
        }
    }
}
