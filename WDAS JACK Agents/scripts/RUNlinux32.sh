export CP=~/AOS/JACK-56c/lib/jack.jar:./lib/wdas_api.jar:./lib/wdas_adaptervrep.jar:./lib/wdas_core.jar:./lib/wdas_launcher.jar:./lib/bsh-2.0b6.jar:./bin
java -Djava.library.path=./lib/lib/linux32 -cp $CP unitn.aose.warehousesim.launcher.Launcher 
