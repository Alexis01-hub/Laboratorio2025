package colectivoTest;

import colectivo.datos.CargadorDatos;
import colectivo.modelo.Linea;
import colectivo.modelo.Parada;
import net.datastructures.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests de CargadorDatos")
class CargadorDatosTest {

    @TempDir
    Path carpetaTemporal;

    private String rutaParadas;
    private String rutaLineas;

    @BeforeEach
    void setUp() throws IOException {
        // Archivo temporal de paradas
        Path archivoParadas = carpetaTemporal.resolve("paradas.txt");
        try (PrintWriter pw = new PrintWriter(archivoParadas.toFile())) {
            pw.println("# Comentario que debe ignorarse");
            pw.println("1;Av. Siempre Viva, 123;");
            pw.println("2;Calle Falsa, 456;");
            pw.println("3;Belgrano, 800;");
            pw.println("");  // línea vacía que debe ignorarse
        }
        rutaParadas = archivoParadas.toString();

        // Archivo temporal de líneas
        Path archivoLineas = carpetaTemporal.resolve("lineas.txt");
        try (PrintWriter pw = new PrintWriter(archivoLineas.toFile())) {
            pw.println("# Comentario que debe ignorarse");
            pw.println("L1;1;2;3;");
            pw.println("L2;3;2;");
        }
        rutaLineas = archivoLineas.toString();
    }

    // ── cargarParadas ──────────────────────────────────────────────────────────

    @Test
    @DisplayName("cargarParadas carga la cantidad correcta de paradas")
    void cargarParadasCargaCantidadCorrecta() throws FileNotFoundException {
        Map<Integer, Parada> paradas = CargadorDatos.cargarParadas(rutaParadas);
        assertEquals(3, paradas.size()); // se esperan 3 paradas válidas (id 1, 2 y 3), líneas con comentarios y vacías se ignoran
    }

    @Test
    @DisplayName("cargarParadas asigna el id correcto a cada parada")
    void cargarParadasAsignaIdCorrecto() throws FileNotFoundException {
        Map<Integer, Parada> paradas = CargadorDatos.cargarParadas(rutaParadas);
        assertNotNull(paradas.get(1)); // debería existir la parada con id 1
        assertNotNull(paradas.get(2)); // debería existir la parada con id 2
        assertNotNull(paradas.get(3)); // debería existir la parada con id 3
    }

    @Test
    @DisplayName("cargarParadas asigna la dirección correcta")
    void cargarParadasAsignaDireccionCorrecta() throws FileNotFoundException {
        Map<Integer, Parada> paradas = CargadorDatos.cargarParadas(rutaParadas);
        assertEquals("Av. Siempre Viva, 123", paradas.get(1).getDireccion()); // dirección de la parada con id 1
    }

    @Test
    @DisplayName("cargarParadas ignora comentarios y líneas vacías")
    void cargarParadasIgnoraComentariosYLineasVacias() throws FileNotFoundException {
        Map<Integer, Parada> paradas = CargadorDatos.cargarParadas(rutaParadas);
        assertNull(paradas.get(0));   // id 0 no existe, línea con comentario se ignora
        assertEquals(3, paradas.size()); // solo se cargan las 3 paradas válidas, líneas vacías se ignoran
    }

    @Test
    @DisplayName("cargarParadas lanza FileNotFoundException si el archivo no existe")
    void cargarParadasLanzaExcepcionSiNoExiste() {
        assertThrows(FileNotFoundException.class,
                () -> CargadorDatos.cargarParadas("ruta/inexistente.txt")); // se espera que lance FileNotFoundException si el archivo de paradas no existe
    }

    // ── cargarLineas ───────────────────────────────────────────────────────────

    @Test
    @DisplayName("cargarLineas carga la cantidad correcta de líneas")
    void cargarLineasCargaCantidadCorrecta() throws FileNotFoundException {
        Map<Integer, Parada> paradas = CargadorDatos.cargarParadas(rutaParadas);
        Map<String, Linea> lineas = CargadorDatos.cargarLineas(rutaLineas, paradas);
        assertEquals(2, lineas.size()); // L1 y L2 son las unicas lineas validas
    }

    @Test
    @DisplayName("cargarLineas asigna el código correcto a cada línea")
    void cargarLineasAsignaCodigoCorrecto() throws FileNotFoundException {
        Map<Integer, Parada> paradas = CargadorDatos.cargarParadas(rutaParadas);
        Map<String, Linea> lineas = CargadorDatos.cargarLineas(rutaLineas, paradas);
        assertNotNull(lineas.get("L1")); // L1 es una línea válida
        assertNotNull(lineas.get("L2")); // L2 es una línea válida
    }

    @Test
    @DisplayName("cargarLineas asocia las paradas en el orden correcto")
    void cargarLineasAsociaParadasEnOrden() throws FileNotFoundException {
        Map<Integer, Parada> paradas = CargadorDatos.cargarParadas(rutaParadas);
        Map<String, Linea> lineas = CargadorDatos.cargarLineas(rutaLineas, paradas);
        Linea l1 = lineas.get("L1");
        assertEquals(3, l1.getParadas().size()); // L1 tiene 3 paradas
        assertEquals(1, l1.getParadas().get(0).getId()); // primera parada de L1 es la parada con id 1
        assertEquals(2, l1.getParadas().get(1).getId()); // segunda parada de L1 es la parada con id 2
        assertEquals(3, l1.getParadas().get(2).getId()); // tercera parada de L1 es la parada con id 3
    }

    @Test
    @DisplayName("cargarLineas ignora ids de paradas que no existen en el mapa")
    void cargarLineasIgnoraIdsInexistentes() throws IOException {
        Path archivoConIdInvalido = carpetaTemporal.resolve("lineas_invalidas.txt");
        try (PrintWriter pw = new PrintWriter(archivoConIdInvalido.toFile())) {
            pw.println("L9;1;99;2;");  // parada 99 no existe
        }
        Map<Integer, Parada> paradas = CargadorDatos.cargarParadas(rutaParadas);
        Map<String, Linea> lineas = CargadorDatos.cargarLineas(archivoConIdInvalido.toString(), paradas);
        // solo se cargan parada 1 y 2 (la 99 se ignora)
        assertEquals(2, lineas.get("L9").getParadas().size()); // se espera que solo se asocien las paradas válidas (1 y 2), la parada 99 se ignora
    }

    @Test
    @DisplayName("cargarLineas lanza FileNotFoundException si el archivo no existe")
    void cargarLineasLanzaExcepcionSiNoExiste() throws FileNotFoundException {
        Map<Integer, Parada> paradas = CargadorDatos.cargarParadas(rutaParadas);
        assertThrows(FileNotFoundException.class,
                () -> CargadorDatos.cargarLineas("ruta/inexistente.txt", paradas)); // se espera que lance FileNotFoundException si el archivo de líneas no existe
    }
}

