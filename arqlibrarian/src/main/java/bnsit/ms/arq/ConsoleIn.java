package bnsit.ms.arq;

import java.util.Scanner;

public class ConsoleIn implements UserIn {
    private Scanner scanner = new Scanner(System.in);

    @Override
    public String readLine() {
        String result = scanner.nextLine().trim();
        // Console in buffers enter, so we swallow it
        // scanner.nextLine().trim()
        return result;
    }
}
