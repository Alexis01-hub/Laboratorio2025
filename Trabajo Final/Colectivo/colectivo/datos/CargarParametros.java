package colectivo.datos;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Clase utilitaria para cargar los parámetros de configuración desde un archivo properties.
 * Permite obtener las rutas de los archivos de líneas y paradas configuradas,
 * así como la cantidad máxima de pasajeros.
 */
public class CargarParametros {
    
    private static String archivoLineas;
    private static String archivoParadas;
    private static int cantidadPasajeros;

    /**
     * Carga los parámetros desde el archivo 'config.properties'.
     * Asigna los valores de las propiedades 'linea', 'parada' y 'cantidadPasajeros' a las variables correspondientes.
     *
     * @throws IOException Si ocurre un error al leer el archivo o faltan parámetros requeridos.
     */
    public static void cargar() throws IOException {
        Properties prop = new Properties(); // Crea un objeto Properties para manejar las Propiedades
        // Intenta cargar el archivo de propiedades desde la ruta especificada
        try (InputStream input = new FileInputStream("config.properties")) {
            prop.load(input); // Carga las propiedades desde el archivo
            archivoLineas = prop.getProperty("linea"); // Obtiene la Propiedad 'linea' y la asigna a archivoLineas
            archivoParadas = prop.getProperty("parada"); // Obtiene la Propiedad 'parada' y la asigna a archivoParadas
            String cantidad = prop.getProperty("cantidadPasajeros"); // Obtiene la Propiedad 'cantidadPasajeros' y la asigna a cantidad
            
            // Validación básica de los parámetros cargados
            if (archivoLineas == null || archivoParadas == null || cantidad == null) {
                throw new IOException("Configuración incompleta en archivo properties");
            }
            // Intentar convertir la cantidad de pasajeros a un entero
            try {
                cantidadPasajeros = Integer.parseInt(cantidad); // Convierte la cadena a un entero
            } catch (NumberFormatException e) {
                throw new IOException("El valor de cantidadPasajeros no es un número válido", e);
            }
        }
    }

    /**
     * Obtiene la ruta del archivo de líneas configurada.
     *
     * @return Ruta del archivo de líneas.
     */
    public static String getArchivoLineas() {
        return archivoLineas;
    }

    /**
     * Obtiene la ruta del archivo de paradas configurada.
     *
     * @return Ruta del archivo de paradas.
     */
    public static String getArchivoParadas() {
        return archivoParadas;
    }

    /**
     * Obtiene la cantidad máxima de pasajeros configurada.
     *
     * @return Cantidad máxima de pasajeros.
     */
    public static int getCantidadPasajeros() {
        return cantidadPasajeros;
    }
}
