install.packages("rJava")
library(rJava)

install.packages("RJDBC")
library(RJDBC)

jdbcDriver <- JDBC(driverClass="oracle.jdbc.OracleDriver", classPath="C:/Users/daddi/Downloads/lib/ojdbc6_g.jar")
conn <- dbConnect(jdbcDriver, "jdbc:oracle:thin:@mulcamfinal.ceym10bgnbuf.ap-northeast-2.rds.amazonaws.com:1521:orcl","mulcamfinal","mulcamfinal")

q1 = "SELECT * FROM USERS"
#dbGetQuery(conn, query)

users <- dbGetQuery(conn, q1)
head(users)

install.packages("dplyr")
library(dplyr)
install.packages("reshape2")
library(reshape2)
install.packages("ggplot2")
library(ggplot2)
install.packages("wordcloud")
library(wordcloud)
install.packages("graphics")
library(graphics)
install.packages("descr")
library(descr)
install.packages("randomcoloR")
library(randomcoloR)

users <- dbGetQuery(conn, q1)
head(users)
  

#freq_gender %>% select(frequency)
#freq_gender2<-matrix(freq_gender$Frequency)
#pie(x=freq_gender,labels = row.names(frequency),clockwise = T,main = '사용자 성별 비율')

#성별비율
freq_gender<-(freq(users$GENDER))
y<-c('남 83.33%','여 16.67%')
genderpie<-with(users,table(GENDER))
pie(genderpie, labels = y, main = '사용자 성별 비율', col = c('lightblue','pink'))


#관심사비율
freq_interest<-(freq(users$INTEREST))
col = c("chocolate1","chartreuse2","darkgoldenrod1","darkorchid1")
y<-c('과자 ')
interestpie<-with(users,table(INTEREST))
pie(interestpie, main = '사용자 관심사 비율')
barplot((with(users,table(INTEREST))), ylim=c(0,5), main = '사용자 관심사 비율', col = col)







