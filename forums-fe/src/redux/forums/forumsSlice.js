import { createAsyncThunk, createSlice } from "@reduxjs/toolkit";
import { client } from "../../utils/client";
import {API_STATUS} from "../../utils/consts";

export const fetchForums = createAsyncThunk("forums/getForums", async (stam, thunkAPI) => {
    return await client.get("/api/forum", thunkAPI.getState().auth.token);
});

export const createForum = createAsyncThunk("forums/createForum", async (subject, thunkAPI) => {
    const result =  await client.post(`/api/forum`, thunkAPI.getState().auth.token,
        {subject: subject});
    thunkAPI.dispatch(fetchForums());
    return result;
});

export const deleteForum = createAsyncThunk("forums/deleteForum", async (forumId, thunkAPI) => {
    const result = await client.delete(`/api/forum/${forumId}`, thunkAPI.getState().auth.token);
    thunkAPI.dispatch(fetchForums());
    return result;
});


export const forumsSlice = createSlice({
    name: "forums",
    initialState: {
        forumList: [],
        fetchForumsStatus: API_STATUS.IDLE,
        createForumStatus: API_STATUS.IDLE,
        deleteForumsStatus: API_STATUS.IDLE
    },
    reducers: {

    },
    extraReducers(builder) {
        builder
            .addCase(fetchForums.fulfilled, (state, action) => {
                state.fetchForumsStatus = action.payload.status;
                state.forumList = action.payload.data;
            })
            .addCase(fetchForums.rejected, (state, action) => {
                const err = JSON.parse(action.error.message);
                state.fetchForumsStatus = err.status;
                state.forumList = [];
            })
            .addCase(createForum.fulfilled, (state, action) => {
                state.createForumStatus = action.payload.status;
            })
            .addCase(createForum.rejected, (state, action) => {
                const err = JSON.parse(action.error.message);
                state.createForumStatus = err.status;
                state.forumList = [];
            })
            .addCase(deleteForum.fulfilled, (state, action) => {
                state.deleteForumsStatus = action.payload.status;
            })
            .addCase(deleteForum.rejected, (state, action) => {
                const err = JSON.parse(action.error.message);
                state.deleteForumsStatus = err.status;
            })
    },
});

export const { } = forumsSlice.actions;

export default forumsSlice.reducer;