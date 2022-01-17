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

### 튜플

```java
import java.util.*;
import java.util.Map.Entry;
class Solution {
    public int[] solution(String s) {
        String arr[] = s.split("[^0-9$]");
        int cnt = 0;
        HashMap<String, Integer> hs = new HashMap();
        for (int i = 0; i < arr.length; i++)
        {
            if (!arr[i].equals(""))
            {
                if (!hs.containsKey(arr[i]))
                    cnt++;
                hs.put(arr[i], hs.getOrDefault(arr[i], 1) + 1);
            }
        }
        List<Entry<String, Integer>> entrylist = new ArrayList<Entry<String, Integer>>(hs.entrySet());
        Collections.sort(entrylist, new Comparator<Entry<String, Integer>>()
        {
            public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2)
            {
              return o2.getValue().compareTo(o1.getValue());  
            }
        });
        int[] answer = new int[cnt];
        int i = 0;
        for (Entry<String, Integer> entry : entrylist)
        {
            answer[i] = Integer.parseInt(entry.getKey());
            i++;
        }
        return answer;
    }
}
```

> 다른 사람 코드

```java
import java.util.*;
class Solution {
    public int[] solution(String s) {
        Set<String> set = new HashSet<>();
        String[] arr = s.replaceAll("[{]", " ").replaceAll("[}]", " ").trim().split(" , ");
        Arrays.sort(arr, (a, b)->{return a.length() - b.length();});
        int[] answer = new int[arr.length];
        int idx = 0;
        for(String s1 : arr) {
            for(String s2 : s1.split(",")) {
                if(set.add(s2)) answer[idx++] = Integer.parseInt(s2);
            }
        }
        return answer;
    }
}
```

> 스킬 체크 시 다시 푼 코드

```java
import java.util.*;
class Solution {
    public int[] solution(String s) {
        HashMap<String, Integer> hm = new HashMap<>();
        for (int i = 0; i < s.length(); i++)
        {
            String temp = "";
            while (s.charAt(i) >= '0' && s.charAt(i) <= '9')
            {
                temp += s.substring(i, i + 1);
                i++;
            }
            if (temp.length() >= 1)
                hm.put(temp, hm.getOrDefault(temp, 0) + 1);
        }
        int final_len = hm.size();
        int[] answer = new int[final_len];
        int[] temp_result = new int[final_len + 1];
        for (String d : hm.keySet())
            temp_result[hm.get(d)] = Integer.parseInt(d);
        for (int i = final_len, j = 0; i > 0; i--, j++)
            answer[j] = temp_result[i];
        return answer;
    }
}
```

### 게임 맵 최단거리

> 효율성 실패 (dfs 사용 - 깊이 우선탐색)

```java
class Solution {
    int n;
    int m;
    boolean visit[][];
    int result = 210000000;
    public int solution(int[][] maps) {
        int answer = -1;
        n = maps.length;
        m = maps[0].length;
        visit = new boolean[n][m];
        visit[0][0] = true;
        Math.max(answer,find_map(maps,0,0,1));
        if (result == 210000000)
            answer = -1;
        else
            answer = result;
        return answer;
    } // solution end
    
    private int find_map(int[][] maps, int x, int y, int cnt)
    {
        if (x == n - 1 && y == m - 1)
        {
            result = Math.min(result, cnt);
            return cnt;
        }
            
        if (x + 1 < n && maps[x + 1][y] == 1 && visit[x + 1][y] == false)
        {
            visit[x + 1][y] = true;
            find_map(maps, x + 1, y, cnt + 1);
            visit[x + 1][y] = false;
        }
        if (y + 1 < m && maps[x][y + 1] == 1 && visit[x][y + 1] == false)
        {
            visit[x][y + 1] = true;
            find_map(maps, x, y + 1, cnt + 1);
            visit[x][y + 1] = false;
        }
        if (x - 1 >= 0 && maps[x - 1][y] == 1 && visit[x - 1][y] == false)
        {
            visit[x - 1][y] = true;
            find_map(maps, x - 1, y, cnt + 1); 
            visit[x - 1][y] = false;
        }
        if (y - 1 >= 0 && maps[x][y - 1] == 1 && visit[x][y - 1] == false)
        {
            visit[x][y - 1] = true;
            find_map(maps, x, y - 1, cnt + 1);
            visit[x][y - 1] = false;
        }
        return -1;
    }
}
```

