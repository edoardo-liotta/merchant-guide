package com.galaxymerchant.guide.interpreter.impl;

import com.galaxymerchant.guide.command.impl.CreditQueryCommand;
import com.galaxymerchant.guide.converter.IntergalacticConverter;
import org.apache.commons.lang3.StringUtils;
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

public class CreditQueryGuideInterpreterTests {

    @Mock
    private ApplicationContext applicationContext = new GenericApplicationContext();

    @Mock
    private CreditQueryCommand command = new CreditQueryCommand(null);

    @InjectMocks
    private CreditQueryGuideInterpreter interpreter = new CreditQueryGuideInterpreter();

    @BeforeEach
    public void beforeEachTest() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void simpleAssignment() {
        runTest("gLob prok", "silver", 3, BigDecimal.valueOf(15));
        runTest("glob prok  ", "IRon Bronze  ", 4, BigDecimal.valueOf(45));
    }

    private void runTest(String intergalactic, String material, int numeral, BigDecimal unitValue) {
        MockitoAnnotations.initMocks(this);
        Mockito.when(applicationContext.getBean(CreditQueryCommand.class)).thenReturn(command);
        BigDecimal multiply = unitValue.multiply(BigDecimal.valueOf(numeral));
        String testCommand = "How many credits is " + intergalactic + " " + material + " ?";
        Assertions.assertTrue(interpreter.matches(testCommand));
        Mockito.when(command.getIntergalactic()).thenReturn(intergalactic.trim().toLowerCase());
        Mockito.when(command.getMaterial()).thenReturn(material.trim().toLowerCase());
        Mockito.when(command.getResult()).thenReturn(multiply);
        String result = interpreter.execute(testCommand);
        Mockito.verify(command).setRequest((intergalactic + " " + material).toLowerCase());
        String expectedResult = intergalactic.trim().toLowerCase() + " " + StringUtils.capitalize(material.trim().toLowerCase()) + " is " + multiply.toString() + " Credits";
        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    public void validAssignments() {
        Assertions.assertTrue(interpreter.matches("how many Credits is glob prok Silver ?"));
        Assertions.assertTrue(interpreter.matches("how many Credits is glob prok Gold ?"));
        Assertions.assertTrue(interpreter.matches("how many Credits is glob prok Iron ?"));
        Assertions.assertTrue(interpreter.matches("how many Credits is glob prok Iron Bronze ?"));
        Assertions.assertTrue(interpreter.matches("how many Credits is   glob   prok    Iron     ?"));
    }

    @Test
    public void invalidAssignment() {
        Assertions.assertFalse(interpreter.matches("how many Credits is ?"));
    }

    @SpringBootTest
    @DirtiesContext
    static class IntegrationTests {

        @Autowired
        private CreditQueryGuideInterpreter interpreter;

        @Autowired
        private IntergalacticConverter converter;

        @Test
        public void testAssignment() {
            converter.setRomanForIntergalactic("glob", "I");
            converter.setUnitValueForMaterial("silver", 50, 1);
            String result = interpreter.execute("How many credits is glOB gLob  glob   silver ?");
            Assertions.assertEquals("glob glob glob Silver is 150 Credits", result);
        }
    }
}
