package com.pentalog.nguzun;

import static java.lang.System.out;

import com.pentalog.nguzun.common.ModelEnum;
import com.pentalog.nguzun.common.OperationEnum;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * 
 * @author Guzun
 */
public class Laborator1 {

    public static void main(String[] args) {
        if (args.length < 2) {
            out.println("You must provide the model and operation (at leat 2 arguments)");
            return;
        }
        ModelEnum model = ModelEnum.getModelByValue(args[0]);
        OperationEnum operationEnum = OperationEnum.getOperationByValue(args[1]);
        if (model == null) {
            out.println("Unknown model " + args[0]);
            return;
        }
        if (operationEnum == null) {
            out.println("Unknown operation " + args[1]);
            return;
        }
        switch (model) {
        case USER: {
        	Procesor.processUser(operationEnum, args);
            break;
        }
        case GROUP: {
        	Procesor.processGroup(operationEnum, args);
            break;
        }
        case ROLE: {
        	Procesor.processRole(operationEnum, args);
            break;
        }
        }
    }
}
