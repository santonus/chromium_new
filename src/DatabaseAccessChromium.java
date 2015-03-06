import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import au.com.bytecode.opencsv.CSVReader;
public class DatabaseAccessChromium {

	public Connection con;
	public ResultSet rs, rs2;
	public Statement s;
	public Statement s2;
	
	private NetworkBuilder nb= new NetworkBuilder();
	
	private String fileContent;
	private String fileName;
	private String dbName;
	
	private int num;
	
	public DatabaseAccessChromium () {

		fileContent ="";
		fileName="";
		
	}
	public String getFileContent()
	{
		return fileContent;
	}
	
	public String getFileName()
	{
		return fileName;
	}
	
	public String getDBName()
	{
		return dbName;
	}
	
	
	public boolean openConnection(String databaseName, String mysqlUser, String password) throws Exception
	{
		dbName = databaseName;
		Class.forName("com.mysql.jdbc.Driver"); //load mysql driver
		try
		{
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/" + databaseName + "?user=" + mysqlUser + "&password=" + password); //set-up connection with database
			s = con.createStatement(); //Statements to issue sql queries
			s2 = con.createStatement();
		} catch (SQLException e) 
		{
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	public String[]  getMinMax() throws Exception {
		 
		System.out.println("Calculating Min and Max date From The database");
		String[] list=null;
		//System.out.println("connection object"+s);
		rs= s.executeQuery("select min(date),max(date) from comment");
		boolean status;
		status=rs.next();
		if(status)
		{
		 list=new String[2];
		 list[0]=rs.getString(1);
		 list[1]=rs.getString(2);
		}
		return list;
		
	}
	
	public String getOwnermail() throws Exception {
		System.out.println("Extracting OwnerMail List");
		//rs=s.executeQuery("select owner_email from person");
		rs=s.executeQuery("select sender from comment");
		boolean status;
		status =rs.next();
		String ownerList=null;// new ArrayList<String>();
		while(status)
		{
			//ownerlist.add(rs.getString(1));
			ownerList=ownerList+"\'"+rs.getString(1)+"\'"+",";
			status=rs.next();
		}
		
		return ownerList.substring(0,ownerList.length()-1);
		
		
	}
	public void generateDRON(String ownerList, String startDate, String endDate) throws Exception
	{
		ArrayList<String> developers = new ArrayList<String>();
		ArrayList<String> developers2= new ArrayList<String>();
		ArrayList<String> developers3= new ArrayList<String>();
		ArrayList<Integer> edges     = new ArrayList<Integer>();
		
		
		System.out.println("");
		System.out.println("Calculating the Total Number of Distinct Developers...");
		String qry;
		if(ownerList!=null)
		{
			qry="select distinct sender from comment where sender in ("+ownerList+") and date>='"+startDate+ "' and date<='"+endDate+"'";
			System.out.println(qry);
		}
		else
		{
			 qry="select distinct sender from comment where  date>='"+startDate+ "' and date<='"+endDate+"'";	
			 System.out.println("manish"+qry);
		}
				
		System.out.println(qry);
		rs = s.executeQuery(qry);
			
		while(rs.next())
		{
			developers.add(rs.getString("sender"));
		}
		
		num=developers.size();
	    if(ownerList!=null)
	    	qry="select a.sender,b.recipients from comment a , temp_comment b where a.id=b.id and a.sender!=b.recipients and a.sender in ("+ownerList+") and a.date>='"+startDate+"' and date<='"+endDate+"' and b.recipients in ("+ownerList+")group by a.sender,b.recipients";
	    else
	    	qry="select a.sender,b.recipients from comment a , temp_comment b where a.id=b.id and a.sender!=b.recipients and  a.date>='"+startDate+"' and date<='"+endDate+"' group by a.sender,b.recipients";
				
		rs = s.executeQuery(qry);
		
		while(rs.next())
		{
			developers2.add(rs.getString("sender"));
			developers3.add(rs.getString("recipients"));
			edges.add(1);
		}
		
		fileContent = nb.networkBuilder(developers, developers2, developers3, edges, num);
		
	}
	
}
