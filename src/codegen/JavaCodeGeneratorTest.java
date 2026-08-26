package codegen;

import ast.*;
import lexer.Lexer;
import lexer.Token;
import parser.Parser;
import semantic.SemanticAnalyzer;

import java.util.List;

public class JavaCodeGeneratorTest {

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println(" BanglaX Java Code Generator Test");
        System.out.println("=================================");
        System.out.println();

        // =========================================
        // BanglaX Source
        // =========================================

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

        // =========================================
        // Lexer
        // =========================================

        Lexer lexer = new Lexer(source);

        List<Token> tokens =
                lexer.tokenize();

        System.out.println(
                "Total Tokens: "
                        + tokens.size()
        );

        System.out.println();

        // =========================================
        // Parser
        // =========================================

        Parser parser =
                new Parser(tokens);

        ProgramNode program =
                parser.parse();

        // =========================================
        // Semantic Analysis
        // =========================================

        SemanticAnalyzer analyzer =
                new SemanticAnalyzer();

        analyzer.analyze(program);

        System.out.println(
                "Semantic Analysis: SUCCESS"
        );

        System.out.println();

        // =========================================
        // Java Code Generation
        // =========================================

        JavaCodeGenerator generator =
                new JavaCodeGenerator();

        String javaCode =
                generator.generate(program);

        // =========================================
        // Display Java Code
        // =========================================

        System.out.println(
                "Generated Java Code:"
        );

        System.out.println(
                "---------------------------------"
        );

        System.out.println(javaCode);

        System.out.println(
                "---------------------------------"
        );

        System.out.println();

        System.out.println(
                "Java Code Generation Successful!"
        );

        System.out.println(
                "================================="
        );
    }
}