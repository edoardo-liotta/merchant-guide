package com.galaxymerchant.guide.command.impl;

import com.galaxymerchant.guide.converter.IntergalacticConverter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Arrays;

public class CreditQueryCommandTests {

    @Mock
    private IntergalacticConverter intergalacticConverter = new IntergalacticConverter();

    @InjectMocks
    private CreditQueryCommand command;

    @Test
    public void simpleAssignment() throws ParseException {
        runTest("glob prok", "Silver", 3, BigDecimal.valueOf(15));
        runTest("  glob  prok  ", "Iron", 4, BigDecimal.valueOf(12));
        runTest("  glob  prok  ", "  Iron Bronze    ", 1, BigDecimal.valueOf(10));
    }

    private void runTest(String intergalactic, String material, int numeral, BigDecimal unitValue) throws ParseException {
        MockitoAnnotations.initMocks(this);
        command = new CreditQueryCommand(intergalacticConverter);
        BigDecimal expectedResult = unitValue.multiply(BigDecimal.valueOf(numeral));
        Mockito.when(intergalacticConverter.getKnownIntergalacticNumbers()).thenReturn(Arrays.asList(intergalactic.split(" +")));
        Mockito.when(intergalacticConverter.getNumeralForIntergalactic(intergalactic.replaceAll(" +", " ").trim())).thenReturn(numeral);
        Mockito.when(intergalacticConverter.getUnitValueForMaterial(material.trim())).thenReturn(unitValue);
        command.setRequest(intergalactic + " " + material);
        command.execute();
        BigDecimal result = command.getResult();
        Assertions.assertEquals(expectedResult, result);
    }
}
