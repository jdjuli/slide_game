import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class Contador extends JPanel implements Runnable,ActionListener{
	private long inicio;
	private JLabel tiempo;
	private int segundos;
	private int minutos;
	private JLabel movimientos;
	private JButton reset;
	private Juego juego; //Juego. necesario para reiniciarlo
	private Thread selfThread;
	private int num_movimientos=0;
	
	public Contador(){
		selfThread = new Thread(this);
		selfThread.start();
		tiempo = new JLabel("hola");
		movimientos = new JLabel("mundo");
		reset = new JButton("Reiniciar");
		JPanel resetPane = new JPanel();
		resetPane.add(reset);
		this.setLayout(new BorderLayout());
		reset.addActionListener(this);
		this.add(tiempo,BorderLayout.WEST);
		this.add(resetPane,BorderLayout.CENTER);
		this.add(movimientos,BorderLayout.EAST);
		this.setPreferredSize(new Dimension( 0 , 35) );
	}
	
	public void addMovimiento(){
		if(num_movimientos == 0 ){
			iniciarCronometro();
		}
		num_movimientos++;
		update();
	}
	
	public void iniciarCronometro(){
		inicio = System.nanoTime();
	}
	
	public void run(){
		while(true){
			update();
			try{
				Thread.sleep(1000);
			}catch(Exception e){}
		}
	}
	
	public void update(){
		segundos = (int) ((System.nanoTime() - inicio) / 1000000000) ;
		minutos = segundos / 60 ;
		movimientos.setText(String.valueOf(num_movimientos));
		if(num_movimientos == 0){
			tiempo.setText("00m 00s");
		}else{
			tiempo.setText(String.format("%1$02dm %2$02ds" , minutos , segundos%60 ));
		}
	}
	
	public void setJuego(Juego j){
		this.juego = j;
	}

	@Override
	public void actionPerformed(ActionEvent e){
		if(e.getSource() == reset){
			selfThread.interrupt();
			//reinicio el tablero de juego
			juego.reset();
			//reinicio el contador
			this.reset();
		}
		
	}

	public void reset(){
		num_movimientos = 0;
	}
	
	public int getSegundos(){
		return this.segundos;
	}
	
	public int getMinutos(){
		return this.minutos;
	}
	
	public int getMovimientos(){
		return num_movimientos;
	}
}