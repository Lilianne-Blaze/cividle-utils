package com.example.cividleutils.obj;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

@Accessors(fluent = true)
@AllArgsConstructor
@Getter
public class DoubleAtTick {
    
    private double value;
    
    private long tick;
    

}
