import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Assembler {

    public static void main(String[] args) {
        String inputFileName = args[0];
        String outputFIleName =
            inputFileName.substring(0, inputFileName.lastIndexOf(".")) + ".hack";

        try (BufferedReader reader = new BufferedReader(
            new FileReader(inputFileName)); BufferedWriter writer = new BufferedWriter(
            new FileWriter(outputFIleName))) {

            Parser parser = new Parser(reader);

            while (parser.hasMoreCommands()) {
                parser.advance();

                switch (parser.commandType()) {
                    case C_COMMAND -> {
                        String dest = Code.dest(parser.dest());
                        String jump = Code.jump(parser.jump());
                        String comp = Code.comp(parser.comp());
                        String binaryCommand = "111" + comp + dest + jump + "\n";
                        writer.write(binaryCommand);
                        break;
                    }

                    case A_COMMAND -> {
                        String symbol = parser.symbol();
                        int intSymbol = Integer.parseInt(symbol);
                        String binaryCommand = Integer.toBinaryString(intSymbol);

                        String formattedBinaryCommand = ("000000000000000"
                            + binaryCommand).substring(binaryCommand.length());

                        writer.write("0" + formattedBinaryCommand + '\n');
                        break;
                    }
                }
            }

        } catch (IOException ioe) {
            ioe.printStackTrace();
            System.out.println("Exception : " + ioe.getMessage());
        }
    }
}

