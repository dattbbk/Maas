package dhbk.maas.mahout.servlet.recommendation;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Recommender
 */
@WebServlet("/Recommender")
public class Recommender extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public final String cmd = "hadoop jar /home/hadoop/mahout-distribution-0.7/mahout-core-0.7-job.jar org.apache.mahout.cf.taste.hadoop.item.RecommenderJob -s SIMILARITY_COOCCURRENCE --input u.data --output outputrecommender";
    public final String cmdget = "hadoop fs -getmerge output /home/hadoop/outputrecommender.txt";   
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Recommender() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		deleteFileIfNeed();
		
		PrintWriter out = response.getWriter() ;
		
		exc (this.cmd)	;
		
		exc (this.cmdget) ;
		
		@SuppressWarnings("resource")
		BufferedReader reader = new BufferedReader(new FileReader("/home/hadoop/outputrecommender.txt")) ;
		String line = "";
		StringBuffer buff = new StringBuffer() ;
		while ((line = reader.readLine()) != null) {
			buff.append(line) ;
		}
		
		out.print(buff.toString());
		
		deleteFileIfNeed();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}
	
	private void deleteFileIfNeed () {
		try {
			String cmd1 = "hadoop dfs -rmr outputrecommender" ;
			String cmd2 = "hadoop dfs -rmr temp" ;
			String cmd3 = "rm /home/hadoop/outputrecommender.txt" ;
			
			exc (cmd1) ;
			exc (cmd2) ;
			exc (cmd3) ;
		
		} catch (Exception e) {}
	}
	
	private Process exc (String cmd) throws IOException{
		Process p = null;
		p = Runtime.getRuntime().exec(cmd) ;
		p.getErrorStream() ;
		try {
			p.waitFor() ;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			p.destroy(); 
		}
		return p ;
	}

}
