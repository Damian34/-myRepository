
CalculatorIonic
====================

simple Ionic calculator that keeps last results on the list. the api was made to test Ionic.

### how to run app by console in browser ###

ionic start Calculator blank --v4 \
in the installation process ,installing android platform is not necessary\
after creating project replace src folder then go inside project then type:\
ionic serve

### some commands ###

https://ionicframework.com/docs/v1/guide/installation.html
https://ionicframework.com/docs/intro/tutorial/

//how to install\
npm install -g cordova@7.1.0\
npm install -g ionic@4.0.1

///
in the project, modify only folder src\
main page is in pages\home\home.html\
if I want to add a menu, have to do it in the folder src/app

//create ionic project with name todo2\
ionic start todo2 blank --type ionic1

//or this way\
ionic start HelloWorld blank --v2\
ionic start HelloWorld tutorial --v4

//run app in browser\
ionic serve

//sharing android platform to project\
ionic cordova platform add android\
ionic cordova build android\
ionic cordova run android

ionic cordova platform\
cordova platform remove android\
cordova platform add android@4.1.1

