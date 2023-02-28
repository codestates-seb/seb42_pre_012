import styled from "styled-components";

const UsersContainer = styled.div`
  height: 100vh;
  display: flex;
  margin-left: 17%;
  margin-top: 52px;
  background-color: white;
  width: 52.2%;
  border-left: 1px solid RGB(225, 226, 228);

  h1 {
    margin-top: 30px;
    margin-left: 25px;
    font-size: 27px;
    font-weight: 500;
  }
`;

function Users() {
  return (
    <UsersContainer>
      <h1>Users</h1>
    </UsersContainer>
  );
}

export default Users;
