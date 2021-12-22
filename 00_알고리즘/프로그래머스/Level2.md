### 오픈채팅방

```java
import java.util.*;

class Solution {
    public String[] solution(String[] record) {
        
      
        HashMap hm = new HashMap<String, String>();
        int count = 0;
        for (int i = 0; i < record.length; i++)
        {
            String temp2[] = record[i].split(" ");
            for (int j = 0; j < 1; j++)
            {
                if (temp2[0].equals("Enter"))
                {
                    hm.put(temp2[1],temp2[2]);
                    count++;
                }
                else if (temp2[0].equals("Change"))
                {
                   hm.replace(temp2[1], temp2[2]);
                }
                 else if (temp2[0].equals("Leave"))
                 {
                     count++;
                 }
            }
            
        }
        String[] answer = new String[count];
        count = 0;
        for (int i = 0; i < record.length; i++)
        {    
            String temp2[] = record[i].split(" ");
             for (int j = 0; j < 1; j++)
             {
                 if (temp2[0].equals("Enter"))
                 {
                    answer[count] = ((hm.get(temp2[1]))+("님이 들어왔습니다."));
                  //  System.out.println(answer[count]);
                    count++;
                 }
                else if (temp2[0].equals("Leave"))
                 {
                    answer[count] = ((hm.get(temp2[1]))+("님이 나갔습니다."));
                    count++;
                 }
              }
        }
        return answer;
    }
}
```



### 단체사진 찍기

```java
class Solution {
    static String friend = "ACFJMNRT";
    static String[] datas;
    static int count;
    static boolean[] check;
    public int solution(int n, String[] data) {
        int answer = 0;
        // 8글자중 하나
        // = < >
        // 0 ~ 6 정수 -> 간격을 의미 이 간격은 두 프렌즈 사이에 있는 다른 프렌즈의 수
        // 모든 경우의 수 
        // 조건은 1 ~ 100개
        // 조건 저장을 어떻게 해야하지..?? 조건이 100개........
        // 조건들을 하나로 통일 가장 큰걸로 최대 35?
        // charAt(0) / charAt(2) / charAt(3) / charAt(4)
        // String 을 만든다음에 인덱스를 빼자??? 빼면서 조건들하고 비교?? indexOf();
        // A C F J M N R T   총 경우의수 40320
        count = 0;
        check = new boolean[8];
        char[] arr = new char[8];
        datas = data;
        dfs(arr, 0);
        answer = count;
        return answer;
    } // solution end
    
    private void dfs(char[] arr, int depth)
    {
        if (depth == 8)
        {
            if (!check_arr(arr))
                return;
            count++;
            return;
        }
        for (int i = 0; i < 8; i++)
        {
            if (!check[i])
            {
                arr[depth] = friend.charAt(i);
                check[i] = true;
                dfs(arr, depth + 1);
                check[i] = false;    
            }  
        }
        
        return;
    } // dfs end
    private boolean check_arr(char[] arr)
    {
      //  System.out.println(arr[0]);
        for (int i = 0; i < datas.length; i++)
        {
            char first_f = datas[i].charAt(0);
            char second_f = datas[i].charAt(2);
            char sign = datas[i].charAt(3);
            char num = datas[i].charAt(4);
            int first_index = -1;
            int second_index = -1;
            for (int j = 0; j < 8; j++)
            {
                if (arr[j] == first_f)
                {
                    first_index = j;
                }
                if (arr[j] == second_f)
                {
                    second_index = j;
                }
            }
            
            if (sign == '=')
            {
                if (Math.abs(first_index - second_index) - 1 != (int)(num - '0') )
                {
                 //  System.out.println(Math.abs(first_index - second_index) - 1);
                  //  System.out.println(first_index + " = " + second_index);
                    return false;
                }
            }
           else if (sign == '>')
            {
                if (Math.abs(first_index - second_index) - 1 <= (int)(num - '0') )
                {
                    return false;
                }
            }
            else if (sign == '<')
            {
                if (Math.abs(first_index - second_index) - 1 >= (int)(num - '0') )
                {
                    return false;
                }
            }
        }
        return true;
    }
}
```

### 124나라의 숫자

```java
class Solution {
    public String solution(int n) {
        int bit = 0;
        int carry = 0;
        String a = "";

        while(n >= 1)
        {
            bit = (n % 3);   
            if(bit == 0)
            {
                bit = 4;
                carry++;  
            }

          a = Integer.toString(bit) + a;
          n = (n/3)-carry;
          carry = 0;
        } // while end

        return a;
    }
}
```

### 카카오 프렌즈 컬러링북

