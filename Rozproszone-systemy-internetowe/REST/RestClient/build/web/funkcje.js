$( document ).ready(function() {
    zaloguj();//wywoluje na poczatku
    
});

function parseXMLText(xml){
$(xml).find("texts").each(function()
{
  $("#tabela").empty();
  $("#tabela").append("<table class='tabela2'>");
  $("#tabela").append("<tr><th>Wynik: </th></tr>");
$(this).find("text").each(function()
{
    $("#tabela").append("<tr><td>"+$(this).find("napis").text()+ "</td></tr></table>")});
});
};

function showHTML(html){
    $("#tabela").empty();
    $("#tabela").append("<table class='tabela2'>");
    $("#tabela").append("<tr><th>Wynik: </th></tr>");
    $("#tabela").append("<tr><td>"+html+ "</td></tr></table>");
};

function zaloguj() {
   $.ajax({
        type: "GET",
        contentType: "text/html; charset=utf-8",
        url: "http://localhost:8080/RestServer/web/Usluga",
        data: {},
        dataType: "html",
        success: function (data) {
            //$('#cos').text(data);
            //$('#xmlCzytelnicy').text(getXmlString(data));
            //parseXMLCzytelnicy(data);
            //alert(getXmlString(data));//data.documentElement.childNodes[0].textContent
            //$('#czasSerwera').text(data.time);
        },
        error: function (data) {
            alert( "error " + data );
        }
        });
}

function Text() {
   $.ajax({
        type: "GET",
        contentType: "application/html; charset=utf-8",
        url: "http://localhost:8080/RestServer/web/Usluga/Text",
        data: {},
        dataType: "xml",
        success: function (data) {
            $("#tabela").empty(); 
            parseXMLText(data);
        }
        });
}




function Reset() {
   $.ajax({
        type: "PUT",
        contentType: "application/xml; charset=utf-8",
        url: "http://localhost:8080/RestServer/web/Usluga/Reset",
        data: {},
        dataType: "xml",
        success: function (data) {
            $("#tabela").empty();
            parseXMLText(data);
        }
        });
}

function Clean() {
   $.ajax({
        type: "DELETE",
        contentType: "application/html; charset=utf-8",
        url: "http://localhost:8080/RestServer/web/Usluga/Clean",
        data: {},
        dataType: "html",
        success: function (data) {
            $("#tabela").empty();
        }
        });
}



function BooksAll() {
   $.ajax({
        type: "GET",
        contentType: "application/xml; charset=utf-8",
        url: "http://localhost:8080/RestServer/web/Usluga/BooksAll/"
        + $("#BooksAll").val(),
        data: {},
        dataType: "xml",
        success: function (data) {
            $("#tabela").empty();
            parseXMLText(data);
        }
        });
}

function BooksAvailable() {
   $.ajax({
        type: "GET",
        contentType: "application/xml; charset=utf-8",
        url: "http://localhost:8080/RestServer/web/Usluga/BooksAvailable",
        data: {},
        dataType: "xml",
        success: function (data) {
            $("#tabela").empty();
            parseXMLText(data);
        }
        });
}


function BooksBorrowed() {
   $.ajax({
        type: "GET",
        contentType: "application/xml; charset=utf-8",
        url: "http://localhost:8080/RestServer/web/Usluga/BooksBorrowed",
        data: {},
        dataType: "xml",
        success: function (data) {
            $("#tabela").empty();
            parseXMLText(data);
        }
        });
}


function BooksHeld() {
   $.ajax({
        type: "GET",
        contentType: "application/xml; charset=utf-8",
        url: "http://localhost:8080/RestServer/web/Usluga/BooksHeld",
        data: {},
        dataType: "xml",
        success: function (data) {
            $("#tabela").empty();
            parseXMLText(data);
        }
        });
}


function BookAdd() {
   $.ajax({
        //type: "GET",
        type: "POST",
        contentType: "application/html; charset=utf-8",
        url: "http://localhost:8080/RestServer/web/Usluga/BookAdd/"
        + $("#tytul").val() + "/" + $("#autor").val(),
        data: {},
        dataType: "html",
        success: function (data) {
            $("#tabela").empty();
            parseXMLText(data);
        }
        });
}


