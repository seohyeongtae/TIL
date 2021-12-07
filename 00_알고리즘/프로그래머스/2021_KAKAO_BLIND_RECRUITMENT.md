## 2021_KAKAO_BLIND_RECRUITMENT

### 신규 아이디 추천 (1번)

```java
class Solution {
    public String solution(String new_id) {
        String answer = "";
        new_id = new_id.toLowerCase();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < new_id.length(); i++)
        {
            char t = new_id.charAt(i);
            if ((t >= 'a' && t <= 'z') || (t >= '0' && t <= '9') 
                || t == '-' || t == '_' || t == '.')
                sb.append(t);
        }
        new_id = sb.toString();
        sb = new StringBuilder();
        for (int i = 0; i < new_id.length(); i++)
        {
            if (new_id.charAt(i) == '.')
            {
                int temp = i;
                while(new_id.charAt(i) == '.' && i < new_id.length() - 1)
                    i++;
                if (temp != 0 && i < new_id.length() && new_id.charAt(i) != '.')
                    sb.append('.');
            }
            sb.append(new_id.charAt(i));
        }
        new_id = sb.toString();
        if (new_id.length() > 15)
           new_id = new_id.substring(0, 15);
        if (new_id.charAt(new_id.length() - 1) == '.')
            new_id = new_id.substring(0, new_id.length() - 1);
        if (new_id.length() == 0)
            answer = "aaa";
        else if (new_id.length() < 3)
        {
            sb = new StringBuilder();
            for (int i = 0; i < new_id.length(); i++)
                sb.append(new_id.charAt(i));
            while (sb.length() < 3)
                sb.append(new_id.charAt(new_id.length() - 1));
            answer = sb.toString();
        }
        else
            answer = new_id;
        
        //System.out.println(new_id);
        return answer;
    }
}
```

> 다른 사람 코드 replaceAll 및 정규식 사용

```java
class Solution {
    public String solution(String new_id) {
        String answer = "";
        String temp = new_id.toLowerCase();

        temp = temp.replaceAll("[^-_.a-z0-9]","");
        System.out.println(temp);
        temp = temp.replaceAll("[.]{2,}",".");
        temp = temp.replaceAll("^[.]|[.]$","");
        System.out.println(temp.length());
        if(temp.equals(""))
            temp+="a";
        if(temp.length() >=16){
            temp = temp.substring(0,15);
            temp=temp.replaceAll("^[.]|[.]$","");
        }
        if(temp.length()<=2)
            while(temp.length()<3)
                temp+=temp.charAt(temp.length()-1);

        answer=temp;
        return answer;
    }
}
```

### 메뉴 리뉴얼 (2번)

```java
import java.util.*;
class Solution {
    static HashMap<String, Integer> hs;
    static boolean[] visit;
    int max;

    public String[] solution(String[] orders, int[] course) {
        HashSet<String> arr = new HashSet<String>();

        for (int i = 0; i < orders.length; i++)
             orders[i] = convert_order(orders[i]);

        for (int i = 0; i < course.length; i++)
        {
            hs = new HashMap<String, Integer>(); 
            max = 0;
            for (int j = 0; j < orders.length; j++)
            {
                visit = new boolean[orders[j].length()];
                if (course[i] <= orders[j].length())
                    make_hs(course[i], orders[j], "", 0);
            }
            // System.out.println(hs.keySet());
            // System.out.println(hs.values());
            // System.out.println(max);
            if (max > 1)
            {
                for (String v : hs.keySet())
                {
                    if ((int) hs.get(v) == max)
                        arr.add(v);
                }
            }
        } // for end

        String[] answer = {};
        if (arr.size() > 0)
        {
            answer = new String[arr.size()];
            int a = 0;
            for (String v : arr)
            {
                answer[a] = v;
                a++;
            }
            Arrays.sort(answer);
        }

        return answer;
    } // solution end

    private String convert_order(String orders)
    {
        String convert = "";
        char[] temp = new char[orders.length()];

        for (int i = 0; i < orders.length(); i++)
            temp[i] = orders.charAt(i);
        Arrays.sort(temp);
        for (int i = 0; i < orders.length(); i++)
            convert += temp[i];
        return convert;
    } // convert_order end

    private void make_hs(int course, String orders, String temp, int depth)
    {
        if (temp.length() == course)
        {
            hs.put(temp, (int) hs.getOrDefault(temp, 0) + 1);
            max = Math.max(max, (int) hs.get(temp));
            return;
        }

        for (int i = depth; i < orders.length(); i++)
        {
            if (visit[i] == false)
            {
                visit[i] = true;
                temp = temp + orders.charAt(i);
                make_hs(course, orders, temp, i + 1);
                visit[i] = false;
                temp = temp.substring(0, temp.length() - 1);
            }
        }
        return;
    } // make_hs end

}
```

