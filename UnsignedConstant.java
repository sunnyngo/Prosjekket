package parser;
import main.CodeFile;
import main.Library;
import main.Main;
import scanner.*;
public class UnsignedConstant extends Factor {
	public String name;
	public IntLiteral numliteral;
	public CharLiteral charl;
	public ConstDecl nameC; 
	
	public UnsignedConstant(int n){
		super(n);
	}
	public String identify(){
		return "<Unsigned Constant> " + name + " on line " + lineNum;
	}
	
	public static UnsignedConstant parse(Scanner s){
		enterParser("unsigned constant");
		UnsignedConstant usigC = new UnsignedConstant(s.curLineNum());
		
		 if(s.curToken.kind == TokenKind.intValToken){
			usigC.numliteral =IntLiteral.parse(s);
		}else if(s.curToken.kind == TokenKind.charValToken){
			usigC.charl = CharLiteral.parse(s);
		}else{
			usigC.name = s.curToken.id;
			s.skip(TokenKind.nameToken);
		}
		leaveParser("unsigned constant");
		return usigC;
	}
	public void prettyPrint(){
		if(this.numliteral != null){
			numliteral.prettyPrint();
		}else if(this.charl != null){
			charl.prettyPrint();
		}else{
		 Main.log.prettyPrint(this.name);
		}
	}
	@Override
	public void check(Block curScope, Library lib) {
		if(name != null){
			nameC = (ConstDecl)curScope.findDecl(name.toUpperCase(), this);
			type = nameC.type;
		}else if(numliteral != null){
			numliteral.check(curScope, lib);
			type = numliteral.type;
		}else if(charl != null){
			charl.check(curScope, lib);
			type = charl.type;
		}else{
			Main.error("this is not Unsigned Constant");
		}
		
	}
	@Override
	void genCode(CodeFile f) {
		if(numliteral != null){
			numliteral.genCode(f);
			
		}else if(charl != null){
			charl.genCode(f);
		}else{
			f.genInstr("", "movl", "$"+nameC.c.unsigC.numliteral.i+",%eax", "");
		}
		
	}
	
	
}
