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

