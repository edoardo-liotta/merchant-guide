package com.galaxymerchant.guide.interpreter.impl;

import com.galaxymerchant.guide.command.impl.CreditQueryCommand;
import com.galaxymerchant.guide.interpreter.GuideInterpreter;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class CreditQueryGuideInterpreter implements GuideInterpreter {

    private static final String INTERGALACTIC_GROUP = "intergalactic";
    private static final Pattern PATTERN = Pattern.compile("^how many Credits is +(?<" + INTERGALACTIC_GROUP + ">[\\w ]+) +\\?$");

    private ApplicationContext applicationContext;

    @Override
    public String execute(String command) {
        Matcher m = getPattern().matcher(command);
        m.find();
        String wholeRequest = m.group(INTERGALACTIC_GROUP);
        CreditQueryCommand bean = applicationContext.getBean(CreditQueryCommand.class);
        bean.setRequest(wholeRequest);
        bean.execute();
        return bean.getIntergalactic() + " " + bean.getMaterial() + " is " + bean.getResult() + " Credits";
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
