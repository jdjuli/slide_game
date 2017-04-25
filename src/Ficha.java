import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;


/**
 * Esta clase contiene todo lo relativo a la unidad de juego Ficha, teniendo especial importancia
 * el m�todo paint, dise�ado para que las entidades se "auto-dibujen" sobre el objeto Graphics que
 * se las pase como par�metro, liberando as� de esta tarea a la clase que gestiona el desarrollo del
 * juego.
 * 
 * @author Juli�n
 *
 */
public class Ficha {
	/**
	 * Color que se usar� de fondo en las fichas con codigo par
	 */
	private final static Color colorPar = new Color(250,200,200);
	/**
	 * Color que se usar� de fondo en las fichas con codigo impar
	 */
	private final static Color colorImpar = new Color(200,200,200);
	/**
	 * Alto de la ficha en p�xeles. Es la misma para todas las fichas
	 */
	private static int alto;
	/**
	 * Ancho de la ficha en p�xeles. Es la misma para todas las fichas
	 */
	private static int ancho;
	/**
	 * C�digo que identifica a la ficha. Debe ser �nico dentro del conjunto de fichas del juego en que se 
	 * emplee
	 */
	private int codigo;
	
	/**
	 * Constructor que nos permite crear una ficha indicando su c�digo y su alto y ancho en p�xeles con que 
	 * se pintar� cuando se llame al m�todo paint
	 * 
	 * @param codigo de la ficha
	 */
	public Ficha(int codigo) {
		//El alto y ancho deben asignarse en otro m�todo Static y no en los constructores
		this.codigo = codigo;
	}
	
	/**
	 * M�todo que hace que la ficha se pinte a si misma sobre un objeto Graphics en las coordenadas indicadas.
	 * Hace uso del campo c�digo para que cada ficha sea diferente, contemplando que la ficha con el c�digo 0
	 * no debe mostrar ningun n�mero.
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
			//el marco es un rectangulo hueco del mismo tama�o que el fondo
			g.drawRect( x , y , ancho , alto );
			//Selecciono la fuente con que se dibujar� el numero y lo dibujo
			Font font = new Font("Arial",Font.BOLD, alto/3);
			g.setFont(font);
			g.drawString(String.valueOf(codigo),x+5,y+alto-5);
		}else{
			g.setColor(Color.white);
			g.fillRect( x , y , ancho , alto );
		}
	}
	
	/*
	 * No se considera crear getters de los atributos ancho y alto porque son atributos que deber�an de ser fijados al 
	 * inicio del programa y permanecer constantes.
	 * 
	 * Tampoco se deber�a cambiar el c�digo de ninguna ficha, por lo que para evitar que esto suceda por error, no se
	 * ha programado ningun setter para �l.
	 */
	/**
	 * Getter del codigo que identifica a la ficha
	 * @return c�digo identificador
	 */
	public int getCodigo(){
		return codigo;
	}
	/**
	 * Setter del atributo ancho, que representa el ancho de cada ficha en p�xeles , el cual es com�n a todas ellas
	 * @param ancho Ancho de las fichas en p�xeles
	 */
	public static void setAncho(int ancho){
		Ficha.ancho = ancho;
	}
	/**
	 * Setter del atributo alto, que representa el alto de cada ficha en p�xeles, el cual es com�n a todas ellas
	 * @param alto Alto de las fichas en p�xeles
	 */
	public static void setAlto(int alto){
		Ficha.alto = alto;
	}
}