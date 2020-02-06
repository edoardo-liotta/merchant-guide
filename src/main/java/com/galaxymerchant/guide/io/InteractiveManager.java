package com.galaxymerchant.guide.io;

import com.galaxymerchant.guide.interpreter.GuideInterpreterStrategy;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Scanner;

@Component
public class InteractiveManager {

    private final GuideInterpreterStrategy interpreterStrategy;

    private Scanner scanner = new Scanner(System.in);

    private Writer writer = new PrintWriter(System.out);

    public InteractiveManager(GuideInterpreterStrategy interpreterStrategy) {
        this.interpreterStrategy = interpreterStrategy;
    }

    public void run() throws IOException {
        String input;
        do {
            input = scanner.nextLine();
            if (StringUtils.isNotEmpty(input)) {
                String output = interpreterStrategy.execute(input);
                if (StringUtils.isNotEmpty(output)) {
                    writer.write(output + System.lineSeparator());
                    writer.flush();
                }
            }
        } while (StringUtils.isNotEmpty(input));
    }

    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }

    public void setWriter(Writer writer) {
        this.writer = writer;
    }
}
