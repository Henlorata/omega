import { useQuery } from '@tanstack/react-query'
import React from 'react'
import { TiHome } from 'react-icons/ti'
import { useNavigate, useParams } from 'react-router-dom'
import { getData } from './util'
import { Plant } from './Plant'
const url='http://localhost:8000/api/plants/'
import './Plants.css'

export const Plants = () => {
    const {categId}=useParams()
    const {data,isError,error}=useQuery({queryKey:['plants',url+categId],queryFn:getData})
    const navigate=useNavigate()
    data & console.log(data);
    
  return (
    <div className="plants" style={{position:'relative'}}>
            <TiHome onClick={()=>navigate('/')}
            style={{position:'absolute',top:'10px',left:'10px',fontSize:'2rem',color:'rgb(38, 5, 169)'}}/>
            <h2 style={{textAlign:'center',padding:'1rem'}}>{data && data[0].categName}</h2>
           <div className='cardsHolder'>
            {data && data.map(obj=>
                <Plant key={obj.id} {...obj}/>
            )}
            </div>
    </div>
  )
}


