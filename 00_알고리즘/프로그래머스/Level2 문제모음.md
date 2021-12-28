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

### [1차] 뉴스 클러스터링

```java
import java.util.*;
class Solution {
    public int solution(String str1, String str2) {
        int answer = 0;
        str1 = str1.toUpperCase();
        str2 = str2.toUpperCase();
        HashMap<String, Integer> arr1 = new HashMap<>();
        HashMap<String, Integer> arr2 = new HashMap<>();

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str1.length() - 1; i++)
        {
            sb = new StringBuilder();
            if ((str1.charAt(i) >= 'A' && str1.charAt(i) <= 'Z')
               && (str1.charAt(i + 1) >= 'A' && str1.charAt(i + 1) <= 'Z'))
            {
                sb.append(str1.charAt(i)).append(str1.charAt(i + 1));
                arr1.put(sb.toString(), arr1.getOrDefault(sb.toString(), 0) + 1);
            }
        }
        for (int i = 0; i < str2.length() - 1; i++)
        {
            sb = new StringBuilder();
            if ((str2.charAt(i) >= 'A' && str2.charAt(i) <= 'Z')
               && (str2.charAt(i + 1) >= 'A' && str2.charAt(i + 1) <= 'Z'))
            {
                sb.append(str2.charAt(i)).append(str2.charAt(i + 1));
                arr2.put(sb.toString(), arr2.getOrDefault(sb.toString(), 0) + 1);
            }
        }

        int total_size = 0;
        int common_size = 0;
        for (String key : arr1.keySet())
        {
            if (arr2.containsKey(key))
            {
                total_size += Math.max(arr1.get(key), arr2.get(key));
                common_size += Math.min(arr1.get(key), arr2.get(key));
            }
            else
                total_size += arr1.get(key);
        }
        for (String key : arr2.keySet())
        {
            if (!arr1.containsKey(key))
                total_size += arr2.get(key);
        }
        double result = 0;
        if (total_size > 0)
        {
            result = (double)common_size / (double)total_size;
            answer = (int)(result * 65536);
        }
        else
            return 65536;
        return answer;
    } // solution end
}
```

```java
// 방법1
Iterator<String> keys = map.keySet().iterator();
while( keys.hasNext() ){
    String key = keys.next();
    System.out.println( String.format("키 : %s, 값 : %s", key, map.get(key)) );
}

// 방법2
for( Map.Entry<String, String> elem : map.entrySet() ){
    System.out.println( String.format("키 : %s, 값 : %s", elem.getKey(), elem.getValue()) );
}

// 방법3
for( String key : map.keySet() ){
    System.out.println( String.format("키 : %s, 값 : %s", key, map.get(key)) );
}


```

> 다른 사람 코드

```java
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class Solution {
    private static final Integer MULTIPLIER = Character.MAX_VALUE + 1;

    public int solution(String str1, String str2) {
        String word1 = str1.toLowerCase();
        String word2 = str2.toLowerCase();

        Map<String, Long> words1 = group(word1);
        Map<String, Long> words2 = group(word2);

        Integer intersection = getIntersection(words1, words2);
        Integer union = getUnion(words1, words2);

        if (intersection.equals(union) && union.equals(0)) {
            return MULTIPLIER;
        }

        return (int) (intersection.doubleValue() / union.doubleValue() * MULTIPLIER);
    }

    private Map<String, Long> group(String word) {
        return IntStream.range(0, word.length() - 1)
                .mapToObj(index -> word.substring(index, index + 2))
                .filter(text -> text.chars().allMatch(character -> Character.isAlphabetic((char) character)))
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    }

    private Integer getIntersection(Map<String, Long> words1, Map<String, Long> words2) {
        return words1.entrySet().stream()
                .filter(entry -> words2.containsKey(entry.getKey()))
                .map(entry -> Math.min(entry.getValue(), words2.get(entry.getKey())))
                .mapToInt(Long::intValue)
                .sum();
    }

    private Integer getUnion(Map<String, Long> words1, Map<String, Long> words2) {
        Map<String, Long> copiedWords = new HashMap<>(words2);
        words1.forEach((key, value) -> copiedWords.put(key, Math.max(value, words2.getOrDefault(key, 0L))));

        return copiedWords.values().stream()
                .mapToInt(Long::intValue)
                .sum();

    }
}
```

