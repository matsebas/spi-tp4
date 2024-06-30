package views;

import controllers.HbA1cController;
import controllers.ReportesController;
import exceptions.GlucoForecastException;
import models.Paciente;
import models.Reporte;
import models.ReporteTipo;
import utils.UtilColores;

import java.util.List;
import java.util.Scanner;

public class VisualizacionDatosView {

    private final ReportesController reportesController;
    private final HbA1cController hba1cController;
    private final Scanner scanner;

    public VisualizacionDatosView() {
        this.hba1cController = new HbA1cController();
        this.reportesController = new ReportesController();
        this.scanner = new Scanner(System.in);
    }

    public void visualizarResultadoHbA1c(Paciente paciente) {
        try {
            double resultadoHbA1c = hba1cController.obtenerUltimaEstimacionHbA1c(paciente);
            System.out.println(UtilColores.YELLOW_BOLD_BRIGHT + "> HbA1c ESTIMADA: " + resultadoHbA1c + "%" + UtilColores.RESET);
        } catch (GlucoForecastException e) {
            System.err.println(UtilColores.RED_BOLD_BRIGHT + e.getMessage() + UtilColores.RESET);
        }
    }

    public void visualizarReporte(Paciente paciente) {
        System.out.println(UtilColores.WHITE_BOLD_BRIGHT + "> SELECCIONE EL TIPO DE REPORTE:" + UtilColores.RESET);
        List<ReporteTipo> reportesHabilitados = Reporte.reportesHabilitados();
        for (int i = 0; i < reportesHabilitados.size(); i++) {
            System.out.println(UtilColores.GREEN_BOLD_BRIGHT + "[" + (i + 1) + "] " + reportesHabilitados.get(i) + UtilColores.RESET);
        }

        int opcion = scanner.nextInt();
        scanner.nextLine(); // Consumir el salto de línea
        if (opcion < 1 || opcion > reportesHabilitados.size()) {
            System.out.println(UtilColores.RED_BOLD_BRIGHT + "> Opción no válida." + UtilColores.RESET);
            return;
        }

        ReporteTipo tipoReporte = reportesHabilitados.get(opcion - 1);
        Reporte reporte = reportesController.generarReporte(paciente, tipoReporte);
        reportesController.mostrarReporte(reporte);
    }
}
