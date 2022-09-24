import { createAsyncThunk, createSlice } from "@reduxjs/toolkit";
import { client } from "../../utils/client";
import {API_STATUS} from "../../utils/consts";

const initialState = {
    isAuthenticated: false,
    token: null,
    user: null,
    parsedToken: null,
    isAdmin: false
}

export const authenticationSlice = createSlice({
    name: "auth",
    initialState: initialState,
    reducers: {
        authenticate: (state, action) => {
            state.isAuthenticated = action.payload.isAuthenticated;
            state.token = action.payload.token;
            state.user = action.payload.user;
            state.parsedToken = action.payload.parsedToken;
            if(action.payload.parsedToken.scope.includes('admin')) {
                state.isAdmin = true;
            }
        },
        unAuthenticate: (state) => {
            state = initialState;
        },
    },
    extraReducers(builder) {},
});

export const { authenticate, unAuthenticate } = authenticationSlice.actions;

export default authenticationSlice.reducer;