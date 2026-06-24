function ResultTable({ rows }) {

    if (!rows || rows.length === 0) {
        return (
            <div className="mt-6 text-gray-400">
                No results found
            </div>
        );
    }

    return (
        <div className="mt-6 overflow-x-auto rounded-xl border border-slate-700">

            <table className="min-w-full bg-slate-900 text-white">

                <thead className="bg-slate-800 sticky top-0">

                <tr>
                    {Object.keys(rows[0]).map((key) => (
                        <th
                            key={key}
                            className="px-4 py-3 text-left border-b border-slate-700 font-semibold"
                        >
                            {key}
                        </th>
                    ))}
                </tr>

                </thead>

                <tbody>

                {rows.map((row, index) => (

                    <tr
                        key={index}
                        className="hover:bg-slate-800 transition"
                    >

                        {Object.values(row).map((value, i) => (

                            <td
                                key={i}
                                className="px-4 py-3 border-b border-slate-800 whitespace-nowrap"
                            >
                                {String(value)}
                            </td>

                        ))}

                    </tr>

                ))}

                </tbody>

            </table>

        </div>
    );
}

export default ResultTable;