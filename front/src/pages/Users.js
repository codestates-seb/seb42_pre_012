// mac
// fold all 단축키: cmd + K + 1
// unfol all 단축키: cmd + K + J

import styled from "styled-components";

import { styled as MUIstyled } from "@mui/material/styles";
import { Box, Grid, Typography, Button, Tabs, Tab } from "@mui/material";
import {
  Edit,
  ArrowDropDown,
  Cake,
  Schedule,
  CalendarMonth,
  Add,
  OpenInNew,
} from "@mui/icons-material";

import { useState, useEffect } from "react";
// import { PropTypes } from "@mui/material";
import PropTypes from "prop-types";
import { Routes, Route, Link, useNavigate } from "react-router-dom";
import { fontFamily, style } from "@mui/system";

import axios from "axios";

import { ToggleButtonGroup, ToggleButton } from "@mui/material";

import { FaGithub } from "react-icons/fa";
import { useSelector } from "react-redux";
import { useRef } from "react";
import { useDispatch } from "react-redux";
import { saveMemberInfo } from "../actions/actions";

const UsersContainer = styled.div`
  min-height: 100vh;
  margin-left: 17%;
  margin-top: 52px;
  background-color: white;
  width: 72.5%;
  border-left: 1px solid RGB(225, 226, 228);

  padding: 16px;

  h1 {
    margin-top: 30px;
    margin-left: 25px;
    font-size: 27px;
    font-weight: 500;
  }
`;

const ProfileBtn = MUIstyled(Button)(() => ({
  color: "black",
  borderColor: "black",
  fontSize: "12px",
  textTransform: "none",
  padding: "10px",
  marginLeft: "6px",
  "&:hover": { borderColor: "black" },
}));

function ProfileBlock() {
  // 임시로 랜덤 값 설정
  // TODO: response 객체 다른 값 추가 설정 -> 현재 기준으로 계산
  const daysAsMember = Math.floor(Math.random() * 9) + 1;
  const visitedDays = Math.floor(Math.random() * 9) + 1;
  const consecutiveVisitingDays = Math.floor(Math.random() * 9) + 1;

  const state = useSelector((state) => state);

  return (
    <Box>
      <Grid container sx={{ height: "144px" }}>
        <Grid
          item
          sx={{
            minWidth: "144px",
            padding: "16px",
            display: "flex",
            justifyContent: "center",
            alignItems: "center",
          }}
        >
          <Box
            sx={{
              width: "128px",
              height: "128px",
              backgroundColor: `${state.profileColor}`,
              borderRadius: "5px",
              display: "flex",
              justifyContent: "center",
              alignItems: "center",
            }}
          >
            <Typography
              sx={{ color: "white", fontWeight: "bold", fontSize: "30px" }}
            >
              {state.displayName}
            </Typography>
          </Box>
        </Grid>
        <Grid
          item
          xs
          container
          sx={{
            display: "flex",
            flexDirection: "column",
            justifyContent: "center",
          }}
        >
          <Grid item sx={{ fontSize: "34px", marginBottom: "12px" }}>
            {state.displayName}
          </Grid>
          <Grid
            item
            container
            spacing={0.5}
            sx={{
              "& *": {
                fontSize: "13px",
                marginRight: "5px",
                color: "gray",
                display: "flex",
                justifyContent: "space-between",
                alignItems: "center",
              },
            }}
          >
            <Grid item>
              <Cake fontSize="18px" />
              Member for {daysAsMember} days
            </Grid>
            <Grid item>
              <Schedule fontSize="18px" />
              Last seen this week
            </Grid>
            <Grid item>
              <CalendarMonth fontSize="18px" />
              Visited {visitedDays} days, {consecutiveVisitingDays} consecutive
            </Grid>
          </Grid>
        </Grid>
        <Grid item sx={{ minWidth: "186px" }}>
          <ProfileBtn variant="outlined" startIcon={<Edit />}>
            Edit Profile
          </ProfileBtn>
          <ProfileBtn variant="outlined" endIcon={<ArrowDropDown />}>
            Profiles
          </ProfileBtn>
        </Grid>
      </Grid>
    </Box>
  );
}

// Main Tab
const MainTabContainer = styled.div`
  padding-left: 16px;
`;

