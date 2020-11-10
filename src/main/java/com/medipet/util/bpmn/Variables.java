package com.medipet.util.bpmn;

import lombok.Value;

import java.util.AbstractMap;
import java.util.Map;

@Value
public class Variables {
    private Map<String, AbstractMap.SimpleEntry<String, Object>> variables;
}