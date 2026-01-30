package colectivo.utils;

import colectivo.modelo.Colectivo;
import colectivo.modelo.Linea;
import colectivo.modelo.Parada;
import colectivo.modelo.Pasajero;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import net.datastructures.Map;

public class ImprimirRecorrido {
    public static void imprimirRecorrido(List<Colectivo> colectivos, Map<String, Linea> lineas) {
        Scanner scanner = new Scanner(System.in);
        boolean continuar = true;
        while (continuar) {
            System.out.println("Líneas disponibles:");
            for (Linea l : lineas.values()) {
                System.out.println("  Código: " + l.getCodigo());
            }
            String codigoSeleccionado = null;
            Linea lineaSeleccionada = null;
            while (lineaSeleccionada == null) {
                System.out.print("\nIngrese el código de la línea que desea ver: ");
                codigoSeleccionado = scanner.nextLine();
                lineaSeleccionada = lineas.get(codigoSeleccionado);
                if (lineaSeleccionada == null) {
                    System.out.println("Código inválido. Intente nuevamente.");
                }
            }

            Colectivo colectivoSeleccionado = null;
            for (Colectivo c : colectivos) {
                if (c.getLinea().getCodigo().equals(lineaSeleccionada.getCodigo())) {
                    colectivoSeleccionado = c;
                    break;
                }
            }

            if (colectivoSeleccionado != null) {
                System.out.println("\n============================================================");
                System.out.println("Recorrido colectivo línea: " + colectivoSeleccionado.getLinea().getCodigo() + " (paradas: " + colectivoSeleccionado.getLinea().getParadas().size() + ")");
                List<Parada> paradasDeLaLinea = colectivoSeleccionado.getLinea().getParadas();
                List<Pasajero> pasajerosEnColectivo = new ArrayList<>();
                for (Parada parada : paradasDeLaLinea) {
                    List<Pasajero> suben = new ArrayList<>();
                    for (Pasajero p : new ArrayList<>(colectivoSeleccionado.getPasajeros())) {
                        if (p.getOrigen().equals(parada)) {
                            suben.add(p);
                            pasajerosEnColectivo.add(p);
                        }
                    }
                    List<Pasajero> bajan = new ArrayList<>();
                    for (Pasajero p : new ArrayList<>(pasajerosEnColectivo)) {
                        if (p.getDestino().equals(parada)) {
                            bajan.add(p);
                            pasajerosEnColectivo.remove(p);
                        }
                    }
                    System.out.println("------------------------------------------------------------");
                    System.out.println("  Parada: " + parada.getDireccion());
                    System.out.println("    Suben: " + suben.size());
                    System.out.println("    Bajan: " + bajan.size());
                    System.out.println("    Pasajeros a bordo: " + pasajerosEnColectivo.size());
                }
                if (!pasajerosEnColectivo.isEmpty()) {
                    System.out.println(">>> Atención: Quedaron " + pasajerosEnColectivo.size()
                            + " pasajeros a bordo al finalizar el recorrido. Fueron bajados forzosamente.");
                    pasajerosEnColectivo.clear();
                }
                System.out.println("============================================================\n");
            } else {
                System.out.println("No se encontró el colectivo para la línea seleccionada.");
            }

            System.out.print("¿Desea ver el recorrido de otra línea? (s/n): ");
            String respuesta = scanner.nextLine().trim().toLowerCase();
            if (!respuesta.equals("s") && !respuesta.equals("si") && !respuesta.equals("sí")) {
                continuar = false;
            }
        }
        scanner.close();
    }
}
