import React, {useEffect, useState} from 'react';
import {useDispatch, useSelector} from "react-redux";
import {useParams} from "react-router-dom";
import {Button} from "react-bootstrap";
import {CreatePostModal} from "./CreatePostModal";
import Container from "react-bootstrap/Container";
import {PostListElement} from "./PostListElement";
import {fetchPosts} from "../../redux/posts/postsSlice";

export const ForumPage = () => {
    const dispatch = useDispatch();
    const forumPosts = useSelector(state => state.posts.postList);
    const routeParams = useParams();
    const [showModal, setShowModal] = useState(false);

    useEffect(() => {
        if(routeParams.forumId) {
            dispatch(fetchPosts(routeParams.forumId));
        }
    }, [routeParams]);


    function renderPosts() {
        return forumPosts.posts.map(post => <PostListElement
            post={post}
            forumId={routeParams.forumId}
        />);
    }

    function renderPage() {
        if(forumPosts && forumPosts.id === parseInt(routeParams.forumId)) {
            return <React.Fragment>
                {renderPosts()}
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
        <h2>{forumPosts.subject}</h2>
        <Button onClick={handleShowModal}>Create Post</Button>
        <CreatePostModal show={showModal} handleClose={handleCloseModal} forumId={routeParams.forumId}/>
        {renderPage()}
    </Container>
}