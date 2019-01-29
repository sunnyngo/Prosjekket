package parser;
import main.CodeFile;
import main.Library;
import main.Main;
import scanner.Scanner;
public class EmptyStatm extends Statement{

	EmptyStatm(int n) {
		super(n);
	}
	public String identify(){
		return "<empty statm> " + " on line " + lineNum;
	}
	
	public static EmptyStatm parse(Scanner s){
		enterParser("empty statm");
		EmptyStatm es = new EmptyStatm(s.curLineNum());
		leaveParser("empty statm");
		return es;
	}
	@Override
	public void prettyPrint() {
		Main.log.prettyPrint(" ");
		
	}
	
	@Override
	void check(Block curScope, Library lib) {
		// TODO Auto-generated method stub
		
	}
	@Override
	void genCode(CodeFile f) {
	}
	
}
