package com.pentalog.nguzun.common;

/**
 *
 * @author Guzun
 */
public enum OperationEnum {

    GET("get"),
    UPDATE("update"),
    DELETE("delete"),
    INSERT("insert"),
    LIST("list");
    
    private String value;

    private OperationEnum(String value) {
        this.value = value;
    }

    public static OperationEnum getOperationByValue(String value) {
        for (OperationEnum op : OperationEnum.values()) {
            if (op.value.equalsIgnoreCase(value)) {
                return op;
            }
        }
        return null;
    }
}

