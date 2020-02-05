package com.galaxymerchant.guide;

import com.galaxymerchant.guide.converter.IntergalacticConverter;
import com.galaxymerchant.guide.interpreter.GuideInterpreterStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

@Configuration
public class SpringConfiguration {

    @Bean
    public IntergalacticConverter intergalacticConverter() {
        return new IntergalacticConverter();
    }

    @Bean
    public GuideInterpreterStrategy interpreterStrategy() {
        return new GuideInterpreterStrategy();
    }

    @Bean
    ResourcePatternResolver resourceResolver() {
        return new PathMatchingResourcePatternResolver();
    }

}
