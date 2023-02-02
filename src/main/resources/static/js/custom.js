
let chatHistory;
let button;
let textarea;
let chatHistoryList;
let userSpan = document.getElementById('userName').value;
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

function render(message, userSpan) {
scrollToBottom();


let contextResponse = {
response: message,
time: getCurrentTime(),
userName: userSpan
};


if(userSpan === contextResponse.userName) {

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
    let userSpan = document.getElementById('userName').value;
if(userSpan == "") alert('Enter your username to send a message');
console.log("USERSPAN " + userSpan)
sendMsg(userSpan, message, getCurrentTime());
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
        <span class="message-data-time" >${context.time}</span> &nbsp; &nbsp;
        <span class="message-data-name" >${context.toUserName}</span> <i class="fa fa-circle me"></i>
    </div>
    <div class="message other-message float-right">
        ${context.messageOutput}
    </div>
</li>
`;

chatHistoryList.insertAdjacentHTML('beforeend',templateResponse);


    scrollToBottom();
    textarea.value = '';
}
}

function scrollToBottom() {
chatHistoryList.scrollTop = chatHistoryList.scrollHeight;

}

function getCurrentTime() {
return new Date().toLocaleTimeString().replace(/([\d]+:[\d]{2})(:[\d]{2})(.*)/, "$1$3");
}

function addMessage() {
    console.log("SELECTED " + selectedUser)
    // let username = document.querySelector('#username').value;
    if(selectedUser == null) return; 
    if(selectedUser == userSpan) return;
sendMessage(textarea.value);
}

function addMessageEnter(event) {
    // let userSpan = document.getElementById('user-span').textContent;
    let username = document.querySelector('#userName').value;
    if(selectedUser == null) return; 
    if(selectedUser == username) return;
// enter was pressed
if (event.keyCode === 13) {
addMessage();
}
}

init();