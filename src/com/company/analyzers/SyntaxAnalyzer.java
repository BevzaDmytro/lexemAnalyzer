package com.company.analyzers;

import com.company.extensions.LexemsTable;

public class SyntaxAnalyzer {
    private int i = 0;

    private LexemsTable lexems;

    public SyntaxAnalyzer(LexemsTable lexems){
        this.lexems = lexems;
    }


    private boolean isLexemEqual(String string){
        return lexems.getLexems().get(this.i).getName().equals(string);
    }

    private int line() {
        return    lexems.getLexems().get(this.i).getLine();
    }



    public boolean prog() throws Exception {
        this.i = 0;
        if(spOg()){
            if(lexems.getLexems().get(this.i).getName().equals("{")) {
                this.i++;
                if (isLexemEqual("¶")){
                    this.i++;
                    if (spOp()) {
                        if (lexems.getLexems().get(this.i).getName().equals("}")) {
                            this.i++;
                            return true;
                        }
                        else {
                            throw new Exception("Error: excepted } on line" + lexems.getLexems().get(this.i).getLine());
                        }
                    } else {
                        throw new Exception("Unexcpected operator " + lexems.getLexems().get(this.i).getName() + " on line " + line());
                    }

                  }
                  else throw new Exception("Missed enter after {");
                }
                else{
                throw new Exception("Excepted { on line" + lexems.getLexems().get(this.i).getLine());

            }
            }
            else {
            throw new Exception("Bad list of ogol");
        }
    }

    private boolean spOg() throws Exception {

        if(Og()){
            while (!isLexemEqual("{")){
                if(isLexemEqual("¶")){
                    this.i++;
                    if(!Og()){
                        throw new Exception("You lost definition on line "+line());
                    }
                }
                else throw new Exception("You lost enter after definition!");
            }
            return true;
        }
        else throw new Exception("There is no definition on line "+line());
    }

    private boolean Og() throws Exception {
        if(type()){
            if(variablesList()){
                return true;
            }
            else{
                throw new Exception("No list of variables!");
            }
        }
        else{
            throw new Exception("No type defined!");
        }
    }

    private boolean type() {
        if(isLexemEqual("int") || isLexemEqual("float")){
            this.i++;
            return true;
        }
        else{
            return false;
        }
    }

    private boolean isIDN(){
        return lexems.getLexems().get(this.i).getType().equals("IDN");
    }

    private boolean variablesList() throws Exception {
        if(isIDN()){
            this.i++;
                while(!isLexemEqual("¶") && !isLexemEqual("{")){

                    if(isLexemEqual(",")){
                        this.i++;
                        if(isIDN()){
                            this.i++;
                        }
                        else {
                            throw new Exception("Missed IDN after ,");
                        }
                    }
                    else {
                        throw new Exception("Missed , after IDN");
                    }
                }
            return true;
        }
        else {
            throw new Exception("It's not identificator!");
        }
    }


    private boolean spOp() throws Exception {
        if(Op()){
            while(isLexemEqual("¶")){
                this.i++;
                if(!Op()) {
                    throw new Exception("Missed operator on line" + line());
                }
            }
            return true;
        }
        else{
            return false;
        }
        }

    private boolean Op() throws Exception {
        return (output() || input() || loop() || condTransition() || assignment());
    }

    private boolean input() throws Exception {
        if(isLexemEqual("cin")){
            this.i++;
            do {
                if (isLexemEqual(">>")) {
                    this.i++;
                    if (isIDN()) {
                        this.i++;
                    } else {
                        throw new Exception("Its not IDN! on line "+line());
                    }
                } else {
                    throw new Exception("Missed >> on line "+line());
                }
            }while(isLexemEqual(">>"));
            return true;

        }
        else return false;
    }

    private boolean output() throws Exception {
        if(isLexemEqual("cout")){
            this.i++;
            do {
                if (isLexemEqual("<<")) {
                    this.i++;
                    if (isIDN() || isCON()) {
                        this.i++;
                    } else {
                        throw new Exception("Its not IDN! on line "+line());
                    }
                } else {
                    throw new Exception("Missed << on line "+line());
                }
            }while(isLexemEqual("<<"));
            return true;
        }
        else return false;
    }

    private boolean assignment() throws Exception {
        if(isIDN()){
            this.i++;
            if(isLexemEqual("=")){
                this.i++;
//                if(tern() || expression()){
//                    return true;
//                }
//                else{
//                    return false;
//                }
                return (tern() || expression());
            }
            else{
                throw new Exception("Missed = on line "+line());
            }
        }
        else return false;
    }

