### 추석 트래픽
```java
class Solution {
    public int solution(String[] lines) {
        int answer = 0;
        
        Double[][] arr = new Double[lines.length][2];
        for (int i = 0; i < lines.length; i++)
        {
            String[] temp = lines[i].split(" ");
            String[] change_t = temp[1].split(":");
            double second = Double.parseDouble(change_t[2]);
            String milisecond_str = temp[2].replace("s","");
            double milisecond = Math.round(Double.parseDouble(milisecond_str) * 1000) / 1000.0;
            
            double end_time = (Integer.parseInt(change_t[0]) * 3600) + (Integer.parseInt(change_t[1]) * 60) + second;
            double start_time = 0;
            if (end_time - milisecond > 0)
                start_time = Math.round((end_time - milisecond + 0.001) * 1000) / 1000.0;
            
            arr[i][0] = start_time;
            arr[i][1] = end_time;
        }
        double tt = Math.round((arr[0][1]) * 1000) / 1000.0;
        double tt_plus = Math.round((tt + 1 - 0.001) * 1000) / 1000.0;
         
        while (tt <= arr[arr.length - 1][1])
        {
            int tem = 0;
            for (int i = 0; i < arr.length; i++)
            {
                if ((arr[i][0] <= tt && tt <= arr[i][1]) || (tt <= arr[i][0] && arr[i][1] <= tt_plus) ||
                   (arr[i][0] <= tt_plus && tt_plus <= arr[i][1]) || tt == arr[i][1])
                    tem++;  
            }
            answer = Math.max(answer, tem);
            tt = Math.round((tt + 0.1) * 1000) / 1000.0;
            tt_plus = Math.round((tt + 1 - 0.001) * 1000) / 1000.0;
        }
        return answer;
    }
}
```

>  다른 사람 코드
```java
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

class Solution {
  public int solution(String[] lines) throws ParseException {
          int answer = 1;
          int linesLen = lines.length;
          Date[] startTimeArr = new Date[lines.length];
          Date[] endTimeArr = new Date[lines.length];
          String prefixSec = "09:00:0";
          String postfixSec = ".000";
          String dateFormat = "HH:mm:ss.SSS";
          SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);

          //시점 구하기(첫번째 구간의 시점은 제외함)
          for (int i = 0; i < linesLen; i++) {
              String[] splitLine = lines[i].split(" ");
              String secStr = prefixSec + splitLine[2].replace("s", "");
              if (!secStr.contains(".")) {
                  secStr = secStr.concat(postfixSec);
              }
              Date sec = sdf.parse(secStr);
//            System.out.println(sdf.format(sec));
              endTimeArr[i] = sdf.parse((splitLine[1]));
//            System.out.println(sdf.format(endTimeArr[i]));
              startTimeArr[i] = new Date(endTimeArr[i].getTime() - sec.getTime() + 1);
//            System.out.println(sdf.format(startTimeArr[i]));
          }

          //하나씩 구간을 옮겨가면서 현재 종점과 다음 구간의 시점의 시간 차이가 1초 미만인 대상을 모두 찾는다.
          //찾은 경우 임시 total에 1씩 증가시키고, 1회 loop가 종료되면 answer와 비교하여 최대값을 answer에 할당한다.
          for (int i = 1; i < linesLen; i++) {
              int newAnswer = 1;
              for (int j = i; j < linesLen; j++) {
//                System.out.println(sdf.format(startTimeArr[j]));
//                System.out.println(sdf.format(endTimeArr[i-1]));
//                System.out.println(startTimeArr[j].getTime() - endTimeArr[i-1].getTime());
                  if (startTimeArr[j].getTime() - endTimeArr[i-1].getTime() < 1000) {
                      newAnswer++;
                  }
              }
              if (newAnswer > answer) {
                  answer = newAnswer;
              }
          }

          return answer;
      }
}



import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class Solution {

public int solution(String[] lines) {
        int answer = 0;
        List<Long> checkPointList = new ArrayList<Long>();
        List<Log> logList = new ArrayList<Log>();

        for (String line : lines) {
            Log log = new Log(line);
            checkPointList.add(log.getStart());
            checkPointList.add(log.getEnd());
            logList.add(log);
        }

        Collections.sort(checkPointList);

        int top = 0;
        for(int i = 0; i < checkPointList.size(); i++) {
            int count = 0;
            for (Log log : logList) {
                if(log.isIn(checkPointList.get(i), checkPointList.get(i) + 999)) {
                    count++;
                }
            }

            if(count > top) {
                top = count;
            }
        }

        answer = top;
        return answer;
    }

    class Log {
        private long start = 0;
        private long duration = 0;
        private long end = 0;

        public Log(String line) {
            String[] split = line.split(" ");
            String dateString = split[0] + " " + split[1];
            try {
                Date startDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse(dateString);
                this.end = startDate.getTime();

                String[] split2 = split[2].split("s");
                double parseDouble = Double.parseDouble(split2[0]);
                this.duration = (long) (parseDouble * 1000);

                this.start = this.end - this.duration; 

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public boolean isIn(long checkPointStart, long checkPointEnd) {
            if(this.end < checkPointStart || this.start >= checkPointEnd) {
                return false;
            } else
                return true;
        }

        public long getStart() {
            return start;
        }

        public void setStart(long start) {
            this.start = start;
        }

        public long getDuration() {
            return duration;
        }

        public void setDuration(long duration) {
            this.duration = duration;
        }

        public long getEnd() {
            return end;
        }

        public void setEnd(long end) {
            this.end = end;
        }
    }
}

```

