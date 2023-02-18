import styled from "styled-components";
import Topbar from "./components/Topbar";
import GlobalStyle from "./style/Globalstyle";

const Main = styled.div`
  width: 100vw;
  height: 100vh;
  display: flex;
  background-color: RGB(248, 249, 249);
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
