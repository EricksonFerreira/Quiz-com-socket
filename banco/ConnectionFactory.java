package banco;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;


public class ConnectionFactory {
    private static Connection connection;
    private static final String DRIVER = "com.mysql.jdbc.Driver";
    private static final String SERVIDOR = "127.0.0.1";
    private static final String PORTA = "3306";
    private static final String BANCO = "socket";
    private static final String URL = "jdbc:mysql://" + SERVIDOR + ":" + PORTA + "/" + BANCO;
    private static final String USUARIO = "socket";
    private static final String SENHA = "socket";

    public ConnectionFactory() {
    }

    public Connection getConnection() {
        if (connection == null) {
            try {
                Class.forName(DRIVER);
                connection = DriverManager.getConnection(URL, USUARIO, SENHA);
                System.out.println("funcionou");
            } catch (ClassNotFoundException e) {
                JOptionPane.showMessageDialog(null, "Erro ao carregar o driver de conexão\n" + e);
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Não foi possível estabalecer conexão com o banco de dados\n" + e);
            }
        }
        JOptionPane.showMessageDialog(null, "Conexão realizada com sucesso!");
        return connection;
    }
}
