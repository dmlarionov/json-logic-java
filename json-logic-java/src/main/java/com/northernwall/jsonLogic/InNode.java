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
import com.google.gson.JsonElement;

import java.util.Map;

/**
 *
 * @author Richard
 */
class InNode extends BinaryNode {

    InNode(Node left, Node right) {
        super(left, right, " in ");
    }

    @Override
    Result eval(Map<String, Result> data) throws EvaluationException {
        Result leftResult = left.eval(data);
        Result rightResult = right.eval(data);

        if(!leftResult.isString())
            throw new EvaluationException("");

        if (rightResult.isString())
            return new Result(rightResult.getStringValue().contains(leftResult.getStringValue()));

        if(rightResult.isArray())
        {
            JsonArray jj=rightResult.getArrayValue();

            for(int i=0;i<jj.size();i++)
            {
                if(jj.get(i).getAsString().equals(leftResult.getStringValue()))
                    return new Result(true);
            }

            return new Result(false);
        }

        return null;
    }

    @Override
    boolean isConstant() {
        return left.isConstant() && right.isConstant();
    }
    
}
