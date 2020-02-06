package com.galaxymerchant.guide.io;

import com.galaxymerchant.guide.interpreter.GuideInterpreterStrategy;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class CommandFileManager {

    private final GuideInterpreterStrategy interpreterStrategy;

    public CommandFileManager(GuideInterpreterStrategy interpreterStrategy) {
        this.interpreterStrategy = interpreterStrategy;
    }

    public Path parseFile(String path) throws IOException {
        Path sourcePath = getPath(path);
        Path targetPath = Paths.get(sourcePath.getParent().toString(), FilenameUtils.removeExtension(sourcePath.getFileName().toString()) + "-result.txt");
        try (Stream<String> lines = Files.lines(sourcePath)) {
            List<String> output = lines.filter(StringUtils::isNotEmpty).map(interpreterStrategy::execute).filter(StringUtils::isNotEmpty).collect(Collectors.toList());
            Files.write(targetPath, output);
        }
        return targetPath;
    }

    public Path getPath(String path) throws IOException {
        File resource = ResourceUtils.getFile(path.replaceFirst("^~", System.getProperty("user.home")));
        return Paths.get(resource.getAbsolutePath()).normalize();
    }
}
