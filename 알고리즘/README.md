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

### 8. 백트레킹

