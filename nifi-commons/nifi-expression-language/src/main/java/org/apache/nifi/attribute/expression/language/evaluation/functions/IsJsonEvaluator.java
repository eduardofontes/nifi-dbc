/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.nifi.attribute.expression.language.evaluation.functions;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.nifi.attribute.expression.language.EvaluationContext;
import org.apache.nifi.attribute.expression.language.evaluation.BooleanEvaluator;
import org.apache.nifi.attribute.expression.language.evaluation.BooleanQueryResult;
import org.apache.nifi.attribute.expression.language.evaluation.Evaluator;
import org.apache.nifi.attribute.expression.language.evaluation.QueryResult;

import java.io.IOException;

public class IsJsonEvaluator extends BooleanEvaluator {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private final Evaluator<String> subject;

    public IsJsonEvaluator(Evaluator<String> subject) {
        this.subject = subject;
    }

    @Override
    public QueryResult<Boolean> evaluate(EvaluationContext evaluationContext) {
        final String subjectValue = subject.evaluate(evaluationContext).getValue();
        if (subjectValue != null) {
            final String trimmedSubjectValue = subjectValue.trim();
            if (isPossibleJsonArray(trimmedSubjectValue) || isPossibleJsonObject(trimmedSubjectValue)) {
                try {
                    MAPPER.readTree(trimmedSubjectValue);
                    return new BooleanQueryResult(true);
                } catch (IOException ignored) {
                    //IOException ignored
                }
            }
        }
        return new BooleanQueryResult(false);
    }

    private boolean isPossibleJsonArray(String subject) {
        return subject.startsWith("[") && subject.endsWith("]");
    }

    private boolean isPossibleJsonObject(String subject) {
        return subject.startsWith("{") && subject.endsWith("}");
    }

    @Override
    public Evaluator<?> getSubjectEvaluator() {
        return subject;
    }
}
