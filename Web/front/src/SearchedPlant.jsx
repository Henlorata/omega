import React from 'react'
import { TiHome } from 'react-icons/ti'
import './Search.css'
import { useNavigate, useParams } from 'react-router-dom'
import { useQuery } from '@tanstack/react-query'
import { getData } from './util'
import { Plant } from './Plant'
const url='http://localhost:8000/api/search/'

export const SearchedPlant = () => {
    const {name}=useParams()
    const {data,isError,error}=useQuery({queryKey:['plant',url+name],queryFn:getData,retry:false})
    const navigate=useNavigate()
    data & console.log(data);

  return (
    <div className="search"style={{position:'relative'}} >
        <TiHome onClick={()=>navigate('/')}
        style={{position:'absolute',top:'10px',left:'10px',fontSize:'2rem',color:'rgb(38, 5, 169)'}}/>
        <div className="holder">
            <h2 style={{textAlign:'center',padding:'1rem'}}>A keresett növény: <span className='txt'>{name}</span></h2>
            {data ? 
            <div>
                <Plant name={data[0].name} descr={data[0].descr} photo={data[0].photo}/>
            </div> : <div className='err'>{error && error.response?.data?.msg}</div>}
        </div>
    </div>
  )
}

