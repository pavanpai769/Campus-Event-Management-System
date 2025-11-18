
import DataTable from "./DataTable.jsx";
import EventCard from "./EventCard.jsx";

function ShowDataForUser({data,format}){
    if(!data || (Array.isArray(data) && data.length ===0)){
        return <p style={{color:"#737373"}}>No data found</p>;
    }

    const renderConfig = {
        "status":(row)=>(
            <p style={{
                background:(row["status"])? "#DCFCE7" :"#FEF9C2",
                color:(row["status"])?"#449469":"#A4732D",
                display: "inline-block",
                padding:"5px 10px 5px 10px",
                fontSize:"12px",
                borderRadius:"15px"
            }}
            >{ row["status"]? "Approved":"Pending"}
            </p>
        ),

    }

    if(format === "table"){
        return <DataTable data = {data} renderConfig={renderConfig} />
    }else if(format === "card") {
        return <EventCard data={data} />
    }
    return <></>
}

export default ShowDataForUser;