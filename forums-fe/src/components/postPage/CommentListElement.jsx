import {Button, Card} from "react-bootstrap";
import React, {useEffect, useState}  from "react";
import {useDispatch, useSelector} from "react-redux";
import {FaTrash} from "react-icons/fa";
import {deleteComment} from "../../redux/posts/postsSlice";

export const CommentListElement = ({comment, forumId, postId}) => {
    const isAdmin = useSelector(state => state.auth.isAdmin);
    const users = useSelector(state => state.users.userList);
    const dispatch = useDispatch();
    const [author, setAuthor] = useState(null);

    useEffect(() => {
        console.log([comment])
        const user = users.find(u => u.user_id === comment.authorId);
        if(user) {
            setAuthor(user);
        }
    }, [users]);

    function handleDeleteComment(event) {
        event.stopPropagation();
        dispatch(deleteComment({forumId: forumId, postId: postId, commentId: comment.id}));
    }

    function renderDeleteButton() {
        return <Button variant="outline-secondary" className="delete-button" onClick={handleDeleteComment} disabled={!isAdmin}><FaTrash /></Button>
    }
    return <Card className="list-item-card">
        <Card.Body>
            <Card.Subtitle className="mb-2 text-muted">Posted at {new Date(comment.time).toLocaleString()} by {author ? author.name : 'Unknown'}</Card.Subtitle>
            <Card.Text className="pre-wrap">{comment.content}</Card.Text>
            {renderDeleteButton()}
        </Card.Body>
    </Card>
}