### 브라이언의 고민

```java
> 처음 틀린코드...
import java.util.*;
class Solution {
    int i;
    StringBuilder sb;
    HashSet<Character> hs;
    
    //AaAaA
    private boolean check_sen_one (String sen, int t, char c)
    {
     // System.out.println("check_sen_one" + "  char  " + c + "  " + sen.charAt(t) + " i = " + t);
        boolean check = true;
        int j;
        for (j = t; j < sen.length(); j++)
        {
            if (sen.charAt(j) == c && check == true)
            {
                check = false;
                if (j + 1 == sen.length())
                    return false;                
                else
                    sb.append(String.valueOf(sen.charAt(j + 1)));
            }
            else if (sen.charAt(j) >= 'A' && sen.charAt(j) <= 'Z' && check == false)
                check = true;
            else
            {
                sb.append(" ");
                break;
            }
        }
        i = j - 1;
       //System.out.println("one   " + i + "  sb   "+ sb);
        return true;
    }
    private boolean check_sen_two (String sen, int t, char c)
    {
       // System.out.println("check_sen_two" +  " char  " + c + "  i == " + t);
        int j = t;
        int k = i + 1;
        int ch = 0;
        boolean upper = false;
        boolean check = true;
        for (j = i + 1; j < sen.length(); j++)
        {
            if (sen.charAt(j) >= 'a' && sen.charAt(j) <= 'z' && sen.charAt(j) != c)
            {
                // System.out.println("two for  char  " + sen.charAt(j) + " j == " + j + " i == " + i);
                if (hs.contains(sen.charAt(j)))
                    return false;
                else
                    hs.add(sen.charAt(j));
              // System.out.println("two for  char  " + sen.charAt(j));
                if (ch == 0)
                {
                    sb.append(String.valueOf(sen.charAt(j - 1)));
                    check = check_sen_one(sen, j, sen.charAt(j));
                    j = i - 1;
                    ch = 1;
                }
                upper = true;
                continue;
            }
            if (sen.charAt(j) == c)
                break;
        }
        i = j;
        
        if (upper == false)
        {
            if (k == j)
                return false;
            while (k < j && sen.charAt(k) != 'c')
            {
                if (sen.charAt(k) >= 'a' && sen.charAt(k) <= 'z')
                    return false;
                sb.append(String.valueOf(sen.charAt(k)));
                k++;
            }
            sb.append(" ");
            i = k;
        }
       // System.out.println("two finish i   " + i);
        return check;
    }
    //check_false(sentence.charAt(i), i + 1 , sentence))
    private boolean check_false(char a, int t, String sen)
    {
        for (int i = t; i < sen.length(); i++)
        {
            if (sen.charAt(i) == a)
                return false;
        }
        return true;
    }
    private boolean no_lower(String sentence)
    {
        for (int t = 0; t < sentence.length(); t++)
        {
            if (sentence.charAt(t) >= 'a' && sentence.charAt(t) <= 'z' )
                return false;       
        }
        return true;
    }
    // check_char(sentence, sentence.charAt(i)
    private boolean check_char(String sentence, char c)
    {
        int cnt = 0;
       // System.out.println("check char   " + c);
        for (int t = 0; t < sentence.length(); t++)
        {
            if (sentence.charAt(t) == c)
                cnt++;
            if (cnt > 2)
                return false;
        }
        if (cnt == 2)
        {
           // System.out.println("do two ==  ");
            return true;
        }
            
        return false;
    }
    public String solution(String sentence) {
        String answer = "invalid";
        boolean upper = false;
        sb = new StringBuilder();
        hs = new HashSet<Character>();
        i = 0;
        if (no_lower(sentence))
            return sentence;
        while (i < sentence.length())
        {
            //System.out.println(i);
            //AaAaA
            if (i - 1 >= 0 && i + 1 < sentence.length() && sentence.charAt(i) >= 'A' && sentence.charAt(i) <= 'Z'
               && sentence.charAt(i - 1) >= 'A' && sentence.charAt(i - 1) <= 'Z' && upper == true 
               && sentence.charAt(i + 1) >= 'a' && sentence.charAt(i + 1) <= 'z')
            {
                sb.append(String.valueOf(" " + sentence.charAt(i)));
            }
     
            else if(sentence.charAt(i) >= 'a' && sentence.charAt(i) <= 'z' 
               && !hs.contains(sentence.charAt(i)) && check_char(sentence, sentence.charAt(i)))
            {
                hs.add(sentence.charAt(i));
                upper = false;
                sb.append(" ");
                if (!check_sen_two(sentence, i, sentence.charAt(i)))
                    return answer;
            }
            else if(sentence.charAt(i) >= 'a' && sentence.charAt(i) <= 'z' 
               && (hs.contains(sentence.charAt(i)) || upper == false))
            {
              // System.out.println("solution while   " + sentence.charAt(i) + " i == " + i);
                return answer;
            } 
            else if(sentence.charAt(i) >= 'a' && sentence.charAt(i) <= 'z' 
               && !hs.contains(sentence.charAt(i)) && upper == true && i + 2 < sentence.length()
                   && ((sentence.charAt(i+2) == sentence.charAt(i)) 
                       || check_false(sentence.charAt(i), i + 1 , sentence)) )
            {
                hs.add(sentence.charAt(i));
                upper = false;
                if (!check_sen_one(sentence, i, sentence.charAt(i)))
                    return answer;
            }
            // aAAAAa   aAbAbAbAa
            else if (sentence.charAt(i) >= 'A' && sentence.charAt(i) <= 'Z')
            {
                upper = true;
                sb.append(String.valueOf(sentence.charAt(i)));
            }
            i++;
        }
        answer = sb.toString().trim().replaceAll("  ", " ");
        return answer;
    } // solution end
}
```

