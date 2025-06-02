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
        Properties prop = new Properties();
        try (InputStream input = new FileInputStream("config.properties")) {
            prop.load(input); 
            archivoLineas = prop.getProperty("linea");
            archivoParadas = prop.getProperty("parada");
            String cantidad = prop.getProperty("cantidadPasajeros");
            
            // Validación básica de los parámetros cargados
            if (archivoLineas == null || archivoParadas == null || cantidad == null) {
                throw new IOException("Configuración incompleta en archivo properties");
            }
            // Intentar convertir la cantidad de pasajeros a un entero
            try {
                cantidadPasajeros = Integer.parseInt(cantidad);
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
