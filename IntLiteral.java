package parser;
import main.CodeFile;
import main.Library;
import main.Main;
import scanner.Scanner;
import scanner.TokenKind;
public class IntLiteral extends UnsignedConstant{

	IntLiteral(int n) {
		super(n);
	}
	public int i;
	public static IntLiteral parse(Scanner s){
		enterParser("number literal");
		IntLiteral in = new IntLiteral(s.curLineNum());
		in.i = s.curToken.intVal;
		s.skip(TokenKind.intValToken);
		leaveParser("number literal");
		return in;
	}
	
	public String identify() {
		return "<intliteral> " + " on line " + lineNum;
	}
	public void prettyPrint(){
		 Main.log.prettyPrint(this.i + "");
		
	}

	
	@Override
	public void check(Block curScope, Library lib)  {
		type = lib.decls.get("INTEGER").type;
	}
	void genCode(CodeFile f) {
		f.genInstr("", "movl", "$"+this.i+",%eax",""+this.i);
	}
}
