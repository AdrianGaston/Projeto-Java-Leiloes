
import java.sql.PreparedStatement;
import java.sql.Connection;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProdutosDAO {

    Connection conn;
    PreparedStatement prep;
    ResultSet resultset;

    public void cadastrarProduto(ProdutosDTO produto) {
        conectaDAO conexaoDAO = new conectaDAO();
        conn = conexaoDAO.connectDB();
        
        String sql = "INSERT INTO produtos (nome, valor, status) VALUES (?, ?, ?)";
        
        try {
            prep = conn.prepareStatement(sql);
            prep.setString(1, produto.getNome());
            prep.setInt(2, produto.getValor());
            prep.setString(3, produto.getStatus());
            
            prep.executeUpdate();
            JOptionPane.showMessageDialog(null, "Produto cadastrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar produto: " + e.getMessage());
        } finally {
            conexaoDAO.desconectar();
        }
    }
    
    //Método para listar os produtos cadastrados
    public ArrayList<ProdutosDTO> listarProdutos() {
        String sql = "SELECT * FROM produtos";
        
        List<ProdutosDTO> listaProdutos = new ArrayList<>();
        
        conectaDAO conecta = new conectaDAO();
        conn = conecta.connectDB();
        try {
            
            if (conn == null || conn.isClosed()) {
                conecta.connectDB(); //ARRUMAR
            }
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            
            //Estrutura de repetição para pegar os produtos ja cadastrados do banco
            while (rs.next()) {
                ProdutosDTO produto = new ProdutosDTO();
                produto.setId(rs.getInt("id"));
                produto.setNome(rs.getString("nome"));
                produto.setValor(rs.getInt("valor"));
                produto.setStatus(rs.getString("status"));
                
                listaProdutos.add(produto);
            }
            rs.close();
            stmt.close();
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
        return (ArrayList<ProdutosDTO>) listaProdutos;
    }
}
