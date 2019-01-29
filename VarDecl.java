package parser;

import main.CodeFile;
import main.Library;
import main.Main;
import scanner.Scanner;
import scanner.TokenKind;

public class VarDecl extends PascalDecl {
	Type t;
	VarDecl next;
	Block context;
	VarDecl(String id, int lNum) {
		super(id, lNum);
	}
	public static VarDecl parse(Scanner s){
		enterParser("var decl");
		VarDecl vd =  new VarDecl(s.curToken.id.toUpperCase(),s.curLineNum());
		s.skip(TokenKind.nameToken);
		s.skip(TokenKind.colonToken);
		vd.t = Type.parse(s);
		s.skip(TokenKind.semicolonToken);
		leaveParser("var decl");
		return vd;
	}
	public void checkWhetherAssignable(PascalSyntax where){
		
	}

    public void checkWhetherFunction(PascalSyntax where){
    	
    }
    public void checkWhetherProcedure(PascalSyntax where){
    	
    }
    public void checkWhetherValue(PascalSyntax where){
    	
    }
	
    public String identify(){
    	return "<varDecl> " + name + " on line " + lineNum;
    }
    public void prettyPrint(){
		 Main.log.prettyPrint(this.name + " : ");
		 t.prettyPrint();
		 Main.log.prettyPrint("; \n");
	}
    @Override
	void check(Block curScope, Library lib){
		t.check(curScope, lib);
		type = t.type;
	}
	@Override
	void genCode(CodeFile f) {
		// TODO Auto-generated method stub
		
	}
}
