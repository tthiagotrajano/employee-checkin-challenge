import React, { useState, useEffect } from "react";
import { useNavigate, useLocation } from "react-router-dom";
import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import "../styles/Check.css";
import background from "../assets/background-moura.jpg";
import { FaSignInAlt, FaSignOutAlt, FaPowerOff } from "react-icons/fa";
import { insertCheckin, insertCheckout, logout } from "../services/api";
import axios from "axios";

export default function Check() {
  const navigate = useNavigate();
  const location = useLocation();
  const user = location.state?.user;

  const [checkInTime, setCheckInTime] = useState(null);
  const [checkOutTime, setCheckOutTime] = useState(null);
  const [loadingCheckin, setLoadingCheckin] = useState(false);
  const [loadingCheckout, setLoadingCheckout] = useState(false);
  const [loadingLogout, setLoadingLogout] = useState(false);

  useEffect(() => {
    const lastRecord = user?.lastWorkRecord;
    if (!lastRecord || (lastRecord.checkInTime && lastRecord.checkOutTime)) {
      setCheckInTime(null);
      setCheckOutTime(null);
    } else {
      setCheckInTime(lastRecord.checkInTime || null);
      setCheckOutTime(lastRecord.checkOutTime || null);
    }
  }, [user]);

  const notifySuccess = (message) => toast.success(message);
  const notifyError = (message) => toast.error(message);

  const handleCheckIn = async () => {
    setLoadingCheckin(true);
    try {
      const response = await insertCheckin();
      const timestamp = response.timestamp || new Date().toISOString();
      setCheckInTime(timestamp);
      setCheckOutTime(null);
      notifySuccess("Check-in realizado com sucesso!");
    } catch (err) {
      console.error(err);
      notifyError("Erro ao realizar check-in!");
    } finally {
      setLoadingCheckin(false);
    }
  };

  const handleCheckOut = async () => {
    setLoadingCheckout(true);
    try {
      const response = await insertCheckout();
      const timestamp = response.timestamp || new Date().toISOString();
      setCheckOutTime(timestamp);
      notifySuccess("Check-out realizado com sucesso!");

      setTimeout(() => {
        setCheckInTime(null);
        setCheckOutTime(null);
      }, 1000);
    } catch (err) {
      console.error(err);
      notifyError("Erro ao realizar check-out!");
    } finally {
      setLoadingCheckout(false);
    }
  };

  const handleGoReport = () => navigate("/report");

  const handleLogout = async () => {
    setLoadingLogout(true);
    try {
      const response = await logout();
      notifySuccess("Logout realizado com sucesso!");
      navigate("/"); 
    } catch (err) {
      console.error(err);
      notifyError("Erro ao realizar logout!");
    } finally {
      setLoadingLogout(false);
    }
  };

  const isCheckinEnabled = !checkInTime;
  const isCheckoutEnabled = !!checkInTime && !checkOutTime;

  return (
    <div className="check-page" style={{ backgroundImage: `url(${background})` }}>
      <div className="check-card">
        <button
          className="btn-logout"
          onClick={handleLogout}
          disabled={loadingLogout}
        >
          <FaPowerOff className="icon-btn" />
          {loadingLogout ? "Saindo..." : "Logout"}
        </button>

        <h2>Bem-vindo, {user?.name || "Usuário"}!</h2>

        <div className="check-buttons-row">
          <div className="check-button-container">
            <button
              className="btn-checkin"
              onClick={handleCheckIn}
              disabled={!isCheckinEnabled || loadingCheckin}
            >
              <FaSignInAlt className="icon-btn" />
              {loadingCheckin ? "Carregando..." : "Check-in"}
            </button>
          </div>

          <div className="check-button-container">
            <button
              className="btn-checkout"
              onClick={handleCheckOut}
              disabled={!isCheckoutEnabled || loadingCheckout}
            >
              <FaSignOutAlt className="icon-btn" />
              {loadingCheckout ? "Carregando..." : "Check-out"}
            </button>
          </div>
        </div>

        <button className="btn-report" onClick={handleGoReport}>
          Acessar Relatório
        </button>
      </div>

      <ToastContainer
        position="top-right"
        autoClose={3000}
        hideProgressBar={false}
        newestOnTop={true}
        closeOnClick
        rtl={false}
        pauseOnFocusLoss
        draggable
        pauseOnHover
      />
    </div>
  );
}
