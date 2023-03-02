import styled from "styled-components";
import { Link, useNavigate, useParams } from "react-router-dom";
import { useState, useEffect } from "react";
import axios from "axios";
import { useSelector } from "react-redux";

import { GoTriangleUp, GoTriangleDown } from "react-icons/go";
import { RxBookmarkFilled, RxBookmark } from "react-icons/rx";
import Editor from "../components/Editor";
import RightSidebar from "../components/RightSidebar";

const QuestionContainer = styled.div`
  display: flex;
  margin-left: 17%;
  margin-top: 52px;
  background-color: white;
  border-left: 1px solid RGB(225, 226, 228);

  article {
    width: 660px;
    line-height: 22px;
    margin-top: 20px;
    color: RGB(49, 52, 55);
  }

  .questionDetail {
    margin-top: 10px;
    display: flex;
    align-items: flex-start;
    margin-left: 75px;
    width: 660px;
    height: 90px;
  }

  .spanGroup {
    margin-right: 145px;
    color: RGB(115, 123, 132);
    font-size: 14px;
    span {
      margin-right: 10px;
    }
  }

  .edited {
    margin-top: 7px;
    color: RGB(0, 116, 204);
    font-size: 12px;
    margin-right: auto;
  }

  .userInfo {
    display: flex;
    flex-wrap: wrap;
    width: 200px;
    height: 70px;
    background-color: RGB(217, 233, 247);
    box-sizing: border-box;
    padding: 10px 7px 10px 7px;
    border-radius: 4px;

    .asked {
      color: RGB(108, 117, 126);
      font-size: 12.5px;
      margin-bottom: 5px;
      margin-right: 15px;
    }

    .profileImg {
      display: flex;
      justify-content: center;
      align-items: center;
      width: 33px;
      height: 33px;
      background-color: cadetblue;
      border-radius: 4px;
      font-size: 18px;
      color: white;
      box-sizing: border-box;
      padding-top: 3px;
      margin-right: 8px;
    }

    .displayName {
      color: RGB(24, 122, 206);
      margin-top: 9px;
    }
  }

  .likeBar {
    display: flex;
    flex-direction: column;
    align-items: flex-start;
    margin-left: 22px;
    margin-right: 3px;
    height: 100%;
    width: 50px;
    color: RGB(186, 191, 195);
  }

  .likeSection {
    display: flex;
    flex-direction: column;
    align-items: center;
  }

  .likeBtnUp {
    font-size: 43px;
    margin-top: 12.5px;
  }

  .likeBtnDown {
    font-size: 43px;
  }

  .Likecount {
    font-size: 22px;
    margin: 6px 0 4px 0;
    color: RGB(106, 115, 124);
  }
`;

const ContentMain = styled.div``;

const QuestionSection = styled.div`
  width: 740px;
`;

const AnswerSection = styled.div`
  width: 740px;

  h2 {
    font-size: 20px;
    margin-left: 25px;
    margin-bottom: 20px;
  }
`;

const QuestionHeader = styled.div`
  margin-left: 25px;
  display: flex;
  flex-direction: column;
  width: 1045px;
  height: 135px;
  box-sizing: border-box;
  padding-bottom: 135px;
  border-bottom: 1px solid RGB(227, 230, 232);

  h1 {
    width: 946px;
    margin-top: 25px;
    font-size: 27.3px;
    font-weight: 400;
    letter-spacing: -0.3px;
    margin-bottom: 8px;
    line-height: 35px;
  }

  h2 {
    font-size: 13.8px;
    .headerContentTitle {
      margin-right: 5px;
      color: RGB(116, 124, 133);
    }
    .headerContent {
      margin-right: 20px;
      color: RGB(49, 52, 55);
    }
  }

  .questionButton {
    display: flex;
    justify-content: center;
    align-items: center;
    text-decoration: none;
    position: absolute;
    width: 105px;
    height: 38px;
    top: 73px;
    left: 1210px;
    background-color: RGB(9, 149, 255);
    color: white;
    border: none;
    border-radius: 4px;
    box-shadow: rgba(0, 0, 0, 0.15) 2.4px 2.4px 3.2px;
  }
`;

