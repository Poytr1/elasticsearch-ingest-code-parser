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
import org.elasticsearch.plugin.ingest.code.parser.model.Element;
import org.elasticsearch.test.ESTestCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.is;

public class CodeParserProcessorTests extends ESTestCase {

    public void testThatProcessorWorks() throws Exception {
        Map<String, Object> document = new HashMap<>();
        document.put("source_field", "class A { int foo; public int bar() { return 2; } }");
        IngestDocument ingestDocument = RandomDocumentPicks.randomIngestDocument(random(), document);

        CodeParserProcessor processor = new CodeParserProcessor(randomAlphaOfLength(10), "source_field", Arrays.asList("elements"));
        Map<String, Object> data = processor.execute(ingestDocument).getSourceAndMetadata();

        assertThat(data, hasKey("elements"));
        ArrayList<Element> elements = new ArrayList<>();
        // add class A
        elements.add(new Element("A", "class", 1, 6, 6));
        // add field foo
        elements.add(new Element("foo", "field", 1, 14, 16));
        // add method bar
        elements.add(new Element("bar()", "method", 1, 30, 34));
        assertThat(data.get("elements"), is(elements.toString()));
    }
}

