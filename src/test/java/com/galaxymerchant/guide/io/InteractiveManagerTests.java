package com.galaxymerchant.guide.io;

import com.galaxymerchant.guide.interpreter.GuideInterpreterStrategy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Scanner;

public class InteractiveManagerTests {

    @Mock
    private GuideInterpreterStrategy interpreterStrategy = new GuideInterpreterStrategy();

    @InjectMocks
    private InteractiveManager interactiveManager;

    @BeforeEach
    public void beforeEachTest() {
        MockitoAnnotations.initMocks(this);
        interactiveManager = new InteractiveManager(interpreterStrategy);
    }

    @Test
    public void test() throws IOException {
        String command = "Hello";
        String expected = "result";
        String testString = command + System.lineSeparator() + System.lineSeparator();
        Scanner scanner = new Scanner(testString);
        Writer targetWriter = new StringWriter();
        interactiveManager.setScanner(scanner);
        interactiveManager.setWriter(targetWriter);
        Mockito.when(interpreterStrategy.execute(Mockito.anyString())).thenReturn(expected);
        interactiveManager.run();
        Mockito.verify(interpreterStrategy).execute(command);
        Assertions.assertEquals(expected + System.lineSeparator(), targetWriter.toString());
    }
}
