package com.galaxymerchant.guide;

import com.galaxymerchant.guide.io.CommandFileManager;
import com.galaxymerchant.guide.io.InteractiveManager;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;

@SpringBootApplication
public class GuideApplication implements CommandLineRunner {

    private static final String FILE_OPTION = "f";
    private static final String INTERACTIVE_OPTION = "i";

    @Resource(name = "commandFileManager")
    private CommandFileManager commandFileManager;

    @Resource(name = "interactiveManager")
    private InteractiveManager interactiveManager;

    public static void main(String[] args) {
        SpringApplication.run(GuideApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Options options = new Options();
        options.addOption(FILE_OPTION, true, "the command file to parse");
        options.addOption(INTERACTIVE_OPTION, "runs as interactive");

        CommandLine cmd = new DefaultParser().parse(options, args);
        if (cmd.hasOption(FILE_OPTION)) {
            String path = cmd.getOptionValue(FILE_OPTION);
            try {
                Path sourcePath = commandFileManager.getPath(path);
                System.out.println("Parsing file " + sourcePath + " ...");
                Path targetPath = commandFileManager.parseFile(path);
                System.out.println("File parsed. You can find the results in " + targetPath.toAbsolutePath());
            } catch (FileNotFoundException | NoSuchFileException e) {
                System.out.println("Couldn't find file at path " + path + " , due to " + e.getClass().getCanonicalName() + ": " + e.getMessage());
                System.exit(1);
            } catch (IOException e) {
                System.out.println("I/O exception occurred while accessing path " + path + " , due to " + e.getClass().getCanonicalName() + ": " + e.getMessage());
                System.exit(2);
            }
        }

        if (cmd.hasOption(INTERACTIVE_OPTION)) {
            interactiveManager.run();
        }
    }
}
