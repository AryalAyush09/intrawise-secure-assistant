import { useState } from "react";
import api from "../api/axios";

const Admin = () => {
  const [file, setFile] = useState(null);
  const [message, setMessage] = useState("");
  const [loading, setLoading] = useState(false);

  const handleFileChange = (e) => {
    setFile(e.target.files[0]);
    setMessage("");
  };

  const handleUpload = async () => {
    if (!file) {
      setMessage("Please select a file");
      return;
    }

    const formData = new FormData();
    formData.append("file", file);

    try {
      setLoading(true);

      const res = await api.post(
        "/api/documents/upload",
        formData,
        {
          headers: {
            "Content-Type": "multipart/form-data",
          },
        }
      );

      setMessage(res.data.message || "Upload successful");
      setFile(null);
    } catch (err) {
      setMessage("Upload failed");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div style={{ padding: "20px" }}>
      <h2>Admin Panel – Document Upload</h2>

      <input
        type="file"
        accept=".pdf,.docx,.txt"
        onChange={handleFileChange}
      />

      <br /><br />

      <button onClick={handleUpload} disabled={loading}>
        {loading ? "Uploading..." : "Upload"}
      </button>

      <br /><br />

      {message && <p>{message}</p>}
    </div>
  );
};

export default Admin;
