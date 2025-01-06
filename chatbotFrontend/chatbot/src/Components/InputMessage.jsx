import React, { useState } from 'react'
import arrow from "../static/rightarrow.png"
function InputMessage({onSendMessage}) {
  const [inputValue, setInputValue] = useState('');

  const handleSendMesage =()=>{
    if(inputValue.trim()){
        onSendMessage(inputValue);
        setInputValue('');
    }
  }
  return (
    <div>

      <div className='inputTextContent d-flex gap-2 justify-content-center'>
        <textarea className='input' value={inputValue} onChange={(e) => setInputValue(e.target.value)} ></textarea>
        <button className='sendButton' onClick={handleSendMesage}>
          <img src={arrow} alt="Right Arrow" className="btn-img" />
        </button>
      </div>
    </div>
  )
}

export default InputMessage
