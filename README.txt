Challenges:
1.- Interpret a non valid XML with special characters in content
Solution: Use Antlr to parse and define a grammar based on xml but changed to accept no-validation issues

1.- Out of memory
Solution: 
eclipse.exe -vmargs -Xmx2048M
Despite it runs making the it takes "minutes" to extract the tokens and to parse the information


Examples of commands

	// Selecting nodes
	   java -jar  xmlcltool-0.0.1-SNAPSHOT-jar-with-dependencies.jar -file 1.xml -selector "//testSQL/Sample[2]/queryString" -select 
	   java -jar  xmlcltool-0.0.1-SNAPSHOT-jar-with-dependencies.jar -file 1.xml -selector "//testSQL/Sample[2]/responseH" -select
	   java -jar  xmlcltool-0.0.1-SNAPSHOT-jar-with-dependencies.jar -file 1.xml -selector "//testSQL/Sample/java.net.SQL" -select
	   java -jar  xmlcltool-0.0.1-SNAPSHOT-jar-with-dependencies.jar -file 1.xml -selector "//testSQL/Sample[2]/java.net.SQL" -select
	   java -jar  xmlcltool-0.0.1-SNAPSHOT-jar-with-dependencies.jar -file 1.xml -selector "//testSQL/Sample[6]/Data" -select


	// Selecting Attributes
	   java -jar  xmlcltool-0.0.1-SNAPSHOT-jar-with-dependencies.jar -file 1.xml -selector "//testSQL/Sample[3]/responseH@class" -select 
	   java -jar  xmlcltool-0.0.1-SNAPSHOT-jar-with-dependencies.jar -file 1.xml -selector "//testSQL/Sample[3]@lb" -select
	   java -jar  xmlcltool-0.0.1-SNAPSHOT-jar-with-dependencies.jar -file 1.xml -selector "//testSQL/Sample[3]/queryString@lb" -select

	// Updating content
	   java -jar xmlcltool-0.0.1-SNAPSHOT-jar-with-dependencies.jar -file 1.xml -selector “//testSQL/Sample[5]/java.net.SQL” -update “A change made in xml" -backup -verbose
	
	// Updating Attribute
	   java -jar xmlcltool-0.0.1-SNAPSHOT-jar-with-dependencies.jar -file 1.xml -selector “//testSQL/Sample[8]/requestH@class” -update "\"java.lang.Boolean\"" -backup -verbose
	
	// Delete Content
	   java -jar xmlcltool-0.0.1-SNAPSHOT-jar-with-dependencies.jar -file 1.xml -selector “//testSQL/Sample[7]” -delete -backup -verbose

	// Delete Attribute
	   java -jar xmlcltool-0.0.1-SNAPSHOT-jar-with-dependencies.jar -file 1.xml -selector “//testSQL/Sample[8]@ts” -delete -backup -verbose
	
	
	// Insert Tag After Element
	   java -jar xmlcltool-0.0.1-SNAPSHOT-jar-with-dependencies.jar -file testxstream.txt -selector “//testSQL/Sample[13]/requestH” -insert “<anInsertedTag att=\"12\">Something to be inserted</anInsertedTag>” -backup -verbose
	   


- backup modifier
	Creates a copy of the original file before the change
- verbose
	Shows trace information
	