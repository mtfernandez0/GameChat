/*
const url = "http://localhost:8080";
let stompClient;
let selectedUser;

function connectToChat(userId) {
    console.log("Establishing a connection with the chat...")
    let socket = new SockJS(url + "/chat")
    stompClient = Stomp.over(socket)
    stompClient.connect({}, (frame) => {
        console.log("Connected: " + frame)
        console.log("Subscribing to chat with the user: " + userId)
        stompClient.subscribe("/user/" + userId, (res) => {
            let data = JSON.parse(res.body)
            console.log(data)
        })
    })
}

function sendMessage(userId, idFrom, message){
    stompClient.send("/app/chat" + selectedUser, {}, JSON.stringify({
        idReceiver: userId,
        fromLogin: idFrom,
        message: message
    }))
}

function register(){
    let userId = document.getElementById("userId").value;
    $.get(url + "/registration/" + userId, function (res) {
        connectToChat(userId)
    }).fail(function (error) {
        console.log(error)
    })
}

function selectUser(idReceiver){
    console.log("Chatting with : " + idReceiver)
    selectedUser = idReceiver
}

function fetchAll(){
    $.get(url + "/fetchAll", function (res) {
        let ids = res;
        let templateHtml = ""
        for (let i = 0; i < ids.length; i++) {
            templateHtml += "<a href='#' onclick='selectUser(\"" + ids[i] + "\")'></a><h2>" + ids[i] + "</h2></a>"
        }
        $("#userList").html(templateHtml)
    })
}*/
