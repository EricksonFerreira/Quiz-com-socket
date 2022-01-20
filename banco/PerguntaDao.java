package banco;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PerguntaDao {
    private Connection connection;

    public PerguntaDao() {
        this.connection = new ConnectionFactory().getConnection();
    }

    public void adiciona(Pergunta perg) {
        String sql = "insert into " + "perguntas(titulo,resposta)" + "values(?,?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, perg.getTitulo());
            stmt.setString(2, perg.getResposta());
            stmt.execute();
//            stmt.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public Integer getquantPergSemResposta(){
          try {
            PreparedStatement stmt = this.connection.prepareStatement("select count(*) AS quantidade from perguntas where jaRespondida=0");
            ResultSet rs = stmt.executeQuery();

            Integer valor = rs.getInt("quantidade");

            rs.close();
            stmt.close();

            return valor;
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
    public Pergunta getPergunta(Integer id) {
        try {
            PreparedStatement stmt = this.connection.prepareStatement("select id,titulo,resposta from perguntas where id=?");
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();


                Pergunta pergunta = new Pergunta();
            if (rs.next()) {
                pergunta.setId(rs.getInt("id"));
                pergunta.setTitulo(rs.getString("titulo"));
                pergunta.setResposta(rs.getString("resposta"));
            }
            rs.close();
            stmt.close();

            return pergunta;
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
    public List<Pergunta> getLista() {
        try {
            List<Pergunta> perguntas = new ArrayList<Pergunta>();
            PreparedStatement stmt = this.connection.prepareStatement("select * from perguntas");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Pergunta pergunta = new Pergunta(
                                                rs.getInt("id"),
                                                rs.getString("titulo"),
                                                rs.getString("resposta")
                );
                perguntas.add(pergunta);
            }
            rs.close();
            stmt.close();
            return perguntas;
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }public List<Pergunta> getTodasPerguntas() {
        try {
            List<Pergunta> perguntas = new ArrayList<Pergunta>();
            PreparedStatement stmt = this.connection.prepareStatement("select * from perguntas");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Pergunta pergunta = new Pergunta(
                                                rs.getInt("id"),
                                                rs.getString("titulo"),
                                                rs.getString("resposta"),
                                                rs.getBoolean("jaRespondida"),
                                                rs.getBoolean("ativa"),
                                                rs.getString("respondeu")
                );
                perguntas.add(pergunta);
            }
            rs.close();
            stmt.close();
            return perguntas;
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
    public void perguntaRespondida(int idPergunta, String nomeCliente){
       String sql = "UPDATE perguntas SET jaRespondida = true, ativa = false, respondeu = ? WHERE id = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, nomeCliente);
            stmt.setInt(2, idPergunta);
            stmt.execute();
//            stmt.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public List<Integer> getAllIdDisponivel(){
         try {
            List<Integer> perguntas = new ArrayList<Integer>();
            PreparedStatement stmt = this.connection.prepareStatement("select id from perguntas where jaRespondida = 0");
            ResultSet rs = stmt.executeQuery();

                List<Integer> lista = new ArrayList<Integer>();
            while (rs.next()) {
                lista.add(rs.getInt("id"));
            }
            rs.close();
            stmt.close();
            return lista;

        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
    public Integer indicePerguntaAtiva(){
         try {
//            Integer perguntas = new ArrayList<Integer>();
            PreparedStatement stmt = this.connection.prepareStatement("select id from perguntas where ativa = 1 limit 1 ");
            ResultSet rs = stmt.executeQuery();

            Integer valor = null;
            if (rs.next()) {
               valor = rs.getInt("id");
            }
            rs.close();
            stmt.close();
            return valor;

        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    public void ativaPergunta(Integer idPergunta){
         String sql = "UPDATE perguntas SET ativa = 1 WHERE id = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);

            stmt.setInt(1, idPergunta);
            stmt.execute();
//            stmt.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
