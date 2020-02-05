package com.galaxymerchant.guide.command.impl;

import com.galaxymerchant.guide.converter.IntergalacticConverter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.text.ParseException;

public class NumeralQueryCommandTests {

    @Mock
    private IntergalacticConverter converter = new IntergalacticConverter();

    @InjectMocks
    private NumeralQueryCommand command;

    @Test
    public void simpleAssignment() throws ParseException {
        String intergalactic = "pish tegj glob glob";
        MockitoAnnotations.initMocks(this);
        command = new NumeralQueryCommand(converter);
        Mockito.when(converter.getNumeralForIntergalactic(intergalactic)).thenReturn(42);
        command.setIntergalactic(intergalactic);
        command.execute();
        Assertions.assertEquals(42, command.getResult());
    }
}
