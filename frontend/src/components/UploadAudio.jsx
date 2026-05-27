import { useEffect, useState } from "react";

function UploadAudio() {

    const [file, setFile] = useState(null);
    const [transcript, setTranscript] = useState("");
    const [history, setHistory] = useState([]);
    const [loading, setLoading] = useState(false);

    const handleFileChange = (e) => {
        setFile(e.target.files[0]);
    };

    const handleUpload = async () => {

        if (!file) {
            alert("Select audio file");
            return;
        }

        const formData = new FormData();
        formData.append("file", file);

        try {

            setLoading(true);

            const response = await fetch(
                "http://localhost:8080/api/speech/upload",
                {
                    method: "POST",
                    body: formData,
                }
            );

            const data = await response.text();

            setTranscript(data);
            fetchHistory();

        } catch (error) {
            console.error(error);
            alert("Upload failed");
        } finally {
            setLoading(false);
        }
    };
    const fetchHistory = async () => {

        try {

            const response = await fetch(
                "http://localhost:8080/api/speech/history"
            );

            const data = await response.json();

            setHistory(data);

        } catch (error) {
            console.error(error);
        }
    };
    useEffect(() => {
        fetchHistory();
    }, []);

    return (
        <div className="container">

            <h1>Speech To Text</h1>

            <input
                type="file"
                accept=".wav"
                onChange={handleFileChange}
            />

            <button onClick={handleUpload}>
                {loading ? "Uploading..." : "Upload Audio"}
            </button>

            <div className="transcript-box">

                <h3>Transcript</h3>

                <p>
                    {transcript || "Transcript will appear here..."}
                </p>

            </div>
            <div className="transcript-box">

                <h3>Previous Transcripts</h3>

                {
                    history.map((item) => (
                        <div
                            key={item.id}
                            style={{
                                marginBottom: "20px",
                                padding: "15px",
                                backgroundColor: "#ffffff",
                                borderRadius: "8px",
                                border: "1px solid #ddd"
                            }}
                        >

                            <p>
                                <strong>File:</strong> {item.fileName}
                            </p>

                            <p>
                                <strong>Transcript:</strong>
                            </p>

                            <p>{item.transcript}</p>

                            <p>
                                <strong>Uploaded:</strong>{" "}
                                {new Date(item.uploadedAt)
                                    .toLocaleString()}
                            </p>

                        </div>
                    ))
                }

            </div>

        </div>
    );
}

export default UploadAudio;