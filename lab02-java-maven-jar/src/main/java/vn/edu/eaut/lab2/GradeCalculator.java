package vn.edu.eaut.lab2;

public class GradeCalculator {

    // Kiểm tra điểm có hợp lệ trong khoảng 0 đến 10 hay không
    public static boolean isValidScore(double score) {
        return score >= 0.0 && score <= 10.0;
    }

    // Tính điểm tổng kết: Chuyên cần 10%, Giữa kỳ 30%, Cuối kỳ 60%
    public static double calculateFinalGrade(double attendance, double midterm, double finalExam) {
        return (attendance * 0.10) + (midterm * 0.30) + (finalExam * 0.60);
    }

    // Xếp loại theo thang điểm A, B, C, D, F
    public static String classifyGrade(double finalGrade) {
        if (finalGrade >= 8.5) return "A";
        if (finalGrade >= 7.0) return "B";
        if (finalGrade >= 5.5) return "C";
        if (finalGrade >= 4.0) return "D";
        return "F";
    }
}