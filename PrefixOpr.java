package parser;

import main.CodeFile;
import main.Library;
import main.Main;
import scanner.Scanner;
import scanner.TokenKind;

public class PrefixOpr extends PascalSyntax {
	TokenKind tokenkind ;
	PrefixOpr pOpr;
	PrefixOpr(int n){
		super(n);
	}
	
	public String identify(){
		return null;
	}
	public static PrefixOpr parse(Scanner s){
		enterParser("prefix opr");
		PrefixOpr po = new PrefixOpr(s.curLineNum());
		po.tokenkind = s.curToken.kind;
		if(s.curToken.kind == TokenKind.addToken){
			s.skip(TokenKind.addToken);
		}else{
			s.skip(TokenKind.subtractToken);
		}
		leaveParser("prefix opr");
		return po;
	}
	
	public void prettyPrint(){
		 Main.log.prettyPrint(this.tokenkind + "");
	}

	@Override
	void check(Block curScope, Library lib) {
		// TODO Auto-generated method stub
		
	}

	@Override
	void genCode(CodeFile f) {
		if (tokenkind == TokenKind.subtractToken){
			f.genInstr("", "negl", "%eax", "  -(prefix)");
		}
	}
	
}
