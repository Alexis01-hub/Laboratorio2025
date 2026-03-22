package colectivo.aplicacion;

import java.util.List;
import java.util.ArrayList;
import java.util.Properties;


import colectivo.modelo.Colectivo;
import colectivo.datos.CargadorDatos;
import colectivo.modelo.Linea;
import colectivo.modelo.Parada;
import colectivo.modelo.Pasajero;
import colectivo.utils.GeneradorPasajeros;
import colectivo.datos.CargarParametros;
import colectivo.utils.CalculadorMetricas;
import colectivo.utils.ImprimirRecorrido;
import net.datastructures.ChainHashMap;
import net.datastructures.Map;


/**
 * Clase principal que ejecuta la simulación del sistema de colectivos.
 * Lee la configuración, genera pasajeros aleatorios y simula recorridos por línea.
 *
 * Supuesto de modelado:
 * cada instancia de Colectivo representa un recorrido (vuelta) de una línea,
 * no un vehículo físico único reutilizado entre vueltas.
 */
public class Simulador {
    private static final CalculadorMetricas calculadorMetricas = new CalculadorMetricas(); // Instancia de CalculadorMetricas para calcular métricas al finalizar la simulación

    public static void main(String[] args) throws Exception {

        // 1. Leer configuración desde el archivo config.properties
        Properties config = CargarParametros.cargar();
        int cantidadPasajeros = Integer.parseInt(config.getProperty("cantidadPasajeros", "1000"));
        String archivoParadas = config.getProperty("parada", "Colectivo/parada.txt");
        String archivoLineas = config.getProperty("linea", "Colectivo/linea.txt");
        int capacidadMaxima = Integer.parseInt(config.getProperty("capacidadMaxima", "30"));
        if (capacidadMaxima <= 0) {
            throw new IllegalArgumentException("capacidadMaxima debe ser mayor a 0");
        }

        // 2. Cargar datos de paradas y líneas
        Map<Integer, Parada> paradas = CargadorDatos.cargarParadas(archivoParadas); // Carga las paradas desde el archivo
        Map<String, Linea> lineas = CargadorDatos.cargarLineas(archivoLineas, paradas); // Carga las líneas desde el archivo, asociando las paradas

        // 3. Generar pasajeros aleatorios usando la cantidad definida en la configuración
        List<Pasajero> pasajeros = GeneradorPasajeros.generarDesdeLineas(cantidadPasajeros, lineas);

        // 4. Simular multiples recorridos por cada linea
        List<Colectivo> colectivosUtilizados = new ArrayList<>();

        for (Linea linea : lineas.values()) {
            System.out.println("\n=== Procesando línea: " + linea.getCodigo() + " ===");

            // Filtrar pasajeros que viajan en esta línea
            // IMPORTANTE: El destino DEBE estar DESPUÉS del origen en la línea
            List<Pasajero> pasajerosDeLaLinea = new ArrayList<>();
            for (Pasajero p : pasajeros) {
                int indexOrigen = linea.getParadas().indexOf(p.getOrigen());
                int indexDestino = linea.getParadas().indexOf(p.getDestino());

                boolean coincideLineaAsignada = p.getCodigoLineaAsignada() == null || p.getCodigoLineaAsignada().equals(linea.getCodigo());

                // Validar que ambas paradas existan en la línea y que destino esté DESPUÉS del origen
                if (!p.llego() && coincideLineaAsignada && indexOrigen >= 0 && indexDestino > indexOrigen) {
                    pasajerosDeLaLinea.add(p);
                }
            }

            // Simular múltiples recorridos hasta que todos suban
            int numeroRecorrido = 1;
            int pasajerosRestantes = 0;
            for (Pasajero p : pasajerosDeLaLinea) {
                if (!p.llego()) {
                    pasajerosRestantes++;
                }
            }

            while (pasajerosRestantes > 0) {
                // cada instancia representa un recorrido (vuelta ) de la linea
                Colectivo colectivo = new Colectivo(linea, capacidadMaxima);
                String idColectivo = linea.getCodigo() + "_" + numeroRecorrido;
                colectivo.setId(idColectivo); // formato:Lx_n (linea x, recorrido n)

                // Simular el recorrido por cada parada de la línea
                for (int i = 0; i < linea.getParadas().size(); i++) {
                    Parada paradaActual = linea.getParadas().get(i);

                    // Intentar subir pasajeros en esta parada
                    List<Pasajero> pasajerosEnEstaParada = new ArrayList<>();
                    for (Pasajero p : pasajerosDeLaLinea) {
                        if (!p.subio() && p.getOrigen().equals(paradaActual)) {
                            pasajerosEnEstaParada.add(p);
                        }
                    }

                    // Intentar subir pasajeros (en orden de la lista)
                    for (Pasajero p : pasajerosEnEstaParada) {
                        if (!colectivo.estaLleno()) {
                            boolean pudoSubir = colectivo.subirPasajero(p);
                            if (pudoSubir) {
                                // Determinar si viaja sentado o parado
                                int capacidadSentados = (int) (capacidadMaxima * 0.7);
                                boolean viajoSentado = colectivo.getPasajeros().size() <= capacidadSentados;
                                p.registrarSubida(viajoSentado);
                                colectivo.registrarSubidaEnParada(i, p);
                            }
                        } else {
                            // Colectivo lleno, pasajero espera el siguiente
                            p.incrementarColectivosEsperados();
                        }
                    }

                    // Registrar ocupación en este tramo (entre parada i e i+1)
                    int pasajerosEnTramo = 0;
                    if (i < linea.getParadas().size() - 1) {
                        for (Pasajero p : colectivo.getPasajeros()) {
                            Parada destino = p.getDestino();
                            // Contar si el pasajero viaja en este tramo
                            int indexOrigen = linea.getParadas().indexOf(p.getOrigen());
                            int indexDestino = linea.getParadas().indexOf(destino);
                            if (indexOrigen <= i && indexDestino > i) {
                                pasajerosEnTramo++;
                            }
                        }
                        colectivo.registrarPasajerosEnTramo(i, pasajerosEnTramo);
                    }

                    // Bajar pasajeros en esta parada
                    List<Pasajero> pasajerosABajar = new ArrayList<>();
                    for (Pasajero p : colectivo.getPasajeros()) {
                        if (p.getDestino().equals(paradaActual)) {
                            pasajerosABajar.add(p);
                        }
                    }
                    for (Pasajero p : pasajerosABajar) {
                        colectivo.bajarPasajero(p);
                        colectivo.registrarBajadaEnParada(i, p);
                        // Marcar que el pasajero llegó a su destino final
                        p.marcarComoLlegado();
                    }
                }

                // Agregar colectivo a la lista de utilizados
                colectivosUtilizados.add(colectivo);

                // Contar pasajeros que aún no llegaron a su destino
                pasajerosRestantes = 0;
                for (Pasajero p : pasajerosDeLaLinea) {
                    if (!p.llego()) {
                        pasajerosRestantes++;
                    }
                }

                numeroRecorrido++;
            }

            System.out.println("Línea " + linea.getCodigo() + " completada. Recorridos ejecutados: " + (numeroRecorrido - 1));

        }

        // 5. MOSTRAR RESULTADOS
        System.out.println("\n\n=== RESULTADOS FINALES ===");
        double indiceSatisfaccion = calculadorMetricas.calcularIndiceSatisfaccion(pasajeros);
        System.out.println("Índice de satisfacción: " + String.format("%.2f", indiceSatisfaccion));

        System.out.println("\nOcupación promedio por recorrido:");

        int n = 1;

        // Acumuladores para promedio por línea (usando net.datastructures)
        ChainHashMap<String, Double> sumaOcupacionPorLinea = new ChainHashMap<>();
        ChainHashMap<String, Integer> cantidadColectivosPorLinea = new ChainHashMap<>();

        for (Colectivo c : colectivosUtilizados) {
            double ocupacion = calculadorMetricas.calcularOcupacionPromedio(c, c.getLinea());
            String codigoLinea = c.getLinea().getCodigo();

            System.out.println("  Recorrido #" + n + " (línea " + codigoLinea + "): " + String.format("%.2f", ocupacion));
            n++;

            Double sumaActual = sumaOcupacionPorLinea.get(codigoLinea);
            if (sumaActual == null) {
                sumaActual = 0.0;
            }
            sumaOcupacionPorLinea.put(codigoLinea, sumaActual + ocupacion);

            Integer cantidadActual = cantidadColectivosPorLinea.get(codigoLinea);
            if (cantidadActual == null) {
                cantidadActual = 0;
            }
            cantidadColectivosPorLinea.put(codigoLinea, cantidadActual + 1);
        }

        System.out.println("\nOcupación promedio por línea:");
        for (Linea l : lineas.values()) {
            String codigo = l.getCodigo();
            Integer cantidad = cantidadColectivosPorLinea.get(codigo);

            if (cantidad == null || cantidad == 0) {
                System.out.println("  Línea " + codigo + ": 0.00 (sin recorridos)");
            } else {
                Double suma = sumaOcupacionPorLinea.get(codigo);
                if (suma == null) {
                    suma = 0.0;
                }
                double promedioLinea = suma / cantidad;
                System.out.println("  Línea " + codigo + ": " + String.format("%.2f", promedioLinea));
            }
        }

        //------------
        System.out.println("\n" + "=".repeat(60));
        System.out.println("VISUALIZACION DETALLADA DE RECORRIDOS");
        System.out.println("=".repeat(60));

        ImprimirRecorrido.imprimirRecorrido(colectivosUtilizados, lineas);
    }
}