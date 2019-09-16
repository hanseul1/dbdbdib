package insert;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class Jpt {
	private final static String address = "https://appexam.ybmnet.co.kr/jpt/receipt/receipt_schedule.asp";
	public static final int num = 21;
	
	public static int [] rounds = new int[num];
	public static String [] examDate = new String[num];
	public static String [] resultDate = new String[num];
	public static String [] appStartDate = new String[num];
	public static String [] appEndDate = new String[num];
	public static String [] addAppStartDate = new String[num];
	public static String [] addAppEndDate = new String[num];
	public static String apply = new String();
	
	public static void jptmain() throws Exception{
		Document doc = Jsoup.connect(address).header("User-Agent","Mozilla/5.0").get();
		
		String temp = new String();
		Elements contents = doc.select("td");
		
		for(int i =0; i<num; i++) {
			rounds[i] = Integer.parseInt(contents.get(i*4).text());
			temp = contents.get(i*4+1).text();
			examDate[i] = "20"+temp.substring(0,2)+"-"+temp.substring(3,5)+"-"+temp.substring(6,8);
			temp = contents.get(i*4+2).text();
			resultDate[i] = "20"+temp.substring(0,2)+"-"+temp.substring(3,5)+"-"+temp.substring(6,8);
			apply = contents.get(i*4+3).text();
			temp = apply.split("~")[0].substring(7,18);
			appStartDate[i] = "20"+temp.substring(0,2)+"-"+temp.substring(3,5)+"-"+temp.substring(6,8);
			temp = apply.split("~")[1].substring(1,12);
			appEndDate[i] = "20"+temp.substring(0,2)+"-"+temp.substring(3,5)+"-"+temp.substring(6,8);
			addAppStartDate[i] = apply.split("]")[2].substring(1,12);
			addAppEndDate[i] = apply.split("~")[2].substring(1,12);
		}
	}
}
