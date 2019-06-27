package Controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.DBUtils;
import model.Sach;

/**
 * Servlet implementation class BookManageServlet
 */
@WebServlet("/")
public class BookManageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BookManageServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	String action = request.getServletPath();
	try
	{
		switch (action) {
		case "/detail":
			showBook(request,response);
			break;

		case "/new":
			showNewForm(request,response);
			break;
		case "/insert":
			insertBook(request,response);
			break;
		case "/edit":
			showEditForm(request,response);
			break;
		case "/update":
			updateBook(request,response);
			break;
		case "/":
			listBook(request,response);
			break;
		case "/list":
			listBook(request,response);
			break;
		case "/delete":
			deleteBook(request,response);
			break;
		default:
			
			break;
		}
	}catch (SQLException e) {
		throw new ServletException(e);
		// TODO: handle exception
	}
	
	}
	
	//ham hien tat ca sach
	private void listBook(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException,ServletException
	{
		//lay ve tat ca sach
		ArrayList<Sach> listBook = DBUtils.getByAll();
		//su dung chuc nang dieu huong
		RequestDispatcher dispatcher = request.getRequestDispatcher("ListAllBook.jsp");
		request.setAttribute("listBook", listBook);
		dispatcher.forward(request, response);
	}
	
	private void showBook(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException,ServletException
	{
		//lay ma sach tu url
		int id = Integer.parseInt(request.getParameter("id"));
		//tim sach can hien chi tiet
		Sach book = DBUtils.getByID(id);
		//su dung chuc nang dieu huoing
		RequestDispatcher dispatcher = request.getRequestDispatcher("BookDetails.jsp");
		request.setAttribute("book", book);
		dispatcher.forward(request, response);
	}
	private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		RequestDispatcher dispatcher = request.getRequestDispatcher("BookAdd.jsp");
		//thuc hien dieu huong
		dispatcher.forward(request, response);
		
	}
private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException,SQLException, IOException{
	//lay ma sach cap nhat tu url
	int id = Integer.parseInt(request.getParameter("id"));
	Sach existingBook = DBUtils.getByID(id);
	RequestDispatcher dispatcher = request.getRequestDispatcher("BookEdit.jsp");
	request.setAttribute("book", existingBook);
	dispatcher.forward(request, response);
}
private void insertBook(HttpServletRequest request, HttpServletResponse response) throws  IOException,SQLException{
	String title =  request.getParameter("title");
	String author = request.getParameter("author");
	float price = Float.parseFloat(request.getParameter("price"));
	Sach  book =new Sach(title,author,price);
	DBUtils.insert(book);
	response.sendRedirect("list");
}
private void updateBook(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException{
	int id =  Integer.parseInt(request.getParameter("id"));
	String title = request.getParameter("title");
	String author = request.getParameter("author");
	float price = Float.parseFloat(request.getParameter("price"));
	Sach updateBook = new Sach(id,title,author,price);
	DBUtils.update(updateBook);
	response.sendRedirect("list");
}

private void deleteBook(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException,SQLException{
	int id = Integer.parseInt(request.getParameter("id"));
	DBUtils.delete(id);
	response.sendRedirect("list");

}


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
