export JACK=/Applications/AOS/JACK-56c/lib/jack.jar
export WDAS=./lib/wdas_api.jar:./lib/wdas_adaptervrep.jar:./lib/wdas_core.jar:./lib/wdas_launcher.jar
export CLASSPATH=$JACK:$WDAS:./bin
java -classpath $CLASSPATH aos.main.Jack ./src/jack/WDASJackAgents.prj