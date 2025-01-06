import React, { useEffect, useState } from 'react';
import "../Style/MessageStyle.css";
import InputMessage from '../Components/InputMessage';
import axios from 'axios';

function Message({ sessionId,messages,setMessages,setSessionId }) {

  useEffect(() => {
    
    const fetchMessages = async () => {

      if (sessionId) {
        try {
          const response = await axios.get(`http://localhost:8080/history/${sessionId}`);
          setMessages(response.data);  // Update state with fetched messages
        } catch (err) {
          console.error("Error fetching messages:", err);
        }
      }
    };

    fetchMessages();
  }, [sessionId]);

  const handleSendMessage = async (userMessage) => {
    const newMessage = { prompt: userMessage, response: "..." };
    setMessages((prevMessages) => [...prevMessages, newMessage]);

    try {
      const response = await axios.post('http://localhost:8080/sor', {
        message: userMessage,
        id: sessionId,
      });
      setSessionId(response.data.id);
      const botResponse = response.data.response;
      setMessages((prevMessages) => {
        const updatedMessages = [...prevMessages];
        updatedMessages[updatedMessages.length - 1].response = botResponse;
        return updatedMessages;
      });
    } catch (err) {
      console.error("Error sending message:", err);
    }
  };

  return (
    <div className="chatMessageComponent">
      <div className="message-container">
        {messages.map((msg, index) => (
          <div key={index} className="messageUserBot">
            <div className='message user'>{msg.prompt}</div>
            <div className='message bot'>{msg.response}</div>
          </div>
        ))}
      </div>

      <div className="inputMessageComponent">
        <InputMessage onSendMessage={handleSendMessage} />
      </div>
    </div>
  );
}

export default Message;