const MainTabBtnWrapper = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;

  width: 277.87px;
  margin: 16px 0;
  padding: 2px 0px;
`;

const TabBtn = styled.button`
  width: ${(props) => props.width};
  padding: 6px 12px;
  margin: 0;

  color: ${(props) => COLORS.tabColor};
  background-color: white;
  border: none;
  border-radius: 1000px;

  text-align: start;

  &:hover {
    background-color: ${(props) => COLORS.tab_hoverbgColor};
  }

  &.focused {
    color: ${(props) => COLORS.tab_clickedColor};
    background-color: ${(props) => COLORS.tab_clickedBgColor};
  }
`;

const mainTabComponents = {
  Profile: ProfileTab,
  Activity: ActivityTab,
  Saves: SavesTab,
  Settings: SettingsTab,
};

function MainTab() {
  const [mainTabIdx, setMainTabIdx] = useState(1);

  const mainTabArr = [
    { name: "Profile", defaultPath: "/users/profile" },
    { name: "Activity", defaultPath: "/users/activity/answers" },
    { name: "Saves", defaultPath: "/users/saves/allsaves" },
    { name: "Settings", defaultPath: "/users/settings/editprofile" },
  ];

  const FocusedMainTabComponent =
    mainTabComponents[mainTabArr[mainTabIdx].name];

  const navigate = useNavigate();
  useEffect(() => {
    navigate("/users/activity/answers");
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  return (
    <MainTabContainer>
      <MainTabBtnWrapper>
        {mainTabArr.map((tab, idx) => {
          return (
            <Link to={tab.defaultPath} key={idx}>
              <TabBtn
                className={mainTabIdx === idx ? "focused" : ""}
                onClick={() => setMainTabIdx(idx)}
              >
                {tab.name}
              </TabBtn>
            </Link>
          );
        })}
      </MainTabBtnWrapper>
      <Routes>
        {mainTabArr.map((tab, idx) => (
          <Route
            key={idx}
            path={`${convertToPathStyle(tab.name)}/*`}
            element={<FocusedMainTabComponent />}
          />
        ))}
      </Routes>
    </MainTabContainer>
  );
}

// SubTab
const SubTabContainer = styled.div`
  display: flex;
  gap: 32px;
  & > :last-child {
    /* margin-left: 32px; */
    flex-grow: 1;
  }
`;

const SubTabBtnWrapper = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  align-items: flex-start;

  & > .flexBox {
    display: flex;
    justify-content: space-between;
    align-items: center;

    font-size: 13px;
    margin-top: 16px;
  }

  & .tabCategory {
    font-size: 12px;
    font-weight: bold;
    margin-bottom: 5px;
    &:not(:first-child) {
      margin-top: 24px;
    }
  }
`;

const SubTabContent = styled.div`
  margin-bottom: 32px;
  & .flexBox {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 8px;
  }
  & .flexBox2 {
    display: flex;
    justify-content: center;
    align-items: center;
    & > .flexCol {
      /* flex-grow: 1; */
      & > .flexItem {
        margin: 8px;
      }
    }
  }
  & > .subtabTitle {
    font-size: 27px;
    color: hsl(210, 8%, 5%);
    border-bottom: 1px solid ${(props) => COLORS.subtabBorder};
    padding-bottom: 12px;
    margin-bottom: 24px;
  }
  & .title {
    color: ${(props) => COLORS.subtabColor};
    font-size: 21px;
    margin-bottom: 8px;
    & > span {
      font-size: 13px;
      color: hsl(210, 8%, 45%);
      margin-left: 12px;
    }
  }
  /* & .option {
    background-color: pink;
  } */
  & .box {
    background-color: ${(props) => props.bgColor || COLORS.subtabBgColor};

    border: 1px solid;
    border-color: ${(props) => props.bdrColor || COLORS.subtabBorder};
    border-radius: 5px;

    &.fr {
      display: flex;
      justify-content: center;
      align-items: center;
    }

    &.fc {
      display: flex;
      flex-direction: column;
      justify-content: center;
      align-items: center;
    }

    & .main {
      padding: ${(props) => props.padding || "12px"};
      &.fr {
        display: flex;
        justify-content: center;
        align-items: center;
      }
    }
    & > .delete {
      border-top: 1px solid;
      border-color: ${(props) => props.bdrColor || COLORS.subtabBorder};
      padding: 16px;
    }
    & .item {
      &.fg1 {
        flex-grow: 1;
      }
      &.mb12 {
        margin-bottom: 12px;
      }
      &:not(:last-child) {
        margin-bottom: 12px;
      }
      & > .itemTitle {
        font-size: 15px;
        font-weight: 600;
        color: hsl(210, 8%, 5%);
        margin-bottom: 5px;
      }
      & > input.publicInfo {
        width: 401.128px;
        height: 14.994px;
        padding: 7.8px 9.1px;
      }
      & > input[type="textarea"] {
        width: 100%;
        min-height: 255px;
      }
      & > input.links {
        width: 254px;
      }
    }
  }
  & .contentItem {
    margin-bottom: 48px;
  }
  & #saveProfileBtn {
    width: 91px;
    height: 35px;
    padding: 10.4px;
    font-size: 13px;
    background-color: RGB(9, 149, 255);
    color: white;
    border: none;
    border-radius: 5px;
    text-align: center;
    cursor: pointer;

    &:hover {
      background-color: "#0074CC";
    }
  }
`;

