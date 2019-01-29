package parser;
import main.CodeFile;
import main.Library;
import main.Main;
import scanner.Scanner;
import scanner.TokenKind;
import types.IntType;
public class TypeName extends PascalDecl{
	String name;
	types.Type type;
	TypeName(String name,int n) {
		super(name, n);
	}
	public String identify(){
		return "<Type name> " + name + " on line " + lineNum;
	}
	
	public static TypeName parse(Scanner s){
		enterParser("type name");
		TypeName tn = new TypeName(s.curToken.id.toUpperCase(),s.curLineNum());
		tn.name = s.curToken.id;
		s.skip(TokenKind.nameToken);
		leaveParser("type name");
		return tn;
	}
	 public void prettyPrint(){
		 Main.log.prettyPrint(this.name);
		 
	}
	 //det sjekker om type og det henter fra library.
	 @Override
		void check(Block curScope, Library lib) {
		PascalDecl d = curScope.findDecl(this.name.toUpperCase(), this);
		if(this.name.toUpperCase().equals("INTEGER")){
			this.type = lib.decls.get("INTEGER").type;
		}else if (this.name.toUpperCase().equals("BOOLEAN")){
			this.type = lib.decls.get("BOOLEAN").type;
		}else if (this.name.toUpperCase().equals("CHAR")){
			this.type = lib.decls.get("CHAR").type;
		}else if (this.name.toUpperCase().equals("TRUE")){
			this.type = lib.decls.get("TRUE").type;
		}else if (this.name.toUpperCase().equals("FALSE")){
			this.type = lib.decls.get("FALSE").type;
		}else if (this.name.toUpperCase().equals("EOL")){
			this.type = lib.decls.get("EOL").type;
		}
	}
	@Override
	void checkWhetherAssignable(PascalSyntax where) {
		// TODO Auto-generated method stub
		
	}
	@Override
	void checkWhetherFunction(PascalSyntax where) {
		// TODO Auto-generated method stub
		
	}
	@Override
	void checkWhetherProcedure(PascalSyntax where) {
		// TODO Auto-generated method stub
		
	}
	@Override
	void checkWhetherValue(PascalSyntax where) {
		// TODO Auto-generated method stub
		
	}
	@Override
	void genCode(CodeFile f) {
		// TODO Auto-generated method stub
		
	}
}
