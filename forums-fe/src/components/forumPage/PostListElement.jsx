import React, {useEffect, useState} from 'react';
import {Button, Card} from "react-bootstrap";
import {useNavigate} from "react-router-dom";
import {FaTrash} from "react-icons/fa";
import {deletePost} from "../../redux/posts/postsSlice";
import {useDispatch, useSelector} from "react-redux";

export const PostListElement = ({post, forumId}) => {
    const navigate = useNavigate();
    const isAdmin = useSelector(state => state.auth.isAdmin);
    const users = useSelector(state => state.users.userList);
    const dispatch = useDispatch();
    const [author, setAuthor] = useState(null);
    useEffect(() => {
        const user = users.find(u => u.user_id === post.authorId);
        if(user) {
            setAuthor(user);
        }
    }, [users]);

    const navigateToPost = () => {
        navigate(`/forum/${forumId}/post/${post.id}`);
    };

    function handleDeletePost(event) {
        event.stopPropagation();
        dispatch(deletePost({forumId: forumId, postId: post.id}));
    }

    function renderDeleteButton() {
        return <Button variant="outline-secondary" className="delete-button" onClick={handleDeletePost} disabled={!isAdmin}><FaTrash /></Button>
    }

    return <Card
        onClick={navigateToPost}
        className="list-item-card"
        key={post.id}>
        <Card.Body>
            <Card.Subtitle className="mb-2 text-muted">Created by {author ? author.name : 'Unknown'} at {post.time}</Card.Subtitle>
            <div>{renderDeleteButton()}</div>
            <div>{post.title}</div>
        </Card.Body>
    </Card>
}