> queue 를 이용한 bfs (넓이 우선탐색) 통과코드 / 동적계획법

```java
import java.util.LinkedList;
import java.util.Queue;
class Solution {
    public int solution(int[][] maps) {
        int m = maps.length, n = maps[0].length;

        int[][] visited = new int[m][n];
        visited[0][0] = 1;

        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[] {0, 0});

        while (!queue.isEmpty()) {
            int[] q = queue.poll();

            if(q[0] == m-1 && q[1] == n-1) {
                return visited[m-1][n-1];
            }

            if(q[0]+1 < m && maps[q[0]+1][q[1]] == 1 && visited[q[0]+1][q[1]] == 0) {
                queue.add(new int[] {q[0]+1, q[1]});
                visited[q[0]+1][q[1]] += visited[q[0]][q[1]] + 1;
            }
            if(q[0] > 0 && maps[q[0]-1][q[1]] == 1 && visited[q[0]-1][q[1]] == 0) {
                queue.add(new int[] {q[0]-1, q[1]});
                visited[q[0]-1][q[1]] += visited[q[0]][q[1]] + 1;
            }
            if(q[1]+1 < n && maps[q[0]][q[1]+1] == 1 && visited[q[0]][q[1]+1] == 0) {
                queue.add(new int[] {q[0], q[1]+1});
                visited[q[0]][q[1]+1] += visited[q[0]][q[1]] + 1;
            }
            if(q[1] > 0 && maps[q[0]][q[1]-1] == 1 && visited[q[0]][q[1]-1] == 0) {
                queue.add(new int[] {q[0], q[1]-1});
                visited[q[0]][q[1]-1] += visited[q[0]][q[1]] + 1;
            }
        }

        return -1;
    }
}
```

### 예상 대진표

```java
class Solution
{
    public int solution(int n, int a, int b)
    {
        int answer = 0;
        int cnt = 1;
        while (a / 2 + a % 2 != b / 2 + b % 2)
        {
            cnt++;
            a = a / 2 + a % 2;
            b = b / 2 + b % 2;
        }
        answer = cnt;
        return answer;
    }
}
```

> 다른 사람 코드

```java
class Solution
{
    public int solution(int n, int a, int b)
    {
        int round = 0;
        while(a != b)
        {
            a = a/2 + a%2;
            b = b/2 + b%2;
            round++;
        }
        return round;
    }
}

// 두번째


class Solution
{
    public int solution(int n, int a, int b)
    {
        return Integer.toBinaryString((a-1)^(b-1)).length();
    }
}
```

### 괄호 회전하기

> HashMap 말고 Stack 을 사용해야 한다.
>
> ([)]  이러한 경우의수를 제외하기 위해 

```java
import java.util.*;
class Solution {
    public int solution(String s) {
        int answer = 0;
        int len = s.length();
        String change_s = s;
        for (int i = 0; i < len; i++)
        {
            answer += check_s(change_s);
            char temp = change_s.charAt(0);
            change_s = change_s.substring(1, len);
            change_s += temp;   
        }
        return answer;
    } // solution end
    
    private int check_s(String s)
    {
        Stack<Character> st = new Stack<>();
        for (int i = 0; i < s.length(); i++)
        {
            if (s.charAt(i) == '[' || s.charAt(i) == '{' || s.charAt(i) == '(')
                st.push(s.charAt(i));
            else if (s.charAt(i) == ']')
            {
                if (st.empty() || st.peek() != '[')
                    return 0;
                else
                   st.pop();
            }
            else if (s.charAt(i) == '}')
            {
                if (st.empty() || st.peek() != '{')
                    return 0;
                else
                   st.pop();
            }
            else if (s.charAt(i) == ')')
            {
                if (st.empty() || st.peek() != '(')
                    return 0;
                else
                   st.pop();
            }
        }
        if (!st.empty())
            return 0;
        return 1;
    }
}
```

### 배달

> 플로이드 와샬 알고리즘 사용

