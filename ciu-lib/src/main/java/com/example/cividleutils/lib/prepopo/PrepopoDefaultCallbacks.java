package com.example.cividleutils.lib.prepopo;

public interface PrepopoDefaultCallbacks extends PrepopoCallbacks {

    @Override
    public default void prepopoPreSerialize(PrepopoSerContext prepopoSerContext) {
    }

    @Override
    public default void prepopoPostSerialize(PrepopoSerContext prepopoSerContext) {
    }

    @Override
    public default void prepopoPostDeserialize(PrepopoDeserContext prepopoDeserContext) {
    }

}
