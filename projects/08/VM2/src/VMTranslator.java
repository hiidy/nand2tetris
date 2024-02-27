import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class VMTranslator {

    public static void main(String[] args) {
        String inputFileName = args[0];
        String outputFileName = inputFileName.substring(0, inputFileName.lastIndexOf(".")) + ".asm";
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFileName));
            BufferedWriter writer = new BufferedWriter(new FileWriter(outputFileName))) {

            Parser parser = new Parser(reader);
            CodeWriter codeWriter = new CodeWriter(writer, inputFileName);
            while (parser.hasMoreCommands()) {
                parser.advance();
                switch (parser.commandType()) {
                    case C_ARITHMETIC -> {
                        codeWriter.writeArithmetic(parser.arg1());
                    }
                    case C_PUSH -> {
                        codeWriter.writePushPop("push", parser.arg1(), parser.arg2());
                    }
                    case C_POP -> {
                        codeWriter.writePushPop("pop", parser.arg1(), parser.arg2());
                    }
                    case C_LABEL -> {
                        codeWriter.writeLabel(parser.arg1());
                    }
                    case C_GOTO -> {
                        codeWriter.writeGoTo(parser.arg1());
                    }
                    case C_IF -> {
                        codeWriter.writeIf(parser.arg1());
                    }
                    case C_FUNCTION -> {
                        codeWriter.writeFunction(parser.arg1(), parser.arg2());
                    }
                    case C_CALL -> {
                        codeWriter.writeCall(parser.arg1(), parser.arg2());
                    }
                    case C_RETURN -> {
                        codeWriter.writeReturn();
                    }
                }
            }


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
