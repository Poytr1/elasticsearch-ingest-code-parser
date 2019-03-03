/*
 * Copyright [2018] [Pengcheng Xu]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package org.elasticsearch.plugin.ingest.code.parser;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.elasticsearch.ingest.AbstractProcessor;
import org.elasticsearch.ingest.IngestDocument;
import org.elasticsearch.ingest.Processor;

import java.io.IOException;
import java.util.Map;
import java.util.List;

import org.antlr.v4.runtime.*;

import static org.elasticsearch.ingest.ConfigurationUtils.readStringProperty;
import static org.elasticsearch.ingest.ConfigurationUtils.readList;

/**
 * @author poytr1
 */
public class CodeParserProcessor extends AbstractProcessor {

    public static final String TYPE = "code_parser";

    private final String field;
    private final List<String> targetFields;

    private final Logger logger = LogManager.getLogger(CodeParserProcessor.class);

    public CodeParserProcessor(String tag, String field, List<String> targetFields) throws IOException {
        super(tag);
        // "source_field"
        this.field = field;
        // ["elements"]
        this.targetFields = targetFields;
    }

    @Override
    public IngestDocument execute(IngestDocument ingestDocument) {
        String content = ingestDocument.getFieldValue(field, String.class);
        CodePointCharStream inputStream = CharStreams.fromString(content);
        Lexer lexer = new Java8Lexer(inputStream);
        TokenStream tokenStream = new CommonTokenStream(lexer);
        Java8Parser parser = new Java8Parser(tokenStream);
        ExtendedJava8Visitor extendedJava8Visitor = new ExtendedJava8Visitor();
        extendedJava8Visitor.visit(parser.compilationUnit());
        ingestDocument.setFieldValue(targetFields.get(0), extendedJava8Visitor.getElements().toString());
        return ingestDocument;
    }

    @Override
    public String getType() {
        return TYPE;
    }

    public static final class Factory implements Processor.Factory {

        @Override
        public CodeParserProcessor create(Map<String, Processor.Factory> factories, String tag, Map<String, Object> config) 
            throws Exception {
            String field = readStringProperty(TYPE, tag, config, "field");
            List<String> targetFields = readList(TYPE, tag, config, "target_fields");
            if (targetFields.size() == 0) {
                throw new IllegalArgumentException("target fields is missing");
            }
            return new CodeParserProcessor(tag, field, targetFields);
        }
    }

}
