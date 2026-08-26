package ast;

public class ASTTest {

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println("       BanglaX AST Test");
        System.out.println("=================================");

        NumberExpression number =
                new NumberExpression("25");

        IdentifierExpression identifier =
                new IdentifierExpression("x");

        BinaryExpression addition =
                new BinaryExpression(
                        identifier,
                        "+",
                        number
                );

        AssignmentStatement assignment =
                new AssignmentStatement(
                        "x",
                        addition
                );

        PrintStatement print =
                new PrintStatement(addition);

        ProgramNode program =
                new ProgramNode();

        program.addStatement(assignment);
        program.addStatement(print);

        System.out.println();
        System.out.println("AST Output:");
        System.out.println("---------------------------------");
        System.out.print(program.print());

        System.out.println("---------------------------------");
        System.out.println("AST Test Successful!");
        System.out.println("=================================");
    }
}