// Profile Tab
const ProfileTabContainer = styled.div`
  display: flex;
  gap: 32px;
  & :first-child {
    flex-grow: 1;
  }
  & :last-child {
    flex-grow: 3;
  }
`;

function ProfileTab() {
  const reputationCnt = 1;
  const reachedCnt = 0;
  const answersCnt = 0;
  const questionsCnt = 0;

  // axios

  return (
    <ProfileTabContainer>
      <div className="left">
        <SubTabContent>
          <div className="title">Stats</div>
          <div className="box">
            <div className="main">
              <div className="flexBox2">
                <div className="flexCol">
                  <div className="flexItem">
                    <div>{reputationCnt}</div>
                    <div>reputation</div>
                  </div>
                  <div className="flexItem">
                    <div>{answersCnt}</div>
                    <div>answers</div>
                  </div>
                </div>
                <div className="flexCol">
                  <div className="flexItem">
                    <div>{reachedCnt}</div>
                    <div>reached</div>
                  </div>
                  <div className="flexItem">
                    <div>{questionsCnt}</div>
                    <div>questions</div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </SubTabContent>
        <SubTabContent>
          <div className="flexBox">
            <div className="title">Communities</div>
            <div>Edit</div>
          </div>
          <div className="box">
            <div className="main">Stackoverflow</div>
          </div>
        </SubTabContent>
      </div>
      <div className="right">
        {/* About */}
        <SubTabContent
          bgColor={COLORS.subtabBgColor2}
          bdrColor={COLORS.subtabBorder2}
          padding="32px"
        >
          <div className="title">About</div>
          <div className="box">
            <div className="main">
              Your about me section is currently blank. Would you like to add
              one? Edit profile
            </div>
          </div>
        </SubTabContent>
        {/* Badges */}
        <SubTabContent
          bgColor={COLORS.subtabBgColor2}
          bdrColor={COLORS.subtabBorder2}
          padding="32px"
        >
          <div className="title">Badges</div>
          <div className="box">
            <div className="main">You have not earned any badges.</div>
          </div>
        </SubTabContent>
        {/* Posts */}
        <SubTabContent
          bgColor={COLORS.subtabBgColor2}
          bdrColor={COLORS.subtabBorder2}
          padding="48px"
        >
          <div className="title">Posts</div>
          <div className="box">
            <div className="main">
              <div>image</div>
              <p>
                Just getting started? Try answering a question! Your most
                helpful questions, answers and tags will appear here. Start by
                answering a question or selecting tags that match topics you’re
                interested in.
              </p>
            </div>
          </div>
        </SubTabContent>
      </div>
    </ProfileTabContainer>
  );
}

// string convert functions
const convertToPathStyle = (str) => {
  return str.replace("&", "").toLowerCase().replaceAll(" ", "");
};

const convertToComponentStyle = (str) => {
  return str
    .replace("&", "N")
    .split(" ")
    .map((el) => el[0].toUpperCase() + el.slice(1).toLowerCase())
    .join("");
};

