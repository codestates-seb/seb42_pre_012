import styled from "styled-components";
import { Routes, Route, useNavigate } from "react-router-dom";
import { useState } from "react";

import LeftSidebar from "./components/LeftSidebar";
import Topbar from "./components/Topbar";
import GlobalStyle from "./style/Globalstyle";
import Home from "./pages/Home";
import Questions from "./pages/Questions";
import Tags from "./pages/Tags";
import Users from "./pages/Users";
import Footer from "./components/Footer";
import Login from "./pages/Login";
import Signup from "./pages/Signup";

const Body = styled.div`
  display: flex;
  flex-direction: column;
`;

const Main = styled.div`
  width: 100vw;
  display: flex;
`;

function App() {
  const [login, setLogin] = useState(false);
  const navigate = useNavigate();

  function onLogin(e) {
    e.preventDefault();
    setLogin(!login);
    console.log(login);
    if (login === false) {
      navigate("/");
    } else {
      navigate("/login");
    }
  }

  return (
    <Body>
      <GlobalStyle />
      <Main>
        <Topbar login={login} onLogin={onLogin} />
        <LeftSidebar login={login} />
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/questions" element={<Questions />} />
          <Route path="/tags" element={<Tags />} />
          <Route path="/users" element={<Users />} />
          <Route path="/login" element={<Login onLogin={onLogin} />} />
          <Route path="/signup" element={<Signup onLogin={onLogin} />} />
        </Routes>
      </Main>
      <Footer login={login} />
    </Body>
  );
}

export default App;
