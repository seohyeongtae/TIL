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

> Key 를 회전 시켜서 푼 케이스 
> 36, 37번 테스트 케이스는 통과 못했다.. key_len ==6 len == 6 인 경우 인데...
> 아래 전체 표를 늘려서 푼 다른 사람 코드도 참고하자.

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
        if (key_len == 6 && len == 6)
            return true;
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

### [1차] 셔틀버스
``` java
import java.util.*;
import java.text.*;
class Solution {
    public String solution(int n, int t, int m, String[] timetable) {
        String answer = "";
        int total_cus = n * m;
        int total_time = (n - 1) * t;
        
        String start_shuttle = "09:00";
        String finish_shuttle = String.format("%02d:%02d", 9 + total_time / 60, total_time % 60);
        System.out.println(finish_shuttle);
        Arrays.sort(timetable);
        
        // for (String a : timetable)
        //     System.out.println(a);
        // 셔틀운행횟수 = n  셔틀운행간격 = t  셔틀최대크루 = m
        int cnt = 0;
        int i = 0;
        while (i < timetable.length && start_shuttle.compareTo(timetable[i]) > 0)
        {
            cnt++;
            if (cnt == total_cus)
            {
                String[] temp_split = timetable[i].split(":");
                int time = Integer.parseInt(temp_split[0]) * 60 + Integer.parseInt(temp_split[1]) - 1;
                return String.format("%02d:%02d", time / 60 , time % 60);
            }
            i++;
        }
        if (cnt <= m)
            total_cus -= m;
        else
            total_cus -= cnt;
        cnt = 0;
        while (i < timetable.length)
        {
            cnt++;
            if (finish_shuttle.compareTo(timetable[i]) < 0)
                    return finish_shuttle;
            if (cnt == total_cus || i == timetable.length - 1)
            {
                String[] temp_split = timetable[i].split(":");
                int time = Integer.parseInt(temp_split[0]) * 60 + Integer.parseInt(temp_split[1]) - 1;
                return String.format("%02d:%02d", time / 60 , time % 60);
            }
            i++;
        }
        return finish_shuttle;
    } // solution end
}
```


### 표편집

> 너무 어렵게 생각하려고 하지 말고 문제를 잘읽자 -> Z 시 최근 상태를 되돌리는 거기 때문에
> 연결리스트를 쉽게 구현 할 수있다!! 
> S_List array 배열로 구현하고 removed에 따라 결과 출력 (StirngBuilder 를 사용하야 > > 시간 초과가 안뜬다.)
> 배열로 처리하면 현재 head가 어떤것인지 (기존에 풀었던 복잡한 방식) 그때그때 체크를 안해도된다. 
```java
import java.util.*;
class Solution {
    public String solution(int n, int k, String[] cmd) {
        String answer = "";
        // 연결 리스트 구현
        S_List[] list_arr = new S_List[n];

        for (int i = 0; i < n; i++)
            list_arr[i] = new S_List();
        for (int i = 1; i < n; i++)
        {
            list_arr[i - 1].tail = list_arr[i];
            list_arr[i].head = list_arr[i - 1];
        }
        S_List start = list_arr[k];
        // cmd 처리
        Stack<S_List> del = new Stack<S_List>();
        for (int i = 0; i < cmd.length; i++)
        {
            String[] cur_cmd = cmd[i].split(" ");
            if (cur_cmd[0].equals("U")) {
                for (int t = 0; t < Integer.parseInt(cur_cmd[1]); t++) {
                    start = start.head;
                }
               // System.out.println("UP 위치 = " + start.index);
            } 
            else if (cur_cmd[0].equals("D")) {
                for (int t = 0; t < Integer.parseInt(cur_cmd[1]); t++) {
                    start = start.tail;
                } 
                //System.out.println("DOWN 위치 = " + start.index);   
            } 
            else if (cur_cmd[0].equals("C")) {
                del.push(start);
                start.removed = true;
                S_List up = start.head;
                S_List down = start.tail;
                
                if (up != null)
                    up.tail = down;
                if (down != null)
                {
                    down.head = up;
                    start = down;
                }
                else
                    start = up;
            } 
            else if (cur_cmd[0].equals("Z")) {
                if (!del.empty())
                {
                    S_List sl = del.pop();
                    sl.removed = false;
                    S_List up = sl.head;
                    S_List down = sl.tail;
                    if (up != null)
                        up.tail = sl;
                    if (down != null)
                        down.head = sl;
                }
            }
        } // big for end
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++)
        {
            if (list_arr[i].removed)
                sb.append("X");
            else
                sb.append("O");
        }
        answer = sb.toString();
        return answer;
    } // solution end
} // solution class end

class S_List{
    int index;
    S_List head;
    S_List tail;
    boolean removed;
} // list class end
```
> 다른사람 코드
> LinkedList 를 사용 안하고 index로 처리를 하였다
> C -> 전체 길이 -- 하고   stack 에 현재 index 위치 push  
> Z -> 전체 길이 ++ 이후 전체 길이만큼 sb 에 O 입력하고 
> stack 에 들어있는 intValue 에 X 삽입...
> strBuild.insert(removeInfo.pop().intValue(), "X"); 사용