// Activity Tab
const activityTabComponents = {
  Summary: ActivitySummaryTab,
  Answers: ActivityAnswersTab,
  Questions: ActivityQuestionsTab,
  Tags: ActivityTagsTab,
  Articles: ActivityArticlesTab,
  Badges: ActivityBadgesTab,
  Following: ActivityFollowingTab,
  Bounties: ActivityBountiesTab,
  Reputation: ActivityReputationTab,
  AllActions: ActivityAllActionsTab,
  Response: ActivityResponseTab,
  Votes: ActivityVotesTab,
};

function ActivitySummaryTab() {
  return (
    <SubTabContent padding="48px">
      <div className="title">Summary</div>
      <div className="box fr">
        <div className="main" style={{ fontSize: "35px" }}>
          텅
        </div>
      </div>
    </SubTabContent>
  );
}
function ActivityAnswersTab() {
  // axios
  const answersCnt = 0;

  const sortOptionArr = ["Score", "Activity", "Newest", "Views"];

  const [sortOption, setSortOption] = useState("Score");

  const handleSortOption = (e, option) => {
    setSortOption(option);
  };

  // console.log("activity answers tab sort option: ", sortOption);

  return (
    <SubTabContent padding="48px">
      <div className="flexBox">
        <div className="title">{`${answersCnt} Anwers`}</div>
        <ToggleButtonGroup
          value={sortOption}
          onChange={handleSortOption}
          exclusive
          size="small"
        >
          {sortOptionArr.map((opt, idx) => (
            <ToggleButton
              key={idx}
              value={opt}
              sx={{ fontSize: "11px", textTransform: "none" }}
            >
              {opt}
            </ToggleButton>
          ))}
        </ToggleButtonGroup>
      </div>
      <div className="box fc">
        <div className="main">You have not answered any answers</div>
        <div className="delete">Deleted answers</div>
      </div>
    </SubTabContent>
  );
}
function ActivityQuestionsTab() {
  // axios
  const questionsCnt = 0;

  const sortOptionArr = ["Score", "Activity", "Newest", "Views"];

  const [sortOption, setSortOption] = useState("Score");

  const handleSortOption = (e, option) => {
    setSortOption(option);
  };

  return (
    <SubTabContent padding="48px">
      <div className="flexBox">
        <div className="title">{`${questionsCnt} Questions`}</div>
        <ToggleButtonGroup
          value={sortOption}
          onChange={handleSortOption}
          exclusive
          size="small"
        >
          {sortOptionArr.map((opt, idx) => (
            <ToggleButton
              key={idx}
              value={opt}
              sx={{ fontSize: "11px", textTransform: "none" }}
            >
              {opt}
            </ToggleButton>
          ))}
        </ToggleButtonGroup>
      </div>
      <div className="box fc">
        <div className="main">You have not answered any questions</div>
        <div className="delete">Deleted questions</div>
      </div>
    </SubTabContent>
  );
}
const TestContainer = styled.div`
  background-color: ivory;
  margin-bottom: 32px;

  & > .test_fr {
    display: flex;
    justify-content: space-between;
    align-items: center;
    & > .test_title {
      color: ${(props) => COLORS.subtabColor};
      font-size: 21px;
      margin-bottom: 8px;
    }
    & > .test_opts {
      background-color: pink;
    }
  }

  & > .test_box {
    background-color: white;
    border: 1px solid;
    border-color: ${(props) => COLORS.subtabBorder};
    border-radius: 5px;
    & > .test_upper {
      border-bottom: 1px solid;
      border-color: ${(props) => COLORS.subtabBorder};
      padding: 32px;
      /* width: auto; */
    }
    & > .test_lower {
      padding: 12px;
    }
  }
`;

function ActivityTagsTab() {
  return (
    <TestContainer>
      <div className="test_fr">
        <div className="test_title">0 Answers</div>
        <div className="test_opts">options</div>
      </div>
      <div className="test_box">
        <div className="test_upper">You have not answered any questions</div>
        <div className="test_lower">Deleted answers</div>
      </div>
    </TestContainer>
  );
}
function ActivityArticlesTab() {
  //
}
function ActivityBadgesTab() {
  //
}
function ActivityFollowingTab() {
  //
}
function ActivityBountiesTab() {
  //
}
function ActivityReputationTab() {
  //
}
function ActivityAllActionsTab() {
  //
}
function ActivityResponseTab() {
  //
}
function ActivityVotesTab() {
  //
}

