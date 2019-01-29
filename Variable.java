package parser;
import main.CodeFile;
import main.Library;
import main.Main;
import scanner.Scanner;
import scanner.TokenKind;
public class Variable extends Factor {
	String name;
	Expression ex;
	PascalDecl pd;
	
	Variable(int n) {
		super(n);
	}
	public String identify(){
		return "<variable> " + name + " on line " + lineNum;
	}
	public static Variable parse(Scanner s){
		enterParser("variable");
		Variable v = new Variable(s.curLineNum());
		v.name = s.curToken.id.toUpperCase();
		s.skip(TokenKind.nameToken);
		if(s.curToken.kind == TokenKind.leftBracketToken){
			s.skip(TokenKind.leftBracketToken);
			v.ex = Expression.parse(s);
			s.skip(TokenKind.rightBracketToken);
		}
		leaveParser("variable");
		return v;
	}
	
	public void prettyPrint() {
		Main.log.prettyPrint(this.name +"");
		if(ex != null){
			Main.log.prettyPrint(" [");
			ex.prettyPrint();
			Main.log.prettyPrint("] ");
		}
	}
	@Override
	public void check(Block curScope, Library lib){
		//System.out.println("name:  "+this.name);
		//for(String s: curScope.decls.keySet()) System.out.println(s);
		pd =curScope.findDecl(this.name.toUpperCase(), this);
		type = pd.type;
		if(ex != null)ex.check(curScope, lib);
	}
	@Override
	void genCode(CodeFile f) {
		if(pd instanceof VarDecl){
			this.context = ((VarDecl)pd).context;  
		}
		if(ex != null){
			ex.context = this.context;
		}
		int tmp = (-4)*context.declLevel;
		f.genInstr("", "movl", ""+tmp+"(%ebp),%edx","");
		f.genInstr("", "movl", (pd.declOffset)+"(%edx),%eax", " "+name);
		}
	
}
