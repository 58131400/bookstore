package model;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;









public class DBUtils {
private static String jdbcURL ="jdbc:mysql://localhost/bookstore";
private static String jdbcUsername ="root";
private static String jdbcPassword = "";

//thuc hien ket noi den database va tra ve doi tuong kieu connection
//no giu kenh ket noi den csdl
protected static Connection ConnectDB() throws SQLException{
	Connection jdbcConnection;
	try {
		Class.forName("com.mysql.jdbc.Driver");
		jdbcConnection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
		
		
	}catch (ClassNotFoundException e) {
		
		throw new SQLException(e);
		// TODO: handle exception
		
	}
	try {
	jdbcConnection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
	}
	catch (SQLException ex) {
		// TODO: handle exception
		 System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
	}
	return jdbcConnection;
}
// ham them 1 sach
public static void insert(Sach newbook) throws SQLException{
	// ket noi db
	Connection connection = ConnectDB();
	//truy van dl
	String sql =  "INSERT INTO sach(title,author,price) values(?,?,?)";
	PreparedStatement statement = connection.prepareStatement(sql);
	//truyen tham so
	statement.setString(1, newbook.getTitle());
	statement.setString(2, newbook.getAuthor());
	statement.setFloat(3, newbook.getPrice());
	//thuc hien truy van
	statement.executeUpdate();
	//dong ket noi
	statement.close();
	connection.close();
	
}
public static ArrayList<Sach> getByAll() throws SQLException{
	ArrayList<Sach> listBook = new ArrayList<>();
	
	Connection connection  = ConnectDB();
	String sql = "SELECT * from sach";
	Statement statement =  connection.createStatement();
	ResultSet bangKetQua = statement.executeQuery(sql);
	
	//duyet kq
	while(bangKetQua.next())
	{
		int id = bangKetQua.getInt("id");
		String title = bangKetQua.getString("title");
		String author = bangKetQua.getString("author");
		float price = bangKetQua.getFloat("price");
		//goi lai trong bien book
		Sach book = new Sach(id,title,author,price);
		listBook.add(book);
		
	}
	bangKetQua.close();
	statement.close();
	connection.close();
	return  listBook;
}
public static void delete(int idbook) throws SQLException{
	Connection connection = ConnectDB();
	String sql = "DELETE from sach where id = ?";
	PreparedStatement statement  = connection.prepareStatement(sql);
	statement.setInt(1,idbook);
	statement.executeUpdate();
	statement.close();
	connection.close();
}
public static void update(Sach book) throws SQLException{
	Connection connection = ConnectDB();
	String sql = "Update sach set title =?, author =?, price =?";
	sql+= "where id = ?";
	PreparedStatement statement =  connection.prepareStatement(sql);
	statement.setString(1, book.getTitle());
	statement.setString(2, book.getAuthor());
	statement.setFloat(3, book.getPrice());
	statement.setInt(4, book.getId());
	
	statement.executeUpdate();
	statement.close();
	connection.close();
}
public static Sach getByID(int id) throws SQLException{
	Connection connection = ConnectDB();
	String sql = "Select * from sach where id =?";
	Sach book = null;
	PreparedStatement statement  = connection.prepareStatement(sql);
	statement.setInt(1, id);
	ResultSet resultSet =statement.executeQuery();
	if(resultSet.next())
	{
		String title = resultSet.getString("title");
		String author = resultSet.getString("author");
		
		float price = resultSet.getFloat("price");
		book = new Sach(id, title, author, price);
		
	}
	resultSet.close();
	statement.close();
	connection.close();
	return book;
}
}
