

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * Esta es la clase más importante del juego ya que es el juego en sí, extiende JPanel
 * y contendrá las fichas de que se compone el juego, encargándose de que que la
 * interaccion con el usuario sea correcta y los datos aportados por la clase Contador
 * también sean correctos.
 * 
 * @author Julián
 *
 */
public class Juego extends JPanel implements MouseListener{
	/**
	 * Alto que se usará para pintar cada ficha en píxeles
	 */
	private final static int ALTO_FICHA = 50; 
	/**
	 * Ancho que se usará para pintar cada ficha en píxeles
	 */
	private final static int ANCHO_FICHA = 50;
	/**
	 * Margen entre fichas. No se aplica a cada ficha, es decir,
	 * un ancho de 5px no genera un espacio entre fichas de 10px
	 */
	private final static int SEPARACION_FICHA = 10;
	/**
	 * Objeto que muestra la informacion durante el juego, es necesario para
	 * que puedan intercambiar mensajes
	 */
	private Contador c; //Barra de debajo, necesario para actualizar su informacion
	/**
	 * Numero de fichas en horizontal (columnas de fichas)
	 */
	private int h;
	/**
	 * Numero de fichas en vertical (filas de fichas)
	 */
	private int v;
	/**
	 * Matriz que almacena las fichas
	 */
	private Ficha[][] fichas;

	
	//CONSTRUCTORES
	/**
	 * Este es el único constructor, permite crear un nuevo tablero de juego indicando el número de
	 * fichas que tendrá, tanto en horizontal como en vertical
	 * 
	 * @param h Numero de fichas horizontales
	 * @param v Numero de fichas verticales
	 * @throws RuntimeException Si alguna de las dimensiones del tablero es inferior a 2
	 */
	public Juego(int h, int v) throws RuntimeException{
		//h = columnas     v = filas
		//El juego tiene que ser de al menos 2*2 cuadrados, menos que eso 
		//es injugable y debe lanzar excepcion
		if(h<2 || v<2) throw new RuntimeException("El tablero tiene que ser al menos de 2*2");
		this.h = h;
		this.v = v;
		//Paso a la clase Ficha el tamaño que deberán tener todas las fichas que se utilicen
		Ficha.setAlto(ALTO_FICHA);
		Ficha.setAncho(ANCHO_FICHA);
		//creo las fichas y las coloco aleatoriamente
		iniciarFichas();
		//inicio el tablero dándole el tamaño apropiado.
		iniciarTablero();
		this.addMouseListener(this);
	}
	
	/**
	 * Este método debe ser llamado por el constructor, sirve para dimensionar el panel que
	 * contendrá las fichas.
	 */
	private void iniciarTablero(){
		//configuro el alto y ancho del panel a partir del numero de fichas y las constantes de tamaño.
		int ancho=SEPARACION_FICHA*(h+1)+ANCHO_FICHA*h;
		int alto=SEPARACION_FICHA*(v+1)+ALTO_FICHA*v;
		this.setPreferredSize( new Dimension(ancho,alto) );
	}
	/**
	 * Este método es llamado por el constructor pero debe llamarse también cada vez que se
	 * desee generar un juego nuevo.
	 * <br>
	 * No permite cambiar el numero de fichas, solo vuelve a crearlas y a colocarlas de 
	 * forma aleatoria.
	 */
	private void iniciarFichas(){
		//inicializo el array de fichas con el tamaño requerido
		fichas = new Ficha[h][v];
		//creo un ArrayList de tamaño suficiente como para contener todas las fichas
		ArrayList<Ficha> _fichas = new ArrayList<Ficha>(h*v);
		//creo un objeto Random para colocar aleatoriamente las fichas
		Random r = new Random();
		int rnd;
		for(int i=0 ; i<h*v ; i++){//creo las fichas dentro del ArrayList
			_fichas.add(new Ficha(i));
		}
		for(int i=0 ; i<h ; i++){
			//introduzco las fichas aleatoriamente dentro de la matriz
			for(int j=0 ; j<v ; j++){
				rnd = r.nextInt(_fichas.size());
				fichas[i][j] = _fichas.get(rnd);
				//voy borrando la ficha que introduzco para que no se repitan
				_fichas.remove(rnd);
			}
		}
	}
	
