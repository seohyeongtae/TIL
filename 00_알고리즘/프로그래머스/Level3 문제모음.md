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