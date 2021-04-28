package com.htr.enums;

/**
 * @Desc: 性别 枚举
 */
public enum Sex {
    woman(0, "F"),
    man(1, "M"),
    secret(2, "CONF.");

    public final Integer type;
    public final String value;

    Sex(Integer type, String value) {
        this.type = type;
        this.value = value;
    }
}
