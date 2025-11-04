import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;       // For List interface
import java.util.ArrayList;  // For ArrayList implementation
import java.util.Date;
import java.text.SimpleDateFormat; // For formatting dates
import java.text.ParseException; // For handling parsing exceptions

class CircularButton extends JButton {
    public CircularButton(String text) {
        super(text);
        setContentAreaFilled(false); // Make the button transparent
        setFocusPainted(false); // Remove focus border
        setBorderPainted(false); // Remove border
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); // Change cursor to hand
        setFont(new Font("Segoe UI", Font.BOLD, 16)); // Set font size
    }

    @Override
    protected void paintComponent(Graphics g) {
        // Draw a circular button
        g.setColor(getBackground());
        g.fillOval(0, 0, getWidth(), getHeight());
        super.paintComponent(g);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(50, 50); // Set the size of the button
    }

    @Override
    public void setBackground(Color bg) {
        super.setBackground(bg);
        repaint(); // Repaint to update the button color
    }
}

public class UI_more {
    private static final String[] FUNCTION_NAMES = {
        "calculateGPA", "calculateCGPA", "calculateFinalMarks", "calculatePercentage",
        "checkPassFail", "calculateGrade", "calculateSubjectAverage", "calculateClassAverage",
        "isStudentEligibleForScholarship", "trackStudentProgress",
        "createStudyPlan", "adjustStudyPlanForDifficulty", "suggestStudyMaterial",
        "calculateStudyTime", "createStudySchedule", "calculateTimeToMasterSubject",
        "checkStudyBreaks", "calculateRevisionTime", "trackStudySessions",
        "suggestLearningTechniques", "generateMockTest", "calculateTestScore",
        "calculateExamDuration", "trackTestPerformance", "generateTestResult",
        "suggestTestPreparationSchedule", "calculateConfidenceLevel",
        "calculateExpectedScore", "trackStudySessionsForTest", "generatePastExamQuestions",
        "recommendBooks", "suggestOnlineCourses", "findResearchPapers",
        "generateFlashcards", "searchForTutorialVideos", "generateQuizzes",
        "suggestPodcasts", "recommendStudyGroups", "generateMindMap",
        "createSummaryNotes", "createCourseSchedule", "calculateCourseCompletionPercentage",
        "checkPrerequisite", "generateStudentReportCard", "generateCourseFeedback",
        "trackStudentAttendance", "generateClassRoster", "calculateCourseGrade",
        "assignHomework", "createCourseOutline"
    };

    private static StringBuilder outputHistory = new StringBuilder();
    private static StringBuilder inputHistory = new StringBuilder();

    public static void main(String[] args) {
        JFrame frame = new JFrame("EduCompiler");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 700);
        frame.setLayout(new BorderLayout());

