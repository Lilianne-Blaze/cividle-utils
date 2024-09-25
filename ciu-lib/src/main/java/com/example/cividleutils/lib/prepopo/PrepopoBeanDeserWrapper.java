package com.example.cividleutils.lib.prepopo;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.SettableBeanProperty;
import com.fasterxml.jackson.databind.deser.std.StdDelegatingDeserializer;
import com.fasterxml.jackson.databind.util.Converter;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PrepopoBeanDeserWrapper<T> extends StdDelegatingDeserializer<T> {

    private static final Class PPM_CLASS = PrepopoDefaultCallbacks.class;

    private JavaType delegateType;

    private JsonDeserializer<T> delegateDeserializer;

    private Class<T> targetClass;

    public PrepopoBeanDeserWrapper(Converter<Object, T> converter, JavaType delegateType,
	    JsonDeserializer<?> delegateDeserializer) {
	super(converter, delegateType, delegateDeserializer);
	this.delegateType = delegateType;
	this.delegateDeserializer = (JsonDeserializer<T>) delegateDeserializer;
    }

    @Override
    public SettableBeanProperty findBackReference(String refName) {
	return delegateDeserializer.findBackReference(refName);
    }

    @Override
    public T deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
	T result = delegateDeserializer.deserialize(p, ctxt);
	
	Class resultClass = result.getClass();
	if (PPM_CLASS.isAssignableFrom(resultClass)) {
	    PrepopoDefaultCallbacks ppm = (PrepopoDefaultCallbacks)result;
	    PrepopoDeserContext pdc = new PrepopoDeserContext(p, ctxt);
	    ppm.prepopoPostDeserialize(pdc);
	}

	return result;
    }
}
