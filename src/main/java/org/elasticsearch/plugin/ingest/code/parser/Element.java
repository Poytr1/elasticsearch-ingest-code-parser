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

import com.github.javaparser.Position;

/**
 * @author poytr1
 */
public class Element {

    private String name;
    private String type;
    private Position start;
    private Position end;

    public void setEnd(Position end) {
        this.end = end;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStart(Position start) {
        this.start = start;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Position getEnd() {
        return end;
    }

    public Position getStart() {
        return start;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Element(name: " + name + ", type: " + type + ", start: " + start.toString() + ", end: " + end.toString() + ")";
    }

}
