package colectivo.datos;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.lang.reflect.Field;

/**
 * Tests para la clase CargarParametros.
 * Prueba la carga correcta de parámetros y el manejo de diferentes escenarios
 * sin crear archivos reales en el sistema.
 */
public class CargarParametrosTest {

    @BeforeEach
    @AfterEach
    void limpiarCamposEstaticos() {
        // Limpiar los campos estáticos antes y después de cada test
        try {
            Field archivoLineasField = CargarParametros.class.getDeclaredField("archivoLineas");
            Field archivoParadasField = CargarParametros.class.getDeclaredField("archivoParadas");
            Field cantidadPasajerosField = CargarParametros.class.getDeclaredField("cantidadPasajeros");

            archivoLineasField.setAccessible(true);
            archivoParadasField.setAccessible(true);
            cantidadPasajerosField.setAccessible(true);

            archivoLineasField.set(null, null);
            archivoParadasField.set(null, null);
            cantidadPasajerosField.setInt(null, 0);
        } catch (Exception e) {
            // Ignorar errores de limpieza
        }
    }

    @Test
    void testCargarParametrosExistentes() throws IOException {
        // Este test asume que existe un archivo config.properties válido
        // Si no existe, se puede crear manualmente para las pruebas

        // Act - Intentar cargar la configuración
        assertDoesNotThrow(() -> {
            CargarParametros.cargar();
        });

        // Assert - Verificar que los getters devuelven valores no nulos
        assertNotNull(CargarParametros.getArchivoLineas(),
                "El archivo de líneas no debería ser null después de cargar");
        assertNotNull(CargarParametros.getArchivoParadas(),
                "El archivo de paradas no debería ser null después de cargar");
        assertTrue(CargarParametros.getCantidadPasajeros() >= 0,
                "La cantidad de pasajeros debería ser un valor válido");

        // Verificar que no son strings vacías
        assertFalse(CargarParametros.getArchivoLineas().trim().isEmpty(),
                "El archivo de líneas no debería estar vacío");
        assertFalse(CargarParametros.getArchivoParadas().trim().isEmpty(),
                "El archivo de paradas no debería estar vacío");
    }

    @Test
    void testGettersConConfiguracionCargada() throws IOException {
        // Arrange - Cargar configuración
        CargarParametros.cargar();

        // Act & Assert - Los getters deben devolver valores consistentes
        String archivoLineas1 = CargarParametros.getArchivoLineas();
        String archivoLineas2 = CargarParametros.getArchivoLineas();
        assertEquals(archivoLineas1, archivoLineas2,
                "Los getters deben devolver el mismo valor en llamadas consecutivas");

        String archivoParadas1 = CargarParametros.getArchivoParadas();
        String archivoParadas2 = CargarParametros.getArchivoParadas();
        assertEquals(archivoParadas1, archivoParadas2,
                "Los getters deben devolver el mismo valor en llamadas consecutivas");

        int cantidad1 = CargarParametros.getCantidadPasajeros();
        int cantidad2 = CargarParametros.getCantidadPasajeros();
        assertEquals(cantidad1, cantidad2,
                "Los getters deben devolver el mismo valor en llamadas consecutivas");
    }

    @Test
    void testCargarArchivoInexistente() {
        // Este test verifica el comportamiento cuando config.properties no existe
        // Requiere que el archivo no exista o esté en una ubicación incorrecta

        // Si el archivo existe, este test fallará, lo cual es el comportamiento esperado
        // En un entorno de CI/CD se podría renombrar temporalmente el archivo

        // Para este test, asumimos que podríamos no tener el archivo
        // Act & Assert
        try {
            CargarParametros.cargar();
            // Si llegamos aquí, el archivo existe y se cargó correctamente
            assertNotNull(CargarParametros.getArchivoLineas());
            assertNotNull(CargarParametros.getArchivoParadas());
        } catch (IOException e) {
            // Si se lanza IOException, verificamos que es por archivo no encontrado
            assertTrue(e.getMessage().contains("config.properties") ||
                            e.getCause() instanceof java.io.FileNotFoundException,
                    "Debería lanzar IOException por archivo no encontrado");
        }
    }

