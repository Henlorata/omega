
import React from 'react';
import './MyCard.css'
import { useNavigate } from 'react-router-dom';

export const MyCard = ({id,name,photo}) => {
  const navigate=useNavigate()
  return (
    <div className="card25 mt-16">
      <div className="card25-header">
        <img
          src={photo}
          alt={name}
        />
      </div>
      <div className="card25-details">
          <button onClick={()=>navigate('/plants/'+id)} className="card25-btn">{name}</button>
      </div>
    </div>
  );
};

