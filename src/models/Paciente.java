package models;

import jdbc.ConexionBD;
import jdbc.Persistencia;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Paciente extends Usuario<Paciente> implements Persistencia<Paciente> {

    private LocalDate fechaNacimiento;
    private double pesoCorporal;
    private LocalDate fechaDiagnostico;

    public Paciente(Long id, String nombre, String apellido, String email, String telefono, LocalDate fechaNacimiento, double pesoCorporal, LocalDate fechaDiagnostico) {
        super(id, nombre, apellido, email, telefono);
        this.fechaNacimiento = fechaNacimiento;
        this.pesoCorporal = pesoCorporal;
        this.fechaDiagnostico = fechaDiagnostico;
    }

    // Getters y setters

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public double getPesoCorporal() {
        return pesoCorporal;
    }

    public void setPesoCorporal(double pesoCorporal) {
        this.pesoCorporal = pesoCorporal;
    }

    public LocalDate getFechaDiagnostico() {
        return fechaDiagnostico;
    }

    public void setFechaDiagnostico(LocalDate fechaDiagnostico) {
        this.fechaDiagnostico = fechaDiagnostico;
    }

    /**
     * Busca todas las {@link Medicion} de un {@link Paciente} por su ID
     *
     * @return la lista de {@link Medicion}
     * @throws SQLException si hay un error de base de datos
     */
    public List<Medicion> getMediciones() throws SQLException {
        return Medicion.buscarPorPacienteId(this.getId());
    }

    /**
     * Busca todas las estimaciones de HbA1c de un {@link Paciente} por su ID
     *
     * @return la lista de {@link EstimacionGlicosilada}
     * @throws SQLException si hay un error de base de datos
     */
    public List<EstimacionGlicosilada> getEstimacionesGlicosilada() throws SQLException {
        return EstimacionGlicosilada.buscarPorPacienteId(this.getId());
    }

    /**
     * Sobrescribe el método autenticar para verificar si el nombre de usuario y la contraseña proporcionados
     * coinciden con el usuario y la contraseña de un {@link Paciente}
     *
     * @param usuario el nombre de usuario a autenticar
     * @param clave   la contraseña a autenticar
     * @return true si el nombre de usuario y la contraseña coinciden con el usuario y la contraseña de demostración,
     * false en caso contrario
     */
    @Override
    public boolean autenticar(String usuario, String clave) {
        return DEMO_USER_PACIENTE.equalsIgnoreCase(usuario) && DEMO_PASS_PACIENTE.equalsIgnoreCase(clave);
    }

    @Override
    public void guardar() throws SQLException {
        // Primero, guardar la información del usuario base
        super.guardar();

        // Luego, guardar la información específica del paciente
        String sql = "INSERT INTO pacientes (id, fecha_nacimiento, peso_corporal, fecha_diagnostico) " +
                "VALUES (?, ?, ?, ?)";

        try (Connection conn = getConexion(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Establecer los parámetros del PreparedStatement
            stmt.setLong(1, getId()); // Usar el ID generado al guardar el usuario base
            stmt.setDate(2, Date.valueOf(getFechaNacimiento()));
            stmt.setDouble(3, getPesoCorporal());
            stmt.setDate(4, Date.valueOf(getFechaDiagnostico()));

            stmt.executeUpdate();
        }
    }

    @Override
    public void actualizar() throws SQLException {
        // Primero, actualizar la información del usuario base
        super.actualizar();

        // Luego, actualizar la información específica del paciente
        String sql = "UPDATE pacientes SET fecha_nacimiento = ?, peso_corporal = ?, fecha_diagnostico = ? WHERE id = ?";
        try (Connection conn = getConexion(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Establecer los parámetros del PreparedStatement
            stmt.setDate(1, Date.valueOf(getFechaNacimiento()));
            stmt.setDouble(2, getPesoCorporal());
            stmt.setDate(3, Date.valueOf(getFechaDiagnostico()));
            stmt.setLong(4, getId());

            stmt.executeUpdate();
        }
    }

    @Override
    public void eliminar() throws SQLException {
        // Primero, eliminar la información específica del paciente
        String sql = "DELETE FROM pacientes WHERE id = ?";
        try (Connection conn = getConexion(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        }

        // Luego, eliminar la información del usuario base
        super.eliminar();
    }

    /**
     * Busca todos los {@link Paciente}
     *
     * @return la lista de {@link Paciente}
     * @throws SQLException si hay un error de base de datos
     */
    public static List<Paciente> listarTodos() throws SQLException {
        String sql = "SELECT * FROM pacientes p JOIN usuarios u ON p.id = u.id ORDER BY p.id";
        List<Paciente> pacientes = new ArrayList<>();
        try (Connection conn = ConexionBD.getInstancia().getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                pacientes.add(new Paciente(
                        rs.getLong("id"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("email"),
                        rs.getString("telefono"),
                        rs.getDate("fecha_nacimiento").toLocalDate(),
                        rs.getDouble("peso_corporal"),
                        rs.getDate("fecha_diagnostico").toLocalDate()));
            }
        }
        return pacientes;
    }


    /**
     * Busca un {@link Paciente} por su ID
     *
     * @param id el ID del {@link Paciente}
     * @return {@link Paciente}
     * @throws SQLException si hay un error de base de datos
     */
    public static Paciente buscarPorId(Long id) throws SQLException {
        String sql = "SELECT * FROM pacientes p JOIN usuarios u ON p.id = u.id WHERE p.id = ?";
        try (Connection conn = ConexionBD.getInstancia().getConexion(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Paciente(rs.getLong("id"),
                            rs.getString("nombre"),
                            rs.getString("apellido"),
                            rs.getString("email"),
                            rs.getString("telefono"),
                            rs.getDate("fecha_nacimiento").toLocalDate(),
                            rs.getDouble("peso_corporal"),
                            rs.getDate("fecha_diagnostico").toLocalDate());
                } else {
                    return null; // Paciente no encontrado
                }
            }
        }
    }

}
