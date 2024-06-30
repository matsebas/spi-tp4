package models;

import exceptions.GlucoForecastException;
import jdbc.ConexionBD;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;

public abstract class Reporte {

    private Long id;
    private final Paciente paciente;
    private final ReporteTipo tipoReporte;
    private String datos;
    private LocalDate fechaGeneracion;

    public Reporte(Paciente paciente, ReporteTipo tipoReporte) {
        this.paciente = paciente;
        this.tipoReporte = tipoReporte;
    }

    public Long getId() {
        return id;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public ReporteTipo getTipoReporte() {
        return tipoReporte;
    }

    public String getDatos() {
        return this.datos;
    }

    public LocalDate getFechaGeneracion() {
        return fechaGeneracion;
    }

    /**
     * Genera los datos del reporte y lo almacena en la base de datos.
     *
     * @throws GlucoForecastException si hay un error
     */
    public void procesar() throws GlucoForecastException {
        this.datos = this.generarDatos();
        this.fechaGeneracion = LocalDate.now();

        String sql = "INSERT INTO reportes (paciente_id, tipo_reporte, fecha_generacion, datos) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConexionBD.getInstancia().getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setLong(1, paciente.getId());
            stmt.setString(2, tipoReporte.name());
            stmt.setDate(3, java.sql.Date.valueOf(this.fechaGeneracion));
            stmt.setString(4, this.datos);
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    this.id = generatedKeys.getLong(1);
                } else {
                    throw new SQLException("No se pudo obtener el ID generado para el reporte.");
                }
            }
        } catch (SQLException e) {
            throw new GlucoForecastException("Error al generar el reporte", e);
        }
    }

    /**
     * Método abstracto para generar los datos del reporte a partir de la información del @{@link Paciente}
     * Cada subclase debe implementar este método dependiendo del tipo de reporte que se requiera generar
     *
     * @return Reporte generado
     */
    protected abstract String generarDatos() throws GlucoForecastException;

    /**
     * Retorna una lista de los tipos de reportes habilitados.
     *
     * @return Lista de @{@link ReporteTipo} habilitados
     */
    public static List<ReporteTipo> reportesHabilitados() {
        return List.of(ReporteTipo.HB1AC_HISTORICO, ReporteTipo.PROMEDIOS_GLUCEMIA, ReporteTipo.RANGOS_GLUCEMIA);
    }
}
