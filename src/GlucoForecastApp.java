/**
 * @Alumno: Matias Sebastiao
 * @DNI: 31070095
 * @Legajo: VINF011605
 */

import models.Paciente;
import utils.UtilColores;
import views.RegistroDatosView;
import views.VisualizacionDatosView;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

/**
 * Clase principal de la aplicación GlucoForecast.
 *
 * @author <a href="https://github.com/matsebas">Matias Sebastiao</a>
 */
public class GlucoForecastApp {

    private final RegistroDatosView registroDatosView;
    private final VisualizacionDatosView visualizacionDatosView;
    private List<Paciente> pacientes;
    ;
    private Paciente pacienteSeleccionado;

    public GlucoForecastApp() {
        this.registroDatosView = new RegistroDatosView();
        this.visualizacionDatosView = new VisualizacionDatosView();
        try {
            this.pacientes = Paciente.listarTodos();
        } catch (SQLException e) {
            System.out.println();
            System.out.println(UtilColores.RED_BOLD_BRIGHT + "Error al conectar con la base de datos: " + e.getMessage() + UtilColores.RESET);
            System.out.println();
        }
    }

    public void iniciar() {
        Scanner scanner = new Scanner(System.in);
        boolean salir = false;

        while (!salir) {
            System.out.println(UtilColores.BLUE_BOLD_BRIGHT + "================ LISTADO DE PACIENTES ================" + UtilColores.RESET);
            System.out.println(UtilColores.WHITE_BOLD_BRIGHT + "> SELECCIONE UN PACIENTE:" + UtilColores.RESET);
            System.out.println("-------------------------");
            for (int i = 0; i < this.pacientes.size(); i++) {
                System.out.println(UtilColores.GREEN_BOLD_BRIGHT + "[" + (i + 1) + "] " + pacientes.get(i).getNombre() + " " + pacientes.get(i).getApellido() + UtilColores.RESET);
            }
            System.out.println("[" + (pacientes.size() + 1) + "] SALIR");
            System.out.println(UtilColores.BLUE_BOLD_BRIGHT + "======================================================" + UtilColores.RESET);

            int seleccionPaciente = scanner.nextInt();
            if (seleccionPaciente > 0 && seleccionPaciente <= pacientes.size()) {
                pacienteSeleccionado = pacientes.get(seleccionPaciente - 1);
                mostrarMenuPrincipal();
            } else if (seleccionPaciente == pacientes.size() + 1) {
                salir = true;
            } else {
                System.out.println(UtilColores.RED_BOLD_BRIGHT + "Opción no válida. Intente nuevamente." + UtilColores.RESET);
            }
        }
        scanner.close();
    }

    private void mostrarMenuPrincipal() {
        Scanner scanner = new Scanner(System.in);
        boolean salir = false;

        while (!salir) {
            System.out.println(UtilColores.BLUE_BOLD_BRIGHT + "=================== MENU PRINCIPAL ===================" + UtilColores.RESET);
            System.out.println(UtilColores.GREEN_BOLD_BRIGHT + "PACIENTE SELECCIONADO: " + UtilColores.YELLOW_BOLD_BRIGHT + pacienteSeleccionado.getNombre() + " " + pacienteSeleccionado.getApellido() + UtilColores.RESET);
            System.out.println("------------------------------------------------------");
            System.out.println(UtilColores.WHITE_BOLD_BRIGHT + "> SELECCIONE UNA OPCIÓN:" + UtilColores.RESET);
            System.out.println("------------------------");
            System.out.println(UtilColores.GREEN_BOLD_BRIGHT + "[1] Registrar datos" + UtilColores.RESET);
            System.out.println(UtilColores.GREEN_BOLD_BRIGHT + "[2] Visualizar resultado de HbA1c" + UtilColores.RESET);
            System.out.println(UtilColores.GREEN_BOLD_BRIGHT + "[3] Generar Reportes" + UtilColores.RESET);
            System.out.println("[4] VOLVER");
            System.out.println(UtilColores.BLUE_BOLD_BRIGHT + "======================================================" + UtilColores.RESET);

            int opcion = scanner.nextInt();
            switch (opcion) {
                case 1:
                    registroDatosView.registrarDatos(pacienteSeleccionado);
                    break;
                case 2:
                    visualizacionDatosView.visualizarResultadoHbA1c(pacienteSeleccionado);
                    break;
                case 3:
                    visualizacionDatosView.visualizarReporte(pacienteSeleccionado);
                    break;
                case 4:
                    salir = true;
                    break;
                default:
                    System.out.println(UtilColores.RED_BOLD_BRIGHT + "Opción no válida. Intente nuevamente." + UtilColores.RESET);
            }
        }
    }

    public static void main(String[] args) {
        GlucoForecastApp app = new GlucoForecastApp();
        app.iniciar();
    }
}
