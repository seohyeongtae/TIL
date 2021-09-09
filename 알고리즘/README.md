## 알고리즘

### 백준

> https://st-lab.tistory.com/96  (각 문제별 자세한 설명을 해주심 -> 원리 부터 알고리즘 이해 까지)
>
> 다음에 꼭 한번 더 보기 (ex 재귀) 

### 1_입출력과 사칙연산,if,for

- 출력
- 사칙연산
- BufferedReader/ BufferedWriter
- StringTokenizer

### 2_while,1차원배열

* 배열사용
* substring사용
* arraylist / hashmap
* String.format / Math.round

### 3. 함수, 문자열

* stringbuilder / boloean 선언
* ASCII 코드
* toUpperCase (대문자)
* String  - contains 사용

### 4. 기본수학 1

* split

* 2차원 배열 사용

* BigInteager 사용

* 더하기 add /  빼기 subtract / 곱하기 multiply / 나누기 divide  

  ​	ex) 

  	BigInteger a = new BigInteger(st.nextToken());
  		BigInteger b = new BigInteger(st.nextToken());
  		BigInteger sum = a.add(b);

### 5. 기본수학 2

* 에라토스테네스의 체 (제곱근 이용 Math.sqrt()) -> 소수 찾기

* 삼항연산자 사용

* Math.pow(a,2)  a의 2제곱 / Math.PI (원주율) / Math.abs (절대값 구하기)

* DecimalFormat 

  		DecimalFormat df  = new DecimalFormat("#.######");
    		
    		System.out.println(df.format(a));
    		System.out.println(df.format(b)+".000000");

* 두 원사이의 거리를 통해 (피타고라스의 정리) 겹치는 점 찾기
* Integer.valueOf(st.nextToken());

### 6. 재귀, 브루트 포스

- 팩토리얼
- 이중배열 사용
- 하노이 탑 이동 순서
- 분해합
- 영화감독 숌

### 7. 정렬

* 선택정렬
* Arrays.sort()
* Counting Sort 정렬 알고리즘
* 통계학
* 람다식 사용 
* 단어 사전순 정렬 compareTo()
* map / hashmap 개념

### 8. 백트레킹

* https://st-lab.tistory.com/114 (백트레킹이란)
* DFS (깊이우선탐색)
* [마스킹기법](https://blog.daum.net/swrush/590)
* System.exit(0); - 0 = 정상종료

### 9. 동적 계획법 1

* 캐쉬를 이용한 피보나치 수 구현 (재귀를 사용할 떄 생기는 불필요한 함수 계산을 줄인다.)
* Memoization -> 값을 재활용
* 각 문제 별로 알맞는 점화식 찾기
* Top-Down  방식(보통 재귀 사용)  -> 큰 문제에서 작은문제로 쪼개어 들어가는 것
* Bottom-Up (보통 반복문 사용) 방식 -> 작은 문제부터 풀어나가는 것 
* LIS (최장 증가 부분 수열)
* LDS (최장 감소 부분 수열) - ex) 바이토닉 정렬
* 반대로 생각하기(전깃줄 2565번) -> 겹치는 부분을 생각하는 것이 아니라 설치 가능한 최대한의 전기줄 (LIS)를 구한 뒤 현재 설치 된 전깃줄 수를 빼자. 
* LCS (최장 공통 부분 수열)
* 배낭 문제 (Knapsack) 냅색 알고리즘 

### 10. 그리디 알고리즘

* 그리디 알고리즘 : 매 번의 선택에서 가장 좋아보이는 선택을 하여 적절한 답을 찾아간다.
* [활동 선택 문제](https://en.wikipedia.org/wiki/Activity_selection_problem)
* 정렬을 위한 [Comparable 과 Comparator](https://st-lab.tistory.com/243)
* [Generic 에 대한 이해](https://st-lab.tistory.com/153)

### 11. 정수론 및 조합론

* 유클리드 호제법 [링크1](https://terms.naver.com/entry.naver?docId=2073670&cid=47324&categoryId=47324) [링크2](https://blog.naver.com/galaxyenergy/222264614390) [링크3](https://blog.naver.com/gdpresent/222452479379)

  * 두 정수 *a*, *b*의 최대공약수를 *G*(*a*, *b*)라고 하자.

    정수 *a*, *b*, *q* *r* (*b* ≠ 0)에 대하여 *a* = *bq* + *r*,이면 *G*(*a*, *b*) = *G*(*b*, *r*)가 성립한다.

  * 최대공약수, 최소공배수 구하기
  
* [이항계수](https://ko.wikipedia.org/wiki/%EC%9D%B4%ED%95%AD_%EA%B3%84%EC%88%98) / 파스칼의 삼각형  [링크](https://st-lab.tistory.com/159)

* [모듈라연산](https://st-lab.tistory.com/162)   - ex>  **(A+B) %C = (A%C + B%C)%C**   / **(A\*B)%C = (A%C \* B%C)%C** 

  > 모듈라 연산에서 나눗셈 연산은 없다!  **(A%B) %C != ((A%C) % (B%C)%C**

  * ***a mod m = r\*** 표현은 **a % m = r** 이랑 같은 의미
  * (34 mod 7) = (27 mod 7) = (62 mod 7) = 6  → (34 % 7) == (27 % 7) == (62 % 7) == 6
  * 반대로 생각하면 ***a = km + r*** (나눈 수 m의 k배수에 r을 더한 값은 a를 만족한다) (k = a div m (몫)이랑 같다.)
  * (34 mod 7 = 6) → (34 = 4 × 7 + 6)           (27 mod 7 = 6) → (27 = 4 × 3 + 6)
  
* [페르마의 소정리](https://ko.wikipedia.org/wiki/%ED%8E%98%EB%A5%B4%EB%A7%88%EC%9D%98_%EC%86%8C%EC%A0%95%EB%A6%AC)

* Hashmap 관련 사용 함수들 (프로그래머스 - 해시 참고)

  * Entry
  * Iterator
  * Collections.sort

* 이항계수, 조합도 팩토리얼로 구성되어 있다.

### 12. 스택

* [자바 컬렉션 프레임워크 (Collection)](https://st-lab.tistory.com/142?category=856997)
* [리스트 인터페이스](https://st-lab.tistory.com/146)

### 13. 큐, 덱

