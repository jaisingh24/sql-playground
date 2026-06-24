import Editor from "@monaco-editor/react";

function SqlEditor({ query, setQuery }) {

    return (
        <Editor
            height="350px"
            theme="vs-dark"
            language="sql"
            value={query}
            onChange={(value) => setQuery(value)}
            options={{
                fontSize: 16,
                minimap: {
                    enabled: false
                },
                scrollBeyondLastLine: false
            }}
        />
    );
}

export default SqlEditor;