> 다른사람 코드 참고

```java
import java.util.*;

public class Solution {

    public String solution(String sentence) {
        StringBuilder answerList = new StringBuilder();
        //HashMap과 달리 입력 순서 보장
        LinkedHashMap<Character, ArrayList<Integer>> lowerCount = new LinkedHashMap<>();
        String invalid = "invalid";
        try {
            int size = sentence.length();

            //소문자의 각 종류 / 위치 파악
            for(int i=0; i<size; i++){
                char c = sentence.charAt(i);

                if(Character.isLowerCase(c)){
                    if(!lowerCount.containsKey(c)){
                        lowerCount.put(c, new ArrayList<Integer>());
                    }
                    lowerCount.get(c).add(i);
                }
            }

            int stringIdx = 0;
            int startChar, endChar;
            int lastStartChar = -1, lastEndChar = -1;
            int startWord = 0, endWord = 0;
            int lastStartWord= -1, lastEndWord = -1;
            int count, distance;
            int rule = 0;

            ArrayList<Integer> temp;
            for(char c : lowerCount.keySet()){
                temp = lowerCount.get(c);
                count = temp.size();
                startChar = temp.get(0);
                endChar = temp.get(count-1);


                //AaA, AaAaAaA...
                if(count == 1 || count >= 3){
                    for(int i=1; i<count; i++){
                        //간격 2 넘어가면 x
                        if(temp.get(i) - temp.get(i-1) != 2) return invalid;
                    }
                    rule = 1;
                }

                else if (count == 2){
                    distance = endChar - startChar;

                    //다른 기호 안에 있음 (규칙 2와 겹침)
                    if(distance == 2 && (endChar < lastEndChar && startChar > lastStartChar)){
                        rule = 1;
                    }
                    else if(distance >= 2){
                        rule = 2;
                    }
                    //소문자 연속은 x
                    else  return invalid;
                }

                //규칙에 따른 예외
                if(rule == 1){
                    //기호 위치에서 앞뒤로 한칸씩
                    startWord = startChar -1;
                    endWord = endChar+1;

                    //이전 단어 안에 포함되어 있는 경우
                    if(lastStartWord < startWord && lastEndWord > endWord){
                        //규칙 2 아니면 안됨
                        if(startChar - lastStartChar  == 2 && lastEndChar - endChar == 2){
                            continue;
                        }
                        else return invalid;
                    }
                }

                else if (rule == 2){
                    startWord = startChar;
                    endWord = endChar;
                    //규칙 2는 중복되면 안됨
                    if(lastStartWord < startWord && lastEndWord > endWord) return invalid;
                }

                if(lastEndWord >= startWord) return  invalid;

                //소문자 등장 이전에 존재하던 앞의 단어 추가
                if(stringIdx < startWord){
                    answerList.append(makeWord(sentence,stringIdx,startWord-1));
                    answerList.append(" ");
                }
                answerList.append(makeWord(sentence,startWord,endWord));
                answerList.append(" ");
                lastStartWord = startWord;
                lastEndWord = endWord;
                lastStartChar = startChar;
                lastEndChar = endChar;
                stringIdx = endWord+1;
            }
            //뒤에 남는 단어들도 더하기
            if(stringIdx < size){
                answerList.append(makeWord(sentence,stringIdx,size-1));
            }
        }
        catch (Exception e){
            return invalid;
        }
        return answerList.toString().trim();
    }

    public String makeWord(String sentence, int start, int end){
        String word = sentence.substring(start, end+1);
        return word.replaceAll("[a-z]","");
    }
}

```