### 거리두기 확인하기

```java
class Solution {
    public int[] solution(String[][] places) {
        int[] answer = new int[5];
        for (int i = 0; i < places.length; i++)
        {
            char arr[][] = new char[5][5];
            for (int j = 0; j < 5; j++)
            {
                for (int k = 0; k < 5; k++)
                    arr[j][k] = places[i][j].charAt(k);
            }
            if (check_people(arr))
                answer[i] = 1;
            else
                answer[i] = 0;
        }
        return answer;
    } // solution end
    
    private boolean check_people(char[][] arr)
    {
        for (int i = 0; i < 5; i++)
        {
            for (int j = 0; j < 5; j++)
            {
                if (arr[i][j] == 'P')
                {
                    if(!check_arr(arr, i, j))
                        return false;
                }
            }
        }
        return true;
    } // check_people end
    
    private boolean check_arr(char[][] arr, int y, int x)
    {
        for (int i = y; i < 5; i++)
        {
            for (int j = 0; j < 5; j++)
            {
                if ((i != y || j != x) && arr[i][j] == 'P')
                {
                    if (Math.abs((y - i)) + Math.abs((x - j)) == 1)
                        return false;
                    else if (Math.abs((y - i)) + Math.abs((x - j)) == 2)
                    {
                        if (!check_partiton(arr, y, x, i, j))
                            return false;       
                    }
                }
            }
        }
        return true;
    } // check_arr end
    private boolean check_partiton(char[][] arr, int y, int x, int y2, int x2)
    {
        if (y == y2)
        {
            int temp = Math.min(x, x2);
            if (arr[y][temp + 1] == 'O')
                return false;
        }
        else if (x == x2)
        {
            if (arr[y + 1][x] == 'O')
                return false;
        }
        else
        {
            if (arr[y][x2] == 'O' || arr[y2][x] == 'O')
                return false;
        }
        return true;
    } // check_partiton end
}
```

### 수식 최대화

> 얕은 복사, 깊은 복사 제대로 숙지하기..
>
> ex) clone() 사용
>
> https://woovictory.github.io/2020/04/22/Java-Array-Copy/
>
> https://highcode.tistory.com/6 (정규표현식 설명)

