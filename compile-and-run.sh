#!/bin/bash

# Use command below to run graphical version of app
# ./target/image/bin/java -m com.school.app/com.school.app.Main

# If maven is installed, the GUI version can be runned with:
# mvn clean javafx:run

# Use command below to run CLI version of app
# Important to ignore module-info becuase it requires external packages
find ./src/main/ \
  -type f -name "*.java" \
  -not -name "Main.java" \
  -not -name "module-info.java" \
  -not -name "*Controller.java" \
  -not -name "FilterableComboBox.java">sources.txt

javac -d ./output/ @sources.txt

java -cp ./output/ com.school.app.MainCLI
