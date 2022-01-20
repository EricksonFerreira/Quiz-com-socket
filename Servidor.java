import banco.Pergunta;
import banco.PerguntaDao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class Servidor implements Runnable{
	
	private static ArrayList<BufferedWriter> clientes;
	private static ServerSocket server;
	private String nome;
	private Socket con;
	private InputStream in;
	private InputStreamReader inr;
	private BufferedReader bfr;
	public List<Pergunta> perguntas = new ArrayList<Pergunta>();
    public Integer indiceAtivo;
	public int quantidadeHosts = 0;
	public PerguntaDao dao = new PerguntaDao();

	public Servidor(Socket con) {

//		this.perguntas.add(new Pergunta("Qual a capital de Pernambuco?","recife"));
//		this.perguntas.add(new Pergunta("Qual o nome do atual presidente do país?","bolsonaro"));
//		this.perguntas.add(new Pergunta("Em qual país é situado o Vaticano?","vaticano"));
//		this.perguntas.add(new Pergunta("Qual a nacionalidade de Neymar?","brasileiro"));
//
		this.con = con;
		
		try {
			in = con.getInputStream();
			inr = new InputStreamReader(in);
			bfr = new BufferedReader(inr);
		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public void run() {
		try {
			this.perguntas = dao.getLista();
//			Integer quantPergSemResposta = dao.getquantPergSemResposta();
//			if(perguntas.size() == 0 && quantPergSemResposta != 0){
			if(perguntas.size() == 0 ){
				dao.adiciona(new Pergunta("Qual a capital de Pernambuco?","recife"));
				dao.adiciona(new Pergunta("Quem é o presidente do Brasil?","bolsonaro"));
				dao.adiciona(new Pergunta("Quem nasce no Brasil é?","brasileiro"));
				dao.adiciona(new Pergunta("Onde nasceu Maradona?","argentina"));
				dao.adiciona(new Pergunta("Em que ano foi descoberto o Brasil?","1500"));
			};
			int a = 1;
			// maisUmHost();
			 String msg;
			 OutputStream ou = this.con.getOutputStream();
			 Writer ouw = new OutputStreamWriter(ou);
			 BufferedWriter bfw = new BufferedWriter(ouw);
			 clientes.add(bfw);
			 nome = msg = bfr.readLine();
			 pergunta(bfw, false);
				 System.out.println(msg);
			 while (!"Sair".equalsIgnoreCase(msg) && msg != null) {
			 	msg = bfr.readLine();
				this.indiceAtivo = this.dao.indicePerguntaAtiva();
			 	if(this.dao.getPergunta(this.indiceAtivo).getResposta().equals(msg)){
							acertou(bfw);
							sendToAll(bfw, msg, true);
					 	List<Integer> numeros = this.dao.getAllIdDisponivel();
						if(numeros.size() == 0) {
							resultado(bfw);
							resultadoSendoToAll(bfw);
						}

			 	}else{
			 		sendToAll(bfw, msg,false);
			 	}
				
			 	System.out.println(123);
			 	System.out.println(msg);
			 }
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}


	// Manda para todos menos o proprio cliente
	public void sendToAll(BufferedWriter bwSaida, String msg, boolean respostaCorreta) throws IOException {
		BufferedWriter bws;
		
		for (BufferedWriter bw : clientes) {
			bws = (BufferedWriter)bw;
			if (!(bwSaida == bws)) {
				
				bw.write(nome + " -> " + msg + "\r\n");
				if(respostaCorreta){
					// acertou(bwSaida);
					// bw.flush();
					
					bw.write("==================:"+"\r\n");		
					bw.write(nome + " respondeu Corretamente!"+"\r\n");
					bw.write("==================:"+"\r\n");		
					bw.write("\r\n");

					List<Integer> numeros = this.dao.getAllIdDisponivel();
					if(numeros.size() != 0) {
						bw.write(this.dao.getPergunta(indiceAtivo).getTitulo() + "\r\n");
					}
					// bw.write("\r\n");			
					// this.perguntas.get(indiceAtivo).set(2,"s");
					// this.perguntas.get(indiceAtivo).add(nome);
						
				}
				bw.flush();
			}
		}
	}
	public void maisUmHost(){
		this.quantidadeHosts++;
	}
	public static void main (String []args) {
		try {
			JLabel lblMessage = new JLabel("Porta do Servidor");
			JTextField txtPorta = new JTextField("12345");
			Object[] texts = {lblMessage, txtPorta};
			JOptionPane.showMessageDialog(null, texts);
			server = new ServerSocket(Integer.parseInt(txtPorta.getText()));
			clientes = new ArrayList<BufferedWriter>();
			JOptionPane.showMessageDialog(null, "Servidor ativo na porta: " + txtPorta.getText());
			
			while (true) {
				System.out.println("Aguardando a conexão...");
				Socket con = server.accept();
				System.out.println("Cliente conectado...");
				Servidor tratamento = new Servidor(con);
				Thread t = new Thread(tratamento);
				t.start();
				// t.sleep(200);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}


	private boolean podePerguntar(Integer numeroAleatorio){
        if(this.dao.getPergunta(numeroAleatorio).isAtiva() == false){
            return true;
        }
        return false;		
		// this.perguntas.get(indiceAtivo).set(2,"s");
		// this.perguntas.get(indiceAtivo).add(nome);
		// pergunta(bw,true);	
    }
	public void resultado(BufferedWriter bw) throws IOException{

        bw.write("=== Respostas ===\r\n");
		List<Pergunta> perguntas = dao.getTodasPerguntas();
        for (int i = 0;i<perguntas.size();i++) {
            bw.write("P"+i+": "+perguntas.get(i).getRespondeu() + "\r\n");
        }
		bw.flush();

    }
	public void resultadoSendoToAll(BufferedWriter bwSaida) throws IOException{
		BufferedWriter bws;
		for (BufferedWriter bw : clientes) {
			bws = (BufferedWriter)bw;
			if (!(bwSaida == bws)) {
				bw.write("=== Respostas ===\r\n");
				List<Pergunta> perguntas = dao.getTodasPerguntas();
				for (int i = 0;i<perguntas.size();i++) {
					bw.write("P"+i+": "+perguntas.get(i).getRespondeu() + "\r\n");
				}
				bw.flush();
			}
		}

//	dao.truncate();


    }
	public void acertou(BufferedWriter bw) throws IOException{
		bw.write("==================:"+"\r\n");		
		bw.write("Você respondeu Corretamente! "+ "\r\n");
		bw.write("==================:"+"\r\n");		
		bw.write("\r\n");		
		bw.flush();
//		this.perguntas.get(indiceAtivo).setJaRespondida(true);
//		this.perguntas.get(indiceAtivo).setRespondeu(nome);
//		this.perguntas.get(indiceAtivo).setAtiva(false);
		this.dao.perguntaRespondida(indiceAtivo, nome);
		pergunta(bw,true);
	}
//	public Integer indicePerguntaAtiva(){
//		for (int i = 0;i<perguntas.size();i++) {
//			if(this.perguntas.get(i).isAtiva() == true){
//				return i;
//			}
//		}
//		return null;
//	}
	public void pergunta(BufferedWriter bw, boolean perguntaNova) throws IOException{
        try{
			
//
			Random r = new Random();
			List<Integer> numeros = this.dao.getAllIdDisponivel();
			if(numeros.size() != 0) {

				int id = numeros.get(r.nextInt(numeros.size()));

				boolean naoTemIndice = false;
				try {
					naoTemIndice = !(this.indiceAtivo >= 0);
				} catch (NullPointerException e) {
					naoTemIndice = true;
				}


				if (naoTemIndice) {
					Integer indicePerguntaAtiva = this.dao.indicePerguntaAtiva();
					if (indicePerguntaAtiva != null) {
						indiceAtivo = indicePerguntaAtiva;
						String textoPergunta = this.dao.getPergunta(indicePerguntaAtiva).getTitulo();
						bw.write(textoPergunta + "\r\n");
						bw.flush();
					} else {
						String textoPergunta = this.dao.getPergunta(id).getTitulo();
						bw.write(textoPergunta + "\r\n");
						indiceAtivo = id;
//					this.perguntas.get(indiceAtivo).setAtiva(true);
						this.dao.ativaPergunta(indiceAtivo);
						bw.flush();
					}
				} else if (this.quantidadeHosts > 1 && naoTemIndice) {
					pergunta(bw, perguntaNova);
				} else if (perguntaNova == false) {
					String textoPergunta = this.dao.getPergunta(id).getTitulo();
					bw.write(textoPergunta + "\r\n");
					bw.flush();
				} else if (perguntaNova == true && podePerguntar(id)) {
					String textoPergunta = this.dao.getPergunta(id).getTitulo();
					bw.write(textoPergunta + "\r\n");
					this.indiceAtivo = id;
//				this.perguntas.get(indiceAtivo).setAtiva(true);
					this.dao.ativaPergunta(indiceAtivo);
					bw.flush();
				}
			}
        }catch(NullPointerException e){
            
            System.out.print(e);
        }
	}
    public int numeroAleatorio(int min, int max){

        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;
    
        return randomNum;
    }

}