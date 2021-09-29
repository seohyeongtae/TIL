## KoNLP 패키지 설치가 되지 않을경우



**\1. rstudio 끄기**



아래 사이트에서 Rtools35.exe 설치

설치 시 기본 옵션이외의 설치 옵션 모두 클릭



https://cran.r-project.org/bin/windows/Rtools/history.html

[ **Building R for Windows**Building R for Windows This document is a collection of resources for building packages for R under Microsoft Windows, or for building R itself (version 1.9.0 or later). The original collection was put together by Prof. Brian Ripley and Duncan Murdoch; it is currently maintained by Jeroen Ooms. The...cran.r-project.org](https://cran.r-project.org/bin/windows/Rtools/history.html)

**\2. rstudio 켜기**



install.packages("multilinguer")





**\3. 의존성 설치**



install.packages(c("hash", "tau", "Sejong", "RSQLite", "devtools", "bit", "rex", "lazyeval", "htmlwidgets", "crosstalk", "promises", "later", "sessioninfo", "xopen", "bit64", "blob", "DBI", "memoise", "plogr", "covr", "DT", "rcmdcheck", "rversions"), type = "binary")



**\4. Git를 통해 KoNLP를 다운 받기 **



\- git 연결 프로그램 다운로드

install.packages("remotes")



\- KoNLP 설치

remotes::install_github('haven-jeon/KoNLP', upgrade = "never", INSTALL_opts=c("--no-multiarch"))





**\5. KoNLP 설치 확인 및 단어 다운로드**



library(KoNLP)

useSystemDic()

useSejongDic()

useNIADic()

 