        // Header
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(52, 73, 94));
        header.setPreferredSize(new Dimension(30, 50));

        // Info Button
        CircularButton infoButton = new CircularButton("i");
        infoButton.setBackground(new Color(231, 76, 60)); // Set background color
        infoButton.setForeground(Color.WHITE); // Set text color
        infoButton.addActionListener(e -> openUserManual());

        // Add hover effect
        infoButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                infoButton.setBackground(new Color(192, 57, 43)); // Darker red on hover
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                infoButton.setBackground(new Color(231, 76, 60)); // Original color
            }
        });

        JLabel titleLabel = new JLabel("EduCompiler", SwingConstants.CENTER);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));

        header.add(infoButton, BorderLayout.WEST);
        header.add(titleLabel, BorderLayout.CENTER);

        // Main Content
        JPanel mainContent = new JPanel(new BorderLayout());
        mainContent.setPreferredSize(new Dimension(800, 500));

        // Toolbar
        JPanel toolbar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        toolbar.setBackground(new Color(224, 224, 224));

        JComboBox<String> functionSelect = new JComboBox<>(FUNCTION_NAMES);
        functionSelect.insertItemAt("Select Function", 0);
        functionSelect.setSelectedIndex(0);

        JButton addButton = new JButton("ADD");
        addButton.setBackground(new Color(39, 174, 96)); // Green color
        addButton.setForeground(Color.WHITE);

        JButton runButton = new JButton("RUN");
        runButton.setBackground(new Color(39, 174, 96)); // Green color
        runButton.setForeground(Color.WHITE);

        toolbar.add(functionSelect);
        toolbar.add(addButton);
        toolbar.add(runButton);

        // Input and Output Panel
        JPanel ioPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        ioPanel.setPreferredSize(new Dimension(800, 400));

        // Input Area
        JTextArea codeArea = new JTextArea();
        codeArea.setBorder(BorderFactory.createTitledBorder("Write Here..."));
        codeArea.setLineWrap(true);
        codeArea.setWrapStyleWord(true);

        JScrollPane inputScroll = new JScrollPane(codeArea);
        
        // Create input container with clear button
        JPanel inputContainer = new JPanel(new BorderLayout());
        
        JLabel inputHeader = new JLabel("INPUT");
        inputHeader.setBackground(new Color(224, 224, 224));
        inputHeader.setOpaque(true);
        inputHeader.setHorizontalAlignment(SwingConstants.CENTER);
        
        JButton clearInputButton = new JButton("CLEAR INPUT");
        clearInputButton.setBackground(new Color(231, 76, 60)); // Red color
        clearInputButton.setForeground(Color.WHITE);
        
        inputContainer.add(inputHeader, BorderLayout.NORTH);
        inputContainer.add(inputScroll, BorderLayout.CENTER);
        inputContainer.add(clearInputButton, BorderLayout.SOUTH);

        // Output Area
        JPanel outputContainer = new JPanel(new BorderLayout());
        outputContainer.setBackground(new Color(216, 209, 194));

        JLabel outputHeader = new JLabel("OUTPUT");
        outputHeader.setBackground(new Color(224, 224, 224));
        outputHeader.setOpaque(true);
        outputHeader.setHorizontalAlignment(SwingConstants.CENTER);

        JTextArea outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setBackground(new Color(188, 192, 196));

        JButton clearOutputButton = new JButton("CLEAR OUTPUT");
        clearOutputButton.setBackground(new Color(231, 76, 60)); // Red color
        clearOutputButton.setForeground(Color.WHITE);

        outputContainer.add(outputHeader, BorderLayout.NORTH);
        outputContainer.add(new JScrollPane(outputArea), BorderLayout.CENTER);
        outputContainer.add(clearOutputButton, BorderLayout.SOUTH);

        ioPanel.add(inputContainer);
        ioPanel.add(outputContainer);

        // Add components to main content
        mainContent.add(toolbar, BorderLayout.NORTH);
        mainContent.add(ioPanel, BorderLayout.CENTER);

        // Footer
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.CENTER));
        footer.setBackground(new Color(208, 208, 208));
        footer.add(new JLabel("© 2025 EduCompiler. All rights reserved."));

        // Add all to frame
        frame.add(header, BorderLayout.NORTH);
        frame.add(mainContent, BorderLayout.CENTER);
        frame.add(footer, BorderLayout.SOUTH);

        // Action listeners
        addButton.addActionListener(e -> {
            String selectedFunction = (String) functionSelect.getSelectedItem();
            if (!selectedFunction.equals("Select Function")) {
                String functionCode = selectedFunction + "(" + getParameterDescription(selectedFunction) + ");\n";
                
                // Check if the text area is empty
                if (codeArea.getText().trim().isEmpty()) {
                    // If empty, just set the new function code
                    codeArea.setText(functionCode);
                } else {
                    // If not empty, append to what's already there
                    codeArea.append(functionCode);
                }
            }
        });

        runButton.addActionListener(e -> executeCode(outputArea, codeArea));
        
        clearInputButton.addActionListener(e -> {
            codeArea.setText("");
            inputHistory.setLength(0); // Clear input history
        });
        
        clearOutputButton.addActionListener(e -> {
            outputArea.setText("");
            outputHistory.setLength(0); // Clear output history
        });

        frame.setVisible(true);
    }

    private static String getParameterDescription(String functionName) {
        switch (functionName) {
            case "calculateGPA":
                return "(double[] marks)";
            case "calculateCGPA":
                return "(double[] gpas)";
            case "calculateFinalMarks":
                return "(double midTermMarks, double finalExamMarks)";
            case "calculatePercentage":
                return "(double marksObtained, double totalMarks)";
            case "checkPassFail":
                return "(double marks)";
            case "calculateGrade":
                return "(double percentage)";
            case "calculateSubjectAverage":
                return "(double[] subjectMarks)";
            case "calculateClassAverage":
                return "(double[] marks)";
            case "isStudentEligibleForScholarship":
                return "(double gpa, int extracurriculars)";
            case "trackStudentProgress":
                return "(String studentID, String subjectScores)";
            case "createStudyPlan":
                return "(String subject, double studyHours, int daysAvailable)";
            case "adjustStudyPlanForDifficulty":
                return "(String subject, String difficultyLevel)";
            case "suggestStudyMaterial":
                return "(String subject)";
            case "calculateStudyTime":
                return "(String subjectDifficulty, String goals)";
            case "createStudySchedule":
                return "(double studyHoursPerDay, int totalSubjects)";
            case "calculateTimeToMasterSubject":
                return "(double totalHours, double practiceRate)";
            case "checkStudyBreaks":
                return "(double studyTime)";
            case "calculateRevisionTime":
                return "(String subject, int revisionSessions)";
            case "trackStudySessions":
                return "(String date, String subject, double hours)";
            case "suggestLearningTechniques":
                return "(String subject)";
            case "generateMockTest":
                return "(String subject, String difficulty)";
            case "calculateTestScore":
                return "(int correctAnswers, int totalQuestions)";
            case "calculateExamDuration":
                return "(int totalQuestions, double timePerQuestion)";
            case "trackTestPerformance":
                return "(double[] previousScores)";
            case "generateTestResult":
                return "(String[] studentAnswers, String[] correctAnswers)";
            case "suggestTestPreparationSchedule":
                return "(String subject, String examDate)";
            case "calculateConfidenceLevel":
                return "(double[] previousScores, double currentPerformance)";
            case "calculateExpectedScore":
                return "(double studyHours, double practiceTestScores)";
            case "trackStudySessionsForTest":
                return "(String date, String subject, String focusAreas)";
            case "generatePastExamQuestions":
                return "(String subject)";
            case "recommendBooks":
                return "(String subject)";
            case "suggestOnlineCourses":
                return "(String subject)";
            case "findResearchPapers":
                return "(String subject)";
            case "generateFlashcards":
                return "(String subject, String topic)";
            case "searchForTutorialVideos":
                return "(String subject)";
            case "generateQuizzes":
                return "(String subject)";
            case "suggestPodcasts":
                return "(String subject)";
            case "recommendStudyGroups":
                return "(String subject)";
            case "generateMindMap":
                return "(String subject, String topic)";
            case "createSummaryNotes":
                return "(String subject, String topic)";
            case "createCourseSchedule":
                return "(String[] classes, String semester)";
            case "calculateCourseCompletionPercentage":
                return "(int totalCredits, int completedCredits)";
            case "checkPrerequisite":
                return "(String course, String[] studentCourses)";
            case "generateStudentReportCard":
                return "(String studentID)";
            case "generateCourseFeedback":
                return "(String courseID)";
            case "trackStudentAttendance":
                return "(String studentID, String classDate)";
            case "generateClassRoster":
                return "(String courseID)";
            case "calculateCourseGrade":
                return "(String courseID, String studentID)";
            case "assignHomework":
                return "(String studentID, String homeworkDetails)";
            case "createCourseOutline":
                return "(String courseID, String[] topics)";
            default:
                return "";
        }
    }

    private static void executeCode(JTextArea outputArea, JTextArea codeArea) {
        String input = codeArea.getText().trim();
        String[] lines = input.split("\n");
        StringBuilder output = new StringBuilder();

        for (String line : lines) {
            line = line.trim();
            if (line.isEmpty())
                continue;

            try {
                String methodName = line.substring(0, line.indexOf("("));
                String[] argsVal = getArgs(line);
                Method[] methods = UI_more.class.getDeclaredMethods();
                boolean methodFound = false;

                for (Method method : methods) {
                    if (method.getName().equals(methodName)) {
                        Class<?>[] paramTypes = method.getParameterTypes();
                        Object[] convertedArgs = new Object[paramTypes.length];

                        for (int i = 0; i < paramTypes.length; i++) {
                            if (paramTypes[i] == double.class) {
                                convertedArgs[i] = Double.parseDouble(argsVal[i]);
                            } else if (paramTypes[i] == String.class) {
                                convertedArgs[i] = argsVal[i].replaceAll("\"", "");
                            } else if (paramTypes[i] == double[].class) {
                                String[] numsStr = argsVal[i].replaceAll("[\\[\\]]", "").split(","); // Remove brackets
                                double[] nums = new double[numsStr.length];
                                for (int j = 0; j < numsStr.length; j++) {
                                    nums[j] = Double.parseDouble(numsStr[j]);
                                }
                                convertedArgs[i] = nums;
                            } else if (paramTypes[i] == int.class) {
                                convertedArgs[i] = Integer.parseInt(argsVal[i]);
                            } else if (paramTypes[i] == int[].class) {
                                String[] numsStr = argsVal[i].replaceAll("[\\[\\]]", "").split(","); // Remove brackets
                                int[] nums = new int[numsStr.length];
                                for (int j = 0; j < numsStr.length; j++) {
                                    nums[j] = Integer.parseInt(numsStr[j]);
                                }
                                convertedArgs[i] = nums;
                            } else if (paramTypes[i] == String[].class) {
                                String[] strArray = argsVal[i].replaceAll("[\\[\\]]", "").split(","); // Remove brackets
                                convertedArgs[i] = strArray;
                            }
                        }

                        Object result = method.invoke(null, convertedArgs);
                        output.append("Output of " + methodName + ":\n" + result.toString() + "\n\n");
                        methodFound = true;
                        break;
                    }
                }

                if (!methodFound) {
                    output.append("Unrecognized command: " + methodName + "\n");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                output.append("Error: " + ex.getMessage() + "\n");
            }
        }

        outputArea.setText(output.toString());
    }

    private static String[] getArgs(String input) {
        int start = input.indexOf('(');
        int end = input.indexOf(')');
        String argsStr = input.substring(start + 1, end);
        return argsStr.replaceAll(" ", "").split(",");
    }

    private static void openUserManual() {
        try {
            File pdfFile = new File("E:\\6TH SEM\\compiler\\compiler\\TUTORIAL.pdf");
            if (pdfFile.exists()) {
                Desktop.getDesktop().open(pdfFile);
            } else {
                JOptionPane.showMessageDialog(null, "File not found: TUTORIAL.pdf");
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error opening file.");
        }
    }

    // All the academic functions implementation
    public static double calculateGPA(double[] grades) {
        double totalPoints = 0;
        for (double grade : grades) {
            totalPoints += grade;
        }
        double average = totalPoints / grades.length;
        return (average / 100) * 10; // Scaled GPA
    }

    public static double calculateCGPA(double[] gpas) {
        double totalPoints = 0;
        for (double gpa : gpas) {
            totalPoints += gpa;
        }
        return totalPoints / gpas.length;
    }

    public static double calculateFinalMarks(double midTermGrade, double finalExamGrade) {
        return (midTermGrade * 0.2) + (finalExamGrade * 0.5);
    }

    public static double calculatePercentage(double marksObtained, double totalMarks) {
        return (marksObtained / totalMarks) * 100;
    }

    public static String checkPassFail(double grade) {
        return grade >= 35 ? "Pass" : "Fail";
    }

    public static String calculateGrade(double percentage) {
        if (percentage >= 85)
            return "O";
        else if (percentage >= 75)
            return "A+";
        else if (percentage >= 65)
            return "A";
        else if (percentage >= 55)
            return "B+";
        else if (percentage >= 45)
            return "B";
        else if (percentage >= 35)
            return "C";
        else
            return "F";
    }

    public static double calculateSubjectAverage(double[] subjectGrades) {
        double totalPoints = 0;
        for (double grade : subjectGrades) {
            totalPoints += grade;
        }
        return totalPoints / subjectGrades.length;
    }

    public static double calculateClassAverage(double[] grades) {
        double totalPoints = 0;
        for (double grade : grades) {
            totalPoints += grade;
        }
        return totalPoints / grades.length;
    }

    public static String isStudentEligibleForScholarship(double gpa, int extracurriculars) {
        return gpa >= 3.5 && extracurriculars > 2 ? "Eligible" : "Not Eligible";
    }

    public static String trackStudentProgress(String studentID, String scoresText) {
        String studentIDTrimmed = studentID.trim();

        // Parse scores properly - expecting a semicolon-separated format
        // Format should be "Subject1:Score1;Subject2:Score2;Subject3:Score3"

        // First, check if input is empty
        if (studentIDTrimmed.isEmpty()) {
            return "Error: Student ID is required.";
        }

        // Split by semicolons to get subject-score pairs
        String[] subjectScorePairs = scoresText.split(";");
        if (subjectScorePairs.length == 0) {
            return "Error: No scores provided.";
        }

        // Map to store subject and score
        java.util.Map<String, Double> scoreMap = new java.util.LinkedHashMap<>();

        // Process each subject-score pair
        for (String pair : subjectScorePairs) {
            pair = pair.trim();
            if (pair.isEmpty())
                continue;

            String[] parts = pair.split(":");
            if (parts.length != 2) {
                return "Error: Invalid format in \"" + pair + "\". Use format \"Subject:Score\".";
            }

            String subject = parts[0].trim();
            String scoreStr = parts[1].trim();

            if (subject.isEmpty()) {
                return "Error: Subject name cannot be empty in \"" + pair + "\".";
            }

            try {
                double score = Double.parseDouble(scoreStr);
                if (score < 0 || score > 100) {
                    return "Error: Score must be between 0 and 100 for " + subject;
                }
                scoreMap.put(subject, score);
            } catch (NumberFormatException ex) {
                return "Error: Invalid score format \"" + scoreStr + "\" for " + subject + ". Must be a number.";
            }
        }

        if (scoreMap.isEmpty()) {
            return "Error: No valid scores provided.";
        }

        // Calculate statistics
        double total = 0;
        double highestScore = Double.MIN_VALUE;
        double lowestScore = Double.MAX_VALUE;
        String highestSubject = "";
        String lowestSubject = "";

        // Build the report
        StringBuilder report = new StringBuilder();
        report.append("══════════════════════════════════\n");
        report.append("   STUDENT PROGRESS REPORT\n");
        report.append("══════════════════════════════════\n\n");
        report.append("Student ID: ").append(studentIDTrimmed).append("\n\n");
        report.append("SUBJECT PERFORMANCE:\n");

        // Process each subject and build the report
        for (Map.Entry<String, Double> entry : scoreMap.entrySet()) {
            String subject = entry.getKey();
            double score = entry.getValue();
            String grade = calculateGrade(score);

            report.append(String.format("  %-15s: %6.2f (%3s)\n", subject, score, grade));

            total += score;

            // Track highest and lowest scores
            if (score > highestScore) {
                highestScore = score;
                highestSubject = subject;
            }
            if (score < lowestScore) {
                lowestScore = score;
                lowestSubject = subject;
            }
        }

        // Calculate average
        double average = total / scoreMap.size();
        String overallGrade = calculateGrade(average);

        // Add summary statistics
        report.append("\nSUMMARY STATISTICS:\n");
        report.append(String.format("  Total Subjects  : %3d\n", scoreMap.size()));
        report.append(String.format("  Average Score   : %6.2f\n", average));
        report.append(String.format("  Overall Grade   : %6s\n", overallGrade));
        report.append(String.format("  Highest Score   : %6.2f in %s\n", highestScore, highestSubject));
        report.append(String.format("  Lowest Score    : %6.2f in %s\n", lowestScore, lowestSubject));

        // Add performance comment
        report.append("\nPERFORMANCE ANALYSIS:\n");
        if (average >= 85) {
            report.append("  Excellent performance across all subjects!");
        } else if (average >= 70) {
            report.append("  Good performance with room for improvement in some areas.");
        } else if (average >= 50) {
            report.append("  Satisfactory performance. Consider focusing on weaker subjects.");
        } else {
            report.append("  Needs significant improvement. Please seek academic support.");
        }

        report.append("\n\n══════════════════════════════════\n");

        return report.toString();
    }

    public static String createStudyPlan(String subject, double studyHours, int daysAvailable) {
        if (subject == null || subject.isEmpty() || studyHours <= 0 || daysAvailable <= 0) {
            return "Error: All fields are required.";
        }

        final String subjectTrimmed = subject.trim();
        final double hoursPerDay = studyHours / daysAvailable;
        final int breakTime = (int) (Math.floor(hoursPerDay / 2) * 15);

        return String.format(
            "Study Plan for %s%nTotal Hours: %.1f%nDays: %d%nHours/Day: %.2f%nBreaks: %d mins/day",
            subjectTrimmed, studyHours, daysAvailable, hoursPerDay, breakTime
        );
    }

    public static String adjustStudyPlanForDifficulty(String subject, String difficultyLevel) {
        if (subject == null || difficultyLevel == null) {
            return "Error: Both fields required.";
        }

        final String cleanDifficulty = difficultyLevel.trim().toLowerCase();
        final Map<String, Double> difficultyFactors = new HashMap<>();
        difficultyFactors.put("easy", 0.8);
        difficultyFactors.put("medium", 1.0);
        difficultyFactors.put("hard", 1.5);
        difficultyFactors.put("very hard", 2.0);

        if (!difficultyFactors.containsKey(cleanDifficulty)) {
            return "Invalid difficulty level";
        }

        final double factor = difficultyFactors.get(cleanDifficulty);
        return String.format(
            "Adjusted plan for %s (%s):%n- Time modifier: %.0f%%%n- Review frequency: %s",
            subject.trim(), cleanDifficulty, (factor - 1) * 100,
            factor > 1 ? "Daily" : "Every other day"
        );
    }

    public static String suggestStudyMaterial(String subject) {
        if (subject == null || subject.isEmpty()) {
            return "Error: Subject required.";
        }

        final Map<String, List<String>> materials = new HashMap<>();
        materials.put("math", Arrays.asList(
            "Thomas' Calculus", "3Blue1Brown YouTube", "Khan Academy Math"
        ));
        materials.put("physics", Arrays.asList(
            "Feynman Lectures", "MIT Physics Courses", "PhET Simulations"
        ));
        // Add more subjects as needed...

        final String cleanSubject = subject.trim().toLowerCase();
        StringBuilder sb = new StringBuilder();
        sb.append("Study materials for ").append(subject).append(":\n");

        for (Map.Entry<String, List<String>> entry : materials.entrySet()) {
            if (cleanSubject.contains(entry.getKey())) {
                entry.getValue().forEach(item -> sb.append("- ").append(item).append("\n"));
                return sb.toString();
            }
        }

        return "General materials:\n- Khan Academy\n- Coursera\n- YouTube Tutorials";
    }

    public static String calculateStudyTime(String subjectDifficulty, String goals) {
        if (subjectDifficulty == null || goals == null) {
            return "Error: Both subject difficulty and learning goals are required.";
        }

        String difficultyTrimmed = subjectDifficulty.trim().toLowerCase();
        String goalsTrimmed = goals.trim().toLowerCase();

        double difficultyMultiplier;
        switch (difficultyTrimmed) {
            case "easy": difficultyMultiplier = 1; break;
            case "medium": difficultyMultiplier = 1.5; break;
            case "hard": difficultyMultiplier = 2; break;
            case "very hard": difficultyMultiplier = 2.5; break;
            default: return "Error: Difficulty must be 'easy', 'medium', 'hard', or 'very hard'.";
        }

        double goalMultiplier;
        if (goalsTrimmed.contains("basic") || goalsTrimmed.contains("pass")) {
            goalMultiplier = 1;
        } else if (goalsTrimmed.contains("intermediate") || goalsTrimmed.contains("good")) {
            goalMultiplier = 1.5;
        } else if (goalsTrimmed.contains("advanced") || goalsTrimmed.contains("excellent")) {
            goalMultiplier = 2;
        } else if (goalsTrimmed.contains("master") || goalsTrimmed.contains("expert")) {
            goalMultiplier = 3;
        } else {
            return "Error: Goals must indicate desired proficiency level (basic/pass, intermediate/good, advanced/excellent, master/expert).";
        }

        double baseHours = 20; // Base hours for an easy subject with basic goals
        double totalHours = baseHours * difficultyMultiplier * goalMultiplier;

        // Calculate distribution of hours
        double lectureHours = totalHours * 0.3;
        double practiceHours = totalHours * 0.4;
        double reviewHours = totalHours * 0.2;
        double testingHours = totalHours * 0.1;

        StringBuilder result = new StringBuilder();
        result.append("Study Time Calculation\n\n");
        result.append("Parameters:\n");
        result.append("- Difficulty: ").append(subjectDifficulty).append(" (").append(difficultyMultiplier).append("x multiplier)\n");
        result.append("- Goals: ").append(goals).append(" (").append(goalMultiplier).append("x multiplier)\n\n");
        result.append("Estimated Total Study Time: ").append(String.format("%.1f", totalHours)).append(" hours\n\n");
        result.append("Recommended Distribution:\n");
        result.append("- Lectures/Reading: ").append(String.format("%.1f", lectureHours)).append(" hours\n");
        result.append("- Practice/Exercises: ").append(String.format("%.1f", practiceHours)).append(" hours\n");
        result.append("- Review/Revision: ").append(String.format("%.1f", reviewHours)).append(" hours\n");
        result.append("- Self-testing: ").append(String.format("%.1f", testingHours)).append(" hours\n\n");
        result.append("Note: Actual time may vary based on individual learning pace and prior knowledge.\n");

        return result.toString();
    }

    public static String createStudySchedule(double studyHoursPerDay, int totalSubjects) {
        if (studyHoursPerDay <= 0 || totalSubjects <= 0) {
            return "Error: Both study hours per day and total subjects are required.";
        }

        double hoursNum = studyHoursPerDay;
        int subjectsNum = totalSubjects;

        // Calculate time per subject
        double hoursPerSubject = hoursNum / subjectsNum;

        if (hoursPerSubject < 0.25) {
            return "Warning: Study time per subject is very low. Consider increasing total study hours or focusing on fewer subjects.";
        }

        // Create schedule based on optimal learning times
        int morningSubjects = (int) Math.ceil(subjectsNum * 0.4); // 40% of subjects in morning
        int afternoonSubjects = (int) Math.ceil(subjectsNum * 0.3); // 30% of subjects in afternoon
        int eveningSubjects = subjectsNum - morningSubjects - afternoonSubjects; // Remaining in evening

        StringBuilder schedule = new StringBuilder();
        schedule.append("Daily Study Schedule\n\n");
        schedule.append("Total Study Hours: ").append(String.format("%.1f", hoursNum)).append(" hours\n");
        schedule.append("Number of Subjects: ").append(subjectsNum).append("\n");
        schedule.append("Average Time Per Subject: ").append(String.format("%.2f", hoursPerSubject)).append(" hours\n\n");

        schedule.append("Recommended Schedule:\n\n");

        if (morningSubjects > 0) {
            schedule.append("Morning Session (6:00 AM - 12:00 PM):\n");
            schedule.append("- Subjects to focus on: ").append(morningSubjects).append("\n");
            schedule.append("- Recommended for: Complex subjects, analytical thinking, memory-intensive tasks\n");
            schedule.append("- Total time: ").append(String.format("%.2f", (morningSubjects * hoursPerSubject))).append(" hours\n\n");
        }

        if (afternoonSubjects > 0) {
            schedule.append("Afternoon Session (12:00 PM - 5:00 PM):\n");
            schedule.append("- Subjects to focus on: ").append(afternoonSubjects).append("\n");
            schedule.append("- Recommended for: Practical applications, group studies, discussions\n");
            schedule.append("- Total time: ").append(String.format("%.2f", (afternoonSubjects * hoursPerSubject))).append(" hours\n\n");
        }

        if (eveningSubjects > 0) {
            schedule.append("Evening Session (5:00 PM - 10:00 PM):\n");
            schedule.append("- Subjects to focus on: ").append(eveningSubjects).append("\n");
            schedule.append("- Recommended for: Creative subjects, reading, revision\n");
            schedule.append("- Total time: ").append(String.format("%.2f", (eveningSubjects * hoursPerSubject))).append(" hours\n\n");
        }

        schedule.append("Tips:\n");
        schedule.append("- Take a 5-10 minute break every 25-30 minutes of study\n");
        schedule.append("- Switch subjects every 1-2 hours to maintain focus\n");
        schedule.append("- Review what you've learned before going to sleep\n");

        return schedule.toString();
    }

     public static String calculateTimeToMasterSubject(double totalHours, double practiceRate) {
        if (totalHours <= 0 || practiceRate <= 0) {
            return "Error: Both total hours and practice rate are required.";
        }
    
        double hoursNum = totalHours;
        double rateNum = practiceRate;
    
        // Calculate days needed based on practice rate
        int daysPerWeek = (int) rateNum;
        double hoursPerDay = 2; // Assuming 2 hours of effective study per day
        int totalDays = (int) Math.ceil(hoursNum / hoursPerDay);
        int totalWeeks = (int) Math.ceil(totalDays / daysPerWeek);
        int totalMonths = (int) Math.ceil(totalWeeks / 4.3);
    
        // Calculate mastery levels
        int basicProficiency = (int) Math.ceil(hoursNum * 0.3 / hoursPerDay / daysPerWeek * 7);
        int intermediateProficiency = (int) Math.ceil(hoursNum * 0.6 / hoursPerDay / daysPerWeek * 7);
        int advancedProficiency = (int) Math.ceil(hoursNum * 0.9 / hoursPerDay / daysPerWeek * 7);
        int mastery = (int) Math.ceil(hoursNum / hoursPerDay / daysPerWeek * 7);
    
        StringBuilder result = new StringBuilder();
        result.append("Time to Master Subject\n\n");
        result.append("Parameters:\n");
        result.append("- Total Study Hours Required: ").append(hoursNum).append(" hours\n");
        result.append("- Practice Rate: ").append(rateNum).append(" days per week\n");
        result.append("- Assumed Daily Study Time: ").append(hoursPerDay).append(" hours\n\n");
    
        result.append("Estimated Timeline:\n");
        result.append("- Total Days of Study: ").append(totalDays).append(" days\n");
        result.append("- Total Weeks: ").append(totalWeeks).append(" weeks\n");
        result.append("- Total Months: ").append(totalMonths).append(" months\n\n");
    
        result.append("Proficiency Milestones (in days):\n");
        result.append("- Basic Proficiency: ").append(basicProficiency).append(" days\n");
        result.append("- Intermediate Proficiency: ").append(intermediateProficiency).append(" days\n");
        result.append("- Advanced Proficiency: ").append(advancedProficiency).append(" days\n");
        result.append("- Mastery: ").append(mastery).append(" days\n\n");
    
        result.append("Note: Timeline assumes consistent practice and effective study methods.\n");
    
        return result.toString();
    }
    public static String calculateRevisionTime(String subject, int revisionSessions) {
        if (subject == null || revisionSessions <= 0) {
            return "Error: Both subject and number of revision sessions are required.";
        }
    
        String subjectTrimmed = subject.trim().toLowerCase();
        double subjectCoefficient = 1;
    
        if (subjectTrimmed.contains("math") || subjectTrimmed.contains("physics") || 
            subjectTrimmed.contains("engineering") || subjectTrimmed.contains("programming")) {
            subjectCoefficient = 1.5; // STEM subjects need more revision
        } else if (subjectTrimmed.contains("language") || subjectTrimmed.contains("vocabulary") || 
                   subjectTrimmed.contains("memorization")) {
            subjectCoefficient = 1.2; // Language and memorization-heavy subjects
        } else if (subjectTrimmed.contains("history") || subjectTrimmed.contains("literature") || 
                   subjectTrimmed.contains("philosophy")) {
            subjectCoefficient = 1.3; // Humanities with lots of content
        }
    
        // Calculate base revision time
        double firstSessionMinutes = 60 * subjectCoefficient;
        double totalMinutes = firstSessionMinutes;
    
        // Calculate spaced repetition schedule
        StringBuilder result = new StringBuilder();
        result.append("Revision Time Calculation for ").append(subject).append("\n\n");
        result.append("Parameters:\n");
        result.append("- Subject Type Coefficient: ").append(subjectCoefficient).append("\n");
        result.append("- Number of Revision Sessions: ").append(revisionSessions).append("\n\n");
    
        for (int i = 0; i < revisionSessions; i++) {
            double currentMinutes = firstSessionMinutes * Math.pow(0.7, i); // Each revision takes less time
            totalMinutes += currentMinutes;
    
            String timing;
            switch (i) {
                case 0: timing = "Initial comprehensive review"; break;
                case 1: timing = "1 day after initial review"; break;
                case 2: timing = "3 days after initial review"; break;
                case 3: timing = "1 week after initial review"; break;
                case 4: timing = "2 weeks after initial review"; break;
                default: timing = (i - 2) + " weeks after initial review"; break;
            }
    
            result.append("- Session ").append(i + 1).append(": ").append(Math.round(currentMinutes)).append(" minutes (").append(timing).append(")\n");
        }
    
        result.append("\nTotal Revision Time: ").append(Math.round(totalMinutes)).append(" minutes (").append(String.format("%.1f", totalMinutes / 60)).append(" hours)\n");
        result.append("Note: This schedule follows spaced repetition principles for optimal retention.\n");
    
        return result.toString();
    }
    
    public static String trackStudySessions(String date, String subject, double hours) {
        if (date == null || subject == null || hours <= 0) {
            return "Error: Date, subject, and hours are all required.";
        }
    
        String dateTrimmed = date.trim();
        String subjectTrimmed = subject.trim();
    
        // Validate date format (simple validation)
        if (!dateTrimmed.matches("^\\d{4}-\\d{2}-\\d{2}$") && !dateTrimmed.matches("^\\d{2}/\\d{2}/\\d{4}$")) {
            return "Error: Date must be in YYYY-MM-DD or MM/DD/YYYY format.";
        }
    
        // Calculate productivity score (simple algorithm)
        double productivityScore = (hours <= 1) ? (70 + Math.random() * 10) : (80 + Math.random() * 10);
        double focusScore = 75 + Math.random() * 25; // 75-100%
    
        // Generate retention prediction
        String retentionPrediction;
        if (focusScore > 90) {
            retentionPrediction = "Excellent (80-90%)";
        } else if (focusScore > 80) {
            retentionPrediction = "Good (70-80%)";
        } else if (focusScore > 70) {
            retentionPrediction = "Average (60-70%)";
        } else {
            retentionPrediction = "Below average (40-60%)";
        }
    
        // Create session record
        StringBuilder record = new StringBuilder();
        record.append("Study Session Record\n\n");
        record.append("Date: ").append(dateTrimmed).append("\n");
        record.append("Subject: ").append(subjectTrimmed).append("\n");
        record.append("Duration: ").append(String.format("%.2f", hours)).append(" hours\n\n");
        record.append("Session Analytics:\n");
        record.append("- Productivity Score: ").append(String.format("%.1f", productivityScore)).append("%\n");
        record.append("- Focus Level: ").append(String.format("%.1f", focusScore)).append("%\n");
        record.append("- Predicted Retention: ").append(retentionPrediction).append("\n\n");
    
        // Add recommendations based on scores
        record.append("Recommendations:\n");
        if (productivityScore < 75) {
            record.append("- Consider shorter, more focused study sessions\n");
            record.append("- Try the Pomodoro technique (25 min sessions with 5 min breaks)\n");
        }
        if (focusScore < 80) {
            record.append("- Minimize distractions in your study environment\n");
            record.append("- Consider meditation or brief exercise before studying\n");
        }
        if (hours > 4) {
            record.append("- Break up long sessions into smaller chunks\n");
            record.append("- Ensure you're taking adequate breaks (5-15 min per hour)\n");
        }
    
        return record.toString();
    }
    
    public static String suggestLearningTechniques(String subject) {
        if (subject == null || subject.trim().isEmpty()) {
            return "Error: Please provide a valid subject.";
        }
    
        String subjectLower = subject.toLowerCase().trim();
        StringBuilder response = new StringBuilder();
        response.append("Learning Techniques for ").append(subject).append("\n\n");
    
        // Define subject categories and their associated learning techniques
        Map<String, List<String>> subjectTechniques = new HashMap<>();
        subjectTechniques.put("mathematics", Arrays.asList(
            "Practice problems repeatedly",
            "Spaced repetition for formulas and concepts",
            "Teach concepts to others (Feynman Technique)",
            "Connect abstract concepts to real-world applications",
            "Use visualization for geometric problems"
        ));
        subjectTechniques.put("physics", Arrays.asList(
            "Draw diagrams for problem-solving",
            "Laboratory experiments and simulations",
            "Connect formulas to physical intuition",
            "Spaced repetition for formulas",
            "Work through derivations step-by-step"
        ));
        // Add more subjects and techniques as needed...
    
        // Find matching subject or use default
        List<String> techniques = subjectTechniques.getOrDefault(subjectLower, Arrays.asList(
            "Active recall practice",
            "Spaced repetition with flashcards",
            "Mind mapping for concept connections",
            "Pomodoro technique for focused study sessions",
            "Teaching concepts to others (Feynman Technique)"
        ));
    
        response.append("Subject-Specific Techniques:\n");
        for (String technique : techniques) {
            response.append("- ").append(technique).append("\n");
        }
    
        response.append("\nGeneral Effective Learning Techniques:\n");
        response.append("- Interleaved practice (mix different topics in one study session)\n");
        response.append("- Retrieval practice (test yourself frequently)\n");
        response.append("- Elaborative interrogation (ask 'why' questions)\n");
        response.append("- Concrete examples to illustrate abstract concepts\n");
        response.append("- Dual coding (combine verbal and visual learning materials)\n");
    
        return response.toString();
    }
    public static String generateMockTest(String subject, String difficulty) {
        // Sample questions (you can expand this list as needed)
        List<Map<String, String>> questions = Arrays.asList(
            Map.of("question", "What is 2 + 2?", "correctAnswer", "4"),
            Map.of("question", "What is the capital of France?", "correctAnswer", "Paris"),
            Map.of("question", "What is 5 * 6?", "correctAnswer", "30"),
            Map.of("question", "What is the square root of 16?", "correctAnswer", "4"),
            Map.of("question", "What is the largest planet in our solar system?", "correctAnswer", "Jupiter")
        );
    
        // For simplicity, we are returning all questions regardless of difficulty
        StringBuilder result = new StringBuilder();
        result.append("Mock Test for ").append(subject).append(" (").append(difficulty).append(" difficulty)\n\n");
        
        for (Map<String, String> q : questions) {
            result.append(q.get("question")).append("\n");
        }
    
        return result.toString();
    }
    public static double calculateExamDuration(int totalQuestions, double timePerQuestion) {
    return totalQuestions * timePerQuestion; // Total duration in minutes
}

public static double trackTestPerformance(double[] previousScores) {
    double totalScores = 0;
    for (double score : previousScores) {
        totalScores += score;
    }
    return totalScores / previousScores.length; // Returns average score
}

public static double calculateTestScore(int correctAnswers, int totalQuestions) {
    if (totalQuestions == 0) return 0; // Avoid division by zero
    return ((double) correctAnswers / totalQuestions) * 100; // Returns score as percentage
}

public static Map<String, Object> generateTestResult(String[] studentAnswers, String[] correctAnswers) {
    if (studentAnswers.length != correctAnswers.length) {
        throw new IllegalArgumentException("Error: Mismatched number of student and correct answers.");
    }

    int correctCount = 0;
    for (int i = 0; i < studentAnswers.length; i++) {
        if (studentAnswers[i].trim().equals(correctAnswers[i].trim())) {
            correctCount++;
        }
    }
    int totalQuestions = correctAnswers.length;
    double score = ((double) correctCount / totalQuestions) * 100;

    Map<String, Object> result = new HashMap<>();
    result.put("correctCount", correctCount);
    result.put("totalQuestions", totalQuestions);
    result.put("score", String.format("%.2f", score));

    return result;
}

public static String suggestTestPreparationSchedule(String subject, String examDate) {
    if (subject == null || examDate == null) return "Please enter both subject and exam date.";

    Date examDay;
    try {
        examDay = new SimpleDateFormat("yyyy-MM-dd").parse(examDate);
    } catch (ParseException e) {
        return "Error: Invalid date format. Please use YYYY-MM-DD.";
    }

    Date today = new Date();
    long daysLeft = (examDay.getTime() - today.getTime()) / (1000 * 60 * 60 * 24);

    if (daysLeft < 1) return "Exam is today or already passed! No schedule needed.";

    return String.format("Suggested Plan:\n- Study %s for %d days.\n- Revise & solve mock tests on the last day.", subject, daysLeft - 1);
}
public static String calculateConfidenceLevel(double[] previousScores, double currentPerformance) {
    if (previousScores == null || previousScores.length == 0 || Double.isNaN(currentPerformance)) {
        return "Invalid input.";
    }
    
    double totalPoints = 0;
    for (double score : previousScores) {
        totalPoints += score;
    }
    double averageScore = totalPoints / previousScores.length;
    double confidenceLevel = (averageScore + currentPerformance) / 2;
    
    return String.format("%.2f", confidenceLevel);
}
public static double calculateExpectedScore(double studyHours, double[] practiceTestScores) {
    if (practiceTestScores == null || practiceTestScores.length == 0) {
        return 0.0;
    }
    
    // Create a set to remove duplicates and then calculate average
    java.util.Set<Double> uniqueScores = new java.util.HashSet<>();
    for (double score : practiceTestScores) {
        uniqueScores.add(score);
    }
    
    double totalScore = 0;
    for (double score : uniqueScores) {
        totalScore += score;
    }
    
    double avgPracticeScore = uniqueScores.isEmpty() ? 0 : totalScore / uniqueScores.size();
    
    // Calculate expected score using the formula
    double expectedScore = Math.min(100, avgPracticeScore + (studyHours * 2));
    
    return Math.round(expectedScore);
}
public static String trackStudySessionsForTest(String date, String subject, String[] focusAreas) {
    if (date == null || subject == null || focusAreas == null) {
        return "Error: All parameters are required.";
    }
    
    // Remove duplicates from focus areas
    java.util.Set<String> uniqueFocusAreasSet = new java.util.HashSet<>();
    for (String area : focusAreas) {
        if (area != null && !area.trim().isEmpty()) {
            uniqueFocusAreasSet.add(area.trim());
        }
    }
    
    // Convert to list for formatting
    java.util.List<String> uniqueFocusAreas = new java.util.ArrayList<>(uniqueFocusAreasSet);
    
    // Format the result
    StringBuilder result = new StringBuilder();
    result.append("Study Session Tracking\n\n");
    result.append("Date: ").append(date).append("\n");
    result.append("Subject: ").append(subject).append("\n");
    result.append("Focus Areas:\n");
    
    for (int i = 0; i < uniqueFocusAreas.size(); i++) {
        result.append("- ").append(uniqueFocusAreas.get(i)).append("\n");
    }
    
    // Add study tips based on subject
    result.append("\nStudy Tips:\n");
    if (subject.toLowerCase().contains("math")) {
        result.append("- Focus on solving practice problems\n");
        result.append("- Review formulas and theorems\n");
    } else if (subject.toLowerCase().contains("science") || subject.toLowerCase().contains("physics")) {
        result.append("- Connect theoretical concepts with practical applications\n");
        result.append("- Work through example problems step by step\n");
    } else if (subject.toLowerCase().contains("history")) {
        result.append("- Create timelines for important events\n");
        result.append("- Practice recalling key dates and figures\n");
    } else {
        result.append("- Use active recall techniques\n");
        result.append("- Create connections between different concepts\n");
    }
    
    return result.toString();
}
public static String generatePastExamQuestions(String subject) {
    if (subject == null || subject.trim().isEmpty()) {
        return "No subject specified";
    }
    
    // Define question links for various subjects
    java.util.Map<String, String[]> questionLinks = new java.util.HashMap<>();
    
    questionLinks.put("Math", new String[]{
        "Khan Academy Math Resources: https://www.khanacademy.org/math",
        "CBSE Class 12 Mathematics Previous Year Papers: https://www.selfstudys.com/books/cbse-prev-paper/english/class-12th/mathematics-pyp/1411",
        "College Dunia CBSE Class 12 Mathematics Question Papers: https://collegedunia.com/exams/cbse-class-xii/mathematics-question-paper"
    });
    
    questionLinks.put("Science", new String[]{ 
        "NIOS Previous Year Question Papers: https://nios.ac.in/student-information-section/question-paper-of-previous-year-examination-academic.aspx", 
        "CBSE Official Question Papers: https://www.cbse.gov.in/cbsenew/question-paper.html", 
        "Class 10 Science Previous Year Papers: https://www.educart.co/previous-year-question-paper/cbse-class-10-science-previous-year-papers" 
    });
    
    questionLinks.put("History", new String[]{ 
        "Cambridge IGCSE History Past Papers: https://www.cambridgeinternational.org/programmes-and-qualifications/cambridge-igcse-history-0470/past-papers/", 
        "CBSE Class 12 History Previous Year Papers: https://www.selfstudys.com/books/cbse-prev-paper/english/class-12th/history-pyp/6323", 
        "PapaCambridge IGCSE History Past Papers: https://pastpapers.papacambridge.com/papers/caie/igcse-history-0470" 
    });
    
    questionLinks.put("Biology", new String[]{ 
        "CBSE Class 12 Biology Previous Year Papers: https://www.selfstudys.com/books/cbse-prev-paper/english/12th/biology-pyp/1404", 
        "BYJU'S CBSE Class 12 Biology Last 5 Years Papers: https://byjus.com/cbse-study-material/last-5-years-question-papers-cbse-12th-biology/", 
        "College Dunia CBSE Class 12 Biology Question Papers: https://collegedunia.com/exams/cbse-class-xii/biology-question-paper" 
    });
    
    // Check if the subject exists in our map
    for (java.util.Map.Entry<String, String[]> entry : questionLinks.entrySet()) {
        if (subject.equalsIgnoreCase(entry.getKey()) || subject.toLowerCase().contains(entry.getKey().toLowerCase())) {
            StringBuilder result = new StringBuilder();
            String[] links = entry.getValue();
            for (String link : links) {
                result.append(link).append("\n");
            }
            return result.toString();
        }
    }
    
    return "No available past exam questions for this subject.";
}
public static String recommendBooks(String subject) {
    if (subject == null || subject.trim().isEmpty()) {
        return "No subject specified";
    }
    
    // Define book recommendations for various subjects
    java.util.Map<String, String[]> bookRecommendations = new java.util.HashMap<>();
    
    bookRecommendations.put("Math", new String[]{
        "📘 'Calculus' by James Stewart",
        "📙 'Introduction to Linear Algebra' by Gilbert Strang",
        "📗 'Discrete Mathematics and Its Applications' by Kenneth H. Rosen"
    });
    
    bookRecommendations.put("Science", new String[]{
        "🔬 'A Brief History of Time' by Stephen Hawking",
        "🧪 'The Selfish Gene' by Richard Dawkins",
        "🌍 'The Elegant Universe' by Brian Greene"
    });
    
    bookRecommendations.put("History", new String[]{
        "📜 'Sapiens: A Brief History of Humankind' by Yuval Noah Harari",
        "🏰 'Guns, Germs, and Steel' by Jared Diamond",
        "🌍 'The History of the Ancient World' by Susan Wise Bauer"
    });
    
    // Check if the subject exists in our map
    for (java.util.Map.Entry<String, String[]> entry : bookRecommendations.entrySet()) {
        if (subject.equalsIgnoreCase(entry.getKey()) || subject.toLowerCase().contains(entry.getKey().toLowerCase())) {
            StringBuilder result = new StringBuilder();
            String[] books = entry.getValue();
            for (String book : books) {
                result.append(book).append("\n");
            }
            return result.toString();
        }
    }
    
    return "No book recommendations available for this subject.";
}
public static String suggestOnlineCourses(String subject) {
    if (subject == null || subject.trim().isEmpty()) {
        return "No subject specified";
    }
    
    // Define online courses for various subjects
    java.util.Map<String, String[]> onlineCourses = new java.util.HashMap<>();
    
    onlineCourses.put("Math", new String[]{
        "📚 'Mathematics for Machine Learning' on Coursera",
        "📚 'Calculus 1' on edX",
        "📚 'Linear Algebra' on Khan Academy"
    });
    
    onlineCourses.put("Science", new String[]{
        "📚 'Introduction to Biology' on Coursera",
        "📚 'Physics 101' on edX",
        "📚 'Chemistry: Concept Development and Application' on Khan Academy"
    });
    
    onlineCourses.put("History", new String[]{
        "📚 'World History: A New Perspective' on Coursera",
        "📚 'The Modern World: Global History since 1760' on edX",
        "📚 'Introduction to Ancient Egypt and Its Civilization' on edX"
    });
    
    onlineCourses.put("Biology", new String[]{
        "📚 'Biology: The Secret of Life' on edX",
        "📚 'Introduction to Genetics and Evolution' on Coursera",
        "📚 'Human Anatomy' on Khan Academy"
    });
    
    // Check if the subject exists in our map
    for (java.util.Map.Entry<String, String[]> entry : onlineCourses.entrySet()) {
        if (subject.equalsIgnoreCase(entry.getKey()) || subject.toLowerCase().contains(entry.getKey().toLowerCase())) {
            StringBuilder result = new StringBuilder();
            String[] courses = entry.getValue();
            for (String course : courses) {
                result.append(course).append("\n");
            }
            return result.toString();
        }
    }
    
    return "❌ No online courses available for this subject.";
}
public static String findResearchPapers(String subject) {
    if (subject == null || subject.trim().isEmpty()) {
        return "No subject specified";
    }
    
    // Define research papers for various subjects
    java.util.Map<String, String[]> researchPapers = new java.util.HashMap<>();
    
    researchPapers.put("Math", new String[]{
        "📄 'A Survey of Mathematical Models in Biology' - ResearchGate",
        "📄 'Mathematics and the Imagination' - JSTOR",
        "📄 'The Role of Mathematics in Science' - arXiv"
    });
    
    researchPapers.put("Science", new String[]{
        "📄 'The Science of Climate Change' - Nature",
        "📄 'Recent Advances in Biology' - PubMed",
        "📄 'Physics Research Papers' - arXiv"
    });
    
    researchPapers.put("History", new String[]{
        "📄 'Historical Analysis of the Cold War' - JSTOR",
        "📄 'The Impact of Colonialism on Modern History' - ResearchGate",
        "📄 'The Role of Women in World War II' - Academia.edu"
    });
    
    researchPapers.put("Biology", new String[]{
        "📄 'Genetic Engineering: A Review' - PubMed",
        "📄 'The Impact of Climate Change on Biodiversity' - Nature",
        "📄 'Cell Biology Research Papers' - ResearchGate"
    });
    
    // Check if the subject exists in our map
    for (java.util.Map.Entry<String, String[]> entry : researchPapers.entrySet()) {
        if (subject.equalsIgnoreCase(entry.getKey()) || subject.toLowerCase().contains(entry.getKey().toLowerCase())) {
            StringBuilder result = new StringBuilder();
            String[] papers = entry.getValue();
            for (String paper : papers) {
                result.append(paper).append("\n");
            }
            return result.toString();
        }
    }
    
    return "❌ No research papers available for this subject.";
}
public static String generateFlashcards(String subject, String topic) {
    if (subject == null || subject.trim().isEmpty()) {
        return "No subject specified";
    }
    
    // Define flashcards for various subjects
    java.util.Map<String, String[]> flashcards = new java.util.HashMap<>();
    
    flashcards.put("Math", new String[]{
        "Flashcard 1: What is the derivative of x^2? Answer: 2x",
        "Flashcard 2: What is the integral of 1/x? Answer: ln|x| + C",
        "Flashcard 3: What is the Pythagorean theorem? Answer: a^2 + b^2 = c^2"
    });
    
    flashcards.put("Science", new String[]{
        "Flashcard 1: What is the chemical formula for water? Answer: H2O",
        "Flashcard 2: What is Newton's second law? Answer: F = ma",
        "Flashcard 3: What is the process of photosynthesis? Answer: Conversion of light energy into chemical energy"
    });
    
    flashcards.put("History", new String[]{
        "Flashcard 1: What year did World War II begin? Answer: 1939",
        "Flashcard 2: Who was the first President of the United States? Answer: George Washington",
        "Flashcard 3: What was the significance of the Magna Carta? Answer: It limited the power of the king"
    });
    
    flashcards.put("Biology", new String[]{
        "Flashcard 1: What is the basic unit of life? Answer: The cell",
        "Flashcard 2: What is DNA? Answer: Deoxyribonucleic acid, the molecule that carries genetic information",
        "Flashcard 3: What is the function of ribosomes? Answer: Protein synthesis"
    });
    
    // Check if the subject exists in our map
    for (java.util.Map.Entry<String, String[]> entry : flashcards.entrySet()) {
        if (subject.equalsIgnoreCase(entry.getKey()) || subject.toLowerCase().contains(entry.getKey().toLowerCase())) {
            StringBuilder result = new StringBuilder();
            String[] cards = entry.getValue();
            for (String card : cards) {
                result.append(card).append("\n");
            }
            return result.toString();
        }
    }
    
    return "❌ No flashcards available for " + topic + " in " + subject + ".";
}

public static String searchForTutorialVideos(String subject) {
    if (subject == null || subject.trim().isEmpty()) {
        return "No subject specified";
    }
    
    // Define tutorial videos for various subjects
    java.util.Map<String, String[]> tutorialVideos = new java.util.HashMap<>();
    
    tutorialVideos.put("Math", new String[]{
        "📹 'Introduction to Algebra' on YouTube",
        "📹 'Calculus Basics' on Khan Academy",
        "📹 'Geometry Explained' on YouTube"
    });
    
    tutorialVideos.put("Science", new String[]{
        "📹 'Physics Fundamentals' on YouTube",
        "📹 'Biology Basics' on Khan Academy",
        "📹 'Chemistry 101' on YouTube"
    });
    
    tutorialVideos.put("History", new String[]{
        "📹 'World War II Overview' on YouTube",
        "📹 'The Cold War Explained' on YouTube",
        "📹 'Ancient Civilizations' on Khan Academy"
    });
    
    tutorialVideos.put("Biology", new String[]{
        "📹 'Cell Biology Basics' on YouTube",
        "📹 'Genetics Explained' on Khan Academy",
        "📹 'Human Anatomy Overview' on YouTube"
    });
    
    // Check if the subject exists in our map
    for (java.util.Map.Entry<String, String[]> entry : tutorialVideos.entrySet()) {
        if (subject.equalsIgnoreCase(entry.getKey()) || subject.toLowerCase().contains(entry.getKey().toLowerCase())) {
            StringBuilder result = new StringBuilder();
            String[] videos = entry.getValue();
            for (String video : videos) {
                result.append(video).append("\n");
            }
            return result.toString();
        }
    }
    
    return "No tutorial videos available for this subject.";
}

public static String generateQuizzes(String subject) {
    if (subject == null || subject.trim().isEmpty()) {
        return "No subject specified";
    }
    
    // Define quizzes for various subjects
    java.util.Map<String, String[]> quizzes = new java.util.HashMap<>();
    
    quizzes.put("Math", new String[]{
        "📝 'Basic Algebra Quiz' on Quizlet",
        "📝 'Calculus Practice Quiz' on Khan Academy",
        "📝 'Geometry Quiz' on ProProfs"
    });
    
    quizzes.put("Science", new String[]{
        "📝 'Physics Quiz' on Quizlet",
        "📝 'Biology Quiz' on ProProfs",
        "📝 'Chemistry Quiz' on Khan Academy"
    });
    
    quizzes.put("History", new String[]{
        "📝 'World History Quiz' on Quizlet",
        "📝 'American History Quiz' on ProProfs",
        "📝 'Ancient Civilizations Quiz' on Kahoot"
    });
    
    quizzes.put("Biology", new String[]{
        "📝 'Cell Biology Quiz' on Quizlet",
        "📝 'Genetics Quiz' on ProProfs",
        "📝 'Human Anatomy Quiz' on Kahoot"
    });
    
    // Check if the subject exists in our map
    for (java.util.Map.Entry<String, String[]> entry : quizzes.entrySet()) {
        if (subject.equalsIgnoreCase(entry.getKey()) || subject.toLowerCase().contains(entry.getKey().toLowerCase())) {
            StringBuilder result = new StringBuilder();
            String[] quizArray = entry.getValue();
            for (String quiz : quizArray) {
                result.append(quiz).append("\n");
            }
            return result.toString();
        }
    }
    
    return "No quizzes available for this subject.";
}

public static String suggestPodcasts(String subject) {
    if (subject == null || subject.trim().isEmpty()) {
        return "No subject specified";
    }
    
    // Define podcasts for various subjects
    java.util.Map<String, String[]> podcasts = new java.util.HashMap<>();
    
    podcasts.put("Math", new String[]{
        "🎙️ 'The Math Dude' Podcast",
        "🎙️ 'Math Mutation' Podcast",
        "🎙️ 'The Numberphile Podcast'"
    });
    
    podcasts.put("Science", new String[]{
        "🎙️ 'Science Vs' Podcast",
        "🎙️ 'The Infinite Monkey Cage' Podcast",
        "🎙️ 'Stuff You Should Know' Podcast"
    });
    
    podcasts.put("History", new String[]{
        "🎙️ 'Hardcore History' Podcast",
        "🎙️ 'Revolutions' Podcast",
        "🎙️ 'You Must Remember This' Podcast"
    });
    
    podcasts.put("Biology", new String[]{
        "🎙️ 'The Biology Podcast'",
        "🎙️ 'The Naked Biologist' Podcast",
        "🎙️ 'The Brain Science Podcast'"
    });
    
    // Check if the subject exists in our map
    for (java.util.Map.Entry<String, String[]> entry : podcasts.entrySet()) {
        if (subject.equalsIgnoreCase(entry.getKey()) || subject.toLowerCase().contains(entry.getKey().toLowerCase())) {
            StringBuilder result = new StringBuilder();
            String[] podcastArray = entry.getValue();
            for (String podcast : podcastArray) {
                result.append(podcast).append("\n");
            }
            return result.toString();
        }
    }
    
    return "No podcasts available for this subject.";
}
public static String recommendStudyGroups(String subject) {
    if (subject == null || subject.trim().isEmpty()) {
        return "No subject specified";
    }

    java.util.Map<String, String[]> studyGroups = new java.util.HashMap<>();

    studyGroups.put("Math", new String[]{
        "📚 'Math Study Group' on Facebook",
        "📚 'Algebra Study Group' on Discord",
        "📚 'Calculus Study Buddies' on Meetup"
    });

    studyGroups.put("Science", new String[]{
        "📚 'Science Study Group' on Facebook",
        "📚 'Biology Study Group' on Discord",
        "📚 'Physics Study Buddies' on Meetup"
    });

    studyGroups.put("History", new String[]{
        "📚 'History Study Group' on Facebook",
        "📚 'World History Study Group' on Discord",
        "📚 'American History Study Buddies' on Meetup"
    });

    studyGroups.put("Biology", new String[]{
        "📚 'Biology Study Group' on Facebook",
        "📚 'Genetics Study Group' on Discord",
        "📚 'Microbiology Study Buddies' on Meetup"
    });

    return java.util.Arrays.stream(studyGroups.getOrDefault(subject, new String[]{"No study groups available for this subject."}))
            .reduce("", (acc, item) -> acc + item + "\n");
}
public static String generateMindMap(String subject, String topic) {
    if (subject == null || subject.trim().isEmpty()) {
        return "No subject specified";
    }

    java.util.Map<String, String[]> mindMaps = new java.util.HashMap<>();

    mindMaps.put("Math", new String[]{
        "🗺️ 'Algebra Mind Map' - A visual representation of algebra concepts.",
        "🗺️ 'Geometry Mind Map' - A visual representation of geometry concepts.",
        "🗺️ 'Calculus Mind Map' - A visual representation of calculus concepts."
    });

    mindMaps.put("Science", new String[]{
        "🗺️ 'Physics Mind Map' - A visual representation of physics concepts.",
        "🗺️ 'Biology Mind Map' - A visual representation of biology concepts.",
        "🗺️ 'Chemistry Mind Map' - A visual representation of chemistry concepts."
    });

    mindMaps.put("History", new String[]{
        "🗺️ 'World History Mind Map' - A visual representation of world history events.",
        "🗺️ 'American History Mind Map' - A visual representation of American history events.",
        "🗺️ 'Ancient Civilizations Mind Map' - A visual representation of ancient civilizations."
    });

    mindMaps.put("Biology", new String[]{
        "🗺️ 'Cell Biology Mind Map' - A visual representation of cell biology concepts.",
        "🗺️ 'Genetics Mind Map' - A visual representation of genetics concepts.",
        "🗺️ 'Ecology Mind Map' - A visual representation of ecology concepts."
    });

    return java.util.Arrays.stream(mindMaps.getOrDefault(subject, new String[]{"No mind maps available for " + topic + " in " + subject + "."}))
            .reduce("", (acc, item) -> acc + item + "\n");
}

public static String createSummaryNotes(String subject, String topic) {
    if (subject == null || subject.trim().isEmpty()) {
        return "No subject specified";
    }

    java.util.Map<String, String[]> summaryNotes = new java.util.HashMap<>();

    summaryNotes.put("Math", new String[]{
        "📄 Summary of Algebra: Key concepts include variables, equations, and functions.",
        "📄 Summary of Geometry: Focus on shapes, angles, and theorems.",
        "📄 Summary of Calculus: Covers limits, derivatives, and integrals."
    });

    summaryNotes.put("Science", new String[]{
        "📄 Summary of Physics: Discusses motion, forces, and energy.",
        "📄 Summary of Biology: Covers cell structure, genetics, and evolution.",
        "📄 Summary of Chemistry: Focuses on elements, compounds, and reactions."
    });

    summaryNotes.put("History", new String[]{
        "📄 Summary of World History: Key events from ancient to modern times.",
        "📄 Summary of American History: Covers colonization, independence, and civil rights.",
        "📄 Summary of Ancient Civilizations: Discusses Mesopotamia, Egypt, and Greece."
    });

    summaryNotes.put("Biology", new String[]{
        "📄 Summary of Cell Biology: Focus on cell structure and function.",
        "📄 Summary of Genetics: Discusses DNA, genes, and heredity.",
        "📄 Summary of Ecology: Covers ecosystems, biodiversity, and conservation."
    });

    return java.util.Arrays.stream(summaryNotes.getOrDefault(subject, new String[]{"No summary notes available for " + topic + " in " + subject + "."}))
            .reduce("", (acc, item) -> acc + item + "\n");
}
public static String generateCourseFeedback(int courseID) {
    java.util.Map<Integer, String> feedbackDatabase = new java.util.HashMap<>();
    
    feedbackDatabase.put(101, "Great course! The material was well-structured and engaging.");
    feedbackDatabase.put(102, "The course was informative, but the pace was a bit fast.");
    feedbackDatabase.put(103, "I enjoyed the practical sessions, but more examples would be helpful.");
    feedbackDatabase.put(104, "Excellent course! The instructor was very knowledgeable.");
    
    return feedbackDatabase.getOrDefault(courseID, "No feedback available for this course.");
}

public static String[] generateClassRoster(int courseID) {
    java.util.Map<Integer, String[]> classRoster = new java.util.HashMap<>();
    
    classRoster.put(101, new String[]{"Bhoomi", "Ananya", "Shanaya", "Aditi", "Sunny", "Aditya"});
    classRoster.put(102, new String[]{"Ana", "Eni", "Tanay", "Ani", "Tarun"});
    classRoster.put(103, new String[]{"Riddhi", "Siddhi", "Gauri"});
    
    return classRoster.getOrDefault(courseID, new String[]{"No students enrolled in this course."});
}

public static String calculateCourseGrade(int courseID, int studentID) {
    java.util.Map<Integer, java.util.Map<Integer, Integer>> grades = new java.util.HashMap<>();
    
    java.util.Map<Integer, Integer> course101Grades = new java.util.HashMap<>();
    course101Grades.put(1, 85);
    course101Grades.put(2, 90);
    course101Grades.put(3, 78);
    
    java.util.Map<Integer, Integer> course102Grades = new java.util.HashMap<>();
    course102Grades.put(4, 88);
    course102Grades.put(5, 92);
    course102Grades.put(6, 80);
    
    java.util.Map<Integer, Integer> course103Grades = new java.util.HashMap<>();
    course103Grades.put(7, 75);
    course103Grades.put(8, 85);
    course103Grades.put(9, 95);
    course103Grades.put(12, 78);
    course103Grades.put(13, 34);
    course103Grades.put(14, 67);
    course103Grades.put(15, 78);
    course103Grades.put(16, 55);
    course103Grades.put(17, 56);
    
    java.util.Map<Integer, Integer> course104Grades = new java.util.HashMap<>();
    course104Grades.put(10, 45);
    course104Grades.put(11, 95); // Note: In the original JavaScript, studentID 11 had two values (90 and 95), using the last one
    
    grades.put(101, course101Grades);
    grades.put(102, course102Grades);
    grades.put(103, course103Grades);
    grades.put(104, course104Grades);
    
    java.util.Map<Integer, Integer> courseGrades = grades.get(courseID);
    if (courseGrades == null) {
        return "Grade not found for this student in the specified course.";
    }
    
    Integer grade = courseGrades.get(studentID);
    if (grade == null) {
        return "Grade not found for this student in the specified course.";
    }
    
    return grade.toString();
}

public static String assignHomework(int studentID, String homeworkDetails) {
    return "Homework assigned to Student ID " + studentID + ": " + homeworkDetails;
}

public static String createCourseOutline(int courseID, String[] topics) {
    StringBuilder outline = new StringBuilder("Course Outline for Course ID " + courseID + ":\n");
    
    for (String topic : topics) {
        outline.append("- ").append(topic).append("\n");
    }
    
    return outline.toString().trim();
}
public static String[] createCourseSchedule(String semester) {
    java.util.Map<String, String[]> schedules = new java.util.HashMap<>();
    
    schedules.put("Semester1", new String[]{
        "Math 101 - Monday, Wednesday, Friday 9:00 AM - 10:30 AM",
        "Biology 102 - Tuesday, Thursday 11:00 AM - 12:30 PM",
        "History 201 - Monday, Wednesday 1:00 PM - 2:30 PM"
    });
    
    schedules.put("Semester2", new String[]{
        "Chemistry 101 - Monday, Wednesday, Friday 10:00 AM - 11:30 AM",
        "Physics 102 - Tuesday, Thursday 1:00 PM - 2:30 PM",
        "English 201 - Monday, Wednesday 3:00 PM - 4:30 PM"
    });
    
    return schedules.getOrDefault(semester, new String[]{"No available course schedule for this semester."});
}

public static String[] checkPrerequisite(String course, String[] studentCourses) {
    java.util.Map<String, String[]> coursePrerequisites = new java.util.HashMap<>();
    
    coursePrerequisites.put("Math 101", new String[]{});
    coursePrerequisites.put("Biology 102", new String[]{"Math 101"});
    coursePrerequisites.put("History 201", new String[]{});
    coursePrerequisites.put("Chemistry 101", new String[]{"Biology 102"});
    coursePrerequisites.put("Physics 102", new String[]{"Math 101"});
    coursePrerequisites.put("English 201", new String[]{});
    
    String[] requiredPrerequisites = coursePrerequisites.getOrDefault(course, new String[]{});
    java.util.List<String> unmetPrerequisites = new java.util.ArrayList<>();
    
    for (String prerequisite : requiredPrerequisites) {
        boolean found = false;
        for (String studentCourse : studentCourses) {
            if (prerequisite.equals(studentCourse)) {
                found = true;
                break;
            }
        }
        if (!found) {
            unmetPrerequisites.add(prerequisite);
        }
    }
    
    if (unmetPrerequisites.isEmpty()) {
        return new String[]{"You meet the prerequisites for this course."};
    } else {
        String[] result = new String[unmetPrerequisites.size() + 1];
        result[0] = "You need to complete the following prerequisites:";
        for (int i = 0; i < unmetPrerequisites.size(); i++) {
            result[i + 1] = unmetPrerequisites.get(i);
        }
        return result;
    }
}
public static String generateStudentReportCard(int studentID) {
    java.util.Map<Integer, java.util.Map<String, Object>> studentRecords = new java.util.HashMap<>();
    // Ensure courses are properly initialized
    java.util.Map<String, String> student1Courses = new java.util.HashMap<>();
    student1Courses.put("Math", "A");
    student1Courses.put("Science", "B+");
    student1Courses.put("English", "A-");
    student1Courses.put("History", "B");

    java.util.Map<String, String> student2Courses = new java.util.HashMap<>();
    student2Courses.put("Math", "B+");
    student2Courses.put("Science", "A");
    student2Courses.put("English", "B+");
    student2Courses.put("Geography", "A-");

    java.util.Map<String, String> student3Courses = new java.util.HashMap<>();
    student3Courses.put("Math", "A-");
    student3Courses.put("Science", "B");
    student3Courses.put("English", "A");
    student3Courses.put("History", "B+");

    java.util.Map<String, String> student4Courses = new java.util.HashMap<>();
    student4Courses.put("Math", "B");
    student4Courses.put("Science", "B+");
    student4Courses.put("English", "B");
    student4Courses.put("Geography", "A");

    java.util.Map<String, String> student5Courses = new java.util.HashMap<>();
    student5Courses.put("Math", "A");
    student5Courses.put("Science", "A-");
    student5Courses.put("English", "B+");
    student5Courses.put("History", "A-");

    java.util.Map<String, String> student6Courses = new java.util.HashMap<>();
    student6Courses.put("Math", "B+");
    student6Courses.put("Science", "B");
    student6Courses.put("English", "A");
    student6Courses.put("Geography", "B+");

    java.util.Map<String, Object> student1 = new java.util.HashMap<>();
    student1.put("name", "Bhoomi");
    student1.put("courses", student1Courses);

    java.util.Map<String, Object> student2 = new java.util.HashMap<>();
    student2.put("name", "Nandini");
    student2.put("courses", student2Courses);

    java.util.Map<String, Object> student3 = new java.util.HashMap<>();
    student3.put("name", "Aditi");
    student3.put("courses", student3Courses);

    java.util.Map<String, Object> student4 = new java.util.HashMap<>();
    student4.put("name", "Pantu");
    student4.put("courses", student4Courses);

    java.util.Map<String, Object> student5 = new java.util.HashMap<>();
    student5.put("name", "Jhandu");
    student5.put("courses", student5Courses);

    java.util.Map<String, Object> student6 = new java.util.HashMap<>();
    student6.put("name", "Mandu");
    student6.put("courses", student6Courses);

    studentRecords.put(1, student1);
    studentRecords.put(2, student2);
    studentRecords.put(3, student3);
    studentRecords.put(4, student4);
    studentRecords.put(5, student5);
    studentRecords.put(6, student6);

    java.util.Map<String, Object> student = studentRecords.get(studentID);
    if (student == null) {
        return "Student not found.";
    }

    StringBuilder reportCard = new StringBuilder();
    reportCard.append("Student Name: ").append(student.get("name")).append("\n");

    @SuppressWarnings("unchecked")
    java.util.Map<String, String> courses = (java.util.Map<String, String>) student.get("courses");
    if (courses == null) {
        return "No courses found for this student.";
    }

    reportCard.append("Courses:\n");
    for (java.util.Map.Entry<String, String> entry : courses.entrySet()) {
        reportCard.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
    }

    return reportCard.toString();
}
}