### 자물쇠와 열쇠

> key 를 회전시켜야하는데 lock을 회전시켜서 틀린 코드

```java
class Solution {
    boolean result;
    int len;
    public boolean solution(int[][] key, int[][] lock) {
        boolean answer = true;
        len = lock.length;
        result = false;
        int c = 0;
        for (int i = 0; i < len; i++)
        {
            for (int j = 0; j < len; j++)
            {
                if (lock[i][j] == 0)
                    c = 1;
            }
        }
        if (c == 0)
            return true;
        
        // lock
        // change up down left right
        // check
        check(lock, key);
        change_up(lock, key);
        change_down(lock, key);
        
        // 90 lock
        int[][] arr_90 = change_arr(lock);
        check(arr_90, key);
        change_up(arr_90, key);
        change_down(arr_90, key);
        // 180 lock
        int[][] arr_180 = change_arr(arr_90);
        check(arr_180, key);
        change_up(arr_180, key);
        change_down(arr_180, key);
        // 270 lock
        int[][] arr_270 = change_arr(arr_180);
        check(arr_270, key);
        change_up(arr_270, key);
        change_down(arr_270, key);
        
        answer = result;
        return answer;
    } //solution end
    
    private void check(int[][] lock, int[][] key)
    {
        int c = 0;
        roop:for (int i = 0; i < key.length; i++)
        {
            for (int j = 0; j <  key.length; j++)
            {
                if (lock[i][j] == 0 && key[i][j] == 0)
                {
                    c = 1;
                    break roop;
                }
            }
        }
        roop2:for (int i = 0; i < len; i++)
        {
            for (int j = key.length; j < len; j++)
                if (lock[i][j] == 0)
                {
                    c = 1;
                    break roop2;
                }
        }
        roop3:for (int i = 0; i < len; i++)
        {
            for (int j = key.length; j < len; j++)
                if (lock[j][i] == 0)
                {
                    c = 1;
                    break roop3;
                }
        }
        if (c == 0)
            result = true;
    } // check_end
    
    private void change_right(int[][] lock, int[][] key)
    {
        int arrtemp[][] = new int[len][len];
        for (int i = 0; i < len; i++)
        {
            if (lock[i][len - 1] == 0)
                return;
        }
        // System.out.println("right    ");
        for (int i = len - 1; i > 0; i--)
        {
            for (int j = 0; j < len - 1; j++)
            {
                arrtemp[j][i] = lock[j][i - 1];
            }
        }
        for (int i = 0; i < len; i++)
        {
            arrtemp[i][0] = 1;
        }
        check(arrtemp, key);
        change_right(arrtemp, key);
    } // change_right end
    
    private void change_left(int[][] lock, int[][] key)
    {
        int arrtemp[][] = new int[len][len];
        for (int i = 0; i < len; i++)
        {
            if (lock[i][0] == 0)
                return;
        }
        // System.out.println("left  " );
        for (int i = 0; i < len - 1; i++)
        {
            for (int j = 0; j < len; j++)
            {
                arrtemp[j][i] = lock[j][i + 1];
            }
        }
        for (int i = 0; i < len; i++)
        {
            arrtemp[i][len - 1] = 1;
        }
        check(arrtemp, key);
        change_left(arrtemp, key);
    } // change_left end
    
    
    private void change_down(int[][] lock, int[][] key)
    {
        int temp[][] = new int[len][len];
        int temp2[][] = new int[len][len];
        int arrtemp[][] = new int[len][len];
        for (int i = 0; i < len; i++)
        {
            for (int j = 0; j < len; j++)
            {
                temp[i][j] = lock[i][j];
                temp2[i][j] = lock[i][j];
            }
        }
        change_left(temp2, key);
        change_right(temp, key);
        for (int i = 0; i < len; i++)
        {
            if (lock[len - 1][i] == 0)
                return;
        }
        // System.out.println("DOWN");
        for (int i = len - 1; i > 0; i--)
        {
            for (int j = 0; j < len; j++)
                arrtemp[i][j] = lock[i - 1][j];
        }
        for (int i = 0; i < len; i++)
            arrtemp[0][i] = 1;
        check(arrtemp, key);
        change_down(arrtemp, key);

    } // change_down end
    
    private void change_up(int[][] lock, int[][] key)
    {
        int temp[][] = new int[len][len];
        int temp2[][] = new int[len][len];
        int arrtemp[][] = new int[len][len];
        for (int i = 0; i < len; i++)
        {
            for (int j = 0; j < len; j++)
            {
                temp[i][j] = lock[i][j];
                temp2[i][j] = lock[i][j];
            }
        }
        
        change_left(temp2, key);
        change_right(temp, key);
        for (int i = 0; i < len; i++)
        {
            if (lock[0][i] == 0)
                return;
        }
        // System.out.println("UP");
        for (int i = 0; i < len - 1; i++)
        {
            for (int j = 0; j < len; j++)
                arrtemp[i][j] = lock[i + 1][j];
        }
        for (int i = 0; i < len; i++)
            arrtemp[len - 1][i] = 1;
        check(arrtemp, key);
        change_up(arrtemp, key);
    } // change_up end
    
    private int[][] change_arr(int[][] lock)
    {
        int num = len / 2;
        int[][] temp = new int[len][len];
        int t = 0;
        while (t < num)
        {
            for (int i = t; i < len - t; i++)
            {
                temp[t][i] = lock[len - 1 - i][t];
                temp[i][t] = lock[len - 1 - t][i];
                temp[len - 1 - t][i] = lock[len - 1 - i][len - 1 - t];
                temp[len - 1 - i][len - 1 - t] = lock[t][len - 1 - i];
            }
            t++;
        }
        if (num % 2 == 1)
        {
            temp[num][num] = lock[num][num];
        }
        // for (int i = 0; i < len; i++)
        // {
        //     for (int j = 0; j < len; j++)
        //         System.out.print(temp[i][j]);
        //     System.out.println();
        // }
        // System.out.println();
        
        return temp;
    } // change arr end
    
} // class end
```

