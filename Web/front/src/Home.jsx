import React from 'react'
import { GiVanillaFlower } from "react-icons/gi";
import {useQuery} from '@tanstack/react-query'
import { getData } from './util';
import { MyCard } from './MyCard';
const url='http://localhost:8000/api/categories'
import { TiHome } from "react-icons/ti";
import { CiSearch } from "react-icons/ci";
import { useNavigate } from 'react-router-dom';
import { useState } from 'react';

export const Home = () => {
    const [name,setName]=useState('')
    const {data,isError,error}=useQuery({queryKey:['categories',url],queryFn:getData})
    const navigate=useNavigate()

  return (
    <div className="app" style={{position:'relative'}}>
    <TiHome style={{position:'absolute',top:'10px',left:'10px',fontSize:'2rem',color:'rgb(38, 5, 169)'}}/>
        <div className='home'>
            <div className="title">
                <p>🌿 </p>
                <h1>Természet Kézikönyve</h1>
                <h3>Fedezd fel a növények, gyümölcsök és fűszerek világát!</h3>
                <div className="input10">
                    <form>
                        <input type="email" placeholder="növény neve" value={name} onChange={(e)=>setName(e.target.value)}/>
                        <button type="button" onClick={()=>navigate('/search/'+name)} disabled={!name}><CiSearch/></button>
                    </form>
                </div>
            </div>
            <div className="cards">
                {data && data.map(obj=>
                    <MyCard key={obj.id} {...obj}/>
                )}
            </div>
        </div>
    </div>
  )
}

