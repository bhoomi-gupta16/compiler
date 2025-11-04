// import java.util.*;


// public class FunctionTokenizer {
// enum TokenType {KT,IT,SCT,OT,DT}
//     private static final Set<String> knownKeywords = new HashSet<>(Arrays.asList(
//         "delimiter","function","mean","division","multiply","sub","equals","nequals","square","sqrt","negate","asc","log","greater","mod"
//     ));

    
//     static final Set<Character> delimiters = new HashSet<>(Arrays.asList(';', '(', ')', '[', ']'));
//     static List<Pair<String, TokenType>> tokenizeFunction(String functionDeclaration) {
//         List<Pair<String, TokenType>> tokens = new ArrayList<>();
//         String[] parts = functionDeclaration.split("\\s+|(?=[();,])|(?<=[();,])");
//         boolean isFunctionName = false;
//         boolean isParameter = false;

//         for (String part : parts) {
//             if (part.isEmpty()) continue;

//             if (knownKeywords.contains(part)) {
//                 tokens.add(new Pair<>(part, TokenType.KT));
//             } else if (delimiters.contains(part.charAt(0))) {
//                 tokens.add(new Pair<>(part, TokenType.DT));
//                 isFunctionName = false;
//                 isParameter = part.equals("(");
//             } else if (isFunctionName) {
//                 tokens.add(new Pair<>(part, TokenType.KT));
//                 isFunctionName = false;
//             } else if (isParameter) {
//                 tokens.add(new Pair<>(part, TokenType.IT));
//             } else {
//                 isFunctionName = true;
//                 tokens.add(new Pair<>(part, TokenType.KT));
//             }
//         }
//         return tokens;
//     }

//     private static void printTokens(List<Pair<String, TokenType>> tokens) {
//         for (Pair<String, TokenType> token : tokens) {
//             System.out.println("Token: " + token.getKey() + " -> " + token.getValue());
//         }
//     }

//     public static void main(String[] args) {
//         List<String> functionDeclarations = Arrays.asList(
//             "double mean(double []nums)",
//             "double median(double[]nums)",
//             "double variance(double[]nums)",
//             "double sd(double nums[])",
//             "double skewness(double[]nums)",
//             "double kurtosis(double[]nums)",
//             "double covariance(double num[])",
//             "double calculateAccuracy(int TP, int TN, int FP, int FN);",
//             "double calculatePrecision(int TP, int FP);",
//             "double calculateRecall(int TP, int FN);",
//             "double calculateF1Score(double precision, double recall);",
//             "double calculateAUCROC(double[] TPR, double[] FPR);",
//             "double calculateLogLoss(double[] yTrue, double[] yPred);",
//             "double calculateMSE(double[] yTrue, double[] yPred);",
//             "double calculateRMSE(double mse);",
//             "double calculateR2Score(double[] yTrue, double[] yPred);",
//             "double calculateTruePositiveRate(int TP, int FN);",
//             "double calculateFalsePositiveRate(int FP, int TN);",
//             "double calculateSpecificity(int TN, int FP);",
//             "double calculateBalancedAccuracy(double sensitivity, double specificity);",
//             "double calculateAdjustedRSquared(double rSquared, int n, int k);",
//             "double calculateGiniCoefficient(double[] values);",
//             "double calculateCalibrationError(double[] yTrue, double[] yPred);",
//             "double calculateBrierScore(double[] yTrue, double[] yPred);",
//             "double calculateEntropyLoss(double[] probabilities);",
//             "double calculateMeanAbsoluteError(double[] yTrue, double[] yPred);",
//             "double calculateExplainedVarianceScore(double[] yTrue, double[] yPred);",
//             "double calculateJaccardIndex(double[] setA, double[] setB);",
//             "double calculateHammingLoss(double[] yTrue, double[] yPred);",
//             "double calculateMAPE(double[] yTrue, double[] yPred);",
//             "double calculateSMAPE(double[] yTrue, double[] yPred);",
//             "double calculateWAPE(double[] yTrue, double[] yPred, double[] weights);",
//             "double calculateMASE(double[] yTrue, double[] yPred, double[] seasonalError);",
//             "double calculateMSPE(double[] yTrue, double[] yPred);",
//             "double calculateMDA(double[] yTrue, double[] yPred);",
//             "double calculateMeanPoissonDeviance(double[] yTrue, double[] yPred);",
//             "double calculateMeanGammaDeviance(double[] yTrue, double[] yPred);",
//             "double calculateP4Metric(double[] yTrue, double[] yPred);",
//             "double calculateCohenKappa(double[] yTrue, double[] yPred);",
//             "double calculatePhiCoefficient(int TP, int TN, int FP, int FN);",
//             "double calculateCumulativeGain(double[] relevanceScores);",
//             "double calculateDCG(double[] relevanceScores);",
//             "double calculateNDCG(double DCG, double IDCG);",
//             "double calculateHitRate(int hits, int total);",
//             "double calculateSpearmanRankCorrelation(double[] x, double[] y);",
//             "double calculateMAPScore(double[] precisionValues);",
//             " double add(double[]num);",
//             " double subtract(double[]num);",
//             " double divide(double[]num);",
//             " double remainder(double[]num);"
//         );    
//         for (String functionDeclaration : functionDeclarations) {
//             System.out.println("Function Declaration: " + functionDeclaration);
//             List<Pair<String, TokenType>> tokens = tokenizeFunction(functionDeclaration);
//             printTokens(tokens);
//             System.out.println("//-------//");
//         }
//     }
//     static class Pair<K, V> {
//         private final K key;
//         private final V value;

