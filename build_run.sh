#!/usr/bin/env bash
export JAVA_HOME='C:\Users\Slava\.jdks\corretto-17.0.11\'
echo $JAVA_HOME

cur=`pwd`
echo $cur

cd ./fielgo-cloud/ || exit
./gradlew clean bootJar

cd $cur || exit
cd ./station-cloud/ || exit
./gradlew clean bootJar

cd $cur || exit
cd ./pump-controller/ || exit
npm run build

cd $cur || exit

# npx serve -s build
read