	/**
	 * Método sobrescrito de la clase <b>JPanel</b>.
	 * <br>
	 * Se encarga de pintar las fichas sobre el panel. La clase ficha está diseñada para 
	 * "autopintarse", por eso se llama a .paint(Graphics , int , int) una vez por cada 
	 * ficha de la matriz. Previamente se calcula la coordenada de la esquina superior izda
	 * desde donde debe empezar a pintarse la ficha.
	 */
	protected void paintComponent(Graphics g) {
		//Pinto de color blanco un rectángulo relleno del mismo tamaño que el panel
		g.setColor(Color.white);
		g.fillRect(0,0,(int) this.getSize().getHeight(), (int) this.getSize().getWidth());
		//recorro la matriz pidiendo a cada ficha que se pinte
		for(int i=0 ; i<fichas.length ; i++){//h
			for(int j=0 ; j<fichas[0].length ; j++){//v
				//calculo las coordenadas en que debe pintarse la ficha
				int y = SEPARACION_FICHA*(j+1)+ANCHO_FICHA*j;
				int x = SEPARACION_FICHA*(i+1)+ALTO_FICHA*i;
				//y se las paso como parámetros
				fichas[i][j].paint(g,x,y);
			}
		}
	}
	
	/**
	 * Dada una coordenada vertical devuelve la fila de fichas a la que corresponde o 
	 * Excepcion si no corresponde con ninguna fila (por ejemplo si la coordenada es del
	 * espacio que separa una fila de otra).<br>
	 * Funciona dinámicamente en base a las constantes privadas de la clase, por lo que
	 * no es necesario modificar este método para mantenerlo operativo si se modifican los
	 * tamaños del programa.
	 * 
	 * @param y coordenada vertical en pixeles
	 * @return <b>fila</b> Fila de una hipotética matriz de fichas
	 * @throws GameException Si la la coordenada no corresponde con ninguna fila
	 */
	private int getFila(int y) throws GameException{
		//v es el numero de filas de que se compone el tablero
		for(int i=0 ; i<v ; i++){
			//la ficha tiene que estar entre las coordenadas inicio y fin.
			//si el numero pasado como parametro (coordenada y del raton)
			//se encuentra en ese intervalo, se ha hecho clic en esa fila y
			//por tanto devuelvo el número de la fila
			int inicio = SEPARACION_FICHA*(i+1)+ALTO_FICHA*i;
			int fin = inicio+ALTO_FICHA;
			if(y>=inicio && y<=fin) return i;
		}
		//si durante el bucle no se alcanza el return es porque no se ha seleccionado
		//ninguna fila, esto lo he decidido codificar como una excepcion
		throw new GameException("No has seleccionado ninguna fila");
	}
	
	/**
	 * Dada una coordenada horizontal devuelve la columna de fichas a la que corresponde o 
	 * Excepcion si no corresponde con ninguna columna (por ejemplo si la coordenada es del
	 * espacio que separa una columna de otra).<br>
	 * Funciona dinámicamente en base a las constantes privadas de la clase, por lo que
	 * no es necesario modificar este método para mantenerlo operativo si se modifican los
	 * tamaños del programa.
	 * 
	 * @param x coordenada horizontal en pixeles
	 * @return <b>columna</b> Columna de una hipotética matriz de fichas
	 * @throws GameException Si la coordenada no corresponde con ninguna columna
	 */
	private int getColumna(int x) throws GameException{
		//h es el numero de columnas de que se compone el tablero
		for(int i=0 ; i<h ; i++){
			//la ficha tiene que estar entre las coordenadas inicio y fin.
			//si el numero pasado como parametro (coordenada x del raton)
			//se encuentra en ese intervalo, se ha hecho clic en esa columna y
			//por tanto devuelvo el número de la columna
			int inicio = SEPARACION_FICHA*(i+1)+ANCHO_FICHA*i;
			int fin = inicio+ANCHO_FICHA;
			if(x>=inicio && x<=fin) return i;
		}
		//si durante el bucle no se alcanza el return es porque no se ha seleccionado
		//ninguna fila, esto lo he decidido codificar como una excepcion
		throw new GameException("No has seleccionado ninguna columna");
	}
	
