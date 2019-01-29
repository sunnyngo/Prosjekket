package parser;
import main.CodeFile;
import main.Library;
import main.Main;
import scanner.Scanner;
import scanner.TokenKind;

public class Type extends PascalSyntax{
	TypeName tname;
	ArrayType arrayT;
	types.Type type;
	Type(int n) {
		super(n);
	}
	public static Type parse(Scanner s){
		enterParser("type");
		Type t = new Type(s.curLineNum());
		
		if(s.curToken.kind == TokenKind.nameToken){
			t.tname = TypeName.parse(s);
		}else{
			t.arrayT = ArrayType.parse(s);
		}
		leaveParser("type");
		return t;
	}
	public String identify(){
		return "<type> " + tname.name + " on line " + lineNum;
	}
	 public void prettyPrint(){
		 if (tname != null){
			 tname.prettyPrint();
		 }else if(arrayT != null){
			 arrayT.prettyPrint();
		 }
		
	}
	 //det sjekker type name eller type array.
	 @Override
	void check(Block curScope, Library lib){
		if(tname != null){
			tname.check(curScope, lib);
			type = tname.type;
		}else{
			arrayT.check(curScope, lib);
			type = arrayT.type;
		}
	}
	@Override
	void genCode(CodeFile f) {
		// TODO Auto-generated method stub
		
	}
}
