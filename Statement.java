package parser;
import main.Library;
import main.Main;
import scanner.Scanner;
import scanner.TokenKind;

abstract class Statement extends PascalSyntax {	
	Block context;
	Statement next;
	types.Type type;
	Statement(int n) {
		super(n);
	}
	
	public String identify(){
		return "<statement> " + " on line " + lineNum;
	}
	
	public static Statement parse(Scanner s){
		enterParser("statment");
		Statement st = null;
		//assign statm
		if(s.curToken.kind == TokenKind.nameToken && (s.nextToken.kind == TokenKind.assignToken || s.nextToken.kind == TokenKind.leftBracketToken)){
			st = AssignStatm.parse(s);
		//proc call
		}else if(s.curToken.kind == TokenKind.nameToken ||(s.curToken.kind == TokenKind.nameToken && s.nextToken.kind == TokenKind.leftParToken)){
			st = ProcCall.parse(s);
		//if-statm
		}else if(s.curToken.kind == TokenKind.ifToken){
			st= IfStatm.parse(s);
		//while-statm
		}else if(s.curToken.kind == TokenKind.whileToken){
			st = WhileStatm.parse(s);
		//compound statm
		}else if(s.curToken.kind == TokenKind.beginToken){
			st = CompoundStatm.parse(s);
		//empty statm
		}else{
			st = EmptyStatm.parse(s);
		}
		leaveParser("statment");
		return st;
	}
	abstract public void prettyPrint();
	abstract void check(Block curScope, Library lib);
}