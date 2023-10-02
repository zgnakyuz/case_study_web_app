import React from "react";
import { useState, useEffect } from "react";
import { Routes, Route, Link } from "react-router-dom";
import "bootstrap/dist/css/bootstrap.min.css";
import "./App.css";
import { Alert, AlertColor, Snackbar} from '@mui/material';

import * as AuthService from "./services/auth.service";
import IUser from './types/user.type';

import Login from "./components/Login";
import Register from "./components/Register";
import Home from "./components/Home";
import BoardUser from "./components/BoardUser";
import BoardAdmin from "./components/BoardAdmin";

import EventBus from "./common/EventBus";

const App: React.FC = () => {
  const [isUserAdmin, setIsUserAdmin] = useState<boolean | undefined>(undefined);
  const [currentUser, setCurrentUser] = useState<IUser | undefined>(undefined);
  const [snackbarMessage, setSnackbarMessage] = useState<string | null>(null);
  const [isSnackbarOpen, setIsSnackbarOpen] = useState(false);
  const [snackbarAlertType, setSnackbarAlertType] = useState<AlertColor | undefined>(undefined);

  useEffect(() => {
    const user = AuthService.getCurrentUser();

    if (user) {
      setCurrentUser(user);
      setIsUserAdmin(user.roles.includes("ROLE_ADMIN"));
    }

    EventBus.on("logout", logOut);

    return () => {
      EventBus.remove("logout", logOut);
    };
  }, []);

  const closeSnackbar = () => {
    setIsSnackbarOpen(false);
    setSnackbarMessage(null);
  };

  const logOut = () => {
    AuthService.logout()
      .then(() => {
        setIsUserAdmin(undefined);
        setCurrentUser(undefined);
        setSnackbarAlertType("success")
        setSnackbarMessage("Logout successful.");
        setIsSnackbarOpen(true);
      })
      .catch((error) => {
        setSnackbarAlertType("error")
        setSnackbarMessage("Logout failed. Please try again.");
        setIsSnackbarOpen(true);
      });
  };

  return (
    <div>
      <Snackbar open={isSnackbarOpen} autoHideDuration={4000} onClose={closeSnackbar}>
        <Alert onClose={closeSnackbar} severity={snackbarAlertType} sx={{ width: '100%' }}>
          {snackbarMessage}
        </Alert>
      </Snackbar>
      <nav className="navbar navbar-expand navbar-dark bg-dark">
        <Link to={"/"} className="navbar-brand">
          VendingMachine
        </Link>
        <div className="navbar-nav mr-auto">
          {isUserAdmin && (
            <li className="nav-item">
              <Link to={"/admin"} className="nav-link">
                Admin Board
              </Link>
            </li>
          )}
        </div>

        {currentUser ? (
          <div className="navbar-nav ml-auto">
            <span className="nav-link">
              {currentUser.username}
            </span>
            <li className="nav-item">
              <Link to={"/"} className="nav-link" onClick={logOut}>
                Log Out
              </Link>
            </li>
          </div>
        ) : (
          <div className="navbar-nav ml-auto">
            <li className="nav-item">
              <Link to={"/login"} className="nav-link">
                Login
              </Link>
            </li>

            <li className="nav-item">
              <Link to={"/register"} className="nav-link">
                Sign Up
              </Link>
            </li>
          </div>
        )}
      </nav>

      <div className="container mt-3">
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/home" element={<Home />} />
          <Route path="/login" element={<Login />} />
          <Route path="/register" element={<Register />} />
          <Route path="/user" element={<BoardUser />} />
          <Route path="/admin" element={<BoardAdmin />} />
        </Routes>
      </div>
    </div>
  );
};

export default App;
