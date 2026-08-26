package codegen;

import ast.*;

public class CodeGenerator {

    private final StringBuilder output;

    public CodeGenerator() {
        output = new StringBuilder();
    }

    // =========================================
    // Generate Program
    // =========================================

    public String generate(ProgramNode program) {

        output.setLength(0);

        for (Statement statement : program.getStatements()) {
            generateStatement(statement);
        }

        return output.toString();
    }

    // =========================================
    // Generate Statement
    // =========================================

    private void generateStatement(Statement statement) {

        // -----------------------------------------
        // Assignment
        // -----------------------------------------

        if (statement instanceof AssignmentStatement) {

            AssignmentStatement assignment =
                    (AssignmentStatement) statement;

            output.append(
                    assignment.getName()
                            + " = "
                            + generateExpression(
                                    assignment.getExpression()
                            )
            );

            output.append("\n");

            return;
        }

        // -----------------------------------------
        // Print
        // -----------------------------------------

        if (statement instanceof PrintStatement) {

            PrintStatement print =
                    (PrintStatement) statement;

            output.append(
                    "PRINT "
                            + generateExpression(
                                    print.getExpression()
                            )
            );

            output.append("\n");

            return;
        }

        // -----------------------------------------
        // IF
        // -----------------------------------------

        if (statement instanceof IfStatement) {

            generateIf(
                    (IfStatement) statement
            );

            return;
        }

        // -----------------------------------------
        // WHILE
        // -----------------------------------------

        if (statement instanceof WhileStatement) {

            generateWhile(
                    (WhileStatement) statement
            );
        }
    }

    // =========================================
    // Generate IF
    // =========================================

    private void generateIf(
            IfStatement statement) {

        output.append("IF ");

        output.append(
                generateExpression(
                        statement.getCondition()
                )
        );

        output.append("\n");

        for (Statement stmt :
                statement.getThenBranch()) {

            output.append("    ");

            generateStatement(stmt);
        }

        if (!statement.getElseBranch().isEmpty()) {

            output.append("ELSE\n");

            for (Statement stmt :
                    statement.getElseBranch()) {

                output.append("    ");

                generateStatement(stmt);
            }
        }

        output.append("END IF\n");
    }

    // =========================================
    // Generate WHILE
    // =========================================

    private void generateWhile(
            WhileStatement statement) {

        output.append("WHILE ");

        output.append(
                generateExpression(
                        statement.getCondition()
                )
        );

        output.append("\n");

        for (Statement stmt :
                statement.getBody()) {

            output.append("    ");

            generateStatement(stmt);
        }

        output.append("END WHILE\n");
    }

    // =========================================
    // Generate Expression
    // =========================================

    private String generateExpression(
            Expression expression) {

        // -----------------------------------------
        // Number
        // -----------------------------------------

        if (expression instanceof NumberExpression) {

            NumberExpression number =
                    (NumberExpression) expression;

            return number.getValue();
        }

        // -----------------------------------------
        // Identifier
        // -----------------------------------------

        if (expression instanceof IdentifierExpression) {

            IdentifierExpression identifier =
                    (IdentifierExpression) expression;

            return identifier.getName();
        }

        // -----------------------------------------
        // Binary Expression
        // -----------------------------------------

        if (expression instanceof BinaryExpression) {

            BinaryExpression binary =
                    (BinaryExpression) expression;

            String left =
                    generateExpression(
                            binary.getLeft()
                    );

            String right =
                    generateExpression(
                            binary.getRight()
                    );

            return left
                    + " "
                    + binary.getOperator()
                    + " "
                    + right;
        }

        return "";
    }
}