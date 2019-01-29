package parser;
import main.CodeFile;
import main.Library;
import main.Main;
import scanner.Scanner;
import scanner.TokenKind;
public class AssignStatm extends Statement{
	Expression ex;
	Variable v;
	AssignStatm(int n) {
		super(n);
	}
	public String identify(){
		return "<Assign statm> "+v.name + " on line " + lineNum;
	}

	public static AssignStatm parse(Scanner s){
		enterParser("assign statm");
		AssignStatm as = new AssignStatm(s.curLineNum());
		as.v = Variable.parse(s);
		s.skip(TokenKind.assignToken);
		as.ex = Expression.parse(s);
		leaveParser("assign statm");
		return as;
	}
	
	public void prettyPrint() {
		v.prettyPrint();
	    Main.log.prettyPrint(" := ");
	    ex.prettyPrint();
		
	}
	//det sjekker variable --> expression.
	@Override
	void check(Block curScope, Library lib) {
		v.check(curScope, lib);
		v.pd.checkWhetherAssignable(this);
		ex.check(curScope, lib);
		//v.type.checkType(ex.type,":=",this, "Different types in assignment!");
	}
	@Override
	void genCode(CodeFile f) {
		ex.context = this.context;
		ex.genCode(f); 
		int antall = -4*this.context.declLevel;
		int antall_offset = v.pd.declOffset;
		if(v.pd instanceof VarDecl){
			f.genInstr("", "movl",""+antall+"(%ebp),%edx","");
			f.genInstr("", "movl", "%eax,"+antall_offset+"(%edx)", v.pd.name+":=");
		}else if(v.pd instanceof FuncDecl){
			f.genInstr("", "movl", ""+antall+"(%ebp),%edx","");
			f.genInstr("", "movl", "%eax,-32(%edx)",v.pd.name +":=");
		}else{
			// det er array
		}
	}
}