const QuestionMain = styled.div`
  display: flex;

  .bookmark {
    font-size: 20px;
    margin-left: 11.5px;
    cursor: pointer;
  }
`;
const QuestionSub = styled.div`
  .Tags {
    display: flex;
    align-items: center;
    margin-left: 75px;
    width: 660px;
    height: 90px;
    color: RGB(57, 115, 156);

    .tag {
      background-color: RGB(225, 236, 244);
      margin-right: 7px;
      font-size: 14px;
      padding: 6px;
      border-radius: 3px;
    }
  }
`;

const CommentList = styled.div`
  border-top: 1px solid RGB(235, 236, 237);
  margin-left: 75px;
  margin-bottom: 40px;

  .addComment {
    margin-top: 15px;
    color: RGB(181, 186, 191);
    font-size: 14px;
    cursor: pointer;
    &:hover {
      color: RGB(141, 184, 230);
    }
  }
`;

const AddCommentForm = styled.form`
  display: none;
  margin-top: 15px;
  margin-left: 20px;

  input {
    &:first-child {
      width: 500px;
      height: 80px;
      margin-right: 10px;
      overflow-y: scroll;
      border: 1px solid RGB(186, 191, 195);
      border-radius: 4px;
      &:focus {
        outline: none;
        border: 1px solid RGB(107, 187, 247);
        box-shadow: 0px 0px 1px 4px RGB(215, 229, 242);
      }
    }

    &:nth-child(2) {
      cursor: pointer;
      height: 35px;
      width: 100px;
      background-color: RGB(9, 149, 255);
      color: white;
      border: none;
      border-radius: 4px;
      box-shadow: rgba(0, 0, 0, 0.15) 2.4px 2.4px 3.2px;
    }
  }

  span {
    position: relative;
    color: RGB(0, 116, 204);
    top: 55px;
    right: 98px;
    font-size: 14px;
  }
`;

const QuestionAddCommentForm = styled.form`
  margin-top: 15px;
  margin-left: 20px;
  display: flex;

  input {
    &:first-child {
      width: 500px;
      height: 80px;
      margin-right: 10px;
      overflow-y: scroll;
      border: 1px solid RGB(186, 191, 195);
      border-radius: 4px;
      &:focus {
        outline: none;
        border: 1px solid RGB(107, 187, 247);
        box-shadow: 0px 0px 1px 4px RGB(215, 229, 242);
      }
    }

    &:nth-child(2) {
      cursor: pointer;
      height: 35px;
      width: 100px;
      background-color: RGB(9, 149, 255);
      color: white;
      border: none;
      border-radius: 4px;
      box-shadow: rgba(0, 0, 0, 0.15) 2.4px 2.4px 3.2px;
    }
  }

  span {
    position: relative;
    color: RGB(0, 116, 204);
    top: 55px;
    right: 98px;
    font-size: 14px;
  }
`;

const Comment = styled.div`
  display: flex;
  flex-direction: column;
  border-bottom: 1px solid RGB(235, 236, 237);
  font-size: 14px;
  box-sizing: border-box;
  padding: 10px 10px 10px 25px;
  color: RGB(49, 52, 55);
  letter-spacing: 0.2px;
  line-height: 18px;

  .commentInfo {
    box-sizing: border-box;
    padding-top: 5px;
    align-self: flex-end;
    .commentDisplayName {
      color: RGB(0, 116, 204);
    }

    .commentTime {
      color: RGB(145, 154, 162);
      font-size: 13px;
    }
  }
`;

const AnswerList = styled.div`
  .answerMain {
    display: flex;
    border-top: 1px solid RGB(225, 226, 228);
    margin-left: 25px;
  }

  .likeBar {
    margin-left: 0;
  }

  article {
    box-sizing: border-box;
    margin-bottom: 25px;
  }
`;

const AddComment = styled.div``;

const AnswerPost = styled.div`
  height: 450px;
  margin-left: 25px;
  h2 {
    margin-left: 0;
    border-top: 1px solid RGB(225, 226, 228);
    padding-top: 25px;
    margin-bottom: 25px;
  }

  .answeredEditor {
    width: 715px;
    height: 280px;
  }

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
`;

