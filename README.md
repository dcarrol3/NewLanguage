# The Javier Language

Custom programming language for learning purposes. Written in Java.


#### Built on following systems
* Linux
* Mac OS



#### Tools used
* Java 1.8+



#### How to install the language
A pre-installed version can be found in the submissions folder.
1. Download the Javier.jar file.
2. If not already provided, create a directory called data in the same directory as Javier.jar.



#### How to build and run
1. In a terminal, navigate to the project directory.
2. Create a new directory called JavierBuild
3. Enter the following command:\
```javac -d ./JavierBuild -classpath json-simple-1.1.1.jar src/*/*.java src/*/*/*.java ```
4. The project is now built. Navigate into the JavierBuild folder within the terminal.
5. Enter the following commands to run the project from this build:\
```java main.Main compile javierFilename```\
```java main.Main run javierFilename```

#### Bash scripts
Before compiling and running, follow the How To Install The Language instructions above.\
From the terminal at the Javier.jar location, enter the following commands to compile and run a javier program.\
Filenames do NOT include the file extension.\
All Javier program files MUST end in .javier\
All Javier program files MUST be in the data directory created above.


##### Compiler build
```java -jar Javier.jar compile fileName```

##### Run the Javier Runtime
```java -jar Javier.jar run fileName```


#### Youtube video
