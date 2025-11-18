export async function getUserStatistics(){
    try{
        const response = await fetch("http://localhost:8080/user/statistics", {
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
        console.error("err" + err)
    }
}

export async function createEvent(event){
    console.log(JSON.stringify(event))
    try{
        const response = await fetch("http://localhost:8080/create-event",{
            method:"POST",
            credentials:"include",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(event),
        })

        if(response.ok){
            console.log("Created successfully")
            return true;
        }else{
            console.error("Something went wrong")
            console.log(response.message)
            return false;
        }
    }catch (err){
        console.error(err);
        return false;
    }
}

export async function getData(api){
    console.log(api);
    try{
        const response = await fetch(api,{
            method:"GET",
            credentials:"include"
        })

        if(response.ok){
            return await response.json()
        }
        console.log(response.message)
    } catch (err){
        console.log(err)
    }
}

export async function unregisterEvent(eventId) {
    try{
        const response = await fetch(`http://localhost:8080/user/${eventId}/unregister`,{
            method:"DELETE",
            credentials:"include"
        });

        if(response.ok){
            console.log("successfully unregistered")
            return true;
        }else{
            console.error(response.message)
            return false;
        }
    }catch(err) {
        console.log(err)
        return false;
    }
}

export async function registerEvent(eventId){
    try{
        const response = await fetch(`http://localhost:8080/user/${eventId}/register`,{
            method:"POST",
            credentials:"include"
        });

        if(response.ok){
            console.log("successfully unregistered")
            return true;
        }else{
            console.error(response.message)
            return false;
        }
    }catch(err) {
        console.log(err)
        return false;
    }
}