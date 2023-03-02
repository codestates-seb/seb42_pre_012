// action types
export const SAVE_MEMBER_INFO = "SAVE_MEMBER_INFO";

// action controllers
export const saveMemberInfo = (memberInfo) => {
  return {
    type: SAVE_MEMBER_INFO,
    payload: memberInfo,
  };
};
