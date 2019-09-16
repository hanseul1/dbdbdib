package insert;

import java.util.Date;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class Toeic {
	private final static String address = "http://appexam.ybmnet.co.kr/toeic/info/receipt_schedule.asp";
	static int idx= 0;
	public static final int ths = 37;    //처음회차 시작 줄
	public static final int the = 148;   //마지막회차 마지막 줄
	public static final int num = 25;    //시험 개수
	
	public static int [] th = new int[num];        //시험회차
	public static String [] examdate = new String[num];  //시험날짜
	public static String [] examtime = new String[num];  //시험 시간
	public static String [] resultdate = new String[num];  //성적발표일
	public static String [] appStartDate = new String[num];    
	public static String [] appEndDate = new String[num];
	public static String [] addAppStartDate = new String[num];
	public static String [] addAppEndDate = new String[num];
	public static String [] applyStr = new String[num];
	public static String temp = new String();
	
	Date startRegister = new Date();
	Date endRegister = new Date();
	Date ExamDate = new Date();
	Date AnnounDate = new Date();
	
	
	public static void toeicmain() throws Exception{
		Document doc = Jsoup.connect(address).header("User-Agent","Mozilla/5.0").get();
	
		Elements contents = doc.select("td");
		
		for( int i = 0; i < num; i++) {
			int strline = ths+i*4;
			temp = contents.get(strline).text();
			th[i] = Integer.parseInt(temp.substring(1,4));
			temp = contents.get(strline+1).text().split("\\s")[0];
			examdate[i] = "20"+temp.substring(0,2)+"-"+temp.substring(3,5)+"-"+temp.substring(6,8);
			temp = contents.get(strline+2).text().split("\\s")[0];
			resultdate[i] = "20"+temp.substring(0,2)+"-"+temp.substring(3,5)+"-"+temp.substring(6,8);
			applyStr = contents.get(strline+3).text().split("\\s");
			temp = applyStr[0];
			temp = temp.substring(4);
			appStartDate[i] = "20"+temp.substring(0,2)+"-"+temp.substring(3,5)+"-"+temp.substring(6,8);
			temp = applyStr[2];
			temp = temp.substring(4);
			appEndDate[i] = "20"+temp.substring(0,2)+"-"+temp.substring(3,5)+"-"+temp.substring(6,8);
			addAppStartDate[i] = applyStr[5];
			addAppStartDate[i] = addAppStartDate[i].substring(4);
			addAppEndDate[i] = applyStr[7];
			addAppEndDate[i] = addAppEndDate[i].substring(4);
		}
	}
}
