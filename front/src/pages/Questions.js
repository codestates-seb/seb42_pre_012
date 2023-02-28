import styled from "styled-components";

const QuestionsContainer = styled.div`
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
`;

function Questions() {
  return (
    <QuestionsContainer>
      <h1>All Questions</h1>
    </QuestionsContainer>
  );
}

export default Questions;
