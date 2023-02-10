 const url = 'https://chatapp-ogulcan.up.railway.app';
 //const url = 'http://localhost:8080';
let stompClient;
let selectedUser;
let isSelected = false;
let subscription;
let users = [];
let sessionUsers = [];
let messages = [];
let myData;
let messageFrom;
let socket;




function connectToChat(userName) {
    console.log('connecting to chat...')
    socket = new SockJS(url+'/chat');
    socket.addEventListener('open', e => {
        console.log('New user is subscribed the channel')
        fetchAll();
        
    })
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function(frame) {
        console.log('connected to : ' + frame);
	
     
         
         
         
    
        let user = userName;
        subscription = stompClient.subscribe('/topic/messages/'+user, function(response) {
            let data = JSON.parse(response.body);
           
               	socket.onmessage = e => { 
					   
					   renderNotification(data)
					   
					   }   
                
                if(selectedUser !== data.msgFrom ) {
					console.log('Yeah it works')
                    messageFrom = data;
                    renderNotification(data);
                    
                } else {
                    
                    render(data.message,data.msgFrom);
                }
       }); 
     
    })
}



function renderNotification(data){
		
    document.querySelectorAll('span[data-user]').forEach(i => {
                       
        if(i.getAttribute('data-user') == data.msgFrom){
            notifyUser("You have a message dude!",data)
            i.textContent = 'new message';
            i.style.color = 'red';
         }
     
     })
 return;
}


function sendMsg(from,text,date) {

   
    stompClient.send("/app/chat/"+ selectedUser, {}, JSON.stringify({
        message:text,
        msgFrom:from,
        date:date,
        sender:from,
        receiver:selectedUser
        
       
        
    }))
   
}

function register() {
    setInterval(fetchAll,10000);
          
    fetch(url+'/register-chat').then(response => response.json()).then(data => {
        console.log(data)
        connectToChat(data.username)
        document.querySelector('#userName').value = data.username; 
    }).catch(err => {
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
               
                i.textContent = '';
            }
        })
    }
            
   
   

    selectedUser = userName;

    document.getElementById('selectedUserId').textContent = 'Chat with: ' + selectedUser;
   

   

    fetch('/messages').then(response => response.json()).then(data => {
        
        data.flatMap(i => {
           
          renderAll(i.fromLogin, i.message,i.date,i.receiver.username);
        
    });
    });
   
    
}

function fetchAll() {
	
    fetch(url+'/fetchAllUsers').then(response => {
        return response.json();
    }).then(data => {
        users = data;
     
        let user;
        let usersList = document.getElementById('users-list');
        usersList.innerHTML = "";
       

        
        for(let i = 0; i < users.length; i++) {
            
            user = users[i];
            
            usersList.innerHTML +=  `<a href="#" onclick="selectUser('${user.username}')"><li class="clearfix" style="list-style:none;"><img style="width:24px;"
            src="https://i.pravatar.cc/150?img=${i+1}"
            alt="avatar">
            <div class="about">
                <div class="name">${user.username}</div>
                <div class="status">
                    <i class="fa fa-circle online"></i> 
                    <span id="n-msg" data-user="${user.username}"></span>
                </div>
            </div></li></a>`
        }

        
      
    }).catch(err => console.log(err));
}


document.querySelector('#refresh').addEventListener('click', fetchAll);

function renderAll(from,message,date,receiver){
    
    
    let me =  document.getElementById('userName').value;
    let chatHistoryList = document.querySelector('.chat-history');
  
    
    let list = chatHistoryList.querySelector('ul');
    list.innerHTML = '';

       
        if((from== me && receiver == selectedUser) ||(receiver == me && from == selectedUser)  ) {
         
            
            let templateResponse = `<li class="clearfix" style="display:flex; flex-direction:column; align-items:flex-${from == selectedUser? 'start' : 'end'};">
        <div class="message-data align-right">
        <span class="message-data-time">${date}, Today</span> &nbsp; &nbsp;
        <span class="message-data-name">${from}</span> <i class="fa fa-circle me"></i>
        </div>
        <div class="message other-message float-right">
        ${message}
        </div>
        </li>`;
        
        
        setTimeout(function () {
            
            list.insertAdjacentHTML('beforeend',templateResponse);
            scrollToBottom();
        }.bind(this), 1);
    }
    
}


function removeUser() {
    let me = document.getElementById('userName').value;
    stompClient.unsubscribe();
    fetch('/remove',{
        method:'POST',
        headers:{
            'Content-type':'application/JSON'
        },
        body:me
    }).then(response => response.json()).then(data => console.log(data) );
}

// document.querySelector('#register-btn').addEventListener('click', (e) => {
 



   
// })
document.querySelector('#register-btn').addEventListener('click',register);




 function notifyUser(message,data) {
   // Check if notifications are supported
   if (!("Notification" in window)) {
     alert("This browser does not support desktop notification");
   } else if (Notification.permission === "granted") {
     // If permission is granted, create the notification
     let notification =new Notification(message);
     notification.onclick = render(data.message,data.msgFrom)
   } else if (Notification.permission !== "denied") {    
	    // If permission is not granted, ask for permission
     Notification.requestPermission().then(function (permission) {
       if (permission === "granted") {
        let notification= new Notification(message);
         notification.onclick = render(data.message,data.msgFrom)
       }
     });
   }
 }



