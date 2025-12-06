#!/bin/bash

# Use command below to run graphical version of app
# ./target/image/bin/java -m com.school.app/com.school.app.Main

# Use command below to run CLI version of app
# Important to ignore module-info becuase it requires external packages
find ./src/main/ -type f -name "[!module-info.java]*.java" >sources.txt

javac -d ./output/ @sources.txt

java -cp ./output/ com.school.app.Main
