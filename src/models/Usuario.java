package models;

import jdbc.Persistencia;

import java.sql.*;

public abstract class Usuario<V> implements Persistencia<V> {

    public Usuario(Long id, String nombre, String apellido, String email, String telefono) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.telefono = telefono;
    }

    public abstract boolean autenticar(String usuario, String clave);


    public static final String DEMO_USER_PACIENTE = "paciente";
    public static final String DEMO_PASS_PACIENTE = "paciente";

    public static final String DEMO_USER_CUIDADOR = "cuidador";
    public static final String DEMO_PASS_CUIDADOR = "cuidador";

    public static final String DEMO_USER_MEDICO = "medico";
    public static final String DEMO_PASS_MEDICO = "medico";

    protected Long id;
    protected String nombre;
    protected String apellido;
    protected String email;
    protected String telefono;

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    @Override
    public void guardar() throws SQLException {
        String sql = "INSERT INTO usuarios (nombre, apellido, email, telefono) VALUES (?, ?, ?, ?)";

        try (Connection conn = getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            // Establecer los parámetros del PreparedStatement
            stmt.setString(1, getNombre());
            stmt.setString(2, getApellido());
            stmt.setString(3, getEmail());
            stmt.setString(4, getTelefono());

            stmt.executeUpdate();

            // Obtener el ID generado automáticamente y asignarlo al usuario
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    this.setId(generatedKeys.getLong(1));
                } else {
                    throw new SQLException("No se pudo obtener el ID generado para el usuario.");
                }
            }
        }
    }


    @Override
    public void actualizar() throws SQLException {
        String sql = "UPDATE usuarios SET nombre = ?, apellido = ?, email = ?, telefono = ? WHERE id = ?";
        try (Connection conn = getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nombre);
            stmt.setString(2, apellido);
            stmt.setString(3, email);
            stmt.setString(4, telefono);
            stmt.setLong(5, id);
            stmt.executeUpdate();
        }
    }

    @Override
    public void eliminar() throws SQLException {
        String sql = "DELETE FROM usuarios WHERE id = ?";
        try (Connection conn = getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        }
    }
}