//         public Pair(K key, V value) {
//             this.key = key;
//             this.value = value;
//         }
//         public K getKey() {
//             return key;
//         }
//         public V getValue() {
//             return value;
//         }
//     }
// }


import java.util.*;
import java.util.regex.*;

public class FunctionTokenizer {
    enum TokenType { RETURN_TYPE, FUNCTION_NAME, PARAMETER_TYPE, PARAMETER_NAME, DELIMITER, OPERATOR, SPECIAL_CHAR }

    private static final Set<String> keywords = new HashSet<>(Arrays.asList(
        "calculate", "check", "track", "create", "adjust", "suggest", "generate", "recommend", "find",
        "search", "assign"
    ));
    private static final Set<String> knownKeywords = new HashSet<>(Arrays.asList(
        "int", "double", "float", "boolean", "char", "void", "String",
        "template", "typename", "const", "vector", "pair", "function"
    ));
    private static final Set<Character> delimiters = new HashSet<>(Arrays.asList(';', '(', ')', '[', ']'));
    private static final Set<Character> operators = new HashSet<>(Arrays.asList('+', '-', '*', '/'));

    public static List<Pair<String, TokenType>> tokenizeFunctionDeclaration(String functionDeclaration) {
        List<Pair<String, TokenType>> tokens = new ArrayList<>();
        Pattern pattern = Pattern.compile("^(\\w+(\\[\\]*)*)\\s+(\\w+)\\s*\\((.*?)\\);?$");
        Matcher matcher = pattern.matcher(functionDeclaration);

        if (!matcher.matches()) {
            System.out.println("Error parsing function: " + functionDeclaration);
            return tokens;
        }

        // Extract function components
        String returnType = matcher.group(1).trim();
        String functionName = matcher.group(3).trim();
        String parameters = matcher.group(4).trim();

        // Add tokens for return type and function name
        tokens.add(new Pair<>(returnType, TokenType.RETURN_TYPE));
        tokens.add(new Pair<>(functionName, TokenType.FUNCTION_NAME));

        // Process parameters
        if (!parameters.isEmpty()) {
            String[] params = parameters.split("(?<=\\])[,\\s]+|[,\\s]+(?=\\w)");
            for (String param : params) {
                param = param.trim();
                String[] parts = param.split("\\s+");
                if (parts.length == 2) {
                    tokens.add(new Pair<>(parts[0], TokenType.PARAMETER_TYPE));
                    tokens.add(new Pair<>(parts[1], TokenType.PARAMETER_NAME));
                } else {
                    tokens.add(new Pair<>(param, TokenType.PARAMETER_TYPE)); // If not properly formatted
                }
            }
        }

        return tokens;
    }

    private static void printTokens(List<Pair<String, TokenType>> tokens) {
        for (Pair<String, TokenType> token : tokens) {
            System.out.println("Token: " + token.getKey() + " -> " + token.getValue());
        }
    }

