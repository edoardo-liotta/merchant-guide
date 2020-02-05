package com.galaxymerchant.guide.interpreter;

import com.galaxymerchant.guide.converter.IntergalacticConverter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
public class GuideInterpreterStrategyTests {

    @Autowired
    private GuideInterpreterStrategy guideInterpreterStrategy;

    @Autowired
    private IntergalacticConverter intergalacticConverter;

    @BeforeEach
    public void beforeEachTest() {
        intergalacticConverter.reset();
    }

    @Test
    public void romanAssignmentTest() {
        Optional<GuideInterpreter> command = guideInterpreterStrategy.getInterpreterForCommand("glob means I");
        Assertions.assertTrue(command.isPresent());
    }

    @Test
    public void creditAssignmentTest() {
        Optional<GuideInterpreter> command = guideInterpreterStrategy.getInterpreterForCommand("glob glob units of Silver are worth 34 Credits");
        Assertions.assertTrue(command.isPresent());
    }

    @Test
    public void numeralQueryTest() {
        Optional<GuideInterpreter> command = guideInterpreterStrategy.getInterpreterForCommand("how much is pish tegj glob glob ?");
        Assertions.assertTrue(command.isPresent());
    }

    @Test
    public void creditQueryTest() {
        Optional<GuideInterpreter> command = guideInterpreterStrategy.getInterpreterForCommand("how many Credits is glob prok Silver ?");
        Assertions.assertTrue(command.isPresent());
    }

    @Test
    public void invalidQueryTest() {
        String commandString = "how much wood could a woodchuck chuck if a woodchuck could chuck wood ?";
        Optional<GuideInterpreter> command = guideInterpreterStrategy.getInterpreterForCommand(commandString);
        Assertions.assertFalse(command.isPresent());
        String result = guideInterpreterStrategy.execute(commandString);
        Assertions.assertEquals("I have no idea what you are talking about", result);
    }
}
