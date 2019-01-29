package parser;
import main.CodeFile;
import main.Library;
import main.Main;
import scanner.Scanner;
import scanner.TokenKind;
public class ConstDecl extends PascalDecl {
	public Constant c;
	ConstDecl next;
	Block context;
	public ConstDecl(String id, int lNum) {
		super(id, lNum);
	}
	public String identify(){
		return "<constDecl> " + name + " on line " + lineNum;
	}
	public static ConstDecl parse(Scanner s){
		enterParser("const decl");
		ConstDecl cd = new ConstDecl(s.curToken.id.toUpperCase(),s.curLineNum());
		s.skip(TokenKind.nameToken);
		s.skip(TokenKind.equalToken);
		cd.c = Constant.parse(s);
		s.skip(TokenKind.semicolonToken);
		leaveParser("const decl");
		return cd;
	}
	public void checkWhetherAssignable(PascalSyntax where){
		where.error("You cannot assign to a constant.");
	}

    public void checkWhetherFunction(PascalSyntax where){
    	
    }
    public void checkWhetherProcedure(PascalSyntax where){
    	
    }
    public void checkWhetherValue(PascalSyntax where){
    	
    }
    public void prettyPrint(){
		 Main.log.prettyPrint(this.name + " = ");
		 c.prettyPrint();
		 Main.log.prettyPrint(";");
	}
	//det sjekker constant type.
    @Override
	void check(Block curScope, Library lib) {
		c.check(curScope, lib);
		type = c.type;
		
	}
	@Override
	void genCode(CodeFile f) {
		// TODO Auto-generated method stub
		
	}
	
}
