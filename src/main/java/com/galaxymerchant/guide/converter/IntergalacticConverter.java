package com.galaxymerchant.guide.converter;

import com.github.fracpete.romannumerals4j.RomanNumeralFormat;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class IntergalacticConverter {

    private Map<String, String> intergalacticMap = new HashMap<>();
    private Map<String, BigDecimal> materialConversionMap = new HashMap<>();

    public void setRomanForIntergalactic(String intergalactic, String roman) {
        intergalacticMap.put(intergalactic, roman);
    }

    public int getNumeralForIntergalactic(String intergalacticNumber) throws ParseException {
        String roman = toRoman(intergalacticNumber);
        return new RomanNumeralFormat().parse(roman).intValue();
    }

    public BigDecimal getUnitValueForMaterial(String material) {
        return materialConversionMap.getOrDefault(material, BigDecimal.ZERO);
    }

    public void setUnitValueForMaterial(String material, long credits, int units) {
        if (StringUtils.isEmpty(material) || credits < 0 || units <= 0) {
            throw new IllegalArgumentException();
        }
        BigDecimal creditsPerUnit = BigDecimal.valueOf(credits).divide(BigDecimal.valueOf(units), 2, RoundingMode.HALF_UP);
        materialConversionMap.put(material.trim(), creditsPerUnit);
    }

    public Collection<String> getKnownIntergalacticNumbers() {
        return intergalacticMap.keySet();
    }

    private String toRoman(String intergalacticNumber) {
        return Arrays.stream(intergalacticNumber.split(" +"))
                .filter(StringUtils::isNotEmpty)
                .map(x -> {
                    if (!intergalacticMap.containsKey(x)) {
                        throw new IllegalArgumentException("Can't convert Intergalactic " + x + " to Roman");
                    }
                    return intergalacticMap.get(x);
                })
                .collect(Collectors.joining());
    }

    public void reset() {
        intergalacticMap.clear();
        materialConversionMap.clear();
    }
}