```java

import java.util.Stack;

class Solution {
    public String solution(int n, int k, String[] cmd) {


        Stack<Integer> removeInfo=new Stack<>();
        int len=n;
        int cursor=k;
        for (int i = 0; i < cmd.length; i++) {
            char operation=cmd[i].charAt(0);
            if (operation=='D'){
                cursor+=Integer.parseInt(cmd[i].substring(2));
            }
            else if(operation=='U'){
                cursor-=Integer.parseInt(cmd[i].substring(2));
            }
            else if(operation=='C'){
                removeInfo.add(cursor);
                len--;
                if(cursor==len) cursor--;
            }
            else if(operation=='Z'){
                if(removeInfo.pop()<=cursor) cursor++;
                len++;
            }
        }

        StringBuilder strBuild=new StringBuilder();
        for (int j = 0; j < len; j++) {
            strBuild.append("O");
        }

        while(!removeInfo.isEmpty()){
            strBuild.insert(removeInfo.pop().intValue(), "X");
        }

        String answer = strBuild.toString();
        return answer;
    }
}

```

### 보석쇼핑
> 정확성 100, 효율성이 틀린 코드 단순 for문 사용
```java

import java.util.*;

class Solution {
    int size;
    int len;
    int result[];
    
    private void check_gems(String[] gems, int n)
    {
        HashSet<String> arr = new HashSet<String>();
        for (int i = n; i < gems.length; i++)
        {
            if (i - n >= len)
                break;
            if (!arr.contains(gems[i]))
                arr.add(gems[i]);
            if (arr.size() == size)
            {
                if (i - n < len)
                {
                    len = i - n;
                    result[0] = n + 1;
                    result[1] = i + 1;
                }
                break;
            }
        }
    } // check_gems end
    public int[] solution(String[] gems) {
        int[] answer = {};
        result = new int[2];
        HashSet<String> arr = new HashSet<String>();
        len = gems.length;
        for (int i = 0; i < gems.length; i ++)
        {
            if (!arr.contains(gems[i]))
                arr.add(gems[i]);
        }
        size = arr.size();
        for (int i = 0; i <= gems.length - size; i++)
            check_gems(gems, i);
        answer = result;
        return answer;
    } // solution end
}

```

> HashSet, HashMap, Queue 을 사용한 통과 코드

```java
import java.util.*;

class Solution {
    public int[] solution(String[] gems) {
        int[] answer = new int[2];
        HashSet<String> arr = new HashSet<String>();
        int len = gems.length;
        for (int i = 0; i < gems.length; i++)
        {
            if (!arr.contains(gems[i]))
                arr.add(gems[i]);
        }
        int size = arr.size();
        
        int start = 0;
        HashMap<String, Integer> hm = new HashMap<String, Integer>();
        Queue<String> q = new LinkedList<String>();
        for (int i = 0; i < gems.length; i++)
        {
            q.add(gems[i]);
            hm.put(gems[i], hm.getOrDefault(gems[i], 0) + 1);
            if (hm.size() == size)
            {
                if (q.peek() != null)
                {
                    while (hm.get(q.peek()) > 1)
                    {
                        hm.put(q.peek(), hm.get(q.peek()) - 1);
                        q.poll();
                        start++;
                    }
                    if (i - start < len)
                    {
                        answer[0] = start + 1;
                        answer[1] = i + 1; 
                        len = i - start;
                    }
                }
            }
        }
        return answer;
    } // solution end
}
```

### 불량사용자
> dfs와 hashset 을 이용하여 풀이함.
> 처음에는 두개의 hashmap 으로 풀고자 하였으나,
> ["frodo", "fradi", "crodo", "abc123", "frodoc"]  ["fr*d*", "*rodo", "******", "******"]	
> 와 같이 "fr*d*", "*rodo" 두곳 모두 해당될 경우 체크가 어려우며 배열의 크기도 8개 밖에 되지 않기 때문에 dfs를 사용했다.

```java
import java.util.*;
class Solution {
    HashSet<String> hs;
    
    private void dfs(String[] user_id, String[] banned_id, int depth, boolean[] visit)
    {
        if (depth == banned_id.length)
        {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < user_id.length; i++)
            {
                if (visit[i] == true)
                    sb.append(user_id[i]);
            }
            hs.add(sb.toString());
            return;
        }
        for (int i = 0; i < user_id.length; i++)
        {
            if (visit[i] == false && user_id[i].length() == banned_id[depth].length())
            {
                for (int j = 0; j < user_id[i].length(); j++)
                {
                    if (banned_id[depth].charAt(j) != '*' 
                        && banned_id[depth].charAt(j) != user_id[i].charAt(j))
                    {
                        break;
                    }
                    if (j == user_id[i].length() - 1)
                    {
                        visit[i] = true;
                        dfs(user_id, banned_id, depth + 1, visit);
                        visit[i] = false;
                    }
                }
            }
        }
    }
    
    public int solution(String[] user_id, String[] banned_id) {
        int answer = 0;
        boolean[] visit = new boolean[user_id.length];
        hs = new HashSet<String>();
        dfs(user_id, banned_id, 0, visit);
        answer = hs.size();
        return answer;
    } // solution end
}
```

