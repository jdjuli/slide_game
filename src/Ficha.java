import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;



public class Ficha {
	private static int alto;
	private static int ancho;
	private int codigo;
	
	public Ficha(int codigo, int ancho, int alto) {
		this.codigo = codigo;
		Ficha.ancho = ancho;
		Ficha.alto = alto;
	}
	
	public void paint(Graphics g , int x, int y){
		g.setColor(Color.white);
		g.fillRect( x , y , ancho , alto );
		if(this.codigo != 0){
			g.setColor(Color.black);
			g.drawRect( x , y , ancho , alto );
			Font font = new Font("Arial",Font.BOLD, alto/3);
			g.setFont(font);
			g.drawString(String.valueOf(codigo),x+5,y+alto-5);
		}
	}
	
	public int getCodigo(){
		return codigo;
	}
}