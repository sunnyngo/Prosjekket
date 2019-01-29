package parser;
import main.CodeFile;
import main.Library;
import main.Main;
import scanner.Scanner;
import scanner.TokenKind;
public class CharLiteral extends UnsignedConstant {
	public CharLiteral(int n) {
		super(n);
	}
	public char ch;
	public static CharLiteral parse(Scanner s){
		enterParser("char literal");
		CharLiteral c = new CharLiteral(s.curLineNum());
		c.ch = s.curToken.charVal;
		s.skip(TokenKind.charValToken);
		leaveParser("char literal");
		return c;
	}
	public String identify() {
		return "<charliteral> " + " on line " + lineNum;
	}
	public void prettyPrint(){
		Main.log.prettyPrint("'");
		Main.log.prettyPrint(this.ch + "");
		Main.log.prettyPrint("'");
	}
	//det sjekker type på library.
	@Override
	public void check(Block curScope, Library lib) {
		type = lib.decls.get("CHAR").type;
	}
	@Override
	void genCode(CodeFile f) {
		int ascii_key = (int) this.ch;
			
		if(ascii_key == 0 || ascii_key == 1 || ascii_key == 10){// new code 
			f.genInstr("", "movl", "$"+ascii_key+",%eax"," "+ascii_key);
		}else{
			f.genInstr("", "movl", "$"+ascii_key+",%eax","  '"+this.ch+"'");
		}
	}
}
