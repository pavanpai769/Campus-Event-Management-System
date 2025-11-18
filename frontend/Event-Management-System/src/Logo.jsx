function Logo(props){
    const style ={
        height: props.size,
        width: props.size,
        background:"#212C87",
        color:"white",
        fontSize : props.size/2,
        display:"flex",
        justifyContent:"center",
        alignItems:"center",
        fontWeight:"bold",
        borderRadius:"12px",
        fontFamily: `-apple-system, BlinkMacSystemFont, "Segoe UI", "Roboto",
                    "Oxygen", "Ubuntu", "Cantarell", "Helvetica Neue", sans-serif`,
    }
    return(
        <div style={style}>
            CE
        </div>
    )
}

export default Logo;