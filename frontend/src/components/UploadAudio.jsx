import { useEffect, useState } from "react";
import { API_BASE } from "../config";

function UploadAudio() {

   const [file, setFile] = useState(null);
   const [transcript, setTranscript] = useState("");
   const [history, setHistory] = useState([]);
   const [audioUrl, setAudioUrl] = useState("");
    const [openId, setOpenId] = useState(null);
    const [loading, setLoading] = useState(false);
    const [message, setMessage] = useState("");
    const [error, setError] = useState("");
    const token = localStorage.getItem("token") || "";
    const handleFileChange = (e) => {
        setFile(e.target.files[0]);
    };

    const handleUpload = async () => {

        if (!file) {
            setError("Please select audio file");
            return;
        }

        const formData = new FormData();
        formData.append("file", file);

        try {

            setLoading(true);

            setError("");
            setMessage("");

           const response = await fetch(`${API_BASE}/api/speech/upload`, {
               method: "POST",
               headers: {
                   Authorization: `Bearer ${token}`
               },
               body: formData,
           });

            const data = await response.text();

            setTranscript(data);

            setAudioUrl(URL.createObjectURL(file));

            setMessage("Audio uploaded successfully!");

            fetchHistory();

        } catch (error) {

            console.error(error);

            setError("Upload failed");

        } finally {

            setLoading(false);

        }
    };
    const fetchHistory = async () => {

        try {

            const response = await fetch(`${API_BASE}/api/speech/history`, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });

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

            <div className="header">
                <h1>🎙️ Speech To Text Converter</h1>

                <button
                    onClick={() => {
                        localStorage.removeItem("token");
                        window.location.reload();
                    }}
                >
                    Logout
                </button>
            </div>

           <div className="upload-card">

               <h2>Upload Audio</h2>

               <p>
                   Upload an audio file and instantly generate a transcript.
               </p>

               <input
                   type="file"
                   accept=".wav,.mp3"
                   onChange={handleFileChange}
               />

               <br /><br />

               <button onClick={handleUpload}>
                   {loading ? "Uploading..." : "Upload Audio"}
               </button>

           </div>

            {loading && (
              <p style={{ textAlign: "center", color: "#4f46e5", marginTop: "10px" }}>
                ⏳ Processing your audio... please wait
              </p>
            )}

            {message && <p>{message}</p>}

            {error && <p>{error}</p>}


            {transcript && (
                <div className="transcript-card">

                    <h3>Transcript</h3>

                    <p>{transcript}</p>

                    <hr />

                    <h3>Uploaded Audio</h3>

                    <audio controls>
                      <source src={audioUrl} type="audio/mpeg" />
                    </audio>

                </div>
            )}
            <div className="transcript-box">

              <h3>Previous Transcripts</h3>

              {history.length === 0 ? (
                <p style={{ color: "gray", marginTop: "10px" }}>
                  No transcripts yet. Upload your first audio 🎧
                </p>
              ) : (
                history.map((item) => (
                  <details key={item.id} style={{
                    marginBottom: "20px",
                    padding: "15px",
                    backgroundColor: "#fff",
                    borderRadius: "10px",
                    border: "1px solid #eee"
                  }}>

                    <summary style={{ cursor: "pointer", fontWeight: "600" }}>
                      🎧 {item.fileName}
                    </summary>

                    <p style={{ marginTop: "10px" }}>
                      {item.transcript}
                    </p>

                    <audio controls>
                      <source src={item.filePath} type="audio/mpeg" />
                    </audio>

                    <p style={{ marginTop: "10px", fontSize: "12px", color: "gray" }}>
                      {new Date(item.uploadedAt).toLocaleString()}
                    </p>

                  </details>
                ))
              )}

            </div>

        </div>
    );
}

export default UploadAudio;