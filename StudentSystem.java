import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class StudentSystem {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        try {
            Class.forName("org.mariadb.jdbc.Driver");

            Connection con = DriverManager.getConnection(
                "jdbc:mariadb://localhost:3306/college",
                "studentuser",
                "student123"
            );

            System.out.println("Database Connected Successfully!");

            int choice;

            do {
                System.out.println();
                System.out.println("===== STUDENT MANAGEMENT SYSTEM =====");
                System.out.println("1. Add Student");
                System.out.println("2. View Students");
                System.out.println("3. Search Student");
                System.out.println("4. Update Student");
                System.out.println("5. Delete Student");
                System.out.println("6. Exit");
                System.out.print("Enter your choice: ");

                choice = sc.nextInt();
                sc.nextLine();

                if (choice == 1) {

                    System.out.print("Enter student name: ");
                    String name = sc.nextLine();

                    System.out.print("Enter register number: ");
                    String regNo = sc.nextLine();

                    System.out.print("Enter department: ");
                    String department = sc.nextLine();

                    System.out.print("Enter year: ");
                    int year = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Enter email: ");
                    String email = sc.nextLine();

                    String sql = "INSERT INTO students " +
                                 "(name, reg_no, department, year, email) " +
                                 "VALUES (?, ?, ?, ?, ?)";

                    PreparedStatement ps = con.prepareStatement(sql);

                    ps.setString(1, name);
                    ps.setString(2, regNo);
                    ps.setString(3, department);
                    ps.setInt(4, year);
                    ps.setString(5, email);

                    ps.executeUpdate();

                    System.out.println("Student added successfully!");

                    ps.close();
                }

                else if (choice == 2) {

                    String sql = "SELECT * FROM students";

                    PreparedStatement ps = con.prepareStatement(sql);
                    ResultSet rs = ps.executeQuery();

                    System.out.println();
                    System.out.println("========== STUDENT DETAILS ==========");

                    boolean found = false;

                    while (rs.next()) {

                        found = true;

                        System.out.println("ID          : " + rs.getInt("id"));
                        System.out.println("Name        : " + rs.getString("name"));
                        System.out.println("Register No : " + rs.getString("reg_no"));
                        System.out.println("Department  : " + rs.getString("department"));
                        System.out.println("Year        : " + rs.getInt("year"));
                        System.out.println("Email       : " + rs.getString("email"));
                        System.out.println("-------------------------------------");
                    }

                    if (found == false) {
                        System.out.println("No students found.");
                    }

                    rs.close();
                    ps.close();
                }

                else if (choice == 3) {

                    System.out.print("Enter register number to search: ");
                    String regNo = sc.nextLine();

                    String sql = "SELECT * FROM students WHERE reg_no = ?";

                    PreparedStatement ps = con.prepareStatement(sql);

                    ps.setString(1, regNo);

                    ResultSet rs = ps.executeQuery();

                    if (rs.next()) {

                        System.out.println();
                        System.out.println("Student Found!");
                        System.out.println("ID          : " + rs.getInt("id"));
                        System.out.println("Name        : " + rs.getString("name"));
                        System.out.println("Register No : " + rs.getString("reg_no"));
                        System.out.println("Department  : " + rs.getString("department"));
                        System.out.println("Year        : " + rs.getInt("year"));
                        System.out.println("Email       : " + rs.getString("email"));

                    } else {
                        System.out.println("Student not found!");
                    }

                    rs.close();
                    ps.close();
                }

                else if (choice == 4) {

                    System.out.print("Enter register number to update: ");
                    String regNo = sc.nextLine();

                    System.out.print("Enter new name: ");
                    String name = sc.nextLine();

                    System.out.print("Enter new department: ");
                    String department = sc.nextLine();

                    System.out.print("Enter new year: ");
                    int year = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Enter new email: ");
                    String email = sc.nextLine();

                    String sql = "UPDATE students SET name = ?, " +
                                 "department = ?, year = ?, email = ? " +
                                 "WHERE reg_no = ?";

                    PreparedStatement ps = con.prepareStatement(sql);

                    ps.setString(1, name);
                    ps.setString(2, department);
                    ps.setInt(3, year);
                    ps.setString(4, email);
                    ps.setString(5, regNo);

                    int rows = ps.executeUpdate();

                    if (rows > 0) {
                        System.out.println("Student updated successfully!");
                    } else {
                        System.out.println("Student not found!");
                    }

                    ps.close();
                }

                else if (choice == 5) {

                    System.out.print("Enter register number to delete: ");
                    String regNo = sc.nextLine();

                    String sql = "DELETE FROM students WHERE reg_no = ?";

                    PreparedStatement ps = con.prepareStatement(sql);

                    ps.setString(1, regNo);

                    int rows = ps.executeUpdate();

                    if (rows > 0) {
                        System.out.println("Student deleted successfully!");
                    } else {
                        System.out.println("Student not found!");
                    }

                    ps.close();
                }

                else if (choice == 6) {

                    System.out.println("Thank you!");
                    System.out.println("Program exited.");

                }

                else {
                    System.out.println("Invalid choice!");
                }

            } while (choice != 6);

            con.close();
            sc.close();

        } catch (Exception e) {

            System.out.println("Error!");
            System.out.println(e);
        }
    }
}
