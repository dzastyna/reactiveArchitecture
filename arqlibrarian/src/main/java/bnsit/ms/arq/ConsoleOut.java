package bnsit.ms.arq;

public class ConsoleOut implements UserOut {
    @Override
    public void print(String text) {
        System.out.print(text);
    }

    @Override
    public void printLine(String text) {
        System.out.println(text);
    }
}
