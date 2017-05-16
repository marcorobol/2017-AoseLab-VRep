export PATH=$PATH:../../linux64
export WDAS=./lib/wdas_api.jar:./lib/wdas_adaptervrep.jar:./lib/wdas_core.jar:./lib/wdas_launcher.jar:./lib/bsh-2.0b6.jar
export CLASSPATH=$CLASSPATH:~/AOS/JACK-56c/lib/jack.jar:$WDAS:./bin
java aos.main.Jack ./src/jack/WDASJackAgents.prj 
