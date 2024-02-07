import java.io.BufferedReader;
import java.io.IOException;

public class Parser {

    private String currentLine;
    private String nextLine;
    private BufferedReader reader;

    public enum CommandType {
        A_COMMAND,
        C_COMMAND,
        L_COMMAND
    }

    public Parser(BufferedReader input) {
        this.reader = input;
        this.currentLine = null;
    }

    public boolean hasMoreCommands() throws IOException {
        do {
            nextLine = reader.readLine();
        } while (nextLine != null && (nextLine.trim().isEmpty() || nextLine.trim().startsWith("//")));

        if (nextLine == null) {
            return false;
        }
        return true;
    }

    public void advance() {
        currentLine = nextLine.trim();
    }


    public CommandType commandType() {
        if (currentLine.startsWith("@")) {
            return CommandType.A_COMMAND;
        } else if (currentLine.startsWith("(") && currentLine.endsWith(")")) {
            return CommandType.L_COMMAND;
        } else {
            return CommandType.C_COMMAND;
        }
    }

    public String symbol() throws IllegalArgumentException {
        if (commandType() == CommandType.A_COMMAND) {
            return currentLine.substring(1);
        } else if (commandType() == CommandType.L_COMMAND) {
            return currentLine.substring(1, currentLine.length() - 1);
        } else {
            throw new IllegalStateException("invalid CommandType");
        }
    }


    public String dest() {
        if (commandType() != CommandType.C_COMMAND) {
            throw new IllegalStateException("CommandType should be C_COMMAND");
        }
        if (currentLine.contains("=")) {
            return currentLine.split("=")[0];
        }
        return "null";
    }

    public String comp() {
        if (commandType() != CommandType.C_COMMAND) {
            throw new IllegalStateException("CommandType should be C_COMMAND");
        }
        if (currentLine.contains("=")) {
            return currentLine.split("=")[1];
        } else {
            return currentLine.split(";")[0];
        }
    }

    public String jump() {
        if (commandType() != CommandType.C_COMMAND) {
            throw new IllegalStateException("CommandType should be C_COMMAND");
        }
        if (currentLine.contains(";")) {
            return currentLine.split(";")[1];
        } else {
            return "null";
        }
    }
}