```java
class Solution {
    public int solution(int N, int[][] road, int K) {
        int answer = 1;
        int arr[][] = new int[N + 1][N + 1];
        int len = road.length;
        for (int i = 0; i < N + 1; i++)
        {
            for (int j = 0; j < N + 1; j++)
                arr[i][j] = 5000001;
        }
        for (int i = 0; i < len; i++)
        {
            if (arr[road[i][0]][road[i][1]] > road[i][2])
                arr[road[i][0]][road[i][1]] = road[i][2];
            if (arr[road[i][1]][road[i][0]] > arr[road[i][0]][road[i][1]])
                arr[road[i][1]][road[i][0]] = arr[road[i][0]][road[i][1]];
        }
        
        for (int i = 0; i <  N + 1; i++) // 거쳐가는 경로
        {
            for (int j = 0; j <  N + 1; j++) // 출발 경로
            {
                for (int k = 0; k <  N + 1; k++) // 도착 경로
                {
                    if (arr[j][k] > arr[j][i] + arr[i][k])
                    {
                        arr[j][k] = arr[j][i] + arr[i][k];
                    }
                }
            }
        }
        for (int i = 2; i < N + 1; i++)
        {
            if (arr[1][i] <= K)
                answer++;
        }
        return answer;
    }
}
```

> 다음 부터는 Math.min 을 사용하자 (다른사람 코드)

```java
class Solution {
    public int solution(int N, int[][] road, int K) {
        int answer = 0;
        int[][] map = new int[N+1][N+1];

        for(int i = 1; i <= N; i++) {
            for(int j = 1; j <= N; j++) {
                map[i][j] = 200000001;
            }
        }

        for(int i = 1; i <= N; i++) {
            map[i][i] = 0;
        }

        for(int i = 0; i < road.length; i++) {
            map[road[i][0]][road[i][1]] = Math.min(map[road[i][0]][road[i][1]], road[i][2]);
            map[road[i][1]][road[i][0]] = Math.min(map[road[i][1]][road[i][0]], road[i][2]);
        }

        for(int k = 1; k <= N; k++) {
            for(int i = 1; i <= N; i++) {
                for(int j = 1; j <= N; j++) {
                    if(map[i][j] > map[i][k] + map[k][j]) {
                        map[i][j] = map[i][k] + map[k][j];
                    }
                }
            }
        }

        for(int i = 1; i <= N; i++) {
            if(map[1][i] <= K)
                answer++;
        }

        return answer;
    }
}
```

### 피로도

```java
class Solution {
    boolean visit[];
    int result;
    int g_k;
    public int solution(int k, int[][] dungeons) {
        int answer = -1;
        result = 0;
        g_k = k;
        visit = new boolean[dungeons.length];
        find(dungeons, dungeons.length, 0);
        answer = result;
        return answer;
    } // solution end
    
    private void find(int[][] dun, int len, int r)
    {
        for (int i = 0; i < len; i++)
        {
            if (visit[i] == false)
            {
                visit[i] = true;
                if (g_k >= dun[i][0])
                {
                    g_k -= dun[i][1];
                    find(dun, len, r + 1);
                    g_k += dun[i][1];
                }
                visit[i] = false;
            }
        }
        result = Math.max(result, r);
        return;
    } // find end
}
```

> 다른 사람 코드

```java
class Solution {
    public static boolean check[];
    public static int ans = 0;

    public int solution(int k, int[][] dungeons) {
        check = new boolean[dungeons.length];

        dfs(k, dungeons, 0);

        return ans;
    }
    public static void dfs(int tired, int[][] dungeons, int cnt){
        for(int i=0; i<dungeons.length; i++){
            if(!check[i] && dungeons[i][0]<=tired){
                check[i] = true;
                dfs(tired-dungeons[i][1], dungeons, cnt+1);
                check[i] = false;
            }
        }
        ans = Math.max(ans, cnt);
    }
}
```

### 프렌즈 4블록

