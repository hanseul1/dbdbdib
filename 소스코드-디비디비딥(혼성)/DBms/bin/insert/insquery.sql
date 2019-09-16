package insert;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {
	public static Connection getConnection() throws ClassNotFoundException, SQLException{
		String url = "jdbc:mysql://localhost";
		String user = "root";
		String pass = "12345";
		
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
		
		st.executeUpdate("CREATE TABLE certexam"
				+ "(Exam_num	int		NOT NULL,"
				+ "cert_num		int	 	NOT NULL,"
				+ "cert_name		varchar(10)	 	NOT NULL,"
				+ "primary key(cert_num))");
		
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
		
		st.executeUpdate("CREATE TABLE certexam_list"
				+ "(Cert_num	int		NOT NULL,"
				+ "Kind		int		NOT NULL,"
				+ "Name		varchar(10)	 	NOT NULL,"
				+ "Rounds		int		NOT NULL,"
				+ "Test		int		NOT NULL,"
				+ "Start_register		DATE	 	NOT NULL,"
				+ "End_register		DATE	 	NOT NULL,"
				+ "Exam_date		DATE	 	NOT NULL,"
				+ "Announ_date		DATE	 	NOT NULL,"
				+ "Cost		int	 	NOT NULL,"
				+ "primary key(Cert_num,Kind,Rounds))");
		
		st.executeUpdate("ALTER TABLE engexam ADD FOREIGN KEY(Exam_num) REFERENCES exam(Exam_num)");
		st.executeUpdate("ALTER TABLE certexam ADD FOREIGN KEY(Exam_num) REFERENCES exam(Exam_num)");
		st.executeUpdate("ALTER TABLE engexam_list ADD FOREIGN KEY(Eng_num) REFERENCES engexam(Eng_num)");
		st.executeUpdate("ALTER TABLE certexam_list ADD FOREIGN KEY(Cert_num) REFERENCES certexam(Cert_num)");
		
		st.executeLargeUpdate("insert into exam values(0,'engexam')");
		st.executeLargeUpdate("insert into exam values(1,'certexam')");
		
		st.executeLargeUpdate("insert into engexam values(0,0,'toeic')");
		st.executeLargeUpdate("insert into engexam values(0,1,'opic')");
		st.executeLargeUpdate("insert into engexam values(0,2,'tos')");
		st.executeLargeUpdate("insert into engexam values(0,3,'jpt')");
		
		//토익 테이블 insert
		for(int i = 0 ; i<toeicInfo.num; i++) {
			st.executeLargeUpdate("insert into Engexam_list values(0,0,'"+toeicInfo.th[i]+"','"+toeicInfo.appStartDate[i]+"','"+toeicInfo.appEndDate[i]+"','"+toeicInfo.examdate[i]+"','"+toeicInfo.resultdate[i]+"',44500)");
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
		
		int rounds;
		String start = new String();
		String end = new String();
		String exam = new String();
		String announ = new String();
		int cost;
		int i;
		String name = "opic";
		
			rs = st.executeQuery("SELECT * "                                                         
					+ "FROM engexam "
					+ "WHERE engexam.Eng_name = "+name);
					//+ " AND engexam.Eng_num = engexam_list.Eng_num"); 
			while(rs.next()){   
				if(rs.getInt("Rounds")!=0) rounds = rs.getInt("Rounds");
				start = rs.getString("Start_register");
				end = rs.getString("End_register");
				exam = rs.getString("Exam_date");
				announ = rs.getString("Announ_date");
				cost = rs.getInt("Cost");
				System.out.println(start+"   "+end+"   "+exam+"   "+announ+"   "+cost);
			}  
		
//		 String now = new SimpleDateFormat("yyyy-MM-dd").format(new Date()).toString();
//		rs = st.executeQuery("SELECT * "                                                         
//				+ "FROM engexam_list "                                                                                    
//				+ "WHERE eng_num = 0 "
//				+ "AND exam_date>"+ now
//				+ " AND exam_date<=date"+(now() - INTERVAL 30 DAY))); 
//		while(rs.next()){   
//			if(rs.getInt("Rounds")!=0) rounds = rs.getInt("Rounds");
//			start = rs.getString("Start_register");
//			end = rs.getString("End_register");
//			exam = rs.getString("Exam_date");
//			announ = rs.getString("Announ_date");
//			cost = rs.getInt("Cost");
//			System.out.println(start+"   "+end+"   "+exam+"   "+announ+"   "+cost);
//		}  
	}
}
