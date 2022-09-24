import './App.css';

import 'bootstrap/dist/css/bootstrap.min.css';
import {
  BrowserRouter as Router,
  useNavigate,
} from "react-router-dom";
import { Auth0Provider } from '@auth0/auth0-react';
import {Provider} from "react-redux";
import {rootReducer} from "./redux/rootReducer";
import {configureStore} from "@reduxjs/toolkit";
import {MainApp} from "./components/mainAPP/MainApp";

const Auth0ProviderWithRedirectCallback = ({ children, ...props }) => {
  const navigate = useNavigate();
  const onRedirectCallback = (appState) => {
    navigate((appState && appState.returnTo) || window.location.pathname);
  };
  return (
      <Auth0Provider onRedirectCallback={onRedirectCallback} {...props}>
        {children}
      </Auth0Provider>
  );
};

const store = configureStore({ reducer: rootReducer });

function App() {
  return (
      <Provider store={store}>
        <Router>
          <Auth0ProviderWithRedirectCallback
              domain="dev-nhindvpp.us.auth0.com"
              clientId="dziZPv6hHD1JsApFUnR3fPunhWOE88xB"
              redirectUri={window.location.origin}
              audience="http://localhost:8080"
              scope="admin"
          >
            <MainApp />
          </Auth0ProviderWithRedirectCallback>
        </Router>
      </Provider>
  );
}

export default App;
