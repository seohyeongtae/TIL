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
query2 <- "SELECT ITEMNAME, SUM(quantity)AS SUMQ FROM orderlist GROUP BY ROLLUP(ITEMNAME)"
product2 <- dbGetQuery(conn, query2)

install.packages("ggplot2")
library(ggplot2)
install.packages("wordcloud")
library(wordcloud)
install.packages("MASS")
library(MASS)
install.packages("graphics")

product3 <-data.frame(product2)
product4<-table(product2$SUMQ)
barplot(product4,main = "Chart of Orderlist",beside = FALSE)


sold<-product3$SUMQ
goods<-product3$ITEMNAME
plot(sold,goods,main = "Chart of Orderlist")

 
sold<-table(product3$SUMQ)
goods<-table(product3$ITEMNAME)
pie(x=)

#orderlist - barchart
sold<-c(product3$SUMQ)
names(product3$ITEMNAME)
names(sold)<-names(product3$ITEMNAME)
pie(x=sold)
bp<-barplot(product2$SUMQ,names.arg = product2$ITEMNAME,las=1, col=rainbow(4))





