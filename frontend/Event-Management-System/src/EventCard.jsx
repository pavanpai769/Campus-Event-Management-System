import EventCardItem from "./EventCardItem.jsx";

function EventCard({ data }) {
    return (
        <>
            {data.map((obj, index) => (
                <EventCardItem key={index} obj={obj} />
            ))}
        </>
    );
}

export default EventCard