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
	   java -jar  xmlcltool-0.0.1-SNAPSHOT-jar-with-dependencies.jar -file 1.xml -selector "//testSQL/Sample[3]/responseH/@class" -select 
	   java -jar  xmlcltool-0.0.1-SNAPSHOT-jar-with-dependencies.jar -file 1.xml -selector "//testSQL/Sample[3]/@lb" -select
	   java -jar  xmlcltool-0.0.1-SNAPSHOT-jar-with-dependencies.jar -file 1.xml -selector "//testSQL/Sample[3]/queryString/@lb" -select

	// Updating content
	   java -jar xmlcltool-0.0.1-SNAPSHOT-jar-with-dependencies.jar -file 1.xml -selector “//testSQL/Sample[5]/java.net.SQL” -update “A change made in xml" -backup -verbose
	
	// Updating Attribute
	   java -jar xmlcltool-0.0.1-SNAPSHOT-jar-with-dependencies.jar -file 1.xml -selector “//testSQL/Sample[8]/requestH/@class” -update "\"java.lang.Boolean\"" -backup -verbose
	
	// Delete Content
	   java -jar xmlcltool-0.0.1-SNAPSHOT-jar-with-dependencies.jar -file 1.xml -selector “//testSQL/Sample[7]” -delete -backup -verbose

	// Delete Attribute
	   java -jar xmlcltool-0.0.1-SNAPSHOT-jar-with-dependencies.jar -file 1.xml -selector “//testSQL/Sample[8]/@ts” -delete -backup -verbose
	
	
	// Insert Tag After Element
	   java -jar xmlcltool-0.0.1-SNAPSHOT-jar-with-dependencies.jar -file testxstream.txt -selector “//testSQL/Sample[13]/requestH” -insert “<anInsertedTag att=\"12\">Something to be inserted</anInsertedTag>” -backup -verbose
	   


- backup modifier
	Creates a copy of the original file before the change
- verbose
	Shows trace information
	
	
	
Solution Concepts

When I read the invalid xml I convert it to 2 models.
1.- An object model that allows me to traverse it and solve the previous kind of queries (We can call them as tinyXPath)
2.- an equivalent string replacing each invalid character to a valid replacement (char X)

the generated model in 1 has important information:The start position of each element (tag or attribute)
That allows us to find the selected tag or attribute in the former file
The model 2 also has important information. The string generated as model 2 is a valid XML
There is another model that is the one the document uses for performing the XPath Queries where the document is the original document replacing all content parts for their Base64 representation
Then queries which mentions elements of content should be asked representing content as Base64. The response will be corresponding to the original content. 

Applying XPath to model 2 has 2 challenges
a) The java xml model has no information about start position of each element. Each element is a logical "Node" model on a hierarchical structure
b) rebuilding the xml from model 2 can generate the result changing the order of the attributes

Then ... the final string cannot be generated from model 2

The solution I'm implementing is.
1.- Convert the original file to model 1
2.- Convert the origina file to model 2
3.- Apply the query using Xpath on model 2

The step 3 returns a specific Node (tag or attribute)
4.- Based on the returned node create a query of tinyXPath that returns the same node
5.- Apply the tinyXPathQuery to model 1


For example:
//TestPlan/hashTree/hashTree/ThreadGroup[1]/following-sibling::hashTree/SamplerProxy[@enabled='true'][1]
is equivalent to
//TestPlan/hashTree[1]/hashTree[1]/hashTree[2]/SamplerProxy

And //TestPlan/hashTree/hashTree/ThreadGroup[1]/following-sibling::hashTree/SamplerProxy[@enabled='true'][2]
is equivalent to
//TestPlan/hashTree[1]/hashTree[1]/hashTree[3]/SamplerProxy

Ah...also I've changed making them compliant with XPath (beginning with 1)

The limitations are

1.- Queries are always about Tags or Attributes

2.- Cannot use xquery filtering by conditions on Content. (mainly when content contains special characters or malformed & entities)

Only those

As all special characters and & on malformed entities are replaced by "X" literally in the model 2

	