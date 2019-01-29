package parser;
import main.CodeFile;
import main.Library;
import main.Main;
import scanner.Scanner;
import scanner.TokenKind;
public class ParamDecl extends PascalDecl{
	ParamDecl next;
	TypeName tname;
	ParamDecl(String id, int lNum) {
		super(id, lNum);
		
	}
	public String identify(){
	   	return "<paramDecl> " + name + " on line " + lineNum;
	   }
   public static ParamDecl parse(Scanner s){
	   enterParser("param decl");
	   ParamDecl pd = new ParamDecl(s.curToken.id.toUpperCase(),s.curLineNum());
	   s.skip(TokenKind.nameToken);
	   s.skip(TokenKind.colonToken);
	   pd.tname = TypeName.parse(s);
	   leaveParser("param decl");
	   return pd;
   }
   public void prettyPrint(){
		 Main.log.prettyPrint(this.name + " : ");
		 tname.prettyPrint();
	}
   public void checkWhetherAssignable(PascalSyntax where){
	   
	}

   public void checkWhetherFunction(PascalSyntax where){
	  
   }
   public void checkWhetherProcedure(PascalSyntax where){
	  
   }
   public void checkWhetherValue(PascalSyntax where){
	   
   }

   @Override
	void check(Block curScope, Library lib)  {
		tname.check(curScope, lib);
		type = tname.type;
	}
@Override
void genCode(CodeFile f) {
	// TODO Auto-generated method stub
	
}
   

}
