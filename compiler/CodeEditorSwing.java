// import javax.swing.*;
// import java.awt.*;

// import java.io.File;
// import java.io.IOException;
// import java.util.*;
// import java.util.List;

// public class CodeEditorSwing {
//     private static boolean isDarkMode = false;
//     private static JTextArea codeArea, outputArea;

//     public static void main(String[] args) {
//         JFrame frame = new JFrame("Swing Code Editor");
//         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//         frame.setSize(900, 500);
//         frame.setLayout(new BorderLayout());
//         JPanel topBar = new JPanel();
//         JButton themeToggleButton = new JButton("Dark Mode");
//         themeToggleButton.addActionListener(e -> toggleTheme(frame, themeToggleButton));
//         topBar.add(themeToggleButton);
//         JPanel sidebar = new JPanel();
//         sidebar.setPreferredSize(new Dimension(100, frame.getHeight()));
//         sidebar.setLayout(new BorderLayout());

//         ImageIcon pdfIcon = new ImageIcon("pdf.png"); 
//         JButton pdfButton = new JButton(pdfIcon);
//         pdfButton.setBorderPainted(false);
//         pdfButton.setContentAreaFilled(false);
//         pdfButton.setFocusPainted(false);
//         pdfButton.addActionListener(e -> openPDF("your_file.pdf"));

//         sidebar.add(pdfButton, BorderLayout.NORTH);

//         codeArea = new JTextArea();
//         codeArea.setBorder(BorderFactory.createTitledBorder("Write Code Here"));

//         outputArea = new JTextArea();
//         outputArea.setEditable(false);
//         outputArea.setBorder(BorderFactory.createTitledBorder("Output"));

//         JButton runButton = new JButton("Run");
//         runButton.addActionListener(e -> runTokenizer());

//         JPanel textPanel = new JPanel(new GridBagLayout());
//         GridBagConstraints gbc = new GridBagConstraints();
//         gbc.fill = GridBagConstraints.BOTH;
//         gbc.weightx = 1.0;
//         gbc.weighty = 1.0;
//         gbc.insets = new Insets(5, 5, 5, 5);

//         gbc.gridx = 0;
//         gbc.gridy = 0;
//         gbc.weightx = 0.5;
//         textPanel.add(new JScrollPane(codeArea), gbc);

//         gbc.gridx = 1;
//         textPanel.add(new JScrollPane(outputArea), gbc);

//         gbc.gridx = 0;
//         gbc.gridy = 1;
//         gbc.gridwidth = 2;
//         gbc.weighty = 0.0;
//         textPanel.add(runButton, gbc);

//         frame.add(topBar, BorderLayout.NORTH);
//         frame.add(sidebar, BorderLayout.WEST);
//         frame.add(textPanel, BorderLayout.CENTER);

//         frame.setVisible(true);
//     }

//     private static void toggleTheme(JFrame frame, JButton button) {
//         if (isDarkMode) {
//             UIManager.put("Panel.background", Color.WHITE);
//             UIManager.put("TextArea.background", Color.WHITE);
//             UIManager.put("TextArea.foreground", Color.BLACK);
//             button.setText("Dark Mode");
//         } else {
//             UIManager.put("Panel.background", Color.DARK_GRAY);
//             UIManager.put("TextArea.background", Color.BLACK);
//             UIManager.put("TextArea.foreground", Color.WHITE);
//             button.setText("Light Mode");
//         }
//         SwingUtilities.updateComponentTreeUI(frame);
//         isDarkMode = !isDarkMode;
//     }

//     private static void openPDF(String filePath) {
//         try {
//             File pdfFile = new File(filePath);
//             if (pdfFile.exists()) {
//                 Desktop.getDesktop().open(pdfFile);
//             } else {
//                 JOptionPane.showMessageDialog(null, "File not found: " + filePath);
//             }
//         } catch (IOException e) {
//             e.printStackTrace();
//             JOptionPane.showMessageDialog(null, "Error opening file.");
//         }
//     }

//     private static void runTokenizer() {
//         String functionDeclaration = codeArea.getText().trim();
//         if (functionDeclaration.isEmpty()) {
//             outputArea.setText("No input provided.");
//             return;
//         }

//         List<Pair<String, FunctionTokenizer.TokenType>> tokens = FunctionTokenizer.tokenizeFunction(functionDeclaration);
//         StringBuilder outputText = new StringBuilder();
//         for (Pair<String, FunctionTokenizer.TokenType> token : tokens) {
//             outputText.append("Token: ").append(token.getKey()).append(" -> ").append(token.getValue()).append("\n");
//         }
//         outputArea.setText(outputText.toString());
//     }
// }

// // class FunctionTokenizer {
// //     enum TokenType {KT, IT, SCT, OT, DT}

// //     private static final Set<String> knownKeywords = new HashSet<>(Arrays.asList(
// //             "delimiter", "function", "mean", "division", "multiply", "sub", "equals", "nequals",
// //             "square", "sqrt", "negate", "asc", "log", "greater", "mod"
// //     ));

// //     private static final Set<Character> delimiters = new HashSet<>(Arrays.asList(';', '(', ')', '[', ']'));

// //     public static List<Pair<String, TokenType>> tokenizeFunction(String functionDeclaration) {
// //         List<Pair<String, TokenType>> tokens = new ArrayList<>();
// //         String[] parts = functionDeclaration.split("\\s+|(?=[();,])|(?<=[();,])");
// //         boolean isFunctionName = false;
// //         boolean isParameter = false;

// //         for (String part : parts) {
// //             if (part.isEmpty()) continue;

// //             if (knownKeywords.contains(part)) {
// //                 tokens.add(new Pair<>(part, TokenType.KT));
// //             } else if (delimiters.contains(part.charAt(0))) {
// //                 tokens.add(new Pair<>(part, TokenType.DT));
// //                 isFunctionName = false;
// //                 isParameter = part.equals("(");
// //             } else if (isFunctionName) {
// //                 tokens.add(new Pair<>(part, TokenType.KT));
// //                 isFunctionName = false;
// //             } else if (isParameter) {
// //                 tokens.add(new Pair<>(part, TokenType.IT));
// //             } else {
// //                 isFunctionName = true;
// //                 tokens.add(new Pair<>(part, TokenType.KT));
// //             }
// //         }
// //         return tokens;
// //     }
// // }

// // class Pair<K, V> {
// //     private final K key;
// //     private final V value;

// //     public Pair(K key, V value) {
// //         this.key = key;
// //         this.value = value;
// //     }

// //     public K getKey() {
// //         return key;
// //     }

// //     public V getValue() {
// //         return value;
// //     }
// // }
