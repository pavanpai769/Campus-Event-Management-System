import {Check, Mail, X, User, Calendar, MapPin, Clock} from "lucide-react";
import styles from "./requestApproveCard.module.css"
import {useNavigate} from "react-router-dom";

function RequestApproveCard({data,handleDeleteRecord}){
    console.log("data"+JSON.stringify(data));
    const navigate = useNavigate();
    async function approve(api){
        console.log(api)
        try{
            const response = await fetch(api,{
                method:"PATCH",
                credentials:"include"
            })

            if(response.ok){
                console.log(response.json())
                navigate(0);
            }
        }catch (err){
            console.err(err)
        }
    }
    return(
        <div className={styles.cardContainer}>{
            data.map( (obj)=>(
                <div className={styles.card}>
                    {
                        (obj.type === "event") ?
                            <>

                                <p className={styles.name}>
                                    {obj.name}
                                </p>
                                <p className={styles.desc}>{obj.description}</p>
                                <div className={styles.details}>
                                    <p className={styles.venue}>
                                        <MapPin style={{height:"17px",width:"17px",marginRight:"10px",color:"#BA4019"}}/>
                                        {obj.venue}
                                    </p>
                                    <p className={styles.venue}>
                                        <Calendar style={{height:"17px",width:"17px",marginRight:"10px",color:"#BA4019"}}/>
                                        {obj.date}
                                    </p>
                                    <p className={styles.venue}>
                                        <Clock style={{height:"17px",width:"17px",marginRight:"10px",color:"#BA4019"}} />
                                        {obj.time}
                                    </p>
                                </div>
                                <p className={styles.host}>Hosted by: @ {obj.host}</p>
                            </>
                            :
                            <>
                                <p className={styles.name}>
                                    <User style={{height:"22px",width:"22px",marginRight:"10px",color:"#BA4019"}}/>
                                    {obj.name}
                                </p>
                                <h4 style={{color:"#737373",marginTop:"10px"}}>@ {obj.username}</h4>
                                <p className={styles.mail}><Mail style={{height:"17px",width:"17px",marginRight:"10px"}}/>{obj.email}</p>
                            </>


                    }
                    <div className={styles.buttonContainer}>
                        <button id={styles.delete} className={styles.button} onClick={()=>handleDeleteRecord(obj)}>Delete<X style={{height:"17px",width:"17px",marginLeft:"10px"}}/></button>
                        <button id={styles.approve} className={styles.button} onClick={()=>approve(obj.approve)}>Approve <Check style={{height:"17px",width:"17px",marginLeft:"10px"}}/></button>
                    </div>
                </div>
                )
            )
       }
        </div>
    )
}

export default RequestApproveCard;