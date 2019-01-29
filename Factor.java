package parser;
import main.Library;
import scanner.Scanner;
import scanner.TokenKind;
abstract class Factor extends PascalSyntax{
	Factor next;
	types.Type type;
	Block context;
	Factor(int n) {
		super(n);
	}
	public String identify(){
		return "<factor> "  + " on line " + lineNum;
	}
	public static Factor parse(Scanner s){
		enterParser("factor");
		Factor f = null;
		//func call
		if(s.curToken.kind == TokenKind.nameToken && s.nextToken.kind != TokenKind.leftBracketToken && s.nextToken.kind != TokenKind.leftParToken ){
			f = Variable.parse(s);
		}else if(s.curToken.kind == TokenKind.nameToken && s.nextToken.kind == TokenKind.leftParToken){
				f = FuncCall.parse(s);
		//variable
		}else if(s.curToken.kind == TokenKind.nameToken && s.nextToken.kind == TokenKind.leftBracketToken){
				f = Variable.parse(s);
		//Unsigned constant
		}else if(s.curToken.kind == TokenKind.nameToken || s.curToken.kind == TokenKind.intValToken||s.curToken.kind == TokenKind.charValToken){
			f = UnsignedConstant.parse(s);
		//inner expr
		}else if(s.curToken.kind == TokenKind.leftParToken){
			f = InnerExpr.parse(s);
		//negation
		}else{
			f = Negation.parse(s);
		}
		leaveParser("factor");
		return f;
	}
	abstract public void prettyPrint();
	abstract public void check(Block curScope, Library lib);
	
}
