package com.galaxymerchant.guide.interpreter.impl;

import com.galaxymerchant.guide.command.impl.MaterialAssignmentCommand;
import com.galaxymerchant.guide.interpreter.GuideInterpreter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class MaterialAssignmentGuideInterpreter implements GuideInterpreter {

    private static final String INTERGALACTIC_GROUP = "intergalactic";
    private static final String MATERIAL_GROUP = "material";
    private static final String CREDITS_GROUP = "credits";
    private static final Pattern PATTERN = Pattern.compile("^ *(?<" + INTERGALACTIC_GROUP + ">[\\w ]+) +units of +(?<" + MATERIAL_GROUP + ">.+) +are worth (?<" + CREDITS_GROUP + ">\\d+) Credits$");

    private ApplicationContext applicationContext;

    @Override
    public String execute(String command) {
        Matcher m = getPattern().matcher(command);
        m.find();

        String intergalactic = m.group(INTERGALACTIC_GROUP);
        String material = m.group(MATERIAL_GROUP);
        long credits = Long.parseLong(m.group(CREDITS_GROUP));
        MaterialAssignmentCommand bean = applicationContext.getBean(MaterialAssignmentCommand.class);
        bean.setIntergalactic(intergalactic);
        bean.setMaterial(material);
        bean.setCredits(credits);
        bean.execute();

        return StringUtils.EMPTY;
    }

    @Override
    public Pattern getPattern() {
        return PATTERN;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }
}
