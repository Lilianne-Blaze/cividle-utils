package com.example.cividleutils.lib.prepopo;

public interface PrepopoCallbacks {

    public void prepopoPreSerialize(PrepopoSerContext prepopoSerContext);

    public void prepopoPostSerialize(PrepopoSerContext prepopoSerContext);

    public void prepopoPostDeserialize(PrepopoDeserContext prepopoDeserContext);

}
