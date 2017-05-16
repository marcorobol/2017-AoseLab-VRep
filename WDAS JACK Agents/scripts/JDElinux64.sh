export PATH=$PATH:$PWD/lib/lib/linux64
export LD_LIBRARY_PATH=$LD_LIBRARY_PATH:$PWD/lib/lib/linux64
export WDAS=./lib/wdas_api.jar:./lib/wdas_adaptervrep.jar:./lib/wdas_core.jar:./lib/wdas_launcher.jar:./bin:./lib/bsh-2.0b6.jar
export CLASSPATH=$CLASSPATH:~/AOS/JACK-56c/lib/jack.jar:$WDAS
java -Djava.library.path=$PWD/lib/lib/linux64 aos.main.Jack ./src/jack/WDASJackAgents.prj 
