
let chatHistory;
let button;
let textarea;
let chatHistoryList;

function init() {
cacheDOM();
bindEvents();
}

function bindEvents() {
button.addEventListener('click', addMessage);
textarea.addEventListener('keyup', addMessageEnter);
}

function cacheDOM() {
chatHistory = document.querySelector('.chat-history');
button = document.querySelector('#send-btn');
textarea = document.querySelector('#chat-input');
chatHistoryList = chatHistory.querySelector('ul');
}

function render(message, userName) {
scrollToBottom();


let contextResponse = {
response: message,
time: getCurrentTime(),
userName: userName
};


if(userName === contextResponse.userName) {

    let templateResponse = `<li class="clearfix" style="display:flex; flex-direction:column; align-items:flex-start;">
    <div class="message-data align-right">
    <span class="message-data-time">${contextResponse.time}, Today</span> &nbsp; &nbsp;
    <span class="message-data-name">${contextResponse.userName}</span> <i class="fa fa-circle me"></i>
    </div>
    <div class="message other-message float-right">
    ${contextResponse.response}
    </div>
    </li>`;
    
    
    setTimeout(function () {
        chatHistoryList.insertAdjacentHTML('beforeend',templateResponse);
        scrollToBottom();
    }.bind(this), 1500);
}
}

function sendMessage(message) {
let username = document.querySelector('#userName').value;
if(username == "") alert('Enter your username to send a message')
sendMsg(username, message);
scrollToBottom();


if (message.trim() !== '') {

var context = {
messageOutput: message,
time: getCurrentTime(),
toUserName: selectedUser
};
var templateResponse = `
<li class="clearfix" style="display:flex; flex-direction:column; align-items:flex-end;">
    <div class="message-data align-right">
        <span class="message-data-time" >${getCurrentTime()}</span> &nbsp; &nbsp;
        <span class="message-data-name" >${username}</span> <i class="fa fa-circle me"></i>
    </div>
    <div class="message other-message float-right">
        ${message}
    </div>
</li>
`;

chatHistoryList.insertAdjacentHTML('beforeend',templateResponse);


    scrollToBottom();
    textarea.value = '';
}
}

function scrollToBottom() {
chatHistory.scrollTop = chatHistory.scrollHeight;
}

function getCurrentTime() {
return new Date().toLocaleTimeString().replace(/([\d]+:[\d]{2})(:[\d]{2})(.*)/, "$1$3");
}

function addMessage() {
    let username = document.querySelector('#userName').value;
    if(selectedUser == null) return; 
    if(selectedUser == username) return;
sendMessage(textarea.value);
}

function addMessageEnter(event) {
    let username = document.querySelector('#userName').value;
    if(selectedUser == null) return; 
    if(selectedUser == username) return;
// enter was pressed
if (event.keyCode === 13) {
addMessage();
}
}

init();