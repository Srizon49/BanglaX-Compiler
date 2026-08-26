package codegen;

import ast.*;

import java.util.HashSet;
import java.util.Set;

public class JavaCodeGenerator {

    private final StringBuilder output;
    private int indentLevel;

    // Track variables that are already declared
    private final Set<String> declaredVariables;

    public JavaCodeGenerator() {

        output = new StringBuilder();
        indentLevel = 0;

        declaredVariables = new HashSet<>();
    }

    // =========================================
    // Generate Complete Java Program
    // =========================================

    public String generate(ProgramNode program) {

        output.setLength(0);
        indentLevel = 0;

        declaredVariables.clear();

        output.append("public class GeneratedProgram {\n\n");

        indentLevel++;

        appendLine(
                "public static void main(String[] args) {"
        );

        indentLevel++;

        for (Statement statement :
                program.getStatements()) {

            generateStatement(statement);
        }

        indentLevel--;

        appendLine("}");

        indentLevel--;

        output.append("\n}");

        return output.toString();
    }

    // =========================================
    // Generate Statement
    // =========================================

    private void generateStatement(
            Statement statement) {

        // -----------------------------------------
        // Assignment
        // -----------------------------------------

        if (statement instanceof AssignmentStatement) {

            generateAssignment(
                    (AssignmentStatement) statement
            );

            return;
        }

        // -----------------------------------------
        // Print
        // -----------------------------------------

        if (statement instanceof PrintStatement) {

            PrintStatement print =
                    (PrintStatement) statement;

            appendLine(
                    "System.out.println("
                            + generateExpression(
                                    print.getExpression()
                            )
                            + ");"
            );

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
    // Generate Assignment
    // =========================================

    private void generateAssignment(
            AssignmentStatement statement) {

        String name =
                statement.getName();

        String expression =
                generateExpression(
                        statement.getExpression()
                );

        // -----------------------------------------
        // First assignment
        // -----------------------------------------

        if (!declaredVariables.contains(name)) {

            String type =
                    getJavaType(
                            statement.getExpression()
                    );

            appendLine(
                    type
                            + " "
                            + name
                            + " = "
                            + expression
                            + ";"
            );

            declaredVariables.add(name);

            return;
        }

        // -----------------------------------------
        // Existing variable
        // -----------------------------------------

        appendLine(
                name
                        + " = "
                        + expression
                        + ";"
        );
    }

    // =========================================
    // Generate IF
    // =========================================

    private void generateIf(
            IfStatement statement) {

        String condition =
                generateExpression(
                        statement.getCondition()
                );

        appendLine(
                "if ("
                        + condition
                        + ") {"
        );

        indentLevel++;

        for (Statement stmt :
                statement.getThenBranch()) {

            generateStatement(stmt);
        }

        indentLevel--;

        // -----------------------------------------
        // ELSE
        // -----------------------------------------

        if (!statement.getElseBranch().isEmpty()) {

            appendLine("} else {");

            indentLevel++;

            for (Statement stmt :
                    statement.getElseBranch()) {

                generateStatement(stmt);
            }

            indentLevel--;
        }

        appendLine("}");
    }

    // =========================================
    // Generate WHILE
    // =========================================

    private void generateWhile(
            WhileStatement statement) {

        String condition =
                generateExpression(
                        statement.getCondition()
                );

        appendLine(
                "while ("
                        + condition
                        + ") {"
        );

        indentLevel++;

        for (Statement stmt :
                statement.getBody()) {

            generateStatement(stmt);
        }

        indentLevel--;

        appendLine("}");
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

    // =========================================
    // Determine Java Type
    // =========================================

    private String getJavaType(
            Expression expression) {

        // -----------------------------------------
        // Number
        // -----------------------------------------

        if (expression instanceof NumberExpression) {

            NumberExpression number =
                    (NumberExpression) expression;

            if (number.getValue().contains(".")) {

                return "double";
            }

            return "int";
        }

        // -----------------------------------------
        // Binary Expression
        // -----------------------------------------

        if (expression instanceof BinaryExpression) {

            BinaryExpression binary =
                    (BinaryExpression) expression;

            String leftType =
                    getJavaType(
                            binary.getLeft()
                    );

            String rightType =
                    getJavaType(
                            binary.getRight()
                    );

            if ("double".equals(leftType)
                    || "double".equals(rightType)) {

                return "double";
            }

            return "int";
        }

        // -----------------------------------------
        // Default
        // -----------------------------------------

        return "int";
    }

    // =========================================
    // Add Indented Line
    // =========================================

    private void appendLine(
            String line) {

        for (int i = 0;
             i < indentLevel;
             i++) {

            output.append("    ");
        }

        output.append(line);
        output.append("\n");
    }
}