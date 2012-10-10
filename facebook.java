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


/**
 * 
 * @author yoga1290
 * @see https://developers.facebook.com/docs/authentication/server-side/
 * @see https://developers.facebook.com/docs/authentication/permissions/
 * @see https://developers.facebook.com/docs/opengraph/using-app-tokens/
 */


public class facebook
{
	/*
	 * Direct to:
	 https://www.facebook.com/dialog/oauth?client_id=187627904695576&redirect_uri=http://yoga1290.appspot.com/oauth/facebook/callback/&scope=user_about_me,email,publish_stream&state=USER_ID
	 */


	private String CLIENT_ID="***********",CLIENT_SECRET="*************************";//to be set later!
	public facebook(String app_id,String app_secret)
	{
		CLIENT_ID=app_id;
		CLIENT_SECRET=app_secret;
	}
	public static String getAccessToken(String CLIENT_ID,String CLIENT_SECRET,String code)
	{
		String res="";
		try
		{
//			https://graph.facebook.com/oauth/access_token?client_id=187627904695576&redirect_uri=http://yoga1290.appspot.com/oauth/facebook/callback/&client_secret=e35d3d770c34bf5c2204177015d1b28e&code=
			URL url = new URL("https://graph.facebook.com/oauth/access_token?client_id="+CLIENT_ID
							+"&redirect_uri=http://yoga1290.appspot.com/oauth/facebook/callback/"
							+"&client_secret="+CLIENT_SECRET
							+"&code="+code);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            InputStream in=connection.getInputStream();


		//TODO: better reading is possible, but,I'm just testing basic stuff here!
            int ch;
            while((ch=in.read())!=-1)
            			res+=(char)ch+"";
            in.close();
            
            //Extract the access token
            res=res.substring(res.indexOf("access_token=")+13,res.indexOf("&expires="));
		}catch(Exception e){
			res=e.getMessage();
		}
		return res;
	}
	public static String getUser(String access_token)
	{
		String res="";
		try
		{
			URL url = new URL("https://graph.facebook.com/me?access_token="+access_token);
	        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	        InputStream in=connection.getInputStream();
	        int ch;
	        while((ch=in.read())!=-1)
	        			res+=(char)ch+"";
	        in.close();
		}catch(Exception e){
			res=e.getMessage();
		}
		return res;
	}
	
	public static String extractJSON(String feild,String txt)
	{
		try{
			String tmp=txt.substring(txt.indexOf(feild)+feild.length()+3,txt.length());
			return txt.substring(txt.indexOf(feild)+feild.length()+3,tmp.indexOf("\"")+txt.indexOf(feild)+feild.length()+3);
		}catch(Exception e){
			return e.getMessage();
		}
	}