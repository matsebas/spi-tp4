package controllers;

import exceptions.GlucoForecastException;
import models.*;

public class ReportesController {

    public Reporte generarReporte(Paciente paciente, ReporteTipo tipoReporte) {
        try {
            Reporte reporte;
            switch (tipoReporte) {
                case RANGOS_GLUCEMIA:
                    reporte = new ReporteRangosGlucemia(paciente);
                    break;
                case HB1AC_HISTORICO:
                    reporte = new ReporteHbA1cHistorico(paciente);
                    break;
                case PROMEDIOS_GLUCEMIA:
                    reporte = new ReportePromediosGlucemia(paciente);
                    break;
                default:
                    System.out.println("Reporte No implementado o no disponible.");
                    return null;
            }
            // Procesar el reporte para generar los datos y guardarlos en la base de datos
            reporte.procesar();
            return reporte;
        } catch (GlucoForecastException e) {
            System.out.println("Error al generar el reporte: " + e.getMessage());
            return null;
        }
    }

    public void mostrarReporte(Reporte reporte) {
        if (reporte != null) {
            System.out.println("Reporte #" + reporte.getId() + ": " + reporte.getTipoReporte());
            System.out.println("Fecha: " + reporte.getFechaGeneracion());
            System.out.println("Datos: " + reporte.getDatos());
        }
    }
}