> 다른 사람 코드

```java
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Arrays;
class Solution {
       static HashMap<String,Integer> map;
    static int m;
    public String[] solution(String[] orders, int[] course) {
       PriorityQueue<String> pq = new PriorityQueue<>();
        for (int i=0;i<course.length;i++){
            map = new HashMap<>();
            m=0;
            for (int j=0;j<orders.length;j++) {
                find(0, "", course[i], 0, orders[j]);
            }
            for (String s : map.keySet()){
                if (map.get(s)==m&&m>1){
                    pq.offer(s);
                }
            }
        }
        String  ans[] = new String[pq.size()];
        int k=0;
        while (!pq.isEmpty()){
            ans[k++] = pq.poll();
        }
        return ans;
    }
    static void find(int cnt,String str,int targetNum,int idx,String word){
        if (cnt==targetNum){
            char[] c = str.toCharArray();
            Arrays.sort(c);
            String temps="";
            for (int i=0;i<c.length;i++)temps+=c[i];
            map.put(temps,map.getOrDefault(temps,0)+1);
            m = Math.max(m,map.get(temps));
            return;
        }
        for (int i=idx;i<word.length();i++){
            char now =word.charAt(i);
            find(cnt+1,str+now,targetNum,i+1,word);
        }
    }
}
```



### 순위 검색 (3번)

> 효율성에서 실패한다. 
>
> https://tech.kakao.com/2021/01/25/2021-kakao-recruitment-round-1/ 
>
> -> 문제 해설 및 풀이 링크

```java
class Solution {
    public int[] solution(String[] info, String[] query) {
       
        String[] lang = new String[info.length];
        String[] job = new String[info.length];
        String[] career = new String[info.length];
        String[] food = new String[info.length];
        String[] score = new String[info.length];
        
        for (int i = 0; i < info.length; i++)
        {
            String[] temp = new String[5];
            temp = info[i].split(" ");
            lang[i] = temp[0];
            job[i] = temp[1];
            career[i] = temp[2];
            food[i] = temp[3];
            score[i] = temp[4];
        }
        
        int[] answer = new int[query.length];
        int a = 0;
        for (int i = 0; i < query.length; i++)
        {
            query[i] = query[i].replaceAll("and","");
            query[i] = query[i].replaceAll("  "," ");
            //System.out.println(query[i]);
            String[] temp = query[i].split(" ");
            //System.out.println(temp.length);
            for (int j = 0; j < info.length; j++)
            {
                if ((lang[j].equals(temp[0]) || temp[0].equals("-")) &&
                   (job[j].equals(temp[1]) || temp[1].equals("-")) &&
                   (career[j].equals(temp[2]) || temp[2].equals("-")) &&
                   (food[j].equals(temp[3]) || temp[3].equals("-")))
                {
                    int t1 = change_score(score[j]);
                    int t2 = change_score(temp[4]);;
                    if (t1 >= t2)
                        answer[a] += 1;
                }
                
            } // big for end
            a++;
        }
        return answer;
    } // solution end
    
    private int change_score(String t)
    {
        int a = 0;
        for (int i = 0; i < t.length(); i++)
        {
            a *= 10;
            a += t.charAt(i) - '0';
        }
        return a;
    }
}
```

