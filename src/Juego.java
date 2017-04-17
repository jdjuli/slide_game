

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

	public Juego(int h, int v) throws RuntimeException{
		if(h<2 || v<2) throw new RuntimeException("El tablero tiene que ser al menos de 2*2");
		this.h = h;
		this.v = v;
		iniciarFichas();
		iniciarTablero();
		this.addMouseListener(this);
	}
	

	private void iniciarTablero(){
		int ancho=SEPARACION_FICHA*(h+1)+ANCHO_FICHA*h;
		int alto=SEPARACION_FICHA*(v+1)+ALTO_FICHA*v;
		this.setPreferredSize( new Dimension(ancho,alto) );
	}

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
	
	protected void paintComponent(Graphics g) {
		for(int i=0 ; i<fichas.length ; i++){//h
			for(int j=0 ; j<fichas[0].length ; j++){//v
				int y = SEPARACION_FICHA*(j+1)+ANCHO_FICHA*j;
				int x = SEPARACION_FICHA*(i+1)+ALTO_FICHA*i;
				fichas[i][j].paint(g,x,y);
			}
		}
	}
	
	private int getFila(int y) throws GameException{
		//v es el numero de filas de que se compone el tablero
		for(int i=0 ; i<v ; i++){
			int inicio = SEPARACION_FICHA*(i+1)+ALTO_FICHA*i;
			int fin = inicio+ALTO_FICHA;
			if(y>=inicio && y<=fin) return i;
		}
		throw new GameException("No has seleccionado ninguna fila");
	}
	
	private int getColumna(int x) throws GameException{
		//v es el numero de filas de que se compone el tablero
		for(int i=0 ; i<h ; i++){
			int inicio = SEPARACION_FICHA*(i+1)+ANCHO_FICHA*i;
			int fin = inicio+ANCHO_FICHA;
			if(x>=inicio && x<=fin) return i;
		}
		throw new GameException("No has seleccionado ninguna columna");
	}
	
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
    
    public void setContador(Contador c){
    	this.c = c;
    }
    
    public void reset(){
    	iniciarFichas();
    	this.repaint();
    }
    
}