package insert;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class Tos {
	public static final int num = 53;
	public static String [] examDate = new String[num];
	public static String [] appStartDate = new String[num];
	public static String [] appEndDate = new String[num];
	public static String [] resultDate = new String[num];
	private final static String address = "http://appexam.ybmnet.co.kr/toeicswt/receipt/schedule.asp";
	
	public static void tosmain() throws Exception {
			Document doc = Jsoup.connect(address).header("User-Agent","Mozilla/5.0").get();
			
			Elements contents = doc.select("td");
			String temp = new String();
			
			for(int i =0; i<num; i++) {
				temp = contents.get(i*3).text();
				examDate[i] = temp.substring(0,4)+"-"+temp.substring(6,8)+"-"+temp.substring(10,12);
				temp = contents.get(i*3+1).text().split("~")[0];
				appStartDate[i] = temp.substring(0,4)+"-"+temp.substring(6,8)+"-"+temp.substring(10,12);
				temp = contents.get(i*3+1).text().split("~")[1];
				appEndDate[i] = temp.substring(1,5)+"-"+temp.substring(7,9)+"-"+temp.substring(11,13);
				temp = contents.get(i*3+2).text();
				resultDate[i] = temp.substring(0,4)+"-"+temp.substring(6,8)+"-"+temp.substring(10,12);
			}
		
	}
}
