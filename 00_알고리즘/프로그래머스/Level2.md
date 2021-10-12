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