    public static void main(String[] args) {
        List<String> functionDeclarations = Arrays.asList(
            "Bhoomi_double Bhoomi_calculateGPA(double[] Bhoomi_bhoomi_grades);",
"Bhoomi_double Bhoomi_calculateCGPA(double[] Bhoomi_grades);",
"Bhoomi_char Bhoomi_calculateFinalGrade(double Bhoomi_midTermGrade, double Bhoomi_finalExamGrade);",
"Bhoomi_double Bhoomi_calculatePercentage(double Bhoomi_marksObtained, double Bhoomi_totalMarks);",
"Bhoomi_boolean Bhoomi_checkPassFail(char Bhoomi_grade);",
"Bhoomi_char Bhoomi_calculateGrade(double Bhoomi_percentage);",
"Bhoomi_double Bhoomi_calculateSubjectAverage(double[] Bhoomi_subjectGrades);",
"Bhoomi_double Bhoomi_calculateClassAverage(double[][] Bhoomi_grades);",
"Bhoomi_void Bhoomi_trackStudentProgress(int Bhoomi_studentID);",
"Bhoomi_boolean Bhoomi_isStudentEligibleForScholarship(double Bhoomi_gpa, boolean Bhoomi_extracurriculars);",
"Bhoomi_void Bhoomi_createStudyPlan(String Bhoomi_subject, int Bhoomi_studyHours, int Bhoomi_daysAvailable);",
"Bhoomi_void Bhoomi_adjustStudyPlanForDifficulty(String Bhoomi_subject, int Bhoomi_difficultyLevel);",
"Bhoomi_String Bhoomi_suggestStudyMaterial(String Bhoomi_subject);",
"Bhoomi_double Bhoomi_calculateStudyTime(int Bhoomi_subjectDifficulty, int Bhoomi_goals);",
"Bhoomi_void Bhoomi_createStudySchedule(int Bhoomi_studyHoursPerDay, int Bhoomi_totalSubjects);",
"Bhoomi_int Bhoomi_calculateTimeToMasterSubject(int Bhoomi_totalHours, int Bhoomi_practiceRate);",
"Bhoomi_boolean Bhoomi_checkStudyBreaks(int Bhoomi_studyTime);",
"Bhoomi_double Bhoomi_calculateRevisionTime(String Bhoomi_subject, int Bhoomi_revisionSessions);",
"Bhoomi_void Bhoomi_trackStudySessions(String Bhoomi_date, String Bhoomi_subject, int Bhoomi_hours);",
"Bhoomi_String Bhoomi_suggestLearningTechniques(String Bhoomi_subject);",
"Bhoomi_void Bhoomi_generateMockTest(String Bhoomi_subject, int Bhoomi_difficulty);",
"Bhoomi_double Bhoomi_calculateTestScore(int Bhoomi_correctAnswers, int Bhoomi_totalQuestions);",
"Bhoomi_int Bhoomi_calculateExamDuration(int Bhoomi_totalQuestions, int Bhoomi_timePerQuestion);",
"Bhoomi_void Bhoomi_trackTestPerformance(double[] Bhoomi_previousScores);",
"Bhoomi_String Bhoomi_generateTestResult(String[] Bhoomi_studentAnswers, String[] Bhoomi_correctAnswers);",
"Bhoomi_void Bhoomi_suggestTestPreparationSchedule(String Bhoomi_subject, String Bhoomi_examDate);",
"Bhoomi_double Bhoomi_calculateConfidenceLevel(double[] Bhoomi_previousScores, double Bhoomi_currentPerformance);",
"Bhoomi_double Bhoomi_calculateExpectedScore(int Bhoomi_studyHours, double[] Bhoomi_practiceTestScores);",
"Bhoomi_void Bhoomi_trackStudySessionsForTest(String Bhoomi_date, String Bhoomi_subject, String[] Bhoomi_focusAreas);",
"Bhoomi_String[] Bhoomi_generatePastExamQuestions(String Bhoomi_subject);",
"Bhoomi_String Bhoomi_recommendBooks(String Bhoomi_subject);",
"Bhoomi_String Bhoomi_suggestOnlineCourses(String Bhoomi_subject);",
"Bhoomi_String[] Bhoomi_findResearchPapers(String Bhoomi_subject);",
"Bhoomi_void Bhoomi_generateFlashcards(String Bhoomi_subject, String Bhoomi_topic);"

        );

        for (String functionDeclaration : functionDeclarations) {
            System.out.println("Function Declaration: " + functionDeclaration);
            List<Pair<String, TokenType>> tokens = tokenizeFunctionDeclaration(functionDeclaration);
            printTokens(tokens);
            System.out.println("-----------------------");
        }
    }

    static class Pair<K, V> {
        private final K key;
        private final V value;

        public Pair(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }
    }
}

