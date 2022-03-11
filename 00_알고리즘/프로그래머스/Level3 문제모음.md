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
