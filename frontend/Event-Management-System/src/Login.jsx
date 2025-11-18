import Logo from "./Logo.jsx";
import styles from "./login.module.css"
import {useState} from "react";
import {Link, useNavigate} from "react-router-dom";


function Login() {
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [errorMessage, setErrorMessage] = useState("");
    const [activeUser, setActiveUser] = useState("user");

    const navigate = useNavigate();

    const handleLogin = async (e) => {
        e.preventDefault();
        if (activeUser === "user") {
            await handleUserLogin();
        } else {
            await handleAdminLogin();
        }
    }

    const handleAdminLogin = async () => {
        try {
            const response = await fetch("http://localhost:8080/login/admin", {
                method: "POST",
                credentials: "include",
                headers: {"Content-Type": "application/x-www-form-urlencoded"},
                body: new URLSearchParams({username, password}),
            });

            console.log(response);

            if (response.ok) {
                navigate("/admin")
            } else if (response.status === 403) {
                setErrorMessage(await response.text())
            } else if (response.status === 401) {
                setErrorMessage("Invalid Username or Password ")
            }
        } catch (error) {
            console.error("Network error:", error);
            alert("Network error. Please try again later.");
        }
    }

    const handleUserLogin = async () => {
        try {
            const response = await fetch("http://localhost:8080/login/user", {
                method: "POST",
                credentials: "include",
                headers: {"Content-Type": "application/x-www-form-urlencoded"},
                body: new URLSearchParams({username, password}),
            });

            console.log(response);

            if (response.ok) {
                navigate("/user")
            } else if (response.status === 403) {
                setErrorMessage(await response.text())
            } else if (response.status === 401) {
                setErrorMessage("Invalid Username or Password ")
            }
        } catch (error) {
            console.error("Network error:", error);
            alert("Network error. Please try again later.");
        }
    }

    return (
        <div className={styles.body}>
            <form className={styles.form} id="form" onSubmit={handleLogin}>
                <Logo size={50}/>
                <h2>
                    Welcome Back
                </h2>
                <p className={styles.text}>
                    Sign in to your campus events account
                </p>
                <div>
                    <h4>Account Type</h4>
                    <div className={styles.accountTypeContainer}>
                        <div
                            className={`${styles.accountType} ${(activeUser === "user") ? styles.activeAccountStyle : styles.inactiveAccountStyle}`}
                            onClick={() => setActiveUser("user")}> User
                        </div>
                        <div
                            className={`${styles.accountType} ${(activeUser === "admin") ? styles.activeAccountStyle : styles.inactiveAccountStyle}`}
                            onClick={() => setActiveUser("admin")}> Admin
                        </div>
                    </div>
                </div>
                <div>
                    <h4>Username</h4>
                    <input
                        className={styles.input}
                        type="text"
                        placeholder="Enter your Username"
                        value={username}
                        onChange={(e) => setUsername(e.target.value)}
                        required
                    />
                </div>

                <div>
                    <h4>Password</h4>
                    <input
                        className={styles.input}
                        type="password"
                        placeholder="Enter your Password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        required
                    />
                </div>
                {
                    (errorMessage === "") ? (<></>) : (
                        <div style={{width: "410px"}}>
                            <p style={{color: "red"}}>
                                {errorMessage}
                            </p>
                        </div>
                    )
                }
                <button className={styles.button}>Sign in</button>
                <p className={styles.text}>Dont have an account ? <Link to="/signup" className={styles.signupTxt}>Sign
                    up</Link></p>
            </form>
        </div>
    )
}

export default Login;