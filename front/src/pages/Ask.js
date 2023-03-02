import styled from "styled-components";
import { useState } from "react";

import pencil from "../assets/pencil.png";
import askBackgroundImg from "../assets/askBackgroundImg.png";
import Editor from "../components/Editor";

const AskContainer = styled.div`
  display: flex;
  flex-direction: column;
  margin-left: 17%;
  margin-top: 52px;
  background-color: RGB(248, 249, 249);
  width: 81.8%;
  border-left: 1px solid RGB(225, 226, 228);
  box-sizing: border-box;
  min-height: 160vh;

  header {
    display: flex;
    height: 140px;
    width: 100%;
    align-items: center;
    justify-content: space-between;
    margin-left: 25px;

    h1 {
      font-size: 28px;
      font-weight: 700;
    }

    img {
      height: 120px;
      margin-right: 40px;
    }
  }

  main {
    margin-left: 25px;
    display: flex;
    flex-direction: column;

    button {
      width: 140px;
      height: 40px;
      display: flex;
      justify-content: center;
      align-items: center;
      text-decoration: none;
      background-color: RGB(9, 149, 255);
      color: white;
      border: none;
      border-radius: 4px;
      box-shadow: rgba(0, 0, 0, 0.15) 2.4px 2.4px 3.2px;
    }
  }

  h2 {
    font-weight: 700;
  }

  p {
    font-size: 13px;
    margin: 7px 0 7px 0;
  }
`;

const AskExplanation = styled.div`
  background-color: RGB(235, 244, 251);
  border: 1px solid RGB(166, 206, 237);
  width: 850px;
  height: 260px;
  box-sizing: border-box;
  padding-top: 30px;
  padding-left: 25px;
  border-radius: 3px;
  margin-bottom: 20px;

  h2 {
    font-size: 22px;
    box-sizing: border-box;
    margin-bottom: 15px;
  }

  p {
    line-height: 20px;
    margin-bottom: 15px;
  }

  span {
    color: RGB(0, 116, 204);
  }

  h3 {
    font-size: 14px;
    font-weight: 600;
    margin-bottom: 10px;
  }

  li {
    list-style: disc;
    margin-left: 30px;
    font-size: 14px;
    color: RGB(59, 64, 68);
    line-height: 18px;
  }
`;

const AskTitle = styled.div`
  width: 850px;
  height: 135px;
  background-color: white;
  box-sizing: border-box;
  border: 1px solid RGB(234, 235, 237);
  padding-top: 30px;
  padding-left: 25px;
  border-radius: 3px;
  border: 1px solid RGB(234, 235, 237);

  input {
    width: 96%;
    height: 28px;
    border-radius: 3px;
    border: 1px solid RGB(186, 191, 196);

    &::placeholder {
      color: RGB(186, 191, 195);
      padding-left: 10px;
    }

    &:focus {
      outline: none;
      border: 1px solid RGB(107, 187, 247);
      box-shadow: 0px 0px 1px 4px RGB(215, 229, 242);
    }
  }
`;

const AskBody = styled.div`
  width: 850px;
  height: 350px;
  background-color: white;
  border: 1px solid RGB(234, 235, 237);
  border-radius: 3px;
  margin-top: 20px;
  box-sizing: border-box;
  padding-top: 30px;
  padding-left: 25px;
  margin-bottom: 20px;

  .askEditor {
    height: 250px;
    width: 795px;

    &:focus-within {
      outline: none;
      border: 0.5px solid RGB(107, 187, 247);
      box-shadow: 0px 0px 1px 4px RGB(215, 229, 242);
    }
  }
`;

const AskTags = styled.div`
  width: 850px;
  height: 135px;
  background-color: white;
  box-sizing: border-box;
  border: 1px solid RGB(234, 235, 237);
  padding-top: 30px;
  padding-left: 25px;
  border-radius: 3px;
  border: 1px solid RGB(234, 235, 237);
  margin-bottom: 15px;

  input {
    width: 96%;
    height: 28px;
    border-radius: 3px;
    border: 1px solid RGB(186, 191, 196);

    &::placeholder {
      color: RGB(186, 191, 195);
      padding-left: 10px;
    }

    &:focus {
      outline: none;
      border: 1px solid RGB(107, 187, 247);
      box-shadow: 0px 0px 1px 4px RGB(215, 229, 242);
    }
  }
`;

