import React, { useState, useEffect } from "react";
import { useNavigate, useLocation, redirect } from "react-router-dom";
import "../styles/Report.css";
import background from "../assets/background-moura.jpg";
import { FaPowerOff, FaArrowLeft } from "react-icons/fa";
import { getWorkRecords, logout } from "../services/api";
import { toast, ToastContainer } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

export default function Report() {
  const navigate = useNavigate();
  const location = useLocation();
  const user = location.state?.user;

  const [records, setRecords] = useState([]);
  const [nameFilter, setNameFilter] = useState("");
  const [startDateFilter, setStartDateFilter] = useState("");
  const [endDateFilter, setEndDateFilter] = useState("");
  const [loading, setLoading] = useState(false);
  const [page, setPage] = useState(0);
  const [totalPages, setTotalPages] = useState(0);
  const [loadingLogout, setLoadingLogout] = useState(false);

  const fetchRecords = async () => {
    setLoading(true);
    try {
      const data = await getWorkRecords(
        nameFilter,
        startDateFilter,
        endDateFilter,
        page,
        10
      );
      setRecords(data.content);
      setTotalPages(data.totalPages);
    } catch (err) {
      console.error(err);
      toast.error("Erro ao carregar os registros!");
    } finally {
      setLoading(false);
    }
  };

  const notifySuccess = (message) => toast.success(message);
  const notifyError = (message) => toast.error(message);

  useEffect(() => {
    fetchRecords();
  }, [page]);

    const handleLogout = async () => {
        setLoadingLogout(true);
        try {
        const response = await logout();
        notifySuccess("Logout realizado com sucesso!");
        navigate("/"); 
        } catch (err) {
        console.error(err);

        if (err.message == "You are not logged in. Please log in.") {
            navigate("/");
        }

        notifyError("Erro ao realizar logout.");
        } finally {
        setLoadingLogout(false);
        }
    };

  const handleBack = () => {
    navigate("/check", { state: { user } });
  };

  const handleFilter = () => {
    setPage(0);
    fetchRecords();
  };

  const handlePageChange = (newPage) => {
    if (newPage < 0 || newPage >= totalPages) return;
    setPage(newPage);
  };

  const formatDate = (iso) => {
    if (!iso) return "";
    const date = new Date(iso);
    return date.toLocaleString("pt-BR", { hour12: false });
  };

    const formatDuration = (seconds) => {
        if (seconds == null) return "";

        const hrs = Math.floor(seconds / 3600);
        const mins = Math.floor((seconds % 3600) / 60);
        const secs = seconds % 60;

        let result = "";
        if (hrs > 0) result += `${hrs} hora${hrs > 1 ? "s" : ""} `;
        if (mins > 0) result += `${mins} minuto${mins > 1 ? "s" : ""} `;
        if (secs > 0 || (hrs === 0 && mins === 0)) result += `${secs} segundo${secs > 1 ? "s" : ""}`;

        return result.trim();
    };

  return (
    <div className="report-page" style={{ backgroundImage: `url(${background})` }}>
      <div className="report-header">
        <button className="btn-back" onClick={handleBack}>
          <FaArrowLeft /> Voltar
        </button>
        <button
          className="btn-logout"
          onClick={handleLogout}
          disabled={loadingLogout}
        >
          <FaPowerOff className="icon-btn" />
          {loadingLogout ? "Saindo..." : "Logout"}
        </button>
      </div>

      <div className="report-filters">
        <input
          type="text"
          placeholder="Nome"
          value={nameFilter}
          onChange={(e) => setNameFilter(e.target.value)}
        />
<div className="date-filters">
  <div className="filter-item">
    <label>De:</label>
    <input
      type="date"
      value={startDateFilter}
      onChange={(e) => {
        setStartDateFilter(e.target.value);
        if (endDateFilter && e.target.value > endDateFilter) {
          setEndDateFilter("");
        }
      }}
    />
  </div>

  <div className="filter-item">
    <label>Até:</label>
    <input
      type="date"
      value={endDateFilter}
      onChange={(e) => {
        const selectedDate = e.target.value;
        if (startDateFilter && selectedDate < startDateFilter) {
          toast.error('A data do campo "Até:" não pode ser menor que a do campo "De:".');
          return;
        }
        setEndDateFilter(selectedDate);
      }}
    />
  </div>
</div>

    
        <button className="btn-filter" onClick={handleFilter}>
          Filtrar
        </button>
      </div>

      <div className="report-table-container">
        {loading ? (
            <div className="loading-container">
                <p>Carregando...</p>
            </div>
        ) : (
          <table className="report-table">
            <thead>
              <tr>
                <th>Nome</th>
                <th>Check-in</th>
                <th>Check-out</th>
                <th>Duração </th>
              </tr>
            </thead>
            <tbody>
              {records.length === 0 ? (
                <tr>
                  <td colSpan="4">Nenhum registro encontrado</td>
                </tr>
              ) : (
                records.map((r) => (
                  <tr key={r.id}>
                    <td>{r.name}</td>
                    <td>{formatDate(r.checkInTime)}</td>
                    <td>{formatDate(r.checkOutTime)}</td>
                    <td>{formatDuration(r.duration)}</td>
                  </tr>
                ))
              )}
            </tbody>
          </table>
        )}
        
        <div className="report-pagination">
            <button
            className={`page-btn ${page === 0 ? "disabled" : ""}`}
            onClick={() => handlePageChange(page - 1)}
            disabled={page === 0}
            >
            Anterior
            </button>
            <span className="page-info">
            Página {page + 1} de {totalPages}
            </span>
            <button
            className={`page-btn ${page + 1 >= totalPages ? "disabled" : ""}`}
            onClick={() => handlePageChange(page + 1)}
            disabled={page + 1 >= totalPages}
            >
            Próxima
            </button>
        </div>
      </div>


      <ToastContainer
        position="top-right"
        autoClose={3000}
        hideProgressBar={false}
        newestOnTop
        closeOnClick
        rtl={false}
        pauseOnFocusLoss
        draggable
        pauseOnHover
      />
    </div>
  );
}
