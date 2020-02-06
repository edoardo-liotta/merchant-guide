package com.galaxymerchant.guide.converter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.text.ParseException;

public class IntergalacticConverterTests {

    private IntergalacticConverter converter;

    @BeforeEach
    public void beforeEachTest() {
        converter = new IntergalacticConverter();
    }

    @Test
    public void testUnitDivision() {
        converter.setUnitValueForMaterial("silver", 34, 2);
        Assertions.assertEquals(BigDecimal.valueOf(17).doubleValue(), converter.getUnitValueForMaterial("silver").doubleValue());
    }

    @Test
    public void testUnknownMaterial() {
        Assertions.assertEquals(BigDecimal.valueOf(0), converter.getUnitValueForMaterial("Nothing"));
    }

    @Test
    public void testNegativeValues() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> converter.setUnitValueForMaterial("Silver", 34, 0));
        Assertions.assertThrows(IllegalArgumentException.class, () -> converter.setUnitValueForMaterial("Silver", -34, 2));
        Assertions.assertThrows(IllegalArgumentException.class, () -> converter.setUnitValueForMaterial("Silver", 34, -2));
        Assertions.assertThrows(IllegalArgumentException.class, () -> converter.setUnitValueForMaterial("Silver", -34, -2));
    }

    @Test
    public void demiRomanToNumeral() throws ParseException {
        converter.setRomanForIntergalactic("I", "I");
        converter.setRomanForIntergalactic("V", "V");
        converter.setRomanForIntergalactic("X", "X");
        converter.setRomanForIntergalactic("L", "L");
        converter.setRomanForIntergalactic("C", "C");
        converter.setRomanForIntergalactic("D", "D");
        converter.setRomanForIntergalactic("M", "M");

        Assertions.assertEquals(7, converter.getNumeralForIntergalactic("V I      I"));
    }

}