function ActivityTab() {
  const [activityTabIdx, setActivityTabIdx] = useState(1);
  const activityTab = [
    "Summary",
    "Answers",
    "Questions",
    "Tags",
    "Articles",
    "Badges",
    "Following",
    "Bounties",
    "Reputation",
    "All actions",
    "Response",
    "Votes",
  ];

  const FocusedActivityTabComponent =
    activityTabComponents[convertToComponentStyle(activityTab[activityTabIdx])];

  return (
    <SubTabContainer>
      <SubTabBtnWrapper>
        {activityTab.map((tab, idx) => {
          return (
            <Link key={idx} to={convertToPathStyle(tab)}>
              <TabBtn
                className={activityTabIdx === idx ? "focused" : ""}
                onClick={() => setActivityTabIdx(idx)}
                width="122.75px"
              >
                {tab}
              </TabBtn>
            </Link>
          );
        })}
      </SubTabBtnWrapper>
      <Routes>
        {activityTab.map((tab, idx) => (
          <Route
            key={idx}
            path={convertToPathStyle(tab)}
            element={<FocusedActivityTabComponent />}
          />
        ))}
      </Routes>
    </SubTabContainer>
  );
}

// Saves Tab
const savesTabComponents = {
  AllSaves: SavesAllSavesTab,
  ForLater: SavesForLaterTab,
};

function SavesAllSavesTab() {
  return (
    <SubTabContent padding="48px">
      <div className="title">All Saves</div>
      <div className="box fr">
        <div className="main" style={{ fontSize: "35px" }}>
          텅
        </div>
      </div>
    </SubTabContent>
  );
}
function SavesForLaterTab() {
  //
}
function SavesTab() {
  const [savesTabIdx, setSavesTabIdx] = useState(0);
  const savesTab = ["All Saves", "For later"];
  const FocusedSavesTabComponent =
    savesTabComponents[convertToComponentStyle(savesTab[savesTabIdx])];

  return (
    <SubTabContainer>
      <SubTabBtnWrapper>
        {savesTab.map((tab, idx) => {
          return (
            <Link key={idx} to={convertToPathStyle(tab)}>
              <TabBtn
                className={savesTabIdx === idx ? "focused" : ""}
                onClick={() => setSavesTabIdx(idx)}
                width="210.66px"
              >
                {tab}
              </TabBtn>
            </Link>
          );
        })}
        <div className="flexBox">
          <div>MY LISTS</div>
          <div>+</div>
        </div>
      </SubTabBtnWrapper>
      <Routes>
        {savesTab.map((tab, idx) => (
          <Route
            key={idx}
            path={convertToPathStyle(tab)}
            element={<FocusedSavesTabComponent />}
          />
        ))}
      </Routes>
    </SubTabContainer>
  );
}

