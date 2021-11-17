## String,Date

### 루시와 엘라 찾기

```Oracle
/*
SELECT ANIMAL_ID, NAME, SEX_UPON_INTAKE FROM ANIMAL_INS
WHERE (NAME = 'Lucy' or NAME = 'Pickle' or NAME = 'Rogan' or NAME = 'Sabrina' or NAME = 'Mitty' OR NAME = 'Ella')
*/

SELECT ANIMAL_ID, NAME, SEX_UPON_INTAKE FROM ANIMAL_INS
WHERE NAME = "Lucy"
OR NAME = "Pickle"
OR NAME = "Rogan"
OR NAME = "Sabrina"
OR NAME = "Mitty"
OR NAME = "Ella"
ORDER BY ANIMAL_ID;
```

### 이름에 el이 들어가는 동물 찾기

```
/*
SELECT ANIMAL_ID, NAME
FROM ANIMAL_INS
WHERE REGEXP_LIKE(NAME,'el|eL|El|EL') AND ANIMAL_TYPE='Dog'
ORDER BY NAME
*/

/*
SELECT animal_id, name
from animal_ins
where lower(name) like '%el%' and animal_type ='Dog'
order by name asc;
*/

SELECT ANIMAL_ID, NAME FROM ANIMAL_INS
WHERE NAME LIKE "%EL%"
AND ANIMAL_TYPE IN ("dog")
ORDER BY NAME;
```

### 중성화 여부 파악하기

```
/*
SELECT ANIMAL_ID,
       NAME,
       CASE WHEN REGEXP_LIKE(SEX_UPON_INTAKE, 'Neutered|Spayed') THEN 'O'
            ELSE 'X'
       END AS 중성화
FROM ANIMAL_INS
ORDER BY ANIMAL_ID
*/

SELECT ANIMAL_ID
     , NAME
     , CASE 
          WHEN SEX_UPON_INTAKE LIKE 'Spayed%' 
            OR SEX_UPON_INTAKE LIKE 'Neutered%' THEN 'O'
         ELSE 'X'
         END 중성화
FROM ANIMAL_INS
ORDER BY ANIMAL_ID
```

### 오랜 기간 보호한 동물(2)

```

```

### DATETIME 에서 DATE로 형 변환

```

```

