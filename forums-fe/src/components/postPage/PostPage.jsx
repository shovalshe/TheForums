import React, {useEffect, useState} from 'react';
import {useDispatch, useSelector} from "react-redux";
import {useParams} from "react-router-dom";
import {Button, Card} from "react-bootstrap";
import Container from "react-bootstrap/Container";
import {getPost, getPostComments} from "../../redux/posts/postsSlice";
import {CreateCommentModal} from "./CreateCommentModal";
import {CommentListElement} from "./CommentListElement";

export const PostPage = () => {
    const dispatch = useDispatch();
    const post = useSelector(state => state.posts.post);
    const routeParams = useParams();
    const [showModal, setShowModal] = useState(false);
    const [author, setAuthor] = useState(null);
    const users = useSelector(state => state.users.userList);

    useEffect(() => {
        if(post) {
            const user = users.find(u => u.user_id === post.authorId);
            if (user) {
                setAuthor(user);
            }
        }
    }, [users, post]);

    useEffect(() => {
        if(routeParams.forumId && routeParams.postId) {
            dispatch(getPost({
                forumId: routeParams.forumId,
                postId: routeParams.postId
            }));
            dispatch(getPostComments({
                forumId: routeParams.forumId,
                postId: routeParams.postId
            }));
        }
    }, [routeParams]);

    function renderComments(comments) {
        return post.comments.map(comment => <CommentListElement comment={comment} forumId={routeParams.forumId} postId={routeParams.postId}/>);
    }

    function renderPage() {
        if(post) {
            return <React.Fragment>
                <Card className="list-item-card">
                    <Card.Header>{post.title}</Card.Header>
                    <Card.Body>
                        <Card.Subtitle className="mb-2 text-muted">Posted at {new Date(post.time).toLocaleString()} by {author ? author.name : 'Unknown'}</Card.Subtitle>
                        <Card.Text className="pre-wrap">{post.content}</Card.Text>
                    </Card.Body>
                </Card>
                {renderComments()}
                <Button onClick={handleShowModal}>Add Comment</Button>
            </React.Fragment>
        }
    }

    const handleShowModal = () => {
        setShowModal(true);
    }

    const handleCloseModal = () => {
        setShowModal(false);
    }

    return <Container className="page-container">
        <CreateCommentModal show={showModal} handleClose={handleCloseModal} forumId={routeParams.forumId} postId={routeParams.postId}/>
        {renderPage()}
    </Container>
}