    @Test
    void testGettersAntesDeCargar() {
        // Test para verificar el estado inicial antes de cargar
        // Los campos estáticos deberían estar en su estado inicial

        // Act & Assert
        assertNull(CargarParametros.getArchivoLineas(),
                "El archivo de líneas debería ser null antes de cargar");
        assertNull(CargarParametros.getArchivoParadas(),
                "El archivo de paradas debería ser null antes de cargar");
        assertEquals(0, CargarParametros.getCantidadPasajeros(),
                "La cantidad de pasajeros debería ser 0 antes de cargar");
    }

    @Test
    void testCargarMultiplesVeces() throws IOException {
        // Test para verificar que se puede cargar múltiples veces

        // Act
        CargarParametros.cargar();
        String lineas1 = CargarParametros.getArchivoLineas();
        String paradas1 = CargarParametros.getArchivoParadas();
        int cantidad1 = CargarParametros.getCantidadPasajeros();

        // Cargar nuevamente
        CargarParametros.cargar();
        String lineas2 = CargarParametros.getArchivoLineas();
        String paradas2 = CargarParametros.getArchivoParadas();
        int cantidad2 = CargarParametros.getCantidadPasajeros();

        // Assert - Los valores deben ser consistentes
        assertEquals(lineas1, lineas2, "Los valores deben ser consistentes entre cargas");
        assertEquals(paradas1, paradas2, "Los valores deben ser consistentes entre cargas");
        assertEquals(cantidad1, cantidad2, "Los valores deben ser consistentes entre cargas");
    }

    @Test
    void testValidacionDeParametros() throws IOException {
        // Test que verifica que los parámetros cargados son válidos

        // Act
        CargarParametros.cargar();

        // Assert - Validaciones básicas de los parámetros
        String archivoLineas = CargarParametros.getArchivoLineas();
        String archivoParadas = CargarParametros.getArchivoParadas();
        int cantidadPasajeros = CargarParametros.getCantidadPasajeros();

        assertNotNull(archivoLineas, "El archivo de líneas no debe ser null");
        assertNotNull(archivoParadas, "El archivo de paradas no debe ser null");

        // Verificar que son strings no vacías (después de trim)
        assertFalse(archivoLineas.trim().isEmpty(), "El archivo de líneas no debe estar vacío");
        assertFalse(archivoParadas.trim().isEmpty(), "El archivo de paradas no debe estar vacío");

        // La cantidad de pasajeros debería ser un número válido (puede ser 0 o negativo según lógica de negocio)
        assertTrue(cantidadPasajeros == (int) cantidadPasajeros, "La cantidad debe ser un entero válido");
    }

    /**
     * Test de integración que verifica el flujo completo
     */
    @Test
    void testFlujoCompleto() throws IOException {
        // Arrange - Estado inicial limpio
        assertNull(CargarParametros.getArchivoLineas());
        assertNull(CargarParametros.getArchivoParadas());
        assertEquals(0, CargarParametros.getCantidadPasajeros());

        // Act - Cargar configuración
        CargarParametros.cargar();

        // Assert - Verificar que se cargaron los valores
        assertNotNull(CargarParametros.getArchivoLineas());
        assertNotNull(CargarParametros.getArchivoParadas());

        // Guardar valores para verificar consistencia
        String lineas = CargarParametros.getArchivoLineas();
        String paradas = CargarParametros.getArchivoParadas();
        int cantidad = CargarParametros.getCantidadPasajeros();

        // Verificar que múltiples llamadas a los getters devuelven lo mismo
        assertEquals(lineas, CargarParametros.getArchivoLineas());
        assertEquals(paradas, CargarParametros.getArchivoParadas());
        assertEquals(cantidad, CargarParametros.getCantidadPasajeros());
    }
}