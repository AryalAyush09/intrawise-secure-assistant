import { useState, useEffect, useRef } from "react";
import { FaComments, FaCog } from "react-icons/fa";
import api from "../../api/axios";
import { useAuth } from "../../auth/authContext";
import "./chat.css";

const Chat = () => {
  const { user } = useAuth();
  const username = user?.fullName;

  const [activeTab, setActiveTab] = useState("chat");
  const [showWelcome, setShowWelcome] = useState(true);
  const [question, setQuestion] = useState("");
  const [messages, setMessages] = useState([]);
  const [loading, setLoading] = useState(false);

  const messagesEndRef = useRef(null);

  // Show welcome screen for 2.5 seconds
  useEffect(() => {
    const timer = setTimeout(() => setShowWelcome(false), 2500);
    return () => clearTimeout(timer);
  }, []);

  // Scroll to bottom when messages update
  useEffect(() => {
    messagesEndRef.current?.scrollIntoView({ behavior: "smooth" });
  }, [messages, loading]);

  // Send question to backend
  const sendQuestion = async () => {
    if (!question.trim()) return;

    // Add user message to chat
    setMessages(prev => [...prev, { role: "user", text: question }]);
    setQuestion("");
    setLoading(true);

    try {
      const token = localStorage.getItem("token");
      const res = await api.post(
        "/api/v1/query/ask",
        { questions: question }, // matches QueryDTO
        { headers: { Authorization: `Bearer ${token}` } }
      );

      // Backend returns ApiResponse<RagResponse>
      const answer = res.data.data?.answer || "No answer received";

      // Add assistant message to chat
      setMessages(prev => [
        ...prev,
        { role: "assistant", text: answer }
      ]);
    } catch (err) {
      console.error(err.response?.data || err.message);
      setMessages(prev => [
        ...prev,
        { role: "assistant", text: "Something went wrong" }
      ]);
    } finally {
      setLoading(false);
    }
  };

  if (showWelcome) {
    return (
      <div className="welcome-screen">
        <h1>Welcome to AI Assistant</h1>
      </div>
    );
  }

  return (
    <div className="chat-layout">
      {/* Left Sidebar */}
      <div className="chat-left">
        <div className="left-top">
          <h2>{username}</h2>
        </div>
        <div className="left-menu">
          <button
            className={activeTab === "chat" ? "active" : ""}
            onClick={() => setActiveTab("chat")}
          >
            <FaComments className="icon" /> Chat
          </button>
          <button
            className={activeTab === "settings" ? "active" : ""}
            onClick={() => setActiveTab("settings")}
          >
            <FaCog className="icon" /> Settings
          </button>
        </div>
      </div>

      {/* Right Content */}
      <div className="chat-page">
        {activeTab === "chat" && (
          <>
            <div className="chat-header">AI Assistant</div>

            <div className="chat-messages">
              {messages.map((msg, i) => (
                <div key={i} className={`message ${msg.role}`}>
                  {msg.text}
                </div>
              ))}
              {loading && <div className="message assistant">Thinking...</div>}
              <div ref={messagesEndRef} />
            </div>

            <div className="chat-input">
              <input
                placeholder="Ask something..."
                value={question}
                onChange={(e) => setQuestion(e.target.value)}
                onKeyDown={(e) => e.key === "Enter" && sendQuestion()}
              />
              <button onClick={sendQuestion}>Send</button>
            </div>
          </>
        )}

        {activeTab === "settings" && (
          <div className="settings-page">
            <h3>Settings</h3>
            <p>Here you can put your settings options.</p>
          </div>
        )}
      </div>
    </div>
  );
};

export default Chat;
