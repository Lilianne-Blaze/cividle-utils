package com.example.cividleutils.lib.prepopo;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;
import net.lbcode.commons.base.annotations.CodeMaturityNeedsReview;

@Getter
@Accessors(fluent = true)
@AllArgsConstructor
public class PrepopoSerContext {

    private JsonGenerator jsonGenerator;

    private SerializerProvider serializerProvider;

    public ObjectCodec codec() {
	return jsonGenerator.getCodec();
    }

    @CodeMaturityNeedsReview("is codec it always ObjectMapper?")
    public ObjectMapper objectMapper() {
	return (ObjectMapper) jsonGenerator.getCodec();
    }

}
