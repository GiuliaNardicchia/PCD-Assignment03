#!/usr/bin/env bash

mkdir -p bin

javac -d bin app/src/main/java/pcd/lab10/rmi/*.java
if [ $? -ne 0 ]; then
  echo "Errore durante la compilazione."
  exit 1
fi

cd bin || exit

echo "Avvio rmiregistry..."
( rmiregistry & )
sleep 1

cd ..

echo "Avvio server..."
java -classpath bin -Djava.rmi.server.codebase=file:bin/ pcd.lab10.rmi.Test02_Server2