// Settings Tab
const settingsTabComponents = {
  PersonalInformation: SettingsPersonalInformationTab,
  EditProfile: SettingsEditProfileTab,
  DeleteProfile: SettingsDeleteProfileTab,
  EmailSettings: SettingsEmailSettingsTab,
  EditEmailSettings: SettingsEditEmailSettingsTab,
  TagWatchingNIgnoring: SettingsTagWatchingNIgnoringTab,
  CommunityDigests: SettingsCommunityDigestsTab,
  QuestionSubscriptions: SettingsQuestionSubscriptionsTab,
  SiteSettings: SettingsSiteSettingsTab,
  Preferences: SettingsPreferencesTab,
  Flair: SettingsFlairTab,
  HideCommunities: SettingsHideCommunitiesTab,
  Access: SettingsAccessTab,
  YourCollectives: SettingsYourCollectivesTab,
  YourLogins: SettingsYourLoginsTab,
  Api: SettingsApiTab,
  AuthorizedApplications: SettingsAuthorizedApplicationsTab,
};
function SettingsPersonalInformationTab() {
  //
}
function SettingsEditProfileTab() {
  const state = useSelector((state) => state);
  const dispatch = useDispatch();

  const handleSubmit = (e) => {
    e.preventDefault();
    // displayName PATCH하고 새로 GET한 값을 redux state로 저장
    axios
      .patch(
        `http://ec2-13-124-137-67.ap-northeast-2.compute.amazonaws.com:8080/members/${state.memberId}`,
        {
          displayName,
          password: "",
        }
      )
      .then((res) => {
        if (res.status === 200) {
          axios
            .get(
              `http://ec2-13-124-137-67.ap-northeast-2.compute.amazonaws.com:8080/members/profile/${state.memberId}`
            )
            .then((res) => {
              const { displayName } = res.data.data.member;
              dispatch(
                saveMemberInfo({
                  displayName,
                })
              );
              return;
            })
            .catch(console.log);
        }
        return;
      })
      .catch(console.log);
  };

  const [displayName, setDisplayName] = useState(state.displayName);

  return (
    <form>
      <SubTabContent padding="24px">
        <div className="subtabTitle">Edit your profile</div>
        <div className="contentItem">
          <div className="title">Public information</div>
          <div className="box">
            <div className="main">
              <div className="item">
                <div className="itemTitle">Profile image</div>
                <Box
                  sx={{
                    width: "164px",
                    height: "164px",
                    backgroundColor: `${state.profileColor}`,
                    borderRadius: "5px",
                    display: "flex",
                    justifyContent: "center",
                    alignItems: "center",
                  }}
                >
                  <Typography
                    sx={{
                      color: "white",
                      fontWeight: "bold",
                      fontSize: "37px",
                    }}
                  >
                    {state.displayName}
                  </Typography>
                </Box>
              </div>
              <div className="item">
                <div className="itemTitle">Display name</div>
                <input
                  type="text"
                  value={displayName}
                  className="publicInfo"
                  onChange={(e) => setDisplayName(e.target.value)}
                />
              </div>
              <div className="item">
                <div className="itemTitle">Location</div>
                <input
                  type="text"
                  disabled
                  placeholder="Seoul"
                  className="publicInfo"
                />
              </div>
              <div className="item">
                <div className="itemTitle">Title</div>
                <input
                  type="text"
                  disabled
                  placeholder="No title has been set"
                  className="publicInfo"
                />
              </div>
              <div className="item">
                <div className="itemTitle">About me</div>
                <input
                  type="textarea"
                  value="markdown input UI but disabled"
                  disabled
                />
              </div>
            </div>
          </div>
        </div>

        <div className="contentItem">
          <div className="title">Links</div>
          <div className="box">
            <div className="main fr">
              <div className="item fg1">
                <div className="itemTitle">Website link</div>
                <input type="text" className="links" value={""} disabled />
              </div>
              <div className="item fg1">
                <div className="itemTitle">Twitter link or username</div>
                <input type="text" className="links" value={""} disabled />
              </div>
              <div className="item fg1 mb12">
                <div className="itemTitle">GitHub link or username</div>
                <input type="text" className="links" value={""} disabled />
              </div>
            </div>
          </div>
        </div>

        <div className="contentItem">
          <div className="title">
            Private information <span>Not shown publicly</span>
          </div>
          <div className="box">
            <div className="main">
              <div className="item">
                <div className="itemTitle">Full name</div>
                <input type="text" value={state.displayName} disabled />
              </div>
            </div>
          </div>
        </div>

        <div className="fr">
          <input
            type="submit"
            value="Save profile"
            id="saveProfileBtn"
            onClick={handleSubmit}
          />
          <button id="cancelProfileChangesBtn" value="Cancel" />
        </div>
      </SubTabContent>
    </form>
  );
}
function SettingsDeleteProfileTab() {
  //
}
function SettingsEmailSettingsTab() {
  //
}
function SettingsEditEmailSettingsTab() {
  return (
    <SubTabContent padding="48px">
      <div className="title">Edit Email Settings</div>
      <div className="box fr">
        <div className="main" style={{ fontSize: "35px" }}>
          텅
        </div>
      </div>
    </SubTabContent>
  );
}
function SettingsTagWatchingNIgnoringTab() {
  return (
    <SubTabContent padding="48px">
      <div className="title">Tag Watching & Ignoring</div>
      <div className="box fr">
        <div className="main" style={{ fontSize: "35px" }}>
          비었지롱
        </div>
      </div>
    </SubTabContent>
  );
}
function SettingsCommunityDigestsTab() {
  //
}
function SettingsQuestionSubscriptionsTab() {
  //
}
function SettingsSiteSettingsTab() {
  //
}
function SettingsPreferencesTab() {
  //
}
function SettingsFlairTab() {
  //
}
function SettingsHideCommunitiesTab() {
  //
}
function SettingsAccessTab() {
  //
}
function SettingsYourCollectivesTab() {
  //
}
function SettingsYourLoginsTab() {
  //
}
function SettingsApiTab() {
  //
}
function SettingsAuthorizedApplicationsTab() {
  //
}
function SettingsTab() {
  const [settingsTabIdx, setSettingsTabIdx] = useState(1);

  const settingsTab = [
    { type: "TAB_CATEGORY", element: "PERSONAL INFORMATION" },
    { type: "TAB", element: "Edit profile" },
    { type: "TAB", element: "Delete profile" },
    { type: "TAB_CATEGORY", element: "EMAIL SETTINGS" },
    { type: "TAB", element: "Edit email settings" },
    { type: "TAB", element: "Tag watching & ignoring" },
    { type: "TAB", element: "Community digests" },
    { type: "TAB", element: "Question subscriptions", icon: "OpenInNew" },
    { type: "TAB_CATEGORY", element: "SITE SETTINGS" },
    { type: "TAB", element: "Preferences" },
    { type: "TAB", element: "Flair" },
    { type: "TAB", element: "Hide Communities" },
    { type: "TAB_CATEGORY", element: "ACCESS" },
    { type: "TAB", element: "Your Collectives" },
    { type: "TAB", element: "Your logins" },
    { type: "TAB_CATEGORY", element: "API" },
    { type: "TAB", element: "Authorized applications" },
  ];

  const FocusedSettingsTabComponent =
    settingsTabComponents[
      convertToComponentStyle(settingsTab[settingsTabIdx].element)
    ];

  return (
    <SubTabContainer>
      <SubTabBtnWrapper>
        {settingsTab.map((tab, idx) => {
          return (
            <>
              {tab.type !== "TAB" ? (
                <div key={idx} className="tabCategory">
                  {tab.element}
                </div>
              ) : (
                <Link key={idx} to={convertToPathStyle(tab.element)}>
                  <TabBtn
                    className={settingsTabIdx === idx ? "focused" : ""}
                    onClick={() => setSettingsTabIdx(idx)}
                    width="174.953px"
                  >
                    {tab.element}
                    {tab.icon && <OpenInNew fontSize="13px" />}
                  </TabBtn>
                </Link>
              )}
            </>
          );
        })}
      </SubTabBtnWrapper>
      <Routes>
        {settingsTab.map((tab, idx) => (
          <Route
            key={idx}
            path={convertToPathStyle(tab.element)}
            element={<FocusedSettingsTabComponent />}
          />
        ))}
      </Routes>
    </SubTabContainer>
  );
}

function Users() {
  return (
    <UsersContainer>
      <ProfileBlock />
      <MainTab />
    </UsersContainer>
  );
}

// etc.
const COLORS = {
  tabColor: "hsl(210,8%,35%)",
  tab_hoverbgColor: "hsl(210,8%,90%)",
  tab_clickedColor: "white",
  tab_clickedBgColor: "hsl(27,90%,55%)",
  subtabColor: "#232629",
  subtabBorder: "hsl(210,8%,85%)",
  subtabBorder2: "hsl(210,8%,90%)",
  subtabBgColor: "white",
  subtabBgColor2: "hsl(210,8%,97.5%)",
};
// const CustomToggleButtonGroup = (
//   optState,
//   optArr,
//   handler,
//   btnGroupSize,
//   btnFontSize
// ) => {
//   return (
//     <ToggleButtonGroup
//       value={optState}
//       onChange={handler}
//       exclusive
//       size={btnGroupSize}
//     >
//       {optArr.map((opt, idx) => (
//         <ToggleButton key={idx} value={opt} sx={{ fontSize: btnFontSize }}>
//           {opt}
//         </ToggleButton>
//       ))}
//     </ToggleButtonGroup>
//   );
// };

export default Users;
