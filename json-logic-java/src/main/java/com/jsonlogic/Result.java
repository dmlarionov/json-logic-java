/*
 * Copyright 2017 Richard.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jsonlogic;

import com.google.gson.JsonArray;

/**
 *
 * @author Richard
 */
public class Result {

    private final Object value;
    
    public Result(Object value) {

        if(value instanceof  Long)
            this.value=((Long)value).doubleValue();
        else
            this.value = value;
    }

    public Object getCastedValue() {
        return value;
    }

    public boolean isBoolean() {
        return (value instanceof Boolean);
    }
    
    public boolean isNull() {
        return value==null;
    }

    public boolean getBooleanValue() {
        if(!isBoolean())
            if(value == null)
                return false;
            else if( value instanceof JsonArray)
                if (((JsonArray)value).size()==0)
                    return false;
                else
                    return true;
            else if(isDouble())
                if(getDoubleValue()== 0)
                    return false;
                else
                    return true;
            else if(isString())
                if(getStringValue().isEmpty())
                    return false;
                else
                    return true;


        return ((Boolean)value).booleanValue();
    }
    
    public boolean isDouble() {
        return (value instanceof Double);
    }

    public Double getDoubleValue() {
        
        if(value==null)
            return 0.0;
        
        if(isString())
            return Double.valueOf(value.toString());                
        
        return ((Double)value).doubleValue();
    }
    
    public boolean isString() {
        return (value instanceof String);
    }

    public String getStringValue() {
        
        if(isNull())
            return "";
        
        if(isDouble())
            return getDoubleValue().longValue() == getDoubleValue() ? "" + getDoubleValue().longValue() : "" + getDoubleValue();

        return value.toString();
    }

    public boolean isArray() {
        return (value instanceof JsonArray);
    }

    public JsonArray getArrayValue() {
        return (JsonArray)value;
    }
    
}