const ContentSide = styled.div`
  margin-top: 130px;
  margin-left: 10px;
`;

const CommentContainer = styled.div`
  .hide {
    display: none;
  }

  .open {
    display: flex;
  }
`;

function Question({ con, onCon }) {
  const { id } = useParams();
  const navigate = useNavigate();
  const [isBookmarkClicked, setIsBookmarkClicked] = useState(false);
  const [isQuestionCommentClicked, setIsQuestionCommentClicked] =
    useState(false);
  const [data, setData] = useState([]);
  const [answeredData, setAnsweredData] = useState([]);
  const memberId = useSelector((state) => state.memberId);

  useEffect(() => {
    axios
      .all([
        axios.get(
          `http://ec2-13-124-137-67.ap-northeast-2.compute.amazonaws.com:8080/questions/${id}?memberId=${
            "" || memberId
          }`
        ),
        axios.get(`http://ec2-13-124-137-67.ap-northeast-2.compute.amazonaws.com:8080/answers/${id}?memberId=${
          "" || memberId
        }&page=1&sortedBy=newest
    `),
      ])
      .then(
        axios.spread((res1, res2) => {
          setData([res1.data.data]);
          setAnsweredData(res2.data.data.answers);
        })
      )
      .catch((err) => console.log(err));
  }, []);

  const [select, setSelect] = useState(``);

  const CommentState = (e) => {
    setSelect(e);
  };

  const preventDefault = (event) => {
    event.preventDefault();
  };

  function onBookmark() {
    setIsBookmarkClicked(!isBookmarkClicked);
  }

  function onQuestionComment() {
    setIsQuestionCommentClicked(!isQuestionCommentClicked);
  }

  const [update, setUpdate] = useState([]);

  const postAnswer = async (event) => {
    event.preventDefault();
    const response = await axios.post(
      `http://ec2-13-124-137-67.ap-northeast-2.compute.amazonaws.com:8080/answers/${id}`,
      {
        memberId,
        content: con.slice(3, con.length - 4),
      }
    );

    console.log(response);

    if (response.data) {
      setUpdate([...update, response.data]);
    }
  };

  return (
    <QuestionContainer>
      <ContentMain>
        {data.map((question) => {
          return (
            <QuestionSection key={question.question.questionId}>
              <QuestionHeader>
                <h1>{question.question.title}</h1>
                <h2>
                  <span className="headerContentTitle">Asked</span>
                  <span className="headerContent">
                    {question.question.createdAt}
                  </span>
                  <span className="headerContentTitle">Modified</span>
                  <span className="headerContent">
                    {question.question.modifiedAt}
                  </span>
                  <span className="headerContentTitle">Viewed</span>
                  <span className="headerContent">
                    {question.question.viewCnt} times
                  </span>
                  <Link to="/ask" className="questionButton">
                    Ask Question
                  </Link>
                </h2>
              </QuestionHeader>
              <QuestionMain>
                <div className="likeBar">
                  <div className="likeSection">
                    <GoTriangleUp className="likeBtnUp" />
                    <div className="Likecount">{question.question.likeCnt}</div>
                    <GoTriangleDown className="likeBtnDown" />
                  </div>
                  {isBookmarkClicked ? (
                    <RxBookmarkFilled
                      className="bookmark"
                      onClick={onBookmark}
                    />
                  ) : (
                    <RxBookmark className="bookmark" onClick={onBookmark} />
                  )}
                </div>
                <article>{question.question.content}</article>
              </QuestionMain>
              <QuestionSub>
                <div className="Tags">
                  {question.tags.map((t) => {
                    return (
                      <div key={t.tagId} className="tag">
                        {t.tagName}
                      </div>
                    );
                  })}
                </div>
                <div className="questionDetail">
                  <div className="spanGroup">
                    <span>Share</span>
                    <span>Edit</span>
                    <span>Follow</span>
                  </div>
                  <div className="edited">
                    edited {question.question.modifiedAt}
                  </div>
                  <div className="userInfo">
                    <div className="asked">
                      asked {question.question.createdAt}
                    </div>
                    <div className="profileImg">
                      {question.member.profileImage[0]}
                    </div>
                    <div className="displayName">
                      {question.member.displayName}
                    </div>
                  </div>
                </div>
              </QuestionSub>
              <CommentList>
                {question.comments.map((comment) => {
                  return (
                    <Comment key={comment.commentId}>
                      <p>{comment.content}</p>
                      <div className="commentInfo">
                        <span>- </span>
                        <span className="commentDisplayName">
                          {comment.commentWriter + " "}
                        </span>
                        <span className="commentTime">{comment.createdAt}</span>
                      </div>
                    </Comment>
                  );
                })}
                {isQuestionCommentClicked ? (
                  <QuestionAddCommentForm>
                    <input type="text"></input>
                    <input
                      type="submit"
                      value="Add Comment"
                      onClick={onQuestionComment}
                    ></input>
                    <span>help</span>
                  </QuestionAddCommentForm>
                ) : (
                  <div className="addComment" onClick={onQuestionComment}>
                    Add a comment
                  </div>
                )}
              </CommentList>
            </QuestionSection>
          );
        })}

        <AnswerSection>
          <h2>{answeredData.length + " Answers"}</h2>
          <AnswerList>
            {answeredData.map((answer) => {
              return (
                <div key={answer.answerId} className="answer">
                  <div className="answerMain">
                    <div className="likeBar">
                      <div className="likeSection">
                        <GoTriangleUp className="likeBtnUp" />
                        <div className="Likecount">{answer.likeCnt}</div>
                        <GoTriangleDown className="likeBtnDown" />
                      </div>
                    </div>
                    <article>{answer.content}</article>
                  </div>
                  <div className="answerSub">
                    <div className="questionDetail">
                      <div className="spanGroup">
                        <span>Share</span>
                        <span>Edit</span>
                        <span>Follow</span>
                      </div>
                      <div className="edited">
                        {"edited " + answer.modifiedAt}
                      </div>
                      <div className="userInfo">
                        <div className="asked">
                          {"asked " + answer.createdAt}
                        </div>
                        <div className="profileImg">
                          {answer.writer.profileImage[0]}
                        </div>
                        <div className="displayName">
                          {answer.writer.displayName}
                        </div>
                      </div>
                    </div>
                  </div>
                  <CommentList>
                    {answer.comments.map((comment) => {
                      return (
                        <Comment key={comment.commentId}>
                          <p>{comment.content}</p>
                          <div className="commentInfo">
                            <span>- </span>
                            <span className="commentDisplayName">
                              {comment.displayName + " "}
                            </span>
                            <span className="commentTime">
                              {comment.createdAt}
                            </span>
                          </div>
                        </Comment>
                      );
                    })}
                    <CommentContainer>
                      <AddCommentForm
                        className={`${
                          select === answer.answerId
                            ? "addCommentForm open"
                            : "addCommentForm"
                        }`}
                        onSubmit={preventDefault}
                      >
                        <input type="text" />
                        <input
                          type="submit"
                          value="Add Comment"
                          onClick={() => {
                            CommentState(answer.answerId);
                          }}
                        />
                        <span>help</span>
                      </AddCommentForm>
                      <AddComment
                        className={`${
                          select === answer.answerId ? "hide" : "addComment"
                        }`}
                        onClick={() => CommentState(answer.answerId)}
                      >
                        Add a comment
                      </AddComment>
                    </CommentContainer>
                  </CommentList>
                </div>
              );
            })}
          </AnswerList>
          <AnswerPost>
            <h2>Your Answer</h2>
            <div className="answeredEditor">
              <Editor con={con} onCon={onCon} />
            </div>
            <button type="submit" onClick={postAnswer}>
              Post Your Answer
            </button>
          </AnswerPost>
        </AnswerSection>
      </ContentMain>
      <ContentSide>
        <RightSidebar />
      </ContentSide>
    </QuestionContainer>
  );
}

export default Question;
