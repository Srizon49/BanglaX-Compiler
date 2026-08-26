package ui;

import lexer.Lexer;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;

public class CompilerUI extends JFrame {

    // =========================================================
    // COLORS
    // =========================================================

    private static final Color BG =
            new Color(20, 22, 27);

    private static final Color PANEL =
            new Color(29, 32, 39);

    private static final Color PANEL2 =
            new Color(36, 40, 49);

    private static final Color BORDER =
            new Color(58, 63, 74);

    private static final Color TEXT =
            new Color(235, 238, 245);

    private static final Color MUTED =
            new Color(150, 158, 173);

    private static final Color BLUE =
            new Color(75, 145, 255);

    private static final Color GREEN =
            new Color(70, 205, 125);

    private static final Color RED =
            new Color(240, 85, 90);

    private static final Color YELLOW =
            new Color(245, 190, 70);


    // =========================================================
    // COMPONENTS
    // =========================================================

    private JTextPane editor;

    private JTextArea lineNumbers;

    private JTextArea outputArea;

    private JTextArea tokenArea;

    private JTextArea astArea;

    private JLabel fileLabel;

    private JLabel statusLabel;

    private JButton runButton;

    private JLabel[] phaseLabels;

    private Path currentFile;


    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    public CompilerUI() {

        setTitle("BanglaX Compiler - Mini IDE");

        setSize(1280, 820);

        setMinimumSize(
                new Dimension(1050, 680)
        );

        setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE
        );

        setLocationRelativeTo(null);

        buildUI();

        loadDefaultDemo();

