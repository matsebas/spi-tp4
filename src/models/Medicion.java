package models;

import jdbc.ConexionBD;
import jdbc.Persistencia;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Medicion implements Persistencia<Medicion> {
    private Long id;
    private Long pacienteId;
    private LocalDateTime fechaHora;
    private double glucemia;
    private double carbohidratos;
    private double insulinaComida;
    private double insulinaCorreccion;
    private String descripcionComida;
    private double insulinaLenta;
    private String notas;
    private String tags;

    public Medicion(Long id, Long pacienteId, LocalDateTime fechaHora, double glucemia, double carbohidratos, double insulinaComida, double insulinaCorreccion, String descripcionComida, double insulinaLenta, String notas, String tags) {
        this.id = id;
        this.pacienteId = pacienteId;
        this.fechaHora = fechaHora;
        this.glucemia = glucemia;
        this.carbohidratos = carbohidratos;
        this.insulinaComida = insulinaComida;
        this.insulinaCorreccion = insulinaCorreccion;
        this.descripcionComida = descripcionComida;
        this.insulinaLenta = insulinaLenta;
        this.notas = notas;
        this.tags = tags;
    }

    // Getters y setters
    public Long getId() {
        return id;
    }

    public Long getPacienteId() {
        return pacienteId;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public double getGlucemia() {
        return glucemia;
    }

    public double getCarbohidratos() {
        return carbohidratos;
    }

    public double getInsulinaComida() {
        return insulinaComida;
    }

    public double getInsulinaCorreccion() {
        return insulinaCorreccion;
    }

    public String getDescripcionComida() {
        return descripcionComida;
    }

    public double getInsulinaLenta() {
        return insulinaLenta;
    }

    public String getNotas() {
        return notas;
    }

    public String getTags() {
        return tags;
    }

    /**
     * Guarda una {@link Medicion} relacionada a un {@link Paciente} en la base de datos
     *
     * @throws SQLException si hay un error de base de datos
     */
    @Override
    public void guardar() throws SQLException {
        String sql = "INSERT INTO mediciones (paciente_id, fecha_hora, glucemia, carbohidratos, insulina_comida, insulina_correccion, descripcion_comida, insulina_lenta, notas, tags) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setLong(1, pacienteId);
            stmt.setTimestamp(2, Timestamp.valueOf(fechaHora));
            stmt.setDouble(3, glucemia);
            stmt.setDouble(4, carbohidratos);
            stmt.setDouble(5, insulinaComida);
            stmt.setDouble(6, insulinaCorreccion);
            stmt.setString(7, descripcionComida);
            stmt.setDouble(8, insulinaLenta);
            stmt.setString(9, notas);
            stmt.setString(10, tags);

            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    this.id = generatedKeys.getLong(1);
                } else {
                    throw new SQLException("No se pudo obtener el ID generado para la medición.");
                }
            }
        }
    }

    @Override
    public void actualizar() throws SQLException {
        String sql = "UPDATE mediciones SET paciente_id = ?, fecha_hora = ?, glucemia = ?, carbohidratos = ?, " +
                "insulina_comida = ?, insulina_correccion = ?, descripcion_comida = ?, insulina_lenta = ?, notas = ?, tags = ? " +
                "WHERE id = ?";

        try (Connection conn = getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, pacienteId);
            stmt.setTimestamp(2, Timestamp.valueOf(fechaHora));
            stmt.setDouble(3, glucemia);
            stmt.setDouble(4, carbohidratos);
            stmt.setDouble(5, insulinaComida);
            stmt.setDouble(6, insulinaCorreccion);
            stmt.setString(7, descripcionComida);
            stmt.setDouble(8, insulinaLenta);
            stmt.setString(9, notas);
            stmt.setString(10, tags);
            stmt.setLong(11, id);

            stmt.executeUpdate();
        }
    }

    @Override
    public void eliminar() throws SQLException {
        String sql = "DELETE FROM mediciones WHERE id = ?";
        try (Connection conn = getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        }
    }

    /**
     * Busca una {@link Medicion} por su ID
     *
     * @param id el ID de la {@link Medicion}
     * @return la {@link Medicion}
     * @throws SQLException si hay un error de base de datos
     */
    public static Medicion buscarPorId(Long id) throws SQLException {
        String sql = "SELECT * FROM mediciones WHERE id = ?";
        try (Connection conn = ConexionBD.getInstancia().getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // Mapear el resultado a un objeto Medicion
                    return new Medicion(
                            rs.getLong("id"),
                            rs.getLong("paciente_id"),
                            rs.getTimestamp("fecha_hora").toLocalDateTime(),
                            rs.getDouble("glucemia"),
                            rs.getDouble("carbohidratos"),
                            rs.getDouble("insulina_comida"),
                            rs.getDouble("insulina_correccion"),
                            rs.getString("descripcion_comida"),
                            rs.getDouble("insulina_lenta"),
                            rs.getString("notas"),
                            rs.getString("tags")
                    );
                } else {
                    return null; // Medicion no encontrada
                }
            }
        }
    }

    /**
     * Busca todas las {@link Medicion} de un {@link Paciente} por su ID
     *
     * @param pacienteId el ID del {@link Paciente}
     * @return la lista de {@link Medicion}
     * @throws SQLException si hay un error de base de datos
     */
    public static List<Medicion> buscarPorPacienteId(Long pacienteId) throws SQLException {
        String sql = "SELECT * FROM mediciones WHERE paciente_id = ?";
        List<Medicion> mediciones = new ArrayList<>();
        try (Connection conn = ConexionBD.getInstancia().getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, pacienteId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    // Mapear cada resultado a un objeto Medicion y agregarlo a la lista
                    mediciones.add(new Medicion(
                            rs.getLong("id"),
                            rs.getLong("paciente_id"),
                            rs.getTimestamp("fecha_hora").toLocalDateTime(),
                            rs.getDouble("glucemia"),
                            rs.getDouble("carbohidratos"),
                            rs.getDouble("insulina_comida"),
                            rs.getDouble("insulina_correccion"),
                            rs.getString("descripcion_comida"),
                            rs.getDouble("insulina_lenta"),
                            rs.getString("notas"),
                            rs.getString("tags")
                    ));
                }
            }
        }
        return mediciones;
    }
}
