package tests;

import controllers.HbA1cController;
import controllers.MedicionesController;
import exceptions.GlucoForecastException;
import models.Paciente;

import java.sql.SQLException;
import java.time.LocalDate;

/**
 * Clase que contiene las pruebas de la clase GlucoForecast
 */
public class GlucoForecastTests {

    public static void main(String[] args) {
        GlucoForecastTests tests = new GlucoForecastTests();
        tests.setUp();
        tests.testRegistrarGlucemiaValida();
        tests.testRegistrarGlucemiaInvalida();
        tests.testRegistrarCarbohidratos();
        tests.testRegistrarInsulina();
        tests.testCalculoHbA1c();
        tests.testCalculoHbA1cInsuficiente();
    }

    private Paciente paciente;
    private MedicionesController medicionesController;
    private HbA1cController hba1cController;

    public void setUp() {
        paciente = new Paciente(1L, "Lionel", "Messi", "lionel.messi@gmail.com", "123456789", LocalDate.of(1986, 5, 24), 35.5, LocalDate.now());
        medicionesController = new MedicionesController();
        hba1cController = new HbA1cController();
    }

    public void testRegistrarGlucemiaValida() {
        try {
            medicionesController.registrarGlucemia(paciente, 120, "Desayuno", "DESAYUNO");
            assert paciente.getMediciones().size() == 1 : "Error: registro de glucemia válida falló.";
            System.out.println("testRegistrarGlucemiaValida pasó.");
        } catch (AssertionError | GlucoForecastException | SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public void testRegistrarGlucemiaInvalida() {
        try {
            medicionesController.registrarGlucemia(paciente, 700, "Desayuno", "DESAYUNO");
            System.err.println("Error: testRegistrarGlucemiaInvalida no lanzó excepción.");
        } catch (GlucoForecastException e) {
            System.out.println("testRegistrarGlucemiaInvalida pasó.");
        }
    }

    public void testRegistrarCarbohidratos() {
        try {
            medicionesController.registrarCarbohidratos(paciente, 50, "Almuerzo");
            assert paciente.getMediciones().size() == 1 : "Error: registro de carbohidratos falló.";
            System.out.println("testRegistrarCarbohidratos pasó.");
        } catch (AssertionError | GlucoForecastException | SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public void testRegistrarInsulina() {
        try {
            medicionesController.registrarInsulina(paciente, 4, 2, 1);
            assert paciente.getMediciones().size() == 1 : "Error: registro de insulina falló.";
            System.out.println("testRegistrarInsulina pasó.");
        } catch (AssertionError | GlucoForecastException | SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public void testCalculoHbA1c() {
        try {
            medicionesController.registrarGlucemia(paciente, 90, "Desayuno", "DESAYUNO");
            medicionesController.registrarGlucemia(paciente, 140, "Almuerzo", "ALMUERZO");
            medicionesController.registrarGlucemia(paciente, 180, "Cena", "CENA");

            boolean resultado = hba1cController.calcularHbA1c(paciente);
            assert resultado : "Error: cálculo de HbA1c falló.";
            double ultimaEstimacion = hba1cController.obtenerUltimaEstimacionHbA1c(paciente);

            assert ultimaEstimacion >= 7.0 && ultimaEstimacion <= 7.5 : "Error: cálculo de HbA1c fuera del rango esperado.";
            System.out.println("testCalculoHbA1c pasó.");
        } catch (AssertionError | GlucoForecastException e) {
            System.err.println(e.getMessage());
        }
    }

    public void testCalculoHbA1cInsuficiente() {
        try {
            paciente.getMediciones().clear();
            medicionesController.registrarGlucemia(paciente, 120, "Desayuno", "DESAYUNO");

            boolean resultado = hba1cController.calcularHbA1c(paciente);
            assert !resultado : "Error: testCalculoHbA1cInsuficiente no detectó datos insuficientes.";
            System.out.println("testCalculoHbA1cInsuficiente pasó.");
        } catch (GlucoForecastException | SQLException e) {
            System.err.println(e.getMessage());
        }
    }
}
