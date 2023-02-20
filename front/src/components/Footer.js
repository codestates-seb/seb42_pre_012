import styled from "styled-components";

import stackOverflowlogo from "../assets/stackOverflowlogo.png";

const FooterContainer = styled.div`
  height: 307px;
  width: 100vw;
  background-color: RGB(35, 38, 41);
  position: relative;
  display: flex;

  h1 {
    color: RGB(186, 191, 195);
    font-size: 13px;
    font-weight: 800;
    letter-spacing: 0.5px;
    margin-top: 35px;
    margin-bottom: 20px;
  }

  h2 {
    color: RGB(144, 153, 161);
    font-size: 13.5px;
    letter-spacing: 0.3px;
    margin-bottom: 11px;
  }

  h3 {
    color: RGB(143, 152, 160);
    font-size: 12px;
    letter-spacing: 0.3px;
    margin-top: 35px;
    margin-right: 10px;
  }

  .sectionOne {
    margin-left: 158px;
  }

  .sectionTwo {
    margin-left: 100px;
  }

  .sectionThree {
    margin-left: 100px;
  }

  .sectionFour {
    margin-left: 95px;
    h2:nth-child(8) {
      margin-top: 30px;
    }
  }

  .sectionFive {
    display: flex;
    margin-left: 100px;
  }
`;

const LogoCrop = styled.div`
  position: absolute;
  top: 20px;
  left: 93px;
  width: 31px;
  overflow: hidden;

  img {
    width: 185px;
    height: auto;
  }
`;

function Footer() {
  return (
    <FooterContainer>
      <LogoCrop>
        <img src={stackOverflowlogo} alt="" />
      </LogoCrop>
      <div className="sectionOne">
        <h1>STACK OVERFLOW</h1>
        <h2>Questions</h2>
        <h2>Help</h2>
      </div>
      <div className="sectionTwo">
        <h1>PRODUCTS</h1>
        <h2>Teams</h2>
        <h2>Advertising</h2>
        <h2>Collectives</h2>
        <h2>Talent</h2>
      </div>
      <div className="sectionThree">
        <h1>COMPANY</h1>
        <h2>About</h2>
        <h2>Press</h2>
        <h2>Work Here</h2>
        <h2>Legal</h2>
        <h2>Privacy Policy</h2>
        <h2>Terms of Service</h2>
        <h2>Contact Us</h2>
        <h2>Cookie Settings</h2>
        <h2>Cookie Policy</h2>
      </div>
      <div className="sectionFour">
        <h1>STACK EXCHANGE NETWORK</h1>
        <h2>Technology</h2>
        <h2>Culture & recreation</h2>
        <h2>Life & arts</h2>
        <h2>Science</h2>
        <h2>Professional</h2>
        <h2>Business</h2>
        <h2>API</h2>
        <h2>Data</h2>
      </div>
      <div className="sectionFive">
        <h3>Blog</h3>
        <h3>Facebook</h3>
        <h3>Twitter</h3>
        <h3>LinkedIn</h3>
        <h3>Instagramx</h3>
      </div>
    </FooterContainer>
  );
}

export default Footer;