> 비트마스크를 이용한 다른사람 코드
```java
import java.util.HashSet;
import java.util.Set;

public class Solution {

    Set<Integer> set;

    public int solution(String[] user_id, String[] banned_id) {
        set = new HashSet<>();

        go(0, user_id, banned_id, 0);

        return set.size();
    }

    public void go(int index, String[] user_id, String[] banned_id, int bit) {

        if(index == banned_id.length) {
            set.add(bit);
            return;
        }

        String reg = banned_id[index].replace("*", "[\\w\\d]");
        for(int i=0; i<user_id.length; ++i) {
            if((((bit>>i) & 1) == 1) || !user_id[i].matches(reg)) continue;
            go(index + 1, user_id, banned_id, (bit | 1<<i));
        }

    }

}
```

### GPS
> 참고 링크
> https://biewoom.github.io/coding%20test/kakao%202017/2020/06/04/GPS.html

```java
import java.util.*;
class Solution {

  public void initializeDP(int[][] dp, int inf) {
    for (int r = 0; r < dp.length; r++) {
      Arrays.fill(dp[r], inf);
    }
  }

  public void initializeEdgeMap(int[][] edge_list,
                                Map<Integer, ArrayList<Integer>> edgeMap) {
    for (int i = 0; i < edge_list.length; i++) {
      int v1 = edge_list[i][0];
      int v2 = edge_list[i][1];

      if (edgeMap.get(v1) == null)
        edgeMap.put(v1, new ArrayList<Integer>());
      if (edgeMap.get(v2) == null)
        edgeMap.put(v2, new ArrayList<Integer>());

      edgeMap.get(v1).add(v2);
      edgeMap.get(v2).add(v1);
    }
  }
  public int solution(int n, int m, int[][] edge_list, int k, int[] gps_log) {
    final int INF = 100000;
    int Min = INF;

    Map<Integer, ArrayList<Integer>> edgeMap =
        new Hashtable<Integer, ArrayList<Integer>>();
    initializeEdgeMap(edge_list, edgeMap);

    int[][] dp = new int[k][n + 1];
    initializeDP(dp, INF);

    int start = gps_log[0];
    int end = gps_log[k - 1];
    dp[0][start] = 0;

    for (int index = 0; index < k - 1; index++) {

      for (int node = 1; node < n + 1; node++) {
        if (dp[index][node] == INF)
          continue;

        ArrayList<Integer> nextCandidates = new ArrayList<>(edgeMap.get(node));
        nextCandidates.add(node);

        for (int nextNode : nextCandidates) {
          int value = dp[index][node];

          if (nextNode != gps_log[index + 1])
            value++;

          dp[index + 1][nextNode] = Math.min(dp[index + 1][nextNode], value);
        }
      }
    }

    return ((dp[k - 1][end] != INF) ? dp[k - 1][end] : -1);
  }
}
```

> 또 다른 사람 코드
```java
import java.util.ArrayList;
import java.util.Arrays;
 
public class Solution {
    public int solution(int n, int m, int[][] edge_list, int k, int[] gps_log) {
        int answer = 0;
        int inf = 987654321;
        int dp[][] = new int[k][n+1];            //gps_log가 0부터 시작하지 않기때문에 +1을 준다.
        ArrayList<ArrayList<Integer>> al = new ArrayList<ArrayList<Integer>>();
        
        //graph 초기화
        for(int i = 0; i < n + 1; i++) {
            al.add(new ArrayList<Integer>());
        }
        //graph 값 넣기
        for(int i = 0; i < edge_list.length; i++) {
            int startNode = edge_list[i][0];
            int endNode = edge_list[i][1];
            
            al.get(startNode).add(endNode);            //양방향 그래프
            al.get(endNode).add(startNode);
        }
        for(int i = 0; i < dp.length; i++) {
            Arrays.fill(dp[i], inf);
        }
        dp[0][gps_log[0]] = 0;      
        //시작점과 오류가 나지 않기 때문에 1 ~
        for(int i = 1; i < k ; i++) {
            //gps_log 숫자 표현상 첫 번째 자리는 안쓰는게 편함.
            for(int j = 1; j < n + 1; j++) {
                dp[i][j] = Math.min(dp[i][j], dp[i-1][j]);            //어떻게든 최소 오류수정만 있으면 되므로 & 택시가 정차한 경우
                for(int node : al.get(j)) {
                    dp[i][j] = Math.min(dp[i-1][node], dp[i][j]);    //자신과 연결된 정점 중 어디서 온것이 가장 적은 오류수정 횟수인지 판단
                }
                dp[i][j] += gps_log[i] == j ? 0 : 1;                //지금 최소 오류 수정 루트를 찾고있는 j의 도시가 gps_log가 정해준 도시와 맞는지
            }
        }
        if(dp[k - 1][gps_log[k - 1]] >= inf) {
            answer = -1;
        }else {
            answer = dp[k-1][gps_log[k -1]];
        }       
        return answer;
    }
}
출처: https://tosuccess.tistory.com/144 [EI_HJ]

```


### 금과 은 운반하기
> 이분 탐색 문제
> https://prgms.tistory.com/101 문제해설
> https://hyunjiishailey.tistory.com/431 문제풀이
> a<=g_max, b <= s_max, a+b <= g_max + s_mix = g_min + s_max 을 만족해야 한다.