> Key 를 돌려서 틀린 케이스 
>
> ex) 왼쪽으로 갔다가 한 줄을 지우고 오른쪽으로 다시 검색하는 예외처리가 매우매우 어렵다. 
>
> 시간복잡도도 안좋아짐
>
> 결국 키를 90, 180, 270 돌려서 왼쪽끝에서 부터 하나씩 비교해보는 방법으로 해야한다.
>
> 36, 37 번 케이스를 해결 못했다....

```java
class Solution {
    boolean result;
    int len;
    int key_len;
    public boolean solution(int[][] key, int[][] lock) {
        boolean answer = true;
        len = lock.length;
        result = false;
        key_len = key.length;
        int[][] temp_key = new int[len][len];
        for (int i = 0; i < key.length; i++)
        {
            for (int j = 0; j < key.length; j++)
                temp_key[i][j] = key[i][j];
        }
        // change up down left right
        // check
        if (check_key(temp_key))
        {
            // key
            key_up(lock, temp_key);
            key_down(lock, temp_key);
            
            // 90 key
            int[][] arr_90 = change_arr(temp_key);
            key_up(lock, arr_90);
            key_down(lock, arr_90);

            // 180 key
            int[][] arr_180 = change_arr(arr_90);
            key_up(lock, arr_180);
            key_down(lock, arr_180);
            
            // 270 key
            int[][] arr_270 = change_arr(arr_180);
            key_up(lock, arr_270);
            key_down(lock, arr_270);
        }
        answer = result;
        return answer;
    } //solution end
    
    private void key_right(int[][] lock, int[][] key)
    {
        check(lock, key);
        int[][] temp_key = new int[len][len];
        for (int i = len - 1; i > 0; i--)
        {
            for (int j = 0; j < len; j++)
                temp_key[j][i] = key[j][i - 1];
        }
        if (check_key(temp_key) && result == false)
            key_right(lock, temp_key);
    } // key_right end
    
    private void key_left(int[][] lock, int[][] key)
    {
        int[][] temp_key = new int[len][len];
        check(lock, key);
        for (int i = 0; i < len - 1; i++)
        {
            for (int j = 0; j < len; j++)
                temp_key[j][i] = key[j][i + 1];
        }
        if (check_key(temp_key) && result == false)
            key_left(lock, temp_key);
    } // key_left end
    
    private void key_up(int[][] lock, int[][] key)
    {
        key_left(lock, key);
        key_right(lock, key);
        int[][] temp_key = new int[len][len];
        for (int i = 0; i < len - 1; i++)
        {
            for (int j = 0; j < len; j++)
                temp_key[i][j] = key[i + 1][j];
        }
        check(lock, temp_key);
        if (check_key(temp_key) && result == false)
            key_up(lock, temp_key);
    } // key_up end
    
    private void key_down(int[][] lock, int[][] key)
    {
        key_left(lock, key);
        key_right(lock, key);
        int[][] temp_key = new int[len][len];
        for (int i = len - 1; i > 0; i--)
        {
            for (int j = 0; j < len; j++)
                temp_key[i][j] = key[i - 1][j];
        }
        check(lock, temp_key);
        if (check_key(temp_key) && result == false)
            key_down(lock, temp_key);
    } // key_down end
    
    private boolean check_key(int[][] key)
    {
        for (int i = 0; i < len; i++)
        {
            for (int j = 0; j < len; j++)
            {
                if (key[i][j] == 1)
                    return true;
            }
        }
        return false;
    } // check_key end
    
    private void check(int[][] lock, int[][] key)
    {
        for (int i = 0; i < len; i++)
        {
            for (int j = 0; j < len; j++)
            {
                if (lock[i][j] + key[i][j] != 1)
                    return;
            }
        }
        result = true;
    } // check_end
    
    
    private int[][] change_arr(int[][] lock)
    {
        int num = len / 2;
        int[][] temp = new int[len][len];
        int t = 0;
        while (t < num)
        {
            for (int i = t; i < len - t; i++)
            {
                temp[t][i] = lock[len - 1 - i][t];
                temp[i][t] = lock[len - 1 - t][i];
                temp[len - 1 - t][i] = lock[len - 1 - i][len - 1 - t];
                temp[len - 1 - i][len - 1 - t] = lock[t][len - 1 - i];
            }
            t++;
        }
        if (num % 2 == 1)
            temp[num][num] = lock[num][num];
        return temp;
    } // change arr end
    
} // class end
```

