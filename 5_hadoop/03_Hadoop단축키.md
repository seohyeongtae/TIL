## Hadoop 단축키  교재 80p~

```
HADOOP_HOME 을 PATH 로 지정했기 때문에 아무곳에서나 hadoop fs ~ 로 사용할 수 있다. 

ava FsShell
           [-ls <path>]
           (지정한 디렉토리에 있는 파일의 정보를 출력하거나, 특정한 파일을 지정해 정보를 출력하는 명령어 - 권한정보,소유자,소유그룹,생성일자,바이트 수 등 확인 가능)
           		# hadoop fs -ls /test
           
           [-lsr <path>]
           (ls 명령어는 현재의 디렉터리만 출력하는 반면 lsr 명력어는 현재 디렉터리의 하위 디렉터리 정보 까지 출력)
           
           [-du <path>]
           (지정한 디렉터리나 파일의 사용량을 확인 하는 명력어, 바이트 단위로 결과 출력)
           
           [-dus <path>]
           (전체 합계 용량 출력)
           
           [-count[-q] <path>]
           
           [-mv <src> <dst>]
           (이동 및 이름 변경)
           
           [-cp <src> <dst>]
           (복사)
           
           [-rm [-skipTrash] <path>]
           (삭제)
           
           [-rmr [-skipTrash] <path>]
           [-expunge]
           [-put <localsrc> ... <dst>]
           (지정한 로컬 파일 시스템의 파일 및 디렉터리를 목적지 경로로 복사)
           
           [-copyFromLocal <localsrc> ... <dst>]
           [-moveFromLocal <localsrc> ... <dst>]
           [-get [-ignoreCrc] [-crc] <src> <localdst>]
           (하둡데이터를 localhost로 파일 가져오기 )
           # hadoop fs -get  /output/part-r-00000  result.txt 
           
           [-getmerge <src> <localdst> [addnl]]
           (하둡데이터를 merge해서 localhost로 가져오기)
           # hadoop fs -getmerge  /output  resultall.txt
           
           [-cat <src>]
            (지정한 파일의 내용을 화면에 출력)
            
           [-text <src>]
            (cat은 텍스트파일만 출력가능하지만 text는 zip파일 형태로 압축된 파일도 텍스트 형태로 화면에 출력)
            
           [-copyToLocal [-ignoreCrc] [-crc] <src> <localdst>]
           [-moveToLocal [-crc] <src> <localdst>]
           [-mkdir <path>]
           (파일 생성)
           
           [-setrep [-R] [-w] <rep> <path/file>]
           [-touchz <path>]
           [-test -[ezd] <path>]
           [-stat [format] <path>]
           [-tail [-f] <file>]
           [-chmod [-R] <MODE[,MODE]... | OCTALMODE> PATH...]
           [-chown [-R] [OWNER][:[GROUP]] PATH...]
           [-chgrp [-R] GROUP PATH...]
           [-help [cmd]]

Generic options supported are
-conf <configuration file>     specify an application configuration file
-D <property=value>            use value for given property
-fs <local|namenode:port>      specify a namenode
-jt <local|jobtracker:port>    specify a job tracker
-files <comma separated list of files>    specify comma separated files to be copied to the map reduce cluster
-libjars <comma separated list of jars>    specify comma separated jar files to include in the classpath.
-archives <comma separated list of archives>    specify comma separated archives to be unarchived on the compute machines.

The general command line syntax is
bin/hadoop command [genericOptions] [commandOptions]

```