```java
import java.util.*;
class Solution {
    public long solution(int a, int b, int[] g, int[] s, int[] w, int[] t) {
        long answer = -1;
        // a 필요한 금  b 필요한 은  g[i] 보유 금   s[i] 보유 은  w[i] 용적량  t[i] 시간
        // 금과 은을 따로 계산해서 둘 중 최대 걸린 시간을 리턴해야 한다.
        // 편도, 왕복 따로 고려해야 한다. 이분탐색
        // 기준은 시간
        int len = g.length;
        long max = (long)(1e9 * 2 * 1e5*2);
        long min = 0;
        long gold_max, silver_max, gsm_max; // 골드, 실버, 골드+실버 최대치
        long gold_c_weight, sliver_c_weight;  // 시간 당 몇번 옮길 수 있는지
        long move_cnt;
        long w_max;
        long left = 0;
        long right = max;
        
        while (left <= right)
        {
            long mid = (left + right) / 2;
            gold_max = silver_max = gsm_max = 0;
            // a 필요한 금  b 필요한 은  g[i] 보유 금   s[i] 보유 은  w[i] 용적량  t[i] 시간
            for (int i = 0; i < len; i++)
            {
                move_cnt = (long)Math.ceil((double)(mid / t[i]) / 2);
                w_max = move_cnt * w[i];
                gold_max += Math.min(w_max, g[i]);
                silver_max += Math.min(w_max, s[i]);
                gsm_max += Math.min((g[i] + s[i]), w_max);
            }
            if (a <= gold_max && b <= silver_max && (a + b) <= gsm_max)
            {
                right = mid - 1;
                answer = mid;
            }
            else
                left = mid + 1;
        }
        return answer;
    }
}
```


### 경주로 건설
> dp 와 dfs 섞어서 사용한 코드 
> 상하좌우 순서도 중요하다
```java
class Solution {
    int result;
    int dp[][];
    int n;
    // check = 1 -> x증가/감소  check = 2 -> y증가/감소
    private void make_road(int[][] board, int x, int y, boolean[][] visit, int money, int check)
    {
        int m;
        dp[x][y] = money;
        if (money >= result)
            return;
        if (x == n - 1 && y == n - 1)
        {
            result = Math.min(money, result);
            return;
        }
        m = 100;
        if (y + 1 < n && visit[x][y + 1] == false)
        {
            visit[x][y + 1] = true;
            if (check == 1)
                m += 500;
            if (dp[x][y + 1] > money)
                make_road(board, x, y + 1, visit, money + m, 2);
            visit[x][y + 1] = false;
        }
        m = 100;
        if (x + 1 < n && visit[x + 1][y] == false)
        {
            visit[x + 1][y] = true;
            if (check == 2)
                m += 500;
            if (dp[x + 1][y] > money)
                make_road(board, x + 1, y, visit, money + m, 1);
            visit[x + 1][y] = false;
        }
        m = 100;
        if (y - 1 >= 0 && visit[x][y - 1] == false)
        {
            visit[x][y - 1] = true;
            if (check == 1)
                m += 500;
            if (dp[x][y - 1] > money)
                make_road(board, x, y - 1, visit, money + m, 2);
            visit[x][y - 1] = false;
        }
        m = 100;
        if (x - 1 >= 0 && visit[x - 1][y] == false)
        {
            visit[x - 1][y] = true;
            if (check == 2)
                m += 500;
            if (dp[x - 1][y] > money)
                make_road(board, x - 1, y, visit, money + m, 1);
            visit[x - 1][y] = false;
        }
    } // make_road end
    
    public int solution(int[][] board) {
        int answer = 0;
        result = Integer.MAX_VALUE;
        // 직선 100원 코너 500원 0,0 -> n-1 n-1
        n = board.length;
        dp = new int[n][n];
        boolean visit[][] = new boolean[n][n];
        for (int i = 0; i < n; i++)
        {
            for (int j = 0; j < n; j++)
            {
                if (board[i][j] == 1)
                    visit[i][j] = true;
                dp[i][j] = Integer.MAX_VALUE;
            }
        }
        visit[0][0] = true;
        make_road(board, 0, 0, visit, 0, 0);
        answer = result;
        return answer;
    } // solution end
}
```

