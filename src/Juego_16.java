import java.awt.BorderLayout;

import javax.swing.JFrame;


public class Juego_16 {
	
	public static void main(String[] arg){
		JFrame jframe = new JFrame();
		Juego j = new Juego(2,3);
		Contador c = new Contador();
		j.setContador(c);
		c.setJuego(j);
		
		jframe.setLayout(new BorderLayout());
		jframe.add(j,BorderLayout.CENTER);
		jframe.add(c,BorderLayout.SOUTH);
		jframe.pack();
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.setResizable(false);
		jframe.setVisible(true);
	}
	
}