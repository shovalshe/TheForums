import React, {useEffect, useState} from "react";
import {Button, ListGroup, OverlayTrigger, Tooltip} from "react-bootstrap";
import {useDispatch, useSelector} from "react-redux";
import {fetchForums} from "../../redux/forums/forumsSlice";
import {ForumListElement} from "./ForumListElement";
import {AddForumModal} from "./AddForumModal";
import Container from "react-bootstrap/Container";

export const ForumsListPage = () => {
    const dispatch = useDispatch();
    const forums = useSelector(state => state.forums.forumList);
    const isAuthenticated = useSelector(state => state.auth.isAuthenticated);
    const isAdmin = useSelector(state => state.auth.isAdmin);
    const [showModal, setShowModal] = useState(false);

    useEffect(() => {
        if(isAuthenticated)
            dispatch(fetchForums());
    },[isAuthenticated]);

    function getForumListComponents() {
        return forums.map(forum => (<ForumListElement
            forum = {forum}
            key={forum.id}
        />))
    }

    const handleShowModal = () => {
        setShowModal(true);
    }

    const handleCloseModal = () => {
        setShowModal(false);
    }

    const generateAddForumButton = () => {
        if(!isAdmin) {
            return <OverlayTrigger placement="bottom" overlay={<Tooltip>Only administrators can create forums!</Tooltip>}>
                <span className="d-inline-block">
                    <Button onClick={handleShowModal} disabled={!isAdmin}>Add forum</Button>
                </span>
            </OverlayTrigger>
        }
        return <Button onClick={handleShowModal} disabled={!isAdmin}>Add forum</Button>;
    }

    return (
        <Container className="page-container">
            {generateAddForumButton()}
            <AddForumModal show={showModal} handleClose={handleCloseModal}/>
            <ListGroup>
                {getForumListComponents()}
            </ListGroup>
        </Container>
    );
};