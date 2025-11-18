import DataTable from "./DataTable.jsx";
import RequestApproveCard from "./RequestApproveCard.jsx";
import {Trash2} from "lucide-react";


function ShowDataForAdmin({data,format,handleDeleteRecord}){

    if((Array.isArray(data) && data.length===0)){
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

        "delete": (row)=> {
            const deleteRecordData = {}
            if("username" in row){
                deleteRecordData.type = "user";
                deleteRecordData.name = row["name"]
                deleteRecordData.delete = row["delete"]
            }else if("name" in row){
                deleteRecordData.type = "event";
                deleteRecordData.name = row["name"];
                deleteRecordData.delete = row["delete"]
            }
            return <button style={{
                border: "none",
                background: "none",

            }}

                           onClick={() => handleDeleteRecord(deleteRecordData)}
            >
                <Trash2 style={{
                    height: "16px",
                    width: "16px",
                    color: "#9a0101"
                }}/>
            </button>
        },


    }

    if(format === "table"){
        return <DataTable data = {data} renderConfig={renderConfig}/>
    }else if(format === "card") {
        return <RequestApproveCard data={data} handleDeleteRecord={handleDeleteRecord} />
    }
    return <></>
}

export default ShowDataForAdmin;