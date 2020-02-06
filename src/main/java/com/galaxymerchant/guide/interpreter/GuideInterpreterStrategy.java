package com.galaxymerchant.guide.interpreter;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;

@Component("interpreterStrategy")
public class GuideInterpreterStrategy implements ApplicationContextAware {

    private static final String DEFAULT_ERROR_MESSAGE = "I have no idea what you are talking about";

    private Collection<GuideInterpreter> interpreters;

    public String execute(String command) {
        try {
            return getInterpreterForCommand(command).orElseThrow(UnsupportedOperationException::new).execute(command);
        } catch (UnsupportedOperationException | IllegalArgumentException e) {
            return DEFAULT_ERROR_MESSAGE;
        }
    }

    public Optional<GuideInterpreter> getInterpreterForCommand(String command) {
        return interpreters.stream()
                .filter(x -> x.matches(command))
                .findFirst();
    }

    public void setInterpreters(Collection<GuideInterpreter> interpreters) {
        this.interpreters = interpreters;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        setInterpreters(applicationContext.getBeansOfType(GuideInterpreter.class).values());
    }
}