```java
class Solution {
    static int cnt = 0;
    static boolean visit[][];
    public int solution(int m, int n, String[] board) {
        int answer = 0;
        char[][] c_board = new char[m][n];
        for (int i = 0; i < m; i++)
        {
            for (int j = 0; j < n; j++)
                c_board[i][j] = board[i].charAt(j);
        }
        visit = new boolean[m][n];
        while (check_board(m, n, c_board))
        {
            c_board = delete_board(m, n, c_board);
            visit = new boolean[m][n];
        }
        answer = cnt;
        return answer;
    } // solution end
    private char[][] delete_board(int m, int n, char[][] board)
    {
        for (int i = 0; i < m; i++)
        {
            for (int j = 0; j < n; j++)
            {
                if (visit[i][j] == true)
                {
                    board[i][j] = '1';
                    cnt++;
                }
            }
        }
        for (int i = 0; i < n; i++)
        {
            for (int j = m - 1; j >= 0; j--)
            {
                if (board[j][i] == '1')
                {
                    int temp = j;
                    while (j >= 0 && board[j][i] == '1')
                        j--;
                    if (j >= 0)
                    {
                        board[temp][i] = board[j][i];
                        board[j][i] = '1';
                        j = temp;
                    }
                }
            }
        }
        return board;
    } // delete board end
    
    private boolean check_board(int m, int n, char[][] board)
    {
        boolean re = false;
        for (int i = 0; i < m - 1; i++)
        {
            for (int j = 0; j < n - 1; j++)
            {
                char a = board[i][j];
                if (a != '1' && a == board[i][j + 1] && a == board[i + 1][j + 1] &&
                    a == board[i + 1][j])
                {
                    visit[i][j] = true;
                    visit[i][j + 1] = true;
                    visit[i + 1][j] = true;
                    visit[i + 1][j + 1] = true;
                    re = true;
                }
            }
        }
        return re;
    }  // check_board end
}
```

### 2개 이하로 다른 비트

> 부호 확인 잘하자 -> cnt 를 long으로 선언안하고 int 로 선언해서 시간 뺏겼다.

```java
class Solution {
    public long[] solution(long[] numbers) {
        long[] answer = new long[numbers.length];
        for (int i = 0; i < numbers.length; i++)
        {
            long cnt = 1;
            long temp_num = numbers[i];
            while (temp_num > 0)
            {
                long temp = temp_num % 2;
                temp_num = temp_num / 2;
                if (temp == 0)
                {
                    answer[i] = numbers[i] + cnt;
                    if (cnt != 1)
                        answer[i] -= cnt / 2;
                    break;
                }
                cnt *= 2;
            }
            if (answer[i] == 0)
                answer[i] = numbers[i] + cnt - (cnt / 2) ;
        }
        return answer;
    }
}
```

### 삼각 달팽이

> https://blog.naver.com/gkfla1017/222418312339
>
> 참고 풀이 링크 (정답 링크)

```java
class Solution {
    public int[] solution(int n) {
        int[] answer = new int[n*(n+1)/2];
        int[][] mat= new int[n][n];
        
        int x=-1, y=0, idx=1;
        for(int i=0; i<n; i++){
            for(int j=i; j<n; j++){
                if(i%3==0){ //아래로
                    x++;
                }else if(i%3==1){//오른쪽으로
                    y++;
                }else{//대각선으로
                    x--;
                    y--;
                }
                mat[x][y]= idx++;
            }
        }
        
        idx=0;
        for(int i=0; i<n; i++){
            for(int j=0; j<n; j++){
                if(mat[i][j]==0) break;
                answer[idx++]= mat[i][j];
            }
        }
        return answer;
    }
}

```

> 비슷한 다른 사람 코드

```java
class Solution {
    public int[] solution(int n) {
        int[][] li = new int[n][n];
        int x=0,y=0;
        int step = n;
        int value=1;
        while(step >0){
            /*아래로 이동 */
            for(int i=step;i>0;i--){
                li[x++][y] = value++;
            }
            x--; // 행 복귀 
            y++; // 로테이션
            step--; // 스텝 업데이트

            /* 오른쪽 이동 */
            for(int i=step;i>0;i--){
                li[x][y++] = value++;
            }
            y--; // 열 복귀
            x--; // 로테이션
            y--; // 로테이션
            step--;

            /* 북서쪽 이동 */
            for(int i=step;i>0;i--){
                li[x--][y--] = value++;
            }
            x++; // 행 복귀
            y++; // 열 복귀
            x++; // 로테이션
            step--;
        }

        int[] answer = new int[n*(n+1)/2];
        int size=0;
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                if(li[i][j]==0) break;
                answer[size++] = li[i][j];
            }
        }
        return answer;
    }
}
```

### 교점에 별 만들기

