## While

### A+B -5 (10952번)

```java
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class Main {

	public static void main(String[] args) throws NumberFormatException, IOException {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st;
		
		while(true) {
			st = new StringTokenizer(br.readLine());
			int b = Integer.parseInt(st.nextToken());
			int c = Integer.parseInt(st.nextToken());
			
			if(b ==0 && c ==0) {
				break;
			}
			bw.write(b+c+"\n");
		}

		bw.flush();
	}

}
```

### A + B -4 (10951번)

```java
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class Main {

	public static void main(String[] args) throws NumberFormatException, IOException {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		 
		String s;
		
		while((s=br.readLine()) != null) {
			StringTokenizer st = new StringTokenizer(s);
			int b = Integer.parseInt(st.nextToken());
			int c = Integer.parseInt(st.nextToken());
					
			bw.write(b+c+"\n");
				
		}
		bw.flush();	

	}

}
```

###  더하기 사이클 (1110번)

```java
import java.util.*;

public class Main{
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        
        int n = sc.nextInt();
        
        if (n < 0 || n > 99)
        {
        	System.out.println("0이상 99이하의 정수만 입력하세요.");
        	n = sc.nextInt();
        }
        
        int cnt = 0, result = n;
        int tmp = 0;
        
        while (true)
        {            
            if (result < 10)
            {
                result = (result * 10) + (result % 10); 
            }
            else 
            {
		        tmp = (result / 10) + (result % 10);       
		        result = ((result % 10) * 10) + (tmp % 10); 
            }
            cnt++;   
                      
            if (n == result) { break; }
        }
        System.out.println(cnt);
    }
}
```

