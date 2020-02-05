package com.galaxymerchant.guide.command.impl;

import com.galaxymerchant.guide.converter.IntergalacticConverter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class RomanAssignmentCommandTests {

    @Mock
    private IntergalacticConverter intergalacticConverter = new IntergalacticConverter();

    @InjectMocks
    private RomanAssignmentCommand command;

    @BeforeEach
    public void beforeEachTest() {
        MockitoAnnotations.initMocks(this);
        command = new RomanAssignmentCommand(intergalacticConverter);
    }

    @Test
    public void simpleAssignment() {
        String intergalactic = "glob";
        command.setIntergalactic(intergalactic);
        String roman = "I";
        command.setRoman(roman);
        command.execute();
        Mockito.verify(intergalacticConverter).setRomanForIntergalactic(intergalactic, roman);
    }
}
