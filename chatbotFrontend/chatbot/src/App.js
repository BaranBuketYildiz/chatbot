import './App.css';
import React, { useState } from 'react';

import LeftBar from './Components/LeftBar';
import Message from './Components/Message';
function App() {
  const [sessionId, setSessionId] = useState(null);
  const [messages, setMessages] = useState([]);

  // onSessionChange fonksiyonu, sol taraftaki sekmelere tıklanınca çağrılır
  const handleSessionChange = (newSessionId) => {
    setSessionId(newSessionId);
  };
  return (
    <div className='app' >
  <LeftBar onSessionChange={handleSessionChange} sessionId={sessionId} setSessionId={setSessionId}   messages={messages} setMessages={setMessages} /> 
  <Message sessionId={sessionId} setSessionId={setSessionId}  messages={messages} setMessages={setMessages} />

    </div>
  );
}

export default App;
