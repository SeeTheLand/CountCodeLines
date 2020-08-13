package com.dermentli.writer;

import com.dermentli.counters.LinesCounter;

import java.io.PrintStream;

@FunctionalInterface
public interface Writer {
    void write (PrintStream out, LinesCounter linesCounter);
}
