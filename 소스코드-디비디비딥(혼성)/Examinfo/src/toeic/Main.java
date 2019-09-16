package toeic;

import java.util.Date;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class Main {
	private final static String address = "http://appexam.ybmnet.co.kr/toeic/info/receipt_schedule.asp";
	static int idx= 0;
	public static final int ths = 37;    //ó��ȸ�� ���� ��
	public static final int the = 148;   //������ȸ�� ������ ��
	public static final int num = 25;    //���� ����
	
	public static void main(String[] args) throws Exception{
		Document doc = Jsoup.connect(address).header("User-Agent","Mozilla/5.0").get();
		String [] th = new String[50];        //����ȸ��
		String [] examdate = new String[50];  //���賯¥
		String [] examtime = new String[50];  //���� �ð�
		String [] resultdate = new String[50];  //������ǥ��
		String [] appStartDate = new String[50];    
		String [] appEndDate = new String[50];
		String [] addAppStartDate = new String[50];
		String [] addAppEndDate = new String[50];
		String [] applyStr = new String[10];
	
		String temp = new String();
		
		Elements contents = doc.select("td");
		//System.out.println(contents.text());
		
		for( int i = 0; i < num; i++) {
			int strline = ths+i*4;
			th[i] = contents.get(strline).text();
			th[i] = th[i].substring(1,4);
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
		
		for (int j = 0; j < num; j++) {
			System.out.println("ȸ��: "+th[j]);
			System.out.println("���賯¥: "+examdate[j]);
			System.out.println("�����ǥ��¥: "+resultdate[j]);
			System.out.println("�����Ⱓ: "+appStartDate[j]+" ~ "+appEndDate[j]);
			System.out.println("�߰������Ⱓ : "+addAppStartDate[j]+" ~ "+addAppEndDate[j]);
			System.out.println();
		}
		
		
		
	}

}
