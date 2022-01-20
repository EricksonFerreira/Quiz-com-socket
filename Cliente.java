import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.Socket;
// import java.util.ArrayList;
// import java.util.List;
import java.util.Random;
import javax.swing.*;

public class Cliente extends JFrame implements ActionListener, KeyListener {
	
	private static final long serialVersionUID = 1l;
	private JTextArea texto;
	private JTextField txtMsg;
	private JButton btnSend;
	private JButton btnSair;
	private JLabel lblHistorico;
	private JLabel lblMsg;
	private JPanel pnlContent;
	private Socket socket;
	private OutputStream ou;
	private Writer ouw;
	private BufferedWriter bfw;
	private JTextField txtIP;
	private JTextField txtPorta;
	private JTextField txtNome;
	// public List<List<Pegunta>> perguntas = new ArrayList<Pergunta>();
    // public int indiceAtivo;
    
	
	public Cliente() throws IOException{
        // perguntas.add(new ArrayList<>());
        // perguntas.get(0).add("Qual a capital de Pernambuco?");
        // perguntas.get(0).add("Recife");
        // perguntas.get(0).add("n");
        // perguntas.add(new ArrayList<>());
        // perguntas.get(1).add("Quem descobriu o Brasil?");
        // perguntas.get(1).add("Pedro Alvares Cabral");
        // perguntas.get(1).add("n");
        // perguntas.add(new ArrayList<>());
        // perguntas.get(2).add("Em qual país é situado o Vaticano?");
        // perguntas.get(2).add("Vaticano");
        // perguntas.get(2).add("n");
        // perguntas.add(new ArrayList<>());
        // perguntas.get(3).add("Qual a capital do Brasil?");
        // perguntas.get(3).add("Brasilia");
        // perguntas.get(3).add("n");
        // perguntas.add(new ArrayList<>());
        // perguntas.get(4).add("Qual a nacionalidade de Neymar?");
        // perguntas.get(4).add("Brasileiro");
        // perguntas.get(4).add("n");
        // perguntas.add(new ArrayList<>());
        // perguntas.get(5).add("Quando vai ser a próxima copa?");
        // perguntas.get(5).add("2022");
        // perguntas.get(5).add("n");
        // perguntas.add(new ArrayList<>());
        // perguntas.get(6).add("Onde vai ser a próxima copa?");
        // perguntas.get(6).add("Catar");
        // perguntas.get(6).add("n");
        
		JLabel lblMessage = new JLabel("Verificar");
		txtIP = new JTextField("127.0.0.1");
		txtPorta = new JTextField("12345");
		txtNome = new JTextField("cliente");
		Object[] texts = {lblMessage, txtIP, txtPorta, txtNome};
		JOptionPane.showMessageDialog(null, texts);
		pnlContent = new JPanel();
		texto = new JTextArea(10,20);
		texto.setEditable(false);
		texto.setBackground(new Color(240,240,240));
		txtMsg = new JTextField(20);
		lblHistorico = new JLabel("Histórico");
		lblMsg = new JLabel("Mensagem");
		btnSend = new JButton("Enviar");
		btnSend.setToolTipText("Enviar Mensagem");
		btnSair = new JButton("Sair");
		btnSair.setToolTipText("Sair do Chat");
		btnSend.addActionListener(this);
		btnSair.addActionListener(this);
		btnSend.addKeyListener(this);
		btnSair.addKeyListener(this);
		JScrollPane scroll = new JScrollPane(texto);
		texto.setLineWrap(true);
		pnlContent.add(lblHistorico);
		pnlContent.add(scroll);
		pnlContent.add(lblMsg);
		pnlContent.add(txtMsg);
		pnlContent.add(btnSair);
		pnlContent.add(btnSend);
		pnlContent.setBackground(Color.LIGHT_GRAY);
		texto.setBorder(BorderFactory.createEtchedBorder(Color.BLUE, Color.BLUE));
		txtMsg.setBorder(BorderFactory.createEtchedBorder(Color.BLUE, Color.BLUE));
		setTitle(txtNome.getText());
		setContentPane(pnlContent);
		setLocationRelativeTo(null);
		setResizable(false);
		setSize(250,300);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
        
	}
	public void conectar() throws IOException{
        socket = new Socket(txtIP.getText(), Integer.parseInt(txtPorta.getText()));
		ou = socket.getOutputStream();
		ouw = new OutputStreamWriter(ou);
		bfw = new BufferedWriter(ouw);
        // pergunta();

        bfw.write(txtNome.getText()+ "\r\n");
		bfw.flush();
	}
    // private boolean podePerguntar(int numeroAleatorio){
    //     if(perguntas.get(numeroAleatorio).get(2).equals("n")){
    //         return true;
    //     }
    //     return false;
    // }
	// public void resultado() throws IOException{
    //     texto.append("=== Respostas ===\r\n");
    //     for (int i = 0;i<perguntas.size();i++) {
    //         texto.append("P"+i+": "+perguntas.get(i).get(3) + "\r\n");
    //     }

