import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

public class Main {

	public static void main(String[] args) throws SQLException, IOException, URISyntaxException {
		ArrayList<ArrayList<Object>> driverDataList;
		ArrayList<ArrayList<Object>> restDataList;
		Database_events Dobj=new Database_events();
		PrintWriter writer = new PrintWriter("logFile.txt", "UTF-8");
		RestClientApp 	Robj;
		Comparator Cobj;
		DriverData dd ;
		LogInfo Lobj;
		Connection c=Dobj.getConnection();
		DatabaseMetaData dmdt=c.getMetaData();
		Vector<String> tables=Dobj.getTables(c);
		System.out.println(tables.size());
		
		for(int i=0;i<tables.size();i++)
		{
			if(!tables.get(i).equals("INFOLETS"))
			{
			dd = new DriverData();
			Robj=new RestClientApp();
			Cobj=new Comparator();
			Lobj=new LogInfo();
			Connection c1=Dobj.getConnection();
			driverDataList=dd.getDriverData(c1,"SPECIALPRICING_C" );
			Vector<String> URIs =  dd.getUris(c1);
			Vector<String> keys=dd.getKeys();
			restDataList= Robj.getRestData(URIs, keys);
			Vector<String> MissCols=Robj.missedColJson;
			Cobj.setJsonMissCols(MissCols);
			Lobj=Cobj.compare(keys,tables.get(i),driverDataList, restDataList);
			writer.println("*********************************************************");
			writer.println("Table Name: "+Lobj.TableName);
			ResultSet res=dmdt.getPrimaryKeys(null, "ORACLESALESCLOUD",tables.get(i));
			String PrimeColumn="";
			while(res.next()){
			PrimeColumn=res.getString("COLUMN_NAME");
			writer.println("Primary key: "+res.getString("COLUMN_NAME"));
			}
			//int primeValue=Robj.getPrimeData(PrimeColumn);
			//writer.println("Primary key value: "+driverDataList.get( ));
			writer.println("Missed columns from JSON: "+Lobj.MissCols);
			writer.println("Miss Match columns are : "+Lobj.missMatchColumns);
			writer.println("Miss Match Data in driver: "+Lobj.missMatchDataDriver);
			writer.println("Miss Match Data in Rest: "+Lobj.missMatchDataRest);
			writer.println("*********************************************************");
			writer.println();
			writer.println();
			}
			//System.out.println();
			break;
		}
		writer.close();
	

	}

}