> 형변환 주의하기!! // HashSet 활용 방법 
>
> for 문 활용하기 (Long 값을 이용해서 따로 len을 계산하거나 int 로 형변환을 안해도 된다.)

```java
import java.util.*;
class Solution {
    public String[] solution(int[][] line) {
        long max_x = Long.MIN_VALUE;
        long min_x = Long.MAX_VALUE;
        long max_y = Long.MIN_VALUE;
        long min_y = Long.MAX_VALUE;
        HashSet<String> hs = new HashSet<>();
        for (int i = 0; i < line.length - 1; i++)
        {
            for (int j = i + 1; j < line.length; j++)
            {
                long x1 = ((long)line[i][1] * line[j][2] - line[i][2] * line[j][1]); // BF - ED
                long x2 = ((long)line[i][0] * line[j][1] - line[i][1] * line[j][0]); // AD - BC
                long y1 = ((long)line[i][2] * line[j][0] - line[i][0] * line[j][2]); // EC - AF
                if (x2 != 0 && x1 % x2 == 0 && y1 % x2 == 0)
                {
                    long x = x1 / x2;
                    max_x = Math.max(x, max_x);
                    min_x = Math.min(x, min_x);
                    long y = y1 / x2;
                    max_y = Math.max(y, max_y);
                    min_y = Math.min(y, min_y);
                    hs.add(x + "," + y);
                }
            }
        } // for end
        int len = 1;
        if (max_y != min_y)
            len = (int)(max_y - min_y) + 1;
        String[] answer = new String[len];
        int k = len - 1;
        for (long i = min_y; i <= max_y; i++)
        {
            answer[k] = "";
            for (long j = min_x; j <= max_x; j++)
            {
                if (hs.contains(j+","+i))
                    answer[k] += "*";
                else
                    answer[k] += ".";
            }
            k--;
        }
        return answer;
    }
}
```

### 영어 끝말잇기

```java
import java.util.*;
class Solution {
    public int[] solution(int n, String[] words) {
        int[] answer = {0, 0};
        int cnt = 1;
        HashSet<String> hs = new HashSet<>();
        hs.add(words[0]);
        for (int i = 1; i < words.length; i++)
        {
            if (i % n == 0)
                cnt++;
            if (words[i - 1].charAt(words[i - 1].length() - 1) != words[i].charAt(0) ||
               hs.contains(words[i]))
            {
                answer[0] = (i % n) + 1;
                answer[1] = cnt;
                break;
            }
            hs.add(words[i]);
        }
        return answer;
    }
}
```

### 전력망을 둘로 나누기

> int 는 Arrays.sort 사용 불가능 -> Integer만 가능
>
> 크루스칼 알고리즘 응용해봤다.

```java
import java.util.*;
class Solution {
    public int solution(int n, int[][] wires) {
        int answer = Integer.MAX_VALUE;
        int[] arr = new int[n + 1];
        int len = wires.length;
        for (int i = 0; i < len; i++)
        {
            for (int k = 0; k <= n; k++)
                 arr[k] = k;
            for (int j = 0; j < len; j++)
            {
                if (i != j)
                {
                    if (arr[wires[j][0]] <= arr[wires[j][1]])
                    {
                        //arr[wires[j][0]] = arr[wires[j][0]];
                        int t = arr[wires[j][1]];
                        arr[wires[j][1]] = arr[wires[j][0]];
                        for (int l = 1; l <= n; l++)
                        {
                            if (arr[l] == t)
                                arr[l] = arr[wires[j][0]];
                        }
                    }
                    else
                    {
                        int t = arr[wires[j][0]];
                        arr[wires[j][0]] = arr[wires[j][1]];
                        for (int l = 1; l <= n; l++)
                        {
                            if (arr[l] == t)
                                arr[l] = arr[wires[j][1]];
                        }
                        //arr[wires[j][1]] = arr[wires[j][1]];
                    }
                }
            }
            Integer[] t_arr = new Integer[n + 1];
            for (int k = 0; k <= n; k++)
                t_arr[k] = 0;
            for (int k = 1; k <= n; k++)
                t_arr[arr[k]] += 1;
            Arrays.sort(t_arr, (o1, o2) -> o2 - o1);
            // System.out.println(t_arr[0] +"  "+ t_arr[1]);
            answer = Math.min(Math.abs(t_arr[0] - t_arr[1]), answer);
        }// big for end
        
        return answer;
    }
}
```

