package parser;
import java.util.Stack;
import main.CodeFile;
import main.Library;
import main.Main;
import scanner.Scanner;
import scanner.TokenKind;
public class ProcCall extends Statement {
	String name;
	Expression exlist;
	ProcDecl proD;//lens 
	ProcCall(int n) {
		super(n);
	}
	public String identify(){
		return "<proc call> " + name + " on line " + lineNum;
	}
	
	public static ProcCall parse(Scanner s){
		enterParser("proc cal");
		ProcCall pc = new ProcCall(s.curLineNum());
		pc.name = s.curToken.id;
		s.skip(TokenKind.nameToken);
		if(s.curToken.kind == TokenKind.leftParToken){
			s.skip(TokenKind.leftParToken);
			do{
				Expression ny = Expression.parse(s);
				if(pc.exlist == null){
					pc.exlist = ny;
				}else{
					Expression tmp = pc.exlist;
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
		leaveParser("proc call");
		return pc;
	}
	public void prettyPrint() {
		Main.log.prettyPrint(this.name+ "");
		 Expression tmp = exlist;
		 while(tmp != null){
			 Main.log.prettyPrint(" (");
			 tmp.prettyPrint();
			 tmp = tmp.next;
			 if(tmp != null){
				 Main.log.prettyPrint(",");
			 }
			 Main.log.prettyPrint(") ");
		 }
	}
	
	@Override
	void check(Block curScope, Library lib){
		PascalDecl pad = curScope.findDecl(this.name.toUpperCase(), this);
		Expression tmp = exlist;
		int cnt = countList(tmp);
		pad.checkWhetherProcedure(this);
		try{
			if(pad instanceof ProcDecl){
				proD = (ProcDecl)pad;
			}
		}catch(Exception e){}
				
		if(proD != null){
				if(proD.cntpro >= 0){
					//System.out.println(proD.cntpro+"  "+cnt);
					if(cnt < proD.cntpro){
						Main.error(this.lineNum,"Too few parameters in call on p!");
					}
					
					if(cnt > proD.cntpro){
						Main.error(this.lineNum,"Too many parameters in call on p!");
					}
				}
		}
		
		int count = 1;
		for(;tmp != null;tmp = tmp.next){
			tmp.check(curScope, lib);
			//System.out.println("Type: "+tmp.type+"   "+this.lineNum);
			tmp.type.checkType(tmp.se1.type,"Param # "+count++ , this, "param ulike type.");
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
		int antall = 0;
		int count = 0;
		//String name = f.getLabel(this.name);
		if(this.name.equals("write")){
			Expression tmp = exlist;
			
			for(;tmp != null;tmp = tmp.next){
				tmp.context = this.context;
				tmp.genCode(f);
				f.genInstr("", "pushl",  "%eax", "Push next param.");
				if(tmp.type instanceof types.CharType){
					f.genInstr("", "call","write_char" , "");
				}else if (tmp.type instanceof types.IntType){
					f.genInstr("", "call","write_int" , "");
				}else if (tmp.type instanceof types.BoolType){
					f.genInstr("", "call","write_bool" , "");
				}
				f.genInstr("", "addl", "$4,%esp", "Pop param.");			
			}
		}else{
			//her jeg snus verdi i lenkeliste og FIFO
			Stack st = new Stack();
			Expression tmp = exlist;
			while(tmp != null){
				st.push(tmp);
				tmp = tmp.next;
				count++;
			}
			while(!st.empty()){
				Expression tmpex = (Expression)st.pop();
				antall++;
				tmpex.context = this.context;
				tmpex.genCode(f);
				f.genInstr("", "pushl", "%eax", "Push param #"+count--+".");
			}
			
			f.genInstr("", "call", "proc$"+proD.name_lab,"");
			if(antall > 0)f.genInstr("", "addl", "$"+(4*antall)+",%esp", "Pop parameters.");
		}
		
	}
	
}
