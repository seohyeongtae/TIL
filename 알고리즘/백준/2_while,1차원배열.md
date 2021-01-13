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



## 1차원 배열

### 최소,최대 (10818번)

```java
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Scanner;
import java.util.StringTokenizer;


public class Main{
    public static void main(String[] args) throws IOException {
    	BufferedReader br2 = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br2.readLine());
        int[] num = new int[n];
        
        int max = -1000000;
        int min = 1000000;
        
    	
    	StringTokenizer st = new StringTokenizer(br2.readLine());
      
    	for(int i =0; i < n; i++) {
        	num[i] = Integer.parseInt(st.nextToken());
        	min = num[i];
        	if(max < num[i]) {
        		max = num[i];
        	}
        	if(min > num[i]) {
        		min = num[i];
        	}
        }
	        	
    	BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));  
    	bw.write(min+" "+max);
    	bw.flush();
    	
    }
}

```

### 최댓값 (2562번)

```java
import java.util.Scanner;

public class Main{
    public static void main(String[] args) {
    	Scanner sc = new Scanner(System.in);	
        
        int[] num = new int[8];
        
        int max = 0;
        int cnt = 0;
      
    	for(int i =0; i < 8; i++) {
        	num[i] = sc.nextInt();
        	
        	if(max < num[i]) {
        		max = num[i];
        		cnt = i+1;
        	}
        
        }
	        	
    	System.out.println(max);
    	System.out.println(cnt);
    	
    }
}

```

### 숫자의 개수 (2577번)  답 2개

```java
import java.util.Scanner;

public class Main{
    public static void main(String[] args) {
    	Scanner sc = new Scanner(System.in);	
    	
    	int a = sc.nextInt();
    	int b = sc.nextInt();
    	int c = sc.nextInt();
    	
    	String avg = a*b*c+"";
    	String cnt[] = new String[avg.length()];
    	int count[] = new int[10];
    	
    	for(int i =0; i<avg.length();i++) {
    		cnt[i] = avg.substring(i,i+1);
    	}
    	
    	for(int i =0; i<avg.length();i++) {
            
            // for 문으로 돌려도 된다.
    		if(cnt[i].equals("0")) {
    			count[0] +=1;
    		}
    		if(cnt[i].equals("1")) {
    			count[1] +=1;
    		}
    		if(cnt[i].equals("2")) {
    			count[2] +=1;
    		}
    		if(cnt[i].equals("3")) {
    			count[3] +=1;
    		}
    		if(cnt[i].equals("4")) {
    			count[4] +=1;
    		}
    		if(cnt[i].equals("5")) {
    			count[5] +=1;
    		}
    		if(cnt[i].equals("6")) {
    			count[6] +=1;
    		}
    		if(cnt[i].equals("7")) {
    			count[7] +=1;
    		}
    		if(cnt[i].equals("8")) {
    			count[8] +=1;
    		}
    		if(cnt[i].equals("9")) {
    			count[9] +=1;
    		}
    	}
    	for(int i =0; i <10; i++) {
    		System.out.println(count[i]);
    	}
    	
    }
}
```

```java
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int a = sc.nextInt();
		int b = sc.nextInt();
		int c = sc.nextInt();
		int[] count=new int[10];
		int result= (a*b*c);
		System.out.println(result);
		
		while(result >0)
		{
			count[result%10]++;
			result = result/10;
		}
		
		for(int i = 0 ; i < 10 ; i++)
		{
			System.out.println(count[i]);
		}


	}
}

```

### 나머지 (3052번)  정답 3개  (ArrayList, Hashmap 사용답안 포함)

```java

import java.util.Scanner;

public class Main{
    public static void main(String[] args) {
    	Scanner sc = new Scanner(System.in);	
    
    	int num[] = new int[42];
    	int cnt = 0;
    	
    	for(int i=0; i <10; i++) {
    		int a = sc.nextInt();
    		int b = a%42;
    		num[b]++;
    	}
    	
    	for (int i=0; i<42; i++) {
    		if(num[i] !=0 ) {
    			cnt ++;
    		}
    	}
    	System.out.println(cnt);
    }
}

```

```java
import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;

class Main{
    public static void main(String[] args){
        
        Scanner sc = new Scanner(System.in);
        ArrayList<Integer> arr = new ArrayList<Integer>();
        
        for (int i=0; i<10;i++){
            int n = sc.nextInt();
            int k = n % 42;
            if(!arr.contains(k))
                arr.add(k);
        }
        
        System.out.println(arr.size());
            
    }
}
```

```java
import java.io.*;
import java.util.*;
public class Main {

	public static void main(String[] args) throws IOException{
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		// StringTokenizer st;
		
		// 키값과 Value를 담아줄 Map 선언
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		
		for(int i=0; i<10; i++) {
			// key를 입력받은 값%42로 설정 why? HashMap은 중복 키값을 허용하지 않음
			int key = (Integer.parseInt(br.readLine()))%42;
			
			// 따라서 같은 key값이 들어오면 원래 있던 동일한 key의 value는 소멸
			map.put(key, 1);
            // map은 다른 key값이 들어온 만큼의 크기로 정해짐 
		}
        
		// map의 size()만큼 서로 다른 key값, 즉 다른 수가 들어온 것 
		bw.write(Integer.toString(map.size()));
		bw.flush();
	}
}
```

### 평균 (1546번)

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main{
    public static void main(String[] args) throws NumberFormatException, IOException {
    	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    	
    	int a = Integer.parseInt(br.readLine());
    	
    	double grade[] = new double[a];
    	double max = 0;
    	double avg = 0;
    	
    	StringTokenizer st = new StringTokenizer(br.readLine());
    	for(int i=0; i <a; i++) {
    		grade[i] = Integer.parseInt(st.nextToken());
    		if(max <grade[i]) {
    			max = grade[i];
    		}
    	}
    	for (int i=0; i<a; i++) {
    		grade[i] = grade[i]/max*100;
    		avg += grade[i];
    		
    	}
    	avg = avg/a;
    	System.out.println(avg);
    }
}

```

### OX 퀴즈 (8958번)  정답 2개 간단한 소스

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main{
    public static void main(String[] args) throws NumberFormatException, IOException {
    	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    	
    	int a = Integer.parseInt(br.readLine());
    	
    	for(int i=0; i <a; i++) {
    		int count = 0;
    		int x = -1;
        	StringTokenizer st = new StringTokenizer(br.readLine());
        	String q = st.nextToken();
    		String quiz[] = new String[q.length()];
    		for(int j=0; j<q.length(); j++ ) {
    			quiz[j]= q.substring(j,j+1);
    				if(quiz[j].equals("O")) {
    					count ++;
    					x++;
    					count = count+x;
    				}
    				if(quiz[j].equals("X")) {
    					x = -1;
    				}		
    		}
    		System.out.println(count);
    	}
    	    
    }
}
```

```java
import java.util.Scanner;
public class Main {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int n=sc.nextInt();
		
		for (int i = 0; i < n; i++) {
			int score=0;
			int result=0;
			String a=sc.next();
			
			for (int j = 0; j < a.length(); j++) {
				
				if (a.charAt(j)=='O') {
					score++;
					result=result+score;
				} else {
					score=0;
				}
			}
			System.out.println(result);
		}
	}
}
```

