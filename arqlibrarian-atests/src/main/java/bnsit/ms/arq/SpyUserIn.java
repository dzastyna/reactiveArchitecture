package bnsit.ms.arq;

import java.util.LinkedList;
import java.util.Queue;

public class SpyUserIn implements UserIn {
    private final InputAware input;
    private final Queue<String> entered = new LinkedList<String>();

    public SpyUserIn(InputAware input)
    {
        this.input = input;
    }

    public void enterLine(String text) {
        entered.add(text);
        input.onTextLineEntered(text);
    }

    @Override
    public String readLine() {
        return entered.poll();
    }
}
