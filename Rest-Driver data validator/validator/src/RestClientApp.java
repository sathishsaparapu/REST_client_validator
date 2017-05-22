import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Vector;

import org.json.JSONArray;
import org.json.JSONObject;

public class RestClientApp {
	Vector<String> missedColJson;
	Vector<String> rs_keys;
	public static String GetJson(String uri) throws IOException, URISyntaxException
	{
		
		String s=uri+"&onlyData=true";
		 URL url = new URL(s);
		 String out = "";
		    HttpURLConnection request = (HttpURLConnection) url.openConnection();
		    request.setRequestProperty("Accept", "application/json");
		    request.setRequestMethod("GET");
		    request.setRequestProperty("Authorization", "Basic SEFOX0lOVEdfVVNFUjpXZWxjb21lMg==");
		   // System.out.println(request.getResponseCode());
		    BufferedReader in = new BufferedReader(  
			        new InputStreamReader(request.getInputStream()));
		    StringBuilder st=new StringBuilder();
		    while((out=in.readLine())!=null)
		    { 
		    	st.append(out);
	
		    }
		    String st1=st.toString();
		    byte ptext[] = st1.getBytes(); 
		   String str=new String(ptext,"UTF-8");
			return str.toString();
	}
	public ArrayList<ArrayList<Object>> getRestData(Vector<String> Uris,Vector<String> keys) throws IOException, URISyntaxException
	{
		rs_keys=keys;
		Vector<String> rs_keys1= new Vector<String>();
		ArrayList<ArrayList<Object>> rs_ls=new ArrayList<ArrayList<Object>>();
		int uri_index=0,urlCount=Uris.size(),size=0;
		if(Uris.size()>0)
		{
		while(uri_index!=urlCount)
		{
		//System.out.println("uri list  "+uri_list.toString());
		String str=GetJson(Uris.get(uri_index));
		
		//System.out.println("===============================");
		
		
		JSONObject jsonobj=new JSONObject(str);
		JSONArray doc=jsonobj.getJSONArray("items");
		//System.out.println(doc.toString());
		
		for (int i = 0; i < doc.length(); i++) {
			rs_ls.add(new ArrayList<Object>());
		}
		JSONObject rs_obj=new JSONObject();
		if(doc.length()>0)
		{
		if(uri_index<=0)
		{
		
		rs_obj=(JSONObject) doc.get(0);
		java.util.Iterator itr1= rs_obj.keys();
		String str1="";
		/*Storing keys from rest in a vector*/
		while(itr1.hasNext())
		{
			
			rs_keys1.add((String) itr1.next());
		}
		}
		
		//System.out.println("keys size"+rs_keys1.size());
		for (int i = 0; i < doc.length(); i++,size++) {
			rs_obj=(JSONObject) doc.get(i);
			int x1=0;
			while(x1!=keys.size())
			{
				rs_ls.get(size).add("");
				x1++;
			}
			
			for (int j = 0; j < rs_keys1.size(); j++) {
				int k1=keys.indexOf(rs_keys1.get(j).toUpperCase());
				String temp=rs_keys1.get(j);
				//System.out.println(temp);
				Object rs_vobj=rs_obj.get(temp);
				if(rs_keys1.get(j).equalsIgnoreCase(("CSSUBTOTAL_C")))
				{
				String  s=rs_vobj.toString();
//				s1=s.substring(s.length()-1);
//				s2=s.substring(0,s.length()-1);
//				int x1=Integer.
				double val=Double.parseDouble(s);
				System.out.println("****"+BigDecimal.valueOf(val));
				}
				rs_ls.get(size).set(k1,rs_vobj);
				
					}
		}
		
		missedColJson =new Vector<String>();
		for (int i = 0; i < keys.size(); i++) {
			for (int j = 0; j < doc.length(); j++) {
				if(rs_ls.get(j).get(i).equals("") && !missedColJson.contains(keys.get(i)))
					missedColJson.add(keys.get(i));
				//System.out.print("->"+rs_ls.get(j).get(i));				
			}
			//System.out.println();
		}
		}
		uri_index++;
		}
		}
		return rs_ls;
		
	}
	public Vector<String>getJsonMissCols()
	{
		return missedColJson;
	}
	public int getPrimeData(String columnName)
			{
				return rs_keys.indexOf(columnName);
		
			}
}
