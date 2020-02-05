package com.galaxymerchant.guide.command.impl;

import com.galaxymerchant.guide.converter.IntergalacticConverter;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.text.ParseException;

public class MaterialAssignmentCommandTests {

    @Mock
    private IntergalacticConverter intergalacticConverter = new IntergalacticConverter();

    @InjectMocks
    private MaterialAssignmentCommand command;

    @Test
    public void simpleAssignment() throws ParseException {
        testAssignment("glob glob     ", "Silver", 34, 2);
        testAssignment("   glob glob     ", " Silver  ", 30, 3);
    }

    private void testAssignment(String intergalacticNumber, String material, long credits, int mockUnits) throws ParseException {
        MockitoAnnotations.initMocks(this);
        command = new MaterialAssignmentCommand(intergalacticConverter);
        Mockito.when(intergalacticConverter.getNumeralForIntergalactic(intergalacticNumber)).thenReturn(mockUnits);
        command.setIntergalactic(intergalacticNumber);
        command.setMaterial(material);
        command.setCredits(credits);
        command.execute();
        Mockito.verify(intergalacticConverter).getNumeralForIntergalactic(intergalacticNumber);
        Mockito.verify(intergalacticConverter).setUnitValueForMaterial(material, credits, mockUnits);
    }
}
