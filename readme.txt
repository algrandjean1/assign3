Author: Tim Lindquist (Tim.Lindquist@asu.edu)
        Software Engineering, CIDSE, IAFSE, Arizona State University Polytechnic
Version: August 2014

See http://pooh.poly.asu.edu/Cst420

Purpose: Sample project providing GUI classes for waypoint management. This project includes
both Java and C++ programs that demonstrate how to use the waypoint GUIs.
The C++ program depends on the FLTK.

To execute: enter 
ant
from the command line in the project directory to find the defined ant targets. To execute java:
ant execute.java.client

To execute the C++ sample client:
ant build.cpp.client
Then run the resulting program with:
./bin/waypoint

Before you will be able to successfully compile and execute the C++ client, you need to build the 
FLTK toolkit on your system. Instructions for doing so on Mac OSX and Windows Cygwin can be found on
the class web site at:
http://pooh.poly.asu.edu/Cst420/Resources/setupFLTK.html

Depending on your installation of Ant, you may need to place cpp tasks, and ant-contrib tasks for
Ant where it will find them. This project's lib directory contains the java archive:

antlibs.jar

Copy that jar file to your home directory and extract its contents with:

jar xf antlibs.jar

If after extracting the above, you still get an error (if task not found) when doing ant build.cpp.tasks
then copy the task definition files directly to the lib directory of your ant installation:

cp ./.ant/libs/*.jar $ANT_HOME/lib/

Be sure that ANT_HOME is properly defined before doing the copy.
