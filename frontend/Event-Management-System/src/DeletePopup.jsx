import styles from "./deletepopup.module.css"
import {useNavigate} from "react-router-dom";
function DeletePopup({data,closeDeletePopup}){
    console.log(data);
    const navigate = useNavigate();
    async function deleteRecord(){
        try{
            const response = await fetch(data["delete"],{
                method:"DELETE",
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
        <div className={styles.popupOverlay}>
            <div className={styles.popupWindow}>
                <p className={styles.type}>Delete {data["type"]}</p>
                <p className={styles.text}>Are you sure you want to delete "<span className={styles.identifier}>{data["name"]}</span>" ?
                    <br/>
                    This action cannot be undone.
                </p>
                <div className={styles.buttonContainer}>
                    <button className={styles.button} onClick={()=> closeDeletePopup()}>Cancel</button>
                    <button id={styles.delete} className={styles.button} onClick={()=>deleteRecord()}>Delete</button>
                </div>
            </div>
        </div>
    )
}

export default DeletePopup;