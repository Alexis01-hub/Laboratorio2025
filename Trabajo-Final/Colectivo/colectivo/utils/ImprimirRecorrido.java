package colectivo.utils;

import colectivo.modelo.Colectivo;
import colectivo.modelo.Linea;
import colectivo.modelo.Parada;

import net.datastructures.Map;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ImprimirRecorrido {

    public static void imprimirRecorrido(List<Colectivo> colectivos, Map<String, Linea> lineas) {
        Scanner scanner = new Scanner(System.in);
        boolean continuar = true;

        while (continuar) {
            System.out.println("Líneas disponibles:");
            for (Linea l : lineas.values()) {
                System.out.println("  Código: " + l.getCodigo());
            }

            Linea lineaSeleccionada = null;
            while (lineaSeleccionada == null) {
                System.out.print("\nIngrese el código de la línea que desea ver: ");
                String codigoSeleccionado = scanner.nextLine().trim();

                for (Linea l : lineas.values()) {
                    if (l.getCodigo().equalsIgnoreCase(codigoSeleccionado)) {
                        lineaSeleccionada = l;
                        break;
                    }
                }

                if (lineaSeleccionada == null) {
                    System.out.println("Código inválido. Intente nuevamente.");
                }
            }

            List<Colectivo> recorridosLinea = new ArrayList<>();
            for (Colectivo c : colectivos) {
                if (c.getLinea().getCodigo().equalsIgnoreCase(lineaSeleccionada.getCodigo())) {
                    recorridosLinea.add(c);
                }
            }

            if (recorridosLinea.isEmpty()) {
                System.out.println("No se encontraron recorridos para la línea seleccionada.");
            } else {
                System.out.println("\n============================================================");
                System.out.println("Línea " + lineaSeleccionada.getCodigo() + " - Recorridos registrados: " + recorridosLinea.size());
                System.out.println("============================================================\n");

                List<Parada> paradas = lineaSeleccionada.getParadas();

                for (int r = 0; r < recorridosLinea.size(); r++) {
                    Colectivo colectivo = recorridosLinea.get(r);
                    String idMostrado = colectivo.getId() == null ? ("Recorrido_" + (r + 1)) : colectivo.getId();

                    System.out.println("#################### " + idMostrado + " ####################");
                    int aBordo = 0;

                    for (int i = 0; i < paradas.size(); i++) {
                        Parada parada = paradas.get(i);

                        List<Integer> suben = colectivo.getSubidasEnParada(i);
                        List<Integer> bajan = colectivo.getBajadasEnParada(i);

                        aBordo += suben.size();
                        aBordo -= bajan.size();
                        if (aBordo < 0) {
                            aBordo = 0;
                        }

                        System.out.println("------------------------------------------------------------");
                        System.out.println("Parada " + (i + 1) + "/" + paradas.size() + ": " + parada.getDireccion());
                        System.out.println("Suben (" + suben.size() + "): " + suben);
                        System.out.println("Bajan (" + bajan.size() + "): " + bajan);
                        System.out.println("Pasajeros a bordo: " + aBordo);
                        System.out.println("------------------------------------------------------------");

                        if (i < paradas.size() - 1) {
                            System.out.print("Presione ENTER para continuar a la siguiente parada...");
                            scanner.nextLine();
                            System.out.println();
                        }
                    }

                    System.out.println("############ FIN " + idMostrado + " ############\n");
                }
            }

            System.out.print("¿Desea ver el recorrido de otra línea? (s/n): ");
            String respuesta = scanner.nextLine().trim().toLowerCase();
            if (!respuesta.equals("s") && !respuesta.equals("si") && !respuesta.equals("sí")) {
                continuar = false;
            }
            System.out.println();
        }

        scanner.close();
    }
}
