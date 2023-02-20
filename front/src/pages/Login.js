import styled from "styled-components";
import { FaGithub, FaFacebookSquare, FaExternalLinkAlt } from "react-icons/fa";

// 로그인 페이지의 컨테이너
const LoginContainer = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;

  width: 100%;
  height: 100vh;

  background-color: #f1f2f3;

  & > img {
    margin-bottom: 24px;
  }

  a {
    color: #0074cc;
    text-decoration: none;
  }
`;

// 3개의 OAuth2 버튼
// props.bgColor: background-color
// props.color: color
// props.hoverColor: background-color while hovering
const OAuth2Btn = styled.button`
  display: flex;
  justify-content: center;
  align-items: center;

  width: 277.99px;
  height: 37.4px;
  margin: 4px 0;

  background-color: ${(props) => props.bgColor};
  color: ${(props) => props.color || "white"};

  border: 0.5px solid lightgrey;
  border-radius: 5px;
  font-size: 13px;

  &:hover {
    background-color: ${(props) => props.hoverColor};
  }

  & > * {
    margin: 0 5px;
  }
`;

// TODO: form -> 로그인 버튼 클릭 -> Axios API
const LoginBox = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;

  width: 277.99px;
  height: 233.43px;
  margin: 16px 0 24px 0;
  padding: 24px;

  border-radius: 5px;
  background-color: white;

  box-shadow: 0 10px 24px hsla(0, 0%, 0%, 0.05),
    0 20px 48px hsla(0, 0%, 0%, 0.05), 0 1px 4px hsla(0, 0%, 0%, 0.1);
  font-size: 13px;

  & > form {
    color: #0c0d0e;

    li {
      list-style: none;
    }

    label {
      /* color: #0C0D0E; */
      font-size: 15px;
      font-weight: 600;
    }

    div {
      display: flex;
      justify-content: space-between;
      align-items: center;
    }

    /* span {
      color: #0C0D0E;
    } */

    input[type="email"],
    input[type="password"] {
      width: 229.99px;
      height: 32.4px;
      margin: 5px 0 16px 0;
    }

    input[type="submit"] {
      width: 229.99px;
      height: 37.4px;
      background-color: "#0A95FF";
      color: white;
      border: none;
      border-radius: 5px;
      text-align: center;

      &:hover {
        background-color: "#0074CC";
      }
    }
  }
`;

const ExtraMsg = styled.div`
  padding: 16px;
  font-size: 13px;
  text-align: center;
  & p {
    margin: 12px 0;
  }
  & span {
    margin-left: 5px;
  }
`;

function Login() {
  // TODO: 함수 만들기
  const handleClickOAuth2Btn = (e) => {
    //
  };
  return (
    <LoginContainer>
      {/* stack overflow logo2 */}
      <img
        src="https://www.vectorlogo.zone/logos/stackoverflow/stackoverflow-icon.svg"
        alt="stackoverflow logo 2"
        width="32px"
      />

      {/* OAuth2 buttons - Google, Github, Facebook */}
      <OAuth2Btn
        bgColor={"white"}
        hoverColor={"#F8F9F9"}
        color={"#3B4045"}
        onClick={handleClickOAuth2Btn}
      >
        <img
          src="https://upload.wikimedia.org/wikipedia/commons/5/53/Google_%22G%22_Logo.svg"
          alt="Google logo"
          width="18"
        />
        Log in with Google
      </OAuth2Btn>
      <OAuth2Btn
        bgColor={"#2F3337"}
        hoverColor={"#232629"}
        onClick={handleClickOAuth2Btn}
      >
        <FaGithub size="18" alt="Github logo" />
        Log in with GitHub
      </OAuth2Btn>
      <OAuth2Btn
        bgColor={"#385499"}
        hoverColor={"#314A86"}
        onClick={handleClickOAuth2Btn}
      >
        <FaFacebookSquare size="18" alt="Facebook logo" />
        Log in with Facebook
      </OAuth2Btn>

      {/* Login box; form */}
      <LoginBox>
        <form action="">
          <label htmlFor="email_input">Email</label>
          <input type="email" id="email_input" autoComplete="off" />
          <div>
            <label htmlFor="password_input">Password</label>
            <span>
              <a href="#">Forgot password?</a>
            </span>
          </div>
          <input type="password" id="password_input" />
          <input type="submit" value="Log in" />
        </form>
      </LoginBox>

      {/* Extra messages */}
      <ExtraMsg>
        <p>
          Don't have an account?{" "}
          <span>
            <a href="#">Sign up</a>
          </span>
        </p>
        <p>
          Are you an employer?
          <a href="#">
            <span>Sign up on Talent</span>
            <span>
              <FaExternalLinkAlt size="12" alt="external link icon" />
            </span>
          </a>
        </p>
      </ExtraMsg>
    </LoginContainer>
  );
}

export default Login;