> 다른사람 코드 1

```java
import java.util.*;

class Solution {
    static Map<String, ArrayList<Integer>> allInfo;
    static ArrayList<Integer> in;
    public int[] solution(String[] info, String[] query) {
       
        int[] answer = new int[query.length];
        allInfo = new HashMap<>();
        
        // 1. info 모든 경우의 수 map에 저장 
        for(int i=0; i<info.length; i++) {
        	dfs("",0, info[i].split(" "));
        }
        
        // 2. map에 저장된 점수 list 오름차순으로 정렬 	
        List<String> list = new ArrayList<>(allInfo.keySet());
        for(int i=0; i<list.size(); i++) {
        	List<Integer> scoreList = allInfo.get(list.get(i));
        	Collections.sort(scoreList);
        }
        
        for(int i=0; i<query.length; i++) {
        	query[i] = query[i].replaceAll(" and ", "");
        	String[] str = query[i].split(" ");
        	int score = Integer.parseInt(str[1]);

        	answer[i] = search(str[0], score);
        }
        return answer;
    }
	
	
	static void dfs(String pos, int depth, String[] info) {
		
		if(depth == 4) {
			if(!allInfo.containsKey(pos)) {
				in = new ArrayList<>();
				in.add(Integer.parseInt(info[4]));
				allInfo.put(pos, in);
			}else {
				allInfo.get(pos).add(Integer.parseInt(info[4]));
			}
			return;
		}
		dfs(pos+"-", depth+1, info);
		dfs(pos+info[depth], depth+1, info);
		
	}
	
	// 이분 탐색 
	static int search(String str, int score) {
		if(!allInfo.containsKey(str)) return 0;
		List<Integer> scoreList = allInfo.get(str); 
		int start= 0, end = scoreList.size()-1;
		while(start<=end) {
    
			int mid =(start+end)/2;
			if(scoreList.get(mid) <score) {
				start = mid+1;	
			}else {
				end = mid-1;
			}
		}
		return scoreList.size()-start;
	}
}
```

> 다른사람 코드 2

