package ast;

import java.util.ArrayList;
import java.util.List;

public class ProgramNode extends ASTNode {

    private final List<Statement> statements;

    public ProgramNode() {
        this.statements = new ArrayList<>();
    }

    public void addStatement(Statement statement) {
        statements.add(statement);
    }

    public List<Statement> getStatements() {
        return statements;
    }

    @Override
    public String print() {

        StringBuilder result = new StringBuilder();

        for (Statement statement : statements) {
            result.append(statement.print());
            result.append("\n");
        }

        return result.toString();
    }
}