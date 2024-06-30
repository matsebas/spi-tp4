package jdbc;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Interfaz que define los métodos de persistencia de los modelos.
 *
 * @author <a href="https://github.com/matsebas">Matias Sebastiao</a>
 */
public interface Persistencia<V> {

    default Connection getConexion() throws SQLException {
        return ConexionBD.getInstancia().getConexion();
    }

    /**
     * Este método se utiliza para guardar un modelo nuevo en la base de datos
     *
     * @throws SQLException si ocurre algún error al guardar los datos en la base de datos
     */
    void guardar() throws SQLException;

    /**
     * Este método se utiliza para actualizar los datos de un modelo existente en la base de datos.
     *
     * @throws SQLException si ocurre ningún error al actualizar los datos de la base de datos
     */
    void actualizar() throws SQLException;

    /**
     * Este método se utiliza para eliminar los datos de la base de datos.
     *
     * @throws SQLException si ocurre ningún error al eliminar los datos de la base de datos
     */
    void eliminar() throws SQLException;
}