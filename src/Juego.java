

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Juego extends JPanel implements MouseListener{
	private final static int ALTO_FICHA = 50;
	private final static int ANCHO_FICHA = 50;
	private final static int SEPARACION_FICHA = 10;
	private Contador c; //Barra de debajo, necesario para actualizar su informacion
	private int h;
	private int v;
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
		if(h<2 || v<2) throw new RuntimeException("El tablero tiene que ser al menos de 2*2");
		this.h = h;
		this.v = v;
		iniciarFichas();
		iniciarTablero();
		this.addMouseListener(this);
	}
	
	/**
	 * Este método debe ser llamado por el constructor, sirve para dimensionar el panel que
	 * contendrá las fichas.
	 */
	private void iniciarTablero(){
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
		fichas = new Ficha[h][v];
		ArrayList<Ficha> _fichas = new ArrayList<Ficha>(h*v);
		Random r = new Random();
		int rnd;
		for(int i=0 ; i<h*v ; i++){//creo las fichas dentro del ArrayList
			_fichas.add(new Ficha(i,ANCHO_FICHA,ALTO_FICHA));
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
	
	//pintar tablero
	
	/**
	 * Método sobrescrito de la clase <b>JPanel</b>.
	 * <br>
	 * Se encarga de pintar las fichas sobre el panel. La clase ficha está diseñada para 
	 * "autopintarse", por eso se llama a .paint(Graphics , int , int) una vez por cada 
	 * ficha de la matriz. Previamente se calcula la coordenada de la esquina superior izda
	 * desde donde debe empezar a pintarse la ficha.
	 */
	protected void paintComponent(Graphics g) {
		for(int i=0 ; i<fichas.length ; i++){//h
			for(int j=0 ; j<fichas[0].length ; j++){//v
				int y = SEPARACION_FICHA*(j+1)+ANCHO_FICHA*j;
				int x = SEPARACION_FICHA*(i+1)+ALTO_FICHA*i;
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
			int inicio = SEPARACION_FICHA*(i+1)+ALTO_FICHA*i;
			int fin = inicio+ALTO_FICHA;
			if(y>=inicio && y<=fin) return i;
		}
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
		//v es el numero de filas de que se compone el tablero
		for(int i=0 ; i<h ; i++){
			int inicio = SEPARACION_FICHA*(i+1)+ANCHO_FICHA*i;
			int fin = inicio+ANCHO_FICHA;
			if(x>=inicio && x<=fin) return i;
		}
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
			if(fichas[columna][i].getCodigo()==0){
				return true;
			}
		}
		for(int i=0 ; i<h ; i++){//hago lo mismo pero en la fila correspondiente
			if(fichas[i][fila].getCodigo()==0){
				return true;
			}
		}
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
		int filaFichaVacia=-1;
		int columnaFichaVacia=-1;
		for(int i=0; i<h ; i++){
			for(int j=0 ; j<v ; j++){
				if(fichas[i][j].getCodigo() == 0){
					filaFichaVacia=j;
					columnaFichaVacia=i;
				}
			}
		}
		Ficha fichaVacia = fichas[columnaFichaVacia][filaFichaVacia];
		if(fila == filaFichaVacia){
			if(columna > columnaFichaVacia){
				for(int i=columnaFichaVacia ; i<columna ; i++){
					fichas[i][fila]=fichas[i+1][fila];
				}
			}else if(columna < columnaFichaVacia){
				for(int i=columnaFichaVacia ; i>columna ; i--){
					fichas[i][fila]=fichas[i-1][fila];
				}
			}
		}else if(columna == columnaFichaVacia){
			if(fila > filaFichaVacia){
				for(int i=filaFichaVacia ; i<fila ; i++){
					fichas[columna][i]=fichas[columna][i+1];
				}
			}else if(fila < filaFichaVacia){
				for(int i=filaFichaVacia ; i>fila ; i--){
					fichas[columna][i]=fichas[columna][i-1];
				}
			}
		}
		fichas[columna][fila] = fichaVacia;
		//solo se considera movimiento cuando se hace clic en una casilla que no sea
		//la ficha vacía
		if(fila != filaFichaVacia || columna != columnaFichaVacia){
			c.addMovimiento();
			this.repaint();
			comprobarVictoria();
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
		int fichaEsperada=1;
		boolean victoria=true;
		for(int i=0 ; i<v ; i++){
			for(int j=0 ; j<h ; j++){
				if(fichaEsperada==v*h) fichaEsperada=0;
				if(fichas[j][i].getCodigo() != fichaEsperada){
					victoria=false;
				}
				fichaEsperada++;
			}
		}
		
		if(victoria){
			String msg = "¡¡Has ganado!!"+
						"\r\n     Tiempo:           "+String.format("%1$02dmin %2$02dseg",c.getMinutos(),c.getSegundos()%60)+
						"\r\n     Movimientos: "+c.getMovimientos();
			JOptionPane.showMessageDialog(this,msg);
			c.reset();
			this.reset();
		}
		
	}
	
	//eventos raton
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
    		int columna = getColumna(e.getX());
    		int fila = getFila(e.getY());
    		if(fichaMovible(fila,columna)){
    			moverFicha(fila,columna);
    		}
    	}catch(GameException ge){
    	}catch(Exception ex){
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
    	iniciarFichas();
    	this.repaint();
    }
    
}