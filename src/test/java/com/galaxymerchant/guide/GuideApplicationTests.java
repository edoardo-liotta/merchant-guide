package com.galaxymerchant.guide;

import com.galaxymerchant.guide.io.CommandFileManager;
import com.galaxymerchant.guide.io.InteractiveManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.*;
import java.util.Iterator;

@SpringBootTest
class GuideApplicationTests {

    @Mock
    private CommandFileManager commandFileManager = new CommandFileManager(null);

    @Mock
    private InteractiveManager interactiveManager = new InteractiveManager(null);

    @Mock
    private Path sourcePath = new FakePath();

    @Mock
    private Path targetPath = new FakePath();

    @InjectMocks
    private GuideApplication guideApplication = new GuideApplication();

    @BeforeEach
    public void beforeEachTest() {
        MockitoAnnotations.initMocks(this);
        Mockito.when(sourcePath.toAbsolutePath()).thenReturn(sourcePath);
        Mockito.when(targetPath.toAbsolutePath()).thenReturn(targetPath);
    }

    @Test
    public void testValidCommandLine() throws Exception {
        String source = "source";
        Mockito.when(commandFileManager.getPath(source)).thenReturn(sourcePath);
        Mockito.when(commandFileManager.parseFile(source)).thenReturn(targetPath);

        guideApplication.run("-f", source);
        Mockito.verify(commandFileManager).getPath(source);
        Mockito.verify(commandFileManager).parseFile(source);
    }

    @Test
    public void testInteractiveMode() throws Exception {
        guideApplication.run("-i");
        Mockito.verify(interactiveManager).run();
    }

    @Test
    void contextLoads() {
        //Empty on purpose
    }

    static class FakePath implements Path {
        @Override
        public FileSystem getFileSystem() {
            return null;
        }

        @Override
        public boolean isAbsolute() {
            return false;
        }

        @Override
        public Path getRoot() {
            return null;
        }

        @Override
        public Path getFileName() {
            return null;
        }

        @Override
        public Path getParent() {
            return null;
        }

        @Override
        public int getNameCount() {
            return 0;
        }

        @Override
        public Path getName(int index) {
            return null;
        }

        @Override
        public Path subpath(int beginIndex, int endIndex) {
            return null;
        }

        @Override
        public boolean startsWith(Path other) {
            return false;
        }

        @Override
        public boolean endsWith(Path other) {
            return false;
        }

        @Override
        public Path normalize() {
            return null;
        }

        @Override
        public Path resolve(Path other) {
            return null;
        }

        @Override
        public Path relativize(Path other) {
            return null;
        }

        @Override
        public URI toUri() {
            return null;
        }

        @Override
        public Path toAbsolutePath() {
            return null;
        }

        @Override
        public Path toRealPath(LinkOption... options) throws IOException {
            return null;
        }

        @Override
        public WatchKey register(WatchService watcher, WatchEvent.Kind<?>[] events, WatchEvent.Modifier... modifiers) throws IOException {
            return null;
        }

        @Override
        public int compareTo(Path other) {
            return 0;
        }

        @Override
        public boolean startsWith(String other) {
            return false;
        }

        @Override
        public boolean endsWith(String other) {
            return false;
        }

        @Override
        public Path resolve(String other) {
            return null;
        }

        @Override
        public Path resolveSibling(Path other) {
            return null;
        }

        @Override
        public Path resolveSibling(String other) {
            return null;
        }

        @Override
        public File toFile() {
            return null;
        }

        @Override
        public WatchKey register(WatchService watcher, WatchEvent.Kind<?>... events) throws IOException {
            return null;
        }

        @Override
        public Iterator<Path> iterator() {
            return null;
        }
    }
}
