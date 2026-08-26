package codegen;

import ast.*;
import lexer.Lexer;
import lexer.Token;
import parser.Parser;
import semantic.SemanticAnalyzer;

import java.util.List;

public class CodeGeneratorTest {

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println("   BanglaX Code Generation Test");
        System.out.println("=================================");
        System.out.println();

        // =========================================
        // BanglaX Source Program
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
        // Lexical Analysis
        // =========================================

        Lexer lexer = new Lexer(source);

        List<Token> tokens = lexer.tokenize();

        System.out.println("Total Tokens: " + tokens.size());
        System.out.println();

        // =========================================
        // Syntax Analysis
        // =========================================

        Parser parser = new Parser(tokens);

        ProgramNode program = parser.parse();

        // =========================================
        // Semantic Analysis
        // =========================================

        SemanticAnalyzer semanticAnalyzer =
                new SemanticAnalyzer();

        semanticAnalyzer.analyze(program);

        System.out.println("Semantic Analysis: SUCCESS");
        System.out.println();

        // =========================================
        // Code Generation
        // =========================================

        CodeGenerator generator =
                new CodeGenerator();

        String generatedCode =
                generator.generate(program);

        // =========================================
        // Display Generated Code
        // =========================================

        System.out.println("Generated Intermediate Code:");
        System.out.println("---------------------------------");

        System.out.println(generatedCode);

        System.out.println("---------------------------------");
        System.out.println();

        System.out.println(
                "Code Generation Test Successful!"
        );

        System.out.println("=================================");
    }
}