> 다른사람 코드
>
> 자기 자식의 개수만 가지고 구하는 방법

```java
class Solution {
    int N, min;
    int[][] map;
    int[] vst;
    int dfs(int n){
        vst[n] = 1;
        int child = 1;
        for(int i = 1; i <= N; i++) {
            if(vst[i] == 0 && map[n][i] == 1) {
                child += dfs(i);
            }
        }
        min = Math.min(min, Math.abs(child - (N - child)));
        return child;
    }
    public int solution(int n, int[][] wires) {
        N = n;
        min = n;
        map = new int[n+1][n+1];
        vst = new int[n+1];
        for(int[] wire : wires) {
            int a = wire[0], b = wire[1];
            map[a][b] = map[b][a] = 1;
        }
        dfs(1);
        return min;
    }
}
```

### [1차] 캐시

> cacheSize 가 0인 경우도 예외 처리해주어야 한다. 문제 잘 읽기!!

```java
import java.util.*;
class Solution {
    public int solution(int cacheSize, String[] cities) {
        int answer = 0;
        Queue<String> q = new LinkedList<>(); 
     
     // 아예 처음부터 예외처리해주어도 된다.
     // if (cacheSize == 0)
     //   return cities.length * 5;
        
        for (int i = 0; i < cities.length; i++)
        {
            cities[i] = cities[i].toUpperCase();
            if (q.contains(cities[i]))
            {
                Queue<String> t = new LinkedList<>();
                while (!q.isEmpty())
                {
                    if (!q.peek().equals(cities[i]))
                        t.add(q.peek());
                    q.poll();
                }
                t.add(cities[i]);
                q = t;
                answer += 1;
            }
            else if (q.size() < cacheSize && cacheSize != 0)
            {
                answer += 5;
                q.add(cities[i]);
            }
            else
            {
                if (cacheSize != 0)
                {
                    q.poll();
                    q.add(cities[i]);    
                }
                answer += 5;
            }
        }
        return answer;
    }
}
```

> 다른 사람 코드 (queue 가 아닌 ArrayList 사용)

```java
import java.util.ArrayList;

class Solution {
    public static int solution(int cacheSize, String[] cities) {
        int answer = 0;
        ArrayList<String> list = new ArrayList<String>();

        if (cacheSize == 0)
            return cities.length * 5;

        for (int i = 0; i < cities.length; i++) {
            cities[i] = cities[i].toUpperCase();

            if (list.contains(cities[i])) {
                answer++;
                list.remove(cities[i]);
                list.add(cities[i]);
            } else {
                answer += 5;
                if (list.size() == cacheSize) {
                    list.remove(0);
                    list.add(cities[i]);
                }
                else 
                    list.add(cities[i]);
            }
        }
        return answer;
    }
}
```

### 이진 변환 반복하기

```java
class Solution {
    public int[] solution(String s) {
        int zero_cnt = 0;
        int change_cnt = 0;
        while (!s.equals("1"))
        {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < s.length(); i++)
            {
                if (s.charAt(i) == '1')
                    sb.append("1");
                else
                    zero_cnt++;
            }
            int num = sb.length();
            String temp = "";
            while (num > 0)
            {
                temp = (num % 2) + temp;
                num = num / 2;
            }
            s = temp;
            change_cnt++;
        }
        int[] answer = {change_cnt, zero_cnt};
        return answer;
    }
}
```

### 모음 사전

