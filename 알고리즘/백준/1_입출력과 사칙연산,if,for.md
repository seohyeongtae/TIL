## 입출력과 사칙연산 / if / for

### 출력 

```java
public class Main {

	public static void main(String[] args) {
		System.out.println("\\    /\\");
		System.out.println(" )  ( ')");
		System.out.println("(  /  )");
		System.out.println(" \\(__)|");

	}

}

```

### A+B (1000번)

```java
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int a = sc.nextInt();
		int b = sc.nextInt();
		
		System.out.println(a+b);
		sc.close();
	}

}
```

### 두 수 비교하기 (1330번)

```java

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int a = sc.nextInt();
		int b = sc.nextInt();
		
		if(-10000< a && a <10000 &&-10000< b && b <10000) {
			if(a>b) {
				System.out.println(">");
			}
			else if(a<b) {
				System.out.println("<");
			}
			else if(a==b) {
				System.out.println("==");
			}
		}
		
		sc.close();
	}

}
```

### 윤년 (2753번)

```java
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int a = sc.nextInt();
		
		// 나머지값을 계산
		if((a%4 == 0 && a%100 != 0) || a%400 == 0) {
			System.out.println("1");
		} else {
			System.out.println("0");
			}
		
		sc.close();
	}

}
```

### 사분면 맞추기(14681번)

```java
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int a = sc.nextInt();
		int b = sc.nextInt();
		
		if(a>=0 && b>=0) {
			System.out.println("1");
		}else if(a<=0 && b>=0) {
			System.out.println("2");
		}else if(a<=0 && b<=0) {
			System.out.println("3");
		}else if(a>=0 && b<=0) {
			System.out.println("4");
		}else if(a==0 && b==0) {
			System.out.println("0,0");
		}
		
		sc.close();
	}

}
```

### 알람시계 (2884번)

```java
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int h = sc.nextInt();
		int m = sc.nextInt();
		
		if((0<=h && h<=23) && (0<=m && m<=59)) {
			if(m<45) {
				if(h==0) {
					System.out.println("23");
					System.out.println(m+15);
					return;
				}
				System.out.println((h-1));
				System.out.println(m+15);
			}else {
				System.out.println(h);
				System.out.println(m-45);
			}
			
		}
		
		sc.close();
	}

}
```

### 구구단 (2739번)

```java
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int a = sc.nextInt();
		
		for(int i = 1; i <=9; i++) {
			System.out.println(a+" * "+i+" = "+a*i);
		}
		sc.close();
	}

}

```

### A+B -3 (10950번)

```java
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int a = sc.nextInt();
		
		for(int i = 1; i <=a; i++) {
			
			int b = sc.nextInt();
			int c = sc.nextInt();
			System.out.println(b+c);
					
		}
		sc.close();
	}

}
```

### 합 (8393번)

```java
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int a = sc.nextInt();
		int b = 0;
		
		for(int i = 1; i <=a; i++) {
			 b += i;	
		}
		System.out.println(b);
		sc.close();
	}

}
```

### 빠른 A+B (BufferedReader/ BufferedWriter) 사용 (15552번)

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
		
		int a = Integer.parseInt(br.readLine());
		
		for (int i = 1; i <=a; i++) {
			st = new StringTokenizer(br.readLine());
			int b = Integer.parseInt(st.nextToken());
			int c = Integer.parseInt(st.nextToken());
			int sum = b+c;
			bw.write(sum+"\n");
			
		}
		bw.flush();
		
	}

}
```

### StringTokenizer 설명 
>
> StringTokenizer 클래스는 java.util 패키지 안에 존재한다.
>
> 
>
> 하나의 문자열을 여러개로 분리하기 위해 사용한다. .split()과 비슷한 기능을 한다고 생각하면 된다.
>
> 주로 사용하는 메소드들에 대해 정리 해보자

```java
import java.util.StringTokenizer;

public class StringTokenizerEx {

	public static void main(String[] args) {
		
		String query = "test ee qq aaddfdf";
		StringTokenizer st = new StringTokenizer(query);
		// 순서대로 분리된 토큰의 개수, 다음토큰, 다음에 토큰여부를 boolean으로 반환
		System.out.println(st.countTokens() + " " + st.nextToken() + " " + st.hasMoreTokens());
		
		String query2 = "name=kim&old=20";
		// = 또는 & 으로 구분
		StringTokenizer st2 = new StringTokenizer(query2, "=&");
		System.out.println(st2.countTokens() + " " + st2.nextToken() + " " + st2.hasMoreTokens());
		
		String query3 = "name=lee&old=22";
		// 3번째 매개변수 boolean으로 가능 true하면 구분자가 포함된문자도 토큰에 들어감.
		StringTokenizer st3 = new StringTokenizer(query2, "=&", true);
		System.out.println(st3.countTokens() + " " + st3.nextToken() + " " + st3.hasMoreTokens());
	}

}
```

### N 찍기 (2741번)

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
		
		int a = Integer.parseInt(br.readLine());
		int b =0;
		for (int i = 1; i <=a; i++) {
			b +=i;
			bw.write(i+"\n");
			
		}
		bw.flush();
		
	}

}
```

### A+B -7 (11021)번

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
		
		int a = Integer.parseInt(br.readLine());
		
		for (int i = 1; i <=a; i++) {
			st = new StringTokenizer(br.readLine());
			int b = Integer.parseInt(st.nextToken());
			int c = Integer.parseInt(st.nextToken());
			int sum = b+c;
			bw.write("Case #"+i+": "+sum+"\n");
			
		}
		bw.flush();
		
	}

}

```

### 별 찍기 -1 (2438번)

```java
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Main {

	public static void main(String[] args) throws NumberFormatException, IOException {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		
		int a = Integer.parseInt(br.readLine());
		int b = 0;
		for (int i = 1; i <=a; i++) {
			b++;
			for(int j =0; j<b;j++ ) {
				bw.write("*");	
			}
			bw.write("\n");
		}
		bw.flush();
		
	}

}
```

### 별찍기 -2 (2439번)

```java
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Main {

	public static void main(String[] args) throws NumberFormatException, IOException {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		
		int a = Integer.parseInt(br.readLine());
		int b = 0;
		for (int i = 1; i <=a; i++) {
			b++;
			for(int k =0; k <a-b;k++) {
				bw.write(" ");
			}
			
			for(int j =0; j<b;j++ ) {
				bw.write("*");	
			}
			bw.write("\n");
		}
		bw.flush();
		
	}

}
```

###  X보다 작은 수 (10871번) - Scanner/BurrferedReader   2개 정답

```java
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int N,X,a;
		N=sc.nextInt();
		X=sc.nextInt();
		for(int i=0;i<N;i++) {
			a=sc.nextInt();
			if(a<X) {
				System.out.print(a+" ");
			}
		}
		
	}

}
```

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
		
		st = new StringTokenizer(br.readLine());
		int b = Integer.parseInt(st.nextToken());
		int c = Integer.parseInt(st.nextToken());
		
		st = new StringTokenizer(br.readLine());
		
		for (int i = 0; i <b; i++) {
			int a = Integer.parseInt(st.nextToken());
				if(a<c) {
					bw.write(a+" ");
			}
			
		}
		bw.flush();
	}

}
```

