package com.galaxymerchant.guide.interpreter.impl;

import com.galaxymerchant.guide.command.impl.MaterialAssignmentCommand;
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

import java.math.BigDecimal;

public class MaterialAssignmentGuideInterpreterTests {

    @Mock
    private ApplicationContext applicationContext = new GenericApplicationContext();

    @Mock
    private MaterialAssignmentCommand command = new MaterialAssignmentCommand(null);

    @InjectMocks
    private MaterialAssignmentGuideInterpreter interpreter = new MaterialAssignmentGuideInterpreter();

    @BeforeEach
    public void beforeEachTest() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void simpleAssignment() {
        testAssignment("glob glob     ", "SilvEr", 34);
        testAssignment("glob piSH    ", "Iron Bronze", 30);
    }

    private void testAssignment(String intergalacticNumber, String material, long credits) {
        MockitoAnnotations.initMocks(this);
        Mockito.when(applicationContext.getBean(MaterialAssignmentCommand.class)).thenReturn(command);
        String testCommand = intergalacticNumber + " units of " + material + " are worth " + credits + " Credits";
        Assertions.assertTrue(interpreter.matches(testCommand));
        interpreter.execute(testCommand);
        Mockito.verify(command).setIntergalactic(intergalacticNumber.toLowerCase());
        Mockito.verify(command).setMaterial(material.toLowerCase());
        Mockito.verify(command).setCredits(credits);
        Mockito.verify(command).execute();
    }

    @Test
    public void invalidAssignment() {
        Assertions.assertFalse(interpreter.matches(" units of Silver are worth 34 Credits"));
        Assertions.assertFalse(interpreter.matches("glob units of Silver are worth  Credits"));
        Assertions.assertFalse(interpreter.matches("glob units of Silver are worth some Credits"));
    }

    @SpringBootTest
    @DirtiesContext
    static class IntegrationTests {

        @Autowired
        private MaterialAssignmentGuideInterpreter interpreter;

        @Autowired
        private IntergalacticConverter converter;

        @Test
        public void testAssignment() {
            converter.setRomanForIntergalactic("glob", "I");
            interpreter.execute("glob glob units of Silver are worth 30 Credits");
            BigDecimal numeral = converter.getUnitValueForMaterial("silver");
            Assertions.assertEquals(BigDecimal.valueOf(15).doubleValue(), numeral.doubleValue());
        }
    }
}
