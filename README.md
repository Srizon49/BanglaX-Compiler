# BanglaX Compiler

BanglaX is a small educational programming language compiler that allows
users to write simple programs using Bangla keywords.

The compiler translates BanglaX source code into Java source code and then
compiles and executes the generated Java program.

---

## Compiler Pipeline

```text
BanglaX Source Code
        |
        v
Lexical Analysis
        |
        v
Parser
        |
        v
Abstract Syntax Tree (AST)
        |
        v
Semantic Analysis
        |
        v
Symbol Table & Type Checking
        |
        v
Java Code Generation
        |
        v
Java Compilation
        |
        v
Program Execution
```

---

## Features

- Bangla programming keywords
- Lexical analysis
- Syntax analysis / parsing
- Abstract Syntax Tree (AST)
- Semantic analysis
- Symbol table
- Type checking
- Undeclared variable detection
- Duplicate declaration detection
- Arithmetic expressions
- Comparison expressions
- If-else statements
- While loops
- Java source code generation
- Automatic Java compilation
- Automatic program execution
- Compiler error reporting with line numbers

---

## BanglaX Keywords

| BanglaX Keyword | Meaning |
|-----------------|---------|
| সংখ্যা          | NUMBER  |
| দশমিক          | DECIMAL |
| দেখাও           | PRINT   |
| যদি             | IF      |
| নাহলে           | ELSE    |
| যতক্ষণ          | WHILE   |
| শেষ             | END     |

---

## Example Program

```text
সংখ্যা x = 10
দশমিক price = 99.50

দেখাও x

যদি x >= 10
দেখাও price
নাহলে
দেখাও 0
শেষ

যতক্ষণ x < 15
x = x + 1
শেষ
```

---

## Compiler Phases

### 1. Lexical Analysis

The lexer reads BanglaX source code and converts it into tokens.

For example:

```text
সংখ্যা x = 25
```

is converted into tokens representing the variable declaration, identifier,
assignment operator, and number.

---

### 2. Parsing

The parser receives the tokens and checks the syntax according to the
BanglaX grammar.

It handles:

- Variable declarations
- Assignments
- Expressions
- Print statements
- If-else statements
- While loops

---

### 3. Abstract Syntax Tree (AST)

The AST represents the structural form of the program.

Example:

```text
x = (x + 25)
দেখাও (x + 25)
```

The AST makes it easier for later compiler phases to understand the program.

---

### 4. Semantic Analysis

Semantic analysis checks whether the program is logically valid.

It performs:

- Variable declaration checking
- Undeclared variable detection
- Duplicate declaration detection
- Type compatibility checking
- Expression type checking

---

### 5. Symbol Table

The symbol table stores information about variables used in the program.

Example:

```text
x -> NUMBER
price -> DECIMAL
```

The semantic analyzer uses the symbol table to verify variable usage and
type compatibility.

---

### 6. Java Code Generation

BanglaX statements are translated into Java source code.

Example:

```text
সংখ্যা x = 25
```

becomes:

```java
int x = 25;
```

And:

```text
দেখাও x
```

becomes:

```java
System.out.println(x);
```

---

### 7. Java Compilation and Execution

The generated Java source code is compiled using the Java compiler
(`javac`).

After successful compilation, the generated Java program is executed
automatically.

---

## Project Structure

```text
BanglaX-Compiler
|
+-- src
|   |
|   +-- lexer
|   |   +-- Lexer.java
|   |   +-- Token.java
|   |   +-- TokenType.java
|   |
|   +-- parser
|   |   +-- Parser.java
|   |   +-- ParserTest.java
|   |
|   +-- ast
|   |   +-- ASTNode.java
|   |   +-- Expression.java
|   |   +-- Statement.java
|   |   +-- AssignmentStatement.java
|   |   +-- BinaryExpression.java
|   |   +-- IdentifierExpression.java
|   |   +-- NumberExpression.java
|   |   +-- PrintStatement.java
|   |   +-- IfStatement.java
|   |   +-- WhileStatement.java
|   |   +-- ProgramNode.java
|   |
|   +-- semantic
|   |   +-- SemanticAnalyzer.java
|   |   +-- Symbol.java
|   |   +-- SymbolTable.java
|   |
|   +-- codegen
|   |   +-- CodeGenerator.java
|   |   +-- JavaCodeGenerator.java
|   |   +-- JavaCompiler.java
|   |
|   +-- error
|   |   +-- CompilerError.java
|   |
|   +-- Main.java
|
+-- examples
+-- tests
+-- docs
|
+-- final_demo.bx
+-- program.bx
+-- program_backup.bx
|
+-- invalid_expression.bx
+-- invalid_semantic.bx
+-- invalid_syntax.bx
+-- invalid_type.bx
|
+-- README.md
```

