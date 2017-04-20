import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class Contador extends JPanel implements Runnable,ActionListener{
	/**
	 * Elemento que mostrará el tiempo transcurrido
	 */
	private JLabel tiempo;
	/**
	 * Elemento que mostrará los movimientos realizados
	 */
	private JLabel movimientos;
	/**
	 * Botón para reiniciar el juego
	 */
	private JButton reset;
	/**
	 * Objeto Juego, es necesario para poder reiniciarlo cuando 
	 * se pulse el boton
	 */
	private Juego juego;
	/**
	 * El objeto contiene su propio hilo de ejecución para poder
	 * detenerse de forma autónoma e independiente del juego.
	 */
	private Thread selfThread;
	/**
	 * Marca de tiempo para poder calcular por diferencia el tiempo de juego.
	 * Solo debe actualizarse cuando se desee reiniciar el "cronómetro"
	 */
	private long inicio;
	/**
	 * Numero de movimientos realizados
	 */
	private int num_movimientos=0;
	/**
	 * Segundos de juego en el rango [0~59].
	 * Se calcula a partir de la diferencia entre System.nanoTime() y la variable inicio
	 */
	private int segundos;
	/**
	 * Minutos de juego. 
	 * Se calcula a partir de la diferencia entre System.nanoTime() y la variable inicio
	 */
	private int minutos;
	
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
	
	/**
	 * Añade un movimiento al contador de movimientos
	 */
	public void addMovimiento(){
		if(num_movimientos == 0 ){
			iniciarCronometro();
		}
		num_movimientos++;
		update();
	}
	
	/**
	 * Establece el tiempo de inicio que tomará el cronómetro
	 * como referencia para saber el tiempo de juego.
	 */
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
	
	/**
	 * Actualiza los campos de la barra, estableciendo los valores
	 * correspondientes al tiempo (que se expresa como "MMm SSs"
	 * y los movimientos empleados hasta ese momento
	 */
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
	
	/**
	 * Setter de Juego, no se contempla getter porque el juego se desarrolla
	 * sobre el mismo objeto Juego actualizando las fichas
	 * @param j objeto Juego sobre el cual debe actuar este panel
	 */
	public void setJuego(Juego j){
		this.juego = j;
	}

	/**
	 * Método implementado de ActionListener que se encarga de gestionar el 
	 * boton de reinicio del juego reinicializando los objetos a través de los 
	 * métodos reset()
	 */
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
	
	/**
	 * Método que facilita la reinicialización del panel
	 */
	public void reset(){
		num_movimientos = 0;
		segundos = 0;
		minutos = 0;
	}
	
	/**
	 * Getter que devuelve los segundos transcurridos de juego entre
	 * el inicio y la última actualización.
	 * 
	 * Está en formato [0~59]
	 * 
	 * @return segundos de juego
	 */
	public int getSegundos(){
		return this.segundos;
	}
	
	/**
	 * Getter que devuelve los minutos de juego transcurridos entre
	 * el inicio y la última actualización.
	 * 
	 * @return minutos de juego
	 */
	public int getMinutos(){
		return this.minutos;
	}
	
	/**
	 * Getter para obtener los movimientos realizados
	 * @return numero de movimientos 
	 */
	public int getMovimientos(){
		return num_movimientos;
	}
}