```java
import java.util.*;

class Solution {
    static boolean visited[][];
    static int x;
    static int y;
    
    public int[] solution(int m, int n, int[][] picture) {
        int numberOfArea = 0;
        int maxSizeOfOneArea = 0;
        visited = new boolean[m][n];        
        x = m;
        y = n;
        for (int i = 0; i < m; i++)
        {
            for (int j = 0; j < n; j++)
            {
                if (visited[i][j] == false && picture[i][j] != 0)
                {
                    numberOfArea++;
                    maxSizeOfOneArea = Math.max(dfs(i, j , picture, 1, picture[i][j]), maxSizeOfOneArea);
                   // System.out.println(maxSizeOfOneArea);
                }
            }
        }
        int[] answer = new int[2];
        answer[0] = numberOfArea;
        answer[1] = maxSizeOfOneArea;
        return answer;
    } // solution end
    
    private int dfs(int m, int n, int[][] picture, int maxsize, int color)
    {
        if (visited[m][n] == false && color != 0)
        {
           // System.out.println("color =.  "+color);
            visited[m][n] = true;
            if (m < x - 1)
            {
                if (color == picture[m+1][n] && visited[m+1][n] == false)
                {
                    maxsize = dfs(m+1, n, picture, maxsize+1, picture[m+1][n]);
                }
            }
            if (n < y - 1)
            {
                if (color == picture[m][n+1] && visited[m][n+1] == false)
                {
                    maxsize = dfs(m, n+1, picture, maxsize+1, picture[m][n+1]);
                }
            }
            if (m > 0)
            {
                if (color == picture[m-1][n] && visited[m-1][n] == false)
                {
                    maxsize = dfs(m-1, n, picture, maxsize+1, picture[m-1][n]);
                }
            }
            if (n > 0)
            {
                if (color == picture[m][n-1] && visited[m][n-1] == false)
                {
                    maxsize = dfs(m, n-1, picture, maxsize+1, picture[m][n-1]);
                }
            }
        }
        return maxsize;
    } // dfs end
    
}
```

### 멀쩡한 사각형

> 유클리드 호제법

```java
class Solution {
    public long solution(int w, int h) {
        long answer = 1;
        int gcd = 0;
        long W = w;
        long H = h;
        gcd = get_gcd(w, h);
        answer = (W * H) - (W + H - gcd);
        //answer = (w * h) - ((w / gcd) + (h / gcd) - 1) * gcd;
        return answer;
    }
    // 유클리드 호제법 
    private int get_gcd(int x, int y)
    {
        int a = Math.max(x, y);
        int b = Math.min(x, y);
        while (b != 0)
        {
            int temp = a;
            a = b;
            b = temp % b;
        }
        return a;
    }
}
```

### 짝지어 제거하기

> 효율성 실패 코드
>
> 단순히 전부 제거 가능하지 체크만 하는 쪽으로 고려해 봐야 겠다. -> Stack 을 사용하라 !!

```java
class Solution
{
    public int solution(String s)
    {
        int answer = -1;
        while (check_str(s))
        {
            int len = s.length();
            StringBuilder sb = new StringBuilder();
            int i = 0;
            while (i < len)
            {
                if (i == len - 1)
                {
                    sb.append(s.charAt(i));
                    break;
                }
                else if (s.charAt(i) != s.charAt(i + 1))
                {
                    sb.append(s.charAt(i));
                    i++;
                }
                else
                    i += 2;
            }
            s = sb.toString();
        }
        if (s.length() == 0)
            answer = 1;
        else
            answer = 0;
        return answer;
    }
    
    private boolean check_str(String s)
    {
        int len = s.length();
        for (int i = 0; i < len - 1; i++)
        {
            if (s.charAt(i) == s.charAt(i + 1))
                return (true);
        }
        return (false);
    }
}
```

> Stack 사용 통과 코드

```java
import java.util.*;
class Solution
{
    public int solution(String s)
    {
        int answer = -1;
        int len = s.length();
        Stack st = new Stack();
        st.push(s.charAt(0));
        for (int i = 1; i < len; i++)
        {
            if (!st.empty() && (char)st.peek() == s.charAt(i))
                st.pop();
            else
                st.push(s.charAt(i));
        }
        if (st.empty())
            answer = 1;
        else 
            answer = 0;
        return answer;
    }
}
```

> 비슷한 다른사람 코드

```java
import java.util.*;

class Solution
{
    public int solution(String s)
    {
        int answer = 0;
        Stack<Character> stack = new Stack<>();

        for(char c : s.toCharArray()){
            if(stack.size() == 0){
                stack.push(c);
            }
            else if(stack.peek() == c){
                stack.pop();
            }
            else{
                stack.push(c);
            }
        }
        return stack.size() > 0 ? 0 : 1;
    }
}
```

