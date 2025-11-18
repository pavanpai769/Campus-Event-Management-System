import styles from "./unregisterPopup.module.css"
import {unregisterEvent} from "./UserApi.js";
import {useState} from "react";
import {useNavigate} from "react-router-dom";

function UnregisterPopup({eventId,closePopup}){
    const[error,setError] = useState();
    const navigate = useNavigate();
    function unregister(){
        unregisterEvent(eventId)
            .then( status =>{
                    if(status){
                        console.log("successful")
                        navigate(0);
                        return;
                    }
                    setError("Something went wrong. please try again")
                }
            )
        setTimeout(()=>{
            closePopup();
        }, 700)
    }
    return <div className={styles.popupOverlay}>
        <div className={styles.popup}>
            {
                error ? <p className={styles.text}>{error}</p>
                    :
                <>
                    <p className={styles.text}> Are you sure you want to Unregister ?</p>
                    <div className={styles.btnContainer}>
                        <button className={styles.btn} onClick={closePopup}>Cancel</button>
                        <button id={styles.unregister} className={styles.btn} onClick={unregister}>Unregister</button>
                    </div>
                </>
        }
        </div>
    </div>
}

export default UnregisterPopup;