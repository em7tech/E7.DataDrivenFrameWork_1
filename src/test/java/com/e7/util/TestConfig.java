package com.e7.util;
public class TestConfig{


	//MAIL CONFIGURATION 
	public static String server="smtp.gmail.com";
	public static String from = "techem7.qa@gmail.com";
	public static String password = "akaem76785";
	public static String[] to ={" emranshow007@gmail.com","babussalam.qe@gmail.com"};
	public static String subject = "Extent Project Report";
	
	public static String messageBody ="TestMessage";
	public static String attachmentPath="C:\\ScreenShot\\err.png";
	public static String attachmentName="error.png";
	
	
	
	//SQL DATABASE DETAILS	
	public static String driver="net.sourceforge.jtds.jdbc.Driver"; 
	public static String dbConnectionUrl="jdbc:jtds:sqlserver://192.101.44.22;DatabaseName=monitor_eval"; 
	public static String dbUserName="sa"; 
	public static String dbPassword="$ql$!!1"; 
	
	
	//MYSQL DATABASE DETAILS
	public static String mysqldriver="com.mysql.jdbc.Driver";
	public static String mysqluserName = "root";
	public static String mysqlpassword = "allahalmighty6785";
	public static String mysqlurl = "jdbc:mysql://localhost:1433/emdb";
	
	
	
	
	
	
	
	
	
}