const TitleSideBox = styled.div`
  background-color: RGB(248, 249, 249);
  width: 270px;
  height: 150px;
  box-shadow: rgba(60, 64, 67, 0.3) 0px 0px 2px 0px,
    rgba(60, 64, 67, 0.15) 0px 0px 6px 0px;
  position: absolute;
  top: 473px;
  left: 1140px;
  box-sizing: border-box;

  .titleSideBoxTitle {
    height: 45px;
    display: flex;
    align-items: center;
    border: 0.5px solid RGB(224, 227, 228);
    box-sizing: border-box;
    padding-left: 10px;
  }

  .titleSideBoxBody {
    display: flex;
    align-items: center;
    background-color: white;
    height: 105px;
    border: 0.5px solid RGB(224, 227, 228);
    box-sizing: border-box;
    padding-left: 10px;

    img {
      height: 60px;
    }

    p {
      line-height: 14px;
      margin-left: 5px;
    }
  }
`;

const BodySideBox = styled.div`
  background-color: RGB(248, 249, 249);
  width: 270px;
  height: 150px;
  box-shadow: rgba(60, 64, 67, 0.3) 0px 0px 2px 0px,
    rgba(60, 64, 67, 0.15) 0px 0px 6px 0px;
  position: absolute;
  top: 628px;
  left: 1140px;
  box-sizing: border-box;

  .bodySideBoxTitle {
    height: 45px;
    display: flex;
    align-items: center;
    border: 0.5px solid RGB(224, 227, 228);
    box-sizing: border-box;
    padding-left: 10px;
  }

  .bodySideBoxBody {
    display: flex;
    align-items: center;
    background-color: white;
    height: 105px;
    border: 0.5px solid RGB(224, 227, 228);
    box-sizing: border-box;
    padding-left: 10px;

    img {
      height: 60px;
    }

    p {
      line-height: 14px;
      margin-left: 5px;
    }
  }
`;

function Ask() {
  const [titleSide, setTitleSide] = useState(false);
  const [bodySide, setBodySide] = useState(false);

  function titleSideHandle() {
    setTitleSide(!titleSide);
  }

  function titleSideBlur() {
    setTitleSide(false);
  }

  function bodySideHandle() {
    setBodySide(!bodySide);
  }

  function bodySideBlur() {
    setBodySide(false);
  }

  return (
    <AskContainer>
      <header>
        <h1>Ask a public question</h1>
        <img src={askBackgroundImg} alt="" />
      </header>
      <main>
        <AskExplanation>
          <h2>Writion a good question</h2>
          <p>
            You’re ready to <span>ask</span> a{" "}
            <span>programming-related question</span> and this form will help
            guide you through the process.
            <br /> Looking to ask a non-programming question? See{" "}
            <span>the topics here</span> to find a relevant site.
          </p>
          <h3>Steps</h3>
          <ul>
            <li>Summarize your problem in a one-line title.</li>
            <li>Describe your problem in more detail.</li>
            <li>Describe what you tried and what you expected to happen.</li>
            <li>
              Add “tags” which help surface your question to members of the
              community.
            </li>
            <li>Review your question and post it to the site.</li>
          </ul>
        </AskExplanation>
        <AskTitle>
          <h2>Title</h2>
          <p>
            Be specific and imagine you’re asking a question to another person.
          </p>
          <input
            onFocus={titleSideHandle}
            onBlur={titleSideBlur}
            placeholder="e.g. Is there an R function for finding the index of an element in a vector?"
          ></input>
        </AskTitle>
        <AskBody>
          <h2>What are the details of your problem?</h2>
          <p>
            Introduce the problem and expand on what you put in the title.
            Minimum 20 characters.
          </p>
          <div className="askEditor">
            <Editor
              bodySideHandle={bodySideHandle}
              bodySideBlur={bodySideBlur}
            />
          </div>
        </AskBody>
        <AskTags>
          <h2>Tags</h2>
          <p>
            Add up to 5 tags to describe what your question is about. Start
            typing to see suggestions.
          </p>
          <input placeholder="e.g. (vba css json)"></input>
        </AskTags>
        <button>Post your quesiton</button>
      </main>
      {titleSide ? (
        <TitleSideBox>
          <div className="titleSideBoxTitle">Writing a good title</div>
          <div className="titleSideBoxBody">
            <img src={pencil} alt="" />
            <p>
              Your title should summarize the problem.
              <br />
              <br />
              You might find that you have
              <br /> a better idea of your title after writing out the rest of
              the question.
            </p>
          </div>
        </TitleSideBox>
      ) : null}
      {bodySide ? (
        <BodySideBox>
          <div className="bodySideBoxTitle">Introduce the problem</div>
          <div className="bodySideBoxBody">
            <img src={pencil} alt="" />
            <p>
              Explain how you encountered
              <br /> the problem you’re trying to solve, and any difficulties
              that have prevented you from solving it yourself.
            </p>
          </div>
        </BodySideBox>
      ) : null}
    </AskContainer>
  );
}

export default Ask;
