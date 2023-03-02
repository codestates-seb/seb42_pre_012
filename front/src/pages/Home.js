import styled from "styled-components";
import ButtonGroup from "@mui/material/ButtonGroup";
import Button from "@mui/material/Button";
import { createTheme, ThemeProvider } from "@mui/material/styles";
import { green } from "@mui/material/colors";
import { questions } from "./Questiondata";
import RightSidebar from "../components/RightSidebar";
import { Link } from "react-router-dom";
import { useEffect, useState } from "react";
import axios from "axios";

const HomeContainer = styled.div`
  min-height: 100vh;
  display: flex;
  flex-direction: row;
  margin-left: 17%;
  margin-top: 52px;
  background-color: white;
  width: 100%;
  border-left: 1px solid RGB(225, 226, 228);
  overflow: scroll;

  h1 {
    display: flex;
    flex: 1;
    margin-top: 30px;
    margin-left: 25px;
    font-size: 27px;
    font-weight: 500;
  }
`;

const MainContainer = styled.div`
  display: flex;
  flex-direction: column;
  flex: 8;
`;

const TopQuestions = styled.div`
  display: flex;
  flex-grow: 1;

  .askbutton {
    display: flex;
    justify-content: center;
    align-items: center;
    text-decoration: none;
    font-size: 14px;
    padding-top: 3px;
    box-sizing: border-box;
    width: 105px;
    height: 40px;
    background-color: #0995ff;
    color: white;
    border: none;
    border-radius: 3px;
    cursor: pointer;
    margin-bottom: 20px;
    margin-top: 20px;

    &:hover {
      background-color: #0e6ac9;
    }
  }
`;

const QuestionContent = styled.div`
  display: flex;
  flex-grow: 8;
  border-top: 1px solid RGB(225, 226, 228);
`;

const Buttons = styled.div`
  display: flex;
  flex: 1;
  flex-direction: column;
  align-items: flex-end;
  justify-content: space-around;
  margin-bottom: 14px;
`;

const AllQuestion = styled.ul``;

const Question = styled.li`
  display: flex;
  flex-direction: row;
  padding: 22px 34px 15px 34px;
  font-size: 16px;
  border-bottom: 1px solid RGB(225, 226, 228);
`;

const QuestionLeftForm = styled.div`
  display: flex;
  flex-direction: column;
  align-items: flex-end;
  flex: 1;

  div {
    margin: 2px 16px 8px 0px;
    font-size: 15px;
  }

  Button {
    margin: 0px 16px 8px 0px;
    width: 60px;
    height: 25px;
    font-size: 12px;
    background-color: white;
    color: green;
    border-color: green;
  }
`;

const View = styled.div`
  color: #7f858c;
`;

const QuestionRightForm = styled.div`
  display: flex;
  flex: 5;
  font-size: large;
  flex-direction: column;
`;

const QuestionTitle = styled(Link)`
  margin-left: 5px;
  color: RGB(0, 116, 204);
  text-decoration: none;

  &:visited {
    color: RGB(0, 116, 204);
  }
`;

const AdditionalContent = styled.div`
  display: flex;
  flex-direction: row;
  margin-top: 5px;

  button {
    background-color: #deebf4;
    color: #638ba5;
    margin: 4px;
    cursor: pointer;
    font-size: 12px;
    height: 24px;
    box-shadow: none;

    &:hover {
      background-color: #d0e2f0;
      box-shadow: none;
    }
  }

  div {
    display: flex;
    flex: 1;
    flex-direction: row;
    justify-content: end;
    align-items: flex-end;
    height: 40px;
    font-size: 14px;
  }
`;

const Tagbuttons = styled.form`
  display: flex;
  flex: 1;
`;

const theme = createTheme({
  typography: {
    button: {
      textTransform: "none",
    },
  },
  palette: {
    primary: {
      main: green[500],
    },
    secondary: {
      main: "#65696d",
    },
  },
});

function QuestionsContainer() {
  const [data, setData] = useState([]);

  useEffect(() => {
    axios
      .get(
        `http://ec2-13-124-137-67.ap-northeast-2.compute.amazonaws.com:8080/questions?page=1&sortedBy=newest`
      )
      .then((res) => {
        setData(res.data.data.questions);
        console.log(res.data.data.questions);
      });
  }, []);

  return (
    <QuestionContent>
      <AllQuestion>
        {data.map((question) => {
          return (
            <Question key={question.questionId}>
              <QuestionLeftForm>
                <div>{question.likeCnt} votes</div>
                <ThemeProvider theme={theme}>
                  <Button color="primary" variant="outlined">
                    {question.answerCnt}answer
                  </Button>
                </ThemeProvider>
                <View>{question.viewCnt} views</View>
              </QuestionLeftForm>
              <QuestionRightForm>
                <QuestionTitle to={"/questions/" + question.questionId}>
                  {question.title}
                </QuestionTitle>
                <AdditionalContent>
                  <Tagbuttons>
                    {question.tags.map((prop) => {
                      return (
                        <ThemeProvider key={prop.tagId} theme={theme}>
                          <Button size="small" variant="contained">
                            {prop.tagName}
                          </Button>
                        </ThemeProvider>
                      );
                    })}
                  </Tagbuttons>
                  <div>
                    {question.member.displayName} {question.createdAt}
                  </div>
                </AdditionalContent>
              </QuestionRightForm>
            </Question>
          );
        })}
      </AllQuestion>
    </QuestionContent>
  );
}

function Home() {
  return (
    <HomeContainer>
      <MainContainer>
        <TopQuestions>
          <h1>Top Questions</h1>
          <Buttons>
            <Link to="ask" className="askbutton">
              Ask Questions
            </Link>
            <ThemeProvider theme={theme}>
              <ButtonGroup
                size="large"
                aria-label="small button group"
                color="secondary"
                sx={{ width: 380 }}
              >
                <Button sx={{ fontSize: 13 }}>Newest</Button>
                <Button sx={{ fontSize: 13 }}>Unanswered</Button>
                <Button sx={{ fontSize: 13 }}>Interesting</Button>
                <Button sx={{ fontSize: 13 }}>Hot</Button>
                <Button sx={{ fontSize: 13 }}>Active</Button>
              </ButtonGroup>
            </ThemeProvider>
          </Buttons>
        </TopQuestions>
        <QuestionsContainer />
      </MainContainer>
      <RightSidebar />
    </HomeContainer>
  );
}

export default Home;
