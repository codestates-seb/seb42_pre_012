import styled from "styled-components";
import { useState } from "react";
import { useNavigate } from "react-router-dom";

import { GrSearch } from "react-icons/gr";
import { FaUser } from "react-icons/fa";
import { IoMailOpen } from "react-icons/io5";
import { ImTrophy } from "react-icons/im";
import { BsQuestionCircleFill } from "react-icons/bs";
import { RiLogoutBoxRLine } from "react-icons/ri";

import stackOverflowlogo from "../assets/stackOverflowlogo.png";

const TopbarContainer = styled.div`
  width: 100vw;
  height: 50px;
  box-sizing: border-box;
  background-color: rgb(248, 249, 249);
  border-top: 3px solid RGB(244, 130, 36);
  box-shadow: rgba(60, 64, 67, 0.3) 0px 0px 2px 0px,
    rgba(60, 64, 67, 0.15) 0px 0px 6px 0px;
  display: flex;
  align-items: center;
  position: fixed;

  z-index: 1;

  .topbarLeftBlank {
    width: 5.5%;
  }

  .topbarRightBlank {
    width: auto;
  }

  .StackOverflowlogoContainer {
    display: flex;
    justify-content: center;
    align-items: center;
    width: 165px;
    height: 47px;

    &:hover {
      background-color: RGB(228, 229, 231);
    }
  }

  img {
    position: relative;
    bottom: 2px;
    width: 145px;
    height: auto;
    cursor: pointer;
  }

  .productsText {
    color: RGB(81, 89, 95);
    font-weight: 350;
    font-size: 14px;
    margin-top: 3px;
    margin-left: 15px;
    letter-spacing: 0.3px;
  }
`;

const TopbarSearchContainer = styled.div`
  margin-left: 21px;
  background-color: green;
  position: relative;
  width: 53.5%;
  height: 29px;

  input {
    width: 95.45%;
    height: 99.3%;
    border: 1px solid RGB(186, 191, 196);
    border-radius: 2.5px;
    padding-left: 32px;
    position: relative;
    bottom: 2px;

    &:focus {
      outline: none;
      border: 1px solid RGB(107, 187, 247);
      box-shadow: 0px 0px 1px 4px RGB(215, 229, 242);
    }
  }

  .searchIcon {
    position: absolute;
    bottom: 4.5px;
    left: 8px;
    font-size: 20px;
    opacity: 0.48;
  }
`;

const TopbarRightSideNotLogin = styled.div`
  width: 215px;
  margin-left: 20px;

  .loginButton {
    width: 60px;
    height: 33px;
    cursor: pointer;
    padding: 0;
    color: RGB(76, 127, 165);
    background-color: RGB(225, 236, 244);
    border: 1px solid RGB(121, 167, 199);
    border-radius: 3.5px;

    &:hover {
      background-color: RGB(185, 210, 232);
    }
  }

  .signupButton {
    width: 69px;
    height: 33px;
    cursor: pointer;
    margin-left: 8px;
    padding: 0;
    color: RGB(248, 252, 254);
    background-color: RGB(9, 149, 255);
    border: 1px solid RGB(9, 149, 255);
    border-radius: 3.5px;

    &:hover {
      background-color: RGB(49, 114, 198);
    }
  }
`;

const TopbarRightSideLogin = styled.div`
  display: flex;
  align-items: center;
  width: 215px;
  margin-left: 7px;

  .userIconContainer {
    display: flex;
    align-items: center;
    justify-content: center;
    height: 47px;
    width: 50px;

    &:hover {
      background-color: RGB(228, 229, 231);
    }
  }

  .userIcon {
    font-size: 22px;
    color: RGB(81, 89, 95);
    cursor: pointer;
  }

  .mailIcon {
    font-size: 21px;
    margin-left: 20px;
    color: RGB(81, 89, 95);
  }

  .trophyIcon {
    font-size: 18px;
    margin-left: 19px;
    color: RGB(81, 89, 95);
  }

  .questionIcon {
    font-size: 17px;
    margin-left: 19px;
    color: RGB(81, 89, 95);
    position: relative;
  }

  .logoutIconContainer {
    display: flex;
    justify-content: center;
    font-size: 22px;
    margin-left: 10px;
    color: RGB(81, 89, 95);
    cursor: pointer;
    height: 47px;
    width: 35px;

    &:hover {
      background-color: RGB(228, 229, 231);
    }
  }

  .logoutIcon {
    position: relative;
    top: 13px;
  }
`;

