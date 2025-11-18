import {useLocation, useNavigate} from "react-router-dom";
import {useEffect, useState} from "react";

export default function HandleRedirect({element}){
    const [loading,setLoading] = useState(true);
    const navigate = useNavigate();
    const location = useLocation();
    useEffect(() => {
        fetch("http://localhost:8080/auth/checkAuth",{
            method:"GET",
            credentials:"include"
        })
            .then(async res =>{
                if(res.status === 401){
                    if(location.pathname !== "/login" && location.pathname!== "/signup"){
                        navigate("/login",{replace:true})
                    }
                    setLoading(false);
                    return
                }

                const data = await res.json()
                const role = data.role.toLocaleLowerCase();

                navigate(`/${role}`,{replace:true});
                setLoading(false);
            })
    }, [location.pathname]);
    return loading ? <p>Loading....</p> : element;
}