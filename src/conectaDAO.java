
import com.mysql.jdbc.PreparedStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class conectaDAO {
    Connection conn;
    PreparedStatement st;
    
    //Método para establecer a conexão com o banco de dados
    public Connection connectDB() {

        try {   
            conn = DriverManager.getConnection("jdbc:mysql://localhost/uc11?useSSL=false","root","");
            System.out.println("Conexão estabelecida com sucesso!");
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro ao conectar" + erro.getMessage());
            return null;
        }
        return conn;
    }
    
    //Método para fechar a conexão com o banco de dados
    public void desconectar() {
        if (conn != null) {
           try {
               conn.close();
               System.out.println("Desconectado!");
           } catch(SQLException ex) {
           }
        }
    }
}
