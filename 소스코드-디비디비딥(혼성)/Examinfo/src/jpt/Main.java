package jpt;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class Main {
	private final static String address = "https://appexam.ybmnet.co.kr/jpt/receipt/receipt_schedule.asp";
	public static final int num = 21;
	
	public static void main(String[] args) throws Exception{
		Document doc = Jsoup.connect(address).header("User-Agent","Mozilla/5.0").get();
		String [] rounds = new String[num];
		String [] examDate = new String[num];
		String [] resultDate = new String[num];
		String [] appStartDate = new String[num];
		String [] appEndDate = new String[num];
		String [] addAppStartDate = new String[num];
		String [] addAppEndDate = new String[num];
		String apply = new String();
		String temp = new String();
		
		Elements contents = doc.select("td");
		
		for(int i =0; i<num; i++) {
			rounds[i] = contents.get(i*4).text();
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
		for (int j=0; j<num; j++) {
			System.out.println("회차: "+rounds[j]);
			System.out.println("시험날짜: "+examDate[j]);
			System.out.println("결과발표일: "+resultDate[j]);
			System.out.println("접수기간: "+appStartDate[j]+" ~ "+appEndDate[j]);
			System.out.println("추가접수기간: "+addAppStartDate[j]+" ~ "+addAppEndDate[j]);
		}
	}
}
