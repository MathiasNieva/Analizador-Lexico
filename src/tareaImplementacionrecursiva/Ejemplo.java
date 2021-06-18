package tareaImplementacionrecursiva;

import java.util.LinkedList;
import java.util.Queue;

import lexer.Token;

public class Ejemplo {
	public static void mostrarResultado() {
		Queue<Token> tokens = new LinkedList<>();
		tokens.add(new Token(42));
		tokens.add(new Token(47));
		tokens.add(new Token(257));
		tokens.add(new Token(42));
		tokens.add(new Token(256));
		// tags 256=num, 257=id, 42=*, 43=+, 45=-, 47=/ 
		
		ArithmeticExpression ae = new ArithmeticExpression(tokens);
		System.out.println(ae.analyse());
	}
}
