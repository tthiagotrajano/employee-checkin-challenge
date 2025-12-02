import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { FaExclamationCircle } from "react-icons/fa";
import "../styles/Login.css"; 
import logo from "../assets/moura-logo-login.png";
import background from "../assets/background-moura.jpg";
import { loginUser } from '../services/api';

export default function Login() {
   const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [loading, setLoading] = useState(false);
  const [showModal, setShowModal] = useState(false);
  const [modalMessage, setModalMessage] = useState("");

  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    setShowModal(false);

    try {
      const response = await loginUser(email, password);

      if (response.status === 200) {
        navigate("/check", { state: { user: response.data } });
      }
    } catch (err) {
        let message = "";

        if (err.response && err.response.status === 404) {
            message = "Usuário ou senha incorreto.";
        } else {
            message = "Ocorreu um erro, contate o suporte.";
        }
        
      setModalMessage(message);
      setShowModal(true);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="login-page" style={{ backgroundImage: `url(${background})` }}>
      <form className="login-form" onSubmit={handleSubmit}>
        <img src={logo} alt="Grupo Moura" className="login-logo" />
        <p className="login-welcome">Portal de Check-in</p>

        <div className="login-inputs">
          <label>Email</label>
          <input
            type="text"
            placeholder="email@grupomoura.com"
            value={email}
            required
            onChange={(e) => setEmail(e.target.value)}
          />

          <label>Password</label>
          <input
            type="password"
            placeholder="********"
            value={password}
            required
            onChange={(e) => setPassword(e.target.value)}
          />
        </div>

        <button type="submit" className="login-button" disabled={loading}>
          {loading ? "Loading..." : "LOGIN"}
        </button>
      </form>

      {showModal && (
        <div className="modal-overlay">
          <div className="modal">
            <div className="modal-icon">
              <FaExclamationCircle size={50} color="#ff4d4f" />
            </div>
            <p>{modalMessage}</p>
            <button onClick={() => setShowModal(false)}>Fechar</button>
          </div>
        </div>
      )}
    </div>
  );
}