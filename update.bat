@echo off

cd C:\Dev\Java\Pipes

call gradlew eclipse

del PipesClient.launch
del PipesServer.launch

call gradlew genEclipseRuns

ren runClient.launch PipesClient.launch
ren runServer.launch PipesServer.launch

pause