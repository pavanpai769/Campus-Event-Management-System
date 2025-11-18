import {useEffect, useState} from "react";
import {useLocation, useNavigate} from "react-router-dom";

export async function getCurrentUser(){
    try{
        const response = await fetch("http://localhost:8080/auth/me",{
            method:"GET",
            credentials:"include"
        })
        if(response.ok){
            return await response.json();
        }else{
            console.log(response.message)
        }

    }catch (err){
        console.error(err);
    }
}


export async function logout(){
    try {
        const response = await fetch("http://localhost:8080/logout", {
            method: "GET",
            credentials: "include"
        })

        return response.ok

    }catch (err){
        console.error(err)
        return false
    }
}

