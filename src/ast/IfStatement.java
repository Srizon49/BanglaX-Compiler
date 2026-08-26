package ast;

import java.util.List;

public class IfStatement extends Statement {

    private final Expression condition;
    private final List<Statement> thenBranch;
    private final List<Statement> elseBranch;

    public IfStatement(
            Expression condition,
            List<Statement> thenBranch,
            List<Statement> elseBranch) {

        super();

        this.condition = condition;
        this.thenBranch = thenBranch;
        this.elseBranch = elseBranch;
    }

    public IfStatement(
            Expression condition,
            List<Statement> thenBranch,
            List<Statement> elseBranch,
            int line) {

        super(line);

        this.condition = condition;
        this.thenBranch = thenBranch;
        this.elseBranch = elseBranch;
    }

    public Expression getCondition() {
        return condition;
    }

    public List<Statement> getThenBranch() {
        return thenBranch;
    }

    public List<Statement> getElseBranch() {
        return elseBranch;
    }

    @Override
    public String print() {

        StringBuilder result =
                new StringBuilder();

        result.append(
                "যদি "
        );

        result.append(
                condition.print()
        );

        result.append("\n");

        for (Statement statement :
                thenBranch) {

            result.append(
                    "  "
            );

            result.append(
                    statement.print()
            );

            result.append("\n");
        }

        if (elseBranch != null
                && !elseBranch.isEmpty()) {

            result.append(
                    "নাহলে\n"
            );

            for (Statement statement :
                    elseBranch) {

                result.append(
                        "  "
                );

                result.append(
                        statement.print()
                );

                result.append("\n");
            }
        }

        result.append(
                "শেষ"
        );

        return result.toString();
    }
}