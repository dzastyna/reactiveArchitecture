package bnsit.ms.arq;

import org.junit.Assert;

import java.util.ArrayList;
import java.util.List;

public class SpyUserOut implements InputAware, UserOut {

    private final List<String> history = new ArrayList<String>();

    public SpyUserOut() {
        history.add("");
    }

    @Override
    public void onTextLineEntered(String text) {
        printLine(text);
    }

    @Override
    public void print(String text) {
        int lastElement = history.size() - 1;
        history.set(lastElement, history.get(lastElement) + text);
    }

    @Override
    public void printLine(String text) {
        print(text);
        history.add("");
    }


    public void assertContains(String expression) {
        if (history.size() == 0)
        {
            Assert.fail("No console entries at all. Especially: " + expression);
        }

        if (history.stream().filter(h -> h.matches(".*" + expression + ".*")).count() == 0) {
            Assert.fail(String.format("%sUI does not contain '%s'.%s%s", newLine(), expression, newLine(), formatHistory()));

        }
    }

    public void assertContainsAtLeastLines(int expectedCount) {
        if (history.size() < expectedCount)
        {
            Assert.fail(String.format("%sUI contains less than %d lines%s%s", newLine(), expectedCount, newLine(), formatHistory()));
        }
    }

    private String formatHistory()
    {
        return String.format("History:%s=======%s%s", newLine(), newLine(), joinedHistory());
    }

    private String joinedHistory() {
        return String.join(newLine(), history);
    }

    private String newLine() {
        return System.getProperty("line.separator");
    }
}
