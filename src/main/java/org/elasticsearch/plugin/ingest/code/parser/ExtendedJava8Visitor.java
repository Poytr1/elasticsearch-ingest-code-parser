package org.elasticsearch.plugin.ingest.code.parser;

import org.elasticsearch.plugin.ingest.code.parser.model.Element;

import java.util.ArrayList;
import java.util.List;

public class ExtendedJava8Visitor extends Java8BaseVisitor<Void> {

    private List<Element> elements;

    public ExtendedJava8Visitor() {
        this.elements = new ArrayList<>();
    }

    public List<Element> getElements() {
        return this.elements;
    }

    @Override
    public Void visitClassDeclaration(Java8Parser.ClassDeclarationContext ctx) {
        String name = ctx.normalClassDeclaration().Identifier().getText();
        int line = ctx.normalClassDeclaration().Identifier().getSymbol().getLine();
        int start = ctx.normalClassDeclaration().Identifier().getSymbol().getCharPositionInLine();
        int end = ctx.normalClassDeclaration().Identifier().getSymbol().getCharPositionInLine() +
                ctx.normalClassDeclaration().Identifier().getSymbol().getStopIndex() - ctx.normalClassDeclaration().Identifier().getSymbol().getStartIndex();
        this.elements.add(new Element(name, "class",  line, start, end));
        return visitChildren(ctx);
    }

    @Override public Void visitVariableDeclarator(Java8Parser.VariableDeclaratorContext ctx) {
        String name = ctx.getText();
        int line = ctx.start.getLine();
        int start = ctx.start.getCharPositionInLine();
        int end = start + ctx.getText().length() - 1;
        this.elements.add(new Element(name, "field",  line, start, end));
        return visitChildren(ctx);
    }


    @Override public Void visitConstructorDeclarator(Java8Parser.ConstructorDeclaratorContext ctx) {
        String name = ctx.getText();
        int line = ctx.start.getLine();
        int start = ctx.start.getCharPositionInLine();
        int end = start + ctx.getText().length() - 1;
        this.elements.add(new Element(name, "constructor",  line, start, end));
        return visitChildren(ctx);
    }

    @Override public Void visitMethodDeclarator(Java8Parser.MethodDeclaratorContext ctx) {
        String name = ctx.getText();
        int line = ctx.start.getLine();
        int start = ctx.start.getCharPositionInLine();
        int end = start + ctx.getText().length() - 1;
        this.elements.add(new Element(name, "method",  line, start, end));
        return visitChildren(ctx);
    }
}

