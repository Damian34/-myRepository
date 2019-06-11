

function splitMessage(str) {
    var message = "";
    var message2 = "";
    var i;
    for (i = 0; i < 5; i++) {
        message = message + str[i];
    }
    for (i = 5; i < str.length; i++) {
        message2 = message2 + str[i];
    }
    return [message, message2];
}

function setVisible(vis) {
    try {
        if (!vis) {
            document.getElementById('div1').style.visibility = 'visible';
            document.getElementById('div2').style.visibility = 'hidden';
        } else {
            document.getElementById('div1').style.visibility = 'hidden';
            document.getElementById('div2').style.visibility = 'visible';
        }
    } catch (e) {
    }
}