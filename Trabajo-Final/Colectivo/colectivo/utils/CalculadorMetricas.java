package colectivo.utils;

import colectivo.modelo.Colectivo;
import colectivo.modelo.Linea;
import colectivo.modelo.Parada;
import colectivo.modelo.Pasajero;
import java.util.List;

/**
 * Clase utilitaria para calcular métricas relacionadas con el sistema de transporte público.
 * Proporciona métodos para calcular el índice de satisfacción de los pasajeros
 * y la ocupación promedio de los colectivos en una línea.
 */

public class CalculadorMetricas {

    /**
     * Calcula el índice de satisfacción de los pasajeros basado en sus calificaciones.
     *
     * @param pasajeros Lista de pasajeros cuyos índices de satisfacción se calcularán.
     * @return El índice de satisfacción promedio, un valor entre 0.0 y 1.0.
     *         Devuelve 0.0 si la lista de pasajeros está vacía.
     */

    public double calcularIndiceSatisfaccion(List<Pasajero> pasajeros){
        if(pasajeros.isEmpty()){
            return 0.0; // Evita división por cero, si no hay pasajeros, el índice de satisfacción es 0
        }

        double sumaCalificaciones = 0.0; // Variable para acumular las calificaciones de los pasajeros
        for (Pasajero p : pasajeros) { // Itera sobre cada pasajero en
            sumaCalificaciones += p.getClasificacion(); // Suma la calificación del pasajero a la variable acumuladora
        }

        return sumaCalificaciones / (pasajeros.size() * 5.0); // Calcula el índice de satisfacción dividiendo la suma de calificaciones por el número total de pasajeros multiplicado por 5 (la calificación máxima)

    }

    /**
     * Calcula la ocupación promedio de un colectivo en una línea específica.
     *
     * @param colectivo El colectivo del cual se calculará la ocupación promedio.
     * @param linea La línea de transporte público asociada al colectivo.
     * @return La ocupación promedio del colectivo, un valor entre 0.0 y 1.0.
     *         Devuelve 0.0 si el colectivo o la línea son nulos, o si los datos son insuficientes.
     */

    public double calcularOcupacionPromedio(Colectivo colectivo, Linea linea){
        if (colectivo == null || linea == null){
            return 0.0; //
        }

        int capacidadMaxima = colectivo.getCantidadMaxima(); // Obtiene la capacidad máxima del colectivo
        if (capacidadMaxima <= 0){
            return 0.0;
        }

        List<Parada> paradas  = linea.getParadas();
        if (paradas.isEmpty() || paradas.size() < 2){
            return 0.0;
        }

        int cantidadTramos = paradas.size() - 1;
        double sumaOcupacion = 0.0;

        for (int i = 0; i < cantidadTramos; i++) {
            int pasajerosEnTramo = colectivo.getPasajerosEnTramo(i);
            sumaOcupacion += (double) pasajerosEnTramo / capacidadMaxima;
        }

        return sumaOcupacion / cantidadTramos;

    }
}
