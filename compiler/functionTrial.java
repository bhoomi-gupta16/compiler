public class functionTrial {

    public static double accuracy(int TP, int TN, int FP, int FN) {
        int correctPredictions = TP + TN;
        int totalPredictions = TP + TN + FP + FN;
        return (double) correctPredictions / totalPredictions;
    }

    public static void main(String[] args) {
        int TP = 50; 
        int TN = 30; 
        int FP = 10; 
        int FN = 5;
        double acc = accuracy(TP, TN, FP, FN);
        // List<String> keywords= new ArrayList<>();
        // keywords.add("accuracy");
        // then use accuracy somehow??
        System.out.println("Accuracy: " + acc);
    }
}
