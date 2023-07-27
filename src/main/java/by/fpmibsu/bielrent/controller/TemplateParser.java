package by.fpmibsu.bielrent.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import java.io.PrintWriter;

@AllArgsConstructor
public class TemplateParser {
    @Getter
    private final WebContext context;
    private final TemplateEngine engine;

    public void setContextVariable(String key, Object value) {
        context.setVariable(key, value);
    };

    public void parse(String page, PrintWriter writer) {
        engine.process(page, context, writer);
    }
}