const Keyword = styled.div`
  position: absolute;
  display: flex;
  flex-direction: column;
  background-color: white;
  width: 53.6%;
  height: 182px;
  top: 50px;
  left: 334px;
  border-radius: 5px;
  box-shadow: rgba(60, 64, 67, 0.3) 0px 0px 2px 0px,
    rgba(60, 64, 67, 0.15) 0px 0px 6px 0px;

  .textBubbleArrow {
    position: absolute;
    width: 10px;
    height: 10px;
    background-color: RGB(255, 255, 255);
    left: 380px;
    transform: rotate(45deg);
    border-radius: 1px;
    border-top: 1px solid RGB(209, 209, 209);
    border-left: 1px solid RGB(209, 209, 209);
    top: -6px;
    box-shadow: rgba(60, 64, 67, 0.3) 0px 0px 3px 0px,
      rgba(60, 64, 67, 0.15) 0px 0px -1px 0px;
  }

  .KeywordTextContainer {
    padding-left: 15px;
    display: flex;
    flex-direction: column;
    flex-wrap: wrap;
    height: 72%;
    width: 98%;
    border-bottom: 1px solid RGB(225, 226, 228);
  }

  p {
    margin-top: 15px;
    font-size: 14px;
    color: RGB(131, 139, 145);
  }

  span {
    letter-spacing: 1.2px;
    color: black;
  }

  .keywordBottom {
    display: flex;
    align-items: center;
    justify-content: space-between;
    height: 28%;
  }

  .keywordButton {
    margin-left: 15px;
    width: 92px;
    height: 28px;
    border: 1px solid RGB(121, 167, 199);
    border-radius: 3px;
    background-color: RGB(225, 236, 244);
    font-size: 11.5px;
    color: RGB(68, 122, 161);
    cursor: pointer;
  }

  .keywordSearchHelp {
    font-size: 11.7px;
    letter-spacing: 0.5px;
    color: RGB(42, 129, 209);
    margin-right: 12px;
  }
`;

function Topbar({ login, onLogin }) {
  const [searchFocus, setSearchFocus] = useState(false);
  const navigate = useNavigate();

  function handleFocus() {
    setSearchFocus(!searchFocus);
  }

  function handleblur() {
    setSearchFocus(!searchFocus);
  }

  function navigateToHome() {
    navigate("/");
  }

  function navigateToUsers() {
    navigate("/users");
  }

  function navigateToLogin() {
    navigate("/login");
  }

  function navigateToSignup() {
    navigate("/signup");
  }

  return (
    <TopbarContainer>
      <div className="topbarLeftBlank" />
      <div className="StackOverflowlogoContainer">
        <img onClick={navigateToHome} src={stackOverflowlogo} alt="" />
      </div>
      <span className="productsText">Products</span>
      <TopbarSearchContainer>
        <input
          placeholder="Search..."
          onFocus={handleFocus}
          onBlur={handleblur}
        />
        <GrSearch className="searchIcon" />
      </TopbarSearchContainer>
      {login ? (
        <TopbarRightSideLogin>
          <div className="userIconContainer">
            <FaUser onClick={navigateToUsers} className="userIcon" />
          </div>
          <IoMailOpen className="mailIcon" />
          <ImTrophy className="trophyIcon" />
          <BsQuestionCircleFill className="questionIcon" />
          <div className="logoutIconContainer">
            <RiLogoutBoxRLine onClick={onLogin} className="logoutIcon" />
          </div>
        </TopbarRightSideLogin>
      ) : (
        <TopbarRightSideNotLogin>
          <input
            onClick={navigateToLogin}
            className="loginButton"
            type={"button"}
            value="Log in"
          />
          <input
            onClick={navigateToSignup}
            className="signupButton"
            type={"button"}
            value="Sign up"
          />
        </TopbarRightSideNotLogin>
      )}
      <div className="topbarRightBlank" />
      {searchFocus ? (
        <Keyword>
          <div className="textBubbleArrow" />
          <div className="KeywordTextContainer">
            <p>
              <span>[tag]</span> search within a tag
            </p>
            <p>
              <span>user:1234</span> search by author
            </p>
            <p>
              <span>"words here"</span> exact phrase
            </p>
            <p>
              <span>collective:"Name"</span> collective content
            </p>
            <p>
              <span>answers:0</span> unanswered questions
            </p>
            <p>
              <span>score:3</span> posts with a 3+ score
            </p>
            <p>
              <span>is:question</span> type of post
            </p>
            <p>
              <span>isaccepted:yes</span> search within status
            </p>
          </div>
          <div className="keywordBottom">
            <input
              className="keywordButton"
              type={"button"}
              value={"Ask a question"}
            />
            <span className="keywordSearchHelp">Search help</span>
          </div>
        </Keyword>
      ) : null}
    </TopbarContainer>
  );
}

export default Topbar;