```java
import java.util.HashMap;
import java.util.ArrayList;

class Solution {
    public int[] solution(String[] info, String[] query) {
        String[] langs = {"cpp", "java", "python"},
                 deps = {"backend", "frontend"},
                 exps = {"junior", "senior"},
                 foods = {"chicken", "pizza"};

        HashMap<String, HashMap<String, HashMap<String, HashMap<String, ArrayList<Integer>>>>> langMap = new HashMap();
        for (String lang : langs)
            langMap.put(lang, new HashMap<>());
        for (String lang : langMap.keySet()) {
            HashMap<String, HashMap<String, HashMap<String, ArrayList<Integer>>>> depMap = langMap.get(lang);
            for (String dep : deps)
                depMap.put(dep, new HashMap<>());
            for (String dep : depMap.keySet()) {
                HashMap<String, HashMap<String, ArrayList<Integer>>> expMap = depMap.get(dep);
                for (String exp : exps)
                    expMap.put(exp, new HashMap<>());
                for (String exp : expMap.keySet()) {
                    HashMap<String, ArrayList<Integer>> foodMap = expMap.get(exp);
                    for (String food : foods)
                        foodMap.put(food, new ArrayList<Integer>());
                }
            }
        }

        for (String row : info) {
            String[] parse = row.split(" ");
            langMap.get(parse[0]).get(parse[1]).get(parse[2]).get(parse[3]).add(Integer.parseInt(parse[4]));
        }

        for (HashMap<String, HashMap<String, HashMap<String, ArrayList<Integer>>>> depMap : langMap.values())
            for (HashMap<String, HashMap<String, ArrayList<Integer>>> expMap : depMap.values())
                for (HashMap<String, ArrayList<Integer>> foodMap : expMap.values())
                    for (ArrayList<Integer> scoreList : foodMap.values())
                        scoreList.sort(null);

        int[] answer = new int[query.length];
        int i = 0;
        for (String quest : query) {
            String[] parse = quest.split(" and "),
                     subParse = parse[3].split(" ");
            parse[3] = subParse[0];
            int minScore = Integer.parseInt(subParse[1]);

            for (String lang : langs)
                if (parse[0].equals("-") || parse[0].equals(lang)) {
                    HashMap<String, HashMap<String, HashMap<String, ArrayList<Integer>>>> depMap = langMap.get(lang);
                    for (String dep : deps)
                        if (parse[1].equals("-") || parse[1].equals(dep)) {
                            HashMap<String, HashMap<String, ArrayList<Integer>>> expMap = depMap.get(dep);
                            for (String exp : exps)
                                if (parse[2].equals("-") || parse[2].equals(exp)) {
                                    HashMap<String, ArrayList<Integer>> foodMap = expMap.get(exp);
                                    for (String food : foods)
                                        if (parse[3].equals("-") || parse[3].equals(food)) {
                                            ArrayList<Integer> scoreList = foodMap.get(food);
                                            int n = scoreList.size(),
                                                min = 0,
                                                max = n-1,
                                                mid = max,
                                                idx = n;
                                            while (min <= max) {
                                                mid = (min + max) / 2;
                                                if (scoreList.get(mid) >= minScore) {
                                                    idx = mid;
                                                    max = mid - 1;
                                                } else
                                                    min = mid + 1;
                                            }
                                            answer[i] += n - idx;
                                        }
                                }
                        }
                }
            i++;
        }
        return answer;
    }
}

```

> 다른 사람 코드 3

```java
import java.util.ArrayList;
import java.util.HashMap;
class Solution {
     static final HashMap<String,Integer> idx = new HashMap<>(){
        {
            put("java",0);put("python",1);put("cpp",2);
            put("backend",0);put("frontend",1);
            put("junior",0);put("senior",1);
            put("pizza",0);put("chicken",1);
        }
    };

    static int idxs[][] = {{0,1,2},{0,1},{0,1},{0,1}};
    static int[][][][][] map;
    public int[] solution(String[] info, String[] query) {
        map = new int[3][2][2][2][100001];


        for (int i=0;i<info.length;i++){
            String str[] = info[i].split(" ");
            int a = idx.get(str[0]);
            int b = idx.get(str[1]);
            int c = idx.get(str[2]);
            int d = idx.get(str[3]);
            int score = Integer.parseInt(str[4]);
            map[a][b][c][d][score]++;
        }
        for (int i=0;i<=2;i++){
            for (int j=0;j<=1;j++){
                for (int k=0;k<=1;k++){
                    for (int l=0;l<=1;l++){
                        int sum=0;
                        for (int s=100000;s>=1;s--){
                            sum+=map[i][j][k][l][s];
                            map[i][j][k][l][s]=sum;
                        }
                    }
                }
            }
        }
        int ans[] = new int[query.length];
        for (int i=0;i<query.length;i++){
            String str[] = query[i].split(" and ");
            ArrayList<String> arr = new ArrayList<>();
            String sps[] = str[3].split(" ");
            arr.add(str[0]);arr.add(str[1]);arr.add(str[2]);arr.add(sps[0]);arr.add(sps[1]);

            ans[i] = find(arr,new int[4],0);
        }
        return ans;
    }
    static int find(ArrayList<String> str,int nums[],int index){
        if (index==4){
            return map[nums[0]][nums[1]][nums[2]][nums[3]][Integer.parseInt(str.get(4))];
        }
        int sum=0;
        if (str.get(index).equals("-")){
            for (int i=0;i<idxs[index].length;i++){
                nums[index]=i;
                sum += find(str,nums,index+1);
            }
        }else{
            nums[index] = idx.get(str.get(index));
            sum += find(str,nums,index+1);
        }
        return sum;
    }
}
```

