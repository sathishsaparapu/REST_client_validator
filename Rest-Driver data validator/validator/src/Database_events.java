import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

public class Database_events {
	public Connection getConnection() throws SQLException
	{
		String url="jdbc:datadirect:ddhybrid://10.30.22.107:8080;databaseName=teszt;encryptionmethod=noEncryption";		
		String uname="sathish";
		String pass="sathish";
		return DriverManager.getConnection(url,uname,pass);
	}
	public ResultSet getResultSet(String query)
	{
		return null;
		
	}
	public Vector<String> getTables(Connection c) throws SQLException
	{
		Vector<String> tables=new Vector<String>();
		DatabaseMetaData dmdt=c.getMetaData();
		ResultSet res=dmdt.getTables(null, "ORACLESALESCLOUD", "%", null);
		while(res.next())
		{
		
			tables.add(res.getString(3));
		}
		return tables;
		
	}
	

}
