import styled from "styled-components";
import { Routes, Route } from "react-router-dom";

import LeftSidebar from "./components/LeftSidebar";
import Topbar from "./components/Topbar";
import GlobalStyle from "./style/Globalstyle";
import Home from "./pages/Home";
import Questions from "./pages/Questions";
import Tags from "./pages/Tags";
import Users from "./pages/Users";
import Footer from "./components/Footer";

const Body = styled.div`
  display: flex;
  flex-direction: column;
`;

const Main = styled.div`
  width: 100vw;
  display: flex;
`;

function App() {
  return (
    <Body>
      <GlobalStyle />
      <Main>
        <Topbar />
        <LeftSidebar />
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/questions" element={<Questions />} />
          <Route path="/tags" element={<Tags />} />
          <Route path="/users" element={<Users />} />
        </Routes>
      </Main>
      <Footer />
    </Body>
  );
}

export default App;
