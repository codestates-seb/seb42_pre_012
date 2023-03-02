import styled from "styled-components";
import Button from "@mui/material/Button";

import { HiPencil } from "react-icons/hi";
import { BiMessage } from "react-icons/bi";
import { FaStackOverflow } from "react-icons/fa";
import { RiNumber7 } from "react-icons/ri";

const RightContainer = styled.form`
  display: flex;
  flex-direction: column;
  align-items: baseline;
  margin: 10px;
  flex-grow: 1;
`;

const FirstBox = styled.div`
  background-color: #fdf7e2;
  height: 464px;
  width: 300px;
  border-radius: 5px;
  margin: 10px;
  box-shadow: 0 1px 2px hsla(0, 0%, 0%, 0.05), 0 1px 4px hsla(0, 0%, 0%, 0.05),
    0 2px 8px hsla(0, 0%, 0%, 0.05);

  ul {
    margin: 15px;
  }

  .lastli {
    position: relative;
    bottom: 6px;
  }
`;

const BoxMainContent = styled.div`
  display: flex;
  align-items: center;
  height: 41px;
  background-color: #fbf3d5;
  padding-left: 10px;
  font-size: 12px;
  font-weight: bold;
  color: hsl(210, 8%, 35%);
  border: solid 1px hsl(47, 65%, 84%);
`;

const BoxContent = styled.li`
  margin: 10px 5px;
  font-size: 13px;
  color: #3b4045;
  display: flex;
  flex-direction: row;

  div {
    margin-left: 5px;
    position: relative;
  }

  .logo {
    margin-right: 8px;
  }

  .logo2 {
    margin-right: 8px;
    position: relative;
    bottom: 3px;
  }

  .logo3 {
    margin-right: 13px;
  }
`;

const SecondBox = styled.div`
  height: 95.5px;
  width: 300px;
  background-color: #ffff;
  margin: 10px;
  box-shadow: 0 1px 2px hsla(0, 0%, 0%, 0.05), 0 1px 4px hsla(0, 0%, 0%, 0.05),
    0 2px 8px hsla(0, 0%, 0%, 0.05);
`;

const BoxMainContent2 = styled(BoxMainContent)`
  background-color: hsl(210, 8%, 97.5%);
  border-color: hsl(210, 8%, 85%);
`;

const BoxContent2 = styled.div`
  display: flex;
  align-items: center;
  height: 50px;
  margin-left: 10px;
  font-size: 13px;
  color: #2f82d6;
`;

const ThirdBox = styled.div`
  height: 118px;
  width: 300px;
  background-color: #f8f9f9;
  margin: 10px;
  box-shadow: 0 1px 2px hsla(0, 0%, 0%, 0.05), 0 1px 4px hsla(0, 0%, 0%, 0.05),
    0 2px 8px hsla(0, 0%, 0%, 0.05);
`;

function RightSidebar() {
  return (
    <RightContainer>
      <FirstBox>
        <BoxMainContent>The Overflow Blog</BoxMainContent>
        <ul>
          <BoxContent>
            <HiPencil className="logo" /> Authorization on Rails (Ep. 540)
          </BoxContent>
          <BoxContent>
            <HiPencil className="logo2" size="20" /> Shorten the distance
            between production data and insight (Ep. 541)
            <br />
            sponsored post
          </BoxContent>
        </ul>
        <BoxMainContent>Featured on Meta</BoxMainContent>
        <ul>
          <BoxContent>
            <BiMessage color="skyblue" className="logo" />
            Ticket smash for [status-review] tag: Part Deux
          </BoxContent>
          <BoxContent>
            <BiMessage color="skyblue" className="logo" />
            We've added a "Necessary cookies
            <br /> only" option to the cookie consent
            <br /> popup
          </BoxContent>
          <BoxContent>
            <FaStackOverflow className="logo" />
            The [amazon] tag is being burninated
          </BoxContent>
          <BoxContent>
            <FaStackOverflow className="logo3" />
            Microsoft Azure Collective launch and proposed tag changes
          </BoxContent>
          <BoxContent>
            <FaStackOverflow className="logo" />
            Temporary policy: ChatGPT is banned
          </BoxContent>
        </ul>
        <BoxMainContent>Hot Meta Posts</BoxMainContent>
        <ul>
          <BoxContent className="lastli">
            <RiNumber7 className="logo" />
            Addressing questions caused by a<br /> common tutorial, but dealing
            with
            <br /> different...
          </BoxContent>
        </ul>
      </FirstBox>
      <SecondBox>
        <BoxMainContent2>Custom Filters</BoxMainContent2>
        <BoxContent2>Create a custom filter</BoxContent2>
      </SecondBox>
      <ThirdBox>
        <BoxMainContent2>Ignored Tags</BoxMainContent2>
        <Button>Add an ignored tag</Button>
      </ThirdBox>
    </RightContainer>
  );
}

export default RightSidebar;
