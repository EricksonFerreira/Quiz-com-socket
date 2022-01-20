package banco;

public class Pergunta {
    private int id;
    private String titulo;   
    private String resposta;   
    private boolean jaRespondida;   
    private boolean ativa;   
    private String respondeu; 

    public Pergunta() {
        this.jaRespondida = false;
        this.ativa = false;
        this.respondeu = null;
    }
    public Pergunta(int id,String titulo, String resposta) {
        this.id = id;
        this.titulo = titulo;
        this.resposta = resposta;
        this.jaRespondida = false;
        this.ativa = false;
        this.respondeu = null;
    }
    public Pergunta(String titulo, String resposta) {
        this.titulo = titulo;
        this.resposta = resposta;
        this.jaRespondida = false;
        this.ativa = false;
        this.respondeu = null;
    }
    public Pergunta(int id,String titulo, String resposta,boolean jaRespondida, boolean ativa, String respondeu) {
        this.id = id;
        this.titulo = titulo;
        this.resposta = resposta;
        this.jaRespondida = jaRespondida;
        this.ativa = ativa;
        this.respondeu = respondeu;
    }

    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    public String getResposta() {
        return resposta;
    }
    public void setResposta(String resposta) {
        this.resposta = resposta;
    }
    public boolean isJaRespondida() {
        return jaRespondida;
    }
    public void setJaRespondida(boolean jaRespondida) {
        this.jaRespondida = jaRespondida;
    }
    public boolean isAtiva() {
        return ativa;
    }
    public void setAtiva(boolean ativa) {
        this.ativa = ativa;
    }
    public String getRespondeu() {
        return respondeu;
    }
    public void setRespondeu(String respondeu) {
        this.respondeu = respondeu;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
