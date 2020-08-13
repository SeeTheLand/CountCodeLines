package com.dermentli;

import com.dermentli.counters.FileLinesCounter;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class FileLinesCounterTest {

    private FileLinesCounter fileLinesCounter;

    private String path;
    private Integer lines;

    @Parameterized.Parameters
    public static Collection inputs() {
        return Arrays.asList(new Object[][]{
                {"src/test/resources/testNoComments.java", 5},
                {"src/test/resources/testWithBlancLines.java", 7},
                {"src/test/resources/testWithComments.java", 7},
                {"src/test/resources/blancFile.java", 0},
                {"src/test/resources/testCommentInsideString.java", 7},
                {"src/test/resources/testQuotesEcran.java", 7}
        });
    }

    public FileLinesCounterTest(String path,  Integer lines) {
        this.path = path;
        this.lines = lines;
    }

    @Test
    public void test_case() {
        Path filePath = Paths.get(path);
        fileLinesCounter = new FileLinesCounter(filePath);
        int numberOfLines = fileLinesCounter.countLines().getLinesCount();
        Assertions.assertThat(numberOfLines).isEqualTo(lines);
    }

}