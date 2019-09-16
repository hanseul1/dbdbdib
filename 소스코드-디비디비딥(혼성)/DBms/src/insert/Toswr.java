package insert;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class Toswr {
	private final static String address = "http://appexam.ybmnet.co.kr/toeicswt/receipt/schedule.asp";
	
	public static void tosmain() throws Exception {
		Document doc = Jsoup.connect(address).header("User-Agent","Mozilla/5.0").get();
		System.out.println(doc);
		//Elements contents = doc.select("td");
	}
}
