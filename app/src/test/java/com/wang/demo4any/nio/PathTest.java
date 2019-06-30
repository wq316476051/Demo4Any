package com.wang.demo4any.nio;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Path     代替 File
 * Paths    Path工具类，用于获取 Path
 */
public class PathTest {

    @Test
    public void test() throws IOException {
        Path currentDir = Paths.get(".");
        System.out.println(currentDir.toAbsolutePath());

        Path parentDir = Paths.get("..");
        System.out.println(parentDir.toAbsolutePath());
        Path realPath = parentDir.toRealPath();
        System.out.println("realPath = " + realPath);

        Path path = Paths.get("path1", "path2", "path3");
        System.out.println("path = " + path);

        File file = path.toFile();
        System.out.println("file = " + file);

        Path path1 = file.toPath();
        System.out.println("path1 = " + path1);
    }
}
