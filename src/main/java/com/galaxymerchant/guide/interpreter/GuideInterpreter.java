package com.galaxymerchant.guide.interpreter;

import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.NonNull;

import java.util.regex.Pattern;

public interface GuideInterpreter extends ApplicationContextAware {

    @NonNull
    String execute(String command);

    @NonNull
    Pattern getPattern();

    default boolean matches(String command) {
        return getPattern().matcher(command).matches();
    }
}
