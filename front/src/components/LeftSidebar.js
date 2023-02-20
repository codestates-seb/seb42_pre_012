import styled from "styled-components";

import { IoEarth } from "react-icons/io5";
import { MdInfo, MdStars, MdAssignment } from "react-icons/md";
import { NavLink } from "react-router-dom";

const LeftSidebarContainer = styled.div`
  position: fixed;
  display: flex;
  flex-direction: column;
  align-items: flex-end;
  width: 17.2%;
  margin-top: 51.5px;

  .home {
    display: flex;
    align-items: center;
    width: 163px;
    height: 34px;
    margin-top: 23px;
    margin-bottom: 16px;
    box-sizing: border-box;
    padding-top: 4px;
    padding-left: 8px;
    font-size: 14px;
    cursor: pointer;
    color: RGB(104, 110, 116);

    &:hover {
      color: RGB(59, 60, 61);
    }
  }
  .public,
  .collectives,
  .teams {
    font-size: 12px;
    margin-left: 8px;
    color: RGB(118, 126, 135);
    letter-spacing: 0.3px;
  }

  .collectives,
  .teams {
    display: flex;
    align-items: center;
    justify-content: space-between;
    margin-top: 14px;
    margin-right: 10px;
  }

  .teams {
    margin-top: 30px;
  }

  .infoIcon {
    font-size: 15px;
  }

  .mainNav {
    display: flex;
    flex-direction: column;
    margin-top: 3.5px;
    font-size: 13.5px;
    color: RGB(104, 110, 116);
    letter-spacing: 0.5px;
    font-weight: 550;
  }

  .subNav {
    display: flex;
    margin-left: 7px;
    margin-top: 5.7px;
    font-size: 13.5px;
    color: RGB(104, 110, 116);
    letter-spacing: 0.5px;
    font-weight: 550;

    li:first-child {
      display: flex;
      align-items: center;
    }
  }

  .starIcon,
  .teamIcon {
    color: RGB(244, 130, 36);
    font-size: 20px;
    margin-right: 4px;
    margin-bottom: 3px;
  }

  .companies {
    display: flex;
    align-items: center;
    width: 163px;
    height: 34px;
    padding-left: 30px;
    box-sizing: border-box;
    color: RGB(104, 110, 116);
  }

  .active {
    font-weight: 700;
    background-color: RGB(241, 242, 243);
    border-right: 3px solid RGB(244, 130, 36);
    color: black;

    &:hover {
      color: black;
    }
  }
`;

const StyledLink = styled(NavLink)`
  text-decoration: none;
  color: inherit;
  cursor: pointer;
  display: flex;
  align-items: center;
  width: 163px;
  height: 34px;
  padding-left: 30px;
  box-sizing: border-box;
  color: RGB(104, 110, 116);

  .earthIcon {
    font-size: 19px;
    margin-right: 4px;
    position: absolute;
    left: 89px;
    top: 98px;
  }

  &:hover {
    color: RGB(59, 60, 61);
  }
`;

function LeftSidebar() {
  return (
    <LeftSidebarContainer>
      <ol>
        <StyledLink to="/" className="home">
          Home
        </StyledLink>
        <li>
          <span className="public">PUBLIC</span>
          <ol className="mainNav">
            <StyledLink to="questions" className="questions">
              <IoEarth className="earthIcon" />
              Questions
            </StyledLink>
            <StyledLink to="tags">Tags</StyledLink>
            <StyledLink to="users">Users</StyledLink>
            <li className="companies">Companies</li>
          </ol>
        </li>
        <li>
          <span className="collectives">
            COLLECTIVES
            <MdInfo className="infoIcon" />
          </span>
          <ol className="subNav">
            <li>
              <MdStars className="starIcon" />
              Explore Collectives
            </li>
          </ol>
        </li>
        <li>
          <span className="teams">
            TEAMS <MdInfo className="infoIcon" />
          </span>
          <ol className="subNav">
            <li>
              <MdAssignment className="teamIcon" />
              Create free Team
            </li>
          </ol>
        </li>
      </ol>
    </LeftSidebarContainer>
  );
}

export default LeftSidebar;
