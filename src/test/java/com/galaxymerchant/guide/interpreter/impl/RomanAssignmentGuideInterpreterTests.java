package com.galaxymerchant.guide.interpreter.impl;

import com.galaxymerchant.guide.command.impl.RomanAssignmentCommand;
import com.galaxymerchant.guide.converter.IntergalacticConverter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.test.annotation.DirtiesContext;

import java.text.ParseException;

public class RomanAssignmentGuideInterpreterTests {

    @Mock
    private ApplicationContext applicationContext = new GenericApplicationContext();

    @Mock
    private RomanAssignmentCommand command = new RomanAssignmentCommand(null);

    @InjectMocks
    private RomanAssignmentGuideInterpreter interpreter = new RomanAssignmentGuideInterpreter();

    @BeforeEach
    public void beforeEachTest() {
        MockitoAnnotations.initMocks(this);
        Mockito.when(applicationContext.getBean(RomanAssignmentCommand.class)).thenReturn(command);
    }

    @Test
    public void simpleAssignment() {
        String testCommand = "gloB means i";
        Assertions.assertTrue(interpreter.matches(testCommand));
        interpreter.execute(testCommand);
        Mockito.verify(command).setIntergalactic("glob");
        Mockito.verify(command).setRoman("I");
        Mockito.verify(command).execute();
    }

    @Test
    public void invalidAssignment() {
        boolean test = interpreter.matches("glob means N");
        Assertions.assertFalse(test);
    }

    @SpringBootTest
    @DirtiesContext
    static class IntegrationTests {

        @Autowired
        private RomanAssignmentGuideInterpreter interpreter;

        @Autowired
        private IntergalacticConverter converter;

        @Test
        public void testAssignment() throws ParseException {
            interpreter.execute("glob means I");
            int numeral = converter.getNumeralForIntergalactic("glob");
            Assertions.assertEquals(1, numeral);
        }
    }
}
