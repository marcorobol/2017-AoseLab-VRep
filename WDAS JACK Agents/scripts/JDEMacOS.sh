export PATH=$PATH:./lib/lib/macosx
export JACK=/Applications/AOS/JACK-56c/lib/jack.jar
export WDAS=./lib/wdas_api.jar:./lib/wdas_adaptervrep.jar:./lib/wdas_core.jar:./lib/wdas_launcher.jar:./bin:./lib/bsh-2.0b6.jar
export CLASSPATH=$JACK:$WDAS:./bin
java -Djava.library.path=$PWD/lib/lib/linux32 -classpath $CLASSPATH aos.main.Jack ./src/jack/WDASJackAgents.prj 
