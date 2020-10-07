import java.sql.*;
public class DbHelper {

private final String pass="123";
private final String url="jdbc:postgresql://localhost:5432/dbproject";
    public Connection getConnection() throws SQLException{
    return DriverManager.getConnection(url, Main.user, pass);
}
public void showErrorMessage(SQLException exception){
    System.out.println("Error :" +exception.getMessage());
    System.out.println("Error code :"+exception.getErrorCode());
}

}
 