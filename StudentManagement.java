import java.sql.DriverManager;

public class StudentManagement {

    public static void main(String[] args) {

        try {
            Class.forName("org.mariadb.jdbc.Driver");

            Connection con = DriverManager.getConnection(
                "jdbc:mariadb://localhost:3306/college",
                "root",
                ""
            );

            System.out.println("Database Connected Successfully!");

            con.close();

        } catch (Exception e) {
            System.out.println("Connection Failed!");
            System.out.println(e);
        }
    }
}
