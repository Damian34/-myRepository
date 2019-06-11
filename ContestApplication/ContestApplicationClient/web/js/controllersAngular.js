
var app = angular.module('myApp', ['myApp.controllers', 'datatables']);
app.controller('menuCtr', function ($scope, $http, $window) {

    $scope.checkMenu = function () {
        try {
            $scope.usedLogin = readCookie("login");
            $scope.usedPasword = readCookie("password");
            $scope.usedRole = readCookie("role");
            if ($scope.usedLogin == null || $scope.usedLogin == "" || $scope.usedLogin == "undefined") {
                document.cookie = "login=";
                document.cookie = "password=";
                document.cookie = "role=";
                document.getElementById("logedUserName").style.display = "none";
                //change icons witch allow to login and logout
                document.getElementById("SignUp").style.display = "block";
                document.getElementById("logIn").style.display = "block";
                document.getElementById("logOut").style.display = "none";
                document.getElementById("console").style.display = "none";
            } else {
                document.getElementById("logedUserName").style.display = "block";
                //change icons witch allow to login and logout
                document.getElementById("SignUp").style.display = "none";
                document.getElementById("logIn").style.display = "none";
                document.getElementById("logOut").style.display = "block";
                if ($scope.usedRole == "ADMIN") {
                    document.getElementById("console").style.display = "block";
                }
            }
        } catch (e) {
        }
    };
    $scope.checkMenu();
    $scope.logOut = function () {
        document.cookie = "login=";
        document.cookie = "password=";
        document.cookie = "role=";
        $scope.checkMenu();
    };
    $scope.console = function () {
        $window.location.href = "console.html";
    };
});
app.controller('loginCtr', function ($scope, $http, $window) {

    $scope.logIn = function () {
        if ($scope.getLogin.search(";") != -1 || $scope.getPassword.search(";") != -1) {
            $scope.logInResponse = "Nie może zawierać znaków ; ";
        } else {
            $http({
                url: "http://localhost:8080/Contest/web/service/logIn",
                method: "POST",
                data: {"login": $scope.getLogin, "password": Sha3.hash512($scope.getPassword)}
            })
                    .then(function (response) {
                        if (response.data.message == "") {
                            $scope.loginResponse = "Login lub hasło jest niepoprawne";
                        } else {
                            document.cookie = "login=" + $scope.getLogin;
                            document.cookie = "password=" + $scope.getPassword;
                            document.cookie = "role=" + response.data.message;
                            $window.location.href = "index.html";
                        }
                    },
                            function (response) {
                                $scope.loginResponse = "Login lub hasło jest niepoprawne";
                            });
        }
    };
});
app.controller('createAccountCtr', function ($scope, $http, $window) {
    $scope.createAccount = function () {
        $scope.createAccountResponse = "";
        if ($scope.newLogin == null || $scope.newPassword == null || $scope.newPasswordConfirm == null) {
            $scope.createAccountResponse = "Sprawdź czy wprowadzono poprawne dane";
        }
        if ($scope.newLogin.search(";") != -1 || $scope.newPassword.search(";") != -1 ||
                $scope.newPasswordConfirm.search(";") != -1) {
            $scope.createAccountResponse = "Nie może zawierać znaków ; ";
        } else {
            if ($scope.newPassword == $scope.newPasswordConfirm) {
                $http({
                    url: "http://localhost:8080/Contest/web/service/createAccount",
                    method: "POST",
                    data: {"login": $scope.newLogin, "password": Sha3.hash512($scope.newPassword)}
                })
                        .then(function (response) {
                            if (response.data.message == "1") {
                                document.cookie = "login=" + $scope.newLogin;
                                document.cookie = "password=" + $scope.newPassword;
                                document.cookie = "role=USER";
                                $window.location.href = "index.html";
                            } else {
                                $scope.createAccountResponse = "Login jest już w użyciu";
                            }
                        },
                                function (response) {
                                    $scope.createAccountResponse = "Sprawdź czy wprowadzono poprawne dane";
                                });
            } else {
                $scope.createAccountResponse = "Sprawdź czy wprowadzono poprawne dane";
            }
        }
    };
});
app.controller('frontCtr', function ($scope, $http, $window, $timeout) {
    $scope.tickInterval = 1000
    $scope.endOfDay = new Date();
    $scope.endOfDay.setHours(23, 0, 0, 0);
    $scope.clock = $scope.endOfDay - Date.now();

    var tick = function () {
        $scope.clock = $scope.endOfDay - Date.now();
        $timeout(tick, $scope.tickInterval);
    }
    $timeout(tick, $scope.tickInterval);

    $http({
        url: "http://localhost:8080/Contest/web/service/getToDayCompetition",
        method: "GET"
    })
            .then(function (response) {
                if (response.data.title != "") {
                    $scope.theTitle = response.data.title;
                    $scope.theDescription = response.data.description;
                } else {
                    $scope.theTitle = "Dzisiaj nie ma dostępnego konkursu";
                    $scope.theDescription = "";
                }
            });
    $scope.takePart = function () {
        $scope.usedLogin = readCookie("login");
        $scope.usedPasword = readCookie("password");
        $scope.usedRole = readCookie("role");
        if ($scope.usedLogin == "") {
            $window.location.href = "login.html";
        } else {
            $http({
                url: "http://localhost:8080/Contest/web/service/takePart",
                method: "POST",
                data: {"login": $scope.usedLogin, "password": Sha3.hash512($scope.usedPasword)}
            })
                    .then(function (response) {
                        switch (response.data.message) {
                            case "0":
                                $scope.takePartResponse = "Nie możesz wziąć udziału w konkursie";
                                $scope.takePartResponsePositive = "";
                                break;
                            case "1":
                                $scope.takePartResponse = "";
                                $scope.takePartResponsePositive = "Wziąłeś udział w konkursie";
                                break;
                            case "2":
                                $scope.takePartResponse = "Wziąłeś już udział w konkursie";
                                $scope.takePartResponsePositive = "";
                                break;
                        }
                    });
        }
    };
});
app.controller('consoleCtr', function ($scope, $http, $window) {
    $scope.getDate = new Date();
    $scope.usedLogin = readCookie("login");
    $scope.usedPasword = readCookie("password");
    $scope.usedRole = readCookie("role");
    $scope.verifyRole = function () {
        $http({
            url: "http://localhost:8080/Contest/web/service/getRole",
            method: "POST",
            data: {"login": $scope.usedLogin, "password": Sha3.hash512($scope.usedPasword)}
        })
                .then(function (response) {
                    if (response.data.message == "ADMIN") {
                        $window.location.href = "addCompetition.html";
                    }
                });
    };
    $scope.createCompetition = function () {
        if ($scope.getTitle != null && $scope.getDescription != null) {
            $http({
                url: "http://localhost:8080/Contest/web/service/createCompetition",
                method: "POST",
                data: {"login": $scope.usedLogin, "password": Sha3.hash512($scope.usedPasword), "title": $scope.getTitle, "description": $scope.getDescription, "date": dateToString($scope.getDate)}
            })
                    .then(function (response) {
                        switch (response.data.message) {
                            case "0":
                                $scope.createCompetitionResponsePositive = "Konkurs nie może być utworzony";
                                break;
                            case "1":
                                $scope.createCompetitionResponsePositive = "Konkurs został utworzony";
                                break;
                        }
                        $scope.createCompetitionResponse = "";
                    },
                            function (response) {
                                $scope.createCompetitionResponsePositive = "";
                                $scope.createCompetitionResponse = "Sprawdź czy wprowadzono poprawne dane";
                            });
        } else {
            $scope.createCompetitionResponsePositive = "";
            $scope.createCompetitionResponse = "Sprawdź czy wprowadzono poprawne dane";
        }
    };
});

