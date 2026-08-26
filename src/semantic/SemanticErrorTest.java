package semantic;

import ast.IdentifierExpression;
import ast.PrintStatement;
import ast.ProgramNode;

public class SemanticErrorTest {

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println("  BanglaX Semantic Error Test");
        System.out.println("=================================");

        // =========================================
        // Create Program
        // =========================================

        ProgramNode program =
                new ProgramNode();

        // =========================================
        // Use undeclared variable
        //
        // Simulating:
        //
        // Line 1:
        // সংখ্যা x = 25
        //
        // Line 2:
        // দেখাও y
        //
        // 'y' is not declared.
        // =========================================

        PrintStatement printUnknown =
                new PrintStatement(
                        new IdentifierExpression(
                                "y",
                                2
                        )
                );

        program.addStatement(printUnknown);

        // =========================================
        // Semantic Analysis
        // =========================================

        SemanticAnalyzer analyzer =
                new SemanticAnalyzer();

        try {

            analyzer.analyze(program);

            System.out.println();
            System.out.println(
                    "ERROR: Semantic analyzer "
                    + "failed to detect undeclared variable!"
            );

        } catch (RuntimeException e) {

            System.out.println();
            System.out.println(
                    "Expected Semantic Error:"
            );

            System.out.println(
                    e.getMessage()
            );
        }

        System.out.println();
        System.out.println("=================================");
        System.out.println(
                " Semantic Error Test Completed!"
        );
        System.out.println("=================================");
    }
}