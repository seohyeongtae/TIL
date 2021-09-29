systemctl   stop  mysql



yum -y  remove Maria*

 

mariadb 관련 폴더들을 삭제한다.

 

rm -rf /etc/my.cnf*

rm -rf /var/log/mysql  

rm -rf /var/lib/mysql 