> 다른 사람코드
> locksize + keysize * 2  만큼의 표를 만든 후
> key의 위치를 옮겨가며 비교하는 코드
``` java
class Solution {
    private int lockSize;
    private int keySize;
    private int lockCoordStart;
    private int lockCoordEnd;
    private int[][] lockWithSpace;
    private int numHoles;
    private int[][][] keyRotations;

    public boolean solution(int[][] key, int[][] lock) {
        lockSize = lock.length;
        keySize = key.length;
        lockCoordStart = keySize - 1;
        lockCoordEnd = lockSize + keySize - 2;
        lockWithSpace = new int[lockSize + (keySize - 1) * 2][lockSize + (keySize - 1) * 2];
        numHoles = 0;
        for (int y = lockCoordStart; y <= lockCoordEnd; y++) {
            for (int x = lockCoordStart; x <= lockCoordEnd; x++) {
                lockWithSpace[y][x] = lock[y - lockCoordStart][x - lockCoordStart];
                if (lockWithSpace[y][x] == 0) numHoles++;
            }
        }
        // rotate
        keyRotations = new int[4][keySize][keySize];
        makeKeyRotations(key);

        for (int y = 0; y <= lockCoordEnd; y++) {
            for (int x = 0; x <= lockCoordEnd; x++) {
                if (isSolvable(y, x)) return true;
            }
        }
        return false;
    }

    private void makeKeyRotations(int[][] key) {
        keyRotations[0] = key;
        for (int rot = 1; rot < 4; rot++) {
            for (int y = 0; y < keySize; y++) {
                for (int x = 0; x < keySize; x++) {
                    keyRotations[rot][y][x] = keyRotations[rot - 1][x][y];
                }
            }
            for (int y = 0; y < keySize; y++) {
                for (int x = 0; x < keySize / 2; x++) {
                    int tmp = keyRotations[rot][y][x];
                    keyRotations[rot][y][x] = keyRotations[rot][y][keySize - 1 - x];
                    keyRotations[rot][y][keySize - 1 - x] = tmp;
                }
            }
        }
    }

    private boolean isSolvable(int keyLUY, int keyLUX) {
        boolean breakFlag = false;
        // System.out.println("[" + keyLUY + ", " + keyLUX + "]");
        // System.out.println("<" + lockCoordStart + " / " + lockCoordEnd + ">");
        for (int rot = 0; rot < 4; rot++) {
            // System.out.println(rot);
            int cnt = 0;
            int yStart = Math.max(keyLUY, lockCoordStart);
            int xStart = Math.max(keyLUX, lockCoordStart);
            int yEnd = Math.min(keyLUY + keySize - 1, lockCoordEnd);
            int xEnd = Math.min(keyLUX + keySize - 1, lockCoordEnd);
            for (int y = yStart; y <= yEnd; y++) {
                for (int x = xStart; x <= xEnd; x++) {
                    int lockBump = lockWithSpace[y][x];
                    int keyBump = keyRotations[rot][y - keyLUY][x - keyLUX];
                    // System.out.println(y + ", " + x + ": " + lockBump + " " + keyBump);
                    if (keyBump == lockBump) {
                        breakFlag = true;
                        break;
                    }
                    if (keyBump == 1 && lockBump == 0) cnt++;
                }
                if (breakFlag) {
                    break;
                }
            }
            // System.out.println("cnt: " + cnt);
            if (breakFlag) {
                breakFlag = false;
            } else if (cnt == numHoles) {
                return true;
            }
        }
        return false;
    }
}
```
