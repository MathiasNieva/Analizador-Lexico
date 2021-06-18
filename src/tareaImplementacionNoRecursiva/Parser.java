package tareaImplementacionNoRecursiva;

import java.util.Queue;
import java.util.Stack;

import lexer.Tag;
import lexer.Token;
import lexer.Word;

public class Parser {
	public final String[][] table = new String[5][6];
	public Queue<Token> list = null;
	public Stack<String> stack = null;
	public Token current = null;
	public String x;
	
	public Parser(Queue<Token> l) {
		list = l;
		stack = new Stack<String>();
		current = list.remove();
		
		table[0][0] = "T,e";table[0][1] = ""; 	   table[0][2] = "";      table[0][3] = "T,e";    table[0][4] = ""; table[0][5] = "";
		table[1][0] = "";   table[1][1] = "43,T,e";table[1][2] = ""; 	  table[1][3] = "";       table[1][4] = "l";table[1][5] = "l";
		table[2][0] = "F,t";table[2][1] = ""; 	   table[2][2] = ""; 	  table[2][3] = "F,t";    table[2][4] = ""; table[2][5] = "";
		table[3][0] = "";	table[3][1] = "l";     table[3][2] = "42,F,t";table[3][3] = ""; 	 table[3][4] = "l";table[3][5] = "l";
		table[4][0] = "257";table[4][1] = ""; 	   table[4][2] = "";      table[4][3] = "40,E,41";table[4][4] = ""; table[4][5] = "";
		
		stack.add("$");
		stack.add("E");
		x = stack.lastElement();
	}
	
	public boolean analyse() {
		while (x != "$") {
			if (x.equals(Integer.toString(current.tag))) {
				stack.pop();
				current = list.remove();
			}
			else if (x.startsWith("2") || x.startsWith("4")) {
				return false;
			}
			else if ( table[rows(x)][columns(current)].equals("") ) {
				return false;
			}
			else if (table[rows(x)][columns(current)].length() > 0) {
				System.out.println(table[rows(x)][columns(current)]);
				stack.pop();
				String[] array = table[rows(x)][columns(current)].split(",");
				for (int i = array.length - 1; i >= 0; i--) {
					if (!array[i].equals("l")) {
						stack.add(array[i]);
					}
				}
			}
			x = stack.lastElement();
		}
		return true;
	}
	
	public int rows(String s) {
		if (s.equals("E")) return 0;
		else if (s.equals("e")) return 1;
		else if (s.equals("T")) return 2;
		else if (s.equals("t")) return 3;
		else if (s.equals("F")) return 4;
		return -1;
	}
	
	// 257=id, 40=(, 41=) 42=*, 43=+,
	public int columns(Token t) {
		if (t.tag == 257) return 0;
		else if (t.tag == 43) return 1;
		else if (t.tag == 42) return 2;
		else if (t.tag == 40) return 3;
		else if (t.tag == 41) return 4;
		else if (t.tag == 36) return 5;
		return -1;
	}
}
