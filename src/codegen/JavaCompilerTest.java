package codegen;

import ast.ProgramNode;
import lexer.Lexer;
import lexer.Token;
import parser.Parser;
import semantic.SemanticAnalyzer;

import java.io.File;
import java.util.List;

public class JavaCompilerTest {

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println("   BanglaX Full Compilation Test");
        System.out.println("=================================");
        System.out.println();

        try {

            // =========================================
            // BanglaX Source Code
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

            System.out.println(
                    "Source Program:"
            );

            System.out.println(
                    "---------------------------------"
            );

            System.out.println(source);

            System.out.println(
                    "---------------------------------"
            );

            // =========================================
            // LEXICAL ANALYSIS
            // =========================================

            Lexer lexer =
                    new Lexer(source);

            List<Token> tokens =
                    lexer.tokenize();

            System.out.println(
                    "Lexical Analysis: SUCCESS"
            );

            System.out.println(
                    "Total Tokens: "
                            + tokens.size()
            );

            System.out.println();

            // =========================================
            // PARSING
            // =========================================

            Parser parser =
                    new Parser(tokens);

            ProgramNode program =
                    parser.parse();

            System.out.println(
                    "Parsing: SUCCESS"
            );

            System.out.println();

            // =========================================
            // SEMANTIC ANALYSIS
            // =========================================

            SemanticAnalyzer analyzer =
                    new SemanticAnalyzer();

            analyzer.analyze(program);

            System.out.println(
                    "Semantic Analysis: SUCCESS"
            );

            System.out.println();

            // =========================================
            // JAVA CODE GENERATION
            // =========================================

            JavaCodeGenerator generator =
                    new JavaCodeGenerator();

            String javaCode =
                    generator.generate(program);

            System.out.println(
                    "Java Code Generation: SUCCESS"
            );

            System.out.println();

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

            // =========================================
            // CREATE JAVA FILE
            // =========================================

            File generatedFile =
                    new File(
                            "GeneratedProgram.java"
                    );

            try (java.io.FileWriter writer =
                         new java.io.FileWriter(
                                 generatedFile)) {

                writer.write(javaCode);
            }

            System.out.println(
                    "Generated File:"
            );

            System.out.println(
                    generatedFile
                            .getAbsolutePath()
            );

            System.out.println();

            // =========================================
            // COMPILE JAVA FILE
            // =========================================

            JavaCompiler compiler =
                    new JavaCompiler();

            System.out.println(
                    "Compiling GeneratedProgram.java..."
            );

            boolean compiled =
                    compiler.compile(
                            generatedFile
                    );

            if (!compiled) {

                System.out.println();
                System.out.println(
                        "Java Compilation FAILED!"
                );

                return;
            }

            System.out.println(
                    "Java Compilation: SUCCESS"
            );

            System.out.println();

            // =========================================
            // RUN PROGRAM
            // =========================================

            System.out.println(
                    "Running Generated Program:"
            );

            System.out.println(
                    "---------------------------------"
            );

            int exitCode =
                    compiler.run(
                            generatedFile
                    );

            System.out.println(
                    "---------------------------------"
            );

            System.out.println();

            if (exitCode == 0) {

                System.out.println(
                        "Program Execution: SUCCESS"
                );

            } else {

                System.out.println(
                        "Program Execution: FAILED"
                );
            }

            System.out.println();

            System.out.println(
                    "================================="
            );

            System.out.println(
                    " Full Compilation Test Successful!"
            );

            System.out.println(
                    "================================="
            );

        } catch (Exception e) {

            System.out.println();
            System.out.println(
                    "Compilation Error:"
            );

            e.printStackTrace();
        }
    }
}