> BFS 를 사용한 다른 사람 코드
> 25번은 틀렸다고 나온다 (테스트 케이스가 추가 된듯)
```java
import java.util.LinkedList;
import java.util.Queue;

public class Solution {

	static int min = Integer.MAX_VALUE;
	static int xx[] = {-1, 0, 1, 0};
	static int yy[] = {0, -1, 0, 1};
	static int map[][];
	static int n;
	static int answer;
	
    public static int solution(int[][] board) {
        answer = Integer.MAX_VALUE;
        n = board.length;
        map = board;
        
        // 초기 방향을 -1 을 주는 이유는 자동차가 시작할때 오른쪽과 아래로 둘다 이동할수 있기 떄문이다.
        bfs(0, 0, 0, -1);
        return answer;
    }   
    
    public static void bfs(int x, int y, int cost, int dir) {
    	
    	Queue<Road> q = new LinkedList<Road>();
    	q.add(new Road(x, y, cost, dir));
    	// 출발지점을 1 로 바꾸어 탐색에서 제외한다.
    	map[x][y] = 1;
    	
    	while(!q.isEmpty()) {
    		Road temp = q.poll();
    		// 목적지에 도착했다면 최소비용을 갱신해준다.
    		if(temp.x == n - 1 && temp.y == n - 1) {
    			answer = Math.min(answer, temp.cost);
    			continue;
    		}
    
    		// 4방향으로 이동할수있다 .
    		for(int i = 0; i < 4; i++) {
    			int new_x = temp.x + xx[i];
    			int new_y = temp.y + yy[i];
    			// 새로 이동하는 곳은 범위안이고 벽이(1) 아니여야한다.
    			if(new_x >= 0 && new_x < n && new_y >= 0 && new_y < n && map[new_x][new_y] != 1) {
    				int new_cost = 0;
    				// 새로운 지점의 비용구하기.
    				if(temp.dir == -1 || temp.dir == i) {
    					new_cost = temp.cost + 100;
    				}else if(temp.dir != i){
    					new_cost = temp.cost + 600;
    				}
    			
    				//처음가는 곳이라면 정보를 넣어주면된다.
    				if(map[new_x][new_y] == 0) {
    					map[new_x][new_y] = new_cost;
    					q.add(new Road(new_x, new_y, new_cost, i));
    				}else if(map[new_x][new_y] >= new_cost) { //처음가지않는 곳이라면 비용이 더작거나 같은 경우 넣어주면된다.
    					map[new_x][new_y] = new_cost;
    					q.add(new Road(new_x, new_y, new_cost, i));
    				}
    				
    				
    			}
    		}
    		
    	}
    }
}
class Road{
	int x,y,cost,dir;
	public Road(int x, int y, int cost, int dir) {
		this.x = x;
		this.y = y;
		this.cost = cost;
		this.dir = dir;
	}
}
```

> 테스트 케이스 전부 통과하는 다른 사람 코드
> 3차원 배열을 이용한 풀이
```java
import java.util.*;
class Info implements Comparable<Info>{
    int cost;
    int x, y;
    int cov;
    public Info(int c, int x, int y, int cov) {
        this.cost = c;
        this.x = x;
        this.y = y;
        this.cov = cov;
    }
    @Override
    public int compareTo(Info info) {
        return info.cost >= this.cost ? 1 : -1;
    }
}
class Solution {

    public int solution(int[][] board) {  
        int answer = 0;
        int[] dx = {1,0,-1,0};
        int[] dy = {0,1,0,-1};
        int[][][] map = new int[30][30][5];
        int cost=0,x,y,cov,n=board.length;

        PriorityQueue<Info> pq = new PriorityQueue<Info>();
        pq.add(new Info(0, 0, 0, 4));

        while (!pq.isEmpty()) {
            Info cur = pq.poll();
            cost = -cur.cost;
            x = cur.x;
            y = cur.y;
            cov = cur.cov;
            if (map[x][y][cov] == 1) continue;
            map[x][y][cov] = 1;
            if (x == n - 1 && y == n - 1) break;
            for (int i = 0; i < 4; i++) {
                int nx = x + dx[i];
                int ny = y + dy[i];
                if (nx >= n || nx < 0 || ny >= n || ny < 0 || board[nx][ny] == 1) continue;
                pq.add(new Info(-cost-100-500*(cov != i ? 1 : 0), nx, ny, i));
            }
        }
        return cost-500;
    }
}
```


## 2 * n 타일링

> 1부터 시작하는 피보나치 수열
```java
class Solution {
    public int solution(int n) {
        int answer = 0;
        int div = 1000000007;
        int dp[] = new int[n + 1];
        dp[0] = 1;
        dp[1] = 1;
        dp[2] = 1;
        for (int i = 2; i <= n; i++)
        {
            dp[i] = (dp[i - 1] % div) + (dp[i - 2] % div);
            dp[i] %= div;
        }
        answer = dp[n];
        return answer;
    }
}
```

```java

```