---

## Requirements

The project requires:

- Java JDK 11 or later
- Windows Command Prompt or terminal
- UTF-8 support

---

## Compilation

Open Command Prompt in the project directory:

```bat
cd /d E:\BanglaX-Compiler
```

Compile the project:

```bat
javac -encoding UTF-8 -d out src\Main.java src\lexer\Token.java src\lexer\TokenType.java src\lexer\Lexer.java src\parser\Parser.java src\ast\*.java src\semantic\*.java src\codegen\*.java src\error\*.java
```

If compilation completes without errors, the compiler is ready to run.

---

## Running the Compiler

Run the final demonstration program:

```bat
java -cp out Main final_demo.bx
```

The compiler performs the following steps:

1. Lexical Analysis
2. Parsing
3. AST Generation
4. Semantic Analysis
5. Java Code Generation
6. Java Compilation
7. Program Execution

---

## Successful Compilation

A successful compilation displays stages similar to:

```text
[1] Lexical Analysis...
    SUCCESS

[2] Parsing...
    SUCCESS

[3] AST Generation...
    SUCCESS

[4] Semantic Analysis...
    SUCCESS

[5] Java Code Generation...
    SUCCESS

[6] Java Compilation...
    SUCCESS

[7] Program Execution...
```

Finally:

```text
Compilation Successful!
```

---

## Error Handling

BanglaX detects different types of compiler errors.

### Undeclared Variable

Example:

```text
দেখাও y
```

If `y` has not been declared, the compiler reports:

```text
Semantic Error: Variable 'y' is not declared.
```

---

### Syntax Error

Example:

```text
সংখ্যা x 25
```

The compiler reports an error because the assignment operator `=` is
missing.

---

### Type Error

Example:

```text
সংখ্যা x = 25.50
```

A NUMBER variable cannot receive a DECIMAL value.

The compiler reports:

```text
Semantic Error: Variable 'x' declared as NUMBER but assigned DECIMAL value.
```

---

### Source File Error

If the specified `.bx` file does not exist, the compiler reports:

```text
Source file not found.
```

---

## Testing

The project includes tests for different compiler phases.

### AST Test

```bat
java -cp out ast.ASTTest
```

### Parser Test

```bat
java -cp out parser.ParserTest
```

### Semantic Test

```bat
java -cp out semantic.SemanticTest
```

### Semantic Error Test

```bat
java -cp out semantic.SemanticErrorTest
```

### Code Generation Test

```bat
java -cp out codegen.CodeGeneratorTest
```

### Java Code Generator Test

```bat
java -cp out codegen.JavaCodeGeneratorTest
```

### Full Compilation Test

```bat
java -cp out codegen.JavaCompilerTest
```

All major compiler components have been tested successfully.

---

## Final Demonstration

The main demonstration file is:

```text
final_demo.bx
```

Run:

```bat
java -cp out Main final_demo.bx
```

Expected program output:

```text
10
99.5
15
15
```

The complete compiler pipeline should finish with:

```text
Compilation Successful!
```

---

## Compiler Architecture

### Lexer

Responsible for converting source code into tokens.

### Parser

Responsible for checking syntax and constructing the AST.

### AST

Represents the structure of the BanglaX program.

### Semantic Analyzer

Checks variable declarations, types, and semantic correctness.

### Symbol Table

Stores variable names and their corresponding types.

### Java Code Generator

Converts the BanglaX AST into Java source code.

### Java Compiler

Compiles the generated Java source code and executes the resulting
program.

---

## Project Status

The BanglaX Compiler currently supports:

- Lexical Analysis
- Parsing
- AST Construction
- Semantic Analysis
- Symbol Table
- Type Checking
- Error Handling
- Arithmetic Expressions
- Comparison Expressions
- If-Else Statements
- While Loops
- Java Code Generation
- Java Compilation
- Program Execution

The complete compiler pipeline has been tested successfully using the
final demonstration program.

---

## Educational Purpose

BanglaX Compiler is an educational compiler design project created to
demonstrate the major phases of a compiler in a simple Bangla-based
programming language.

The project demonstrates how a high-level source program passes through
lexical analysis, parsing, AST construction, semantic analysis, code
generation, compilation, and execution.