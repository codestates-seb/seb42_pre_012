import styled from "styled-components";

const HomeContainer = styled.div`
  height: 100vh;
  display: flex;
  margin-left: 17%;
  margin-top: 52px;
  background-color: white;
  width: 52.2%;
  border-left: 1px solid RGB(225, 226, 228);

  h1 {
    margin-top: 30px;
    margin-left: 25px;
    font-size: 27px;
    font-weight: 500;
  }

  button {
    width: 105px;
    height: 40px;
  }
`;

function Home() {
  return (
    <HomeContainer>
      <h1>Top Questions</h1>
      <button>Ask Questions</button>
    </HomeContainer>
  );
}

export default Home;
