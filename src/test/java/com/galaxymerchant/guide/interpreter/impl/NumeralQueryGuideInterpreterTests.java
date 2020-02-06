package com.galaxymerchant.guide.interpreter.impl;

import com.galaxymerchant.guide.command.impl.NumeralQueryCommand;
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

public class NumeralQueryGuideInterpreterTests {

    @Mock
    private ApplicationContext applicationContext = new GenericApplicationContext();

    @Mock
    private NumeralQueryCommand command = new NumeralQueryCommand(null);

    @InjectMocks
    private NumeralQueryGuideInterpreter interpreter = new NumeralQueryGuideInterpreter();

    @BeforeEach
    public void beforeEachTest() {
        initMocks();
    }

    @Test
    public void simpleAssignment() {
        testAssignment("pish tegj gloB gLob", 42);
        testAssignment("pish teGj Prok glob  ", 55);
    }

    private void testAssignment(String intergalactic, Integer testResult) {
        initMocks();
        Mockito.when(command.getResult()).thenReturn(testResult);
        String testCommand = "How much is " + intergalactic + " ?";
        Assertions.assertTrue(interpreter.matches(testCommand));
        String executionResult = interpreter.execute(testCommand);
        Mockito.verify(command).setIntergalactic(intergalactic.toLowerCase());
        Mockito.verify(command).execute();
        Assertions.assertEquals(intergalactic.trim().toLowerCase() + " is " + testResult, executionResult);
    }

    private void initMocks() {
        MockitoAnnotations.initMocks(this);
        Mockito.when(applicationContext.getBean(NumeralQueryCommand.class)).thenReturn(command);
        Mockito.doCallRealMethod().when(command).getIntergalactic();
        Mockito.doCallRealMethod().when(command).setIntergalactic(Mockito.anyString());
    }

    @Test
    public void validAssignments() {
        Assertions.assertTrue(interpreter.matches("how muCh is pish tegj glob glob ?"));
        Assertions.assertTrue(interpreter.matches("how much is pish tegj glob  glob ?"));
        Assertions.assertTrue(interpreter.matches("how much is   pish tegj glob glob   ?"));
    }

    @Test
    public void invalidAssignment() {
        Assertions.assertFalse(interpreter.matches("how much is ?"));
        Assertions.assertFalse(interpreter.matches("how much is   ?"));
    }

    @SpringBootTest
    @DirtiesContext
    static class IntegrationTests {

        @Autowired
        private NumeralQueryGuideInterpreter interpreter;

        @Autowired
        private IntergalacticConverter converter;

        @Test
        public void testAssignment() {
            converter.setRomanForIntergalactic("glob", "I");
            String result = interpreter.execute("How mUCh is gLOb gLob Glob ?");
            Assertions.assertEquals("glob glob glob is 3", result);
        }
    }
}
