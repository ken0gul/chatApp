//const url = 'https://chatapp-ogulcan.up.railway.app';
const url = 'http://localhost:8080';
let stompClient;
let selectedUser;
let isSelected = false;
let subscription;
let users = [];
let sessionUsers = [];


    
window.addEventListener('offline', e => {
   
});

 setInterval(fetchAll,1500);

window.addEventListener('load', () => {
    register();
})
function connectToChat(userName) {
    console.log('connecting to chat...')
    let socket = new SockJS(url+'/chat');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function(frame) {
        console.log('connected to : ' + frame);
        subscription = stompClient.subscribe('/topic/messages/'+userName, function(response) {
        let data = JSON.parse(response.body);
        console.log(data);
            if(selectedUser !== data.fromLogin) {
                notifyUser("You have a new message from " + data.fromLogin);
                
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
   
    
   
    let userName=localStorage.getItem('user');
    if(userName == null) return;
    
    document.querySelector('#userName').value = userName;        
    fetch(url+'/register/' +userName).then(response => connectToChat(userName)).catch(err => {
        
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
    
    fetch(url+'/fetchAllUsers').then(response => {
        return response.json();
    }).then(data => {
        users = data;

      
    //    let username = sessionStorage.getItem('user');
    //    let uName = document.getElementById('userName');
     
        usersArray = data;
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
                    <span id="n-msg" data-user=${user}></span>
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


function removeUser(user) {
    let me = document.getElementById('userName').value;
    stompClient.unsubscribe();
    fetch('/remove',{
        method:'POST',
        headers:{
            'Content-type':'application/JSON'
        },
        body:user
    }).then(response => response.json()).then(data => console.log(data) );
}

document.querySelector('#register-btn').addEventListener('click', (e) => {
    let user = document.querySelector('#userName').value;
   
    let previousUser= localStorage.getItem('user');
    if(previousUser == null) {
        localStorage.setItem('user',user);
    } 
    removeUser(previousUser);
    localStorage.setItem('user',user);



   
})
document.querySelector('#register-btn').addEventListener('click',register);




function notifyUser(message) {
  // Check if notifications are supported
  if (!("Notification" in window)) {
    alert("This browser does not support desktop notification");
  } else if (Notification.permission === "granted") {
    // If permission is granted, create the notification
    new Notification(message);
  } else if (Notification.permission !== "denied") {
    // If permission is not granted, ask for permission
    Notification.requestPermission().then(function (permission) {
      if (permission === "granted") {
        new Notification(message);
      }
    });
  }
}