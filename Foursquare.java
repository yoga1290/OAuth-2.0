package yoga1290.printk;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Vector;

import com.google.appengine.labs.repackaged.org.json.JSONObject;
public class Foursquare 
{
	/**
	 * @see 	https://developer.foursquare.com/overview/auth
	 * @param CLIENT_ID
	 * @param CLIENT_SECRET
	 * @param code
	 * @return access_token
	 * @throws Exception
	 */
	public static String getAccessToken(String CLIENT_ID,String CLIENT_SECRET,String redirectURI,String code) throws Exception
	{
		String res="";
		/*
		https://foursquare.com/oauth2/authenticate
		    ?client_id=YOUR_CLIENT_ID
		    	    &response_type=code
		    	    &redirect_uri=YOUR_REGISTERED_REDIRECT_URI
	    	*/
			URL url = new URL("https://foursquare.com/oauth2/access_token?client_id="+CLIENT_ID
					+"&client_secret="+CLIENT_SECRET
					+"&grant_type=authorization_code"
					+"&redirect_uri="+redirectURI
					+"&code="+code);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            InputStream in=connection.getInputStream();
            byte buff[]=new byte[in.available()];
            int ch;
            while((ch=in.read(buff))!=-1)
            		res+=new String(buff,0,ch);
            in.close();
            //Extract the access token
            return new JSONObject(res).getString("access_token");
	}
	
	
	/**
	 * @see https://developer.foursquare.com/docs/venues/search
	 * @param access_token
	 * @param Latitude
	 * @param longitude
	 * @return
	 * @throws Exception
	 * response: {
					venues: [
						{
							id: "503de4dce4b0857b003af5f7"
							name: "monkeyHut"
							contact: { }
							location: {
							lat: 40.7
							lng: -74
							distance: 0
							postalCode: "10004"
							city: "New York"
							state: "NY"
							country: "United States"
							cc: "US"
						}
						canonicalUrl: "https://foursquare.com/v/monkeyhut/503de4dce4b0857b003af5f7"
						categories: [
							{
								id: "4bf58dd8d48988d1e1931735"
								name: "Arcade"
								pluralName: "Arcades"
								shortName: "Arcade"
								icon: {
								prefix: "https://foursquare.com/img/categories_v2/arts_entertainment/arcade_"
								suffix: ".png"
							}
							primary: true
							}
						]
						verified: false
						stats: {
							checkinsCount: 2
							usersCount: 2
							tipCount: 2
						}
						likes: {
							count: 0
							groups: [ ]
						}
						specials: {
							count: 0
							items: [ ]
						}
						hereNow: {
							count: 0
							groups: [ ]
						}
					referralId: "v-1356999154"
					},…]}

	 */
	public static JSONObject search(String access_token,double Latitude ,double longitude) throws Exception
	{
		String res="";
		URL url = new URL("https://api.foursquare.com/v2/venues/search?ll="+Latitude+","+longitude+"&oauth_token="+access_token);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        InputStream in=connection.getInputStream();
        byte buff[]=new byte[in.available()];
        int ch;
        while((ch=in.read(buff))!=-1)
        		res+=new String(buff,0,ch);
        in.close();
        return new JSONObject(res);
	}
	
	
	/**
	 * @see	https://developer.foursquare.com/docs/checkins/add
	 * @param access_token
	 * @param venueId
	 * @return
	 * @throws Exception
	 */
	public static JSONObject checkin(String access_token,String venueId) throws Exception
	{
		String res="";
		URL url = new URL("https://api.foursquare.com/v2/checkins/add?venueId="+venueId+"&oauth_token="+access_token);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        InputStream in=connection.getInputStream();
        byte buff[]=new byte[in.available()];
        int ch;
        while((ch=in.read(buff))!=-1)
        		res+=new String(buff,0,ch);
        in.close();
        return new JSONObject(res);
	}
	
	/**
	 * 
	 * @see https://developer.foursquare.com/docs/users/checkins
	 * @param access_token 
	 * @param YYYYMMDD
	 * @return JSONObject of Checkins
	 * @throws Exception
	 * 
	 * lat/lng: JSONObject(response).getJSONArray("items").get(i).getJSONObject("venue").get("location").getDouble("lat"/"lng")
	 * e.g:
	 * checkins: {
				count: 275
				items: [
					{
						id: "50eb356de4b0aa6e11dcc03a"
						createdAt: 1357591917
						type: "checkin"
						timeZoneOffset: 120
						venue: {
						id: "4c8a7391770fb60c04bad2c3"
						name: "Zanilli's"
						contact: { }
						location: {
							address: "Stanley"
							lat: 31.23531591308383
							lng: 29.950098754019564
							city: "Alexandria"
							country: "Egypt"
							cc: "EG"
						}
						canonicalUrl: "https://foursquare.com/v/zanillis/4c8a7391770fb60c04bad2c3"
						categories: [
							{
								id: "4bf58dd8d48988d16d941735"
								name: "Café"
								pluralName: "Cafés"
								shortName: "Café"
								icon: {
									prefix: "https://foursquare.com/img/categories_v2/food/cafe_"
									suffix: ".png"
								}
								primary: true
							}
						]
						verified: false
						stats: {
							checkinsCount: 164
							usersCount: 104
							tipCount: 13
						}
						likes: {
							count: 8
							groups: [
								{
								type: "others"
								count: 8
								items: [
									{
										id: "15291391"
										firstName: "Ahmed"
										lastName: "E."
										gender: "male"
										photo: {
											prefix: "https://irs0.4sqi.net/img/user/"
											suffix: "/1TNP4R2LA34K5ZSM.jpg"
										}
									}
									{
										id: "35226126"
										firstName: "ziad"
										lastName: "o."
										gender: "male"
										photo: {
											prefix: "https://irs0.4sqi.net/img/user/"
											suffix: "/blank_boy.png"
										}
									},………
									]
								}
							]
						summary: "Ahmed E, ziad o, Mona T & 5 others"
					}
					like: false
					beenHere: {
						count: 1
						marked: false
					}
					specials: {
						count: 0
					}
				}
					likes: {
					count: 0
					groups: [ ]
					}
					like: false
					photos: {
					count: 0
					items: [ ]
					}
					posts: {
					count: 0
					textCount: 0
					}
					comments: {
					count: 0
					items: [ ]
					}
					source: {
					name: "foursquare for Android"
					url: "https://foursquare.com/download/#/android"
					}
				}
	 */
	public static JSONObject getCheckins(String access_token,String YYYYMMDD) throws Exception
	{
		String res="";
		URL url = new URL("https://api.foursquare.com/v2/users/self/checkins?oauth_token="+access_token+"&v="+YYYYMMDD);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        InputStream in=connection.getInputStream();
        byte buff[]=new byte[in.available()];
        int ch;
        while((ch=in.read(buff))!=-1)
        		res+=new String(buff,0,ch);
        in.close();
        return new JSONObject(res);
	}
	public static JSONObject getCheckins(String access_token,String YYYYMMDD,int offset) throws Exception
	{
		String res="";
		URL url = new URL("https://api.foursquare.com/v2/users/self/checkins?oauth_token="+access_token+"&v="+YYYYMMDD+"&offset="+offset);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        InputStream in=connection.getInputStream();
        byte buff[]=new byte[in.available()];
        int ch;
        while((ch=in.read(buff))!=-1)
        		res+=new String(buff,0,ch);
        in.close();
        return new JSONObject(res);
	}
	
	
//	public static int[] getCheckinTimes(String access_token,String venueId,String YYYYMMDD)
//	{
//		Vector<Integer> res=new Vector<Integer>();
//		
//	}
}
