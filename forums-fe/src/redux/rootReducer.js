import { combineReducers } from "redux";
import authReducer from "./auth/authSlice";
import forumsReducer from "./forums/forumsSlice";
import postsReducer from "./posts/postsSlice";
import usersReducer from "./users/usersSlice";
import chatReducer from "./chat/chatSlice";

const combinedReducer = combineReducers({
    users: usersReducer,
    auth: authReducer,
    forums: forumsReducer,
    posts: postsReducer,
    chat: chatReducer
});

export const rootReducer = (state, action) => {
    return combinedReducer(state, action);
};
