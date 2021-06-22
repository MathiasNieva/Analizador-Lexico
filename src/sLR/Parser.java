package sLR;

import java.util.Queue;
import java.util.Stack;

import lexer.Token;

public class Parser {
	public final String[] G = new String[6];
	public final String[][] action = new String[12][6];
	public final String[][] GOTO = new String[12][6];
	public Queue<Token> list = null;
	public Stack<Integer> stack = null;
	public Token current = null;
	public int s;
	
	public Parser(Queue<Token> l) {
		list = l;
		stack = new Stack<Integer>();
		current = list.remove();
		
		G[0] = "E+T";
		G[1] = "T";
		G[2] = "T*F";
		G[3] = "F";
		G[4] = "(E)";
		G[5] = "i";
		
		action[0][0] = "s,5";action[0][1] = "";    action[0][2] = "";    action[0][3] = "s,4";action[0][4] = "";    action[0][5] = "";
		action[1][0] = "";   action[1][1] = "s,6"; action[1][2] = ""; 	 action[1][3] = "";   action[1][4] = "";    action[1][5] = "acc";
		action[2][0] = "";   action[2][1] = "r,2"; action[2][2] = "s,7"; action[2][3] = "";   action[2][4] = "r,2"; action[2][5] = "r,2";
		action[3][0] = "";	 action[3][1] = "r,4"; action[3][2] = "r,4"; action[3][3] = "";   action[3][4] = "r,4"; action[3][5] = "r,4";
		action[4][0] = "s,5";action[4][1] = "";    action[4][2] = "";    action[4][3] = "s,4";action[4][4] = "";    action[4][5] = "";
		action[5][0] = "";   action[5][1] = "r,6"; action[5][2] = "r,6"; action[5][3] = "";   action[5][4] = "r,6"; action[5][5] = "r,6";
		action[6][0] = "s,5";action[6][1] = "";    action[6][2] = "";    action[6][3] = "s,4";action[6][4] = "";    action[6][5] = "";
		action[7][0] = "s,5";action[7][1] = "";    action[7][2] = "";    action[7][3] = "s,4";action[7][4] = "";    action[7][5] = "";
		action[8][0] = "";   action[8][1] = "s,6"; action[8][2] = "";    action[8][3] = "";   action[8][4] = "s,11";action[8][5] = "";
		action[9][0] = "";   action[9][1] = "r,1"; action[9][2] = "s,7"; action[9][3] = "";   action[9][4] = "r,1"; action[9][5] = "r,1";
		action[10][0] = "";  action[10][1] = "r,3";action[10][2] = "r,3";action[10][3] = "";  action[10][4] = "r,3";action[10][5] = "r,3";
		action[11][0] = "";  action[11][1] = "r,5";action[11][2] = "r,5";action[11][3] = "";  action[11][4] = "r,5";action[11][5] = "r,5";
		
		GOTO[0][0] = "1";GOTO[0][1] = "2";GOTO[0][2] = "3";   
		GOTO[1][0] = ""; GOTO[1][1] = ""; GOTO[1][2] = ""; 	 
		GOTO[2][0] = ""; GOTO[2][1] = ""; GOTO[2][2] = ""; 
		GOTO[3][0] = ""; GOTO[3][1] = ""; GOTO[3][2] = "";
		GOTO[4][0] = "8";GOTO[4][1] = "2";GOTO[4][2] = "3";    
		GOTO[5][0] = ""; GOTO[5][1] = ""; GOTO[5][2] = ""; 
		GOTO[6][0] = ""; GOTO[6][1] = "9";GOTO[6][2] = "3";   
		GOTO[7][0] = ""; GOTO[7][1] = ""; GOTO[7][2] = "10";    
		GOTO[8][0] = ""; GOTO[8][1] = ""; GOTO[8][2] = "";    
		GOTO[9][0] = ""; GOTO[9][1] = ""; GOTO[9][2] = ""; 
		GOTO[10][0] = "";GOTO[10][1] = "";GOTO[10][2] = "";
		GOTO[11][0] = "";GOTO[11][1] = "";GOTO[11][2] = "";
		
		stack.add(0);
	}
	
	public boolean analyse() {
		while (true) {
			s = stack.lastElement();
			if ( actionTaken(s).equals("s") ) {
				stack.add(state(s));
				if (!list.isEmpty()) current = list.remove();
			}
			else if ( actionTaken(s).equals("r") ) {
				for (int i = 0; i < G[state(s)-1].length(); i++) {
					stack.pop();
				}
				stack.add(Integer.parseInt(GOTO[stack.lastElement()][symbols(state(s))]));
				System.out.println(G[state(s)-1]);
			}
			else if ( actionTaken(s).equals("acc") ) return true;
			else return false;
		}
	}
	
	public String actionTaken(int s) {
		String[] array = action[s][columns()].split(",");
		return array[0];
	}
	
	public int state(int s) {
		String[] array = action[s][columns()].split(",");
		return Integer.parseInt(array[1]);
	}
	
	public int symbols(int s) {
		if (s == 1) return 0;
		else if (s == 2) return 0;
		else if (s == 3) return 1;
		else if (s == 4) return 1;
		else if (s == 5) return 2;
		else if (s == 6) return 2;
		return -1;
	}

	// 257=id, 40=(, 41=) 42=*, 43=+,
	public int columns() {
		if (current.tag == 257) return 0;
		else if (current.tag == 43) return 1;
		else if (current.tag == 42) return 2;
		else if (current.tag == 40) return 3;
		else if (current.tag == 41) return 4;
		else if (current.tag == 36) return 5;
		return -1;
	}
}
