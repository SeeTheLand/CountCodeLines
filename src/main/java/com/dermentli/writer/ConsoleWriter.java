package com.dermentli.writer;

import com.dermentli.counters.LinesCounter;
import com.dermentli.model.Model;

import java.io.PrintStream;
import java.util.Objects;

public class ConsoleWriter implements Writer {
    private final StringBuilder sb = new StringBuilder();

    @Override
    public void write(PrintStream out, LinesCounter linesCounter) {
        formatConsoleOutput("", linesCounter.countLines());
        out.print(sb.toString());
    }

    private void formatConsoleOutput(String tab, Model stats) {
        sb.append(String.format("%s%s : %s%n", tab,
                stats.getResource().getFileName(),
                stats.getLinesCount()));

        if (stats.getSubResources() != null) {
            stats.getSubResources().stream()
                    .filter(Objects::nonNull)
                    .forEach(res -> formatConsoleOutput(tab.concat("  "), res));
        }
    }
}

