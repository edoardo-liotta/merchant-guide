package com.galaxymerchant.guide.command.impl;

import com.galaxymerchant.guide.command.GuideCommand;
import com.galaxymerchant.guide.converter.IntergalacticConverter;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.text.ParseException;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class MaterialAssignmentCommand implements GuideCommand {

    private String intergalactic;
    private String material;
    private long credits;

    private final IntergalacticConverter intergalacticConverter;

    public MaterialAssignmentCommand(IntergalacticConverter intergalacticConverter) {
        this.intergalacticConverter = intergalacticConverter;
    }

    @Override
    public void execute() {
        try {
            int units = intergalacticConverter.getNumeralForIntergalactic(intergalactic);
            intergalacticConverter.setUnitValueForMaterial(material, credits, units);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Could not parse number of credits");
        }
    }

    public void setIntergalactic(String intergalactic) {
        this.intergalactic = intergalactic;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public void setCredits(long credits) {
        this.credits = credits;
    }
}