angular.module('myApp.controllers', []).controller('consoleCompetitionsCtr', function ($scope, $http, $window, DTOptionsBuilder, DTColumnBuilder) {

    $scope.allCompetitions = function () {
        $http({
            url: "http://localhost:8080/Contest/web/service/getAllCompetitions",
            method: "GET"
        })
                .then(function (response) {
                    if(response.data.competitionMessage.id != null){
                        $scope.comList = [response.data.competitionMessage];
                    }else{
                        $scope.comList = response.data.competitionMessage;
                    }
                });
    };
    $scope.selectCompetition = function (competitionId) {
        document.cookie = "competitionId=" + competitionId;
        $window.location.href = "selectWinner.html";
    };

    $scope.usersOnCompetition = function () {
        $scope.competitionId = readCookie("competitionId");
        $http({
            url: "http://localhost:8080/Contest/web/service/getCurrentWinners",
            method: "POST",
            data: {"message": $scope.competitionId}
        })
                .then(function (response) {
                    $scope.winner1 = response.data.winnerLogin1;
                    $scope.winner2 = response.data.winnerLogin2;
                    $scope.winner3 = response.data.winnerLogin3;
                });
        $http({
            url: "http://localhost:8080/Contest/web/service/getUsersParticipation",
            method: "POST",
            data: {"message": $scope.competitionId}
        })
                .then(function (response) {
                    $scope.loginList = response.data;
                });
    };

    $scope.selectUser = function (indexLogin) {
        if ($scope.winner1 == "") {
            $scope.winner1 = $scope.loginList[indexLogin].message;
        } else if ($scope.winner2 == "") {
            $scope.winner2 = $scope.loginList[indexLogin].message;
        } else if ($scope.winner3 == "") {
            $scope.winner3 = $scope.loginList[indexLogin].message;
        } else {
            $scope.winner1 = $scope.loginList[indexLogin].message;
        }
    };
    $scope.saveWinners = function () {
        $scope.usedLogin = readCookie("login");
        $scope.usedPasword = readCookie("password");
        $scope.competitionId = readCookie("competitionId");
        if ($scope.competitionId != null) {
            $http({
                url: "http://localhost:8080/Contest/web/service/selectWinners",
                method: "POST",
                data: {"competitionId": $scope.competitionId, "user": {"login": $scope.usedLogin, "password": Sha3.hash512($scope.usedPasword)}
                    , "winnerLogin1": $scope.winner1, "winnerLogin2": $scope.winner2, "winnerLogin3": $scope.winner3}
            })
                    .then(function (response) {
                        switch (response.data.message) {
                            case "0":
                                $scope.saveWinnersResponse = "Nie zapisano";
                                $scope.saveWinnersResponsePositive = "";
                                break;
                            case "1":
                                $scope.saveWinnersResponse = "";
                                $scope.saveWinnersResponsePositive = "Zapisano";
                                break;
                            case "2":
                                $scope.saveWinnersResponse = "Konkurs jeszcze się nie skończył";
                                $scope.saveWinnersResponsePositive = "";
                                break;
                        }
                    });
        }
    };

    $scope.getAllWinners = function () {
        $http({
            url: "http://localhost:8080/Contest/web/service/getAllWinners",
            method: "GET"
        })
                .then(function (response) {
                    if(response.data.winnersMessage.competitionId != null){
                        $scope.winnersList = [response.data.winnersMessage];
                    }else{
                        $scope.winnersList = response.data.winnersMessage;
                    }
                });
    };

    $scope.vm = {};
    $scope.vm.dtOptions = DTOptionsBuilder.newOptions()
            .withOption('order', [0, 'asc']);
});

/* other functions */

function readCookie(name) {
    var nameEQ = name + "=";
    var ca = document.cookie.split(';');
    for (var i = 0; i < ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0) == ' ')
            c = c.substring(1, c.length);
        if (c.indexOf(nameEQ) == 0)
            return c.substring(nameEQ.length, c.length);
    }
    return null;
}

function dateToString(date) {
    var day = date.getDate();
    var month = date.getMonth() + 1;
    var year = date.getFullYear();
    var it = year + "-";
    if (month < 10) {
        it = it + "0" + month + "-";
    } else {
        it = it + month + "-";
    }
    if (day < 10) {
        it = it + "0" + day;
    } else {
        it = it + day;
    }
    return it;
}
