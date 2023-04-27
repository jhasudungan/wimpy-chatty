// UI Component
const buttonSendChatTo = document.getElementById('sendChatButton');

// Socket Configuration
const socket = new SockJS("/ws");
let stompClient = Stomp.over(socket);

function openWebSocketConnectionAndSubscribeToQueue() {
	
    const connectionId = document.getElementById('connectionId').value;
    
    stompClient.connect({}, ()=> {
        stompClient.subscribe("/client/"+connectionId+"/queue", onReceivedMessage);
    }, null);
    
}

function onReceivedMessage(payload){
	
	let incomingChatData = JSON.parse(payload.body);
	
	const thisPageSender =  document.getElementById('senderId').value;
	const thisPageReceiver = document.getElementById('receiverId').value;
	
	let bubbleChatParrent = document.getElementById("chatBubbleParrent");
	
	let columns = document.createElement("div");
	columns.className = "columns";
	
	let column = document.createElement("div");
	
	if (incomingChatData.receiver === thisPageReceiver) {
		column.className = "column is-half";
	} else {
		column.className = "column is-half is-offset-6";
	}
	
	
	let notification = document.createElement("div");
	
	if (incomingChatData.sender === thisPageSender) {
		notification.className = "notification is-white";
	} else {
		notification.className = "notification is-primary";
	}
	
	let content = document.createElement("div");
	content.className = "content";
	
	let p = document.createElement("div");
	
	p.innerText = incomingChatData.messageText;
	
	content.appendChild(p);
	notification.appendChild(content);
	column.appendChild(notification);
	columns.appendChild(column);
	
	bubbleChatParrent.appendChild(columns);
	 
}

function sendMessageToChat() {

	const connectionId = document.getElementById('connectionId').value;
	const sender =  document.getElementById('senderId').value;
	const receiver = document.getElementById('receiverId').value;
	const messageText = document.getElementById('chatMessage').value;
	
	let data = {
        "connectionId" : connectionId,
        "sender" : sender,
        "receiver" : receiver,
        "messageText" : messageText
    }
    
    document.getElementById('chatMessage').value = "";
    stompClient.send("/app/personal", {}, JSON.stringify(data));
    
    let bubbleChatParrent = document.getElementById("chatBubbleParrent");
    
}

buttonSendChatTo.addEventListener('click', sendMessageToChat);

openWebSocketConnectionAndSubscribeToQueue();