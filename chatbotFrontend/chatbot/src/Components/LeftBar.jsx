


import React, { useEffect, useState } from 'react'
import '../Style/LeftBarStyle.css';
import axios from 'axios';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faTrashCan } from '@fortawesome/free-solid-svg-icons';

function LeftBar({ onSessionChange,setMessages, sessionId, setSessionId }) {
  const [dataMessage, setDataMessage] = useState();
  const [activeMessageId, setActiveMessageId] = useState(null); // Tıklanan mesajı takip etmek için durum

  /* FirstMessage verilerini çekme */
  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await axios.get("http://localhost:8080/sessions");
        setDataMessage(response.data)
        console.log(response.data);
      } catch (error) {
        console.error("Error fetching data:", error.message);
      }
    };

    fetchData();
  }, sessionId);

  const handleNewMessage = () => {
    setSessionId(null);
    setMessages([]);
  }
  /* SessionId alma */
  const handleMessageClick = (sessionId) => {
    onSessionChange(sessionId);
    setActiveMessageId(sessionId);
  };

  const handleDelete = async (sessionId) => {
    try {
      setDataMessage((prevMessages) => prevMessages.filter((item) => item.id !== sessionId));

      await axios.delete(`http://localhost:8080/${sessionId}`);
      console.log(`Session with ID ${sessionId} deleted`);
    } catch (error) {
      console.error("Error deleting session:", error.message);
      setDataMessage((prevMessages) => [...prevMessages]);
    }
  };

  return (
    <div className='leftBarComponnent'>
      <div className='newMesageCompenent'>
        <button className='newMessage' onClick={handleNewMessage}> New Message</button>
      </div>
      <div className='summaryMessageContent'>
        {dataMessage ? (
          <ul className='messageComponent'>
            {dataMessage.slice(-10).map(item => (

              <li className={`messageSummary ${activeMessageId === item.id ? 'active' : ''}`}
                key={item.id} onClick={() => handleMessageClick(item.id)}>
                {item.firstMessage}
                <button className='deleteButton' onClick={(e) => {
                  e.stopPropagation(); // Tıklamanın handleMessageClick'i tetiklemesini önler
                  handleDelete(item.id);
                }}><FontAwesomeIcon icon={faTrashCan} />
                </button>
              </li>
            ))}
          </ul>
        ) : (
          <p>Loading...</p>
        )}
      </div>
    </div>
  )
}

export default LeftBar
