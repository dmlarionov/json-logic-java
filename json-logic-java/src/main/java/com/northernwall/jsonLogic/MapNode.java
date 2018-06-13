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
package com.northernwall.jsonLogic;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.Map;

/**
 *
 * @author Richard
 */
class MapNode extends BinaryNode {

    MapNode(Node left, Node right) {
        super(left, right, "map");
    }

    @Override
    Result eval(Map<String, Result> data) throws EvaluationException{
        Result leftResult = left.eval(data);

        if (!leftResult.isArray())
            throw new EvaluationException("");

        StringBuilder sb=new StringBuilder();

        right.treeToString(sb);

        if(sb.toString().contains("{\"var\":\"\"}"))
        {
            JsonArray results=new JsonArray();

            for(int i=0;i<leftResult.getArrayValue().size();i++)
            {
                String afterReplace=sb.toString().replace("{\"var\":\"\"}",leftResult.getArrayValue().get(i).getAsDouble()+"");

                try {
                    Result r=new JsonLogic().apply(afterReplace,"");

                    if(!r.isDouble())
                        throw new EvaluationException("no double value on map result");

                    results.add(r.getDoubleValue());

                }catch (Exception ex){
                    ex.printStackTrace();
                }


            }

            return new Result(results);
        }

        return null;
    }

    @Override
    boolean isConstant() {
        return false;
    }

}
