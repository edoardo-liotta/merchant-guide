package com.galaxymerchant.guide.command.impl;

import com.galaxymerchant.guide.command.GuideCommand;
import com.galaxymerchant.guide.converter.IntergalacticConverter;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.text.ParseException;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class NumeralQueryCommand implements GuideCommand {

    private String intergalactic;
    private Integer result;

    private final IntergalacticConverter intergalacticConverter;

    public NumeralQueryCommand(IntergalacticConverter intergalacticConverter) {
        this.intergalacticConverter = intergalacticConverter;
    }

    @Override
    public void execute() {
        result = null;
        try {
            result = intergalacticConverter.getNumeralForIntergalactic(intergalactic.trim());
        } catch (ParseException e) {
            throw new IllegalArgumentException("Couldn't translate " + intergalactic + " to numeral");
        }
    }

    public String getIntergalactic() {
        return intergalactic;
    }

    public void setIntergalactic(String intergalactic) {
        this.intergalactic = intergalactic.trim();
    }

    public int getResult() {
        return result;
    }
}
