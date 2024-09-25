package com.example.cividleutils.lib.prepopo;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdDelegatingSerializer;
import com.fasterxml.jackson.databind.util.Converter;
import java.io.IOException;

public class PrepopoBeanSerWrapper extends StdDelegatingSerializer {
    
    public PrepopoBeanSerWrapper(Converter<Object, ?> converter, JavaType delegateType, JsonSerializer<?> delegateSerializer) {
	super(converter, delegateType, delegateSerializer);
    }
    
    @Override
    public void serialize(Object value, JsonGenerator gen, SerializerProvider provider) throws IOException {
	super.serialize(value, gen, provider); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
	
//	gen.getCodec()
    }
    
    
    
    

}
