
var app = angular.module('myApp', []);

app.controller('myHello', function ($scope, $http) {
    $scope.myHello = "Hello from controller";
    $scope.myHello2 = function () {
        return "Hello from controler2 and other function!!";
    };

    $scope.users = [
        {
            id: 1,
            firstName: "Peter",
            lastName: "Jhons"},
        {
            id: 2,
            firstName: "David",
            lastName: "Bowie"}
    ];

    $http.get("http://localhost:8080/AngularJSExample/web/MyServices/userListJson")
            .then(function (response) {
                $scope.others = response.data.user;
            });

    $scope.myDataRepeated = "";
    $scope.repeatFunction = function () {
        $http({
            url: "http://localhost:8080/AngularJSExample/web/MyServices/repeatJson",
            method: "POST",
            data: {"message": $scope.dataToSend}
        })
                .then(function (response) {
                    alert("it is here?: " + response);
                    $scope.myDataRepeated = response.data.message;
                });
    }
});

app.controller('myController', function ($scope, $http, $window) {
    try {
        $scope.usedLogin = localStorage.getItem('login');
        $scope.usedPasword = localStorage.getItem('password');
        $scope.usedMessage = localStorage.getItem('message');
        if ($scope.usedLogin == null) {
            $scope.logged = false;
            setVisible(false);
        } else {
            $scope.logged = true;
            setVisible(true);
        }
    } catch (e) {
        alert("error: " + e);
    }

    $scope.logout = function () {
        $scope.logged = false;
        $scope.usedLogin = "";
        $scope.usedPasword = "";
        $scope.usedMessage = "";
        localStorage.clear();
        setVisible(false);
    }

    $scope.createUser = function () {
        $http({
            url: "http://localhost:8080/AngularJSExample/web/MyServices/createUser",
            method: "POST",
            data: {"login": $scope.newLogin, "password": $scope.newPassword, "note": ""}
        })
                .then(function (response) {
                    if (response.data.message == "true") {
                        $scope.createUserResponse = "the account was created";
                    } else {
                        if (response.data.message == "false2") {
                            $scope.createUserResponse = "login or password is incorrect";
                        } else {
                            $scope.createUserResponse = "the user already exist";
                        }
                    }
                });
    }

    $scope.login = function () {
        $http({
            url: "http://localhost:8080/AngularJSExample/web/MyServices/findUser",
            method: "POST",
            data: {"login": $scope.getLogin, "password": $scope.getPassword, "note": ""}
        })
                .then(function (response) {
                    var response2 = splitMessage(response.data.message);
                    if (response2[0] == "true ") {
                        try {
                            localStorage.setItem('login', $scope.getLogin);
                            localStorage.setItem('password', $scope.getPassword);
                            localStorage.setItem('message', response2[1]);
                            $window.location.href = "index.html";
                        } catch (e) {
                            alert("error: " + e);
                        }
                    } else {
                        $scope.loginResponse = "the user don't exist";
                    }
                });
    }

    $scope.updateNote = function () {
        localStorage.setItem('message', $scope.usedMessage);
        $http({
            url: "http://localhost:8080/AngularJSExample/web/MyServices/updateNote",
            method: "POST",
            data: {"login": $scope.usedLogin, "password": $scope.usedPasword, "note": $scope.usedMessage}
        })
                .then(function (response) {
                    try {
                        if (response.data.message == "true") {
                            $scope.updateNoteResponse = "the message was saved";
                        }
                    } catch (e) {
                        alert("error: " + e);
                    }
                });
    }
});