function RemoveBook() {
   $.ajax({
        //type: "GET",
        type: "DELETE",
        contentType: "application/html; charset=utf-8",
        url: "http://localhost:8080/RestServer/web/Usluga/RemoveBook/"
        + $("#RemoveBook").val(),
        data: {},
        dataType: "html",
        success: function (data) {
            $("#tabela").empty();
            parseXMLText(data);
        }
        });
}


function BookGive() {
   $.ajax({
        //type: "GET",
        type: "POST",
        contentType: "application/html; charset=utf-8",
        url: "http://localhost:8080/RestServer/web/Usluga/BookGive/"
        + $("#nrks").val() + "/" + $("#nrcz").val() + "/" + $("#dni").val(),
        data: {},
        dataType: "html",
        success: function (data) {
            $("#tabela").empty();
            parseXMLText(data);
        }
        });
}


function ReaderAll() {
   $.ajax({
        type: "GET",
        contentType: "application/xml; charset=utf-8",
        url: "http://localhost:8080/RestServer/web/Usluga/ReaderAll/"
        + $("#ReaderAll").val(),
        data: {},
        dataType: "xml",
        success: function (data) {
            $("#tabela").empty();
            parseXMLText(data);
        }
        });
}


function ReaderStatus() {
   $.ajax({
        type: "GET",
        contentType: "application/xml; charset=utf-8",
        url: "http://localhost:8080/RestServer/web/Usluga/ReaderStatus/"
        + $("#ReaderStatus").val(),
        data: {},
        dataType: "xml",
        success: function (data) {
            $("#tabela").empty();
            parseXMLText(data);
        }
        });
}



function ReaderBorrower() {
   $.ajax({
        type: "GET",
        contentType: "application/xml; charset=utf-8",
        url: "http://localhost:8080/RestServer/web/Usluga/ReaderBorrower",
        data: {},
        dataType: "xml",
        success: function (data) {
            $("#tabela").empty();
            parseXMLText(data);
        }
        });
}


function ReaderAdd() {
   $.ajax({
        //type: "GET",
        type: "POST",
        contentType: "application/html; charset=utf-8",
        url: "http://localhost:8080/RestServer/web/Usluga/ReaderAdd/"
        + $("#imie").val() + "/" + $("#nazwisko").val(),
        data: {},
        dataType: "html",
        success: function (data) {
            parseXMLText(data);
        }
        });
}


function RemoveReader() {
   $.ajax({
        //type: "GET",
        type: "DELETE",
        contentType: "application/html; charset=utf-8",
        url: "http://localhost:8080/RestServer/web/Usluga/RemoveReader/"
        + $("#RemoveReader").val(),
        data: {},
        dataType: "html",
        success: function (data) {
            parseXMLText(data);
        }
        });
}


function BookReturn() {
   $.ajax({
        //type: "GET",
        type: "DELETE",
        contentType: "application/html; charset=utf-8",
        url: "http://localhost:8080/RestServer/web/Usluga/BookReturn/"
        + $("#BookReturn").val(),
        data: {},
        dataType: "html",
        success: function (data) {
            $("#tabela").empty();
            parseXMLText(data);
        }
        });
}


function AllStatus() {
   $.ajax({
        type: "GET",
        contentType: "application/xml; charset=utf-8",
        url: "http://localhost:8080/RestServer/web/Usluga/AllStatus",
        data: {},
        dataType: "xml",
        success: function (data) {
            $("#tabela").empty();
            parseXMLText(data);
        }
        });
}


function BorrowedStatus() {
   $.ajax({
        type: "GET",
        contentType: "application/xml; charset=utf-8",
        url: "http://localhost:8080/RestServer/web/Usluga/BorrowedStatus",
        data: {},
        dataType: "xml",
        success: function (data) {
            $("#tabela").empty();
            parseXMLText(data);
        }
        });
}


function UpdateAll() {
   $.ajax({
        //type: "GET",
        type: "PUT",
        contentType: "application/xml; charset=utf-8",
        url: "http://localhost:8080/RestServer/web/Usluga/UpdateAll",
        data: {},
        dataType: "xml",
        success: function (data) {
            $("#tabela").empty();
            parseXMLText(data);
        }
        });
}