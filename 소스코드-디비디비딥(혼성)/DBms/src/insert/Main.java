package insert;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Main {
	public static Connection getConnection() throws ClassNotFoundException, SQLException{
		String url = "jdbc:mysql://202.30.24.86:20931";
		String user = "root";
		String pass = "whois";
		
		Connection conn = null;

		Class.forName("com.mysql.jdbc.Driver");
		conn = DriverManager.getConnection(url,user,pass);
		
		return conn;
	}
	
	public static void main (String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		Connection conn = getConnection();
		Statement st = null;
		ResultSet rs = null;
		String sql = null;
		st = conn.createStatement();
		
		
		Toeic toeicInfo = new Toeic();
		Opic opicInfo = new Opic();
		Tos tosInfo = new Tos();
		Jpt jptInfo = new Jpt();
		
		toeicInfo.toeicmain();
		opicInfo.opicmain();
		tosInfo.tosmain();
		jptInfo.jptmain();
		
		
		st.executeUpdate("drop database ESC");

		st.executeUpdate("create database ESC");
		
		
		rs = st.executeQuery("use ESC");
		
		st.executeUpdate("CREATE TABLE exam"
				+ "(Exam_num	int		NOT NULL,"
				+ "Exam_name	varchar(10) 	NOT NULL,"
				+ "primary key(Exam_num))");
		
		st.executeUpdate("CREATE TABLE engexam"
				+ "(Exam_num	int		NOT NULL,"
				+ "Eng_num		int	 	NOT NULL,"
				+ "Eng_name		varchar(10)	 	NOT NULL,"
				+ "primary key(Eng_num))");
		
//		st.executeUpdate("CREATE TABLE certexam"
//				+ "(Exam_num	int		NOT NULL,"
//				+ "cert_num		int	 	NOT NULL,"
//				+ "cert_name		varchar(10)	 	NOT NULL,"
//				+ "primary key(cert_num))");
		
		st.executeUpdate("CREATE TABLE engexam_list"
				+ "(Eng_num	int		NOT NULL,"
				+ "Kind		int		NOT NULL,"
				+ "Rounds		int	 	NOT NULL,"
				+ "Start_register		DATE	 	NOT NULL,"
				+ "End_register		DATE	 	NOT NULL,"
				+ "Exam_date		DATE	 	NOT NULL,"
				+ "Announ_date		DATE	 	NOT NULL,"
				+ "Cost		int	 	NOT NULL,"
				+ "primary key(Eng_num,Kind,Exam_date))");
		
//		st.executeUpdate("CREATE TABLE certexam_list"
//				+ "(Cert_num	int		NOT NULL,"
//				+ "Kind		int		NOT NULL,"
//				+ "Name		varchar(10)	 	NOT NULL,"
//				+ "Rounds		int		NOT NULL,"
//				+ "Test		int		NOT NULL,"
//				+ "Start_register		DATE	 	NOT NULL,"
//				+ "End_register		DATE	 	NOT NULL,"
//				+ "Exam_date		DATE	 	NOT NULL,"
//				+ "Announ_date		DATE	 	NOT NULL,"
//				+ "Cost		int	 	NOT NULL,"
//				+ "primary key(Cert_num,Kind,Rounds))");
		
		st.executeUpdate("ALTER TABLE engexam ADD FOREIGN KEY(Exam_num) REFERENCES exam(Exam_num)");
		//st.executeUpdate("ALTER TABLE certexam ADD FOREIGN KEY(Exam_num) REFERENCES exam(Exam_num)");
		st.executeUpdate("ALTER TABLE engexam_list ADD FOREIGN KEY(Eng_num) REFERENCES engexam(Eng_num)");
		//st.executeUpdate("ALTER TABLE certexam_list ADD FOREIGN KEY(Cert_num) REFERENCES certexam(Cert_num)");
		
		st.executeLargeUpdate("insert into exam values(0,'engexam')");
		st.executeLargeUpdate("insert into exam values(1,'certexam')");
		
		st.executeLargeUpdate("insert into engexam values(0,0,'toeic')");
		st.executeLargeUpdate("insert into engexam values(0,1,'opic')");
		st.executeLargeUpdate("insert into engexam values(0,2,'toss')");
		st.executeLargeUpdate("insert into engexam values(0,3,'jpt')");
		
		//토익 테이블 insert
		for(int i = 0 ; i<toeicInfo.num; i++) {
			st.executeLargeUpdate("insert into engexam_list values(0,0,'"+toeicInfo.th[i]+"','"+toeicInfo.appStartDate[i]+"','"+toeicInfo.appEndDate[i]+"','"+toeicInfo.examdate[i]+"','"+toeicInfo.resultdate[i]+"',44500)");
		}
		//오픽 테이블 insert
		for (int i = 0; i<opicInfo.getNum(); i++) {
			st.executeLargeUpdate("insert into engexam_list values(1,0,0,'"+opicInfo.appStartDate[i]+"','"+opicInfo.appEndDate[i]+"','"+opicInfo.examDate[i]+"','"+opicInfo.resultDate[i]+"',78100)");
		}
		//토스(스피킹) 테이블 insert
		for (int i = 0; i<tosInfo.num; i++) {
			st.executeLargeUpdate("insert into engexam_list values(2,1,0,'"+tosInfo.appStartDate[i]+"','"+tosInfo.appEndDate[i]+"','"+tosInfo.examDate[i]+"','"+tosInfo.resultDate[i]+"',77000)");
		}
		
		//jpt 테이블 insert
		for (int i=0; i<jptInfo.num; i++) {
			st.executeLargeUpdate("insert into engexam_list values(3,0,'"+jptInfo.rounds[i]+"','"+jptInfo.appStartDate[i]+"','"+jptInfo.appEndDate[i]+"','"+jptInfo.examDate[i]+"','"+jptInfo.resultDate[i]+"',43500)");
		}
			
			
			st.executeUpdate("CREATE TABLE certexam"
					+ "(Exam_num		int 	NOT NULL,"
					+ "Cert_num			int		NOT NULL,"
					+ "Cert_name		varchar(8)	NOT NULL,"
					+ "primary key(Cert_num))");

			st.executeUpdate("ALTER TABLE certexam convert to charset utf8");
			st.executeUpdate("ALTER TABLE certexam ADD FOREIGN KEY(Exam_num) REFERENCES Exam(Exam_num)");

			st.executeLargeUpdate("insert into certexam values(1,0,'기술사')");                                  	
			st.executeLargeUpdate("insert into certexam values(1,1,'기능장')");                                  
			st.executeLargeUpdate("insert into certexam values(1,2,'기사,산업기사')");                                  
			st.executeLargeUpdate("insert into certexam values(1,3,'기능사')");                                  

			st.executeUpdate("CREATE TABLE certlist"
					+ "(Cert_num		int 	NOT NULL,"
					+ "Rounds			int		NOT NULL,"
					+ "Kind				varchar(10),"
					+ "Detail			varchar(120),"
					+ "primary key(Rounds, Kind))"
					+ "ENGINE = InnoDB character set=utf8");

			st.executeUpdate("ALTER TABLE certlist convert to charset utf8");
			st.executeUpdate("ALTER TABLE certlist ADD FOREIGN KEY(Cert_num) REFERENCES certexam(Cert_num)");

			st.executeLargeUpdate("insert into certlist values(0,111,'생산관리','포장기술사')");                                  
			st.executeLargeUpdate("insert into certlist values(0,111,'건축','건축구조기술사,건축기계설비기술사,건축시공기술사,건축품질시험기술사')");                                  
			st.executeLargeUpdate("insert into certlist values(0,111,'토목','농어업토목기술사,도로및공항기술사,상하수도기술사,수자원개발기술사,지적기술사,지질및지반기술사,철도기술사,측량및지형공간정보기술사,토목구조기술사,토목시공기술사,토목품질시험기술사,토질및기초기술사,항만및해안기술사,해양기술사')");                                  
			st.executeLargeUpdate("insert into certlist values(0,111,'조경','조경기술사')");                                  
			st.executeLargeUpdate("insert into certlist values(0,111,'도시.교통','교통기술사,도시계획기술사')");                                  
			st.executeLargeUpdate("insert into certlist values(0,111,'기계장비설비.설치','건설기계기술사,공조냉동기계기술사,산업기계설비기술사')");                                  
			st.executeLargeUpdate("insert into certlist values(0,111,'자동차','차량기술사')");                                  
			st.executeLargeUpdate("insert into certlist values(0,111,'금형.공작기계','금형기술사')");                                  
			st.executeLargeUpdate("insert into certlist values(0,111,'금속.재료','금속가공기술사,금속재료기술사')");                                  
			st.executeLargeUpdate("insert into certlist values(0,111,'용접','용접기술사')");                                  
			st.executeLargeUpdate("insert into certlist values(0,111,'도장.도금','표면처리기술사')");                                  
			st.executeLargeUpdate("insert into certlist values(0,111,'전기','건축전기설비기술사,발송배전기술사,철도신호기술사')");                                  
			st.executeLargeUpdate("insert into certlist values(0,111,'전자','전자응용기술사')");                                  
			st.executeLargeUpdate("insert into certlist values(0,111,'정보기술','정보관리기술사,컴퓨터시스템응용기술사')");                                  
			st.executeLargeUpdate("insert into certlist values(0,111,'식품','식품기술사')");                                  
			st.executeLargeUpdate("insert into certlist values(0,111,'농업','종자기술사')");                                  
			st.executeLargeUpdate("insert into certlist values(0,111,'축산','축산기술사')");                                  
			st.executeLargeUpdate("insert into certlist values(0,111,'임업','산림기술사')");                                  
			st.executeLargeUpdate("insert into certlist values(0,111,'어업','수산양식기술사,어로기술사')");                                  
			st.executeLargeUpdate("insert into certlist values(0,111,'안전관리','가스기술사,건설안전기술사,기계안전기술사,산업위생관리기술사,소방기술사,전기안전기술사,화공안전기술사')");                                  
			st.executeLargeUpdate("insert into certlist values(0,111,'비파괴검사','비파괴검사기술사')");                                  
			st.executeLargeUpdate("insert into certlist values(0,111,'환경','대기관리기술사,소음진동기술사,수질관리기술사,폐기물처리기술사')");
			st.executeLargeUpdate("insert into certlist values(0,111,'에너지.기상','기상예보기술사')");                                  

			st.executeLargeUpdate("insert into certlist values(0,112,'생산관리','공장관리기술사,품질관리기술사')");                                  
			st.executeLargeUpdate("insert into certlist values(0,112,'디자인','제품디자인기술사')");                                  
			st.executeLargeUpdate("insert into certlist values(0,112,'건축','건축구조기술사,건축기계설비기술사,건축시공기술사')");                                  
			st.executeLargeUpdate("insert into certlist values(0,112,'토목','농어업토목기술사,도로및공항기술사,상하수도기술사,철도기술사,토목구조기술사,토목시공기술사,토목품질시험기술사,토질및기초기술사,항만및해안기술사')");                                  
			st.executeLargeUpdate("insert into certlist values(0,112,'조경','조경기술사')");                                  
			st.executeLargeUpdate("insert into certlist values(0,112,'도시.교통','도시계획기술사')");                                  
			st.executeLargeUpdate("insert into certlist values(0,112,'채광','화약류관리기술사')");                                  
			st.executeLargeUpdate("insert into certlist values(0,112,'기계제작','기계기술사')");                                  
			st.executeLargeUpdate("insert into certlist values(0,112,'철도','철도차량기술사')");                                  
			st.executeLargeUpdate("insert into certlist values(0,112,'조선','조선기술사')");                                  
			st.executeLargeUpdate("insert into certlist values(0,112,'금형.공작기계','금형기술사')");                                  
			st.executeLargeUpdate("insert into certlist values(0,112,'금속.재료','금속제련기술사,세라믹기술사')");                                  
			st.executeLargeUpdate("insert into certlist values(0,112,'화공','화공기술사')");                                  
			st.executeLargeUpdate("insert into certlist values(0,112,'섬유','섬유기술사,의류기술사')");                                  
			st.executeLargeUpdate("insert into certlist values(0,112,'전기','건축전기설비기술사,발송배전기술사,전기응용기술사,전기철도기술사,철도신호기술사')");                                  
			st.executeLargeUpdate("insert into certlist values(0,112,'전자','산업계측제어기술사')");                                  
			st.executeLargeUpdate("insert into certlist values(0,112,'식품','수산제조기술사')");                                  
			st.executeLargeUpdate("insert into certlist values(0,112,'농업','시설원예기술사')");                                  
			st.executeLargeUpdate("insert into certlist values(0,112,'안전관리','건설안전기술사,소방기술사,인간공학기술사')");                                  
			st.executeLargeUpdate("insert into certlist values(0,112,'환경','자연환경관리기술사,토양환경기술사')");                                  
			st.executeLargeUpdate("insert into certlist values(0,112,'에너지.기상','기상예보기술사')");  
			
			st.executeLargeUpdate("insert into certlist values(0,113,'생산관리','품질관리기술사')");                              
			st.executeLargeUpdate("insert into certlist values(0,113,'건축','건축구조기술사,건축기계설비기술사,건축시공기술사')");                              
			st.executeLargeUpdate("insert into certlist values(0,113,'토목','도로및공항기술사,상하수도기술사,수자원개발기술사,지적기술사,지질및지반기술사,철도기술사,측량및지형공간정보기술사,토목구조기술사,토목시공기술사,토질및기초기술사')");                              
			st.executeLargeUpdate("insert into certlist values(0,113,'도시.교통','교통기술사,도시계획기술사')");                              
			st.executeLargeUpdate("insert into certlist values(0,113,'기계장비설비.설치','건설기계기술사,공조냉동기계기술사')");                              
			st.executeLargeUpdate("insert into certlist values(0,113,'철도','철도차량기술사')");                              
			st.executeLargeUpdate("insert into certlist values(0,113,'항공','항공기관기술사,항공기체기술사')");                              
			st.executeLargeUpdate("insert into certlist values(0,113,'자동차','차량기술사')");                              
			st.executeLargeUpdate("insert into certlist values(0,113,'용접','용접기술사')");                              
			st.executeLargeUpdate("insert into certlist values(0,113,'전기','건축전기설비기술사,발송배전기술사,전기응용기술사,전기철도기술사')");                              
			st.executeLargeUpdate("insert into certlist values(0,113,'정보기술','정보관리기술사,컴퓨터시스템응용기술사')");                              
			st.executeLargeUpdate("insert into certlist values(0,113,'식품','식품기술사')");                              
			st.executeLargeUpdate("insert into certlist values(0,113,'농업','농화학기술사')");                              
			st.executeLargeUpdate("insert into certlist values(0,113,'임업','산림기술사')");                              
			st.executeLargeUpdate("insert into certlist values(0,113,'안전관리','가스기술사,건설안전기술사,산업위생관리기술사,소방기술사,전기안전기술사')");                              
			st.executeLargeUpdate("insert into certlist values(0,113,'환경','대기관리기술사,소음진동기술사,수질관리기술사,자연환경관리기술사,토양환경기술사,폐기물처리기술사')");                              
			
			
			st.executeLargeUpdate("insert into certlist values(1,61,'이용.미용','미용장,이용장')");                              
			st.executeLargeUpdate("insert into certlist values(1,61,'조리 ','조리기능장')");                              
			st.executeLargeUpdate("insert into certlist values(1,61,'건축 ','건축목재시공기능장')");                              
			st.executeLargeUpdate("insert into certlist values(1,61,'기계제작 ','기계가공기능장')");                              
			st.executeLargeUpdate("insert into certlist values(1,61,'판금.제관.새시','판금제관기능장')");                              
			st.executeLargeUpdate("insert into certlist values(1,61,'용접 ','용접기능장')");                              
			st.executeLargeUpdate("insert into certlist values(1,61,'전기 ','전기기능장')");                              
			st.executeLargeUpdate("insert into certlist values(1,61,'전자 ','전자기기기능장')");                              
			st.executeLargeUpdate("insert into certlist values(1,61,'제과.제빵','제과기능장')"); 
			
			st.executeLargeUpdate("insert into certlist values(1,61,'건설배관 ','배관기능장')");                              
			st.executeLargeUpdate("insert into certlist values(1,61,'기계장비설비.설치','건설기계정비기능장')");                              
			st.executeLargeUpdate("insert into certlist values(1,61,'자동차 ','자동차정비기능장')");                              
			st.executeLargeUpdate("insert into certlist values(1,61,'금형.공작기계','금형제작기능장')");                              
			st.executeLargeUpdate("insert into certlist values(1,61,'금속.재료','산금속재료기능장,압연기능장,제강기능장,제선기능장림기술사')");                              
			st.executeLargeUpdate("insert into certlist values(1,61,'위험물 ','위험물기능장')");                              
			st.executeLargeUpdate("insert into certlist values(1,61,'안전관리','가스기능장')");                              
			st.executeLargeUpdate("insert into certlist values(1,61,'에너지.기상','에너지관리기능장')");                              
			
			
			st.executeLargeUpdate("insert into certlist values(1,62,'이용.미용','미용장,이용장')");                              
			st.executeLargeUpdate("insert into certlist values(1,62,'조리','조리기능장')");                              
			st.executeLargeUpdate("insert into certlist values(1,62,'건축 ','건축일반시공기능장')");                              
			st.executeLargeUpdate("insert into certlist values(1,62,'기계제작 ','기계가공기능장')");                              
			st.executeLargeUpdate("insert into certlist values(1,62,'용접 ','용접기능장')");                              
			st.executeLargeUpdate("insert into certlist values(1,62,'전기','전기기능장')");                              
			st.executeLargeUpdate("insert into certlist values(1,62,'전자 ','전자기기기능장')");                              
			st.executeLargeUpdate("insert into certlist values(1,62,'제과.제빵','제과기능장')");                              
			st.executeLargeUpdate("insert into certlist values(1,62,'목재.가구.공예','귀금속가공기능장')");                              
			
			st.executeLargeUpdate("insert into certlist values(1,62,'건설배관 ','배관기능장')");                              
			st.executeLargeUpdate("insert into certlist values(1,62,'기계장비설비.설치','건설기계정비기능장')");                              
			st.executeLargeUpdate("insert into certlist values(1,62,'철도 ','철도차량정비기능장')");                              
			st.executeLargeUpdate("insert into certlist values(1,62,'자동차 ','자동차정비기능장')");                              
			st.executeLargeUpdate("insert into certlist values(1,62,'금속.재료','금속재료기능장,압연기능장,제강기능장')");                              
			st.executeLargeUpdate("insert into certlist values(1,62,'단조.주조','주조기능장')");                              
			st.executeLargeUpdate("insert into certlist values(1,62,'도장.도금','표면처리기능장')");                              
			st.executeLargeUpdate("insert into certlist values(1,62,'위험물 ','위험물기능장')");                              
			st.executeLargeUpdate("insert into certlist values(1,62,'안전관리 ','가스기능장')");                              
			st.executeLargeUpdate("insert into certlist values(1,62,'에너지.기상','에너지관리기능장')");                              



			st.executeLargeUpdate("insert into certlist values(0,111,'생산관리','포장기술사')");                                  
			st.executeLargeUpdate("insert into certlist values(0,111,'생산관리','포장기술사')");                                  
			
			st.executeUpdate("CREATE TABLE WrittenExam"
					+ "(Cert_num		int		NOT NULL, "
					+ "Rounds			int		NOT NULL,"
					+ "Exam_date		Date	NOT NULL,"
					+ "Start_register	Date	NOT NULL,"
					+ "End_register		Date	NOT NULL,"
			
					+ "Announ_date		Date	NOT NULL,"
					+ "Cost				int 	NOT NULL,"
					+ "primary key(Rounds,Exam_date))");
			
			st.executeUpdate("ALTER TABLE WrittenExam ADD FOREIGN KEY(Rounds) REFERENCES certlist(Rounds)"); //67800 87100 /  34400 79900 / 19400 44100 / 11900 17200
			st.executeUpdate("ALTER TABLE WrittenExam ADD FOREIGN KEY(Cert_num) REFERENCES certexam(Cert_num)");
			
			st.executeLargeUpdate("insert into WrittenExam values(0,111,'2018-03-02','2018-01-04','2018-01-10','2018-03-02','67800')");                              
			st.executeLargeUpdate("insert into WrittenExam values(0,112,'2018-05-14','2018-04-07','2018-04-13','2018-06-15','67800')");                              
			st.executeLargeUpdate("insert into WrittenExam values(0,113,'2018-08-12','2018-07-07','2018-07-13','2018-09-14','67800')");                              
			
			st.executeLargeUpdate("insert into WrittenExam values(1,61,'2018-03-05','2018-02-03','2018-02-09','2018-03-16','34400')");                              
			st.executeLargeUpdate("insert into WrittenExam values(1,62,'2018-07-08','2018-06-09','2018-06-15','2018-07-20','34400')");                              
			
			
			
			
			st.executeUpdate("CREATE TABLE PracticalExam"
					+ "(Cert_num		int		NOT NULL, "
					+ "Rounds			int		NOT NULL, "
					+ "Kind				varchar(10),"
					+ "Exam_date		Date	NOT NULL,"
					+ "Start_register	Date	NOT NULL,"
					+ "End_register		Date	NOT NULL,"
					+ "Announ_date		Date	NOT NULL,"
					+ "Cost				int 	NOT NULL,"
					+ "primary key(Rounds,Kind,Exam_date))"
					+ " ENGINE = InnoDB character set=utf8");
			
			st.executeUpdate("ALTER TABLE PracticalExam convert to charset utf8");
			st.executeUpdate("ALTER TABLE PracticalExam ADD FOREIGN KEY(Rounds) REFERENCES certlist(Rounds)");
			//		st.executeUpdate("ALTER TABLE PracticalExam ADD FOREIGN KEY(Kind) REFERENCES certlist(Kind)");
			
			st.executeLargeUpdate("insert into PracticalExam values(0,111,'생산관리','2018-04-08','2018-03-06','2018-03-09','2018-03-09','87100')");                              
			st.executeLargeUpdate("insert into PracticalExam values(0,111,'건축','2018-04-08','2018-03-06','2018-03-09','2018-03-09','87100')");                              
			st.executeLargeUpdate("insert into PracticalExam values(0,111,'토목','2018-04-08','2018-03-06','2018-03-09','2018-03-09','87100')");                              
			st.executeLargeUpdate("insert into PracticalExam values(0,111,'조경','2018-04-08','2018-03-06','2018-03-09','2018-03-09','87100')");                              
			st.executeLargeUpdate("insert into PracticalExam values(0,111,'도시.교통','2018-04-08','2018-03-06','2018-03-09','2018-03-09','87100')");                              
			st.executeLargeUpdate("insert into PracticalExam values(0,111,'기계장비설비.설치','2018-04-08','2018-03-06','2018-03-09','2018-03-09','87100')");                              
			st.executeLargeUpdate("insert into PracticalExam values(0,111,'자동차','2018-04-08','2018-03-06','2018-03-09','2018-03-09','87100')");                              
			st.executeLargeUpdate("insert into PracticalExam values(0,111,'금형.공작기계','2018-04-08','2018-03-06','2018-03-09','2018-03-09','87100')");                              
			st.executeLargeUpdate("insert into PracticalExam values(0,111,'금속.재료','2018-04-08','2018-03-06','2018-03-09','2018-03-09','87100')");                              
			st.executeLargeUpdate("insert into PracticalExam values(0,111,'용접','2018-04-08','2018-03-06','2018-03-09','2018-03-09','87100')");                              
			st.executeLargeUpdate("insert into PracticalExam values(0,111,'도장.도금','2018-04-08','2018-03-06','2018-03-09','2018-03-09','87100')");                              
			st.executeLargeUpdate("insert into PracticalExam values(0,111,'전기','2018-04-08','2018-03-06','2018-03-09','2018-03-09','87100')");                              
			st.executeLargeUpdate("insert into PracticalExam values(0,111,'전자','2018-04-08','2018-03-06','2018-03-09','2018-03-09','87100')");                              
			st.executeLargeUpdate("insert into PracticalExam values(0,111,'정보기술','2018-04-08','2018-03-06','2018-03-09','2018-03-09','87100')");                              
			st.executeLargeUpdate("insert into PracticalExam values(0,111,'식품','2018-04-08','2018-03-06','2018-03-09','2018-03-09','87100')");                              
			st.executeLargeUpdate("insert into PracticalExam values(0,111,'농업','2018-04-08','2018-03-06','2018-03-09','2018-03-09','87100')");                              
			st.executeLargeUpdate("insert into PracticalExam values(0,111,'축산','2018-04-08','2018-03-06','2018-03-09','2018-03-09','87100')");                              
			st.executeLargeUpdate("insert into PracticalExam values(0,111,'임업','2018-04-08','2018-03-06','2018-03-09','2018-03-09','87100')");                              
			st.executeLargeUpdate("insert into PracticalExam values(0,111,'어업','2018-04-08','2018-03-06','2018-03-09','2018-03-09','87100')");                              
			st.executeLargeUpdate("insert into PracticalExam values(0,111,'안전관리','2018-04-08','2018-03-06','2018-03-09','2018-03-09','87100')");                              
			st.executeLargeUpdate("insert into PracticalExam values(0,111,'비파괴검사','2018-04-08','2018-03-06','2018-03-09','2018-03-09','87100')");                              
			st.executeLargeUpdate("insert into PracticalExam values(0,111,'환경 ','2018-04-08','2018-03-06','2018-03-09','2018-03-09','87100')");                              
			
			st.executeLargeUpdate("insert into PracticalExam values(0,112,'생산관리','2018-07-22','2018-06-19','2018-06-22','2018-08-11','87100')");                              
			st.executeLargeUpdate("insert into PracticalExam values(0,112,'디자인','2018-07-22','2018-06-19','2018-06-22','2018-08-11','87100')");                              
			st.executeLargeUpdate("insert into PracticalExam values(0,112,'건축','2018-07-22','2018-06-19','2018-06-22','2018-08-11','87100')");                              
			st.executeLargeUpdate("insert into PracticalExam values(0,112,'토목','2018-07-22','2018-06-19','2018-06-22','2018-08-11','87100')");                              
			st.executeLargeUpdate("insert into PracticalExam values(0,112,'조경','2018-07-22','2018-06-19','2018-06-22','2018-08-11','87100')");                              
			st.executeLargeUpdate("insert into PracticalExam values(0,112,'도시.교통','2018-07-22','2018-06-19','2018-06-22','2018-08-11','87100')");                              
			st.executeLargeUpdate("insert into PracticalExam values(0,112,'채광 ','2018-07-22','2018-06-19','2018-06-22','2018-08-11','87100')");                              
			st.executeLargeUpdate("insert into PracticalExam values(0,112,'기계제작','2018-07-22','2018-06-19','2018-06-22','2018-08-11','87100')");                              
			st.executeLargeUpdate("insert into PracticalExam values(0,112,'철도 ','2018-07-22','2018-06-19','2018-06-22','2018-08-11','87100')");                              
			st.executeLargeUpdate("insert into PracticalExam values(0,112,'조선 ','2018-07-22','2018-06-19','2018-06-22','2018-08-11','87100')");                              
			st.executeLargeUpdate("insert into PracticalExam values(0,112,'금형.공작기계','2018-07-22','2018-06-19','2018-06-22','2018-08-11','87100')");                              
			st.executeLargeUpdate("insert into PracticalExam values(0,112,'금속.재료','2018-07-22','2018-06-19','2018-06-22','2018-08-11','87100')");                              
			st.executeLargeUpdate("insert into PracticalExam values(0,112,'화공','2018-07-22','2018-06-19','2018-06-22','2018-08-11','87100')");                              
			st.executeLargeUpdate("insert into PracticalExam values(0,112,'섬유 ','2018-07-22','2018-06-19','2018-06-22','2018-08-11','87100')");                              
			st.executeLargeUpdate("insert into PracticalExam values(0,112,'전기 ','2018-07-22','2018-06-19','2018-06-22','2018-08-11','87100')");                              
			st.executeLargeUpdate("insert into PracticalExam values(0,112,'전자','2018-07-22','2018-06-19','2018-06-22','2018-08-11','87100')");                              
			st.executeLargeUpdate("insert into PracticalExam values(0,112,'식품 ','2018-07-22','2018-06-19','2018-06-22','2018-08-11','87100')");                              
			st.executeLargeUpdate("insert into PracticalExam values(0,112,'농업 ','2018-07-22','2018-06-19','2018-06-22','2018-08-11','87100')");                              
			st.executeLargeUpdate("insert into PracticalExam values(0,112,'안전관리 ','2018-07-22','2018-06-19','2018-06-22','2018-08-11','87100')");                              
			st.executeLargeUpdate("insert into PracticalExam values(0,112,'환경 ','2018-07-22','2018-06-19','2018-06-22','2018-08-11','87100')");                              
			st.executeLargeUpdate("insert into PracticalExam values(0,112,'에너지.기상','2018-07-22','2018-06-19','2018-06-22','2018-08-11','87100')");                              
			
			st.executeLargeUpdate("insert into PracticalExam values(0,113,'생산관리 ','2018-10-28','2018-09-18','2018-09-21','2018-11-17','87100')");                              
			st.executeLargeUpdate("insert into PracticalExam values(0,113,'건축 ','2018-10-28','2018-09-18','2018-09-21','2018-11-17','87100')");                              
			st.executeLargeUpdate("insert into PracticalExam values(0,113,'토목 ','2018-10-28','2018-09-18','2018-09-21','2018-11-17','87100')");                              
			st.executeLargeUpdate("insert into PracticalExam values(0,113,'도시.교통','2018-10-28','2018-09-18','2018-09-21','2018-11-17','87100')");                              
			st.executeLargeUpdate("insert into PracticalExam values(0,113,'기계장비설비.설치','2018-10-28','2018-09-18','2018-09-21','2018-11-17','87100')");                              
			st.executeLargeUpdate("insert into PracticalExam values(0,113,'철도 ','2018-10-28','2018-09-18','2018-09-21','2018-11-17','87100')");                              
			st.executeLargeUpdate("insert into PracticalExam values(0,113,'항공 ','2018-10-28','2018-09-18','2018-09-21','2018-11-17','87100')");                              
			st.executeLargeUpdate("insert into PracticalExam values(0,113,'자동차 ','2018-10-28','2018-09-18','2018-09-21','2018-11-17','87100')");                              
			st.executeLargeUpdate("insert into PracticalExam values(0,113,'용접 ','2018-10-28','2018-09-18','2018-09-21','2018-11-17','87100')");                              
			st.executeLargeUpdate("insert into PracticalExam values(0,113,'전기 ','2018-10-28','2018-09-18','2018-09-21','2018-11-17','87100')");                              
			st.executeLargeUpdate("insert into PracticalExam values(0,113,'정보기술','2018-10-28','2018-09-18','2018-09-21','2018-11-17','87100')");                              
			st.executeLargeUpdate("insert into PracticalExam values(0,113,'식품 ','2018-10-28','2018-09-18','2018-09-21','2018-11-17','87100')");                              
			st.executeLargeUpdate("insert into PracticalExam values(0,113,'농업 ','2018-10-28','2018-09-18','2018-09-21','2018-11-17','87100')");                              
			st.executeLargeUpdate("insert into PracticalExam values(0,113,'임업','2018-10-28','2018-09-18','2018-09-21','2018-11-17','87100')");                              
			st.executeLargeUpdate("insert into PracticalExam values(0,113,'안전관리 ','2018-10-28','2018-09-18','2018-09-21','2018-11-17','87100')");                              
			st.executeLargeUpdate("insert into PracticalExam values(0,113,'환경','2018-10-28','2018-09-18','2018-09-21','2018-11-17','87100')");                              
			
			
			st.executeLargeUpdate("insert into PracticalExam values(1,61,'이용.미용','2018-04-15','2018-03-20','2018-03-23','2018-05-12','79900')");  
			st.executeLargeUpdate("insert into PracticalExam values(1,61,'조리 ','2018-04-15','2018-03-20','2018-03-23','2018-05-12','79900')");  
			st.executeLargeUpdate("insert into PracticalExam values(1,61,'건축 ','2018-04-15','2018-03-20','2018-03-23','2018-05-12','79900')");  
			st.executeLargeUpdate("insert into PracticalExam values(1,61,'기계제작 ','2018-04-15','2018-03-20','2018-03-23','2018-05-12','79900')");  
			st.executeLargeUpdate("insert into PracticalExam values(1,61,'판금.제관.새시','2018-04-15','2018-03-20','2018-03-23','2018-05-12','79900')");  
			st.executeLargeUpdate("insert into PracticalExam values(1,61,'용접','2018-04-15','2018-03-20','2018-03-23','2018-05-12','79900')");  
			st.executeLargeUpdate("insert into PracticalExam values(1,61,'전기 ','2018-04-15','2018-03-20','2018-03-23','2018-05-12','79900')");  
			st.executeLargeUpdate("insert into PracticalExam values(1,61,'전자 ','2018-04-15','2018-03-20','2018-03-23','2018-05-12','79900')");  
			st.executeLargeUpdate("insert into PracticalExam values(1,61,'제과.제빵','2018-04-15','2018-03-20','2018-03-23','2018-05-12','79900')");  
			
			st.executeLargeUpdate("insert into PracticalExam values(1,61,'건설배관','2018-04-15','2018-03-20','2018-03-23','2018-05-26','79900')");                              
			st.executeLargeUpdate("insert into PracticalExam values(1,61,'기계장비설비.설치','2018-04-15','2018-03-20','2018-03-23','2018-05-26','79900')");                              
			st.executeLargeUpdate("insert into PracticalExam values(1,61,'자동차','2018-04-15','2018-03-20','2018-03-23','2018-05-26','79900')");                              
			st.executeLargeUpdate("insert into PracticalExam values(1,61,'금형.공작기계','2018-04-15','2018-03-20','2018-03-23','2018-05-26','79900')");                              
			st.executeLargeUpdate("insert into PracticalExam values(1,61,'금속.재료','2018-04-15','2018-03-20','2018-03-23','2018-05-26','79900')");                              
			st.executeLargeUpdate("insert into PracticalExam values(1,61,'위험물','2018-04-15','2018-03-20','2018-03-23','2018-05-26','79900')");                              
			st.executeLargeUpdate("insert into PracticalExam values(1,61,'안전관리','2018-04-15','2018-03-20','2018-03-23','2018-05-26','79900')");                              
			st.executeLargeUpdate("insert into PracticalExam values(1,61,'에너지.기상','2018-04-15','2018-03-20','2018-03-23','2018-05-26','79900')");                              
			
			
			st.executeLargeUpdate("insert into PracticalExam values(1,62,'이용.미용','2018-09-09','2018-07-24','2018-07-27','2018-09-29','79900')");  
			st.executeLargeUpdate("insert into PracticalExam values(1,62,'조리','2018-09-09','2018-07-24','2018-07-27','2018-09-29','79900')");  
			st.executeLargeUpdate("insert into PracticalExam values(1,62,'건축 ','2018-09-09','2018-07-24','2018-07-27','2018-09-29','79900')");  
			st.executeLargeUpdate("insert into PracticalExam values(1,62,'기계제작 ','2018-09-09','2018-07-24','2018-07-27','2018-09-29','79900')");  
			st.executeLargeUpdate("insert into PracticalExam values(1,62,'용접','2018-09-09','2018-07-24','2018-07-27','2018-09-29','79900')");  
			st.executeLargeUpdate("insert into PracticalExam values(1,62,'전기 ','2018-09-09','2018-07-24','2018-07-27','2018-09-29','79900')");  
			st.executeLargeUpdate("insert into PracticalExam values(1,62,'전자 ','2018-09-09','2018-07-24','2018-07-27','2018-09-29','79900')");  
			st.executeLargeUpdate("insert into PracticalExam values(1,62,'제과.제빵','2018-09-09','2018-07-24','2018-07-27','2018-09-29','79900')");  
			st.executeLargeUpdate("insert into PracticalExam values(1,62,'목재.가구.공예','2018-09-09','2018-07-24','2018-07-27','2018-09-29','79900')");  
			
			st.executeLargeUpdate("insert into PracticalExam values(1,62,'건설배관 ','2018-09-09','2018-07-24','2018-07-27','2018-10-20','79900')");                              
			st.executeLargeUpdate("insert into PracticalExam values(1,62,'기계장비설비.설치','2018-09-09','2018-07-24','2018-07-27','2018-10-20','79900')");                              
			st.executeLargeUpdate("insert into PracticalExam values(1,62,'철도 ','2018-09-09','2018-07-24','2018-07-27','2018-10-20','79900')");                              
			st.executeLargeUpdate("insert into PracticalExam values(1,62,'금속.재료','2018-09-09','2018-07-24','2018-07-27','2018-10-20','79900')");                              
			st.executeLargeUpdate("insert into PracticalExam values(1,62,'단조.주조','2018-09-09','2018-07-24','2018-07-27','2018-10-20','79900')");                              
			st.executeLargeUpdate("insert into PracticalExam values(1,62,'도장.도금','2018-09-09','2018-07-24','2018-07-27','2018-10-20','79900')");                              
			st.executeLargeUpdate("insert into PracticalExam values(1,62,'위험물 ','2018-09-09','2018-07-24','2018-07-27','2018-10-20','79900')");                              
			st.executeLargeUpdate("insert into PracticalExam values(1,62,'안전관리','2018-09-09','2018-07-24','2018-07-27','2018-10-20','79900')");                              
			st.executeLargeUpdate("insert into PracticalExam values(1,62,'에너지.기상','2018-09-09','2018-07-24','2018-07-27','2018-10-20','79900')");                              
			
			String start = new String();
			String end = new String();
			String exam = new String();
			String announ = new String();
			int cost;
			int rounds;
			String name = "toeic";
					
			//전체시험조회		
			rs = st.executeQuery("SELECT * "                                                         
							+ "FROM engexam,engexam_list "
							+ "WHERE engexam.Eng_name = '"+name+"'"
							+ " AND engexam.Eng_num = engexam_list.Eng_num"); 
					while(rs.next()){   
						if(rs.getInt("Rounds")!=0) rounds = rs.getInt("Rounds");
						start = rs.getString("Start_register");
						end = rs.getString("End_register");
						exam = rs.getString("Exam_date");
						announ = rs.getString("Announ_date");
						cost = rs.getInt("Cost");
						System.out.println(start+"   "+end+"   "+exam+"   "+announ+"   "+cost);
						}  



					//가까운시험조회
					Date nowDate = new Date();
				      Date afterDate = new Date();
				      Calendar cal = Calendar.getInstance();
				      cal.setTime(nowDate);
				      cal.add(Calendar.MONTH, 1);
				      
				      afterDate = cal.getTime();
				      String now = new SimpleDateFormat("yyyy-MM-dd").format(nowDate).toString();
				      String after = new SimpleDateFormat("yyyy-MM-dd").format(afterDate).toString();
					 
					 rs = st.executeQuery("SELECT * "                                                         
							+ "FROM engexam_list "                                                                                    
							+ "WHERE eng_num = 0 "
							+ "AND exam_date > '"+now+"'"
							+ " AND exam_date <= '"+after+"'"); 
					 	
					while(rs.next()){   
						if(rs.getInt("Rounds")!=0) rounds = rs.getInt("Rounds");
						start = rs.getString("Start_register");
						end = rs.getString("End_register");
						exam = rs.getString("Exam_date");
						announ = rs.getString("Announ_date");
						cost = rs.getInt("Cost");
					} 




					//선택기간시험조회
					String from = "2017-12-04";  //예시(입력받은 date 넣어주면됨)
					String to = "2018-03-11";
					
					rs = st.executeQuery("SELECT * "                                                         
							+ "FROM engexam_list "                                                                                    
							+ "WHERE eng_num = 0 "
							+ "AND exam_date > '"+from+"'"
							+ " AND exam_date <= '"+to+"'"); 
					while(rs.next()){   
						if(rs.getInt("Rounds")!=0) rounds = rs.getInt("Rounds");
						start = rs.getString("Start_register");
						end = rs.getString("End_register");
						exam = rs.getString("Exam_date");
						announ = rs.getString("Announ_date");
						cost = rs.getInt("Cost");
					}
					
					
					
					String string = "기술사";
					//자격증 전체시험조회
					rs = st.executeQuery("select * "
				            + "from certexam, certlist, WrittenExam "
				            + "where certexam.cert_num = certlist.cert_num "
				            + "and certexam.cert_name = '"+string+"' "
				            + "and certlist.rounds = WrittenExam.Rounds ");
					
					
					//자격증 가까운 시험조회
					rs = st.executeQuery("select * "
				            + "from certexam, certlist, WrittenExam "
				            + "where certexam.cert_num = certlist.cert_num "
				            + "and certexam.cert_name = '"+string+"' "
				            + "and certlist.rounds = WrittenExam.Rounds "
				            + "AND exam_date > '"+now+"'"
							+ " AND exam_date <= '"+after+"'");
					
					//자격증 선택기간 시험조회
					rs = st.executeQuery("select * "
				            + "from certexam, certlist, WrittenExam "
				            + "where certexam.cert_num = certlist.cert_num "
				            + "and certexam.cert_name = '"+string+"' "
				            + "and certlist.rounds = WrittenExam.Rounds "
				            + "AND exam_date > '"+from+"'"
							+ " AND exam_date <= '"+to+"'"); 
					
	}
}
