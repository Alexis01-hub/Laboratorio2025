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
import net.datastructures.Map;
import colectivo.utils.ImprimirRecorrido;

/**
 * Clase principal que ejecuta la simulación del sistema de colectivos.
 * Lee la configuración, genera pasajeros aleatorios, crea colectivos por línea
 * y simula el recorrido de cada colectivo mostrando subidas y bajadas de
 * pasajeros.
 */
public class Simulador {
    public static void main(String[] args) throws Exception {

        // Leer configuración desde el archivo config.properties
        Properties config = CargarParametros.cargar();
        int cantidadPasajeros = Integer.parseInt(config.getProperty("cantidadPasajeros", "100"));
        String archivoParadas = config.getProperty("parada", "Colectivo/parada.txt");
        String archivoLineas = config.getProperty("linea", "Colectivo/linea.txt");
        int capacidadMaxima = Integer.parseInt(config.getProperty("capacidadMaximaColectivo", config.getProperty("capacidadMaxima", "30")));
        if (capacidadMaxima <= 0) {
            throw new IllegalArgumentException("capacidadMaximaColectivo debe ser mayor a 0");
        }

        // Cargar datos de paradas y líneas
        Map<Integer, Parada> paradas = CargadorDatos.cargarParadas(archivoParadas); // Carga las paradas desde el archivo
        Map<String, Linea> lineas = CargadorDatos.cargarLineas(archivoLineas, paradas); // Carga las líneas desde el archivo, asociando las paradas

        // Generar pasajeros aleatorios usando la cantidad definida en la configuración
        List<Pasajero> pasajeros = GeneradorPasajeros.generar(cantidadPasajeros, paradas);

        // Crear un colectivo por cada línea
        List<Colectivo> colectivos = new ArrayList<>(); // Lista para almacenar los colectivos creados
        for (Linea l : lineas.values()) {
            colectivos.add(new Colectivo(l, capacidadMaxima)); // Crea un nuevo colectivo para cada línea y lo agrega a la lista
        }

        // Asignar pasajeros a colectivos solo si la línea contiene origen y destino
        for (Colectivo c : colectivos) {
            for (Pasajero p : pasajeros) {
                List<Parada> paradasDeLaLinea = c.getLinea().getParadas(); // Obtiene las paradas de la línea del colectivo
                if (paradasDeLaLinea.contains(p.getOrigen()) && paradasDeLaLinea.contains(p.getDestino())) {
                    //valida capacidad
                    boolean pudoSubir = c.subirPasajero(p);
                    if (pudoSubir){
                        //determina si sube parado o sentado
                        int capacidadSentados = (int) (c.getCantidadMaxima() * 0.7); // 70% de la capacidad total para sentados
                        boolean viajoSentado = c.getPasajeros().size() <= capacidadSentados; // Si el número de pasajeros es menor o igual a la capacidad de sentados, viaja sentado
                        p.registrarSubida(viajoSentado); // Registra la subida del pasajero, indicando si viaja sentado o parado
                    } else {
                        // No subio al colectivo por estar lleno
                        p.incrementarColectivosEsperados();
                    }

                }
            }
        }

        // Mostrar las líneas disponibles y pedir al usuario que seleccione una
        ImprimirRecorrido.imprimirRecorrido(colectivos, lineas);
        System.out.println("Simulación finalizada.");
    }
}