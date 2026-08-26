package ast;

import java.util.List;

public class WhileStatement extends Statement {

    private final Expression condition;
    private final List<Statement> body;

    public WhileStatement(
            Expression condition,
            List<Statement> body) {

        super();

        this.condition = condition;
        this.body = body;
    }

    public WhileStatement(
            Expression condition,
            List<Statement> body,
            int line) {

        super(line);

        this.condition = condition;
        this.body = body;
    }

    public Expression getCondition() {
        return condition;
    }

    public List<Statement> getBody() {
        return body;
    }

    @Override
    public String print() {

        StringBuilder result =
                new StringBuilder();

        result.append(
                "যতক্ষণ "
        );

        result.append(
                condition.print()
        );

        result.append("\n");

        for (Statement statement :
                body) {

            result.append(
                    "  "
            );

            result.append(
                    statement.print()
            );

            result.append("\n");
        }

        result.append(
                "শেষ"
        );

        return result.toString();
    }
}