import { useState } from "react";
import api from "../api/axios";

const Chat = () => {
  const [question, setQuestion] = useState("");
  const [answer, setAnswer] = useState("");
  const [sources, setSources] = useState([]);
  const [loading, setLoading] = useState(false);

  const sendQuestion = async () => {
    if (!question.trim()) return;

    try {
      setLoading(true);

      const res = await api.post("/api/chat/query", {
        question: question,
      });

      setAnswer(res.data.answer);
      setSources(res.data.sources || []);
    } catch (err) {
      setAnswer("Something went wrong");
      setSources([]);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div style={{ padding: "20px" }}>
      <h2>IntraWise Assistant</h2>

      {/* Answer Section */}
      <div style={{ marginBottom: "20px" }}>
        {loading && <p>Thinking...</p>}

        {!loading && answer && (
          <>
            <p><strong>Answer:</strong></p>
            <p>{answer}</p>
          </>
        )}
      </div>

      {/* Sources Section */}
      {!loading && sources.length > 0 && (
        <div style={{ marginBottom: "20px" }}>
          <p><strong>Sources:</strong></p>
          <ul>
            {sources.map((src, index) => (
              <li key={index}>{src}</li>
            ))}
          </ul>
        </div>
      )}

      {/* Input Section */}
      <div>
        <input
          type="text"
          placeholder="Ask a question..."
          value={question}
          onChange={(e) => setQuestion(e.target.value)}
          style={{ width: "70%", marginRight: "10px" }}
        />
        <button onClick={sendQuestion}>Send</button>
      </div>
    </div>
  );
};

export default Chat;
