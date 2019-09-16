package opic;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Main {
	private final static String address = "http://www.opic.or.kr/opics/servlet/controller.opic.site.receipt.ExamReceiptServlet?p_process=select-list&p_nav=1_1";
	private final static int num = 25; //시험일정개수
	
	public static void main(String[] args) throws Exception{
		Document doc = Jsoup.connect(address).header("User-Agent","Mozilla/5.0").get();
		String [] examDate = new String[num];
		String [] appStartDate = new String[num];
		String [] appEndDate = new String[num];
		String [] resultDate =  new String[num];
		String [] apply = new String[10];
		String temp = new String();
		
		Elements contents = doc.select("td");
		
		for (int i = 0; i < num; i++) {
			examDate[i] = contents.get(i*6+0).text();
			apply = contents.get(i*6+2).text().split("~");
			temp = apply[0];
			appStartDate[i] = temp.substring(0,4)+"-"+temp.substring(5,7)+"-"+temp.substring(8,10);
			temp = apply[1];
			appEndDate[i] = temp.substring(0,4)+"-"+temp.substring(5,7)+"-"+temp.substring(8,10);
			resultDate[i] = contents.get(i*6+4).text().substring(0,10);
		}
		
		for (int j=0; j<num; j++) {
			System.out.println("시험날짜: "+examDate[j]);
			System.out.println("지원시작날짜: "+appStartDate[j]);
			System.out.println("지원마지막날짜: "+appEndDate[j]);
			System.out.println("결과발표일: "+resultDate[j]);
			System.out.println();
		}
		
	}

}
