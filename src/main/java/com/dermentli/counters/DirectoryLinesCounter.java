package com.dermentli.counters;

import com.dermentli.model.Model;
import lombok.Data;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Data
public class DirectoryLinesCounter implements LinesCounter {
    private final Path directoryPath;
    private List<? extends LinesCounter> subFolders;

    @Override
    public Model countLines() {
        Model stats;
        if (Files.isDirectory(directoryPath)) {

            List<Model> subResourcesResults = subFolders.stream()
                    .map(LinesCounter::countLines)
                    .collect(Collectors.toList());

            int total = subResourcesResults.stream()
                    .map(Model::getLinesCount)
                    .reduce(0, Integer::sum);

            stats = Model.builder()
                    .resource(directoryPath)
                    .linesCount(total)
                    .subResources(subResourcesResults)
                    .build();
        } else {
            stats = Model.builder()
                    .resource(directoryPath)
                    .linesCount(new FileLinesCounter(directoryPath).countLinesForFile())
                    .build();
        }

        return stats;
    }

    public DirectoryLinesCounter(Path path) {
        directoryPath = path;
        if (Files.isDirectory(directoryPath)) {
            collectSubResources(path);
        }
    }

    private void collectSubResources(Path resource) {
        try (Stream<Path> entries = Files.list(resource)) {
            subFolders = entries
                    .map(DirectoryLinesCounter::new)
                    .sorted(Comparator.comparing(d -> d.directoryPath))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            System.err.println("Exception while traversing sub resources: " + e.getMessage());
        }
    }


}

