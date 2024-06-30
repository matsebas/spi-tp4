package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase que gestiona la conexión a la base de datos utilizando el patrón Singleton.
 * Proporciona un único punto de acceso a la conexión y asegura que solo se cree una instancia de la conexión.
 *
 * @author <a href="https://github.com/matsebas">Matias Sebastiao</a>
 */
public class ConexionBD {
    /**
     * Instancia única de la clase {@link ConexionBD}.
     */
    private static ConexionBD instancia;
    /**
     * Conexión a la base de datos.
     */
    private Connection conexion;

    /**
     * Constructor privado para prevenir la creación de instancias desde fuera de la clase.
     * Establece la conexión a la base de datos al crear la instancia.
     *
     * @throws SQLException si ocurre un error al establecer la conexión.
     */
    private ConexionBD() throws SQLException {
        crearConexion();
    }
    /**
     * Crea una nueva conexión a la base de datos.
     *
     * @throws SQLException si ocurre un error al establecer la conexión.
     */
    private void crearConexion() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/glucoforecast";
        String usuario = "root";
        String contrasena = "P4ssw0rd!";
        conexion = DriverManager.getConnection(url, usuario, contrasena);
    }
    /**
     * Obtiene la instancia única de la clase {@link ConexionBD}.
     * Si la instancia no existe o la conexión está cerrada, se crea una nueva instancia.
     *
     * @return La instancia única de {@link ConexionBD}.
     * @throws SQLException Si ocurre un error al crear la conexión.
     */
    public static ConexionBD getInstancia() throws SQLException {
        if (instancia == null || instancia.conexion.isClosed()) {
            instancia = new ConexionBD();
        }
        return instancia;
    }
    /**
     * Obtiene la conexión a la base de datos.
     * Si la conexión no existe o está cerrada, se crea una nueva conexión.
     *
     * @return La conexión a la base de datos.
     * @throws SQLException Si ocurre un error al crear la conexión.
     */
    public Connection getConexion() throws SQLException {
        if (conexion == null || conexion.isClosed()) {
            crearConexion();
        }
        return conexion;
    }
}
