import { createAsyncThunk, createSlice } from "@reduxjs/toolkit";
import { client } from "../../utils/client";
import {API_STATUS} from "../../utils/consts";

export const fetchUsers = createAsyncThunk("users/getUsers", async (stam, thunkAPI) => {
    return await client.get("/api/user", thunkAPI.getState().auth.token);
});


export const usersSlice = createSlice({
    name: "users",
    initialState: {
        userList: [],
        fetchUsersStatus: API_STATUS.IDLE,
    },
    reducers: {

    },
    extraReducers(builder) {
        builder
            .addCase(fetchUsers.fulfilled, (state, action) => {
                state.fetchUsersStatus = action.payload.status;
                state.userList = action.payload.data.items;
            })
            .addCase(fetchUsers.rejected, (state, action) => {
                const err = JSON.parse(action.error.message);
                state.fetchUsersStatus = err.status;
                state.userList = [];
            })
    },
});

export const { } = usersSlice.actions;

export default usersSlice.reducer;