package com.galaxymerchant.guide.command.impl;

import com.galaxymerchant.guide.command.GuideCommand;
import com.galaxymerchant.guide.converter.IntergalacticConverter;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class CreditQueryCommand implements GuideCommand {

    private String request;
    private String intergalactic;
    private String material;
    private BigDecimal result;

    private final IntergalacticConverter intergalacticConverter;

    public CreditQueryCommand(IntergalacticConverter intergalacticConverter) {
        this.intergalacticConverter = intergalacticConverter;
    }

    @Override
    public void execute() {
        List<String> numbers = numbers(request);
        int materialIndex = findMaterialIndex(request, numbers);
        intergalactic = String.join(" ", numbers).trim();
        material = request.substring(materialIndex).trim();
        try {
            int units = intergalacticConverter.getNumeralForIntergalactic(intergalactic);
            if (units <= 0) {
                throw new IllegalArgumentException("Intergalactic was 0");
            }
            BigDecimal unitValueForMaterial = intergalacticConverter.getUnitValueForMaterial(material);
            result = unitValueForMaterial.multiply(BigDecimal.valueOf(units));
        } catch (ParseException e) {
            throw new IllegalArgumentException("Couldn't parse number for intergalactic " + intergalactic);
        }
    }

    private int findMaterialIndex(String request, List<String> numbers) {
        int index = -1;
        for (String number : numbers) {
            int foundIndex = request.indexOf(number, index);
            if (foundIndex >= 0) {
                index = foundIndex + number.length();
            } else {
                return index;
            }
        }

        return index;
    }

    public List<String> numbers(String wholeRequest) {
        Collection<String> knownIntergalacticNumbers = intergalacticConverter.getKnownIntergalacticNumbers();
        return getIntergalacticNumbers(knownIntergalacticNumbers, wholeRequest.split(" +"));
    }

    private List<String> getIntergalacticNumbers(Collection<String> knownIntergalacticNumbers, String[] split) {
        List<String> validNumbers = new ArrayList<>();
        for (String part : split) {
            if (knownIntergalacticNumbers.contains(part)) {
                validNumbers.add(part);
            } else {
                break;
            }
        }
        return validNumbers;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getIntergalactic() {
        return intergalactic;
    }

    public String getMaterial() {
        return material;
    }

    public BigDecimal getResult() {
        return result;
    }
}
