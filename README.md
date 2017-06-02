# javaExampleProject
This project is an aggregation of java examples, to allow myself and anyone who's interested the possibility to look up certain executable java code snippets.

The general idea is, that you can look up any example code you are interested in, simply by moving into the directory javaExampleProject/src/main/java/jep/example/ (or any sub-directory/package within this directory) and opening the files you want to look at.
I would suggest that you do not go through the files but rather open the project in some IDE.

The project uses the build tool gradle (https://gradle.org/). Which allows to setup the project on your computer by simply opening your console, moving into the projects folder and typing:

'gradlew eclipse'

Notes:
* the command sets the project up to be opened in eclipse (http://www.eclipse.org/), if you use another IDE you might have to adept the 'build.gradle'-file and run another command
* on linux/mac the command might look a little bit different
* on Windows: if you don't know how to open the console - hit 'Win' + 'R', type in 'cmd' and than hit 'Enter', you can use the command 'cd \<dir\>' to move to some directory, if you want to change the drive your on type '\<drive_letter>:' (ex.: 'D:' to go to D:)
* at some point I might add the possibility to actualy build the project using gralde, however at the current time - that is not intended, since the idea is that you can look at the code in your IDE (or editor) and look at the ouput in the implemented simple graphical user interface (GUI)

To use the GUI simply run the project in your IDE (with JavaExamplesProjectMain being the main class [which can be found in the jep.main-package]), select the example you are intereseted in in the 'Overview'-tab.
You can than look up the packages and class required for this example as well as a description in the 'Description'-tab. If you hit the 'Run'-button. The example will be run and you can look up the logged texts in the 'Output'-tab.
Those logged texts correspond to the texts logged using the #log(String text), #logln(String line), ... methods in the selected example classes #run(String args):String method.
