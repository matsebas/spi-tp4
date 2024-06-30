package controllers;

import exceptions.GlucoForecastException;
import models.Medicion;
import models.Paciente;

import java.sql.SQLException;
import java.time.LocalDateTime;


public class MedicionesController {

    /**
     * Registra una {@link Medicion} de glucemia relacionada a un {@link Paciente} en la base de datos
     *
     * @param paciente el {@link Paciente}
     * @param glucemia el valor de glucemia
     * @param notas    notas (opcional)
     * @param tags     tags (opcional)
     * @throws GlucoForecastException si el valor de glucemia es incorrecto o si hay un error de base de datos
     */
    public void registrarGlucemia(Paciente paciente, double glucemia, String notas, String tags) throws GlucoForecastException {
        if (glucemia < 30 || glucemia > 600) {
            throw new GlucoForecastException("Valor de glucemia fuera de rango.");
        }
        try {
            Medicion medicion = new Medicion(null, paciente.getId(), LocalDateTime.now(), glucemia, 0, 0, 0, "", 0, notas, tags);
            // Guarda la medición en la base de datos
            medicion.guardar();

            // Llamada al controlador de HbA1c para calcular la nueva estimación
            HbA1cController controladorHbA1c = new HbA1cController();
            controladorHbA1c.calcularHbA1c(paciente);
        } catch (SQLException e) {
            throw new GlucoForecastException("Error al registrar la medición de glucemia", e);
        }
    }

    /**
     * Registra una {@link Medicion} de carbohidratos relacionada a un {@link Paciente} en la base de datos
     *
     * @param paciente          el {@link Paciente}
     * @param carbohidratos     el valor de carbohidratos
     * @param descripcionComida la descripción de la comida (opcional)
     * @throws GlucoForecastException si el valor de carbohidratos es incorrecto o si hay un error de base de datos
     */
    public void registrarCarbohidratos(Paciente paciente, double carbohidratos, String descripcionComida) throws GlucoForecastException {
        if (carbohidratos < 0) {
            throw new GlucoForecastException("Cantidad de carbohidratos no puede ser negativa.");
        }

        try {
            Medicion medicion = new Medicion(null, paciente.getId(), LocalDateTime.now(), 0, carbohidratos, 0, 0, descripcionComida, 0, "", "");
            // Guarda la medición en la base de datos
            medicion.guardar();
        } catch (SQLException e) {
            throw new GlucoForecastException("Error al registrar la medición de carbohidratos", e);
        }
    }

    /**
     * Registra una {@link Medicion} de insulina relacionada a un {@link Paciente} en la base de datos
     *
     * @param paciente           el {@link Paciente}
     * @param insulinaComida     la dosis de insulina de comida
     * @param insulinaCorreccion la dosis de insulina de corrección
     * @param insulinaLenta      la dosis de insulina lenta
     * @throws GlucoForecastException si el valor de insulina es incorrecto o si hay un error de base de datos
     */
    public void registrarInsulina(Paciente paciente, double insulinaComida, double insulinaCorreccion, double insulinaLenta) throws GlucoForecastException {
        if (insulinaComida < 0 || insulinaCorreccion < 0 || insulinaLenta < 0) {
            throw new GlucoForecastException("Dosis de insulina no puede ser negativa.");
        }
        try {
            Medicion medicion = new Medicion(null, paciente.getId(), LocalDateTime.now(), 0, 0, insulinaComida, insulinaCorreccion, "", insulinaLenta, "", "");
            // Guarda la medición en la base de datos
            medicion.guardar();
        } catch (SQLException e) {
            throw new GlucoForecastException("Error al registrar la medición de insulina", e);
        }
    }
}
