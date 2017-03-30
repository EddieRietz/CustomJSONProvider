package wasdev.sample.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.annotation.Resource;

import org.ektorp.CouchDbConnector;
import org.ektorp.CouchDbInstance;

import java.util.Map;

@WebServlet("/Generator")
public class Generator extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    @Resource(name = "couchdb/JSON-DB")
	protected CouchDbInstance _db;
	protected CouchDbConnector dbc;
	

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        String dbName = request.getParameter("dbName");
        
        dbc = _db.createConnector("json_db", true);
		Map<String, String> map = dbc.get(Map.class, dbName);
		/*
		for (String docID : dbc.getAllDocIds()) {
				contentMap = dbc.get(Map.class, docID);
				}
				
		*/
		
		String myID = map.get("_id");
		String myRev = map.get("_rev");
        
        response.getWriter().print(myID + " " + myRev);
    }

}
