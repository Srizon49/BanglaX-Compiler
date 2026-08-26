package parser;

import ast.ProgramNode;
import lexer.Lexer;
import lexer.Token;

import java.util.List;

public class ParserTest {

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println("   BanglaX Comprehensive Parser Test");
        System.out.println("=================================");

        String source =
                "সংখ্যা x = 25\n" +
                "দশমিক price = 99.50\n" +
                "দেখাও x + 10\n" +
                "যদি x >= 20\n" +
                "দেখাও x\n" +
                "নাহলে\n" +
                "দেখাও 0\n" +
                "শেষ\n" +
                "যতক্ষণ x < 30\n" +
                "x = x + 1\n" +
                "শেষ\n";

        // =================================
        // STEP 1: LEXER
        // =================================

        Lexer lexer = new Lexer(source);

        List<Token> tokens = lexer.tokenize();

        System.out.println();
        System.out.println("Total Tokens: " + tokens.size());

        // =================================
        // STEP 2: PARSER
        // =================================

        Parser parser = new Parser(tokens);

        ProgramNode program = parser.parse();

        // =================================
        // STEP 3: AST
        // =================================

        System.out.println();
        System.out.println("AST Output:");
        System.out.println("---------------------------------");

        System.out.print(program.print());

        System.out.println("---------------------------------");
        System.out.println("Parser Test Successful!");
        System.out.println("=================================");
    }
}