>경우의 수
>
>문자의 종류는 5개
>문자열 길이는 1, 2, 3, 4, 5 다섯개
>5 + 25 + 125 + 625 + 3125 = 3905
>문자간의 거리 = 경우의 수 총합 / 문자열 길이의 경우의 수(1=5, 2= 25, 3= 125, 4=625, 5=3125)
>
>문자열 종류는 3개
>문자열 길이는 1, 2, 3 세개
>3 + 9 + 27 = 49
>문자간의 거리 = 경우의 수 총합 / 문자열 길이의 경우의 수(1=3, 2= 9, 3= 27)
>
>문자열 종류는 2개
>문자열의 길이는 1, 2 두개
>2 + 4 = 6
>문자간의 거리 = 경우의 수 총합 / 문자열 길이의 경우의 수(1=2, 2=4)
>
>------
>
>사전에 알파벳 모음 'A', 'E', 'I', 'O', 'U'만을 사용하여 만들 수 있는, 길이 5 이하의 모든 단어가 수록되어 있습니다. 사전에서 첫 번째 단어는 "A"이고, 그다음은 "AA"이며, 마지막 단어는 "UUUUU"입니다.
>
>위의 글을 조건으로 경우의 수를 계산한다면
>
>문자의 종류는 5개
>문자열 길이는 1, 2, 3, 4, 5 다섯개
>5 + 25 + 125 + 625 + 3125 = 3905
>문자간의 거리 = 경우의 수 총합 / 문자열 길이의 경우의 수(1=5, 2= 25, 3= 125, 4=625, 5=3125)
>
>첫 번째 자리 경우 간격 781 (3905 / 5)
>두 번째 자리 경우 간격 156 (3905 / 25)
>세 번째 자리 경우 간격 31 (3905 / 125)
>네 번째 자리 경우 간격 6 (3905 / 625)
>다섯 번째 자리 간격 1 (3905 / 3125)
>
>예시 = AAAE
>
>간격 1 + 1 + 1 + 6 = 9
>A는 +1 만 하면 되니까 넘어가고
>E는 네 번째 자리로 간격은 6.
>E는 순서로 1 번째이니까 6 * 1.
>결과 : 9 + 1 = 10 (간격이 9이니 10번째가 찾는 문자가 된다)

```java
class Solution {
    public int solution(String word) {
        int answer = 0;
        // 5 + 25 + 125 + 625 + 3125
        int max = 3905;
        for (int i = 0; i < word.length(); i++)
        {
            if (word.charAt(i) == 'A')
            {
                answer += 1;
            }
            else if (word.charAt(i) == 'E')
            {
                answer += (max / Math.pow(5, i + 1)) * 1 + 1;
            }
            else if (word.charAt(i) == 'I')
            {
                answer += (max / Math.pow(5, i + 1)) * 2 + 1;
            }
            else if (word.charAt(i) == 'O')
            {
                answer += (max / Math.pow(5, i + 1)) * 3 + 1;
            }
            else if (word.charAt(i) == 'U')
            {
                answer += (max / Math.pow(5, i + 1)) * 4 + 1;
            }
        }
        return answer;
    }
}
```

> 다른 사람 코드

```java
class Solution {
    public int solution(String word) {
        int answer = 0, per = 3905;
        for(String s : word.split("")) answer += "AEIOU".indexOf(s) * (per /= 5) + 1;
        return answer;
    }
}




import java.util.*;
class Solution {
    List<String> list = new ArrayList<>();
    void dfs(String str, int len) {
        if(len > 5) return;
        list.add(str);
        for(int i = 0; i < 5; i++) dfs(str + "AEIOU".charAt(i), len + 1);
    }
    public int solution(String word) {
        dfs("", 0);
        return list.indexOf(word);
    }
}
```

### 점프와 순간 이동

```java
import java.util.*;

public class Solution {
    public int solution(int n) {
        int ans = 0;
        while (n != 0)
        {
            if (n % 2 == 0)
                n /= 2;
            else
            {
                n -= 1;
                ans++;
            }
        }
        return ans;
    }
}
```

### n^2 배열 자르기

```java
class Solution {
    public int[] solution(int n, long left, long right) {
        long len = right - left;
        int[] answer = new int[(int)len + 1];
        long x = left % n + 1;  
        long y = left / n + 1;  
        long cnt = x; 
        long num = y;
        if (x > y)
            num = x;
        for (int i = 0; i <= right - left; i++)
        {
            if (num > n)
            {
                y += 1;
                num = y;
                cnt = 1;
            }
            answer[i] = (int)num;
            cnt++;
            if (cnt > y)
                num += 1;
        }
        return answer;
    }
}
```

> 다른 사람 코드

```java
class Solution {
    public int[] solution(int n, long left, long right) {
        int[] answer = new int[(int) (right - left + 1)];
        int index = 0;
        for (long i = left; i <= right ; i++) {
            int row = (int) (i / n + 1);
            int col = (int) (i % n + 1);
            if(row > col){
                answer[index++] = row;
            } else{
                answer[index++] = col;
            }
        }

        return answer;
    }
}
```