	/**
	 * Este metodo determina si una ficha se puede o no mover, para ello comprueba sobre la 
	 * matriz de fichas si la ficha seleccionada tiene en su misma fila y columna (se 
	 * verifican ambas) la ficha con el codigo "0", asociada al espacio vacío. 
	 * Solo si se diera esta condición, la ficha podría moverse, en caso contrario, la ficha 
	 * estaría rodeada y bloqueada por otras.
	 * 
	 * @param fila de la ficha a evaluar
	 * @param columna de la ficha a evaluar
	 * @return true|false si se puede o no mover respectivamente
	 */
	private boolean fichaMovible(int fila, int columna){
		for(int i=0 ; i<v ; i++){//busco la ficha 0 en la columna de la ficha elegida
			if(fichas[columna][i].getCodigo()==0){//si el codigo de alguna de las fichas es 0 devuelvo true
				return true;
			}
		}
		for(int i=0 ; i<h ; i++){//hago lo mismo pero en la fila correspondiente
			if(fichas[i][fila].getCodigo()==0){//tambien si el codigo de alguna es 0, devuelvo true
				return true;
			}
		}
		//si no se ha encontrado la ficha 0 en la fila y columna, es que la ficha está 'bloqueada' 
		//(no tiene a su alrededor el espacio vacío) y por tanto no se puede deslizar o mover
		return false;
	}
	
	/**
	 * Desplaza la ficha seleccionada un hueco hacia el espacio en blanco, arrastrando a otras
	 * fichas cuando sea necesario. 
	 * Solo requiere la posicion de la ficha a mover, internamente evalúa si ésta se puede o no
	 * mover y en caso afirmativo, es capaz de decidir el sentido y longitud del desplazamiento.
	 * Por último, este método actualiza los movimientos realizados y comprueba si el juego ha 
	 * finalizado (si se ordenó la última ficha)
	 * @param fila de la ficha que se desea mover
	 * @param columna de la ficha que se desea mover
	 */
	private void moverFicha(int fila, int columna){
		//declaro dos variables para almacenar la posicion de la ficha vacía
		int filaFichaVacia=-1;
		int columnaFichaVacia=-1;
		//busco la ficha vacía en la matriz y almaceno sus coordenadas
		for(int i=0; i<h ; i++){
			for(int j=0 ; j<v ; j++){
				if(fichas[i][j].getCodigo() == 0){
					filaFichaVacia=j;
					columnaFichaVacia=i;
				}
			}
		}
		//guardo la ficha en otro objeto
		Ficha fichaVacia = fichas[columnaFichaVacia][filaFichaVacia];
		//esta parte es compleja, el primer if decide si hay que mover la ficha horizontal o
		//verticalmente y el segundo (anidado) decide el sentido del desplazamiento.
		if(fila == filaFichaVacia){
			//si la ficha vacía y la ficha seleccionada estan en la misma fila, el desplazamiento es horizontal
			if(columna > columnaFichaVacia){
				//si la ficha elegida se encuentra más a la dcha. que la ficha vacía, se desplaza hacia la IZDA
				for(int i=columnaFichaVacia ; i<columna ; i++){
					fichas[i][fila]=fichas[i+1][fila];
				}
			}else if(columna < columnaFichaVacia){
				//si la ficha elegida se encuentra más a la izda. que la ficha vacía, se desplaza hacia la DCHA
				for(int i=columnaFichaVacia ; i>columna ; i--){
					fichas[i][fila]=fichas[i-1][fila];
				}
			}
		}else if(columna == columnaFichaVacia){
			//si coinciden la columna de la ficha elegida y la vacía, el desplazamiento es vertical
			if(fila > filaFichaVacia){
				//si la fila de la ficha elegida esta más abajo que la fila de la ficha vacía el desplazamiento es hacia ARRIBA
				for(int i=filaFichaVacia ; i<fila ; i++){
					fichas[columna][i]=fichas[columna][i+1];
				}
			}else if(fila < filaFichaVacia){
				//si la fila de la ficha elegida esta más arriba que la fila de la ficha vacía el desplazamiento es hacia ABAJO
				for(int i=filaFichaVacia ; i>fila ; i--){
					fichas[columna][i]=fichas[columna][i-1];
				}
			}
		}
		//despues de mover las fichas, coloco de nuevo la ficha vacía en el lugar en que estaba antes la ficha que se quería mover
		fichas[columna][fila] = fichaVacia;
		//solo se considera movimiento cuando se hace clic en una casilla que no sea
		//la ficha vacía
		if(fila != filaFichaVacia || columna != columnaFichaVacia){
			c.addMovimiento();//sumo 1 a los movimientos
			this.repaint();//repinto la ventana
			comprobarVictoria();//compruebo si se ha terminado el juego
		}
	}
	
