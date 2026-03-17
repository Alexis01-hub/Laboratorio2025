package colectivo.utils;

import colectivo.modelo.Colectivo;
import colectivo.modelo.Linea;
import colectivo.modelo.Parada;
import colectivo.modelo.Pasajero;
import java.util.List;


public class CalculadorMetricas {
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
