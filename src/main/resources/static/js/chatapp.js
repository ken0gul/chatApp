const url = 'http://localhost:8080';
let stompClient;
let selectedUser;
let isSelected = false;





window.addEventListener('load', register)
function connectToChat(userName) {
    console.log('connecting to chat...')
    let socket = new SockJS(url+'/chat');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function(frame) {
        console.log('connected to : ' + frame);
       stompClient.subscribe('/topic/messages/'+userName, function(response) {
        let data = JSON.parse(response.body);
        
            if(selectedUser !== data.fromLogin) {
                
                
                document.querySelectorAll('span[data-user]').forEach(i => {
                       
                       if(i.getAttribute('data-user') == data.fromLogin){
                           
                           i.textContent = 'new message';
                           i.style.color = 'red';
                        }
                    
                    })
                
                return;

            }
            render(data.message,data.fromLogin);
         
            
        
       }); 
    })
}

function sendMsg(from,text) {
   
   
    stompClient.send("/app/chat/"+ selectedUser, {}, JSON.stringify({
        fromLogin:from,
        message:text
    }))
   
}

function register() {
   
    let userName = document.getElementById('userName').value;
    fetch(url+"/register/" +userName).then(response => connectToChat(userName)).catch(err => {
        
        if(err.status == 400){
            alert("It'/s a bit busy now. Try again later")
        }
    })
}


function selectUser(userName) {
    isSelected = true;
    if(isSelected) {
        document.querySelectorAll('span[data-user]').forEach(i => {
            if(i.getAttribute('data-user') == userName){
                console.log('yes user is here')
                i.textContent = '';
            }
        })
    }
            
   
   

    selectedUser = userName;

    document.getElementById('selectedUserId').textContent = 'Chat with: ' + selectedUser;


    fetch('/messages').then(response => response.json()).then(data => {
        data.flatMap(i => {
          renderAll(i);
        
    });
    });
   
    
}

function fetchAll() {
    fetch(url+"/fetchAllUsers").then(response => {
        return response.json();
    }).then(data => {
        let users = data;
        
        let user;
        let usersList = document.getElementById('users-list');
        usersList.innerHTML = "";
        for(let i = 0; i < users.length; i++) {
          
            user = users[i];

            usersList.innerHTML +=  `<a href="#" onclick="selectUser('${user}')"><li class="clearfix" style="list-style:none;"><img style="width:24px;"
            src="https://bootdey.com/img/Content/avatar/avatar${i+1}.png"
            alt="avatar">
            <div class="about">
                <div class="name">${user}</div>
                <div class="status">
                    <i class="fa fa-circle online"></i> 
                    <span id="n-msg" data-user='${user}'></span>
                </div>
            </div></li></a>`
        }

        
      
    });
}


function renderAll(data){
    let me = document.getElementById('userName').value;
    let chatHistoryList = document.querySelector('.chat-history');
  
    
    let list = chatHistoryList.querySelector('ul');
    list.innerHTML = '';

       
        if((data[0] == me && data[3] == selectedUser) ||(data[0] == selectedUser && data[3] == me)  ) {
           
            
            let templateResponse = `<li class="clearfix" style="display:flex; flex-direction:column; align-items:flex-${data[3] == selectedUser? 'start' : 'end'};">
        <div class="message-data align-right">
        <span class="message-data-time">${data[2]}, Today</span> &nbsp; &nbsp;
        <span class="message-data-name">${data[3]}</span> <i class="fa fa-circle me"></i>
        </div>
        <div class="message other-message float-right">
        ${data[1]}
        </div>
        </li>`;
        
        
        setTimeout(function () {
            
            list.insertAdjacentHTML('beforeend',templateResponse);
            scrollToBottom();
        }.bind(this), 1);
    }
    
}

