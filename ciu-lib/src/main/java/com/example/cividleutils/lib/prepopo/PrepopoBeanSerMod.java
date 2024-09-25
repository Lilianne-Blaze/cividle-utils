package com.example.cividleutils.lib.prepopo;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PrepopoBeanSerMod extends BeanSerializerModifier {

    private static final Class PPM_CLASS = PrepopoDefaultCallbacks.class;

    @Override
    public JsonSerializer<?> modifySerializer(SerializationConfig config, BeanDescription beanDesc,
	    JsonSerializer<?> serializer) {
	Class beanClass = beanDesc.getBeanClass();
	boolean impsPpm = PPM_CLASS.isAssignableFrom(beanClass);

	if (impsPpm) {
	    log.debug("PrepopoBeanSerMod configuring wrapper for {}", beanClass);
	    return new PrepopoBeanSerWrapper(null, beanDesc.getType(), serializer);
	} else {
	    log.trace("PrepopoBeanSerMod ignoring  {}", beanClass);
	    return serializer;
	}

    }

}
