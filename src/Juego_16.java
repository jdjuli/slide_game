import java.awt.BorderLayout;

import javax.swing.JFrame;


public class Juego_16 {
	
	public static void main(String[] arg){
		JFrame jframe = new JFrame(); //Creo la ventana
		Juego j = new Juego(4,4); //construyo un nuevo juego de 4x4 fichas
		Contador c = new Contador(); //creo el panel para llevar las estadisticas del juego
		j.setContador(c); //cruzo los objetos Juego y Contador para que puedan utilizarse mutuamente
		c.setJuego(j);
		
		
		jframe.setLayout(new BorderLayout());//Establezco un BorderLayout como organizador de ventana
		jframe.add(j,BorderLayout.CENTER);//Coloco el juego en el centro
		jframe.add(c,BorderLayout.SOUTH);//y el panel informativo debajo (sur)
		jframe.pack(); //Llamo al metodo pack para que se ajuste la ventana al contenido
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//El programa termina si se cierra la ventana
		jframe.setResizable(false);//No permito redimensionar la ventana
		jframe.setVisible(true);//Muestro la ventana haciendola visible
	}
	
}