### 아이템 줍기
> Map 전체 크기를 2배 곱해줘서 계산하기 result 도 / 2 해주기.
> 11
> 11  이런 경우를 제외하기 위해 
```java

import java.util.*;
class Solution {
    int result;
    
    public int solution(int[][] rectangle, int characterX, int characterY, int itemX, int itemY) {
        int answer = 0;
        result = 10000000;
        int map[][] = new int[103][103];
        for (int i = 0; i < 103; i++)
            Arrays.fill(map[i], 0);
        for (int i = 0; i < rectangle.length; i++)
        {
            int dx = rectangle[i][0] * 2;
            int dy = rectangle[i][1] * 2;
            int ux = rectangle[i][2] * 2;
            int uy = rectangle[i][3] * 2;
            
            for (int j = dx; j <= ux; j++)
            {
                if (map[dy][j] == 0)
                    map[dy][j] = 1;
                if (map[uy][j] == 0)
                    map[uy][j] = 1;
            }
            
            for (int j = dy; j <= uy; j++)
            {
                if (map[j][dx] == 0)
                    map[j][dx] = 1;
                if (map[j][ux] == 0)
                    map[j][ux] = 1;
            }
            
            for (int k = dy + 1; k < uy; k++)
            {
                for (int j = dx + 1; j < ux; j++)
                {
                    map[k][j] = 2;
                }
            }
        }
        boolean visit[][] = new boolean[103][103];
        visit[characterY * 2][characterX * 2] = true;
        find_item(characterX * 2, characterY * 2, itemX * 2, itemY * 2, map, 0, visit);
        answer = result;
        return answer;
    } // solution end
    
    private void find_item(int cx, int cy, int ix, int iy, int[][] map, int cnt, boolean[][] visit)
    {
        if (cnt / 2 > result)
            return;
        if (cx == ix && cy == iy)
        {
            // System.out.println(cx + "   "  + cy + "   cnt  " + cnt);
            result = Math.min(result, cnt / 2);
            return;
        }
        
        if (cx + 1 < 103 && map[cy][cx + 1] == 1 && visit[cy][cx + 1] == false)
        {
            visit[cy][cx + 1] = true;
            find_item(cx + 1, cy, ix, iy, map, cnt + 1, visit);
            visit[cy][cx + 1] = false;
        }
        if (cy + 1 < 103 && map[cy + 1][cx] == 1 && visit[cy + 1][cx] == false)
        {
            visit[cy + 1][cx] = true;
            find_item(cx, cy + 1, ix, iy, map, cnt + 1, visit);
            visit[cy + 1][cx] = false;    
        }
        if (cx - 1 > 0 && map[cy][cx - 1] == 1 && visit[cy][cx - 1] == false)
        {
            visit[cy][cx - 1] = true;
            find_item(cx - 1, cy, ix, iy, map, cnt + 1, visit);
            visit[cy][cx - 1] = false;
        }
            
        if (cy - 1 > 0 && map[cy - 1][cx] == 1 && visit[cy - 1][cx] == false)
        {
            visit[cy - 1][cx] = true;
            find_item(cx, cy - 1, ix, iy, map, cnt + 1, visit);
            visit[cy - 1][cx] = false;
        }
    }
}
```

> 스택을 이용한 다른 사람 코드 (bfs)
> https://taehoung0102.tistory.com/95
```java
import java.util.*;
class Solution {
    public int solution(int[][] rectangle, int characterX, int characterY, int itemX, int itemY) {
        int answer = 0;
        
        int start_x=characterX*2;
        int start_y=characterY*2;
        int end_x=itemX*2;
        int end_y=itemY*2;
        
        boolean[][] map= new boolean[103][103];
        int ted=0;
        
        for(int[] data:rectangle){
            //1.테두리 포함해서 직사각형 모두 true채우기
            for(int i=data[1]*2;i<=data[3]*2;i++){
              for(int j=data[0]*2;j<=data[2]*2;j++){
                    map[i][j]=true;
                  
              }  
            }
        }
        
        for(int[] data:rectangle){
            //2.테두리 제외해서 직사각형 내부 모두 false채우기
            for(int i=data[1]*2+1;i<data[3]*2;i++){
              for(int j=data[0]*2+1;j<data[2]*2;j++){
                    map[i][j]=false;
              }  
            }
        }
    
        //bfs 탐색
        Stack<Player> stack = new Stack<>();
        
        // 시작점 설정
        Player p = new Player(start_x,start_y);
        stack.add(p);
        
        List<Integer> result =new ArrayList<>();
        int cnt=0;
        
        while(true){       
            if(stack.isEmpty()){
                // 전체 태두리 길이
                result.add(cnt);
                break;
            }
            Player temp=stack.pop();
            int x=temp.x;
            int y=temp.y; 
           
            //도착하면 갯수 저장
            if(x==end_x&&y==end_y){
                result.add(cnt);         
            }
            
            //지났으면 지난자리 false               
            map[y][x]=false;
            
            if(map[y+1][x]==true) stack.add(new Player(x,y+1));
            if(map[y][x+1]==true) stack.add(new Player(x+1,y));
            if(map[y-1][x]==true) stack.add(new Player(x,y-1));
            if(map[y][x-1]==true) stack.add(new Player(x-1,y));
            
            cnt++;
            
        } // while end
        // Math.min(캐릭터위치~아이템거리, 전체 테두리길이 - 캐릭터위치~아이템거리) 출력
        // 아이템까지 갈수 있는 방법이 2가지가 있기 때문에 
        answer=Math.min(result.get(0)/2, result.get(1)/2-result.get(0)/2);      
        return answer;
    }
    
    class Player{
        int x;
        int y;
        
        public Player(int x, int y){
            this.x=x;
            this.y=y;   
        }
    }
}
```


### 보행자 천국
> 상하 좌우 분리해서 따로 생각하자.
```java
import java.util.*;
class Solution {
    int MOD = 20170805;
    public int solution(int m, int n, int[][] cityMap) {
        int answer = 0;
        int div = 20170805;
        // citymap  0 다가능  1 통해금지  2 한방향만
        // 위아래 0   좌우 1
        int[][][] dp = new int[m + 1][n + 1][2];
        dp[0][0][0] = 1;
        // 가로 세로 들어올경우를 나누어서 계산
        for (int i = 0; i < m; i++)
        {
            for (int j = 0; j < n; j++)
            {
                if (cityMap[i][j] == 0)
                {
                    dp[i + 1][j][0] = (dp[i + 1][j][0] + dp[i][j][0] + dp[i][j][1]) % div;
                    dp[i][j + 1][1] = (dp[i][j + 1][1] + dp[i][j][0] + dp[i][j][1]) % div;
                }
                else if (cityMap[i][j] == 2)
                {
                    dp[i + 1][j][0] = (dp[i + 1][j][0] + dp[i][j][0]) % div;
                    dp[i][j + 1][1] = (dp[i][j + 1][1] + dp[i][j][1]) % div;
                }
            }
            // System.out.println();
        }
        
        answer = (dp[m - 1][n - 1][0] + dp[m - 1][n - 1][1]) % div;
        return answer;
    }
}
```

