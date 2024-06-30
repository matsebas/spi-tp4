package models;

import jdbc.Persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Medico extends Usuario<Medico> implements Persistencia<Medico> {

    private String institucion;
    private List<Paciente> pacientes;

    public Medico(Long id, String nombre, String apellido, String email, String telefono, String institucion) {
        super(id, nombre, apellido, email, telefono);
        this.institucion = institucion;
        this.pacientes = new ArrayList<>();
    }

    // Getters y setters
    public String getInstitucion() {
        return institucion;
    }

    public List<Paciente> getPacientes() {
        return pacientes;
    }

    public void agregarPaciente(Paciente paciente) {
        pacientes.add(paciente);
    }

    /**
     * Sobrescribe el método autenticar para verificar si el nombre de usuario y la contraseña proporcionados
     * coinciden con el usuario y la contraseña de un {@link Medico}
     *
     * @param usuario el nombre de usuario a autenticar
     * @param clave   la contraseña a autenticar
     * @return true si el nombre de usuario y la contraseña coinciden con el usuario y la contraseña de demostración,
     * false en caso contrario
     */
    @Override
    public boolean autenticar(String usuario, String clave) {
        return DEMO_USER_MEDICO.equalsIgnoreCase(usuario) && DEMO_PASS_MEDICO.equalsIgnoreCase(clave);
    }

    /**
     * Guarda un nuevo {@link Medico} en la base de datos. Primero guarda la información del {@link Usuario} base y luego la información
     * específica del médico, incluyendo la relación con cada {@link Paciente} en la tabla `paciente_medico`.
     *
     * @throws SQLException si ocurre un error durante la operación de guardado en la base de datos.
     */
    @Override
    public void guardar() throws SQLException {
        Connection conn = null;
        try {
            conn = getConexion();
            conn.setAutoCommit(false); // Iniciar transacción

            // 1. Guardar la información del usuario base
            super.guardar();

            // 2. Guardar la información específica del médico
            String sqlMedico = "INSERT INTO medicos (id, institucion) VALUES (?, ?)";
            try (PreparedStatement stmtMedico = conn.prepareStatement(sqlMedico)) {
                stmtMedico.setLong(1, getId());
                stmtMedico.setString(2, institucion);
                stmtMedico.executeUpdate();
            }

            // 3. Guardar las relaciones paciente-médico
            String sqlRelacion = "INSERT INTO paciente_medico (paciente_id, medico_id) VALUES (?, ?)";
            try (PreparedStatement stmtRelacion = conn.prepareStatement(sqlRelacion)) {
                for (Paciente paciente : pacientes) {
                    stmtRelacion.setLong(1, paciente.getId());
                    stmtRelacion.setLong(2, this.getId());
                    stmtRelacion.executeUpdate();
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

    /**
     * Actualiza la información de un médico existente en la base de datos.
     * Primero actualiza la información del {@link Usuario} base y luego la información específica del {@link Medico},
     * incluyendo la relación con cada {@link Paciente} en la tabla `paciente_medico`.
     *
     * @throws SQLException si ocurre un error durante la operación de actualización en la base de datos.
     */
    @Override
    public void actualizar() throws SQLException {
        Connection conn = null;
        try {
            conn = getConexion();
            conn.setAutoCommit(false); // Iniciar transacción

            // 1. Actualizar la información del usuario base
            super.actualizar();

            // 2. Actualizar la información específica del médico
            String sqlMedico = "UPDATE medicos SET institucion = ? WHERE id = ?";
            try (PreparedStatement stmtMedico = conn.prepareStatement(sqlMedico)) {
                stmtMedico.setString(1, institucion);
                stmtMedico.setLong(2, getId());
                stmtMedico.executeUpdate();
            }

            // 3. Eliminar las relaciones existentes con pacientes
            String sqlEliminarRelacion = "DELETE FROM paciente_medico WHERE medico_id = ?";
            try (PreparedStatement stmtEliminarRelacion = conn.prepareStatement(sqlEliminarRelacion)) {
                stmtEliminarRelacion.setLong(1, this.getId());
                stmtEliminarRelacion.executeUpdate();
            }

            // 4. Insertar las nuevas relaciones paciente-médico
            String sqlRelacion = "INSERT INTO paciente_medico (paciente_id, medico_id) VALUES (?, ?)";
            try (PreparedStatement stmtRelacion = conn.prepareStatement(sqlRelacion)) {
                for (Paciente paciente : pacientes) {
                    stmtRelacion.setLong(1, paciente.getId());
                    stmtRelacion.setLong(2, this.getId());
                    stmtRelacion.executeUpdate();
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

    /**
     * Elimina un {@link Medico} de la base de datos. Primero elimina las relaciones de la tabla `paciente_medico`,
     * luego la información específica del médico de la tabla `medicos` y finalmente la información del {@link Usuario}
     * base de la tabla `usuarios`.
     *
     * @throws SQLException si ocurre un error durante la operación de eliminación en la base de datos.
     */
    @Override
    public void eliminar() throws SQLException {
        Connection conn = null;
        try {
            conn = getConexion();
            conn.setAutoCommit(false); // Iniciar transacción

            // 1. Eliminar las relaciones médico-paciente
            String sqlEliminarRelacion = "DELETE FROM paciente_medico WHERE medico_id = ?";
            try (PreparedStatement stmtEliminarRelacion = conn.prepareStatement(sqlEliminarRelacion)) {
                stmtEliminarRelacion.setLong(1, this.getId());
                stmtEliminarRelacion.executeUpdate();
            }

            // 2. Eliminar la información específica del médico
            String sqlMedico = "DELETE FROM medicos WHERE id = ?";
            try (PreparedStatement stmtMedico = conn.prepareStatement(sqlMedico)) {
                stmtMedico.setLong(1, this.getId());
                stmtMedico.executeUpdate();
            }

            // 3. Eliminar la información del usuario base
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
