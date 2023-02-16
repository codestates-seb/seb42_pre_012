import styled from "styled-components";

import GlobalStyle from "./styles/GlobalStyle";
import Topbar from "./components/Topbar";

const Main = styled.div`
  width: 100vw;
  height: 100vh;
  display: flex;
  flex-direction: column;
  background-color: wheat;
`;

function App() {
  return (
    <Main>
      <GlobalStyle />
      <Topbar />
    </Main>
  );
}

export default App;
