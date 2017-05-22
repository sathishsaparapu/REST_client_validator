import java.sql.*;
import java.util.ArrayList;
import java.util.Vector;

public class DriverData {
	Vector<String> rs_keys;
	Vector<String> uriList;
	public ArrayList<ArrayList<Object>> getDriverData(Connection c,String tableName) throws SQLException
	{
		
		//String query = "select * from "+ tableName;
		String query="SELECT * FROM OPPORTUNITIES limit 10";
		Database_events dobj=new Database_events();
		Vector<String> uri_list=new Vector<String>();
		rs_keys= new Vector<String>();
		ArrayList<ArrayList<Object>> ds_ls=new ArrayList<ArrayList<Object>>();
		//Connection c=dobj.getConnection();
		java.sql.Statement st= c.createStatement();
		ResultSet res=st.executeQuery(query);
		ResultSetMetaData rmdt=res.getMetaData();
		int count=rmdt.getColumnCount();
		int valueIndex=1,columnIndex=0,x=0;		  
		  while(res.next()) 
			  
		  { 
			  ds_ls.add(new ArrayList<>());
			  valueIndex=1;
		while(valueIndex!=count+1)
		  {
			if(x<=0)
			rs_keys.add(rmdt.getColumnName(valueIndex));
			
			//System.out.print(rmdt.getColumnName(i)+"\t");
			ds_ls.get(columnIndex).add(res.getString(valueIndex));
			//System.out.println(res.getString(i));
			valueIndex++;
			
			}
		x++;
		columnIndex++;
	

	}
		  return ds_ls;
	//uri_list=dobj.getUris(c);
	
	
	
	}
	public Vector<String>getKeys()
	{
		return rs_keys;
	}
	public Vector<String> getUris(Connection c) throws SQLException
	{
		uriList=new Vector<String>();
		String uri="";
		Statement s=c.createStatement();
		ResultSet res=s.executeQuery("select * from INFORMATION_SCHEMA.SYSTEM_REST_EVENTS");
		while (res.next())
		{
			uri=res.getString("URI");
			uriList.add(uri);
			
		}
		return uriList;
		
	}
	

}
