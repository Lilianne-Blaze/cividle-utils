package com.example.cividleutils.lib.prepopo;

import com.fasterxml.jackson.databind.module.SimpleModule;

public class PrepopoModule extends SimpleModule {

    @Override
    public void setupModule(SetupContext context) {
	super.setupModule(context);
	
//	context.addBeanSerializerModifier(_serializerModifier);
	context.addBeanDeserializerModifier(new PrepopoBeanDeserMod());
    }

    
}
