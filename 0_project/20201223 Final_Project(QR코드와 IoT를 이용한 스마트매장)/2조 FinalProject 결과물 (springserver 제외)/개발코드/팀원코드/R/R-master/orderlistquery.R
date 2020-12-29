library(KoNLP)
useSystemDic()
useSejongDic()
useNIADic()

library(rJava)
library(RJDBC)
library(dplyr)
library(reshape2)

jdbcDriver <- JDBC(driverClass="oracle.jdbc.OracleDriver", classPath="C:/Users/i/Downloads/lib/ojdbc6_g.jar")
conn <- dbConnect(jdbcDriver, "jdbc:oracle:thin:@mulcamfinal.ceym10bgnbuf.ap-northeast-2.rds.amazonaws.com:1521:orcl","mulcamfinal","mulcamfinal")

query = "SELECT * FROM orderlist"
orderlist <- dbGetQuery(conn, query)
#head(orderlist)
#str(orderlist)

query1 <- "SELECT * FROM orderlist ORDER BY ITEMNAME, QUANTITY"
product <- dbGetQuery(conn, query1)
query2 <- "SELECT ITEMNAME, SUM(quantity) FROM orderlist GROUP BY ROLLUP(ITEMNAME)"
product2 <- dbGetQuery(conn, query2)

