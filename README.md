eit060-project2
===============

Computer Security EIT060

//>alias sqlite3='/h/d5/q/[STIL-ID]/workspace/eit060-project2/sqlite/sqlite-autoconf-3080300/sqlite3'
>alias sqlite3='[PATH]/sqlite/sqlite-autoconf-3080300/sqlite3'
>sqlite3
>.databases
>.quit


= Client =


= Server =

FÖr att köra servern:
java -classpath ".:sqlite-jdbc-3.7.2.jar" server.Server 9999


> Log! Operation|När|Vem|aCESSGRANTEd
> Access Control för server side sköts av OU-parameter i certifikat, där den är Patient/Nurse/Doctor/Gov
 	Kolla vad den är, jämför med action (nurse får t.ex.< inte ta bort), om getOU != doctor => Tillåt inte


= Certificate =

För att skapa certificate:
gå till server/CA/
kör ./makecert '<name>' '<member type>'
output = clientkeystore, clienttruststore

