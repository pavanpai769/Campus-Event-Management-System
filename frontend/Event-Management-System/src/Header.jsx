import Logo from "./Logo.jsx";
import styles from "./header.module.css"
import {User, ChevronDown, LogOut} from "lucide-react"
import {useState} from "react";
import {logout} from "./Api.js";
import {useNavigate} from "react-router-dom";

function Header({panelType,user}) {

    const [showUserDetails,setShowUserDetails] = useState(false);

    const navigate = useNavigate()

    function handleLogout(){
        logout().then(status =>{
            if(status){
                navigate("/login")
            }else{
                console.log("could not logout")
            }
        });
    }

    function handleShowUserDetails(){
        setShowUserDetails(true);
        document.body.style.overflow="hidden"
    }


    return (
        <header className={styles.header}>
            <div className={styles.logoTextContainer}>
                <Logo size={40}/>
                <div>
                    <p style={{fontSize:"18px",fontWeight:1000}}>Campus Events</p>
                    <p className={styles.p}>{(panelType === "admin")? "Admin Panel":"Student Portal"}</p>
                </div>
            </div>
            <div className={styles.userContainer} onClick={handleShowUserDetails}>
                <div className={styles.user}>
                    <User className={styles.userLogo}></User>
                </div>
                <div>
                    <h4 style={{fontSize:"15px"}}>{user.username}</h4>
                    <p className={styles.p}>{(panelType === "admin")? "Administrator":"Student"}</p>
                </div>
                <ChevronDown className={styles.chevron} />
            </div>
            {
                showUserDetails ?
                    <div className={styles.window} onClick={()=>{ setShowUserDetails(false); document.body.style.overflow="auto"}}>
                        <div className={styles.userDetails}>
                            <p className={styles.name}>{user.name}</p>
                            <p className={styles.username}>@{user.username}</p>
                            <p className={styles.email}>{user.email}</p>
                            <hr className={styles.hr}/>
                            <p className={styles.logout} onClick={handleLogout} ><LogOut className={styles.logoutLogo}/>Logout</p>
                        </div>
                    </div>
                    :<></>
            }
        </header>
    )

}

export default Header