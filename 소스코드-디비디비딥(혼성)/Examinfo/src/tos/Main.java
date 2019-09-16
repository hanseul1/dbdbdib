package tos;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Main {
	public static final int num = 53;
	private final static String address = "http://appexam.ybmnet.co.kr/toeicswt/receipt/schedule.asp";
	public static void main (String[] args) throws Exception {
			Document doc = Jsoup.connect(address).header("User-Agent","Mozilla/5.0").get();
			String [] examDate = new String[num];
			String [] appStartDate = new String[num];
			String [] appEndDate = new String[num];
			String [] resultDate = new String[num];
			String temp = new String();
			
			Elements contents = doc.select("td");
			
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
			
			for(int j = 0 ; j<num; j++) {
				System.out.println("시험날짜: "+examDate[j]);
				System.out.println("지원기간: "+appStartDate[j]+" 부터 "+appEndDate[j]);
				System.out.println("결과발표일: "+resultDate[j]);
			}
	}
}
