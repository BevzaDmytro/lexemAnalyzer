package com.company.analyzers;

import com.company.extensions.*;

import java.util.ArrayList;
import java.util.Stack;

public class SyntaxAnalyzer2 {
    private LexemsTable lexems;
    private int currentState;

    public SyntaxAnalyzer2(LexemsTable lexemsTable) {
        this.lexems = lexemsTable;
    }

    public void analyze() throws Exception {
        StateController stateController = new StateController();
        stateController.createStates();
        Stack stack = new Stack();
        Lexem lexem;
        int i = 0;
        currentState = 1;
        while(i < lexems.getLexems().size()){
            lexem = lexems.getLexems().get(i);

            if(hasState(stateController.getStateByNum(currentState), lexem.getName())){
                Instruction instruction = getInstruction(stateController.getStateByNum(currentState), lexem.getName());
                if(instruction.getStackNum() != 0){
                    stack.push(instruction.getStackNum());
                }
                if(instruction.getBeta() != 0){
                    currentState = instruction.getBeta();
                }
                else if(instruction.getFunc().equals("exit")){
                    if(!stack.empty())
                        currentState = (int) stack.pop();
                    else System.out.println("Stack is empty, programm finished");
//                    continue;
                }
                i++;
            }
            else{
                if(stateController.getStateByNum(currentState).getDefaultFunc().equals("exit")){
                    currentState = (int) stack.pop();
                    continue;
                }
                else if(stateController.getStateByNum(currentState).getDefaultFunc().equals("err")){
                    throw new Exception("Error in state "+currentState+ " on lexem "+lexem.getName());
                }
                else {
                    String str = stateController.getStateByNum(currentState).getDefaultFunc();
                    currentState =Integer.parseInt(str.substring(str.indexOf('<') + 1, str.indexOf('>')));
                    int toStack =  Integer.parseInt(str.substring(str.indexOf(',')+1));
                    stack.push(toStack);
                    continue;
//                    stack.push(stateController.getStates().get(i).getStack());
//                    currentState = stateController.getStates().get(i).getBeta();
                }
//                i++;
            }


        }
    }

    public boolean hasState(State state, String name){
        ArrayList<Instruction> instructions = state.getInstructions();

        for (Lexem lexem: this.lexems.getLexems() ) {
            if(lexem.getName().equals(name) && lexem.getCode()==100) {name="id"; break;}
            if(lexem.getName().equals(name) && lexem.getCode()==101) {name="con"; break;}
        }

        for (Instruction instruction : instructions ) {
            if(instruction.getMark().equals(name)) return true;
        }


        return false;
    }

    public Instruction getInstruction(State state, String name){
        ArrayList<Instruction> instructions = state.getInstructions();
        int i=0;

        for (Lexem lexem: this.lexems.getLexems() ) {
            if(lexem.getName().equals(name) && lexem.getCode()==100) {name="id"; break;}
            if(lexem.getName().equals(name) && lexem.getCode()==101) {name="con"; break;}
        }

        for (Instruction instruction : instructions ) {
            i++;
            if(instruction.getMark().equals(name)) return instruction;
        }
        return state.getInstructions().get(0);
    }

}
