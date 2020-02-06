package com.galaxymerchant.guide.interpreter.impl;

import com.galaxymerchant.guide.command.impl.RomanAssignmentCommand;
import com.galaxymerchant.guide.interpreter.GuideInterpreter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class RomanAssignmentGuideInterpreter implements GuideInterpreter {

    private static final String INTERGALACTIC_GROUP = "intergalactic";
    private static final String ROMAN_GROUP = "roman";
    private static final Pattern PATTERN = Pattern.compile("^(?<" + INTERGALACTIC_GROUP + ">\\w+) (?:is|means) (?<" + ROMAN_GROUP + ">[IVXLCDM])$", Pattern.CASE_INSENSITIVE);

    private ApplicationContext applicationContext;

    @Override
    public String execute(String command) {
        Matcher m = getPattern().matcher(command);
        m.find();
        String intergalactic = m.group(INTERGALACTIC_GROUP).toLowerCase();
        String roman = m.group(ROMAN_GROUP).toUpperCase();
        RomanAssignmentCommand bean = applicationContext.getBean(RomanAssignmentCommand.class);
        bean.setIntergalactic(intergalactic);
        bean.setRoman(roman);
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
