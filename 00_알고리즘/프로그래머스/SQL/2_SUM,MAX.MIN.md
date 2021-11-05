## SUM,MAX,MIN

### 최댓값 구하기

```Oracle
SELECT DATETIME FROM (SELECT * FROM ANIMAL_INS ORDER BY DATETIME DESC)
WHERE ROWNUM = 1;
```

### 최솟값 구하기

```
SELECT min(DATETIME) FROM ANIMAL_INS;
```

### 동물 수 구하기

```
SELECT count(ANIMAL_ID) FROM ANIMAL_INS;
```

### 중복 제거하기

```
SELECT count(DISTINCT NAME) as count FROM ANIMAL_INS;
```

