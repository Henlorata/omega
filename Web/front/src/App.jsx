import { createBrowserRouter, RouterProvider } from 'react-router-dom'
import './App.css'
import { Home } from './Home'
import { Plants } from './Plants'
import { SearchedPlant } from './SearchedPlant'


  const router=createBrowserRouter([
    {path:'/',element:<Home/>},
    {path:'/plants/:categId',element:<Plants/>},
    {path:'/search/:name',element:<SearchedPlant/>}
  ])
  
  function App() {
    
  
    return <RouterProvider router={router} />
  }

export default App
