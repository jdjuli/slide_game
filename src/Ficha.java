import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;



public class Ficha {
	/**
	 * Alto de la ficha en píxeles
	 */
	private static int alto;
	/**
	 * Ancho de la ficha en píxeles
	 */
	private static int ancho;
	/**
	 * Código que identifica a la ficha. Debe ser único dentro del conjunto de fichas del juego en que se 
	 * emplee
	 */
	private int codigo;
	
	/**
	 * Constructor que nos permite crear una ficha indicando su código y su alto y ancho en píxeles con que 
	 * se pintará cuando se llame al método paint
	 * 
	 * @param codigo de la ficha
	 * @param ancho en píxeles
	 * @param alto en píxeles
	 */
	public Ficha(int codigo, int ancho, int alto) {
		//El alto y ancho deben asignarse en otro método Static y no en los constructores
		this.codigo = codigo;
		Ficha.ancho = ancho;
		Ficha.alto = alto;
	}
	
	/**
	 * Método que hace que la ficha se pinte a si misma sobre un objeto Graphics en las coordenadas indicadas.
	 * Hace uso del campo código para que cada ficha sea diferente, contemplando que la ficha con el código 0
	 * no debe mostrar ningun número.
	 * 
	 * @param g objeto Graphics sobre el que se pinta
	 * @param x coordenada horizontal (pixeles)
	 * @param y coordenada vertical (pixeles)
	 */
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
	
	/**
	 * Getter del codigo que identifica a la ficha
	 * @return código identificador
	 */
	public int getCodigo(){
		return codigo;
	}
}