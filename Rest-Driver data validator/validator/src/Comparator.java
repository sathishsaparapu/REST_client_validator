import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import javax.sound.midi.MidiSystem;

public class Comparator {
	static String prime="";
	Vector<String> MissCols;
public void setJsonMissCols(Vector<String> missCols2)
{
	MissCols=missCols2;
}
public LogInfo compare(Vector<String> keys, String tableName, ArrayList<ArrayList<Object>> driverDataList,ArrayList<ArrayList<Object>> restDataList) throws IOException, SQLException
{
	int flag=0;
	LogInfo Lobj=new LogInfo();
	Vector<String> missMatchDataDriver=new Vector<String>();
	Vector<String> missMatchDataRest=new Vector<String>();
	Vector<String> missMatchColumns=new Vector<String>();	

	System.out.println("uri size "+driverDataList.size()+" res size"+restDataList.size());
	int i=0,k,count=0;
	for (int j = 0; j < restDataList.size(); j++) {
		i=0;
		k=driverDataList.get(j).size();
		while(i!=k)
		{
			
			if(driverDataList.get(j).get(i)!=null && restDataList.get(j).get(i)!=null)
			{
				if( restDataList.get(j).get(i).toString().equals(driverDataList.get(j).get(i).toString()))
				{
					
				}
				else
				{
					
//					
					System.out.println(driverDataList.get(j).get(i).toString()+"->"+restDataList.get(j).get(i));
					missMatchColumns.add(keys.get(i));
					missMatchDataDriver.add(driverDataList.get(j).get(i).toString());
					missMatchDataRest.add(restDataList.get(j).get(i).toString());
					count++;
				}
				}
			else
			{
			if(restDataList.get(j).get(i).equals(driverDataList.get(j).get(i)))
			{
				
			}
			else
			{
					
			}
			}
					
			i++;
		}
	}
	
	
	Lobj.TableName=tableName;
	Lobj.MissCols=MissCols;
	Lobj.missMatchColumns=missMatchColumns;
	Lobj.missMatchDataDriver=missMatchDataDriver;
	Lobj.missMatchDataRest=missMatchDataRest;
	return Lobj;

}
}
