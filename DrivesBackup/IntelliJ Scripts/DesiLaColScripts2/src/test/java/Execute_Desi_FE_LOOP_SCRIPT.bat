set ProjectPath=%WORKSPACE%
echo %ProjectPath%
set classpath=%ProjectPath%\src\test\java\Desi_FE_LOOP_Script;
echo %classpath%
java org.testng.TestNG %ProjectPath%\src\test\java\_Desi_FE_LOOP_Test.xml
