

import express from 'express';
import mysql from 'mysql2/promise';
import {configDB} from './configDB.js';
import cors from 'cors'

let connection

try {
    connection = await mysql.createConnection(configDB);
  } catch (err) {
    console.log(err);
  }

const app=express()
app.use(express.json())
app.use(cors())

app.get('/api/plants/:categId',async (request,response)=>{
    const {categId}=request.params
    try {
        const sql = 'SELECT plants.name,plants.descr,plants.photo,categories.name categName FROM plants,categories WHERE plants.categId=categories.id and plants.categId=?'
        const values=[+categId]
        const [rows, fields] = await connection.execute(sql,values);
        response.status(200).send(rows)
      } catch (err) {
            console.log(err);
      }
})

app.get('/api/plant/:id',async (req,resp)=>{
    const { id } = req.params;
        try {
            const sql = 'SELECT * from plants where id=?'
            const values=[ +id] 
            const [rows, fields] = await connection.query(sql,values);//ha több útasítást szeretnénk egyszerre futtani az execute nem engedi!!!
            resp.status(200).send(rows)
        } catch (error) {
             console.log(error);
             response.status(500).json({msg:err})
        } 
})
app.get('/api/search/:name',async (req,resp)=>{
    const { name } = req.params;
        try {
            const sql = 'SELECT * from plants where name=?'
            const values=[name] 
            const [rows, fields] = await connection.query(sql,values);//ha több útasítást szeretnénk egyszerre futtani az execute nem engedi!!!
            if(rows.length>0)    resp.status(200).send(rows)
            else resp.status(404).json({msg:`Nincs találat a/az <${name}> -ra!`})
        } catch (error) {
             console.log(error);
             response.status(500).json({msg:err})
        } 
})



app.get('/api/categories',async (request,response)=>{
    try {
        const sql = 'SELECT * from categories'
        const [rows, fields] = await connection.execute(sql);
        response.status(200).send(rows)
      } catch (err) {
            console.log(err);
      }
})

const port=8000
app.listen(port,()=>console.log(`server listening on port ${port}...`))
