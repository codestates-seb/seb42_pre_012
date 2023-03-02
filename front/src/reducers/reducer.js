import { SAVE_MEMBER_INFO } from "../actions/actions";
import { defaultMemberInfoData } from "./defaultMemberInfoData";

const reducer = (state = defaultMemberInfoData, action) => {
  switch (action.type) {
    case SAVE_MEMBER_INFO:
      return {
        ...state,
        ...action.payload.data,
      };
    default:
      return state;
  }
};

export default reducer;
