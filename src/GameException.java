/**
 * Clase de excepci�n creada para controlar eventos an�malos propios del juego
 * @author Juli�n
 *
 */
public class GameException extends RuntimeException {
	
	GameException(){
		super();
	}
	
	GameException(String msg){
		super(msg);
	}
	
}