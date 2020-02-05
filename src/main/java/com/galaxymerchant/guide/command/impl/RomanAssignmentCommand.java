package com.galaxymerchant.guide.command.impl;

import com.galaxymerchant.guide.command.GuideCommand;
import com.galaxymerchant.guide.converter.IntergalacticConverter;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class RomanAssignmentCommand implements GuideCommand {

    private String intergalactic;
    private String roman;

    private final IntergalacticConverter intergalacticConverter;

    public RomanAssignmentCommand(IntergalacticConverter intergalacticConverter) {
        this.intergalacticConverter = intergalacticConverter;
    }

    @Override
    public void execute() {
        intergalacticConverter.setRomanForIntergalactic(intergalactic, roman);
    }

    public void setIntergalactic(String intergalactic) {
        this.intergalactic = intergalactic;
    }

    public void setRoman(String roman) {
        this.roman = roman;
    }
}
