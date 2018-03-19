
with pom.xml
	open git bash
	run sh'mvn package'

GRANT ALL PRIVILEGES ON cse308 TO 'cse308_root'@'localhost' IDENTIFIED BY 'pandas';

mysql -u cse308_root -p
