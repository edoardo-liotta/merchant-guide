package com.galaxymerchant.guide.io;

import com.galaxymerchant.guide.interpreter.GuideInterpreterStrategy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CommandFileManagerTests {

    @Mock
    private GuideInterpreterStrategy interpreterStrategy = new GuideInterpreterStrategy();

    @InjectMocks
    private CommandFileManager commandFileManager;

    @Test
    public void testTilde() throws IOException {
        commandFileManager = new CommandFileManager(null);

        String pathAfterTilde = "/testpath.txt";
        Path path = commandFileManager.getPath("~" + pathAfterTilde);
        Assertions.assertEquals(System.getProperty("user.home") + pathAfterTilde, path.toString());
    }

    @Test
    public void testParsing() throws IOException {
        MockitoAnnotations.initMocks(this);
        commandFileManager = new CommandFileManager(interpreterStrategy);

        String path = "classpath:test.txt";
        File resource = ResourceUtils.getFile(path);
        Path resourcePath = Paths.get(resource.getAbsolutePath());

        try (Stream<String> lines = Files.lines(resourcePath)) {
            long count = lines.count();
            commandFileManager.parseFile(path);
            Mockito.verify(interpreterStrategy, Mockito.times(Math.toIntExact(count))).execute(Mockito.anyString());
        }
    }

    @SpringBootTest

    static class IntegrationTests {

        @Autowired
        private CommandFileManager commandFileManager;

        @Test
        @DirtiesContext
        public void test() throws IOException {
            Path actualPath = commandFileManager.parseFile("classpath:test.txt");
            List<String> actualLines = Files.lines(actualPath).collect(Collectors.toList());
            List<String> expectedLines = Files.lines(Paths.get(ResourceUtils.getFile("classpath:expected.txt").toURI())).collect(Collectors.toList());
            Assertions.assertEquals(expectedLines, actualLines);
        }
    }
}
