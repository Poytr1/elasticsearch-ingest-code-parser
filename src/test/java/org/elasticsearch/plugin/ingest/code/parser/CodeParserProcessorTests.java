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

import org.elasticsearch.ingest.IngestDocument;
import org.elasticsearch.ingest.RandomDocumentPicks;
import org.elasticsearch.test.ESTestCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.is;

import org.json.JSONObject;

public class CodeParserProcessorTests extends ESTestCase {

    public void testThatProcessorWorks() throws Exception {
        Map<String, Object> document = new HashMap<>();
        document.put("source_field", "class A { int foo; public void bar() { } }");
        IngestDocument ingestDocument = RandomDocumentPicks.randomIngestDocument(random(), document);

        CodeParserProcessor processor = new CodeParserProcessor(randomAlphaOfLength(10), "source_field", Arrays.asList("elements"));
        Map<String, Object> data = processor.execute(ingestDocument).getSourceAndMetadata();

        assertThat(data, hasKey("elements"));
        ArrayList<Map<String, Object>> elements = new ArrayList<>();
        JSONObject jsonObject = new JSONObject();
        JSONObject startPos = new JSONObject();
        JSONObject endPos = new JSONObject();
        // add class A
        startPos.put("line", 1);
        startPos.put("column", 1);
        endPos.put("line", 1);
        endPos.put("column", 42);
        jsonObject.put("name", "A");
        jsonObject.put("start", startPos);
        jsonObject.put("end", endPos);
        jsonObject.put("type", "class");
        elements.add(jsonObject.toMap());
        // add field foo
        startPos.put("line", 1);
        startPos.put("column", 11);
        endPos.put("line", 1);
        endPos.put("column", 18);
        jsonObject.put("name", "foo");
        jsonObject.put("start", startPos);
        jsonObject.put("end", endPos);
        jsonObject.put("type", "field");
        elements.add(jsonObject.toMap());
        // add method bar
        startPos.put("line", 1);
        startPos.put("column", 20);
        endPos.put("line", 1);
        endPos.put("column", 40);
        jsonObject.put("name", "bar");
        jsonObject.put("start", startPos);
        jsonObject.put("end", endPos);
        jsonObject.put("type", "method");
        elements.add(jsonObject.toMap());
        assertThat(data.get("elements"), is(elements));
    }
}

