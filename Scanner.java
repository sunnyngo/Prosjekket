package scanner;

import main.Main;
import static scanner.TokenKind.*;

import java.io.*;

public class Scanner {
    public Token curToken = null, nextToken = null; 

    private LineNumberReader sourceFile = null;
    private String sourceFileName, sourceLine = "";
    private int sourcePos = 0;
    private int numberline = 0;
    public Scanner(String fileName) {
	sourceFileName = fileName;
	try {
	    sourceFile = new LineNumberReader(new FileReader(fileName));
	} catch (FileNotFoundException e) {
	    Main.error("Cannot read " + fileName + "!");
	}

	readNextToken();  readNextToken();
    }


    public String identify() {
	return "Scanner reading " + sourceFileName;
    }


    public int curLineNum() {
	return curToken.lineNum;
    }

    
    private void error(String message) {
	Main.error("Scanner error " +
		   (getFileLineNum()>0 ? "last line" : "line "+getFileLineNum()+":") + 
		   ": " + message);
    }


    public void readNextToken() {
	curToken = nextToken;  nextToken = null;
	String tmp = "";
	// Del 1 her:
	//haandtere aa lese next linjer i file og siste linjer
	
	do{
		if(sourceFile == null){//ferdig
			nextToken = new Token(eofToken, numberline);
			Main.log.noteToken(nextToken);
			return;
		//hoppe mellomrom og tab 
		} else if( sourceLine.length() > sourcePos && sourceLine.length() > 1 && (sourceLine.charAt(this.sourcePos) == ' ' || sourceLine.charAt(this.sourcePos) == '\t')){
			this.sourcePos++;
		}else if(sourceLine.length() == 1 || sourceLine.length() == 0 || sourcePos >= sourceLine.length()){//tom linje
			readNextLine();
		}else if(sourceLine.length() - 1 > sourcePos && (
				(sourceLine.charAt(sourcePos) == '/' && sourceLine.charAt(sourcePos+1) == '*') 
				|| sourceLine.charAt(sourcePos) == '{')){//kommentar med */ eller {
				if(sourceLine.charAt(sourcePos) == '{'){//sjekke indre kommentar
		    		while(sourceLine.charAt(sourcePos) != '}'){
		    			sourcePos++;
		    			if(sourceLine.length() == sourcePos) readNextLine();
		    			if(sourceFile == null){
		    				error("Uendelig");
		    			}
		    			if(sourceLine.charAt(sourcePos) == '{'){
		    				error("Kommentar feilet");
		    			}
		    			
		    		}
		    		//hoppe }
		    		sourcePos++;
		    	}else if(sourceLine.charAt(sourcePos) == '/' && sourceLine.charAt(sourcePos+1) == '*'){//sjekke indre kommentar
		    		while(sourceLine.length() - 1 > sourcePos && !(sourceLine.charAt(sourcePos) == '*'&& sourceLine.charAt(sourcePos+1) == '/')){
		    			sourcePos++;
		    			
		    			if(sourceLine.length() > sourcePos && sourceLine.charAt(sourcePos) == '/' && sourceLine.charAt(sourcePos+1) == '*'){
		    				error("Kommentar feilet 2");
		    			}
		    			if(sourceLine.length() == sourcePos+1) readNextLine();
		    			if(sourceFile == null){
		    				error("Uendelig");
		    			}
		    		}
		    		//hoppe */
		    		sourcePos++;
		    		sourcePos++;
		    		if(sourceLine.length() == sourcePos+1) readNextLine();
		    	}
		}
		//haandtere navn av verdi eks: tmp, tmp12	
		else if(isLetterAZ(sourceLine.charAt(this.sourcePos))){
			tmp = "";
			while(isLetterAZ(sourceLine.charAt(this.sourcePos))|| isDigit(sourceLine.charAt(this.sourcePos))){
				tmp += sourceLine.charAt(this.sourcePos);
				sourcePos++;
				
			}
			nextToken = new Token(tmp,numberline);
			
		//haandtere tall av verdi eks: tmp12 = 123;
		}else if(isDigit(sourceLine.charAt(this.sourcePos)) == true){
			int num = 0;tmp = "";
			while(isDigit(sourceLine.charAt(this.sourcePos)) == true){
				tmp += sourceLine.charAt(this.sourcePos);
				sourcePos++;
				num = Integer.parseInt(tmp);
				
			}
			nextToken = new Token(num,numberline);
			
			//haandtere et karakter av verdi eks: tmp = 'x'; ''''
		}else if(sourceLine.charAt(this.sourcePos) == '\'' && sourceLine.charAt(this.sourcePos + 1) == '\''
				&& sourceLine.charAt(this.sourcePos+2) == '\'' && sourceLine.charAt(this.sourcePos + 3) == '\''){
			nextToken = new Token('\'',numberline);
			sourcePos+= 4;
			 
		}else if(sourceLine.charAt(this.sourcePos) == '\'' && sourceLine.charAt(this.sourcePos + 2) == '\''){
			nextToken = new Token(sourceLine.charAt(this.sourcePos+1),numberline);
			sourcePos+= 3;
			
			//haandtere andre symbolsk
		}else if(sourceLine.charAt(this.sourcePos) == ';'){
				nextToken = new Token(semicolonToken,numberline);
				sourcePos++;
		}else if (sourceLine.charAt(this.sourcePos) == '('){
				nextToken = new Token(leftParToken,numberline);
				sourcePos++;
		}else if (sourceLine.charAt(this.sourcePos) == ')'){
				nextToken = new Token(rightParToken,numberline);
				sourcePos++;
		}else if (sourceLine.charAt(this.sourcePos) == '.' && sourceLine.charAt(this.sourcePos+1) != '.'){
				nextToken = new Token(dotToken,numberline);
				sourcePos++;
		}else if (sourceLine.charAt(this.sourcePos) == '+'){
				nextToken = new Token(addToken,numberline);
				sourcePos++;
		}else if (sourceLine.charAt(this.sourcePos) == ':'&& sourceLine.charAt(this.sourcePos+1) == '=') {
				nextToken = new Token(assignToken,numberline);
				sourcePos++;
				sourcePos++;
		}else if (sourceLine.charAt(this.sourcePos) == ':'&& sourceLine.charAt(this.sourcePos+1) != '='){
				nextToken = new Token(colonToken,numberline);
				sourcePos++;
		}else if (sourceLine.charAt(this.sourcePos) == ','){
				nextToken = new Token(commaToken,numberline);
				sourcePos++;
		}else if (sourceLine.charAt(this.sourcePos) == '=' &&(sourceLine.charAt(sourcePos+1) != '<' ||
					sourceLine.charAt(sourcePos+1) != '>')){
				nextToken = new Token(equalToken,numberline);
				sourcePos++;
		}else if (sourceLine.charAt(this.sourcePos) == '>' && 
				(sourceLine.charAt(this.sourcePos+1) == '=')){
				nextToken = new Token(greaterEqualToken,numberline);
				sourcePos++;
				sourcePos++;
		}else if (sourceLine.charAt(this.sourcePos) == '>' && sourceLine.charAt(this.sourcePos+1) != '<'){
				nextToken = new Token(greaterToken,numberline);//new
				sourcePos++;
		}else if (sourceLine.charAt(this.sourcePos) == '['){
				nextToken = new Token( leftBracketToken,numberline);
				sourcePos++;
		}else if (sourceLine.charAt(this.sourcePos) == ']'){
				nextToken = new Token( rightBracketToken,numberline);
				sourcePos++;
		}else if (sourceLine.charAt(this.sourcePos) == '<' && sourceLine.charAt(this.sourcePos+1) == '='){
				nextToken = new Token(lessEqualToken,numberline);
				sourcePos++;
				sourcePos++;
		}else if (sourceLine.charAt(this.sourcePos) == '<' && sourceLine.charAt(this.sourcePos+1) == '>'){
				nextToken = new Token(notEqualToken,numberline);
				sourcePos++;
				sourcePos++;
		}else if (sourceLine.charAt(this.sourcePos) == '*'){
				nextToken = new Token(multiplyToken,numberline);
				sourcePos++;
		}else if (sourceLine.charAt(this.sourcePos) == '<'){
				nextToken = new Token(lessToken,numberline);
				sourcePos++;
		}else if (sourceLine.charAt(this.sourcePos) == '.' && sourceLine.charAt(this.sourcePos+1) == '.'){
				nextToken = new Token(rangeToken,numberline);
				sourcePos++;
				sourcePos++;
		}else if (sourceLine.charAt(this.sourcePos) == '-'){
				nextToken = new Token(subtractToken,numberline);
				sourcePos++;
		}else{
				error("FEIL: " + sourceLine);
		}
	}while(nextToken == null);
		Main.log.noteToken(nextToken);
    }
   
    private void readNextLine() {
	if (sourceFile != null) {
	    try {
		sourceLine = sourceFile.readLine();
		this.numberline ++;
		if (sourceLine == null) {
		    sourceFile.close();  sourceFile = null;
		    sourceLine = "";  
		} else {
		    sourceLine += " ";
		}
		sourcePos = 0;
	    } catch (IOException e) {
		Main.error("Scanner error: unspecified I/O error!");
	    }
	}
	if (sourceFile != null) 
	    Main.log.noteSourceLine(getFileLineNum(), sourceLine);
    }


    private int getFileLineNum() {
	return (sourceFile!=null ? sourceFile.getLineNumber() : 0);
    }


    // Character test utilities:

    private boolean isLetterAZ(char c) {
	return 'A'<=c && c<='Z' || 'a'<=c && c<='z';
    }


    private boolean isDigit(char c) {
	return '0'<=c && c<='9';
    }


    // Parser tests:

    public void test(TokenKind t) {
	if (curToken.kind != t)
	    testError(t.toString());
    }

    public void testError(String message) {
	Main.error(curLineNum(), 
		   "Expected a " + message +
		   " but found a " + curToken.kind + "!");
    }

    public void skip(TokenKind t) {
	test(t);  
	readNextToken();
    }
}
