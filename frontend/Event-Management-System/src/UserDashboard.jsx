import Header from "./Header.jsx";
import styles from "./userdashboard.module.css";
import { Calendar, CircleCheckBig,CircleAlert,Plus } from "lucide-react";
import { useState, useEffect } from "react";
import {getCurrentUser} from "./Api.js";
import {getData, getUserStatistics} from "./UserApi.js";
import CreateEventPopup from "./CreateEventPopup.jsx";
import ShowDataForUser from "./ShowDataForUser.jsx";


function UserDashboard() {
    const [data, setData] = useState([]);
    const [error, setError] = useState(null);
    const [selectedTab, setSelectedTab] = useState(
         sessionStorage.getItem("selectedTab") || "Registered Events"
    );
    console.log("selected tab = " + selectedTab)
    const [statistics,setStatistics] = useState({});
    const[user,setUser] = useState({});
    const[showCreateEventPopup,setShowCreateEventPopup] = useState(false);


    const baseUrl = "http://localhost:8080/user";


    const dataTabs = {
        "Registered Events": {
            format: "card",
            api: baseUrl + "/registered",
            ignore:[]
        },
        "Available to Join": {
            format: "card",
            api: baseUrl + "/available-to-join",
            ignore:[]
        },
        "Created Events": {
            format: "table",
            api: baseUrl + "/created-events",
            ignore:["description","host"]
        },
        "Approved Events": {
            format: "table",
            api: baseUrl + "/approved",
            ignore:["status","description","host"]
        },
        "Pending Events": {
            format: "table",
            api: baseUrl + "/pending",
            ignore:["status","description","host"]
        },
    };




    async function handleTabs(e) {
        const selected = e.target.value;
        if(selectedTab === selected) return
        setSelectedTab(selected);
        sessionStorage.setItem("selectedTab", selected);
    }

    useEffect(() => {
        getData(dataTabs[selectedTab].api)
            .then((responseData) => {
                const cleanedData = responseData.map((obj) => {
                    // create a shallow copy
                    const newObj = { ...obj };

                    // remove fields safely
                    dataTabs[selectedTab].ignore.forEach((field) => {
                        delete newObj[field];
                    });

                    return newObj;
                });

                setData(cleanedData);
            });
    }, [selectedTab]);


    useEffect(() => {
        getUserStatistics().then(data=> setStatistics(data))
        getCurrentUser().then(currUser=>setUser(currUser));
    }, []);
    return (
        <>
            <Header panelType={"user"} user={user} />
            <div style={{ padding: "50px 70px 0 70px" }}>
                <div className={styles.txtBtnContainer}>
                    <h1>Hey {user.name}</h1>
                    <button className={styles.createBtn} onClick={()=>{ document.body.style.overflow = "hidden";setShowCreateEventPopup(true)}}><Plus style={{width:"16px",height:"16px",marginRight:"5px"}}></Plus> Create Event</button>
                    {
                        showCreateEventPopup ?
                            <CreateEventPopup closePopup={()=>{document.body.style.overflow = "auto";setShowCreateEventPopup(false)}}/>
                            :
                            <></>
                    }
                </div>
                <p className={styles.subheading}>Explore and register for events happening on campus</p>

                {/* Statistics */}
                <div className={styles.statisticContainer}>
                    <div className={styles.statisticCard}>
                        <div className={styles.stHeading}>
                            <Calendar className={styles.stLogo} />
                            <p>Total Events</p>
                        </div>
                        <h2>{statistics["totalEvents"]}</h2>
                    </div>

                    <div className={styles.statisticCard}>
                        <div className={styles.stHeading}>
                            <Calendar className={styles.stLogo} />
                            <p>My Registrations</p>
                        </div>
                        <h2>{statistics["registered"]}</h2>
                    </div>

                    <div className={styles.statisticCard}>
                        <div className={styles.stHeading}>
                            <CircleCheckBig className={styles.stLogo} />
                            <p>Available to Join</p>
                        </div>
                        <h2>{statistics["availableToJoin"]}</h2>
                    </div>

                    <div className={styles.statisticCard}>
                        <div className={styles.stHeading}>
                            <Calendar className={styles.stLogo} />
                            <p>Created Events</p>
                        </div>
                        <h2>{statistics["createdEvents"]}</h2>
                    </div>

                    <div className={styles.statisticCard}>
                        <div className={styles.stHeading}>
                            <CircleCheckBig className={styles.stLogo} />
                            <p>Approved Events</p>
                        </div>
                        <h2>{statistics["approvedEvents"]}</h2>
                    </div>

                    <div className={styles.statisticCard}>
                        <div className={styles.stHeading}>
                            <CircleAlert className={styles.stLogo} />
                            <p>Pending Events</p>
                        </div>
                        <h2>{statistics["pendingEvents"]}</h2>
                    </div>
                </div>

                {/* Tabs */}
                <div className={styles.tabsContainer}>
                    {Object.keys(dataTabs).map((tab, index) => (
                        <button
                            key={index}
                            value={tab}
                            onClick={handleTabs}
                            className={`${styles.tabs} ${
                                selectedTab === tab ? styles.activeTab : ""
                            }`}
                        >
                            {tab}
                        </button>
                    ))}
                </div>

                <div className={styles.data}>
                    {error ? (
                        <p style={{ color: "red" }}>{error}</p>
                    ) : (
                        <ShowDataForUser data={data} format={dataTabs[selectedTab].format} />
                    )}
                </div>

            </div>
        </>
    );
}

export default UserDashboard;