### 합승 택시 요금 (4번)

> 플로이드 와샬 알고리즘 이후 최단 금액 탐색

```java
class Solution {
    public int solution(int n, int s, int a, int b, int[][] fares) {
        int answer = 2147483647;
        int max = 100000000; // 너무 작게하거나 크게해도 문제가 생긴다.
        int[][] arr = new int[n][n];
        for (int i = 0; i < n; i++)
        {
            for (int j = 0; j < n; j++)
            {
                if (i == j)
                    arr[i][j] = 0;
                else
                    arr[i][j] = max;
            }
        }
        for (int i = 0; i < fares.length; i++)
        {
            int temp1 = fares[i][0];
            int temp2 = fares[i][1];
            int price = fares[i][2];
            arr[temp1 - 1][temp2 - 1] = price;
            arr[temp2 - 1][temp1 - 1] = price;
        }
        for (int i = 0; i < n; i++) // 거쳐가는 노드
        {
            for (int j = 0; j < n; j++) // 출발 노드
            {
                for (int k = 0; k < n; k++) // 도착 노드
                {
                    if (arr[j][i] + arr[i][k] < arr[j][k])
                        arr[j][k] = arr[j][i] + arr[i][k];
                }
            }
        }
        
        for (int i = 0; i < n; i++) // 최소비용 탐색
            answer = Math.min((arr[s - 1][i] + arr[i][a - 1] + arr[i][b - 1]), answer);
        
        // for (int i = 0; i < n; i++)
        // {
        //     for (int j = 0; j < n; j++)
        //         System.out.print(arr[i][j] + " ");
        //     System.out.println();
        // }
        
        return answer;
    }
}
```

> 다른 사람 코드
>
> dijkstra(다이크스트라)  알고리즘 사용
>
> https://blog.naver.com/merongvvvv/222065831713

```java
import java.util.*;

class Solution {
    ArrayList<ArrayList<Edge>> graph;
    class Edge implements Comparable<Edge>{
        int next;
        int cost;
        Edge(int next, int cost){
            this.next = next;
            this.cost = cost;
        }
        @Override
        public int compareTo(Edge e){
            return this.cost - e.cost;
        }
    }
    int[] dijkstra(int start, int[] costs){
        PriorityQueue<Edge> pq = new PriorityQueue<>(graph.get(start));
        for(Edge e : graph.get(start)) costs[e.next] = e.cost;
        costs[start] = 0;
        while(!pq.isEmpty()){
            Edge e = pq.poll();
            if(costs[e.next] < e.cost) continue;
            for(Edge ne : graph.get(e.next)){
                if(costs[ne.next] > e.cost + ne.cost){
                    costs[ne.next] = e.cost + ne.cost;
                    pq.add(new Edge(ne.next, e.cost + ne.cost));
                }
            }
        }
        return costs;
    }
    public int solution(int n, int s, int a, int b, int[][] fares) {
        int answer = Integer.MAX_VALUE;

        graph = new ArrayList<>();
        for(int i = 0 ; i < n ; i ++) graph.add(new ArrayList<>());
        for(int[] i : fares){
            graph.get(i[0] - 1).add(new Edge(i[1] - 1, i[2]));
            graph.get(i[1] - 1).add(new Edge(i[0] - 1, i[2]));
        }
        int[] startA = new int[n];
        int[] startB = new int[n];
        int[] start = new int[n];

        Arrays.fill(startA, 100000 * n);
        Arrays.fill(startB, 100000 * n);
        Arrays.fill(start, 100000 * n);

        startA = dijkstra(a - 1, startA);
        startB = dijkstra(b - 1, startB);
        start = dijkstra(s - 1, start);

        for(int i = 0 ; i < n ; i ++) answer = Math.min(answer, startA[i] + startB[i] + start[i]);
        return answer;
    }
}
```

