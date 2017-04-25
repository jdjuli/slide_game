import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;


/**
 * Esta clase contiene todo lo relativo a la unidad de juego Ficha, teniendo especial importancia
 * el método paint, diseñado para que las entidades se "auto-dibujen" sobre el objeto Graphics que
 * se las pase como parámetro, liberando así de esta tarea a la clase que gestiona el desarrollo del
 * juego.
 * 
 * @author Julián
 *
 */
public class Ficha {
	/**
	 * Color que se usará de fondo en las fichas con codigo par
	 */
	private final static Color colorPar = new Color(250,200,200);
	/**
	 * Color que se usará de fondo en las fichas con codigo impar
	 */
	private final static Color colorImpar = new Color(200,200,200);
	/**
	 * Alto de la ficha en píxeles. Es la misma para todas las fichas
	 */
	private static int alto;
	/**
	 * Ancho de la ficha en píxeles. Es la misma para todas las fichas
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
	 */
	public Ficha(int codigo) {
		//El alto y ancho deben asignarse en otro método Static y no en los constructores
		this.codigo = codigo;
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
		if(this.codigo != 0){
			//asigno el color correspondiente segun el codigo sea par o impar
			if(this.codigo%2==0){
				g.setColor(colorPar);
			}else{
				g.setColor(colorImpar);
			}
			//dibujo el rectangulo que es el fondo de la ficha
			g.fillRect( x , y , ancho , alto );
			//el marco y numero se dibujan siempre en negro
			g.setColor(Color.black);
			//el marco es un rectangulo hueco del mismo tamaño que el fondo
			g.drawRect( x , y , ancho , alto );
			//Selecciono la fuente con que se dibujará el numero y lo dibujo
			Font font = new Font("Arial",Font.BOLD, alto/3);
			g.setFont(font);
			g.drawString(String.valueOf(codigo),x+5,y+alto-5);
		}else{
			g.setColor(Color.white);
			g.fillRect( x , y , ancho , alto );
		}
	}
	
	/*
	 * No se considera crear getters de los atributos ancho y alto porque son atributos que deberían de ser fijados al 
	 * inicio del programa y permanecer constantes.
	 * 
	 * Tampoco se debería cambiar el código de ninguna ficha, por lo que para evitar que esto suceda por error, no se
	 * ha programado ningun setter para él.
	 */
	/**
	 * Getter del codigo que identifica a la ficha
	 * @return código identificador
	 */
	public int getCodigo(){
		return codigo;
	}
	/**
	 * Setter del atributo ancho, que representa el ancho de cada ficha en píxeles , el cual es común a todas ellas
	 * @param ancho Ancho de las fichas en píxeles
	 */
	public static void setAncho(int ancho){
		Ficha.ancho = ancho;
	}
	/**
	 * Setter del atributo alto, que representa el alto de cada ficha en píxeles, el cual es común a todas ellas
	 * @param alto Alto de las fichas en píxeles
	 */
	public static void setAlto(int alto){
		Ficha.alto = alto;
	}
}