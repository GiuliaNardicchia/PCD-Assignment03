#!/usr/bin/env bash

# Ferma eventuale rmiregistry (solo su Windows)
echo "Controllo se rmiregistry è già in esecuzione..."
taskkill //F //IM rmiregistry.exe > /dev/null 2>&1

if [ $? -eq 0 ]; then
  echo "rmiregistry terminato."
else
  echo "rmiregistry non era attivo o non è stato trovato."
fi

mkdir -p bin

shopt -s globstar

javac -d bin src/main/java/it/unibo/pcd/assignment03/**/*.java
if [ $? -ne 0 ]; then
  echo "Errore durante la compilazione."
  exit 1
fi

cd bin || exit

echo "Avvio rmiregistry..."
( rmiregistry & )
sleep 1

#cd ..

#echo "Avvio server..."
#java -classpath bin -Djava.rmi.server.codebase=file:bin/ it.unibo.pcd.assignment03.Launcher
