package dhbk.maas.servlet.directorymanage;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ListADirectory
 */
@WebServlet("/ListADirectory")
public class ListADirectory extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListADirectory() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		PrintWriter out = response.getWriter() ;
//		
//		String path = request.getParameter("path") ;
//		File file = new File(path);
//		File[] files = file.listFiles() ;
//		StringBuffer s = new StringBuffer() ;
//		for(File f : files) {
//			s.append(f.getName());
//			s.append("\n");
//		}
//		
//		out.print(s.toString());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter() ;
		
		String path = request.getParameter("path") ;
	
		if(path != null ) {
			File file = new File(path);
			File[] files = file.listFiles() ;
			StringBuffer s = new StringBuffer() ;
			for(File f : files) {
				s.append(f.getName());
				s.append("\n") ;
			}
			out.print(s.toString());
		}
	}

}
