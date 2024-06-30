package models;

import jdbc.ConexionBD;
import jdbc.Persistencia;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa una estimación de HbA1c
 */
public class EstimacionGlicosilada implements Persistencia<EstimacionGlicosilada> {
    private final Long id;
    private final Long pacienteId;
    private final double valorCalculado;
    private final LocalDate fechaCalculo;

    public EstimacionGlicosilada(Long id, Long pacienteId, double valorCalculado, LocalDate fechaCalculo) {
        this.id = id;
        this.pacienteId = pacienteId;
        this.valorCalculado = valorCalculado;
        this.fechaCalculo = fechaCalculo;
    }

    // Getters y setters
    public Long getId() {
        return id;
    }

    public Long getPacienteId() {
        return pacienteId;
    }

    public double getValorCalculado() {
        return valorCalculado;
    }

    public LocalDate getFechaCalculo() {
        return fechaCalculo;
    }

    /**
     * Calcula la estimación de HbA1c a partir de una lista de  {@link Medicion}
     *
     * @param mediciones la lista de {@link Medicion}
     * @return el valor de HbA1c calculado
     */
    public static double calcularEstimacion(List<Medicion> mediciones) {
        double promedioGlucemia = mediciones.stream().mapToDouble(Medicion::getGlucemia).average().orElse(0.0);
        return (promedioGlucemia + 46.7) / 28.7;
    }


    /**
     * Busca todas las estimaciones de HbA1c de un {@link Paciente} por su ID
     *
     * @param id el ID del {@link Paciente}
     * @return la lista de {@link EstimacionGlicosilada}
     */
    public static List<EstimacionGlicosilada> buscarPorPacienteId(Long id) throws SQLException {
        String sql = "SELECT * FROM estimaciones_glicosiladas WHERE paciente_id = ?";
        List<EstimacionGlicosilada> estimaciones = new ArrayList<>();
        try (Connection conn = ConexionBD.getInstancia().getConexion(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    estimaciones.add(new EstimacionGlicosilada(
                            rs.getLong("id"),
                            rs.getLong("paciente_id"),
                            rs.getDouble("valor_calculado"),
                            rs.getDate("fecha_calculo").toLocalDate()
                    ));
                }
            }
        }
        return estimaciones;
    }

    /**
     * Guarda una estimación de HbA1c
     *
     * @throws SQLException si hay un error de base de datos
     */
    @Override
    public void guardar() throws SQLException {

        String sql = "INSERT INTO estimaciones_glicosiladas (paciente_id, valor_calculado, fecha_calculo) " +
                "VALUES (?, ?, ?)";
        try (Connection conn = getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setLong(1, pacienteId);
            stmt.setDouble(2, valorCalculado);
            stmt.setDate(3, java.sql.Date.valueOf(fechaCalculo));

            stmt.executeUpdate();
        }
    }

    /**
     * Actualiza una estimación de HbA1c
     *
     * @throws SQLException si hay un error de base de datos
     */
    @Override
    public void actualizar() throws SQLException {
        String sql = "UPDATE estimaciones_glicosiladas SET paciente_id = ?, valor_calculado = ?, fecha_calculo = ? " +
                "WHERE id = ?";

        try (Connection conn = getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, pacienteId);
            stmt.setDouble(2, valorCalculado);
            stmt.setDate(3, java.sql.Date.valueOf(fechaCalculo));
            stmt.setLong(4, id);
            stmt.executeUpdate();
        }

    }

    /**
     * Elimina una estimación de HbA1c
     *
     * @throws SQLException si hay un error de base de datos
     */
    @Override
    public void eliminar() throws SQLException {
        String sql = "DELETE FROM estimaciones_glicosiladas WHERE id = ?";
        try (Connection conn = getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        }
    }
}
