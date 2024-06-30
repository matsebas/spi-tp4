package models;

import jdbc.Persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Cuidador extends Usuario<Cuidador> implements Persistencia<Cuidador> {

    private List<Paciente> pacientesACargo;

    public Cuidador(Long id, String nombre, String apellido, String email, String telefono) {
        super(id, nombre, apellido, email, telefono);
        this.pacientesACargo = new ArrayList<>();
    }

    // Getters y setters

    public List<Paciente> getPacientesACargo() throws SQLException {
        return this.pacientesACargo;
    }

    public void agregarPacienteACargo(Paciente paciente) {
        this.pacientesACargo.add(paciente);
    }

    public Paciente accederDatosPaciente(Long pacienteId) {
        return pacientesACargo.stream().filter(p -> p.getId().equals(pacienteId)).findFirst().orElse(null);
    }

    /**
     * Sobrescribe el método autenticar para verificar si el nombre de usuario y la contraseña proporcionados
     * coinciden con el usuario y la contraseña de un {@link Cuidador}
     *
     * @param usuario el nombre de usuario a autenticar
     * @param clave   la contraseña a autenticar
     * @return true si el nombre de usuario y la contraseña coinciden con el usuario y la contraseña de demostración,
     * false en caso contrario
     */
    @Override
    public boolean autenticar(String usuario, String clave) {
        return DEMO_USER_CUIDADOR.equalsIgnoreCase(usuario) && DEMO_PASS_CUIDADOR.equalsIgnoreCase(clave);
    }

    @Override
    public void guardar() throws SQLException {
        // Primero, guardar la información del usuario base
        super.guardar();

        // Luego, guardar la relación cuidador-paciente (si es necesario)
        for (Paciente paciente : pacientesACargo) {
            String sql = "INSERT INTO paciente_cuidador (paciente_id, cuidador_id) VALUES (?, ?)";
            try (Connection conn = getConexion();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setLong(1, paciente.getId());
                stmt.setLong(2, this.getId());
                stmt.executeUpdate();
            }
        }
    }

    @Override
    public void actualizar() throws SQLException {
        Connection conn = null;
        try {
            conn = getConexion();
            conn.setAutoCommit(false); // Iniciar transacción

            // 1. Actualizar la información del usuario base
            super.actualizar();

            // 2. Eliminar las relaciones existentes con pacientes
            String sql1 = "DELETE FROM paciente_cuidador WHERE cuidador_id = ?";
            try (PreparedStatement stmt1 = conn.prepareStatement(sql1)) {
                stmt1.setLong(1, this.getId());
                stmt1.executeUpdate();
            }

            // 3. Insertar las nuevas relaciones con pacientes
            String sql2 = "INSERT INTO paciente_cuidador (paciente_id, cuidador_id) VALUES (?, ?)";
            try (PreparedStatement stmt2 = conn.prepareStatement(sql2)) {
                for (Paciente paciente : pacientesACargo) {
                    stmt2.setLong(1, paciente.getId());
                    stmt2.setLong(2, this.getId());
                    stmt2.executeUpdate();
                }
            }

            conn.commit(); // Confirmar transacción si no falló nada
        } catch (SQLException e) {
            if (conn != null) {
                conn.rollback(); // Revertir transacción en caso de error
            }
            throw e; // Relanzar la excepción para que sea manejada en otro lugar
        } finally {
            if (conn != null) {
                conn.setAutoCommit(true); // Restaurar el modo de autocommit
                conn.close(); // Cerrar la conexión
            }
        }
    }

    @Override
    public void eliminar() throws SQLException {
        Connection conn = null;
        try {
            conn = getConexion();
            conn.setAutoCommit(false); // Iniciar transacción

            // 1. Eliminar la relación cuidador-paciente
            String sql1 = "DELETE FROM paciente_cuidador WHERE cuidador_id = ?";
            try (PreparedStatement stmt1 = conn.prepareStatement(sql1)) {
                stmt1.setLong(1, this.getId());
                stmt1.executeUpdate();
            }

            // 2. Eliminar el cuidador
            String sql2 = "DELETE FROM cuidadores WHERE id = ?";
            try (PreparedStatement stmt2 = conn.prepareStatement(sql2)) {
                stmt2.setLong(1, this.getId());
                stmt2.executeUpdate();
            }

            // 3. Eliminar el usuario base
            super.eliminar();

            conn.commit(); // Confirmar transacción si no falló nada
        } catch (SQLException e) {
            if (conn != null) {
                conn.rollback(); // Revertir transacción en caso de error
            }
            throw e; // Relanzar la excepción para que sea manejada en otro lugar
        } finally {
            if (conn != null) {
                conn.setAutoCommit(true); // Restaurar el modo de autocommit
                conn.close(); // Cerrar la conexión
            }
        }
    }
}
