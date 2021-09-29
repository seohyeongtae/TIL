install.packages("multilinguer")
install.packages(c("hash", "tau", "Sejong", "RSQLite", "devtools", "bit", "rex", "lazyeval", "htmlwidgets", "crosstalk", "promises", "later", "sessioninfo", "xopen", "bit64", "blob", "DBI", "memoise", "plogr", "covr", "DT", "rcmdcheck", "rversions"), type = "binary")
install.packages("remotes")
remotes::install_github('haven-jeon/KoNLP', upgrade = "never", INSTALL_opts=c("--no-multiarch"))
library(KoNLP)
useSystemDic()
useSejongDic()
useNIADic()

install.packages("rJava")
library(rJava)

install.packages("RJDBC")
library(RJDBC)

jdbcDriver <- JDBC(driverClass="oracle.jdbc.OracleDriver", classPath="C:/Users/i/Downloads/lib/ojdbc6_g.jar")

conn <- dbConnect(jdbcDriver, "jdbc:oracle:thin:@mulcamfinal.ceym10bgnbuf.ap-northeast-2.rds.amazonaws.com:1521:orcl","mulcamfinal","mulcamfinal")

query = "SELECT * FROM orderlist"
#dbGetQuery(conn, query)

orderlist <- dbGetQuery(conn, query)
head(orderlist)
str(orderlist)

install.packages("dplyr")
library(dplyr)

install.packages("reshape2")
library(reshape2)

#head(orderlist)
#product <- melt(orderlist, id.vars =c("ITEMNAME","QUANTITY"))
#head(product)
#acast(orderlist, )

query1 <- "SELECT * FROM orderlist ORDER BY ITEMNAME, QUANTITY"
product <- dbGetQuery(conn, query1)
query2 <- "SELECT ITEMNAME, SUM(QUANTITY) FROM orderlist"
product2 <- dbGetQuery(conn, query2)













