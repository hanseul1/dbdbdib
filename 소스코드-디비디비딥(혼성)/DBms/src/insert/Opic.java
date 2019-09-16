package insert;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class Opic {
	private final static String address = "http://www.opic.or.kr/opics/servlet/controller.opic.site.receipt.ExamReceiptServlet?p_process=select-list&p_nav=1_1";
	private final static int num = 25; //시험일정개수
	
	public static String [] examDate = new String[getNum()];
	public static String [] appStartDate = new String[getNum()];
	public static String [] appEndDate = new String[getNum()];
	public static String [] resultDate =  new String[getNum()];
	public static String [] apply = new String[10];
	
	public static void opicmain() throws Exception{
		Document doc = Jsoup.connect(address).header("User-Agent","Mozilla/5.0").get();
		
		String temp = new String();
		Elements contents = doc.select("td");
		
		for (int i = 0; i < getNum(); i++) {
			examDate[i] = contents.get(i*6+0).text();
			apply = contents.get(i*6+2).text().split("~");
			temp = apply[0];
			appStartDate[i] = temp.substring(0,4)+"-"+temp.substring(5,7)+"-"+temp.substring(8,10);
			temp = apply[1];
			appEndDate[i] = temp.substring(0,4)+"-"+temp.substring(5,7)+"-"+temp.substring(8,10);
			resultDate[i] = contents.get(i*6+4).text().substring(0,10);
		}
		
	}

	public static int getNum() {
		return num;
	}
}
