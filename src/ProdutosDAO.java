
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
    
    //Método para cadastrar produto
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
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar produto, tente novamente mais tarde", "Erro", JOptionPane.ERROR_MESSAGE);
            System.out.println(e.getMessage());
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
                conecta.connectDB();
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
    
    //Método para vender produto
    public void venderProduto(int produtoId) {
        String sql = "UPDATE produtos SET status = 'Vendido' WHERE id = ?";
        
        try {
            if (conn == null || conn.isClosed()) {
                conectaDAO conexaoDAO = new conectaDAO();
                conn = conexaoDAO.connectDB();
            }
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, produtoId);
            int linha = stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Venda realizada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        }catch (Exception e) {
            System.out.println("Erro ao vender o produto: " + e.getMessage());
        }
    }
    
    // Método para listar os produtos vendidos
    public ArrayList<ProdutosDTO> listarProdutosVendidos() {
        String sql = "SELECT * FROM produtos WHERE status LIKE 'Vendido'";
        ArrayList<ProdutosDTO> listaProdutos = new ArrayList<>();

        try {
            if (conn == null || conn.isClosed()) {
                conectaDAO conecta = new conectaDAO();
                conn = conecta.connectDB();
            }

            try (PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    ProdutosDTO produto = new ProdutosDTO();
                    produto.setId(rs.getInt("id"));
                    produto.setNome(rs.getString("nome"));
                    produto.setValor(rs.getInt("valor"));
                    produto.setStatus(rs.getString("status"));

                    listaProdutos.add(produto);
                }
            }
        } catch (Exception e) {
            System.err.println("Erro ao listar produtos vendidos: " + e.getMessage());
            e.printStackTrace();
        }
        return listaProdutos;
    }

    
}
