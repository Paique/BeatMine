package net.paiique.helper;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class RemoveFolder extends SimpleFileVisitor<Path> {

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attributes) throws IOException {
        Files.delete(file);
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
        Files.delete(dir);
        return FileVisitResult.CONTINUE;
    }

    public static void remove(String path) throws IOException {
        Path sourceDir = Paths.get(path);
        Files.walkFileTree(sourceDir, new RemoveFolder());
    }

    public static void remove(Path path) throws IOException {
        Files.walkFileTree(path, new RemoveFolder());
    }

    public static void remove(File file) throws IOException {
        Path path = file.toPath();
        Files.walkFileTree(path, new RemoveFolder());
    }

}
