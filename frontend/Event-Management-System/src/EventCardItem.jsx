import {useState} from "react";
import {MapPin, Calendar, Clock, User, Users, Mail} from "lucide-react";
import {getData, registerEvent} from "./UserApi.js";
import UnregisterPopup from "./UnregisterPopup.jsx";
import styles from "./eventcarditem.module.css";
import {useNavigate} from "react-router-dom";

export default function EventCardItem({ obj }) {

    const [showHost, setShowHost] = useState(false);
    const [host, setHost] = useState({});
    const [showUnregisterPopup, setShowUnregisterPopup] = useState(false);

    const navigate = useNavigate();

    const event = obj.registrationId ? obj.event : obj;

    function handleShowHost(hostUsername) {
        if (showHost) {
            setShowHost(false);
        } else {
            setShowHost(true);
            getData("http://localhost:8080/user/" + hostUsername)
                .then(hostData => {
                    if (hostData) setHost(hostData);
                });
        }
    }

    function handleRegister(e, eventId) {
        if (e.target.value === "register") {
            registerEvent(eventId).then(status => {
                if (status) navigate(0);
                else alert("Error occurred");
            });
        } else {
            setShowUnregisterPopup(true);
        }
    }

    return (
        <div className={styles.eventCard}>
            {obj.registrationId && (
                <div className={styles.regId}>
                    <u>Registration Id </u>: #{obj.registrationId}
                </div>
            )}

            <h3>{event.name}</h3>
            <p className={styles.eventDesc}>{event.description}</p>
            <br />

            <div className={styles.detailsContainer}>
                <p className={styles.detail}><MapPin className={styles.detailsLogo} />{event.venue}</p>
                <p className={styles.detail}><Calendar className={styles.detailsLogo} /> {event.date}</p>
                <p className={styles.detail}><Clock className={styles.detailsLogo} />{event.time}</p>
            </div>

            <hr className={styles.line} />

            <div className={styles.btnContainer}>
                <button
                    id={styles.host}
                    className={styles.btn}
                    onClick={() => handleShowHost(event.host)}
                >
                    <User className={styles.btnLogo} />Host details
                </button>

                {showHost && (
                    <div className={styles.hostDetailsContainer}>
                        <div className={styles.hostDetails}>
                            <User className={styles.hostLogo} />
                            <p className={styles.hostName}>
                                {host.name}
                                <br />
                                <span className={styles.hostUsername}>@{host.username}</span>
                            </p>
                        </div>
                        <p className={styles.hostEmail}>
                            <Mail className={styles.hostLogo} />{host.email}
                        </p>
                    </div>
                )}

                <button
                    id={styles.register}
                    value={obj.registrationId ? "unregister" : "register"}
                    className={styles.btn}
                    onClick={(e) => handleRegister(e, event["event Id"])}
                >
                    <Users className={styles.btnLogo} />
                    {obj.registrationId ? "Unregister" : "Register"}
                </button>
            </div>

            {showUnregisterPopup && (
                <UnregisterPopup
                    eventId={event["event Id"]}
                    closePopup={() => setShowUnregisterPopup(false)}
                />
            )}
        </div>
    );
}
