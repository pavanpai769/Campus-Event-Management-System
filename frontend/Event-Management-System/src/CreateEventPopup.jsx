import styles from "./createeventpopup.module.css";
import { useState } from "react";
import {createEvent} from "./UserApi.js";
import {useNavigate} from "react-router-dom";

function CreateEventPopup({ closePopup }) {
    const [name, setName] = useState("");
    const [description, setDescription] = useState("");
    const [venue, setVenue] = useState("");

    const defaultDate = new Date().toISOString().split("T")[0];
    const [date, setDate] = useState(defaultDate);
    const [time, setTime] = useState("");

    const [error, setError] = useState("");

    const navigate = useNavigate();

    function validate() {
        if (!name.trim()) {
            setError("Event name is required.");
            return false;
        }

        if (description.length > 200) {
            setError("Description cannot exceed 200 characters.");
            return false;
        }

        if (!venue.trim()) {
            setError("Venue is required.");
            return false;
        }

        if (!date) {
            setError("Please select a date.");
            return false;
        }

        if (!time) {
            setError("Please select a time.");
            return false;
        }

        setError("");
        return true;
    }

    function handleSubmit(e) {
        e.preventDefault();

        if (!validate()) return;

        const event ={
            name,
            description,
            venue,
            date,
            time
        }
        createEvent(event)
            .then((status)=>{
                if(status){
                    navigate(0)
                }else{
                    setError("Something went wrong")
                }
            })
    }

    return (
        <div className={styles.popupOverlay}>
            <form className={styles.popup} onSubmit={handleSubmit}>
                <div>
                    <h2>Create Event</h2>
                    <p className={styles.subheading}>
                        Fill in the event details. Your event will be pending admin approval.
                    </p>
                </div>
                <div className={styles.inputContainer}>
                    <p className={styles.inputHeading}>Event Name</p>
                    <input
                        className={styles.input}
                        type="text"
                        placeholder="Enter Event name"
                        value={name}
                        onChange={(e) => setName(e.target.value)}
                    />
                </div>

                <div className={styles.inputContainer}>
                    <p className={styles.inputHeading}>Description (Optional, Max 200 characters)</p>
                    <textarea
                        className={styles.desc}
                        placeholder="Description"
                        value={description}
                        onChange={(e) => setDescription(e.target.value)}
                    />
                </div>
                <div className={styles.inputContainer}>
                    <p className={styles.inputHeading}>Venue</p>
                    <input
                        className={styles.input}
                        type="text"
                        placeholder="Enter venue"
                        value={venue}
                        onChange={(e) => setVenue(e.target.value)}
                    />
                </div>
            <div className={styles.dateTimeContainer}>
                <div>
                    <p className={styles.inputHeading}>Date</p>
                    <input
                        className={styles.dateTime}
                        type="date"
                        min={defaultDate}
                        value={date}
                        onChange={(e) => setDate(e.target.value)}
                    />
                </div>
                <div>
                    <p className={styles.inputHeading} >Time</p>
                    <input
                        className={styles.dateTime}
                        type="time"
                        value={time}
                        onChange={(e) => setTime(e.target.value)}
                    />
                </div>

            </div >

                <div className={styles.btnContainer}>
                    <button className={styles.btn} type="button" onClick={closePopup}>Cancel</button>
                    <button id={styles.createBtn} className={styles.btn} type="submit">Create</button>
                </div>

                <p style={{ color: "red" }}>{error}</p>
            </form>
        </div>
    );
}

export default CreateEventPopup;
