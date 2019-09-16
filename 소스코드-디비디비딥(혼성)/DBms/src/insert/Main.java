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
		
		//���� ���̺� insert
		for(int i = 0 ; i<toeicInfo.num; i++) {
			st.executeLargeUpdate("insert into engexam_list values(0,0,'"+toeicInfo.th[i]+"','"+toeicInfo.appStartDate[i]+"','"+toeicInfo.appEndDate[i]+"','"+toeicInfo.examdate[i]+"','"+toeicInfo.resultdate[i]+"',44500)");
		}
		//���� ���̺� insert
		for (int i = 0; i<opicInfo.getNum(); i++) {
			st.executeLargeUpdate("insert into engexam_list values(1,0,0,'"+opicInfo.appStartDate[i]+"','"+opicInfo.appEndDate[i]+"','"+opicInfo.examDate[i]+"','"+opicInfo.resultDate[i]+"',78100)");
		}
		//�佺(����ŷ) ���̺� insert
		for (int i = 0; i<tosInfo.num; i++) {
			st.executeLargeUpdate("insert into engexam_list values(2,1,0,'"+tosInfo.appStartDate[i]+"','"+tosInfo.appEndDate[i]+"','"+tosInfo.examDate[i]+"','"+tosInfo.resultDate[i]+"',77000)");
		}
		
		//jpt ���̺� insert
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

			st.executeLargeUpdate("insert into certexam values(1,0,'�����')");                                  	
			st.executeLargeUpdate("insert into certexam values(1,1,'�����')");                                  
			st.executeLargeUpdate("insert into certexam values(1,2,'���,������')");                                  
			st.executeLargeUpdate("insert into certexam values(1,3,'��ɻ�')");                                  

			st.executeUpdate("CREATE TABLE certlist"
					+ "(Cert_num		int 	NOT NULL,"
					+ "Rounds			int		NOT NULL,"
					+ "Kind				varchar(10),"
					+ "Detail			varchar(120),"
					+ "primary key(Rounds, Kind))"
					+ "ENGINE = InnoDB character set=utf8");

			st.executeUpdate("ALTER TABLE certlist convert to charset utf8");
			st.executeUpdate("ALTER TABLE certlist ADD FOREIGN KEY(Cert_num) REFERENCES certexam(Cert_num)");

			st.executeLargeUpdate("insert into certlist values(0,111,'�������','��������')");                                  
			st.executeLargeUpdate("insert into certlist values(0,111,'����','���౸�������,�����輳������,����ð������,����ǰ����������')");                                  
			st.executeLargeUpdate("insert into certlist values(0,111,'���','�����������,���ιװ��ױ����,���ϼ��������,���ڿ����߱����,���������,���������ݱ����,ö�������,�����������������������,����������,���ð������,���ǰ����������,�����ױ��ʱ����,�׸����ؾȱ����,�ؾ�����')");                                  
			st.executeLargeUpdate("insert into certlist values(0,111,'����','��������')");                                  
			st.executeLargeUpdate("insert into certlist values(0,111,'����.����','��������,���ð�ȹ�����')");                                  
			st.executeLargeUpdate("insert into certlist values(0,111,'�����񼳺�.��ġ','�Ǽ��������,�����õ��������,�����輳������')");                                  
			st.executeLargeUpdate("insert into certlist values(0,111,'�ڵ���','���������')");                                  
			st.executeLargeUpdate("insert into certlist values(0,111,'����.���۱��','���������')");                                  
			st.executeLargeUpdate("insert into certlist values(0,111,'�ݼ�.���','�ݼӰ��������,�ݼ��������')");                                  
			st.executeLargeUpdate("insert into certlist values(0,111,'����','���������')");                                  
			st.executeLargeUpdate("insert into certlist values(0,111,'����.����','ǥ��ó�������')");                                  
			st.executeLargeUpdate("insert into certlist values(0,111,'����','�������⼳������,�߼۹��������,ö����ȣ�����')");                                  
			st.executeLargeUpdate("insert into certlist values(0,111,'����','������������')");                                  
			st.executeLargeUpdate("insert into certlist values(0,111,'�������','�������������,��ǻ�ͽý�����������')");                                  
			st.executeLargeUpdate("insert into certlist values(0,111,'��ǰ','��ǰ�����')");                                  
			st.executeLargeUpdate("insert into certlist values(0,111,'���','���ڱ����')");                                  
			st.executeLargeUpdate("insert into certlist values(0,111,'���','�������')");                                  
			st.executeLargeUpdate("insert into certlist values(0,111,'�Ӿ�','�긲�����')");                                  
			st.executeLargeUpdate("insert into certlist values(0,111,'���','�����ı����,��α����')");                                  
			st.executeLargeUpdate("insert into certlist values(0,111,'��������','���������,�Ǽ����������,�����������,����������������,�ҹ�����,������������,ȭ�����������')");                                  
			st.executeLargeUpdate("insert into certlist values(0,111,'���ı��˻�','���ı��˻�����')");                                  
			st.executeLargeUpdate("insert into certlist values(0,111,'ȯ��','�����������,�������������,�������������,��⹰ó�������')");
			st.executeLargeUpdate("insert into certlist values(0,111,'������.���','��󿹺������')");                                  

			st.executeLargeUpdate("insert into certlist values(0,112,'�������','������������,ǰ�����������')");                                  
			st.executeLargeUpdate("insert into certlist values(0,112,'������','��ǰ�����α����')");                                  
			st.executeLargeUpdate("insert into certlist values(0,112,'����','���౸�������,�����輳������,����ð������')");                                  
			st.executeLargeUpdate("insert into certlist values(0,112,'���','�����������,���ιװ��ױ����,���ϼ��������,ö�������,����������,���ð������,���ǰ����������,�����ױ��ʱ����,�׸����ؾȱ����')");                                  
			st.executeLargeUpdate("insert into certlist values(0,112,'����','��������')");                                  
			st.executeLargeUpdate("insert into certlist values(0,112,'����.����','���ð�ȹ�����')");                                  
			st.executeLargeUpdate("insert into certlist values(0,112,'ä��','ȭ������������')");                                  
			st.executeLargeUpdate("insert into certlist values(0,112,'�������','�������')");                                  
			st.executeLargeUpdate("insert into certlist values(0,112,'ö��','ö�����������')");                                  
			st.executeLargeUpdate("insert into certlist values(0,112,'����','���������')");                                  
			st.executeLargeUpdate("insert into certlist values(0,112,'����.���۱��','���������')");                                  
			st.executeLargeUpdate("insert into certlist values(0,112,'�ݼ�.���','�ݼ����ñ����,����ͱ����')");                                  
			st.executeLargeUpdate("insert into certlist values(0,112,'ȭ��','ȭ�������')");                                  
			st.executeLargeUpdate("insert into certlist values(0,112,'����','���������,�Ƿ������')");                                  
			st.executeLargeUpdate("insert into certlist values(0,112,'����','�������⼳������,�߼۹��������,������������,����ö�������,ö����ȣ�����')");                                  
			st.executeLargeUpdate("insert into certlist values(0,112,'����','���������������')");                                  
			st.executeLargeUpdate("insert into certlist values(0,112,'��ǰ','�������������')");                                  
			st.executeLargeUpdate("insert into certlist values(0,112,'���','�ü����������')");                                  
			st.executeLargeUpdate("insert into certlist values(0,112,'��������','�Ǽ����������,�ҹ�����,�ΰ����б����')");                                  
			st.executeLargeUpdate("insert into certlist values(0,112,'ȯ��','�ڿ�ȯ����������,���ȯ������')");                                  
			st.executeLargeUpdate("insert into certlist values(0,112,'������.���','��󿹺������')");  
			
			st.executeLargeUpdate("insert into certlist values(0,113,'�������','ǰ�����������')");                              
			st.executeLargeUpdate("insert into certlist values(0,113,'����','���౸�������,�����輳������,����ð������')");                              
			st.executeLargeUpdate("insert into certlist values(0,113,'���','���ιװ��ױ����,���ϼ��������,���ڿ����߱����,���������,���������ݱ����,ö�������,�����������������������,����������,���ð������,�����ױ��ʱ����')");                              
			st.executeLargeUpdate("insert into certlist values(0,113,'����.����','��������,���ð�ȹ�����')");                              
			st.executeLargeUpdate("insert into certlist values(0,113,'�����񼳺�.��ġ','�Ǽ��������,�����õ��������')");                              
			st.executeLargeUpdate("insert into certlist values(0,113,'ö��','ö�����������')");                              
			st.executeLargeUpdate("insert into certlist values(0,113,'�װ�','�װ���������,�װ���ü�����')");                              
			st.executeLargeUpdate("insert into certlist values(0,113,'�ڵ���','���������')");                              
			st.executeLargeUpdate("insert into certlist values(0,113,'����','���������')");                              
			st.executeLargeUpdate("insert into certlist values(0,113,'����','�������⼳������,�߼۹��������,������������,����ö�������')");                              
			st.executeLargeUpdate("insert into certlist values(0,113,'�������','�������������,��ǻ�ͽý�����������')");                              
			st.executeLargeUpdate("insert into certlist values(0,113,'��ǰ','��ǰ�����')");                              
			st.executeLargeUpdate("insert into certlist values(0,113,'���','��ȭ�б����')");                              
			st.executeLargeUpdate("insert into certlist values(0,113,'�Ӿ�','�긲�����')");                              
			st.executeLargeUpdate("insert into certlist values(0,113,'��������','���������,�Ǽ����������,����������������,�ҹ�����,������������')");                              
			st.executeLargeUpdate("insert into certlist values(0,113,'ȯ��','�����������,�������������,�������������,�ڿ�ȯ����������,���ȯ������,��⹰ó�������')");                              
			
			
			st.executeLargeUpdate("insert into certlist values(1,61,'�̿�.�̿�','�̿���,�̿���')");                              
			st.executeLargeUpdate("insert into certlist values(1,61,'���� ','���������')");                              
			st.executeLargeUpdate("insert into certlist values(1,61,'���� ','�������ð������')");                              
			st.executeLargeUpdate("insert into certlist values(1,61,'������� ','��谡�������')");                              
			st.executeLargeUpdate("insert into certlist values(1,61,'�Ǳ�.����.����','�Ǳ����������')");                              
			st.executeLargeUpdate("insert into certlist values(1,61,'���� ','���������')");                              
			st.executeLargeUpdate("insert into certlist values(1,61,'���� ','��������')");                              
			st.executeLargeUpdate("insert into certlist values(1,61,'���� ','���ڱ������')");                              
			st.executeLargeUpdate("insert into certlist values(1,61,'����.����','���������')"); 
			
			st.executeLargeUpdate("insert into certlist values(1,61,'�Ǽ���� ','��������')");                              
			st.executeLargeUpdate("insert into certlist values(1,61,'�����񼳺�.��ġ','�Ǽ������������')");                              
			st.executeLargeUpdate("insert into certlist values(1,61,'�ڵ��� ','�ڵ�����������')");                              
			st.executeLargeUpdate("insert into certlist values(1,61,'����.���۱��','�������۱����')");                              
			st.executeLargeUpdate("insert into certlist values(1,61,'�ݼ�.���','��ݼ��������,�п������,���������,��������帲�����')");                              
			st.executeLargeUpdate("insert into certlist values(1,61,'���蹰 ','���蹰�����')");                              
			st.executeLargeUpdate("insert into certlist values(1,61,'��������','���������')");                              
			st.executeLargeUpdate("insert into certlist values(1,61,'������.���','���������������')");                              
			
			
			st.executeLargeUpdate("insert into certlist values(1,62,'�̿�.�̿�','�̿���,�̿���')");                              
			st.executeLargeUpdate("insert into certlist values(1,62,'����','���������')");                              
			st.executeLargeUpdate("insert into certlist values(1,62,'���� ','�����Ϲݽð������')");                              
			st.executeLargeUpdate("insert into certlist values(1,62,'������� ','��谡�������')");                              
			st.executeLargeUpdate("insert into certlist values(1,62,'���� ','���������')");                              
			st.executeLargeUpdate("insert into certlist values(1,62,'����','��������')");                              
			st.executeLargeUpdate("insert into certlist values(1,62,'���� ','���ڱ������')");                              
			st.executeLargeUpdate("insert into certlist values(1,62,'����.����','���������')");                              
			st.executeLargeUpdate("insert into certlist values(1,62,'����.����.����','�ͱݼӰ��������')");                              
			
			st.executeLargeUpdate("insert into certlist values(1,62,'�Ǽ���� ','��������')");                              
			st.executeLargeUpdate("insert into certlist values(1,62,'�����񼳺�.��ġ','�Ǽ������������')");                              
			st.executeLargeUpdate("insert into certlist values(1,62,'ö�� ','ö��������������')");                              
			st.executeLargeUpdate("insert into certlist values(1,62,'�ڵ��� ','�ڵ�����������')");                              
			st.executeLargeUpdate("insert into certlist values(1,62,'�ݼ�.���','�ݼ��������,�п������,���������')");                              
			st.executeLargeUpdate("insert into certlist values(1,62,'����.����','���������')");                              
			st.executeLargeUpdate("insert into certlist values(1,62,'����.����','ǥ��ó�������')");                              
			st.executeLargeUpdate("insert into certlist values(1,62,'���蹰 ','���蹰�����')");                              
			st.executeLargeUpdate("insert into certlist values(1,62,'�������� ','���������')");                              
			st.executeLargeUpdate("insert into certlist values(1,62,'������.���','���������������')");                              



			st.executeLargeUpdate("insert into certlist values(0,111,'�������','��������')");                                  
			st.executeLargeUpdate("insert into certlist values(0,111,'�������','��������')");                                  
			
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
			
			st.executeLargeUpdate("insert into PracticalExam values(0,111,'�������','2018-04-08','2018-03-06','2018-03-09','2018-03-09','87100')");                              
			st.executeLargeUpdate("insert into PracticalExam values(0,111,'����','2018-04-08','2018-03-06','2018-03-09','2018-03-09','87100')");                              
			st.executeLargeUpdate("insert into PracticalExam values(0,111,'���','2018-04-08','2018-03-06','2018-03-09','2018-03-09','87100')");                              
			st.executeLargeUpdate("insert into PracticalExam values(0,111,'����','2018-04-08','2018-03-06','2018-03-09','2018-03-09','87100')");                              
			st.executeLargeUpdate("insert into PracticalExam values(0,111,'����.����','2018-04-08','2018-03-06','2018-03-09','2018-03-09','87100')");                              
			st.executeLargeUpdate("insert into PracticalExam values(0,111,'�����񼳺�.��ġ','2018-04-08','2018-03-06','2018-03-09','2018-03-09','87100')");                              
			st.executeLargeUpdate("insert into PracticalExam values(0,111,'�ڵ���','2018-04-08','2018-03-06','2018-03-09','2018-03-09','87100')");                              
			st.executeLargeUpdate("insert into PracticalExam values(0,111,'����.���۱��','2018-04-08','2018-03-06','2018-03-09','2018-03-09','87100')");                              
			st.executeLargeUpdate("insert into PracticalExam values(0,111,'�ݼ�.���','2018-04-08','2018-03-06','2018-03-09','2018-03-09','87100')");                              
			st.executeLargeUpdate("insert into PracticalExam values(0,111,'����','2018-04-08','2018-03-06','2018-03-09','2018-03-09','87100')");                              
			st.executeLargeUpdate("insert into PracticalExam values(0,111,'����.����','2018-04-08','2018-03-06','2018-03-09','2018-03-09','87100')");                              
			st.executeLargeUpdate("insert into PracticalExam values(0,111,'����','2018-04-08','2018-03-06','2018-03-09','2018-03-09','87100')");                              
			st.executeLargeUpdate("insert into PracticalExam values(0,111,'����','2018-04-08','2018-03-06','2018-03-09','2018-03-09','87100')");                              
			st.executeLargeUpdate("insert into PracticalExam values(0,111,'�������','2018-04-08','2018-03-06','2018-03-09','2018-03-09','87100')");                              
			st.executeLargeUpdate("insert into PracticalExam values(0,111,'��ǰ','2018-04-08','2018-03-06','2018-03-09','2018-03-09','87100')");                              
			st.executeLargeUpdate("insert into PracticalExam values(0,111,'���','2018-04-08','2018-03-06','2018-03-09','2018-03-09','87100')");                              
			st.executeLargeUpdate("insert into PracticalExam values(0,111,'���','2018-04-08','2018-03-06','2018-03-09','2018-03-09','87100')");                              
			st.executeLargeUpdate("insert into PracticalExam values(0,111,'�Ӿ�','2018-04-08','2018-03-06','2018-03-09','2018-03-09','87100')");                              
			st.executeLargeUpdate("insert into PracticalExam values(0,111,'���','2018-04-08','2018-03-06','2018-03-09','2018-03-09','87100')");                              
			st.executeLargeUpdate("insert into PracticalExam values(0,111,'��������','2018-04-08','2018-03-06','2018-03-09','2018-03-09','87100')");                              
			st.executeLargeUpdate("insert into PracticalExam values(0,111,'���ı��˻�','2018-04-08','2018-03-06','2018-03-09','2018-03-09','87100')");                              
			st.executeLargeUpdate("insert into PracticalExam values(0,111,'ȯ�� ','2018-04-08','2018-03-06','2018-03-09','2018-03-09','87100')");                              
			
			st.executeLargeUpdate("insert into PracticalExam values(0,112,'�������','2018-07-22','2018-06-19','2018-06-22','2018-08-11','87100')");                              
			st.executeLargeUpdate("insert into PracticalExam values(0,112,'������','2018-07-22','2018-06-19','2018-06-22','2018-08-11','87100')");                              
			st.executeLargeUpdate("insert into PracticalExam values(0,112,'����','2018-07-22','2018-06-19','2018-06-22','2018-08-11','87100')");                              
			st.executeLargeUpdate("insert into PracticalExam values(0,112,'���','2018-07-22','2018-06-19','2018-06-22','2018-08-11','87100')");                              
			st.executeLargeUpdate("insert into PracticalExam values(0,112,'����','2018-07-22','2018-06-19','2018-06-22','2018-08-11','87100')");                              
			st.executeLargeUpdate("insert into PracticalExam values(0,112,'����.����','2018-07-22','2018-06-19','2018-06-22','2018-08-11','87100')");                              
			st.executeLargeUpdate("insert into PracticalExam values(0,112,'ä�� ','2018-07-22','2018-06-19','2018-06-22','2018-08-11','87100')");                              
			st.executeLargeUpdate("insert into PracticalExam values(0,112,'�������','2018-07-22','2018-06-19','2018-06-22','2018-08-11','87100')");                              
			st.executeLargeUpdate("insert into PracticalExam values(0,112,'ö�� ','2018-07-22','2018-06-19','2018-06-22','2018-08-11','87100')");                              
			st.executeLargeUpdate("insert into PracticalExam values(0,112,'���� ','2018-07-22','2018-06-19','2018-06-22','2018-08-11','87100')");                              
			st.executeLargeUpdate("insert into PracticalExam values(0,112,'����.���۱��','2018-07-22','2018-06-19','2018-06-22','2018-08-11','87100')");                              
			st.executeLargeUpdate("insert into PracticalExam values(0,112,'�ݼ�.���','2018-07-22','2018-06-19','2018-06-22','2018-08-11','87100')");                              
			st.executeLargeUpdate("insert into PracticalExam values(0,112,'ȭ��','2018-07-22','2018-06-19','2018-06-22','2018-08-11','87100')");                              
			st.executeLargeUpdate("insert into PracticalExam values(0,112,'���� ','2018-07-22','2018-06-19','2018-06-22','2018-08-11','87100')");                              
			st.executeLargeUpdate("insert into PracticalExam values(0,112,'���� ','2018-07-22','2018-06-19','2018-06-22','2018-08-11','87100')");                              
			st.executeLargeUpdate("insert into PracticalExam values(0,112,'����','2018-07-22','2018-06-19','2018-06-22','2018-08-11','87100')");                              
			st.executeLargeUpdate("insert into PracticalExam values(0,112,'��ǰ ','2018-07-22','2018-06-19','2018-06-22','2018-08-11','87100')");                              
			st.executeLargeUpdate("insert into PracticalExam values(0,112,'��� ','2018-07-22','2018-06-19','2018-06-22','2018-08-11','87100')");                              
			st.executeLargeUpdate("insert into PracticalExam values(0,112,'�������� ','2018-07-22','2018-06-19','2018-06-22','2018-08-11','87100')");                              
			st.executeLargeUpdate("insert into PracticalExam values(0,112,'ȯ�� ','2018-07-22','2018-06-19','2018-06-22','2018-08-11','87100')");                              
			st.executeLargeUpdate("insert into PracticalExam values(0,112,'������.���','2018-07-22','2018-06-19','2018-06-22','2018-08-11','87100')");                              
			
			st.executeLargeUpdate("insert into PracticalExam values(0,113,'������� ','2018-10-28','2018-09-18','2018-09-21','2018-11-17','87100')");                              
			st.executeLargeUpdate("insert into PracticalExam values(0,113,'���� ','2018-10-28','2018-09-18','2018-09-21','2018-11-17','87100')");                              
			st.executeLargeUpdate("insert into PracticalExam values(0,113,'��� ','2018-10-28','2018-09-18','2018-09-21','2018-11-17','87100')");                              
			st.executeLargeUpdate("insert into PracticalExam values(0,113,'����.����','2018-10-28','2018-09-18','2018-09-21','2018-11-17','87100')");                              
			st.executeLargeUpdate("insert into PracticalExam values(0,113,'�����񼳺�.��ġ','2018-10-28','2018-09-18','2018-09-21','2018-11-17','87100')");                              
			st.executeLargeUpdate("insert into PracticalExam values(0,113,'ö�� ','2018-10-28','2018-09-18','2018-09-21','2018-11-17','87100')");                              
			st.executeLargeUpdate("insert into PracticalExam values(0,113,'�װ� ','2018-10-28','2018-09-18','2018-09-21','2018-11-17','87100')");                              
			st.executeLargeUpdate("insert into PracticalExam values(0,113,'�ڵ��� ','2018-10-28','2018-09-18','2018-09-21','2018-11-17','87100')");                              
			st.executeLargeUpdate("insert into PracticalExam values(0,113,'���� ','2018-10-28','2018-09-18','2018-09-21','2018-11-17','87100')");                              
			st.executeLargeUpdate("insert into PracticalExam values(0,113,'���� ','2018-10-28','2018-09-18','2018-09-21','2018-11-17','87100')");                              
			st.executeLargeUpdate("insert into PracticalExam values(0,113,'�������','2018-10-28','2018-09-18','2018-09-21','2018-11-17','87100')");                              
			st.executeLargeUpdate("insert into PracticalExam values(0,113,'��ǰ ','2018-10-28','2018-09-18','2018-09-21','2018-11-17','87100')");                              
			st.executeLargeUpdate("insert into PracticalExam values(0,113,'��� ','2018-10-28','2018-09-18','2018-09-21','2018-11-17','87100')");                              
			st.executeLargeUpdate("insert into PracticalExam values(0,113,'�Ӿ�','2018-10-28','2018-09-18','2018-09-21','2018-11-17','87100')");                              
			st.executeLargeUpdate("insert into PracticalExam values(0,113,'�������� ','2018-10-28','2018-09-18','2018-09-21','2018-11-17','87100')");                              
			st.executeLargeUpdate("insert into PracticalExam values(0,113,'ȯ��','2018-10-28','2018-09-18','2018-09-21','2018-11-17','87100')");                              
			
			
			st.executeLargeUpdate("insert into PracticalExam values(1,61,'�̿�.�̿�','2018-04-15','2018-03-20','2018-03-23','2018-05-12','79900')");  
			st.executeLargeUpdate("insert into PracticalExam values(1,61,'���� ','2018-04-15','2018-03-20','2018-03-23','2018-05-12','79900')");  
			st.executeLargeUpdate("insert into PracticalExam values(1,61,'���� ','2018-04-15','2018-03-20','2018-03-23','2018-05-12','79900')");  
			st.executeLargeUpdate("insert into PracticalExam values(1,61,'������� ','2018-04-15','2018-03-20','2018-03-23','2018-05-12','79900')");  
			st.executeLargeUpdate("insert into PracticalExam values(1,61,'�Ǳ�.����.����','2018-04-15','2018-03-20','2018-03-23','2018-05-12','79900')");  
			st.executeLargeUpdate("insert into PracticalExam values(1,61,'����','2018-04-15','2018-03-20','2018-03-23','2018-05-12','79900')");  
			st.executeLargeUpdate("insert into PracticalExam values(1,61,'���� ','2018-04-15','2018-03-20','2018-03-23','2018-05-12','79900')");  
			st.executeLargeUpdate("insert into PracticalExam values(1,61,'���� ','2018-04-15','2018-03-20','2018-03-23','2018-05-12','79900')");  
			st.executeLargeUpdate("insert into PracticalExam values(1,61,'����.����','2018-04-15','2018-03-20','2018-03-23','2018-05-12','79900')");  
			
			st.executeLargeUpdate("insert into PracticalExam values(1,61,'�Ǽ����','2018-04-15','2018-03-20','2018-03-23','2018-05-26','79900')");                              
			st.executeLargeUpdate("insert into PracticalExam values(1,61,'�����񼳺�.��ġ','2018-04-15','2018-03-20','2018-03-23','2018-05-26','79900')");                              
			st.executeLargeUpdate("insert into PracticalExam values(1,61,'�ڵ���','2018-04-15','2018-03-20','2018-03-23','2018-05-26','79900')");                              
			st.executeLargeUpdate("insert into PracticalExam values(1,61,'����.���۱��','2018-04-15','2018-03-20','2018-03-23','2018-05-26','79900')");                              
			st.executeLargeUpdate("insert into PracticalExam values(1,61,'�ݼ�.���','2018-04-15','2018-03-20','2018-03-23','2018-05-26','79900')");                              
			st.executeLargeUpdate("insert into PracticalExam values(1,61,'���蹰','2018-04-15','2018-03-20','2018-03-23','2018-05-26','79900')");                              
			st.executeLargeUpdate("insert into PracticalExam values(1,61,'��������','2018-04-15','2018-03-20','2018-03-23','2018-05-26','79900')");                              
			st.executeLargeUpdate("insert into PracticalExam values(1,61,'������.���','2018-04-15','2018-03-20','2018-03-23','2018-05-26','79900')");                              
			
			
			st.executeLargeUpdate("insert into PracticalExam values(1,62,'�̿�.�̿�','2018-09-09','2018-07-24','2018-07-27','2018-09-29','79900')");  
			st.executeLargeUpdate("insert into PracticalExam values(1,62,'����','2018-09-09','2018-07-24','2018-07-27','2018-09-29','79900')");  
			st.executeLargeUpdate("insert into PracticalExam values(1,62,'���� ','2018-09-09','2018-07-24','2018-07-27','2018-09-29','79900')");  
			st.executeLargeUpdate("insert into PracticalExam values(1,62,'������� ','2018-09-09','2018-07-24','2018-07-27','2018-09-29','79900')");  
			st.executeLargeUpdate("insert into PracticalExam values(1,62,'����','2018-09-09','2018-07-24','2018-07-27','2018-09-29','79900')");  
			st.executeLargeUpdate("insert into PracticalExam values(1,62,'���� ','2018-09-09','2018-07-24','2018-07-27','2018-09-29','79900')");  
			st.executeLargeUpdate("insert into PracticalExam values(1,62,'���� ','2018-09-09','2018-07-24','2018-07-27','2018-09-29','79900')");  
			st.executeLargeUpdate("insert into PracticalExam values(1,62,'����.����','2018-09-09','2018-07-24','2018-07-27','2018-09-29','79900')");  
			st.executeLargeUpdate("insert into PracticalExam values(1,62,'����.����.����','2018-09-09','2018-07-24','2018-07-27','2018-09-29','79900')");  
			
			st.executeLargeUpdate("insert into PracticalExam values(1,62,'�Ǽ���� ','2018-09-09','2018-07-24','2018-07-27','2018-10-20','79900')");                              
			st.executeLargeUpdate("insert into PracticalExam values(1,62,'�����񼳺�.��ġ','2018-09-09','2018-07-24','2018-07-27','2018-10-20','79900')");                              
			st.executeLargeUpdate("insert into PracticalExam values(1,62,'ö�� ','2018-09-09','2018-07-24','2018-07-27','2018-10-20','79900')");                              
			st.executeLargeUpdate("insert into PracticalExam values(1,62,'�ݼ�.���','2018-09-09','2018-07-24','2018-07-27','2018-10-20','79900')");                              
			st.executeLargeUpdate("insert into PracticalExam values(1,62,'����.����','2018-09-09','2018-07-24','2018-07-27','2018-10-20','79900')");                              
			st.executeLargeUpdate("insert into PracticalExam values(1,62,'����.����','2018-09-09','2018-07-24','2018-07-27','2018-10-20','79900')");                              
			st.executeLargeUpdate("insert into PracticalExam values(1,62,'���蹰 ','2018-09-09','2018-07-24','2018-07-27','2018-10-20','79900')");                              
			st.executeLargeUpdate("insert into PracticalExam values(1,62,'��������','2018-09-09','2018-07-24','2018-07-27','2018-10-20','79900')");                              
			st.executeLargeUpdate("insert into PracticalExam values(1,62,'������.���','2018-09-09','2018-07-24','2018-07-27','2018-10-20','79900')");                              
			
			String start = new String();
			String end = new String();
			String exam = new String();
			String announ = new String();
			int cost;
			int rounds;
			String name = "toeic";
					
			//��ü������ȸ		
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



					//����������ȸ
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




					//���ñⰣ������ȸ
					String from = "2017-12-04";  //����(�Է¹��� date �־��ָ��)
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
					
					
					
					String string = "�����";
					//�ڰ��� ��ü������ȸ
					rs = st.executeQuery("select * "
				            + "from certexam, certlist, WrittenExam "
				            + "where certexam.cert_num = certlist.cert_num "
				            + "and certexam.cert_name = '"+string+"' "
				            + "and certlist.rounds = WrittenExam.Rounds ");
					
					
					//�ڰ��� ����� ������ȸ
					rs = st.executeQuery("select * "
				            + "from certexam, certlist, WrittenExam "
				            + "where certexam.cert_num = certlist.cert_num "
				            + "and certexam.cert_name = '"+string+"' "
				            + "and certlist.rounds = WrittenExam.Rounds "
				            + "AND exam_date > '"+now+"'"
							+ " AND exam_date <= '"+after+"'");
					
					//�ڰ��� ���ñⰣ ������ȸ
					rs = st.executeQuery("select * "
				            + "from certexam, certlist, WrittenExam "
				            + "where certexam.cert_num = certlist.cert_num "
				            + "and certexam.cert_name = '"+string+"' "
				            + "and certlist.rounds = WrittenExam.Rounds "
				            + "AND exam_date > '"+from+"'"
							+ " AND exam_date <= '"+to+"'"); 
					
	}
}
