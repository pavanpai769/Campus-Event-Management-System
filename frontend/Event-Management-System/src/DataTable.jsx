import "./DataTable.css";

const DataTable = ({ data=[], renderConfig = {}}) => {

    if (!Array.isArray(data) || data.length === 0) {
        return <p style={{color:"#737373"}}>No data found</p>;
    }

    const columns = Object.keys(data[0]);

    return (
                <div className="data-table-container">
                    <div className="table-wrapper">
                        <table className="data-table">
                            <thead>
                            <tr>
                                {columns
                                    .map((col) => (
                                    <th key={col}>
                                        {col.charAt(0).toUpperCase() + col.slice(1)}
                                    </th>
                                ))}
                            </tr>
                            </thead>
                            <tbody>
                            {data.map((row, i) => (
                                <tr key={i}>
                                    {columns.map((col) => (
                                        <td key={col}>{
                                            renderConfig[col] ?
                                                renderConfig[col](row):
                                                String(row[col])
                                        }
                                        </td>
                                    ))}
                                </tr>
                            ))}
                            </tbody>
                        </table>
                    </div>
                </div>
    );
};

export default DataTable;
