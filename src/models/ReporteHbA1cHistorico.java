package models;

import exceptions.GlucoForecastException;

import java.sql.SQLException;
import java.util.List;

/**
 * Clase que representa el reporte de HbA1c historico para un paciente
 */
public class ReporteHbA1cHistorico extends Reporte {

    public ReporteHbA1cHistorico(Paciente paciente) {
        super(paciente, ReporteTipo.HB1AC_HISTORICO);
    }

    /**
     * Genera una cadena con formato JSON que representa las estimaciones históricas de HbA1c para un {@link Paciente} dado
     *
     * @return Reporte generado
     */
    @Override
    protected String generarDatos() throws GlucoForecastException {
        try {
            // Obtiene las estimaciones de HbA1c del paciente
            List<EstimacionGlicosilada> estimaciones = this.getPaciente().getEstimacionesGlicosilada();
            StringBuilder datos = new StringBuilder("[");
            for (EstimacionGlicosilada estimacion : estimaciones) {
                datos.append(String.format("{\"fecha\": \"%s\", \"valor\": %.2f},", estimacion.getFechaCalculo(), estimacion.getValorCalculado()));
            }
            if (datos.length() > 1) {
                datos.deleteCharAt(datos.length() - 1);
            }
            datos.append("]");
            return datos.toString();
        } catch (SQLException e) {
            throw new GlucoForecastException("Error al generar el reporte de HbA1c", e);
        }
    }
}