        setVisible(true);
    }


    // =========================================================
    // MAIN UI
    // =========================================================

    private void buildUI() {

        JPanel root =
                new JPanel(
                        new BorderLayout()
                );

        root.setBackground(BG);

        root.add(
                createTopBar(),
                BorderLayout.NORTH
        );

        root.add(
                createCenterArea(),
                BorderLayout.CENTER
        );

        root.add(
                createBottomArea(),
                BorderLayout.SOUTH
        );

        setContentPane(root);
    }


    // =========================================================
    // TOP BAR
    // =========================================================

    private JPanel createTopBar() {

        JPanel top =
                new JPanel(
                        new BorderLayout()
                );

        top.setBackground(PANEL);

        top.setBorder(
                new EmptyBorder(
                        12,
                        18,
                        12,
                        18
                )
        );


        // -----------------------------------------------------
        // LEFT
        // -----------------------------------------------------

        JPanel left =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.LEFT,
                                12,
                                0
                        )
                );

        left.setOpaque(false);


        JLabel logo =
                new JLabel("BanglaX");

        logo.setForeground(TEXT);

        logo.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        23
                )
        );


        JLabel subtitle =
                new JLabel(
                        "Compiler  -  Mini IDE"
                );

        subtitle.setForeground(MUTED);

        subtitle.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        13
                )
        );


        left.add(logo);

        left.add(subtitle);


        // -----------------------------------------------------
        // RIGHT
        // -----------------------------------------------------

        JPanel right =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT,
                                8,
                                0
                        )
                );

        right.setOpaque(false);


        JButton newButton =
                createButton(
                        "New",
                        false
                );


        JButton openButton =
                createButton(
                        "Open",
                        false
                );


        JButton saveButton =
                createButton(
                        "Save",
                        false
                );


        runButton =
                createButton(
                        "Run",
                        true
                );


        newButton.addActionListener(
                e -> newFile()
        );


        openButton.addActionListener(
                e -> openFile()
        );


        saveButton.addActionListener(
                e -> saveFile()
        );


        runButton.addActionListener(
                e -> runCompiler()
        );


        right.add(newButton);

        right.add(openButton);

        right.add(saveButton);

        right.add(runButton);


        top.add(
                left,
                BorderLayout.WEST
        );

        top.add(
                right,
                BorderLayout.EAST
        );


        return top;
    }


    // =========================================================
    // CENTER
    // =========================================================

    private JPanel createCenterArea() {

        JPanel center =
                new JPanel(
                        new BorderLayout(
                                1,
                                1
                        )
                );

        center.setBackground(BORDER);


        center.add(
                createSidebar(),
                BorderLayout.WEST
        );


        center.add(
                createEditor(),
                BorderLayout.CENTER
        );


        return center;
    }


    // =========================================================
    // SIDEBAR
    // =========================================================

    private JPanel createSidebar() {

        JPanel sidebar =
                new JPanel(
                        new BorderLayout()
                );

        sidebar.setPreferredSize(
                new Dimension(
                        225,
                        0
                )
        );

        sidebar.setBackground(PANEL);

        sidebar.setBorder(
                new EmptyBorder(
                        16,
                        12,
                        16,
                        12
                )
        );


        JLabel title =
                new JLabel(
                        "PROJECT FILES"
                );

        title.setForeground(MUTED);

        title.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        11
                )
        );


        sidebar.add(
                title,
                BorderLayout.NORTH
        );


        JPanel files =
                new JPanel();

        files.setLayout(
                new BoxLayout(
                        files,
                        BoxLayout.Y_AXIS
                )
        );

        files.setOpaque(false);

        files.setBorder(
                new EmptyBorder(
                        14,
                        0,
                        0,
                        0
                )
        );


        addFileButton(
                files,
                "final_demo.bx"
        );

        addFileButton(
                files,
                "program.bx"
        );

        addFileButton(
                files,
                "invalid_expression.bx"
        );

        addFileButton(
                files,
                "invalid_semantic.bx"
        );

        addFileButton(
                files,
                "invalid_syntax.bx"
        );

        addFileButton(
                files,
                "invalid_type.bx"
        );


        JScrollPane scroll =
                new JScrollPane(files);

        scroll.setBorder(null);

        scroll.getViewport()
                .setBackground(PANEL);


        sidebar.add(
                scroll,
                BorderLayout.CENTER
        );


        return sidebar;
    }


    // =========================================================
    // FILE BUTTON
    // =========================================================

    private void addFileButton(
            JPanel parent,
            String name
    ) {

        JButton button =
                new JButton(
                        "  [FILE]  " + name
                );

        button.setHorizontalAlignment(
                SwingConstants.LEFT
        );

        button.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        38
                )
        );

        button.setForeground(TEXT);

        button.setBackground(PANEL);

        button.setBorderPainted(false);

        button.setFocusPainted(false);

        button.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        12
                )
        );


        button.addActionListener(
                e -> loadProjectFile(name)
        );


        parent.add(button);
    }


    // =========================================================
    // EDITOR
    // =========================================================

    private JPanel createEditor() {

        JPanel panel =
                new JPanel(
                        new BorderLayout()
                );

        panel.setBackground(BG);


        JPanel header =
                new JPanel(
                        new BorderLayout()
                );

        header.setBackground(PANEL2);

        header.setBorder(
                new EmptyBorder(
                        9,
                        14,
                        9,
                        14
                )
        );


        fileLabel =
                new JLabel(
                        "final_demo.bx"
                );

        fileLabel.setForeground(TEXT);

        fileLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        13
                )
        );


        statusLabel =
                new JLabel(
                        "Ready"
                );

        statusLabel.setForeground(GREEN);

        statusLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        12
                )
        );


        header.add(
                fileLabel,
                BorderLayout.WEST
        );

        header.add(
                statusLabel,
                BorderLayout.EAST
        );


        panel.add(
                header,
                BorderLayout.NORTH
        );


        editor =
                new JTextPane();

        editor.setBackground(BG);

        editor.setForeground(TEXT);

        editor.setCaretColor(TEXT);


        // IMPORTANT:
        // Nirmala UI supports Bangla on Windows.

        editor.setFont(
                new Font(
                        "Nirmala UI",
                        Font.PLAIN,
                        16
                )
        );


        editor.setBorder(
                new EmptyBorder(
                        12,
                        10,
                        12,
                        15
                )
        );


        lineNumbers =
                new JTextArea("1");

        lineNumbers.setEditable(false);

        lineNumbers.setBackground(PANEL);

        lineNumbers.setForeground(MUTED);

        lineNumbers.setFont(
                new Font(
                        "Nirmala UI",
                        Font.PLAIN,
                        16
                )
        );

        lineNumbers.setBorder(
                new EmptyBorder(
                        12,
                        12,
                        12,
                        12
                )
        );


        editor.getDocument()
                .addDocumentListener(
                        new javax.swing.event.DocumentListener() {

                            public void insertUpdate(
                                    javax.swing.event.DocumentEvent e
                            ) {

                                updateLineNumbers();

                            }


                            public void removeUpdate(
                                    javax.swing.event.DocumentEvent e
                            ) {

                                updateLineNumbers();

                            }


                            public void changedUpdate(
                                    javax.swing.event.DocumentEvent e
                            ) {

                                updateLineNumbers();

                            }
                        }
                );


        JScrollPane scroll =
                new JScrollPane(editor);

        scroll.setRowHeaderView(
                lineNumbers
        );

        scroll.setBorder(null);


        panel.add(
                scroll,
                BorderLayout.CENTER
        );


        return panel;
    }


    // =========================================================
    // BOTTOM AREA
    // =========================================================

    private JPanel createBottomArea() {

        JPanel bottom =
                new JPanel(
                        new BorderLayout()
                );

        bottom.setBackground(PANEL);

        bottom.setPreferredSize(
                new Dimension(
                        0,
                        300
                )
        );


        bottom.add(
                createPipeline(),
                BorderLayout.NORTH
        );


        JTabbedPane tabs =
                new JTabbedPane();

        tabs.setBackground(PANEL);

        tabs.setForeground(TEXT);


        // =====================================================
        // OUTPUT
        // =====================================================

        outputArea =
                createTextArea();


        JScrollPane outputScroll =
                new JScrollPane(
                        outputArea
                );

        outputScroll.setBorder(null);


        tabs.addTab(
                "  OUTPUT  ",
                outputScroll
        );


        // =====================================================
        // TOKEN STREAM
        // =====================================================

        tokenArea =
                createTextArea();


        tokenArea.setFont(
                new Font(
                        "Nirmala UI",
                        Font.PLAIN,
                        14
                )
        );


        tokenArea.setText(
                "Run the compiler or load a file to view tokens."
        );


        JScrollPane tokenScroll =
                new JScrollPane(
                        tokenArea
                );

        tokenScroll.setBorder(null);


        tabs.addTab(
                "  TOKEN STREAM  ",
                tokenScroll
        );


        // =====================================================
        // AST VIEWER
        // =====================================================

        astArea =
                createTextArea();


        astArea.setFont(
                new Font(
                        "Nirmala UI",
                        Font.PLAIN,
                        14
                )
        );


        astArea.setText(
                "Run the compiler to generate the AST view."
        );


        JScrollPane astScroll =
                new JScrollPane(
                        astArea
                );

        astScroll.setBorder(null);


        tabs.addTab(
                "  AST VIEWER  ",
                astScroll
        );


        bottom.add(
                tabs,
                BorderLayout.CENTER
        );


        return bottom;
    }


    // =========================================================
    // TEXT AREA
    // =========================================================

    private JTextArea createTextArea() {

        JTextArea area =
                new JTextArea();


        area.setEditable(false);


        area.setBackground(
                new Color(
                        17,
                        19,
                        23
                )
        );


        area.setForeground(TEXT);


        area.setFont(
                new Font(
                        "Nirmala UI",
                        Font.PLAIN,
                        14
                )
        );


        area.setBorder(
                new EmptyBorder(
                        10,
                        15,
                        10,
                        15
                )
        );


        return area;
    }


    // =========================================================
    // PIPELINE
    // =========================================================

    private JPanel createPipeline() {

        JPanel panel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.LEFT,
                                7,
                                8
                        )
                );


        panel.setBackground(PANEL2);


        panel.setBorder(
                new EmptyBorder(
                        5,
                        10,
                        5,
                        10
                )
        );


        phaseLabels =
                new JLabel[7];


        String[] phases = {

                "1  Lexer",

                "2  Parser",

                "3  AST",

                "4  Semantic",

                "5  CodeGen",

                "6  Java",

                "7  Run"
        };


        for (
                int i = 0;
                i < phases.length;
                i++
        ) {

            phaseLabels[i] =
                    createPhaseLabel(
                            phases[i]
                    );


            panel.add(
                    phaseLabels[i]
            );


            if (
                    i <
                            phases.length - 1
            ) {

                JLabel arrow =
                        new JLabel(
                                "->"
                        );


                arrow.setForeground(
                        MUTED
                );


                panel.add(arrow);
            }
        }


        return panel;
    }


    // =========================================================
    // PHASE LABEL
    // =========================================================

    private JLabel createPhaseLabel(
            String text
    ) {

        JLabel label =
                new JLabel(text);


        label.setForeground(MUTED);


        label.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        11
                )
        );


        return label;
    }


    // =========================================================
    // BUTTON
    // =========================================================

    private JButton createButton(
            String text,
            boolean primary
    ) {

        JButton button =
                new JButton(text);


        button.setFocusPainted(false);


        button.setBorderPainted(false);


        button.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        12
                )
        );


        button.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );


        if (primary) {

            button.setBackground(BLUE);

            button.setForeground(
                    Color.WHITE
            );

        } else {

            button.setBackground(PANEL2);

            button.setForeground(TEXT);
        }


        return button;
    }


    // =========================================================
    // DEFAULT DEMO
    // =========================================================

    private void loadDefaultDemo() {

        currentFile =
                Paths.get(
                        "final_demo.bx"
                );


        String demo =

                "\u09B8\u0982\u0996\u09CD\u09AF\u09BE x = 10\n" +

                "\u09A6\u09B6\u09AE\u09BF\u0995 price = 99.50\n" +

                "\n" +

                "\u09A6\u09C7\u0996\u09BE\u0993 x\n" +

                "\u09A6\u09C7\u0996\u09BE\u0993 price\n" +

                "\n" +

                "\u09AF\u09A6\u09BF x >= 10\n" +

                "    \u09A6\u09C7\u0996\u09BE\u0993 (x + 5)\n" +

                "\u09A8\u09BE\u09B9\u09B2\u09C7\n" +

                "    \u09A6\u09C7\u0996\u09BE\u0993 0\n" +

                "\u09B6\u09C7\u09B7\n" +

                "\n" +

                "\u09AF\u09A4\u0995\u09CD\u09B7\u09A3 x < 15\n" +

                "    x = x + 1\n" +

                "\u09B6\u09C7\u09B7\n" +

                "\n" +

                "\u09A6\u09C7\u0996\u09BE\u0993 x";


        editor.setText(demo);


        fileLabel.setText(
                "final_demo.bx"
        );


        statusLabel.setText(
                "Ready"
        );


        statusLabel.setForeground(
                GREEN
        );


        updateLineNumbers();

        resetPipeline();

        generateTokenView();

        generateASTPreview();
    }


    // =========================================================
    // LOAD PROJECT FILE
    // =========================================================

    private void loadProjectFile(
            String fileName
    ) {

        Path path =
                Paths.get(fileName);


        if (!Files.exists(path)) {

            statusLabel.setText(
                    "File not found"
            );


            statusLabel.setForeground(
                    RED
            );


            outputArea.setText(
                    "Unable to open file:\n\n" +
                            path.toAbsolutePath()
            );


            return;
        }


        try {

            String content =
                    Files.readString(
                            path,
                            StandardCharsets.UTF_8
                    );


            editor.setText(content);

            currentFile = path;


            fileLabel.setText(
                    fileName
            );


            statusLabel.setText(
                    "Loaded"
            );


            statusLabel.setForeground(
                    GREEN
            );


            resetPipeline();

            generateTokenView();

            generateASTPreview();


        } catch (IOException ex) {

            showError(
                    "Could not open file:\n" +
                            ex.getMessage()
            );
        }
    }


    // =========================================================
    // NEW FILE
    // =========================================================

    private void newFile() {

        editor.setText("");

        currentFile = null;


        fileLabel.setText(
                "Untitled.bx"
        );


        statusLabel.setText(
                "New File"
        );


        statusLabel.setForeground(
                YELLOW
        );


        outputArea.setText("");

        tokenArea.setText("");


        astArea.setText(
                "Run the compiler to generate the AST view."
        );


        resetPipeline();
    }


    // =========================================================
    // OPEN FILE
    // =========================================================

    private void openFile() {

        JFileChooser chooser =
                new JFileChooser();


        chooser.setDialogTitle(
                "Open BanglaX File"
        );


        if (
                chooser.showOpenDialog(this)
                        !=
                        JFileChooser.APPROVE_OPTION
        ) {

            return;
        }


        Path path =
                chooser.getSelectedFile()
                        .toPath();


        try {

            String content =
                    Files.readString(
                            path,
                            StandardCharsets.UTF_8
                    );


            editor.setText(content);

            currentFile = path;


            fileLabel.setText(
                    path.getFileName()
                            .toString()
            );


            statusLabel.setText(
                    "Loaded"
            );


            statusLabel.setForeground(
                    GREEN
            );


            resetPipeline();

            generateTokenView();

            generateASTPreview();


        } catch (IOException ex) {

            showError(
                    "Could not open file:\n" +
                            ex.getMessage()
            );
        }
    }


    // =========================================================
    // SAVE FILE
    // =========================================================

    private boolean saveFile() {

        if (currentFile == null) {

            JFileChooser chooser =
                    new JFileChooser();


            chooser.setDialogTitle(
                    "Save BanglaX File"
            );


            if (
                    chooser.showSaveDialog(this)
                            !=
                            JFileChooser.APPROVE_OPTION
            ) {

                return false;
            }


            currentFile =
                    chooser.getSelectedFile()
                            .toPath();
        }


        try {

            Files.writeString(
                    currentFile,
                    editor.getText(),
                    StandardCharsets.UTF_8
            );


            fileLabel.setText(
                    currentFile
                            .getFileName()
                            .toString()
            );


            statusLabel.setText(
                    "Saved"
            );


            statusLabel.setForeground(
                    GREEN
            );


            return true;


        } catch (IOException ex) {

            showError(
                    "Could not save file:\n" +
                            ex.getMessage()
            );


            return false;
        }
    }


    // =========================================================
    // RUN COMPILER
    // =========================================================

    private void runCompiler() {

        if (!saveFile()) {

            return;
        }


        generateTokenView();

        generateASTPreview();


        runButton.setEnabled(false);


        statusLabel.setText(
                "Compiling..."
        );


        statusLabel.setForeground(
                YELLOW
        );


        resetPipeline();


        outputArea.setText(
                "BanglaX Compiler\n" +
                        "=================================\n\n" +
                        "Starting compiler...\n\n"
        );


        Thread thread =
                new Thread(
                        () -> {

                            try {

                                ProcessBuilder pb =
                                        new ProcessBuilder(

                                                "java",

                                                "-cp",

                                                "out",

                                                "Main",

                                                currentFile
                                                        .toString()
                                        );


                                pb.redirectErrorStream(
                                        true
                                );


                                Process process =
                                        pb.start();


                                BufferedReader reader =
                                        new BufferedReader(
                                                new InputStreamReader(
                                                        process.getInputStream(),
                                                        StandardCharsets.UTF_8
                                                )
                                        );


                                StringBuilder result =
                                        new StringBuilder();


                                String line;


                                while (
                                        (
                                                line =
                                                        reader.readLine()
                                        )
                                                != null
                                ) {

                                    result.append(line);

                                    result.append(
                                            System.lineSeparator()
                                    );
                                }


                                int exitCode =
                                        process.waitFor();


                                String finalOutput =
                                        result.toString();


                                SwingUtilities.invokeLater(
                                        () -> {

                                            outputArea.setText(
                                                    finalOutput
                                            );


                                            updatePipelineFromOutput(
                                                    finalOutput
                                            );


                                            if (
                                                    exitCode == 0
                                            ) {

                                                statusLabel.setText(
                                                        "Compilation Successful"
                                                );


                                                statusLabel.setForeground(
                                                        GREEN
                                                );


                                                generateASTPreview();

                                                generateTokenView();


                                            } else {

                                                statusLabel.setText(
                                                        "Compilation Failed"
                                                );


                                                statusLabel.setForeground(
                                                        RED
                                                );
                                            }


                                            runButton.setEnabled(
                                                    true
                                            );
                                        }
                                );


                            } catch (Exception ex) {

                                SwingUtilities.invokeLater(
                                        () -> {

                                            outputArea.setText(
                                                    "Compiler could not start.\n\n" +
                                                            ex.getMessage()
                                            );


                                            statusLabel.setText(
                                                    "Compiler Error"
                                            );


                                            statusLabel.setForeground(
                                                    RED
                                            );


                                            runButton.setEnabled(
                                                    true
                                            );
                                        }
                                );
                            }
                        }
                );


        thread.start();
    }


    // =========================================================
    // TOKEN VIEW
    // =========================================================

    private void generateTokenView() {

        if (tokenArea == null) {

            return;
        }


        try {

            tokenArea.setText(
                    TokenViewer.generate(
                            editor.getText()
                    )
            );


            tokenArea.setCaretPosition(0);


        } catch (Exception ex) {

            tokenArea.setText(
                    "Token generation error:\n\n" +
                            ex.getMessage()
            );
        }
    }


    // =========================================================
    // PIPELINE UPDATE
    // =========================================================

    private void updatePipelineFromOutput(
            String output
    ) {

        boolean lexer =
                output.contains(
                        "[1] Lexical Analysis"
                )
                        &&
                        output.contains(
                                "Lexical Analysis...\n    SUCCESS"
                        );


        boolean parser =
                output.contains(
                        "[2] Parsing..."
                )
                        &&
                        output.contains(
                                "[3] AST Generation..."
                        );


        boolean ast =
                output.contains(
                        "[3] AST Generation..."
                )
                        &&
                        output.contains(
                                "[4] Semantic Analysis..."
                        );


        boolean semantic =
                output.contains(
                        "[4] Semantic Analysis..."
                )
                        &&
                        output.contains(
                                "[5] Java Code Generation..."
                        );


        boolean codegen =
                output.contains(
                        "[5] Java Code Generation..."
                )
                        &&
                        output.contains(
                                "[6] Java Compilation..."
                        );


        boolean java =
                output.contains(
                        "[6] Java Compilation..."
                )
                        &&
                        output.contains(
                                "[7] Program Execution..."
                        );


        boolean run =
                output.contains(
                        "Program Execution: SUCCESS"
                );


        setPhase(
                0,
                lexer,
                !lexer &&
                        output.contains(
                                "Compilation Failed"
                        )
        );


        setPhase(
                1,
                parser,
                false
        );


        setPhase(
                2,
                ast,
                false
        );


        setPhase(
                3,
                semantic,
                output.contains(
                        "Semantic Error:"
                )
        );


        setPhase(
                4,
                codegen,
                false
        );


        setPhase(
                5,
                java,
                false
        );


        setPhase(
                6,
                run,
                false
        );
    }


    // =========================================================
    // SET PHASE
    // =========================================================

    private void setPhase(
            int index,
            boolean success,
            boolean error
    ) {

        if (
                index < 0 ||
                        index >= phaseLabels.length
        ) {

            return;
        }


        JLabel label =
                phaseLabels[index];


        String original =
                label.getText();


        int firstSpace =
                original.indexOf(" ");


        String name;


        if (firstSpace >= 0) {

            name =
                    original.substring(
                            firstSpace + 1
                    );

        } else {

            name = original;
        }


        if (error) {

            label.setText(
                    "X " + name
            );


            label.setForeground(
                    RED
            );


        } else if (success) {

            label.setText(
                    "OK " + name
            );


            label.setForeground(
                    GREEN
            );
        }
    }


    // =========================================================
    // RESET PIPELINE
    // =========================================================

    private void resetPipeline() {

        String[] names = {

                "1  Lexer",

                "2  Parser",

                "3  AST",

                "4  Semantic",

                "5  CodeGen",

                "6  Java",

                "7  Run"
        };


        for (
                int i = 0;
                i < names.length;
                i++
        ) {

            phaseLabels[i].setText(
                    names[i]
            );


            phaseLabels[i].setForeground(
                    MUTED
            );
        }
    }


    // =========================================================
    // AST PREVIEW
    // =========================================================

    private void generateASTPreview() {

        if (astArea == null) {

            return;
        }


        String source =
                editor.getText();


        StringBuilder ast =
                new StringBuilder();


        ast.append(
                "BanglaX Abstract Syntax Tree\n"
        );


        ast.append(
                "=================================\n\n"
        );


        ast.append(
                "Program\n"
        );


        String[] lines =
                source.split(
                        "\\r?\\n"
                );


        boolean insideIf = false;

        boolean insideElse = false;

        boolean insideWhile = false;


        for (String line : lines) {

            String trimmed =
                    line.trim();


            if (trimmed.isEmpty()) {

                continue;
            }


            // =================================================
            // NUMBER DECLARATION
            // =================================================

            if (
                    trimmed.startsWith(
                            "\u09B8\u0982\u0996\u09CD\u09AF\u09BE "
                    )
            ) {

                ast.append(
                        "|-- NUMBER Declaration\n"
                );


                ast.append(
                        "|   `-- "
                );


                ast.append(
                        trimmed.substring(7)
                );


                ast.append("\n");


                continue;
            }


            // =================================================
            // DECIMAL DECLARATION
            // =================================================

            if (
                    trimmed.startsWith(
                            "\u09A6\u09B6\u09AE\u09BF\u0995 "
                    )
            ) {

                ast.append(
                        "|-- DECIMAL Declaration\n"
                );


                ast.append(
                        "|   `-- "
                );


                ast.append(
                        trimmed.substring(7)
                );


                ast.append("\n");


                continue;
            }


            // =================================================
            // IF
            // =================================================

            if (
                    trimmed.startsWith(
                            "\u09AF\u09A6\u09BF "
                    )
            ) {

                ast.append(
                        "|-- If Statement\n"
                );


                ast.append(
                        "|   |-- Condition: "
                );


                ast.append(
                        trimmed.substring(4)
                );


                ast.append("\n");


                insideIf = true;

                insideElse = false;

                continue;
            }


            // =================================================
            // ELSE
            // =================================================

            if (
                    trimmed.equals(
                            "\u09A8\u09BE\u09B9\u09B2\u09C7"
                    )
            ) {

                ast.append(
                        "|   |-- Else Branch\n"
                );


                insideElse = true;

                continue;
            }


            // =================================================
            // WHILE
            // =================================================

            if (
                    trimmed.startsWith(
                            "\u09AF\u09A4\u0995\u09CD\u09B7\u09A3 "
                    )
            ) {

                ast.append(
                        "|-- While Statement\n"
                );


                ast.append(
                        "|   |-- Condition: "
                );


                ast.append(
                        trimmed.substring(7)
                );


                ast.append("\n");


                insideWhile = true;

                continue;
            }


            // =================================================
            // END
            // =================================================

            if (
                    trimmed.equals(
                            "\u09B6\u09C7\u09B7"
                    )
            ) {

                if (insideIf) {

                    insideIf = false;

                    insideElse = false;


                    ast.append(
                            "|   `-- End Block\n"
                    );


                } else if (insideWhile) {

                    insideWhile = false;


                    ast.append(
                            "|   `-- End Block\n"
                    );


                } else {

                    ast.append(
                            "|-- End Block\n"
                    );
                }


                continue;
            }


            // =================================================
            // PRINT
            // =================================================

            if (
                    trimmed.startsWith(
                            "\u09A6\u09C7\u0996\u09BE\u0993 "
                    )
            ) {

                String value =
                        trimmed.substring(6);


                if (insideIf) {

                    if (insideElse) {

                        ast.append(
                                "|   |   `-- Print\n"
                        );


                        ast.append(
                                "|   |       `-- "
                        );


                        ast.append(
                                value
                        );


                        ast.append("\n");


                    } else {

                        ast.append(
                                "|   |-- Print\n"
                        );


                        ast.append(
                                "|   |   `-- "
                        );


                        ast.append(
                                value
                        );


                        ast.append("\n");
                    }


                } else if (insideWhile) {

                    ast.append(
                            "|   `-- Print\n"
                    );


                    ast.append(
                            "|       `-- "
                    );


                    ast.append(
                            value
                    );


                    ast.append("\n");


                } else {

                    ast.append(
                            "|-- Print\n"
                    );


                    ast.append(
                            "|   `-- "
                    );


                    ast.append(
                            value
                    );


                    ast.append("\n");
                }


                continue;
            }


            // =================================================
            // ASSIGNMENT
            // =================================================

            if (
                    trimmed.contains("=")
            ) {

                if (insideWhile) {

                    ast.append(
                            "|   `-- Assignment\n"
                    );


                    ast.append(
                            "|       `-- "
                    );


                    ast.append(
                            trimmed
                    );


                    ast.append("\n");


                } else if (insideIf) {

                    ast.append(
                            "|   |-- Assignment\n"
                    );


                    ast.append(
                            "|   |   `-- "
                    );


                    ast.append(
                            trimmed
                    );


                    ast.append("\n");


                } else {

                    ast.append(
                            "|-- Assignment\n"
                    );


                    ast.append(
                            "|   `-- "
                    );


                    ast.append(
                            trimmed
                    );


                    ast.append("\n");
                }
            }
        }


        astArea.setText(
                ast.toString()
        );


        astArea.setCaretPosition(0);
    }


    // =========================================================
    // LINE NUMBERS
    // =========================================================

    private void updateLineNumbers() {

        int lines =
                editor.getDocument()
                        .getDefaultRootElement()
                        .getElementCount();


        StringBuilder numbers =
                new StringBuilder();


        for (
                int i = 1;
                i <= lines;
                i++
        ) {

            numbers.append(i);


            if (i < lines) {

                numbers.append("\n");
            }
        }


        lineNumbers.setText(
                numbers.toString()
        );
    }


    // =========================================================
    // ERROR DIALOG
    // =========================================================

    private void showError(
            String message
    ) {

        JOptionPane.showMessageDialog(
                this,
                message,
                "BanglaX Compiler",
                JOptionPane.ERROR_MESSAGE
        );
    }


    // =========================================================
    // MAIN
    // =========================================================

    public static void main(
            String[] args
    ) {

        SwingUtilities.invokeLater(
                CompilerUI::new
        );
    }
}