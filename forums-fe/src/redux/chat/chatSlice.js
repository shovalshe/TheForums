import { createAsyncThunk, createSlice } from "@reduxjs/toolkit";
import { client } from "../../utils/client";
import {API_STATUS} from "../../utils/consts";
import {fetchForums} from "../forums/forumsSlice";
import {getPostComments} from "../posts/postsSlice";

export const fetchMyChats = createAsyncThunk("chat/getCHats", async (stam, thunkAPI) => {
    return await client.get(`/api/chat`, thunkAPI.getState().auth.token);
});

export const getChatMessagesWithOther = createAsyncThunk("chat/getChatMessagesWithParticipant", async (otherId, thunkAPI) => {
    return await client.get(`/api/chat/with/${otherId}`, thunkAPI.getState().auth.token);
});

export const postChatMessage = createAsyncThunk("chat/postMessage", async (chatMessage, thunkAPI) => {
    const result = await client.post(`/api/chat/to/${chatMessage.userId}/message`, thunkAPI.getState().auth.token,
        {
            content: chatMessage.content
        });
    thunkAPI.dispatch(getChatMessagesWithOther(chatMessage.userId));
    thunkAPI.dispatch(fetchMyChats());
    return result;
});
export const deleteChatMessage = createAsyncThunk("chat/deleteMessage", async (message, thunkAPI) => {
    const result = await client.delete(`/api/chat/message/${message.messageId}`, thunkAPI.getState().auth.token);
    thunkAPI.dispatch(getChatMessagesWithOther(message.otherId));
    return result;
});
export const chatSlice = createSlice({
    name: "chat",
    initialState: {
        chatList: [],
        currentChat: null,
        messageList: [],
        createChatStatus: API_STATUS.IDLE,
        getMessagesStatus: API_STATUS.IDLE,
        postChatMessage: API_STATUS.IDLE,
        deleteMessageStatus: API_STATUS.IDLE,

    },
    reducers: {
        resetMessageList: (state, action) => {
            state.messageList = [];
        }
    },
    extraReducers(builder) {
        builder
            .addCase(fetchMyChats.fulfilled, (state, action) => {
                state.createChatStatus = action.payload.status;
                state.chatList = action.payload.data;
            })
            .addCase(fetchMyChats.rejected, (state, action) => {
                const err = JSON.parse(action.error.message);
                state.fetchPostsStatus = err.status;
                state.postList = [];
            })
            .addCase(getChatMessagesWithOther.fulfilled, (state, action) => {
                state.getMessagesStatus = action.payload.status;
                state.messageList = action.payload.data;
            })
            .addCase(getChatMessagesWithOther.rejected, (state, action) => {
                const err = JSON.parse(action.error.message);
                state.getMessagesStatus = err.status;
                state.messageList = [];
            })
            .addCase(postChatMessage.fulfilled, (state, action) => {
                state.postChatMessage = action.payload.status;
            })
            .addCase(postChatMessage.rejected, (state, action) => {
                const err = JSON.parse(action.error.message);
                state.postChatMessage = err.status;
            })
    },
});

export const {resetMessageList } = chatSlice.actions;

export default chatSlice.reducer;