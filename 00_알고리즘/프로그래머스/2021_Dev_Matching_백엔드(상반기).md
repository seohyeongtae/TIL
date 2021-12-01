## 2021_Dev_Matching_백엔드(상반기)

### 로또의 최고 순위와 최저 순위(1번)

```java
class Solution {
    public int[] solution(int[] lottos, int[] win_nums) {
        int[] answer = new int[2];
        int cnt = 0;
        int zero = 0;
        for (int i: lottos)
        {
            if (i == 0 )
                zero++;
            else
            {
                 for (int j : win_nums)
                {
                    if (i == j)
                        cnt++;
                }
            }
        }
        int max_result = cnt + zero;
        int min_result = cnt;
        answer[0] = 7 - max_result;
        answer[1] = 7 - min_result;
        if (answer[1] == 7)
            answer[1] = 6;
        if (answer[0] == 7)
            answer[0] = 6;
        
        return answer;
    }
}
```

### 행렬 테두리 회전하기 (2번)

```java
class Solution {
    int[][] arr;
    public int[] solution(int rows, int columns, int[][] queries) {
        int[] answer = new int[queries.length];
        arr = new int[rows][columns];
        int cnt = 1;
        for (int i = 0; i < rows; i++)
        {
            for (int j = 0; j < columns; j++)
            {
                arr[i][j] = cnt;
                cnt++;
            }
        }
        
        for (int i = 0; i < queries.length; i++)
        {
            int x1 = queries[i][0] - 1;
            int y1 = queries[i][1] - 1;
            int x2 = queries[i][2] - 1;
            int y2 = queries[i][3] - 1;
            answer[i] = change_arr(x1,y1,x2,y2,rows);
        }
        return answer;
    } // solution end
    private int change_arr(int x1,int y1,int x2,int y2, int rows)
    {
        int min = 100000;
        int temp = arr[x1][y1];
        System.out.println(temp);
        for (int i = x1; i < x2; i++)
        {
            arr[i][y1] = arr[i + 1][y1];
            min = Math.min(min, arr[i][y1]);
        }
        for (int i = y1; i < y2; i++)
        {
            arr[x2][i] = arr[x2][i + 1];
            min = Math.min(min,  arr[x2][i]);
        }
        for (int i = x2; i > x1; i--)
        {
            arr[i][y2] = arr[i - 1][y2];
            min = Math.min(min,   arr[i][y2]);
        }
        for (int i = y2; i > y1; i--)
        {
            arr[x1][i] = arr[x1][i - 1];
            min = Math.min(min, arr[x1][i]);
        }
        arr[x1][y1 + 1] = temp;
        min = Math.min(min, temp);
        return min;
    }
}
```

### 다단계 칫솔 판매

```java
import java.util.*;
class Solution {
    public int[] solution(String[] enroll, String[] referral, String[] seller, int[] amount) {
        int[] answer = new int[enroll.length];
        HashMap node = new HashMap<String, String>();
        HashMap sell = new HashMap<String, Integer>();
        for (int i = 0; i < enroll.length; i++)
        {
            if (!referral[i].equals("-"))
                node.put(enroll[i], referral[i]);
            sell.put(enroll[i], 0);
        }

        for (int i = 0; i < seller.length; i++)
        {
            Queue<String> temp = new LinkedList<String>();
            temp.offer(seller[i]);
            int price = amount[i] * 100;
            String t = seller[i];

            // if (node.containsKey(seller[i]))  
            //    t = node.get(seller[i]).toString();
            // while (node.containsKey(t))
            // {
            //     temp.offer(t);
            //     t = node.get(t).toString();
            // }
            // temp.offer(t);
            sell.put(seller[i], (int) sell.getOrDefault(seller[i], 0) + price - price / 10);
            price = price / 10;
            while (node.containsKey(t) && price >= 1)
            {
                t = node.get(t).toString();
                sell.put(t, (int) sell.getOrDefault(t, 0) + price - price / 10);
                price = price / 10;
            }

        } // for end

        for (int i = 0; i < enroll.length; i++)
            answer[i] = (int) sell.get(enroll[i]);

        return answer;
    }
}
```

> 다른 사람 코드

```java
class Solution {
    public int[] solution(String[] enroll, String[] referral, String[] seller, int[] amount) {
        int[] answer = new int[enroll.length];

        // 순서돌면서 해당 판매자와 판매금액을 고려해서 처리
        for(int order = 0; order < seller.length; order++) {
            String person = seller[order];
            int originProfit = amount[order] * 100;

            process(person, originProfit, enroll, referral, answer);
        }
        return answer;
    }

    // 처리 과정
    private void process(String person, int profit, String[] enroll, String[] referral, int[] answer) {
        int myIndex = findMyIndex(person, enroll);

        if(referral[myIndex].equals("-")) {  // 부모가 더이상 없을 때
            if(profit < 10) {
                answer[myIndex] += profit;

            } else {
                answer[myIndex] += profit - (int)(profit*0.1);
            }
            return;
        } else {    // 부모가 존재할 때
            if(profit < 10) {   
                answer[myIndex] += profit;
            } else {
                int tempParentProfit = (int)(profit * 0.1);
                if(tempParentProfit < 1) {
                    answer[myIndex] += profit;
                    return;
                } else {
                    answer[myIndex] += profit - (int)(profit*0.1);
                    process(referral[myIndex], tempParentProfit, enroll, referral, answer);
                }
            }
        }
    }

    // 본인의 인덱스 찾기
    private int findMyIndex(String name, String[] enroll) {
        for(int i = 0; i < enroll.length; i++) {
            if(name.equals(enroll[i])){
                return i;
            }
        }
        return 0;
    }
}
```

### 헤비 유저가 소유한 장소 (4번)

```
 SELECT ID, NAME, HOST_ID FROM PLACES
WHERE HOST_ID IN (SELECT HOST_ID FROM PLACES GROUP BY HOST_ID HAVING count(HOST_ID) >= 2) ORDER BY ID;
```

