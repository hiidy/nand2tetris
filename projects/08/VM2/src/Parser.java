import java.io.BufferedReader;
import java.io.IOException;

public class Parser {

    private String currentLine;
    private String nextLine;
    private BufferedReader reader;

    public enum CommandType {
        C_ARITHMETIC,
        C_PUSH,
        C_POP,
        C_LABEL,
        C_GOTO,
        C_IF,
        C_FUNCTION,
        C_RETURN,
        C_CALL
    }

    public Parser(BufferedReader input) {
        this.reader = input;
        this.currentLine = null;
    }

    public boolean hasMoreCommands() throws IOException {
        do {
            nextLine = reader.readLine();
        } while (nextLine != null && (nextLine.trim().isEmpty() || nextLine.trim()
            .startsWith("//")));

        if (nextLine == null) {
            return false;
        }
        return true;
    }

    public void advance() {
        currentLine = nextLine.trim();
    }


    public CommandType commandType() {
        switch (currentLine.split(" ")[0]) {
            case "add", "sub", "neg", "eq", "gt", "lt", "and", "or", "not" -> {
                return CommandType.C_ARITHMETIC;
            }
            case "push" -> {
                return CommandType.C_PUSH;
            }
            case "pop" -> {
                return CommandType.C_POP;
            }
            case "label" -> {
                return CommandType.C_LABEL;
            }
            case "goto" -> {
                return CommandType.C_GOTO;
            }
            case "if-goto" -> {
                return CommandType.C_IF;
            }
            case "function" -> {
                return CommandType.C_FUNCTION;
            }
            case "return" -> {
                return CommandType.C_RETURN;
            }
            case "call" -> {
                return CommandType.C_CALL;
            }
            default -> throw new IllegalArgumentException("Invalid command type");
        }
    }

    public String arg1() {
        if (commandType() == CommandType.C_POP || commandType() == CommandType.C_PUSH) {
            return currentLine.split(" ")[1];
        } else {
            return currentLine.split(" ")[0];
        }
    }

    public int arg2() {
        if (commandType() == CommandType.C_POP || commandType() == CommandType.C_PUSH) {
            return Integer.parseInt(currentLine.split(" ")[2].trim());
        }
        throw new IllegalArgumentException("Invalid Command Type");
    }

}