    // }
	// public void pergunta() throws IOException{
    //     try{
    //         int numeroAleatorio = numeroAleatorio(0,6);
    //         if(podePerguntar(numeroAleatorio)){
    //             String textoPergunta = perguntas.get(numeroAleatorio).get(0);
    //             texto.append(textoPergunta + "\r\n");
    //             // indiceAtivo = numeroAleatorio;
    //             bfw.flush();
    //         }else{
    //             int c = 0;
    //             for (int i = 0;i<perguntas.size();i++) {
    //                 if(perguntas.get(i).get(2).equals("n")){
    //                     c++;
    //                 }
    //             }
    //             if(c > 0){
    //                 pergunta();
    //             }else{
    //                 resultado();
    //             }
    //         }
    //     }catch(NullPointerException e){
            
    //         System.out.print(e);
    //     }
	// }
    public int numeroAleatorio(int min, int max){

        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;
    
        return randomNum;
    }
	public void enviarMensagem(String msg) throws IOException{
		if (msg.equals("Sair")) {
			bfw.write("Desconhecido \r\n");
			texto.append("Desconectado \r\n");
		} else {
			bfw.write(msg + "\r\n");
			texto.append(txtNome.getText() + " diz -> " + txtMsg.getText() + "\r\n");
            // if(perguntas.get(indiceAtivo).get(1).equals(txtMsg.getText())){
            //     texto.append(txtNome.getText() + " respondeu Corretamente! "+ "\r\n");
            //     perguntas.get(indiceAtivo).set(2,"s");
            //     perguntas.get(indiceAtivo).add(txtNome.getText());
            //     texto.append("\r\n");
            //     texto.append("==================== "+ "\r\n");
            //     pergunta();
            // }
		}
		bfw.flush();
		txtMsg.setText("");
	}
	public void escutar() throws IOException{

		   InputStream in = socket.getInputStream();
		   InputStreamReader inr = new InputStreamReader(in);
		   BufferedReader bfr = new BufferedReader(inr);
		   String msg = "";

		    while(!"Sair".equalsIgnoreCase(msg))

		       if(bfr.ready()){
		         msg = bfr.readLine();
		       if(msg.equals("Sair"))
		         texto.append("Servidor caiu! \r\n");
		        else
		         texto.append(msg+"\r\n");
		        }
		}
	public void sair() throws IOException {
		enviarMensagem("Sair");
		bfw.close();
		ouw.close();
		ou.close();
		socket.close();
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			try {
				enviarMensagem(txtMsg.getText());
			} catch (Exception e2) {
				// TODO: handle exception
				e2.printStackTrace();
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		try {
			if (e.getActionCommand().equals(btnSend.getActionCommand()))
			enviarMensagem(txtMsg.getText());
			else
			   if (e.getActionCommand().equals(btnSair.getActionCommand()))
			   sair();
		} catch (IOException e1) {
			// TODO: handle exception
			e1.printStackTrace();
		}
	}
	public static void main(String []args) throws IOException{

		   Cliente app = new Cliente();
		   app.conectar();
		   app.escutar();
		}

}