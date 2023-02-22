import React, { useState } from "react";
import styled from "styled-components";
import { RiQuestionnaireFill } from "react-icons/ri";
import { MdThumbsUpDown } from "react-icons/md";
import { IoMdPricetags } from "react-icons/io";
import { BsFillTrophyFill } from "react-icons/bs";
import { FcGoogle } from "react-icons/fc";
import { VscGithub } from "react-icons/vsc";
import { FaFacebookSquare } from "react-icons/fa";

const Container = styled.form`
  display: flex;
  align-items: center;
  background-color: #f1f2f3;
  width: 100%;
  margin-top: 52px;
`;

const LeftContainer = styled.form`
  display: flex;
  flex-direction: column;
  align-items: baseline;
  padding-left: 280px;
  padding-right: 20px;
  padding-bottom: 200px;
`;

const LeftDiv = styled.div`
  margin: 10px;
  font-size: 15px;
`;

const LeftH1 = styled.h1`
  margin: 32px;
  font-size: 27px;
`;
const IconDiv = styled.div`
  display: flex;
  flex-direction: row;
  align-items: center;
`;

const RightContainer = styled.form`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
`;

const FormContainer = styled.form`
  display: flex;
  flex-direction: column;
  align-items: baseline;
  justify-content: center;
  border-radius: 10px;
  margin: 20px;
  padding: 50px 30px;
  background-color: #ffffff;
`;

const Input = styled.input`
  margin-bottom: 25px;
  padding: 10px;
  width: 250px;
  height: 15px;
  border: 1px solid #c4c7cc;
  border-radius: 3px;
  font-size: 14px;
  line-height: 20px;
  color: #3c4146;

  &:focus {
    outline: none;
    border: 1px solid RGB(107, 187, 247);
    box-shadow: 0px 0px 1px 4px RGB(215, 229, 242);
  }
`;

const LastInput = styled(Input)`
  margin-bottom: 10px;
`;

const DetailInput = styled.div`
  color: ${(props) => props.fontcolor || "black"};
  font-size: small;
  margin-bottom: 5px;
  margin-top: 10px;
`;

const Button = styled.button`
  margin-bottom: 30px;
  padding: 10px;
  width: 275px;
  background-color: #0995ff;
  color: white;
  border: none;
  border-radius: 3px;
  cursor: pointer;

  &:hover {
    background-color: #0e6ac9;
  }
`;

const OauthButton = styled.button`
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: center;
  margin-top: 10px;
  width: 330px;
  height: 40px;
  background-color: #207cca;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  font-size: 14px;

  background: ${(props) => props.color || "white"};
  color: ${(props) => (props.color === "#FFFFFF" ? "black" : "white")};
`;

const HumanCheckbox = styled.div`
  background-color: #f1f2f3;
  width: 273px;
  height: 200px;
  margin-bottom: 20px;
  margin-top: 10px;
`;

function Signup() {
  const [email, setEmail] = useState("");
  const [displayName, setDisplayName] = useState("");
  const [password, setPassword] = useState("");

  const handleSubmit = (e) => {
    e.preventDefault();
    // 여기서 계정을 만들기 위한 Ajax 요청 적기
  };

  return (
    <Container>
      <LeftContainer>
        <LeftH1>Join the Stack Overflow community</LeftH1>
        <IconDiv>
          <RiQuestionnaireFill color="#0995FF" size="24" />
          <LeftDiv>Get unstuck — ask a question</LeftDiv>
        </IconDiv>
        <IconDiv>
          <MdThumbsUpDown color="#0995FF" size="24" />
          <LeftDiv>Unlock new privileges like voting and commenting</LeftDiv>
        </IconDiv>
        <IconDiv>
          <IoMdPricetags color="#0995FF" size="24" />
          <LeftDiv>Save your favorite tags, filters, and jobs</LeftDiv>
        </IconDiv>
        <IconDiv>
          <BsFillTrophyFill color="#0995FF" size="24" />
          <LeftDiv>Earn reputation and badges</LeftDiv>
        </IconDiv>
        <DetailInput fontcolor="#AEB3B8">
          Collaborate and share knowledge with a private group for FREE.
          <br />
          Get Stack Overflow for Teams free for up to 50 users.
        </DetailInput>
      </LeftContainer>
      <RightContainer>
        <OauthButton color="#FFFFFF">
          <FcGoogle size="20px" />
          &nbsp;Sign up with Google
        </OauthButton>
        <OauthButton color="#232629">
          <VscGithub size="20px" />
          &nbsp;Sign up with GitHub
        </OauthButton>
        <OauthButton color="#304986">
          <FaFacebookSquare size="20px" />
          &nbsp;Sign up with Facebook
        </OauthButton>
        <FormContainer onSubmit={handleSubmit}>
          <div>Display name</div>
          <Input
            type="text"
            value={displayName}
            onChange={(e) => setDisplayName(e.target.value)}
          />
          <div>Email</div>
          <Input
            type="email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
          />
          <div>Password</div>
          <LastInput
            type="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
          />
          <DetailInput fontcolor="#AEB3B8">
            Passwords must contain at least eight
            <br /> characters, including at least 1 letter and 1<br /> number.
          </DetailInput>
          <HumanCheckbox />
          <DetailInput>
            Opt-in to receive occasional product
            <br /> updates, user research invitations,
            <br /> company announcements, and digests.
          </DetailInput>
          <Button type="submit">Sign up</Button>
          <DetailInput fontcolor="#AEB3B8">
            By clicking “Sign up”, you agree to our terms of
            <br /> service, privacy policy and cookie policy
          </DetailInput>
        </FormContainer>
      </RightContainer>
    </Container>
  );
}

export default Signup;