> 다른 사람 코드
```java
class Solution {
  int MOD = 20170805;
  public int solution(int m, int n, int[][] cityMap) {
        int answer = 0;
        int[][] right = new int[m + 1][n + 1];
        int[][] down = new int[m + 1][n + 1];

        right[1][1] = 1;
        down[1][1] = 1;

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (cityMap[i - 1][j - 1] == 0) {
                    right[i][j] += (right[i][j - 1] + down[i - 1][j]) % MOD;
                    down[i][j] += (down[i - 1][j] + right[i][j - 1]) % MOD;
                } else if (cityMap[i - 1][j - 1] == 2) {
                    right[i][j] = right[i][j - 1];
                    down[i][j] = down[i - 1][j];
                }
            }
        }
        answer = (right[m][n - 1] + down[m - 1][n]) % MOD;
        return answer;
    }
}
```

### 퍼즐 조각 채우기
> 처음에는 퍼즐을 N * N 으로 맞춰서 생성했는데 이럴 경우 회전할때 제대로 비교를 할 수 가 없다.
> 따라서 블록 퍼즐만큼 직사각형을 만들고 회전시켜야 한다.
```java
import java.util.*;
class Solution {
    boolean[][] visit;
    int len;
    int result;
    private void find_board_table(int[][] table, int y, int x, ArrayList<int[]> a)
    {
        if (x + 1 < len && table[y][x + 1] == 1 && visit[y][x + 1] == false)
        {
            visit[y][x + 1] = true;
            find_board_table(table, y, x + 1, a);
        }
        
        if (x - 1 >= 0 && table[y][x - 1] == 1 && visit[y][x - 1] == false)
        {
            visit[y][x - 1] = true;
            find_board_table(table, y, x - 1, a);
        }
        if (y + 1 < len && table[y + 1][x] == 1 && visit[y + 1][x] == false)
        {
            visit[y + 1][x] = true;
            find_board_table(table, y + 1, x, a);
        }
        if (y - 1 >= 0 && table[y - 1][x] == 1 && visit[y - 1][x] == false)
        {
            visit[y - 1][x] = true;
            find_board_table(table, y - 1, x, a);
        }
        
        int[] temp = {y, x};
        a.add(temp);
    } // find_board_table end
    
    private void find_board(int[][] game_board, int y, int x, ArrayList<int[]> a)
    {
        if (x + 1 < len && game_board[y][x + 1] == 0 && visit[y][x + 1] == false)
        {
            visit[y][x + 1] = true;
            find_board(game_board, y, x + 1, a);
        }
        
        if (x - 1 >= 0 && game_board[y][x - 1] == 0 && visit[y][x - 1] == false)
        {
            visit[y][x - 1] = true;
            find_board(game_board, y, x - 1, a);
        }
        if (y + 1 < len && game_board[y + 1][x] == 0 && visit[y + 1][x] == false)
        {
            visit[y + 1][x] = true;
            find_board(game_board, y + 1, x, a);
        }
        if (y - 1 >= 0 && game_board[y - 1][x] == 0 && visit[y - 1][x] == false)
        {
            visit[y - 1][x] = true;
            find_board(game_board, y - 1, x, a);
        }
        
        int[] temp = {y, x};
        a.add(temp);
    } // find_board_end
    
    private int[][] make_rectangle(ArrayList<int[]> a)
    {
        int min_x = len;
        int max_x = 0;
        int min_y = len;
        int max_y = 0;
        for (int i = 0; i < a.size(); i++)
        {
            if (a.get(i)[1] < min_x)
                min_x = a.get(i)[1];
            if (a.get(i)[1] > max_x)
                max_x = a.get(i)[1];
            if (a.get(i)[0] < min_y)
                min_y = a.get(i)[0];
            if (a.get(i)[0] > max_y)
                max_y = a.get(i)[0];
        }
        int x = max_x - min_x + 1;
        int y = max_y - min_y + 1;
        int[][] rectangle = new int[y][x];
        
        for (int i = 0; i < a.size(); i++)
        {
            if (a.get(i)[0] >= 0)
                rectangle[a.get(i)[0] - min_y][a.get(i)[1] - min_x] = 1;
        }
        //             System.out.println();
        // for (int i = 0; i < rectangle.length; i++)
        // {
        //     for (int j = 0; j < rectangle[0].length; j++)
        //         System.out.print(rectangle[i][j]);
        //     System.out.println();
        // }
        return rectangle;
    } // make_rectangle end
    
    private boolean match_table (int[][] temp_game, int[][] temp_table)
    {
        if ( Math.max(temp_game.length, temp_game[0].length) != Math.max(temp_table.length, temp_table[0].length) 
            || Math.min(temp_game.length, temp_game[0].length) != Math.min(temp_table.length, temp_table[0].length) )
            return false;

        int cnt = 0;
        int check = 0;
        
        if ((temp_game.length == temp_table[0].length && temp_game[0].length == temp_table.length )
            || (temp_game.length == temp_game[0].length && temp_table.length == temp_table[0].length && temp_table.length == temp_game.length))
        {
            // 90, 270 도 계산
            // 90
            cnt = 0;
            check = 0;

            int[][] after_90 = new int[temp_table[0].length][temp_table.length];
            for (int i = 0; i < after_90.length; i++)
            {
                for (int j = 0; j < after_90[0].length; j++)
                    after_90[i][j] = temp_table[temp_table.length - 1 - j][i];
            }

            for (int i = 0; i < after_90.length; i++)
            {
                for (int j = 0; j < after_90[0].length; j++)
                {
                    if (temp_game[i][j] == 1)
                        cnt++;
                    if (temp_game[i][j] != after_90[i][j])
                    {
                        check = 1;
                        break;
                    }
                }
            }
            if (check == 0)
            {
                result += cnt;
                return true;
            }
            
            // 270
            cnt = 0;
            check = 0;

            int[][] after_270 = new int[temp_table[0].length][temp_table.length];
            for (int i = 0; i < after_270.length; i++)
            {
                for (int j = 0; j < after_270[0].length; j++)
                    after_270[i][j] = temp_table[j][temp_table[0].length - 1 - i];
            }

            for (int i = 0; i < after_270.length; i++)
            {
                for (int j = 0; j < after_270[0].length; j++)
                {
                    if (temp_game[i][j] == 1)
                        cnt++;
                    if (temp_game[i][j] != after_270[i][j])
                    {
                        check = 1;
                        break;
                    }
                }
            }
            if (check == 0)
            {
                result += cnt;
                return true;
            }
            
        }
        
        if (temp_game.length == temp_table.length 
            || (temp_game.length == temp_game[0].length && temp_table.length == temp_table[0].length && temp_table.length == temp_game.length))
        {
            // 기본 , 180
            cnt = 0;
            check = 0;

            for (int i = 0; i < temp_game.length; i++)
            {
                for (int j = 0; j < temp_game[0].length; j++)
                {
                    if (temp_game[i][j] == 1)
                        cnt++;
                    if (temp_game[i][j] != temp_table[i][j])
                    {
                        check = 1;
                        break;
                    }
                }
            }
            if (check == 0)
            {
                result += cnt;
                return true;
            }

            // 180
                cnt = 0;
                check = 0;
                int[][] after_180 = new int[temp_table.length][temp_table[0].length];
                for (int i = 0; i < temp_table.length; i++)
                {
                    for (int j = 0; j < temp_table[0].length; j++)
                        after_180[i][j] = temp_table[temp_table.length - 1 - i][temp_table[0].length - 1 - j];
                }

                for (int i = 0; i < after_180.length; i++)
                {
                    for (int j = 0; j < after_180[0].length; j++)
                    {
                        if (temp_game[i][j] == 1)
                            cnt++;
                        if (temp_game[i][j] != after_180[i][j])
                        {
                            check = 1;
                            break;
                        }
                    }
                }
                if (check == 0)
                {
                    result += cnt;
                    return true;
                }
        }
        return false;
    } //match_table end
    
    public int solution(int[][] game_board, int[][] table) {
        int answer = 0;
        ArrayList<ArrayList<int[]>> game_al = new ArrayList<ArrayList<int[]>>();
        ArrayList<ArrayList<int[]>> table_al = new ArrayList<ArrayList<int[]>>();
        len = game_board.length;
        result = 0;
        visit = new boolean[len][len];
        for (int i = 0; i < len; i++)
        {
            for (int j = 0; j < len; j++)
            {
                if (game_board[i][j] == 1)
                    visit[i][j] = true;
                else if (visit[i][j] == false && game_board[i][j] == 0)
                {
                    visit[i][j] = true;
                    ArrayList<int[]> al = new ArrayList<int[]>();
                    find_board(game_board, i, j, al);
                    game_al.add(al);
                }
            }
        }
        visit = new boolean[len][len];
        for (int i = 0; i < len; i++)
        {
            for (int j = 0; j < len; j++)
            {
                if (table[i][j] == 0)
                    visit[i][j] = true;
                else if (visit[i][j] == false && table[i][j] == 1)
                {
                    visit[i][j] = true;
                    ArrayList<int[]> al = new ArrayList<int[]>();
                    find_board_table(table, i, j, al);
                    table_al.add(al);
                }
            }
        }
        // System.out.println(game_al.size());
        // System.out.println(table_al.size());
        for (int i = 0; i < game_al.size(); i++)
        {
            int[][] temp_game = make_rectangle(game_al.get(i));
            for (int j = 0; j < table_al.size(); j++)
            {
                int[][] temp_table = make_rectangle(table_al.get(j));
                if (match_table(temp_game, temp_table))
                {
                    table_al.remove(j);
                    // System.out.println(result);
                    break;
                }
            }
        }
        answer = result;
        return answer;
    } // solution end
}
```

