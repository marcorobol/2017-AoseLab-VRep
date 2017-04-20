set JACK="C:\Program Files (x86)\AOS\JACK-56c\lib\jack.jar"
set WDAS=.\lib\wdas_api.jar;.\lib\wdas_adaptervrep.jar;.\lib\wdas_core.jar;.\lib\wdas_launcher.jar
set CLASSPATH=%JACK%;%WDAS%;.\bin
java -classpath %CLASSPATH% aos.main.Jack .\src\jack\WDASJackAgents.prj