package parser;

import scanner.TokenKind;
import main.CodeFile;
import main.Library;
import main.Main;
import scanner.Scanner;
public class TermOpr extends Statement{
	TokenKind tokenKind;
	TermOpr next;
	TermOpr(int n) {
		super(n);
		
	}
	public String identify(){
		return null;
	}
	
	public static TermOpr parse(Scanner s){
		enterParser("term opr");
		TermOpr to = new TermOpr(s.curLineNum());
		to.tokenKind = s.curToken.kind;
		s.skip(s.curToken.kind);
		leaveParser("term opr");
		return to;
	}
	public void prettyPrint(){
		 Main.log.prettyPrint(this.tokenKind+"");
	}
	@Override
	void check(Block curScope, Library lib) {
		// TODO Auto-generated method stub
		
	}
	@Override
	void genCode(CodeFile f) {
		// TODO Auto-generated method stub
		
	}
	
}
