package wasdev.sample.servlet;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;

import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.annotation.Resource;
import javax.servlet.annotation.MultipartConfig;

import java.util.Arrays;
import java.util.LinkedHashMap;

import org.ektorp.CouchDbConnector;
import org.ektorp.CouchDbInstance;
import org.ektorp.AttachmentInputStream;

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
        String attID = request.getParameter("attID");
        String getAttachment = request.getParameter("getAttachment");
        
        
        dbc = _db.createConnector("json_db", true);
		
		
		if(getAttachment != null && getAttachment.equalsIgnoreCase("on")){
			AttachmentInputStream ais = dbc.getAttachment(dbName, attID);
			
			String type = ais.getContentType();
		
			BufferedReader bf = new BufferedReader(new InputStreamReader(ais));
		
			String content = bf.readLine();
			
			bf.close();
			
			response.getWriter().print(content + ";" + type);
			
		} else {
		
		Map<String, String> map = dbc.get(Map.class, dbName);
		/*
		for (String docID : dbc.getAllDocIds()) {
				contentMap = dbc.get(Map.class, docID);
				}
				
		*/
		
		String myID = map.get("_id");
		String myRev = map.get("_rev");
		String myAuthour = map.get("author");
        
        response.getWriter().print(myID + " " + myRev + " "+myAuthour);
		
		}
			
			
		
		
    }

}
