package views;

import controllers.MedicionesController;
import exceptions.GlucoForecastException;
import models.Paciente;
import utils.UtilColores;

import java.util.Scanner;

public class RegistroDatosView {

    private final MedicionesController medicionesController;

    public RegistroDatosView() {
        this.medicionesController = new MedicionesController();
    }

    public void registrarDatos(Paciente paciente) {
        Scanner scanner = new Scanner(System.in);

        System.out.println(UtilColores.WHITE_BOLD_BRIGHT + "> INGRESE EL TIPO DE DATO QUE DESEE REGISTRAR:" + UtilColores.RESET);
        System.out.println(UtilColores.GREEN_BOLD_BRIGHT + "[1] Glucemia" + UtilColores.RESET);
        System.out.println(UtilColores.GREEN_BOLD_BRIGHT + "[2] Carbohidratos" + UtilColores.RESET);
        System.out.println(UtilColores.GREEN_BOLD_BRIGHT + "[3] Insulina\n" + UtilColores.RESET);
        int tipoDato = scanner.nextInt();
        scanner.nextLine();

        try {
            switch (tipoDato) {
                case 1:
                    System.out.println(UtilColores.WHITE_BOLD_BRIGHT + "> Ingrese valor de glucemia:" + UtilColores.RESET);
                    double glucemia = scanner.nextDouble();
                    scanner.nextLine();
                    System.out.println(UtilColores.WHITE_BOLD_BRIGHT + "> Ingrese descripción:" + UtilColores.RESET);
                    String descripcionGlucemia = scanner.nextLine();
                    System.out.println(UtilColores.WHITE_BOLD_BRIGHT + "> Ingrese tags:" + UtilColores.RESET);
                    String tagsGlucemia = scanner.nextLine();
                    medicionesController.registrarGlucemia(paciente, glucemia, descripcionGlucemia, tagsGlucemia);
                    System.out.println(UtilColores.GREEN_BOLD_BRIGHT + "Datos de glucemia registrados correctamente." + UtilColores.RESET);
                    break;
                case 2:
                    System.out.println(UtilColores.WHITE_BOLD_BRIGHT + "> Ingrese cantidad de carbohidratos:" + UtilColores.RESET);
                    double carbohidratos = scanner.nextDouble();
                    scanner.nextLine();
                    System.out.println(UtilColores.WHITE_BOLD_BRIGHT + "> Ingrese descripción:" + UtilColores.RESET);
                    String descripcionCarbohidratos = scanner.nextLine();
                    medicionesController.registrarCarbohidratos(paciente, carbohidratos, descripcionCarbohidratos);
                    System.out.println(UtilColores.GREEN_BOLD_BRIGHT + "Datos de carbohidratos registrados correctamente." + UtilColores.RESET);
                    break;
                case 3:
                    System.out.println(UtilColores.WHITE_BOLD_BRIGHT + "> Ingrese dosis de insulina de comida:" + UtilColores.RESET);
                    double insulinaComida = scanner.nextDouble();
                    System.out.println(UtilColores.WHITE_BOLD_BRIGHT + "> Ingrese dosis de insulina de corrección:" + UtilColores.RESET);
                    double insulinaCorreccion = scanner.nextDouble();
                    System.out.println(UtilColores.WHITE_BOLD_BRIGHT + "> Ingrese dosis de insulina lenta:" + UtilColores.RESET);
                    double insulinaLenta = scanner.nextDouble();
                    medicionesController.registrarInsulina(paciente, insulinaComida, insulinaCorreccion, insulinaLenta);
                    System.out.println(UtilColores.GREEN_BOLD_BRIGHT + "Datos de insulina registrados correctamente." + UtilColores.RESET);
                    break;
                default:
                    System.out.println(UtilColores.RED_BOLD_BRIGHT + "Tipo de dato no válido." + UtilColores.RESET);
            }
        } catch (GlucoForecastException e) {
            System.err.println(UtilColores.RED_BOLD_BRIGHT + e.getMessage() + UtilColores.RESET);
        }
    }
}
