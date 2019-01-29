package parser;
import main.CodeFile;
import main.Library;
import main.Main;
import scanner.Scanner;
import scanner.TokenKind;

public class ArrayType extends PascalSyntax{
	Type t;
	Constant cFrom;
	Constant cto;
	types.Type type;
	ArrayType(int n) {
		super(n);
	}
	public String identify(){
		return "<Array Type> "  + " on line " + lineNum;
	}
	
	public static ArrayType parse(Scanner s){
		enterParser("array type");
		ArrayType art = new ArrayType(s.curLineNum());
		s.skip(TokenKind.arrayToken);
		s.skip(TokenKind.leftBracketToken);
		art.cFrom = Constant.parse(s);
		s.skip(TokenKind.rangeToken);
		art.cto = Constant.parse(s);
		s.skip(TokenKind.rightBracketToken);
		s.skip(TokenKind.ofToken);
		art.t = Type.parse(s);
		leaveParser("array type");
		return art;
	}
	public void prettyPrint(){
		 Main.log.prettyPrint("array" + "[");
		 cFrom.prettyPrint();
	     Main.log.prettyPrint(" .. ");
	     cto.prettyPrint();
	     Main.log.prettyPrint("] of ");
	     t.prettyPrint();

	}
	//det sjekker om 2 constant og type.
	@Override
	void check(Block curScope, Library lib) {
		cFrom.check(curScope, lib);
		cto.check(curScope, lib);
		t.check(curScope, lib);
		//System.out.println("type : "+t.type);
		types.Type e = t.type;
		
		boolean cFromIsInt = cFrom.type instanceof types.IntType;
		boolean ctoIsInt =  cto.type instanceof types.IntType;
		if( !cFromIsInt && !ctoIsInt){//det sjekker om det er typen Integer
			Main.error("Constant maa vare en Integer");
		}
		
		types.Type i = cFrom.type;
		int lo = 0, hi = 0;
		if(cFrom.unsigC.numliteral != null){
			lo = cFrom.unsigC.numliteral.i;
		}
		if(cto.unsigC.numliteral != null){
			hi = cto.unsigC.numliteral.i;
		}
		this.type = t.type;
		//type =new types.ArrayType(e,i,lo,hi);
	}
	@Override
	void genCode(CodeFile f) {
		// TODO Auto-generated method stub
		
	}
	
	
	
}
