package Parser;

import java.io.IOException;

import intercode.*;
import lexer.*;
import symbols.*;

public class Parser {
	private Lexer lex; // lexical analyzer for this parser
	private Token look; // lookahead token
	Tables top = null; // current or top symbol table
	int used = 0; // storage used for declarations
	
	public Parser(Lexer l) throws IOException {
		lex = l;
		move();
	}
	
	void move() throws IOException {
		look = lex.scan();
	}
	
	void error(String s) {
		throw new Error("near line "+lex.line+": "+s);
	}
	
	void match(int t) throws IOException {
		if( look.tag == t ) move();
		else error("syntax error");
	}
	
	public void program() throws IOException { // program -> block
		Stmt s = block();
		int begin = s.newlabel();
		int after = s.newlabel();
		s.emitlabel(begin);
		s.gen(begin, after);
		s.emitlabel(after);
	}
	
	Stmt block() throws IOException { // block -> { decls stmts }
		match('{'); Tables savedEnv = top; top = new Tables(top);
		decls(); Stmt s = stmts();
		match('}'); top = savedEnv;
		return s;
	}
	
	void decls() throws IOException {
		while( look.tag == Tag.BASIC ) { // D -> type ID ;
			Type p = type();
			Token tok = look;
			match(Tag.ID); match(';');
			Id id = new Id((Word)tok, p, used);
			top.put( tok, id );
			used = used + p.width;
		}
	}
	
	Type type() throws IOException {
		Type p = (Type)look; // expect look.tag == Tag.BASIC
		match(Tag.BASIC);
		return p; // T -> basic
	}
	
	Stmt stmts() throws IOException {
		if ( look.tag == '}' ) return Stmt.Null;
		else return new Seq(stmt(), stmts());
	}
	
	Stmt stmt() throws IOException {
		Expr x;
		Stmt s, s1, s2;
		Stmt savedStmt; // save enclosing loop for breaks
		
		switch( look.tag ) {
		case ';':
			move();
			return Stmt.Null;
			
		case Tag.RHYTHM:
			match(Tag.RHYTHM); match(Tag.OF);
			if (look.tag == Tag.ANTICIPATION) {
				match(Tag.ANTICIPATION); match('('); x = bool(); match(')');
				s1 = stmt();
				if( look.tag != Tag.ELSE ) return new If(x, s1);
				match(Tag.ELSE);
				s2 = stmt();
				return new Else(x, s1, s2);
			}
			else if (look.tag == Tag.CONSIDERATION) {
				While whilenode = new While();
				savedStmt = Stmt.Enclosing; Stmt.Enclosing = whilenode;
				match(Tag.CONSIDERATION); match('('); x = bool(); match(')');
				s1 = stmt();
				whilenode.init(x, s1);
				Stmt.Enclosing = savedStmt; // reset Stmt.Enclosing
				return whilenode;
			}
			
		case Tag.BREAK:
			match(Tag.BREAK); match(';');
			return new Break();
			
		case '{':
			return block();
			
		default:
			return assign();
		
		}	
	}
	
	Stmt assign() throws IOException {
		Stmt stmt = null; Token t = look;
		match(Tag.ID);
		Id id = top.get(t);
		if( id == null ) error(t.toString() + " undeclared");
		if( look.tag == '=' ) { // S -> id = E ;
			move(); stmt = new Set(id, bool());
		}
		match(';');
		return stmt;
	}
	
	Expr bool() throws IOException {
		Expr x = join();
		while( look.tag == Tag.OR ) {
			Token tok = look; 
			move(); x = new Or(tok, x, join());
			}
		return x;
	}
	
	Expr join() throws IOException {
		Expr x = equality();
		while( look.tag == Tag.AND ) {
			Token tok = look; move(); x = new And(tok, x, equality());
		}
		return x;
	
	}
	
	Expr equality() throws IOException {
		Expr x = rel();
		while( look.tag == Tag.EQUAL || look.tag == Tag.DIFF ) {
			Token tok = look; move(); x = new Rel(tok, x, rel());
		}
		return x;
	}
	
	Expr rel() throws IOException {
		Expr x = expr();
		switch( look.tag ) {
		case '<': case Tag.LESSER_EQ: case Tag.GREATER_EQ: case '>':
		Token tok = look; move(); return new Rel(tok, x, expr());
		default:
		return x;
		}
	}
	
	Expr expr() throws IOException {
		Expr x = term();
		while( look.tag == '+' || look.tag == '-' ) {
			Token tok = look; move(); x = new Arith(tok, x, term());
		}
		return x;
	}
	
	Expr term() throws IOException {
		Expr x = unary();
		while(look.tag == '*' || look.tag == '/' ) {
			Token tok = look; move(); x = new Arith(tok, x, unary());
		}
		return x;
	}
	
	Expr unary() throws IOException {
		if( look.tag == '!' ) {
			Token tok = look; move(); return new Not(tok, unary());
		}
		else return factor();
	}
	
	Expr factor() throws IOException {
		Expr x = null;
		switch( look.tag ) {
		case '(':
			move(); x = bool(); match(')');
			return x;
		case Tag.NUM:
			x = new Constant(look, Type.Number); move(); return x;
		case Tag.TRUE:
			x = Constant.True; move(); return x;
		case Tag.FALSE:
			x = Constant.False; move(); return x;
		default:
			error("syntax error");
			return x;
		case Tag.ID:
			String s = look.toString();
			Id id = top.get(look);
			if( id == null ) error(look.toString() + " undeclared");
			move();
			if( look.tag != '[' ) return id;
			else return null;
		}
	}
	
	
}
