import java.io.BufferedWriter;
import java.io.IOException;

public class CodeWriter {

    private BufferedWriter writer;
    private static String fileName;
    private String functionName;


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


    public CodeWriter(BufferedWriter writer, String fileName) throws IOException {
        this.writer = writer;
        this.fileName = fileName;
    }

    public void writeArithmetic(String command) throws IOException {
        String result = "";
        switch (command) {
            case "neg", "not" -> {
                result = oneImplicitOperandCommand(command);
            }
            case "add", "sub", "and", "or" -> {
                result = twoImplicitOperandCommand(command);
            }
            case "eq", "gt", "lt" -> {
                result = twoImplicitComparingCommand(command);
            }
        }
        writer.write(result);
    }

    private String oneImplicitOperandCommand(String command) {
        String template = """
            @SP
            AM=M-1
            M=%sM
            @SP
            M=M+1
            """;

        switch (command) {
            case "neg" -> {
                return template.formatted("-");
            }
            case "not" -> {
                return template.formatted("!");
            }
            default -> throw new IllegalArgumentException("Invalid command");
        }
    }

    private String twoImplicitOperandCommand(String command) {
        String template = """
            @SP
            AM=M-1
            D=M
            @SP
            AM=M-1
            M=M%sD
            @SP
            M=M+1
            """;

        switch (command) {
            case "add" -> {
                return template.formatted("+");
            }
            case "sub" -> {
                return template.formatted("-");
            }
            case "and" -> {
                return template.formatted("&");
            }
            case "or" -> {
                return template.formatted("|");
            }
            default -> throw new IllegalArgumentException("Invalid command");
        }
    }

    private String twoImplicitComparingCommand(String command) {
        String template = """
            @SP
            AM=M-1
            D=M
            @SP
            AM=M-1
            D=M-D
            @TRUE_%d
            D;%s
            @SP
            A=M
            M=0
            @CONTINUE_%d 
            0;JMP
            (TRUE_%d)
            @SP
            A=M
            M=-1
            (CONTINUE_%d)
            @SP
            M=M+1
            """;

        return template.formatted(
            command.hashCode(),
            switch (command) {
                case "eq" -> "JEQ";
                case "gt" -> "JGT";
                case "lt" -> "JLT";
                default -> throw new IllegalArgumentException("Invalid command");
            },
            command.hashCode(),
            command.hashCode(),
            command.hashCode()
        );
    }

    public void writePushPop(String command, String segment, int index) throws IOException {
        String result = "";
        switch (command) {
            case "push" -> {
                switch (segment) {
                    case "constant" -> {
                        result = pushConstant(index);
                    }
                    case "local", "argument", "this", "that" -> {
                        result = pushBase(segment, index);
                    }
                    case "temp" -> {
                        result = pushTemp(index);
                    }
                    case "pointer" -> {
                        result = pushPointer(index);
                    }
                    case "static" -> {
                        result = pushStatic(index);
                    }
                    default -> throw new IllegalArgumentException("Invalid segment");
                }
            }
            case "pop" -> {
                switch (segment) {
                    case "local", "argument", "this", "that" -> {
                        result = popBase(segment, index);
                    }
                    case "temp" -> {
                        result = popTemp(index);
                    }
                    case "pointer" -> {
                        result = popPointer(index);
                    }
                    case "static" -> {
                        result = popStatic(index);
                    }
                    default -> throw new IllegalArgumentException("Invalid segment");
                }
            }
        }
        writer.write(result);
    }

    private String pushConstant(int index) {
        return """
            @%d
            D=A
            @SP
            A=M
            M=D
            @SP
            M=M+1
            """.formatted(index);
    }

    private String pushBase(String segment, int index) {
        String template = """
            @%d
            D=A
            @%s
            A=M+D
            D=M
            @SP
            A=M
            M=D
            @SP
            M=M+1
            """;
        switch (segment) {
            case "local" -> {
                return template.formatted(index, "LCL");
            }
            case "argument" -> {
                return template.formatted(index, "ARG");
            }
            case "this" -> {
                return template.formatted(index, "THIS");
            }
            case "that" -> {
                return template.formatted(index, "THAT");
            }
            default -> throw new IllegalArgumentException("Invalid segment");
        }
    }

    private String pushTemp(int index) {
        return """
            @%d
            D=A
            @5
            A=A+D
            D=M
            @SP
            A=M
            M=D
            @SP
            M=M+1
            """.formatted(index);
    }

    private String pushPointer(int index) {
        String tmeplate = """
            @%d
            D=A
            @3
            A=A+D
            D=M
            @SP
            A=M
            M=D
            @SP
            M=M+1
            """.formatted(index);

        switch (index) {
            case 0 -> {
                return tmeplate.formatted(0);
            }
            case 1 -> {
                return tmeplate.formatted(1);
            }
            default -> throw new IllegalArgumentException("Invalid index");
        }
    }

    private String pushStatic(int index) {
        return """
            @%s.%d
            D=M
            @SP
            A=M
            M=D
            @SP
            M=M+1
            """.formatted(fileName, index);
    }

    private String popBase(String segment, int index) {
        String template = """
            @%d
            D=A
            @%s
            D=M+D
            @R13
            M=D
            @SP
            AM=M-1
            D=M
            @R13
            A=M
            M=D
            """;
        switch (segment) {
            case "local" -> {
                return template.formatted(index, "LCL");
            }
            case "argument" -> {
                return template.formatted(index, "ARG");
            }
            case "this" -> {
                return template.formatted(index, "THIS");
            }
            case "that" -> {
                return template.formatted(index, "THAT");
            }
            default -> throw new IllegalArgumentException("Invalid segment");
        }
    }

    private String popTemp(int index) {
        return """
            @%d
            D=A
            @5
            D=A+D
            @R13
            M=D
            @SP
            AM=M-1
            D=M
            @R13
            A=M
            M=D
            """.formatted(index);
    }

    private String popPointer(int index) {
        String template = """
            @%d
            D=A
            @3
            D=A+D
            @R13
            M=D
            @SP
            AM=M-1
            D=M
            @R13
            A=M
            M=D
            """;
        switch (index) {
            case 0 -> {
                return template.formatted(0);
            }
            case 1 -> {
                return template.formatted(1);
            }
            default -> throw new IllegalArgumentException("Invalid index");
        }
    }

    private String popStatic(int index) {
        return """
            @SP
            AM=M-1
            D=M
            @%s.%d
            M=D
            """.formatted(fileName, index);
    }

    public void writeLabel(String label) throws IOException {
        String labelInAssembly = functionName + "$" + label;
        writer.write(labelInAssembly);
    }

    public void writeGoTo(String label) throws IOException {
        String formattedLabel = functionName + "$" + label;
        String gotoInAssembly = "@" + formattedLabel + "\n0;JMP\n";
        writer.write(gotoInAssembly);
    }

    public void writeIf(String label) throws IOException {
        String template = """
            @SP
            AM=M-1
            D=M
            @%s
            D;JNE
            """.formatted(label);
        writer.write(template);

    }
}
