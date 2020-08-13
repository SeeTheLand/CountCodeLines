package com.dermentli;

import com.dermentli.counters.DirectoryLinesCounter;
import com.dermentli.counters.FileLinesCounter;
import com.dermentli.counters.LinesCounter;
import com.dermentli.writer.ConsoleWriter;
import com.dermentli.writer.Writer;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
public class Main {

    public static void main(String[] args) throws IOException {
        if (args.length != 1 || args[0].isEmpty()) {
            System.out.println("Please provide the proper file or path.");
        }
        String fileName = args[0];
        LinesCounter counterType = getCounter(fileName);

        log.info("Filename is {}", fileName);
        Writer writer = new ConsoleWriter();
        writer.write(System.out, counterType);
    }

    private static LinesCounter getCounter(String filename) {
        if (filename == null || !Files.exists(Paths.get(filename))) {
            throw new IllegalArgumentException("File you provided not valid or path is wrong.");
        }
        Path path = Paths.get(filename);
        if (Files.isDirectory(path)) {
            return new DirectoryLinesCounter(path);
        } else if (Files.isRegularFile(path)) {
            return new FileLinesCounter(path);
        }
        else throw new IllegalArgumentException(String.format("Your location is wrong: %s", filename));
    }
}
