<h2> JMeter File  Generator </h2>
This is a simple JMeter extension, what it does is create a file in the java.system.temp directory with the size given.

Just run `mvn package` and copy the FileGenerator-0.0.2-SNAPSHOT.jar to the /lib/ext directory.

After that start JMeter.
Add a Java sampler.
Fill in the parameters 

This allows you to save the filename to a property or a variable. So if you are using the file in multiple threads use the property. If it is all in 
one thread use variable.