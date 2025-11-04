import java.util.*;

public class lexicalAnalysis {
      
        public double calculateGPA(double[] grades) {
            double totalPoints = 0;
            for (double grade : grades) {
                totalPoints += grade;
            }
            double average = totalPoints / grades.length;
            return (average / 100) * 10;
        }
        
        public double calculateCGPA(double[] gpas) {
            double totalPoints = 0;
            for (double gpa : gpas) {
                totalPoints += gpa;
            }
            return totalPoints / gpas.length;
        }
        
        public double calculateFinalGrade(double midTermGrade, double finalExamGrade) {
            return (midTermGrade * 0.2) + (finalExamGrade * 0.5);
        }
        
        public double calculatePercentage(double marksObtained, double totalMarks) {
            return (marksObtained / totalMarks) * 100;
        }
        
        public String checkPassFail(double grade) {
            return grade >= 35 ? "Pass" : "Fail";
        }
        
        public char calculateGrade(double percentage) {
            if (percentage >= 85) return 'O';
            else if (percentage >= 75) return 'A';
            else if (percentage >= 65) return 'B';
            else if (percentage >= 55) return 'C';
            else if (percentage >= 45) return 'D';
            else return 'F';
        }
        
        public double calculateSubjectAverage(double[] subjectGrades) {
            double total = 0;
            for (double grade : subjectGrades) {
                total += grade;
            }
            return total / subjectGrades.length;
        }
        
        public double calculateClassAverage(double[][] grades) {
            double total = 0;
            int count = 0;
            for (double[] studentGrades : grades) {
                for (double grade : studentGrades) {
                    total += grade;
                    count++;
                }
            }
            return total / count;
        }
        
        public boolean isStudentEligibleForScholarship(double gpa, int extracurriculars) {
            return gpa >= 3.5 && extracurriculars > 2;
        }
        
        public String createStudyPlan(String subject, int studyHours, int daysAvailable) {
            if (studyHours <= 0 || daysAvailable <= 0) {
                return "Error: Study hours and days must be positive.";
            }
            double hoursPerDay = (double) studyHours / daysAvailable;
            return "Study Plan for " + subject + "\n" +
                   "Total Study Hours: " + studyHours + "\n" +
                   "Days Available: " + daysAvailable + "\n" +
                   "Hours Per Day: " + String.format("%.2f", hoursPerDay);
        }
        
        public String adjustStudyPlanForDifficulty(String subject, String difficultyLevel) {
            double difficultyFactor;
            switch (difficultyLevel.toLowerCase()) {
                case "easy": difficultyFactor = 0.8; break;
                case "medium": difficultyFactor = 1; break;
                case "hard": difficultyFactor = 1.5; break;
                case "very hard": difficultyFactor = 2; break;
                default: return "Error: Invalid difficulty level.";
            }
            return "Study Plan Adjustment for " + subject + "\n" +
                   "Difficulty Factor: " + difficultyFactor + "x\n" +
                   "Break frequency: Every " + Math.max(1, (int) Math.floor(3 / difficultyFactor)) + " hour(s)\n";
        }

    public static void main(String[] args) {
    }
}
