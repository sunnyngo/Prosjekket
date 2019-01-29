package parser;
import java.util.Stack;
import main.CodeFile;
import main.Library;
import main.Main;
import scanner.Scanner;
import scanner.TokenKind;
public class FuncCall extends Factor {
	String name;
	Expression list;
	PascalDecl pad;
	FuncDecl funcD;
	
	FuncCall(int n) {
		super(n);
	}
	public String identify(){
		return "<FuncCall> " + name + " on line " + lineNum;
	}
	public static FuncCall parse(Scanner s){
		enterParser("func call");
		FuncCall fc = new FuncCall(s.curLineNum());
		fc.name = s.curToken.id;//beholder navn før vi skip det.
		s.skip(TokenKind.nameToken);
		if(s.curToken.kind == TokenKind.leftParToken){//det er "("
			s.skip(TokenKind.leftParToken);
			do{
				Expression ny = Expression.parse(s);//expression lenkeliste.
				if(fc.list == null){
					fc.list = ny;
				}else{
					Expression tmp = fc.list;
					while(tmp.next != null){
						tmp = tmp.next;
					}
					tmp.next = ny;
				}
				if(s.curToken.kind != TokenKind.commaToken)break;
				s.skip(TokenKind.commaToken);
				
			}while(true);
			s.skip(TokenKind.rightParToken);
		}
		leaveParser("func call");
		return fc;
	}
	
	public void prettyPrint() {
		 Main.log.prettyPrint(this.name+ "");
		 Expression tmp = list;
		 while(tmp != null){
			 Main.log.prettyPrint("(");
			 tmp.prettyPrint();
			 tmp = tmp.next;
			 if(tmp != null){
				 Main.log.prettyPrint(",");
			 }
			 Main.log.prettyPrint(")");
		 }
	}
	
	@Override
	public void check(Block curScope, Library lib){
		
		Expression tmp = list;
		int cnt = countList(tmp);
		pad = curScope.findDecl(this.name.toUpperCase(), this);
		pad.checkWhetherFunction(this);
		if(pad instanceof FuncDecl){
			try{
				funcD = (FuncDecl)pad;
			}catch(Exception e){

			}	
			if(funcD != null){
				if(funcD.cntpro >= 0){
					if(cnt < funcD.cntpro){
						Main.error(this.lineNum, "Too few parameters in call on "+ funcD.name+"!");
					}

					if(cnt > funcD.cntpro){
						Main.error(this.lineNum, "Too many parameters in call on "+ funcD.name+"!");
					}
				}
			}

		}
		this.type = pad.type;
		int count = 1;
		for(;tmp != null;tmp = tmp.next){
			tmp.check(curScope, lib);
			//System.out.println(tmp.se1.type + " "+ tmp.se1.lineNum);
			tmp.type.checkType(tmp.se1.type,"Param # "+count++ , this, "param not matach");
		}
		
	}

	public int countList(Expression e){
		int count = 0;
		Expression tmp = e;
		for(;tmp != null;tmp = tmp.next){
			count++;
		}
		return count;
	}
	@Override
	void genCode(CodeFile f) {
		int count = 0;
		Stack st = new Stack();//her jeg snus verdi i lenkeliste og FIFO
		if(list != null){
			Expression ex = list;
			while(ex != null){
				st.push(ex);
				ex = ex.next;
				count++;
			}
			int count2 = count;
			while(!st.empty()){
				Expression tmp = (Expression)st.pop();
				tmp.context = this.context;
				tmp.genCode(f);
				f.genInstr("", "pushl", "%eax", "Push param #"+count2--+".");
			}
		}
		f.genInstr("", "call", "func$"+funcD.name_lab, "");
		f.genInstr("", "addl", "$"+(4*count)+",%esp", "Pop parameters");
	}

}
