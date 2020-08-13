package com.dermentli.counters;

import com.dermentli.model.Model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

import static com.dermentli.constants.Constants.*;

public class FileLinesCounter implements LinesCounter {

    private final Path filePath;

    public FileLinesCounter(Path directoryPath) {
        validateFile(directoryPath);
        filePath = directoryPath;
    }

    private void validateFile(Path directoryPath) {
        if (!directoryPath.getFileName().toString().endsWith(".java")) {
            throw new IllegalArgumentException("Invalid java file: " + directoryPath.getFileName());
        }
    }

    @Override
    public Model countLines() {
        return Model.builder()
                .resource(filePath)
                .linesCount(countLinesForFile())
                .build();
    }

    int countLinesForFile() {
        int counter = 0;
        try (Scanner scanner = new Scanner(Files.newBufferedReader(filePath))) {
            boolean isBlockComment = false;

            while (scanner.hasNext()) {
                String line = scanner.nextLine().trim();

                if (line.startsWith(CODE_START)) {
                    isBlockComment = true;
                    continue;
                }

                // process block comments
                if (line.contains(CODE_START) && line.contains(CODE_END)) {
                    line = line.replaceAll(INLINE_BLOCK_COMMENT_REGEX, "");
                    isBlockComment = false;

                } else if (line.contains(CODE_END) && isBlockComment) {
                    line = line.replace(CODE_END, "");
                    isBlockComment = false;

                } else if (isBlockComment) {
                    continue;
                }

                // line comments
                if (line.contains(LINE_CODE)) {
                    line = line.substring(0, line.indexOf(LINE_CODE));
                }

                // count code results
                if (!line.isEmpty()) {
                    counter += 1;
                    //System.out.println(line);
                }
            }
        } catch (IOException e) {
            System.err.println("Exception during processing file: " + filePath);
        }
        return counter;
    }
}
