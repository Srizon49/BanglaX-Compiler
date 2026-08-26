package codegen;

import ast.ProgramNode;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class JavaCompiler {

    // =========================================
    // Generate Java File
    // =========================================

    public File generateJavaFile(
            ProgramNode program,
            String fileName) throws IOException {

        JavaCodeGenerator generator =
                new JavaCodeGenerator();

        String javaCode =
                generator.generate(program);

        File file =
                new File(fileName);

        try (FileWriter writer =
                     new FileWriter(file)) {

            writer.write(javaCode);
        }

        return file;
    }

    // =========================================
    // Compile Generated Java File
    // =========================================

    public boolean compile(File javaFile)
            throws IOException, InterruptedException {

        ProcessBuilder processBuilder =
                new ProcessBuilder(
                        "javac",
                        javaFile.getAbsolutePath()
                );

        processBuilder
                .inheritIO();

        Process process =
                processBuilder.start();

        int exitCode =
                process.waitFor();

        return exitCode == 0;
    }

    // =========================================
    // Run Generated Java Program
    // =========================================

    public int run(
            File javaFile)
            throws IOException, InterruptedException {

        String className =
                javaFile
                        .getName()
                        .replace(
                                ".java",
                                ""
                        );

        File parent =
                javaFile.getAbsoluteFile()
                        .getParentFile();

        ProcessBuilder processBuilder =
                new ProcessBuilder(
                        "java",
                        "-cp",
                        parent.getAbsolutePath(),
                        className
                );

        processBuilder
                .inheritIO();

        Process process =
                processBuilder.start();

        return process.waitFor();
    }
}