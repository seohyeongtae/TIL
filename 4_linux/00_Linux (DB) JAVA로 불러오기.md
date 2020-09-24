## Linux (DB) JAVA 로 불러오기

> **Oracle**

```java
package d01;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class OracleTest {

	public static void main(String[] args) throws Exception {
		String url = "jdbc:oracle:thin:@192.168.111.101:1521:xe";
		String id = "shop"; // 스키마 아이디 비밀번호로 접속한다. 오라클 user1/user2 는 웹에서 관리하기 위한 사용자
		String password = "111111";

		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection con = DriverManager.getConnection(url, id, password);
		PreparedStatement pstmt = con.prepareStatement("SELECT * FROM ITEMS");
		ResultSet rset = pstmt.executeQuery();
		while (rset.next()) {
			String did = rset.getString("ID");
			String name = rset.getString("NAME");
			int price = rset.getInt("PRICE");
			System.out.println(did + " " + name + " " + price);
		}
		con.close();
	}
}
```



> **MariaDB**

```java
package d01;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MariaTest {

	public static void main(String[] args) throws Exception {
		String url = "jdbc:mariadb://192.168.111.111:3306/shopdb";
		String id = "winuser"; 
		String password = "111111";

		Class.forName("org.mariadb.jdbc.Driver");
		Connection con = DriverManager.getConnection(url, id, password);
		PreparedStatement pstmt = con.prepareStatement("SELECT * FROM MENU");
		ResultSet rset = pstmt.executeQuery();
		while (rset.next()) {
			String did = rset.getString("id");
			String name = rset.getString("name");
			int price = rset.getInt("price");
			System.out.println(did + " " + name + " " + price);
		}
		con.close();
	}
}
```



> **Hive -MariaDb**
>
> Hive 는 lib 을 따로 Properties 해주어야한다.
>
> **hive --service hiveserver2**  명령어를 통해 대기상태를 만들어 주어야한다.

```java
package d01;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class HiveTest {

	public static void main(String[] args) throws Exception {
		String url = "jdbc:hive2://192.168.111.120:10000/default";
		String id = "root";
		String password = "111111";

		Class.forName("org.apache.hive.jdbc.HiveDriver");
		Connection con = DriverManager.getConnection(url, id, password);
		PreparedStatement pstmt = con.prepareStatement("SELECT Year, avg(ArrDelay), avg(DepDelay) FROM airline_delay \r\n" + 
				"WHERE delayYear='2006' GROUP BY Year");
		ResultSet rset = pstmt.executeQuery();
		while (rset.next()) {
			String s1 = rset.getString(1);
			String s2 = rset.getString(2);
			String s3 = rset.getString(3);
			System.out.println(s1 + " " + s2 + " " + s3 );
		}
		con.close();
	}
}
```



> * 알드라이브 설치 후에 (이클립스 WAR 파일을 Linux에서 실행시키기 위해 )
>
>   이클립스에 Dynamic Web Project 생성(파일명: linux) -> [index.html](http://index.html/) 생성 -> 'linux' Warfile
>   알드라이브 /pub 에 넣는다. /var/ftp/pub를 ls 하면 [linux.war](http://linux.war/) 가 있다.
>
> 
>
>   stoptomcat
>   cp [linux.war](http://linux.war/) /usr/local/[apache-tomcat-9.0.37/webapps/](http://apache-tomcat-9.0.37/webapps/)
>   cd /usr/local/[apache-tomcat-9.0.37/webapps/](http://apache-tomcat-9.0.37/webapps/)
>   ls
>   mv ROOT ROOT_BACK
>   ls
>   mv [linux.war](http://linux.war/) [ROOT.war](http://root.war/)
>   ls
>   starttomcat
>
>   /usr/local/[apache-tomcat-9.0.37/webapps/](http://apache-tomcat-9.0.37/webapps/)에 자동으로 ROOT 를 생성
>
> 
>
> find / -name ROOT 로 위치를 찾아가도되며 
>
> 기존 이름으로 넣고 실행시켜도 된다. 