package com.company;

public class SyntaxAnalyzer {
    private int i = 0;
    private int checkStep = 0;

    private LexemsTable lexems;

    public SyntaxAnalyzer(LexemsTable lexems){
        this.lexems = lexems;
    }

    public boolean prog() throws Exception {
        this.i = 0;
        if(spOg()){
            if(lexems.getLexems().get(this.i).getName().equals("{")){
                this.i++;
                while(isLexemEqual("¶")) this.i++;
                if(spOp()){
                    if(lexems.getLexems().get(this.i).getName().equals("}")){
                        this.i++;
                        return true;
                    }
                    else {
                        throw new Exception("Error: excepted } on line" + lexems.getLexems().get(this.i).getLine());
                    }
                    }
                    else{
                    throw new Exception("Unexcpected operator " + lexems.getLexems().get(this.i).getName() +" on line "+ line());
                }
                }
                else{
                throw new Exception("Excepted { on line" + lexems.getLexems().get(this.i).getLine());

            }
            }
            else {
            throw new Exception("Bad list of ogol");
        }
    }

    public boolean spOg() throws Exception {
        if(Og()){
            if(lexems.getLexems().get(this.i).getName().equals("¶")){
                this.i++;
                while(!lexems.getLexems().get(this.i).getName().equals("{")){
                    if(Og()){
                        this.i++;
                        if(lexems.getLexems().get(this.i).getName().equals("¶")) {
                            this.i++;
                        }
                        else{
                            throw new Exception("You lost enter after definition!");
                        }
                    }
                    else {
                        throw new Exception("No ogolosh");
                    }
                }
                return true;
            }
            else {
                throw new Exception("You lost enter after definition!");
            }
        }
        else{
            throw new Exception("No ogolosh");
        }
    }
    public boolean Og() throws Exception {
        if(type()){
            if(variablesList()){
//                this.i++;
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

    public boolean type() throws Exception {
        if(lexems.getLexems().get(this.i).getName().equals("int")){
            this.i++;
            return true;
        }
        else if(lexems.getLexems().get(this.i).getName().equals("float")){
            this.i++;
            return true;
        }
        else {
            throw new Exception("Wrong type!");
        }
    }

    public boolean isIDN(){
        return lexems.getLexems().get(this.i).getType().equals("IDN");
    }

    public boolean variablesList() throws Exception {
        if(isIDN()){
            this.i++;
                while(!lexems.getLexems().get(this.i).getName().equals("¶")){
//                while(isIDN()){
                    if(lexems.getLexems().get(this.i).getName().equals(",")){
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

    public boolean spOp() throws Exception {
        if(Op()){
            this.i++;
            if(lexems.getLexems().get(this.i).getName().equals("¶")){
                this.i++;
                while (Op()){
                        this.i++;
                        if(lexems.getLexems().get(this.i).getName().equals("¶")) {
                            this.i++;
                        }
                        else{
                            throw new Exception("You lost enter after operation! on line "+ (line()-1));
                        }
                    }
                    return true;
                }
                this.i--;
                return true;
            }
            else{
                return false;
            }
        }
//        else{
//            throw new Exception("No operation");
//        }
//
//    }

    public boolean Op() throws Exception {
//        if(tern()){
//            return true;
//        }
        if(assignment()){
            return true;
        }
        if(loop()){
            return true;
        }
        if(condTransition()){
            return true;
        }
        if(input()){
            return true;
        }
        if(output()){
            return true;
        }
        else{
            return false;
//            throw new Exception("Wrong operator: "+lexems.getLexems().get(this.i).getName() + " on line "+ line());
        }
    }

    public boolean isLexemEqual(String string){
        return lexems.getLexems().get(this.i).getName().equals(string);
    }

    public boolean input() throws Exception {
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
//            }while(!isLexemEqual("¶"));
            }while(isLexemEqual(">>"));
            this.i--;
            return true;

        }
        else return false;
//        else throw new Exception("Missed cin");
    }

    public boolean output() throws Exception {
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
//            }while(!isLexemEqual("¶"));
            }while(isLexemEqual("<<"));
            this.i--;
            return true;

        }
//        else throw new Exception("Missed cout");
        else return false;
    }

    public boolean assignment() throws Exception {
        this.checkStep = this.i;
        if(isIDN()){
            this.i++;
            if(isLexemEqual("=")){
                this.i++;
                if(tern() || expression()){
                    return true;
                }
                else{
                    this.i = this.checkStep;
                    return false;
//                    throw new Exception("Missing expression!");
                }
            }
            else{
                throw new Exception("Missed = on line "+line());
            }
        }
        else return false;
    }

    public boolean tern() throws Exception {
        this.checkStep = this.i;
                if(LE()){
                    this.i++;
                    if(isLexemEqual("?")){
                        this.i++;
                        if(expression()){
                            this.i++;
                            if(isLexemEqual(":")){
                                this.i++;
                                if(expression()){
                                    return true;
                                }
                                else {
                                    throw new Exception("Missed expression!");
                                }
                            }
                            else {
                                throw new Exception("Missed :");
                            }
                        }

                        else{
                            throw new Exception("Missed expression!");
                        }
                    }
                    else {
                        throw new Exception("Missed ? on line "+line());
                    }
                }
                //
                else {
                    this.i = this.checkStep;
                    return false;
//                    throw new Exception("It's not LE");
                }
            }


    public boolean condTransition() throws Exception {
        if(isLexemEqual("if")){
            this.i++;
            if(LE()){
                this.i++;
                if(isLexemEqual("then")){
                    this.i++;
                    if(spOp()){
                        this.i++;
                        if(isLexemEqual("fi")){
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

    public boolean loop() throws Exception {
        if(isLexemEqual("for")){
            this.i++;
            if(assignment()){
                this.i++;
                if(isLexemEqual("by")){
                    this.i++;
                    if(expression()){
                        this.i++;
                        if(isLexemEqual("to")){
                            this.i++;
                            if(expression()){
                                this.i++;
                                if(isLexemEqual("do")){
                                    this.i++;
                                    if(spOp()){
                                        this.i++;
                                        if(isLexemEqual("rof")){
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
//        this.checkStep = this.i;
        if(isT()){
            this.i++;
//            if(isLexemEqual("+") || isLexemEqual("-")){
//                this.i++;
//                if(isT()){
//                    return true;
//                }
//                else throw new Exception("Missed terminal on line "+line());
//            }
            while (isLexemEqual("+") || isLexemEqual("-")){
                this.i++;
                if(isT()){
                    this.i++;
                }
                else throw new Exception("Missed terminal on line "+line());
            }

//            else {
                this.i--;
                return true;
//            }
        }
        else {
//            this.i = this.checkStep;
            return false;
        }
    }

    private int line() {
        return    lexems.getLexems().get(this.i).getLine();
    }

    private boolean isT() throws Exception {
        if(isF()){
            this.i++;

//                if (isLexemEqual("*") || isLexemEqual("/")) {
//                    this.i++;
//                    if (isF()) {
//                        return true;
//                    } else throw new Exception("Missed terminal on line " + line());
//
//                } else
//                {

            while (isLexemEqual("*") || isLexemEqual("/")){
                this.i++;
                if(isF()){
                    this.i++;
                }
                else throw new Exception("Missed terminal on line "+line());
            }
                    this.i--;
                    return true;
//                }
            }

        return false;
    }

    private boolean isF() throws Exception {
        if(isIDN() || isCON()){
            return true;
        }
        if(isLexemEqual("(")){
            this.i++;
            if(expression()){
                this.i++;
                if(isLexemEqual(")")){
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
            this.i++;

            while(isLexemEqual("OR")){
                this.i++;
                if (isLT()) {
                    this.i++;
                } else throw new Exception("Missed logic terminal on line " + line());
            }

            this.i--;
            return true;
        }
        else return false;
    }

    private boolean isLT() throws Exception {
        if(isLF()){
            this.i++;

            while(isLexemEqual("AND")){
                    this.i++;
                    if (isLF()) {
                        this.i++;
                    }
                    else throw new Exception("Missed logic terminal on line " + line());
            }

            this.i--;
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
                this.i++;
                if(isLexemEqual("]")){
//                    this.i++;
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
            this.i++;
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
