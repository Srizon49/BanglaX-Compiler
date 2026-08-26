import ast.ProgramNode;
import codegen.JavaCodeGenerator;
import lexer.Lexer;
import lexer.Token;
import parser.Parser;
import semantic.SemanticAnalyzer;
import error.CompilerError;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        printHeader();

        try {

            // =========================================
            // CHECK SOURCE FILE
            // =========================================

            if (args.length == 0) {

                System.out.println("Usage:");
                System.out.println(
                        "java -cp out Main <source.bx>"
                );

                System.out.println();

                System.out.println("Example:");
                System.out.println(
                        "java -cp out Main program.bx"
                );

                return;
            }

            // =========================================
            // READ SOURCE FILE
            // =========================================

            String fileName = args[0];

            File sourceFile =
                    new File(fileName);

            if (!sourceFile.exists()) {

                throw new CompilerError(
                        "Source file not found: "
                                + fileName,
                        0
                );
            }

            if (!sourceFile.isFile()) {

                throw new CompilerError(
                        "Source path is not a file: "
                                + fileName,
                        0
                );
            }

            if (!fileName.toLowerCase().endsWith(".bx")) {

                System.out.println(
                        "Warning: Source file should have .bx extension."
                );

                System.out.println();
            }

            String source =
                    Files.readString(
                            Path.of(fileName)
                    );

            System.out.println(
                    "Source File: "
                            + sourceFile.getAbsolutePath()
            );

            System.out.println();

            // =========================================
            // STEP 1: LEXICAL ANALYSIS
            // =========================================

            System.out.println(
                    "[1] Lexical Analysis..."
            );

            Lexer lexer =
                    new Lexer(source);

            List<Token> tokens =
                    lexer.tokenize();

            System.out.println(
                    "    SUCCESS"
            );

            System.out.println(
                    "    Total Tokens: "
                            + tokens.size()
            );

            System.out.println();

            // =========================================
            // STEP 2: PARSING
            // =========================================

            System.out.println(
                    "[2] Parsing..."
            );

            Parser parser =
                    new Parser(tokens);

            ProgramNode program =
                    parser.parse();

            System.out.println(
                    "    SUCCESS"
            );

            System.out.println();

            // =========================================
            // STEP 3: AST GENERATION
            // =========================================

            System.out.println(
                    "[3] AST Generation..."
            );

            System.out.println(
                    "    SUCCESS"
            );

            System.out.println();

            // =========================================
            // STEP 4: SEMANTIC ANALYSIS
            // =========================================

            System.out.println(
                    "[4] Semantic Analysis..."
            );

            SemanticAnalyzer analyzer =
                    new SemanticAnalyzer();

            analyzer.analyze(program);

            System.out.println(
                    "    SUCCESS"
            );

            System.out.println();

            // =========================================
            // STEP 5: JAVA CODE GENERATION
            // =========================================

            System.out.println(
                    "[5] Java Code Generation..."
            );

            JavaCodeGenerator generator =
                    new JavaCodeGenerator();

            String javaCode =
                    generator.generate(program);

            System.out.println(
                    "    SUCCESS"
            );

            System.out.println();

            // =========================================
            // CREATE GENERATED JAVA FILE
            // =========================================

            File generatedFile =
                    new File(
                            "GeneratedProgram.java"
                    );

            try (FileWriter writer =
                         new FileWriter(
                                 generatedFile
                         )) {

                writer.write(javaCode);
            }

            System.out.println(
                    "Generated Java File:"
            );

            System.out.println(
                    "    "
                            + generatedFile
                                    .getAbsolutePath()
            );

            System.out.println();

            // =========================================
            // STEP 6: JAVA COMPILATION
            // =========================================

            System.out.println(
                    "[6] Java Compilation..."
            );

            ProcessBuilder compileProcess =
                    new ProcessBuilder(
                            "javac",
                            generatedFile
                                    .getAbsolutePath()
                    );

            compileProcess.inheritIO();

            Process compiler =
                    compileProcess.start();

            int compileResult =
                    compiler.waitFor();

            if (compileResult != 0) {

                System.out.println();

                System.out.println(
                        "    Java Compilation: FAILED"
                );

                System.out.println();

                System.out.println(
                        "Compilation stopped because "
                                + "the generated Java code "
                                + "contains errors."
                );

                return;
            }

            System.out.println(
                    "    SUCCESS"
            );

            System.out.println();

            // =========================================
            // STEP 7: PROGRAM EXECUTION
            // =========================================

            System.out.println(
                    "[7] Program Execution..."
            );

            System.out.println(
                    "---------------------------------"
            );

            File parent =
                    generatedFile
                            .getAbsoluteFile()
                            .getParentFile();

            ProcessBuilder runProcess =
                    new ProcessBuilder(
                            "java",
                            "-cp",
                            parent.getAbsolutePath(),
                            "GeneratedProgram"
                    );

            runProcess.inheritIO();

            Process programProcess =
                    runProcess.start();

            int runResult =
                    programProcess.waitFor();

            System.out.println(
                    "---------------------------------"
            );

            System.out.println();

            if (runResult != 0) {

                System.out.println(
                        "    Program Execution: FAILED"
                );

                return;
            }

            System.out.println(
                    "    Program Execution: SUCCESS"
            );

            System.out.println();

            // =========================================
            // FINAL SUCCESS
            // =========================================

            System.out.println(
                    "================================="
            );

            System.out.println(
                    "     Compilation Successful!"
            );

            System.out.println(
                    "================================="
            );

        } catch (CompilerError e) {

            // =========================================
            // COMPILER ERROR
            // =========================================

            System.out.println();

            System.out.println(
                    "================================="
            );

            System.out.println(
                    "       Compilation Failed!"
            );

            System.out.println(
                    "================================="
            );

            System.out.println();

            System.out.println(
                    "Compiler Error:"
            );

            System.out.println(
                    "    " + e.getMessage()
            );

            System.out.println();

            System.out.println(
                    "Compilation stopped."
            );

        } catch (RuntimeException e) {

            // =========================================
            // PARSER / SEMANTIC / OTHER RUNTIME ERROR
            // =========================================

            System.out.println();

            System.out.println(
                    "================================="
            );

            System.out.println(
                    "       Compilation Failed!"
            );

            System.out.println(
                    "================================="
            );

            System.out.println();

            System.out.println(
                    "Compiler Error:"
            );

            System.out.println(
                    "    " + e.getMessage()
            );

            System.out.println();

            System.out.println(
                    "Compilation stopped."
            );

        } catch (Exception e) {

            // =========================================
            // UNEXPECTED ERROR
            // =========================================

            System.out.println();

            System.out.println(
                    "================================="
            );

            System.out.println(
                    "       Compilation Failed!"
            );

            System.out.println(
                    "================================="
            );

            System.out.println();

            System.out.println(
                    "Unexpected Error:"
            );

            System.out.println(
                    "    " + e.getMessage()
            );

            System.out.println();

            System.out.println(
                    "Compilation stopped."
            );
        }
    }

    // =========================================
    // HEADER
    // =========================================

    private static void printHeader() {

        System.out.println(
                "================================="
        );

        System.out.println(
                "        BanglaX Compiler"
        );

        System.out.println(
                "================================="
        );

        System.out.println();
    }
}