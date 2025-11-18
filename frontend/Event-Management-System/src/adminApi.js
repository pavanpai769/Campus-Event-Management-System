
export async function getStatisticsForAdmin(){
    try{
        const response = await fetch("http://localhost:8080/admin/statistics",{
            method:"GET",
            credentials:"include"
        })
        console.log(response)
        if(response.ok){
            return await response.json();
        }else{
            console.log(response.message)
        }

    }catch (err){
        console.error(err);
    }
}

