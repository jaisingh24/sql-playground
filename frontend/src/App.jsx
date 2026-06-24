import { useEffect, useState } from "react";
import axios from "axios";
import SqlEditor from "./components/SqlEditor";
import ResultTable from "./components/ResultTable";

function App() {

    const [query, setQuery] = useState(
        "SELECT * FROM employees LIMIT 5;"
    );

    const [rows, setRows] = useState([]);
    const [executionTime, setExecutionTime] = useState(0);
    const [rowCount, setRowCount] = useState(0);
    const [history, setHistory] = useState([]);

    useEffect(() => {
        loadHistory();
    }, []);

    async function loadHistory() {

        try {

            const response = await axios.get(
                "http://localhost:8080/api/query/history"
            );

            setHistory(response.data);

        } catch (error) {

            console.log(error);

        }
    }

    async function executeQuery() {

        try {

            const response = await axios.post(
                "http://localhost:8080/api/query/execute",
                {
                    query
                }
            );

            setRows(response.data.rows);
            setExecutionTime(response.data.executionTime);
            setRowCount(response.data.rowCount);

            loadHistory();

        } catch (error) {

            alert(error.response?.data?.message);

        }
    }

    function exportToCSV() {

        if (rows.length === 0) {

            alert("No data available");

            return;
        }

        const headers = Object.keys(rows[0]);

        const csvRows = [];

        // Header row
        csvRows.push(headers.join(","));

        // Data rows
        rows.forEach((row) => {

            const values = headers.map(
                header => `"${row[header]}"`
            );

            csvRows.push(values.join(","));

        });

        const csvContent = csvRows.join("\n");

        const blob = new Blob(
            [csvContent],
            {
                type: "text/csv"
            }
        );

        const url = window.URL.createObjectURL(blob);

        const a = document.createElement("a");

        a.href = url;
        a.download = "results.csv";

        a.click();

        window.URL.revokeObjectURL(url);
    }

    return (

        <div className="min-h-screen bg-slate-950 text-white p-6">

            <div className="flex gap-6">

                {/* Sidebar */}
                <div className="w-80 bg-slate-900 rounded-xl p-4">

                    <h2 className="text-2xl font-bold mb-4">
                        Query History
                    </h2>

                    {
                        history.map((item, index) => (

                            <div
                                key={index}
                                onClick={() => setQuery(item)}
                                className="mb-3 p-3 rounded-lg bg-slate-800 cursor-pointer hover:bg-slate-700 transition break-words"
                            >

                                {item}

                            </div>

                        ))
                    }

                </div>

                {/* Main Content */}
                <div className="flex-1">

                    <h1 className="text-5xl font-bold mb-6">
                        SQL Playground
                    </h1>

                    <div className="border border-slate-700 rounded-xl overflow-hidden">

                        <SqlEditor
                            query={query}
                            setQuery={setQuery}
                        />

                    </div>

                    <div className="mt-6 flex gap-4">

                        <button
                            onClick={executeQuery}
                            className="px-6 py-3 bg-blue-600 rounded-lg hover:bg-blue-700"
                        >
                            Execute Query
                        </button>

                        <button
                            onClick={exportToCSV}
                            className="px-6 py-3 bg-green-600 rounded-lg hover:bg-green-700"
                        >
                            Export CSV
                        </button>

                    </div>

                    <div className="mt-6 flex gap-10 text-xl">

                        <div>
                            Execution Time:
                            <span className="text-green-400 ml-2">
                                {executionTime} ms
                            </span>
                        </div>

                        <div>
                            Rows Returned:
                            <span className="text-yellow-400 ml-2">
                                {rowCount}
                            </span>
                        </div>

                    </div>

                    <div className="mt-8">

                        <ResultTable rows={rows}/>

                    </div>

                </div>

            </div>

        </div>

    );
}

export default App;