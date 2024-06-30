package models;

import exceptions.GlucoForecastException;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Clase que representa el reporte de promedios de glucemia para un paciente
 */
public class ReportePromediosGlucemia extends Reporte {

    public ReportePromediosGlucemia(Paciente paciente) {
        super(paciente, ReporteTipo.PROMEDIOS_GLUCEMIA);
    }

    @Override
    public String generarDatos() throws GlucoForecastException {
        try {
            List<Medicion> mediciones = this.getPaciente().getMediciones();
            return mediciones.stream()
                    .collect(Collectors.groupingBy(m -> m.getFechaHora().getHour(),
                            Collectors.averagingDouble(Medicion::getGlucemia)))
                    .entrySet().stream()
                    .map(entry -> String.format("{\"hora\": %d, \"promedio\": %.2f}", entry.getKey(), entry.getValue()))
                    .collect(Collectors.joining(",", "[", "]"));
        } catch (SQLException e) {
            throw new GlucoForecastException("Error al generar el reporte de promedios", e);
        }
    }
}
