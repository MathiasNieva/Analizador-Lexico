package tareaImplementacionrecursiva;

import java.util.Queue;
import lexer.Token;

public class ArithmeticExpression {
	public Queue<Token> list = null;
	public Token current = null;
	
	public ArithmeticExpression(Queue<Token> l) {
		list = l;
		current = list.remove();
	}
	
	public boolean analyse() {
		boolean result =  E();
		if (list.size()>0) return false;
		return result;
	}
	
	public boolean E() {
		return (T() && E1());
	}
	
	public boolean E1() {
		if (current.tag == 43) {
			if (!list.isEmpty()) current = list.remove();
			return T() && E1();
		}
		else if (current.tag == 45) {
			if (!list.isEmpty()) current = list.remove();
			return T() && E1();
		}
		else return true;
	}
	
	public boolean T() {
		return (F() && T1());
	}
	
	public boolean F() {
		if (current.tag == 256 || current.tag == 257) {
			if (!list.isEmpty()) current = list.remove();
			return true;
		}
		return false;
	}
	
	public boolean T1() {
		if (current.tag == 42) {
			if (!list.isEmpty()) current = list.remove();
			return F() && T1();
		}
		else if (current.tag == 47) {
			if (!list.isEmpty()) current = list.remove();
			return F() && T1();
		}
		else return true;
	}
}
