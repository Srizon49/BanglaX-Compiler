package semantic;

import ast.AssignmentStatement;
import ast.IdentifierExpression;
import ast.NumberExpression;
import ast.PrintStatement;
import ast.ProgramNode;

public class SemanticTest {

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println("    BanglaX Semantic Test");
        System.out.println("=================================");

        // =========================================
        // Create Program
        // =========================================

        ProgramNode program =
                new ProgramNode();

        // =========================================
        // Create Statements
        // =========================================

        // x = 25
        AssignmentStatement xAssignment =
                new AssignmentStatement(
                        "x",
                        new NumberExpression("25")
                );

        // price = 99.50
        AssignmentStatement priceAssignment =
                new AssignmentStatement(
                        "price",
                        new NumberExpression("99.50")
                );

        // দেখাও x
        PrintStatement printX =
                new PrintStatement(
                        new IdentifierExpression("x")
                );

        // =========================================
        // Add statements to Program
        // =========================================

        program.addStatement(xAssignment);
        program.addStatement(priceAssignment);
        program.addStatement(printX);

        // =========================================
        // Semantic Analysis
        // =========================================

        SemanticAnalyzer analyzer =
                new SemanticAnalyzer();

        try {

            analyzer.analyze(program);

            System.out.println();
            System.out.println(
                    "Semantic Analysis: SUCCESS"
            );

            // =====================================
            // Display Symbol Table
            // =====================================

            analyzer
                    .getSymbolTable()
                    .printTable();

        } catch (RuntimeException e) {

            System.out.println();
            System.out.println(
                    "Semantic Error: "
                            + e.getMessage()
            );
        }

        System.out.println();
        System.out.println("=================================");
        System.out.println("    Semantic Test Successful!");
        System.out.println("=================================");
    }
}