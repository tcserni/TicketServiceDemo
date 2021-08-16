call mvn clean install -U

cd common
call mvn clean install -U
cd ..

cd api
call mvn clean install -U
cd ..

cd core
call mvn clean install -U
cd ..

cd partner
call mvn clean install -U
cd ..

cd ticket
call mvn clean install -U
cd ..

PAUSE