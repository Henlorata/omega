import React from 'react'


export const Plant = ({name,descr,photo}) => {
  return (
     <div className="plant" >
     <div>
        <img src={photo} alt={name} />
     </div>
     <div>
        <h4>{name}</h4>
        <p>{descr}</p>
     </div>
    </div>
  )
}

