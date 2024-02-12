import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Assembler {

    public static void main(String[] args) {
        String inputFileName = args[0];
        String outputFileName =
            inputFileName.substring(0, inputFileName.lastIndexOf(".")) + ".hack";

        try (BufferedReader reader1 = new BufferedReader(new FileReader(inputFileName));
            BufferedReader reader2 = new BufferedReader(new FileReader(inputFileName));
            BufferedWriter writer = new BufferedWriter(new FileWriter(outputFileName))) {

            Parser parser1 = new Parser(reader1);
            Parser parser2 = new Parser(reader2);

            int address = 0;
            while (parser1.hasMoreCommands()) {
                parser1.advance();
                switch (parser1.commandType()) {
                    case A_COMMAND, C_COMMAND -> address++;
                    case L_COMMAND -> {
                        String symbol = parser1.symbol();
                        SymbolTable.addEntry(symbol, address);
                    }
                }
            }
            reader1.close();

            address = 16;

            while (parser2.hasMoreCommands()) {
                parser2.advance();

                switch (parser2.commandType()) {
                    case C_COMMAND -> {
                        String dest = Code.dest(parser2.dest());
                        String jump = Code.jump(parser2.jump());
                        String comp = Code.comp(parser2.comp());
                        String binaryCommand = "111" + comp + dest + jump + "\n";
                        writer.write(binaryCommand);
                    }

                    case A_COMMAND -> {
                        String symbol = parser2.symbol();
                        if (symbol.matches("[0-9]+")) {
                            int intSymbol = Integer.parseInt(symbol);
                            String binaryCommand = Integer.toBinaryString(intSymbol);

                            String formattedBinaryCommand = ("000000000000000"
                                + binaryCommand).substring(binaryCommand.length());

                            writer.write("0" + formattedBinaryCommand + '\n');
                        } else {
                            if (SymbolTable.contains(symbol)) {
                                String binaryCommand = SymbolTable.getAddress(symbol);

                                String formattedBinaryCommand = ("000000000000000"
                                    + binaryCommand).substring(binaryCommand.length());
                                writer.write("0" + formattedBinaryCommand + '\n');
                            } else {
                                SymbolTable.addEntry(symbol, address);
                                String binaryCommand = SymbolTable.getAddress(symbol);

                                String formattedBinaryCommand = ("000000000000000"
                                    + binaryCommand).substring(binaryCommand.length());
                                writer.write("0" + formattedBinaryCommand + '\n');
                                address++;
                            }
                        }
                    }
                }
            }

        } catch (IOException ioe) {
            ioe.printStackTrace();
            System.out.println("Exception : " + ioe.getMessage());
        }

    }
}

