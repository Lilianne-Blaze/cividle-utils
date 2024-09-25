package com.example.cividleutils.lib.prepopo;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;
import net.lbcode.commons.base.annotations.CodeMaturityNeedsReview;

@Getter
@Accessors(fluent = true)
@AllArgsConstructor
public class PrepopoDeserContext {

    private JsonParser jsonParser;

    private DeserializationContext deserializationContext;

    public ObjectCodec objectCodec() {
	return jsonParser.getCodec();
    }

    @CodeMaturityNeedsReview("is codec it always ObjectMapper?")
    public ObjectMapper objectMapper() {
	return (ObjectMapper) jsonParser.getCodec();
    }

}
