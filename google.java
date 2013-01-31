package yoga1290.printk;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;

public class google 
{

		/*
		 * e.g:
		 * https://accounts.google.com/o/oauth2/auth?redirect_uri=https%3A%2F%2Fyoga1290.appspot.com/oauth/google/callback&response_type=code&client_id=411742560047&approval_prompt=auto&scope=https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.profile&access_type=offline
		 */
		public static String getAccesToken(String CLIENT_ID,String CLIENT_SECRET,String code)
		{
			String res="";
			try {
				
	            URL url = new URL("https://accounts.google.com/o/oauth2/token");
	            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	            connection.setDoOutput(true);
	            connection.setRequestMethod("POST");

	            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
	            String redirect="https://yoga1290.appspot.com/oauth/google/callback";
	            writer.write(
	            					"code="+code+"&client_id="+CLIENT_ID+"&state=&client_secret="+CLIENT_SECRET+"&redirect_uri="+redirect+"&grant_type=authorization_code"
	            				);
	            writer.close();
	    
	            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) 
	            {
	                InputStream in=connection.getInputStream();
	                int ch;
	                while((ch=in.read())!=-1)
	                		res+=(char)ch;
	            } else {
	            		InputStream in=connection.getInputStream();
		                int ch;
		                while((ch=in.read())!=-1)
		                		res+=(char)ch;
	            }
	            
	            
	            res=res.substring(res.indexOf("\"access_token\" : \"")+18);
	            res=res.substring(0,res.indexOf("\""));
	            
	            
	        } catch (Exception e) {
	            res="Error:"+res+"<br>"+e.getMessage();
	        }
			return res;
		}
		public static String getUserInfo(String access_token)
		{
			String res="";
			try
			{	
				URL url = new URL("https://www.googleapis.com/oauth2/v2/userinfo?access_token="+access_token);
		        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		        connection.setDoOutput(true);
		        connection.setRequestMethod("GET");
		        
		        InputStream in=connection.getInputStream();
		        byte buff[]=new byte[in.available()];
	            int ch;
	            while((ch=in.read(buff))!=-1)
	            		res+=new String(buff,0,ch);
			}catch(Exception e)
			{
				res="Error:<br>"+res+"<br>"+e.getMessage();
			}
			return res;
		}
}
