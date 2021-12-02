## 2021_KAKAO_BLIND_RECRUITMENT

### 순위 검색(3번)

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

