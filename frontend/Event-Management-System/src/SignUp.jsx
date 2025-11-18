import Logo from "./Logo.jsx";
import styles from "./login.module.css";
import { Link, useNavigate } from "react-router-dom";
import { useState } from "react";

function SignUp() {
    const navigate = useNavigate(); // to redirect after signup

    // Form state
    const [name, setName] = useState("");
    const [email, setEmail] = useState("");
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [errorMessage,setErrorMessage] = useState("");

    const handleSignup = async (e) => {
        e.preventDefault(); // prevent page reload

        const payload = {
            name,
            email,
            username,
            password,
        };

        try {
            const response = await fetch("http://localhost:8080/public/create-user", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(payload),
            });

            const data = await response.json();

            if (response.ok) {
                navigate("/login");
            } else {
                setErrorMessage(data.message+" !!!");
                console.log(data.message);
            }
        } catch (err) {
            console.error(err);
            alert("Network or server error!");
        }
    };

    return (
        <div className={styles.body}>
            <form className={styles.form} id="form" onSubmit={handleSignup}>
                <Logo size={50}/>
                <h2>Create Account</h2>
                <p className={styles.text}>Join our campus events community</p>

                <div>
                    <h4>Name</h4>
                    <input
                        className={styles.input}
                        type="text"
                        placeholder="Enter your Name"
                        value={name}
                        onChange={(e) => setName(e.target.value)}
                        required
                    />
                </div>

                <div>
                    <h4>Email</h4>
                    <input
                        className={styles.input}
                        type="email"
                        placeholder="Enter your email"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                        required
                    />
                </div>

                <div>
                    <h4>Username</h4>
                    <input
                        className={styles.input}
                        type="text"
                        placeholder="Choose your Username"
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
                    (errorMessage === "")? (<></>):(
                        <div style={{width:"410px"}}>
                            <p style={{color:"red"}}>
                                {errorMessage}
                            </p>
                        </div>
                    )
                }

                <button className={styles.button} type="submit">Sign Up
                </button>

                <p className={styles.text}>
                    Already have an account?{" "}
                    <Link to="/login" className={styles.signupTxt}>
                        Sign in
                    </Link>
                </p>
            </form>
        </div>
    );
}

export default SignUp;
