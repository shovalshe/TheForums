import { createAsyncThunk, createSlice } from "@reduxjs/toolkit";
import { client } from "../../utils/client";
import {API_STATUS} from "../../utils/consts";
import {fetchForums} from "../forums/forumsSlice";

export const fetchPosts = createAsyncThunk("posts/getPosts", async (forumId, thunkAPI) => {
    return await client.get(`/api/forum/${forumId}`, thunkAPI.getState().auth.token);
});

export const createPost = createAsyncThunk("posts/createPost", async (post, thunkAPI) => {
    const result = await client.post(`/api/forum/${post.forumId}/post`, thunkAPI.getState().auth.token,
        {
            title: post.title,
            content: post.content
        });
    thunkAPI.dispatch(fetchPosts(post.forumId));
    return result;
});

export const deletePost = createAsyncThunk("posts/deletePost", async (post, thunkAPI) => {
    const result = await client.delete(`/api/forum/${post.forumId}/post/${post.postId}`, thunkAPI.getState().auth.token);
    thunkAPI.dispatch(fetchPosts(post.forumId));
    return result;
});

export const getPost = createAsyncThunk("posts/getPost", async (post, thunkAPI) => {
    return await client.get(`/api/forum/${post.forumId}/post/${post.postId}`,thunkAPI.getState().auth.token);
});

export const getPostComments = createAsyncThunk("posts/getPostComments", async (post, thunkAPI) => {
    return await client.get(`/api/forum/${post.forumId}/post/${post.postId}/comment`,thunkAPI.getState().auth.token);
});

export const createPostComment = createAsyncThunk("posts/createPostComment", async (comment, thunkAPI) => {
    const result = await client.post(`/api/forum/${comment.forumId}/post/${comment.postId}/comment`,
        thunkAPI.getState().auth.token, {
            content: comment.content
        });
    thunkAPI.dispatch(getPostComments({forumId: comment.forumId, postId: comment.postId}));
    return result;
});

export const deleteComment = createAsyncThunk("posts/deleteComment", async (comment, thunkAPI) => {
    const result = await client.delete(`/api/forum/${comment.forumId}/post/${comment.postId}/comment/${comment.commentId}`, thunkAPI.getState().auth.token);
    thunkAPI.dispatch(getPostComments({forumId: comment.forumId, postId: comment.postId}));
    return result;
});

export const postsSlice = createSlice({
    name: "posts",
    initialState: {
        postList: [],
        post: null,
        fetchPostsStatus: API_STATUS.IDLE,
        createPostStatus: API_STATUS.IDLE,
        getPostStatus: API_STATUS.IDLE,
        createPostCommentStatus: API_STATUS.IDLE,
        deletePostStatus: API_STATUS.IDLE,
        deleteCommentStatus: API_STATUS.IDLE
    },
    reducers: {

    },
    extraReducers(builder) {
        builder
            .addCase(fetchPosts.fulfilled, (state, action) => {
                state.fetchPostsStatus = action.payload.status;
                state.postList = action.payload.data;
            })
            .addCase(fetchPosts.rejected, (state, action) => {
                const err = JSON.parse(action.error.message);
                state.fetchPostsStatus = err.status;
                state.postList = [];
            })
            .addCase(createPost.fulfilled, (state, action) => {
                state.createPostStatus = action.payload.status;
            })
            .addCase(createPost.rejected, (state, action) => {
                const err = JSON.parse(action.error.message);
                state.createPostStatus = err.status;
                state.postList = [];
            })
            .addCase(getPost.fulfilled, (state, action) => {
                state.getPostStatus = action.payload.status;
                state.post = action.payload.data;
            })
            .addCase(getPost.rejected, (state, action) => {
                const err = JSON.parse(action.error.message);
                state.getPostStatus = err.status;
                state.post = null;
            })
            .addCase(getPostComments.fulfilled, (state, action) => {
                state.getPostStatus = action.payload.status;
                state.post.comments = action.payload.data;
            })
            .addCase(getPostComments.rejected, (state, action) => {
                const err = JSON.parse(action.error.message);
                state.getPostStatus = err.status;
                state.post.comments = null;
            })
            .addCase(createPostComment.fulfilled, (state, action) => {
                state.createPostCommentStatus = action.payload.status;
            })
            .addCase(createPostComment.rejected, (state, action) => {
                const err = JSON.parse(action.error.message);
                state.createPostCommentStatus = err.status;
            })
            .addCase(deletePost.fulfilled, (state, action) => {
                state.deletePostStatus = action.payload.status;
            })
            .addCase(deletePost.rejected, (state, action) => {
                const err = JSON.parse(action.error.message);
                state.deletePostStatus = err.status;
            })
            .addCase(deleteComment.fulfilled, (state, action) => {
                state.deleteCommentStatus = action.payload.status;
            })
            .addCase(deleteComment.rejected, (state, action) => {
                const err = JSON.parse(action.error.message);
                state.deleteCommentStatus = err.status;
            })
    },
});

export const { } = postsSlice.actions;

export default postsSlice.reducer;