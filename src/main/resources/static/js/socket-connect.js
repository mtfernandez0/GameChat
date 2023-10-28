'use strict';

let stompClient = null;
let user = null;

function connect(){

    user = document.getElementById('userDto').getAttribute('data-user');
    user = JSON.parse(user);

    let socket = new SockJS('/chat');
    stompClient = Stomp.over(socket);

    stompClient.connect({}, subscribeGroupsUpdates, onError);
}

function subscribeGroupsUpdates(){
        stompClient.subscribe('/topic/public', onMessageReceived);
}

function onError(){
    connectingElement.textContent = "Could not connect to WebSocket Server. Please refresh the page.";
    connectingElement.style.color = 'red';
}

function customButton(type, groupId){
    let button = document.createElement('button');
    button.id = 'group-btn-' + groupId;
    switch (type){
        case 'JOIN':
            button.innerText = 'Leave';
            button.setAttribute('onclick', 'leave('+groupId+')');
            button.classList = 'btn btn-outline-danger';
            break;
        case 'LEAVE':
            button.innerText = 'Join';
            button.setAttribute('onclick', 'join('+groupId+')');
            button.classList = 'btn btn-outline-success';
            break;
        default:
            button.innerText = 'Full!';
            button.classList = 'btn btn-outline-secondary disabled';
            break;
    }
    return button;
}

function onMessageReceived(payload){
    var message = JSON.parse(payload.body);

    let button = document.getElementById('group-btn-' + message.groupId);
    let group = document.getElementById('group-' + message.groupId);

    if (message.username === user.username && button){

        button.replaceWith(customButton(message.messageType, message.groupId));
        updateDropdown(message);
    }

    if(group)
        group.querySelector('.participants').innerText = message.group_size+'/5';
}

function updateDropdown(message){

    if (message.messageType === 'LEAVE')
        document.getElementById('group-item-'+message.groupId).remove();
    else if (message.messageType === 'JOIN')
        addGroupDropdownItem(message.group_owner, message.groupId);
}

function addGroupDropdownItem(owner, id){
    let groupsDropdown = document.getElementById('groups-item-dropdown');

    let dropdownItem = document.createElement('li');
    dropdownItem.id = 'group-item-' + id;
    let dropdownLink = document.createElement('a');
    dropdownLink.classList = 'dropdown-item rounded';
    dropdownLink.innerText = owner;
    dropdownLink.href = '/group/' + id;

    dropdownItem.appendChild(dropdownLink)
    groupsDropdown.appendChild(dropdownItem);

    document.getElementById('groups-name-dropdown').classList.add('bg-danger');
}

function join(groupId){
    let joinBtn = document.getElementById('group-btn-' + groupId);

    joinBtn.classList = 'disabled';
    joinBtn.innerText = 'Joining...';
    joinBtn.classList = 'btn btn-outline-secondary';

    stompClient.send(
        '/app/chat.join/'+groupId,
        {}
    )
}

function leave(groupId){
    let leaveBtn = document.getElementById('group-btn-' + groupId);

    leaveBtn.classList = 'disabled';
    leaveBtn.innerText = 'Leaving...';
    leaveBtn.classList = 'btn btn-outline-secondary';

    stompClient.send(
        '/app/chat.leave/'+groupId,
        {}
    )
}