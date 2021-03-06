package com.runbox.clazz.entry.attribute;

import javax.json.JsonObjectBuilder;

public class ConstantValue extends Attribute {

    public ConstantValue(long offset) {
        super(offset, "ConstantValue");
    }
    
    public ConstantValue(long offset, int index) {
        super(offset, "ConstantValue"); this.index = index;
    }

    private int index = 0;

    public ConstantValue index(int index) {
        this.index = index; return this;
    }
    
    public int index() {
        return index;
    }

    @Override
    public JsonObjectBuilder toJson() {
        return super.toJson().add("index", index);
    }
}
