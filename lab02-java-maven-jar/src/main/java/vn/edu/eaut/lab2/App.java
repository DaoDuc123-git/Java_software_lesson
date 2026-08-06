package vn.edu.eaut.lab2;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== CHUONG TRINH QUAN LY DIEM SINH VIEN ===");
        
        System.out.print("Nhap ma sinh vien: ");
        String studentId = scanner.nextLine();

        System.out.print("Nhap ho ten sinh vien: ");
        String fullName = scanner.nextLine();

        double attendance = inputScore(scanner, "Nhap diem chuyen can (10%): ");
        double midterm = inputScore(scanner, "Nhap diem giua ky (30%): ");
        double finalExam = inputScore(scanner, "Nhap diem cuoi ky (60%): ");

        Student student = new Student(studentId, fullName, attendance, midterm, finalExam);

        double totalGrade = GradeCalculator.calculateFinalGrade(
            student.getAttendanceScore(), 
            student.getMidtermScore(), 
            student.getFinalScore()
        );

        String classification = GradeCalculator.classifyGrade(totalGrade);

        System.out.println("\n---------------- KET QUA ----------------");
        System.out.printf("%s - %s - %.2f - %s\n", 
            student.getStudentId(), 
            student.getFullName(), 
            totalGrade, 
            classification
        );

        scanner.close();
    }

    // Hàm phụ trợ vòng lặp bắt nhập lại nếu điểm ngoài khoảng 0 - 10
    private static double inputScore(Scanner scanner, String prompt) {
        double score;
        while (true) {
            System.out.print(prompt);
            score = scanner.nextDouble();
            if (GradeCalculator.isValidScore(score)) {
                break;
            }
            System.out.println("Diem " + score + " -> khong hop le! Vui long nhap lai trong khoang 0-10.");
        }
        return score;
    }
}