	/**
	 * Metodo que comprueba el final del juego, para ello analiza la matriz elemento a elemento
	 * comprobando si en cada caso la ficha es la que corresponde a esa posición.
	 * A la ficha con el código 0 le corresponde la última posición y no la primera por ser
	 * interpretada como la ficha vacía.
	 * En caso de ganar muestra las estadísticas del juego en un mensaje informativo (tiempo y
	 * movimientos) y al cerrar el mensaje reinicia tanto el juego como el panel informativo.
	 */
	private void comprobarVictoria(){
		int fichaEsperada=1;//la primera ficha es la 1 ya que la 0 es el espacio vacío que debe estar la ultima
		boolean victoria=true;
		//recorro la matriz evaluando que, si es la ultima casilla, el código debe ser 0. Los numeros deben estar
		//ordenados de forma ascendente en orden de lectura.
		//Ejemplo tablero 4*4:
		//  1   2   3   4
		//  5   6   7   8
		//  9  10  11  12
		// 13  14  15   0
		for(int i=0 ; i<v ; i++){
			for(int j=0 ; j<h ; j++){
				if(fichaEsperada==v*h) fichaEsperada=0;
				if(fichas[j][i].getCodigo() != fichaEsperada){
					victoria=false;
				}
				fichaEsperada++;
			}
		}
		//si todas las fichas están donde las corresponde, muestro un mensaje informativo con el tiempo y los movimientos
		//requeridos
		if(victoria){
			String msg = "¡¡Has ganado!!"+
						"\r\n     Tiempo:           "+String.format("%1$02dmin %2$02dseg",c.getMinutos(),c.getSegundos()%60)+
						"\r\n     Movimientos: "+c.getMovimientos();
			JOptionPane.showMessageDialog(this,msg);
			//despues de cerrar el mensaje, reinicio el juego y el panel informativo
			c.reset();
			this.reset();
		}
		
	}
	
	//eventos raton. No los necesito pero es obligatorio implementarlos
    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    /**
     * Método sobreescrito de la intefaz MouseListener para recoger los clic que hace el usuario en 
     * el juego. Este método es el motor del juego ya que todos los eventos relacionados con las 
     * fichas se desencadenan a partir de la ejecución de este método.
     * 
     * Hace uso de una excepción GameException para controlar la posibilidad de que el usuario haga
     * clic fuera de una casilla.
     */
    public void mouseClicked(MouseEvent e) {
    	try{
    		//intento determinar la ficha sobre la que se ha hecho clic
    		int columna = getColumna(e.getX());
    		int fila = getFila(e.getY());
    		//si la ejecución llega aquí es que se ha pulsado una tecla y no el espacio de separacion
    		//entre ellas
    		//compruebo si la ficha seleccionada se puede mover y si es así, la muevo
    		if(fichaMovible(fila,columna)){
    			moverFicha(fila,columna);
    		}
    	}catch(GameException ge){//Se lanza una GameException si no se hace clic en una ficha. No requiere tratamiento
    	}catch(Exception ex){
    		//Si se produce alguna otra excepción, interesa saber la causa para resolverla, por lo que 
    		//imprimo el stack trace
    		ex.printStackTrace();
    	}
    }
    
    /**
     * Setter del objeto de la clase Contador, necesario para actualizar la información de los 
     * movimientos y reiniciarlo cuando termina la partida
     * @param c objeto de la clase Contador asociado al juego
     */
    public void setContador(Contador c){
    	this.c = c;
    }
    
    /**
     * Método que facilita la reinicialización del juego, está pensado para ser llamado desde otras
     * clases.
     */
    public void reset(){
    	//para reiniciar el juego basta con volver a crear y desordenar las fichas y repintar la ventana
    	iniciarFichas();
    	this.repaint();
    }
    
}