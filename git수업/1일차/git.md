# Git

> 인용문 - 일반적으로 정의를 내릴 때 앞에 > 사용
>
> Git은 분산형 버전관리시스템 (DVCS) 중 하나이다.

[vscode](https://code.visualstudio.com/ )    추가작업 체크란에 밑에 4개 모두 체크 하고 설치

[typora](https://typora.io/#windows )

[edupack](https://education.github.com/pack)

## Git 사전 준비

> Git 을 사용하기 전에 커밋을 남기는 사람에 대한 정보를 설정한다. (최초)



```bash
$ git config --global user.name 'seohyeongtae'
$ git config --global user.email 'tmahek1@naver.com'
```

* 추후에 commit을 하면, 작성한 사람(author)로 저장된다.

* email 정보는 github에 등록된 이메일로 설정을 하는 것을 추천(잔디밭)

* 설정 내용을 확인하기 위해서는 아래의 명령어를 입력한다.

  ```bash
  $ git config --global -l
  user.email=tmahek1@naver.com
  user.name=seohyeongtae
  ```

> Git bash 설치 [링크](https://gitforwindows.org/ )

## 기초 흐름

> 작업 -> add -> commit

### 0. 저장소 설정

```bash
$ git init
Initialized empty Git repository in C:/Users/user/Desktop/test2/.git/
```

* git 저장소를 만들게 되면 해당 디렉토리 내에 `.git/` 폴더가 생성
* git bash 에서는 `(master)` 로 현재 작업중인 브랜치가 표기 된다.

### 1. `add`

> 커밋을 위한 파일 목록 (staging area)

```bash
$ git add .  			# 현재 디렉토리의 모든 파일 및 폴더
$ git add a.txt 		# 특정 파일
$ git add md-images/	# 특정 폴더
$ git status
# master 브랜치에 있다.
On branch master

No commits yet
# 커밋이 될 변경사항들 (changes)
# Staging area 단계
Changes to be committed:
	# ustatge를 하기 위해서 .. 명령어 
	# working directory 단계
  (use "git rm --cached <file>..." to unstage)
        new file:   a.txt
# 트랙킹 안된 파일들
# git 으로 아직 관리를 하지 않는다
# working directory 단계
Untracked files:
	# 커밋이 될 것에 추가하려면 ...
	# staging area 로 보내려면, add 명령어를 입력해라.
  (use "git add <file>..." to include in what will be committed)
        b.txt


```

### 2. `commit`

> 버전을 기록(스냅샷)

```bash
$ git commit -m '커밋메세지'
$ git commit -m 'First commit'
[master (root-commit) d3d7594] First commit
 2 files changed, 0 insertions(+), 0 deletions(-)
 create mode 100644 a.txt
 create mode 100644 b.txt

```

* 커밋 메세지는 현재 버전을 알 수 있도록 명확하게 작성한다.

* 커밋 이력을 남기기 확인하기 위해서는 아래의 명령어를 입력한다.

  ```bash
  $ git log
  Author: seohyeongtae <tmahek1@naver.com>
  Date:   Thu Sep 17 13:25:19 2020 +0900
  
      First commit
  
  $ git log -1	# 최근 한개의 버전
  $ git log --oneline	# 한줄로 간단하게 표현
  d3d7594 (HEAD -> master) First commit
  $ git log -1 --oneline
  ```

## status - 상태 확인

> git에 대한 모든 정보는 status에서 확인할 수 있다.

```bash
$ git status
```



 자바코드와 sql 코드를 따로 기록하고 싶을때 구분해서 사용

```bash
$ git add a.sql
$ git commit -m 'DB작업'
$ git add b.java 
$ git commit -m '스프링코드'
```



## 원격 저장소 활용하기

> 원격 저장소를 제공하는 서비스는 github, gitlab, bitbucket 등이 있다.

### 1. 원격 저장소 설정하기

```bash
$ git remote add origin https://github.com/seohyeongtae/git-test.git
      remote add origin {URL}
```

* 깃아, 원격(remote)저장소로 추가해줘(add) origin 이라는 이름으로 URL을
* 원격 저장소 삭제(remove) 하기 위해서는 아래의 명령어를 사용한다.

```bash
$ git remote rm origin
```

### 2. 원격 저장소 확인하기

```bash
$ git remote -v
origin  https://github.com/seohyeongtae/git-test.git (fetch)
origin  https://github.com/seohyeongtae/git-test.git (push)
```

### 3. push

```bash
$ git push origin master
Enumerating objects: 3, done.
Counting objects: 100% (3/3), done.
Delta compression using up to 4 threads
Compressing objects: 100% (2/2), done.
Writing objects: 100% (3/3), 215 bytes | 215.00 KiB/s, done.
Total 3 (delta 0), reused 0 (delta 0), pack-reused 0
To https://github.com/seohyeongtae/git-test.git
 * [new branch]      master -> master
Branch 'master' set up to track remote branch 'master' from 'origin'.

```

* origin 원격저장소의 master 브랜치로 push

```bash
$ git push -f origin master
# 원격저장소 덮어 씌우기 쓸때 주의
```

