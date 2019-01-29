package parser;

import scanner.TokenKind;
import main.CodeFile;
import main.Library;
import main.Main;
import scanner.Scanner;
public class RelOpr extends Statement {
	TokenKind tokenkind;
	RelOpr(int n) {
		super(n);
		
	}
	public String identify(){
		return null;
	}
	
	public static RelOpr parse(Scanner s){
		enterParser("rel opr");
		RelOpr ro = new RelOpr(s.curLineNum());
		ro.tokenkind = s.curToken.kind;
		s.skip(s.curToken.kind);
		leaveParser("rel opr");
		return ro;
	}
	public void prettyPrint(){
		Main.log.prettyPrint(""+this.tokenkind +"");
	}
	
	@Override
	void check(Block curScope, Library lib) {
		
	}
	@Override
	void genCode(CodeFile f) {
		// TODO Auto-generated method stub
		
	}
	
}
