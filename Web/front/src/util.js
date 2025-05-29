import axios from "axios";

export const getData=async ({queryKey})=>{
    const response=await axios.get(queryKey[1])
    return response.data
}

