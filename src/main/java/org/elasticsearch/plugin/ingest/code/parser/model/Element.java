package org.elasticsearch.plugin.ingest.code.parser.model;

public class Element {

    private String name;

    private String type;

    private int start;

    private int end;

    private int line;

    public Element(String name, String type, int line, int start, int end) {
        this.setName(name);
        this.setType(type);
        this.setLine(line);
        this.setStart(start);
        this.setEnd(end);
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    @Override
    public String toString() {
        return "name: " + name + ", type: " + type + ", line: " + line + ", start: " + start + ", end: " + end;
    }
}
