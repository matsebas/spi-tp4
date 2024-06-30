package controllers;

import exceptions.GlucoForecastException;
import models.EstimacionGlicosilada;
import models.Medicion;
import models.Paciente;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class HbA1cController {

    /**
     * Calcula la estimación de HbA1c para un {@link Paciente} y la registra en la base de datos
     *
     * @param paciente el {@link Paciente}
     * @return true si se ha podido registrar la estimación de HbA1c, false en caso contrario
     */
    public boolean calcularHbA1c(Paciente paciente) {

        try {
            // Obtener las mediciones de la base de datos
            List<Medicion> mediciones = paciente.getMediciones();
            // Comprobar que hay suficientes datos para realizar la estimación
            if (!enoughData(mediciones)) {
                return false;
            }

            double estimacionHbA1c = EstimacionGlicosilada.calcularEstimacion(mediciones);
            EstimacionGlicosilada estimacion = new EstimacionGlicosilada(null, paciente.getId(), estimacionHbA1c, LocalDate.now());
            estimacion.guardar();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al registrar la estimación de HbA1c: " + e.getMessage());
            return false;
        }
    }

    public double obtenerUltimaEstimacionHbA1c(Paciente paciente) throws GlucoForecastException {
        try {
            // Obtener las estimaciones de HbA1c del historial de la base de datos
            List<EstimacionGlicosilada> historialEstimaciones = paciente.getEstimacionesGlicosilada();

            if (historialEstimaciones.isEmpty()) {
                throw new GlucoForecastException("No hay suficientes datos para calcular la estimación de HbA1c.");
            }

            EstimacionGlicosilada ultimaEstimacion = historialEstimaciones.get(historialEstimaciones.size() - 1);
            return ultimaEstimacion.getValorCalculado();
        } catch (SQLException e) {
            throw new GlucoForecastException("Error al obtener la última estimación de HbA1c.", e);
        }
    }

    private boolean enoughData(List<Medicion> mediciones) {
        return mediciones.size() >= 3;
    }
}
