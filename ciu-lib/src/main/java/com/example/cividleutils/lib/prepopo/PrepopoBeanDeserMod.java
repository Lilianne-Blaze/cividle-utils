package com.example.cividleutils.lib.prepopo;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.BeanDeserializerModifier;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PrepopoBeanDeserMod extends BeanDeserializerModifier {

    private static final Class PPM_CLASS = PrepopoDefaultCallbacks.class;

    @Override
    public JsonDeserializer<?> modifyDeserializer(DeserializationConfig config, BeanDescription beanDesc,
	    JsonDeserializer<?> deserializer) {
	Class beanClass = beanDesc.getBeanClass();
	boolean impsPpm = PPM_CLASS.isAssignableFrom(beanClass);

	if (impsPpm) {
	    log.debug("PrepopoBeanDeserMod configuring wrapper for {}", beanClass);
	    return new PrepopoBeanDeserWrapper(null, beanDesc.getType(), deserializer);
	} else {
	    log.trace("PrepopoBeanDeserMod ignoring  {}", beanClass);
	    return deserializer;
	}
    }

}
