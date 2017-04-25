/**
 * Clase de excepción creada para controlar eventos anómalos propios del juego
 * @author Julián
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