package ast;

public abstract class Statement extends ASTNode {

    private int line;

    public Statement() {
        this.line = 0;
    }

    public Statement(int line) {
        this.line = line;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }
}