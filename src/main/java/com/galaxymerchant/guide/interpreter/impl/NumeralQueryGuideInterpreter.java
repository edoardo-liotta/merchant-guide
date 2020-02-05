package com.galaxymerchant.guide.interpreter.impl;

import com.galaxymerchant.guide.command.impl.NumeralQueryCommand;
import com.galaxymerchant.guide.interpreter.GuideInterpreter;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class NumeralQueryGuideInterpreter implements GuideInterpreter {

    private static final String INTERGALACTIC_GROUP = "intergalactic";
    private static final Pattern PATTERN = Pattern.compile("^how much is +(?<" + INTERGALACTIC_GROUP + ">\\w[\\w ]+) +\\?$");

    private ApplicationContext applicationContext;

    @Override
    public String execute(String command) {
        Matcher m = getPattern().matcher(command);
        m.find();
        String intergalactic = m.group(INTERGALACTIC_GROUP);
        NumeralQueryCommand bean = applicationContext.getBean(NumeralQueryCommand.class);
        bean.setIntergalactic(intergalactic);
        bean.execute();
        return bean.getIntergalactic() + " is " + bean.getResult();
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