    private boolean tern() throws Exception {
        if(isLexemEqual("?")) {
            this.i++;
            if (LE()) {
                if (isLexemEqual("?")) {
                    this.i++;
                    if (expression()) {
                        if (isLexemEqual(":")) {
                            this.i++;
                            if (expression()) {
                                return true;
                            } else {
                                throw new Exception("Missed expression!");
                            }
                        } else {
                            throw new Exception("Missed :");
                        }
                    } else {
                        throw new Exception("Missed expression!");
                    }
                } else {
                    throw new Exception("Missed ? on line " + line());
                }
            } else {
                throw new Exception("It's not LE");
            }
        }
        else return false;
    }

    private boolean condTransition() throws Exception {
        if(isLexemEqual("if")){
            this.i++;
            if(LE()){
//                this.i++;
                if(isLexemEqual("then")){
                    this.i++;
                    if(spOp()){
//                        this.i++;
                        if(isLexemEqual("fi")){
                            this.i++;
                            return true;
                        }
                        else throw new Exception("Wrong end of IF statement on line "+line());
                    }
                    else throw new Exception("Wrong list of operators");
                }
                else{
                    throw new Exception("Missed then statement on line"+lexems.getLexems().get(this.i).getLine());
                }
            }
            else{
                throw new Exception("Missed logical expression on line "+line());
            }
        }
        return false;
    }

    private boolean loop() throws Exception {
        if(isLexemEqual("for")){
            this.i++;
            if(assignment()){
                if(isLexemEqual("by")){
                    this.i++;
                    if(expression()){
                        if(isLexemEqual("to")){
                            this.i++;
                            if(expression()){
                                if(isLexemEqual("do")){
                                    this.i++;
                                    if(spOp()){
                                        if(isLexemEqual("rof")){
                                            this.i++;
                                            return true;
                                        }
                                        else throw new Exception("Missed reyword ROF");
                                    }
                                    else throw new Exception("Missed operators list");
                                }
                                else{
                                    throw new Exception("Missed keyword DO");
                                }
                            }
                            else throw new Exception("Missed expression after TO");
                        }
                        else{
                            throw new Exception("Missed keyword TO");
                        }
                    }
                    else{
                        throw new Exception("Missed expression after by");
                    }
                }
                else{
                    throw new Exception("Missed 'by' on line "+ line());
                }
            }
            else throw new Exception("Missed assignment");
        }
        else return false;
    }

    private boolean expression() throws Exception {
        if(isT()){
            while (isLexemEqual("+") || isLexemEqual("-")){
                this.i++;
                if(!isT()){
                    throw new Exception("Missed terminal on line "+line());
                }
            }
                return true;
        }
        else {
            return false;
        }
    }



    private boolean isT() throws Exception {
        if(isF()){
            while (isLexemEqual("*") || isLexemEqual("/")){
                this.i++;
                if(!isF()){
                    throw new Exception("Missed expression on line "+line()+" after "+ lexems.getLexems().get(i-1).getName());
                }
            }
            return true;
            }

        return false;
    }

    private boolean isF() throws Exception {
        if(isIDN() || isCON()){
            this.i++;
            return true;
        }
        if(isLexemEqual("(")){
            this.i++;
            if(expression()){
                if(isLexemEqual(")")){
                    this.i++;
                    return true;
                }
                else throw new Exception("Missed ) on line "+line());
            }
            else throw new Exception("Missed expression on line "+line());
        }
        else return false;
    }

    private boolean isCON() {
        return lexems.getLexems().get(this.i).getCode() == 101;
    }

    private boolean LE() throws Exception {
        if(isLT()) {
            while(isLexemEqual("OR")){
                this.i++;
                if (!isLT()) {
                    throw new Exception("Missed logic terminal on line " + line());
                }
            }
            return true;
        }
        else return false;
    }

    private boolean isLT() throws Exception {
        if(isLF()){
            while(isLexemEqual("AND")){
                    this.i++;
                    if (isLF()) {
                        throw new Exception("Missed logic terminal on line " + line());
                    }
            }
            return true;
        }
        else return false;
    }

    private boolean isLF() throws Exception {
        if(isLexemEqual("NOT")){
            this.i++;
            if(isLF()){
                return true;
            }
            else throw new Exception("Missed logic expression after NOT on line "+line());
        }
        else if(isLexemEqual("[")){
            this.i++;
            if(LE()){
                if(isLexemEqual("]")){
                    this.i++;
                    return true;
                }
                else throw new Exception("Missed ]on line "+ line());
            }
            else throw new Exception("Missed logic exp after [ on line "+ line());
        }
        else if(isRelation()){
            return true;
        }
        else return false;
    }

    private boolean isRelation() throws Exception {
        if(expression()){
            if(isSG()){
                this.i++;
                if(expression()){
                    return true;
                }
                else throw new Exception("Missed expression on line "+line());
            }
            else {
                return false;
            }
        }
        return false;
    }

    private boolean isSG() {
        return (isLexemEqual(">") || isLexemEqual(">=") || isLexemEqual("<") ||
                isLexemEqual("<=") || isLexemEqual("!=") || isLexemEqual("=="));
    }


}
