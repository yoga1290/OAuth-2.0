import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Enumeration;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

import javax.servlet.http.*;

import org.apache.tools.ant.taskdefs.Length;
import org.datanucleus.store.connection.ConnectionResourceType;
import org.mortbay.log.Log;


public class google
{
	/*
	 * e.g:
	 * https://accounts.google.com/o/oauth2/auth?redirect_uri=https%3A%2F%2Fyoga1290.appspot.com/oauth/google/callback&response_type=code&client_id=411742560047&approval_prompt=force&scope=https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.profile&access_type=offline
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
            					"code="+code+"&client_id="+CLIENT_ID+"&scope=&client_secret="+CLIENT_SECRET+"&redirect_uri="+redirect+"&grant_type=authorization_code"
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
            
            
            res=res.substring(res.indexOf("\"access_token\" : \""+17));
            res=res.substring(0,res.indexOf("\""));
            
            
        } catch (Exception e) {
            res=e.getMessage();
        }
		return res;
	}
	public static String getUserInfo(String access_token)
	{
		String res="";
		try{
			URL url = new URL("https://www.googleapis.com/oauth2/v2/userinfo");
			
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.addRequestProperty("Authorization","OAuth "+access_token);

		//TODO: better reading is possible; I'm just testing here!
            InputStream in=connection.getInputStream();
            int ch;
            while((ch=in.read())!=-1)
            		res+=(char)ch;
		}catch(Exception e){res=e.getMessage();}
		return res;
	}
}