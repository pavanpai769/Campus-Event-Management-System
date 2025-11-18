import Header from "./Header.jsx";
import styles from "./admindashboard.module.css";
import { Calendar, CircleAlert, CircleCheckBig, Users } from "lucide-react";
import ShowDataForAdmin from "./ShowDataForAdmin.jsx";
import { useState, useEffect } from "react";
import DeletePopup from "./DeletePopup.jsx";
import { getStatisticsForAdmin} from "./adminApi.js";
import {getCurrentUser} from "./Api.js";


function AdminDashboard() {
    const [data, setData] = useState([]);
    const [error, setError] = useState(null);
    const [selectedTab, setSelectedTab] = useState(
        sessionStorage.getItem("selectedTab") || "All Events"
    );
    const [statistics,setStatistics] = useState({});
    const [showDeletePopup, setShowDeletePopup] = useState(false);
    const [deleteRecordData,setDeleteRecordData] = useState({});
    const[user,setUser] = useState({});

    function closeDeletePopup(){
        setShowDeletePopup(false);
        setDeleteRecordData({});
        document.body.style.overflow = 'auto'

    }

    const baseUrl = "http://localhost:8080/admin";


    const dataTabs = {
        "All Events": {
            description:"View all events (approved and pending)",
            format: "table",
            api: baseUrl + "/events",
            ignore:["type","description","approve"]
        },
        "Approved Events": {
            description:"Manage all approved events on the platform",
            format: "table",
            api: baseUrl + "/events/approved",
            ignore:["status","description","type","approve"]
        },
        "Pending Events": {
            description:"Review and approve event requests",
            format: "card",
            api: baseUrl + "/events/pending",
            ignore:[]
        },
        "All Users": {
            description:"Manage all registered users on the platform",
            format: "table",
            api: baseUrl + "/users",
            ignore:["approve","type"]
        },
        "Approved Users": {
            description:"Manage approved user accounts",
            format: "table",
            api: baseUrl + "/users/approved",
            ignore:["status","type","approve"]
        },
        "Pending Users": {
            description:"Review and approve user registration requests",
            format: "card",
            api: baseUrl + "/users/pending",
            ignore:[]
        },
    };

    function addDetails(tab, object) {
        if (tab.includes("Event")) {
            const eventId = object["event Id"];
            return {
                type:"event",
                delete:baseUrl + "/events/" + eventId + "/delete",
                approve: baseUrl+"/events/"+eventId+"/approve"
            }
        }
        const userId = object["user Id"];
        return {
            type:"user",
            delete:baseUrl + "/users/" + userId + "/delete",
            approve: baseUrl+"/users/"+userId+"/approve"
        }

    }

    async function fetchData(tabName) {
        const { api, ignore = [] } = dataTabs[tabName];

        try {
            const res = await fetch(api, {
                method: "GET",
                credentials: "include",
            });

            if (!res.ok) throw new Error("Failed to fetch data");

            let json = await res.json();
            json = Array.isArray(json) ? json : [json];

            const updatedJson = json
                .filter((obj) => Object.keys(obj).length > 0)
                .map((obj) => {
                    const merged = { ...obj, ...addDetails(tabName, obj) };
                    ignore.forEach((field) => delete merged[field]);
                    return merged;
                });

            setData(updatedJson);
            setError(null);
        } catch (err) {
            setError(err.message);
            setData([]);
        }
    }


    async function handleTabs(e) {
        const selected = e.target.value;
        if(selectedTab === selected) return
        setSelectedTab(selected);
        sessionStorage.setItem("selectedTab", selected); // ✅ store selected tab
        await fetchData(selected);
    }

    function handleDeleteRecord(record){
        setShowDeletePopup(true);
        setDeleteRecordData(record);
        document.body.style.overflow = 'hidden'
    }

    useEffect(() => {
        // ✅ when page loads, fetch last selected tab data
        fetchData(selectedTab);
    }, [selectedTab]);

    useEffect(() => {
        getStatisticsForAdmin().then(data=> setStatistics(data))
        getCurrentUser().then(currUser=>setUser(currUser));
    }, []);
    return (
        <>
            <Header panelType={"admin"} user={user} />
            <div style={{ padding: "50px 70px 0 70px" }}>
                <h1>Admin Dashboard</h1>
                <p className={styles.subheading}>Manage events, users, and approvals</p>

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

                    <div className={styles.statisticCard}>
                        <div className={styles.stHeading}>
                            <Users className={styles.stLogo} />
                            <p>Total Users</p>
                        </div>
                        <h2>{statistics["totalUsers"]}</h2>
                    </div>

                    <div className={styles.statisticCard}>
                        <div className={styles.stHeading}>
                            <CircleCheckBig className={styles.stLogo} />
                            <p>Approved Users</p>
                        </div>
                        <h2>{statistics["approvedUsers"]}</h2>
                    </div>

                    <div className={styles.statisticCard}>
                        <div className={styles.stHeading}>
                            <CircleAlert className={styles.stLogo} />
                            <p>Pending Users</p>
                        </div>
                        <h2>{statistics["pendingUsers"]}</h2>
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

                <div className={styles.dataContainer}>
                    <h3>{selectedTab}</h3>
                    <p className={styles.dataDescription}>{dataTabs[selectedTab].description}</p>
                    <div className={styles.data}>
                        {error ? (
                            <p style={{ color: "red" }}>{error}</p>
                        ) : (
                            <ShowDataForAdmin data={data} format={dataTabs[selectedTab].format} handleDeleteRecord={handleDeleteRecord} />
                        )}
                    </div>
                </div>
            </div>
            {
                showDeletePopup ? <DeletePopup data ={deleteRecordData} closeDeletePopup={closeDeletePopup} /> : <></>
            }
        </>
    );
}

export default AdminDashboard;
