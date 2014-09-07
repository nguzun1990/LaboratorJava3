package com.pentalog.nguzun.common;

/**
 *
 * @author Guzun
 */
public enum ModelEnum {

    USER("user"),
    GROUP("group"),
    ROLE("role");

    private String value;

    private ModelEnum(String value) {
        this.value = value;
    }

    public static ModelEnum getModelByValue(String value) {
        for (ModelEnum model : ModelEnum.values()) {
            if (model.value.equalsIgnoreCase(value)) {
                return model;
            }
        }
        return null;
    }
}

/*
public enum OperationEnum {

GET("get"),
DELETE("delete");
private String value;

public Operation(String value) {
this.value = value;
}

public String getValue() {
return this.value;
}

public static Operation fromValue(String value) {
for (Operation op : Operation.values()) {
if (op.value.equalsIgnoreCase(value)) {
return op;
}
}
throw new IllegalArgumentException("Illegal value for Operation enum {0}", value);
}
}

 */