```java
class Solution {
    long result;
    String num_arr[];
    String index_arr[];
    public long solution(String expression) {
        long answer = 0;
        result = 0;
        // 숫자만 빼오기
        String g_num_arr[] = expression.split("[^0-9$]");
        // 숫자 제외 빼오기
        String g_index_arr[] = expression.split("[\\d]+");
   
        String g_index[] = {"+","-","*"};
        num_arr = g_num_arr.clone();
        index_arr = g_index_arr.clone();
        make_arr(g_index);
   
        String g_index1[] = {"+","*","-"};
        num_arr = g_num_arr.clone();
        index_arr = g_index_arr.clone();
        make_arr(g_index1);
        
        String g_index2[] = {"*","+","-"};
        num_arr = g_num_arr.clone();
        index_arr = g_index_arr.clone();
        make_arr(g_index2);
        
        String g_index3[] = {"*","-","+"};
        num_arr = g_num_arr.clone();
        index_arr = g_index_arr.clone();
        make_arr(g_index3);

        String g_index4[] = {"-","+","*"};
        num_arr = g_num_arr.clone();
        index_arr = g_index_arr.clone();
        make_arr(g_index4);

        String g_index5[] = {"-","*","+"};
        num_arr = g_num_arr.clone();
        index_arr = g_index_arr.clone();
        make_arr(g_index5);
        
        answer = result;
        return answer;
    } // solution end
    
    private void make_arr(String[] g_index)
    {
        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < index_arr.length; j++)
            {
                if (index_arr[j].equals(g_index[i]))
                {
                    let_cal(j);
                    j = 0;
                }
            }
        }
        // System.out.println("rrre =  " + Math.abs(Long.parseLong(num_arr[0])));
        result = Math.max(result, Math.abs(Long.parseLong(num_arr[0])));
        return;
    } // make_arr end
    
    private void let_cal(int i)
    {
        String temp_num_arr[] = new String[num_arr.length - 1];
        String temp_index_arr[] = new String[index_arr.length - 1];
        long temp = 0;
        switch(index_arr[i])
        {
            case "+" :
                temp = Long.parseLong(num_arr[i - 1]) + Long.parseLong(num_arr[i]);
                break;
            case "-" :
                temp = Long.parseLong(num_arr[i - 1]) - Long.parseLong(num_arr[i]);
                break;
            case "*" :
                temp = Long.parseLong(num_arr[i - 1]) * Long.parseLong(num_arr[i]);
                break;
        }
        num_arr[i - 1] = temp + "";
        // System.out.println("temp =  " + temp);
        for (int j = 0, k = 0; j < num_arr.length - 1; j++, k++)
        {
            if (k == i)
                k++;
            temp_num_arr[j] = num_arr[k];
        }
        num_arr = temp_num_arr;
        
        for (int j = 0, k = 0; j < index_arr.length - 1; j++, k++)
        {
            if (k == i)
                k++;
            temp_index_arr[j] = index_arr[k];
        }
        index_arr = temp_index_arr;
        return;
    }
}
```

> 다른사람 코드

```java
import java.util.*;

class Solution {

    public long cal(long a, long b, char op){
        if(op == '+')
            return a + b;
        else if(op == '-')
            return a - b;
        else
            return a * b;
    }

    public void calc(ArrayList<Long> numbers, ArrayList<Character> ops, char op){
        for(int i = 0; i < ops.size(); i++){
            if(ops.get(i) == op){
                long num1 = numbers.get(i);
                long num2 = numbers.get(i + 1);
                long result = cal(num1, num2, op);
                numbers.remove(i);
                numbers.remove(i);
                numbers.add(i, result);
                ops.remove(i);
                i--;
            }
        }
    }

    public long operation(ArrayList<Long> nums, ArrayList<Character> opers, char op1, char op2, char op3){
        ArrayList<Long> numbers = new ArrayList<>();
        ArrayList<Character> ops = new ArrayList<>();

        for(int i = 0; i < nums.size(); i++){
            numbers.add(nums.get(i));
        }

        for(int i = 0; i < opers.size(); i++){
            ops.add(opers.get(i));
        }

        calc(numbers, ops, op1);
        calc(numbers, ops, op2);
        calc(numbers, ops, op3);

        return numbers.get(0);
    }

    public long solution(String expression) {
        long answer = 0;
        ArrayList<Long> numbers = new ArrayList<>();    
        ArrayList<Character> operators = new ArrayList<>();

        String num = "";

        for(int i = 0; i < expression.length(); i++){
            if(expression.charAt(i) == '-' || expression.charAt(i) == '+' || expression.charAt(i) == '*'){
                operators.add(expression.charAt(i));
                numbers.add(Long.parseLong(num));
                num = "";
            }

            else{
                num += expression.charAt(i);
            }
        }
        numbers.add(Long.parseLong(num));

        long[] result = new long[6];

        result[0] = operation(numbers, operators, '+', '-', '*');
        result[1] = operation(numbers, operators, '+', '*', '-');
        result[2] = operation(numbers, operators, '-', '+', '*');
        result[3] = operation(numbers, operators, '-', '*', '+');
        result[4] = operation(numbers, operators, '*', '-', '+');
        result[5] = operation(numbers, operators, '*', '+', '-');

        for(int i = 0; i < 6; i++){
            if(answer < Math.abs(result[i]))
                answer = Math.abs(result[i]);
        }

        return answer;
    }
}
```

