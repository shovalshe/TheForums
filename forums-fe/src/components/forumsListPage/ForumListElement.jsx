import React from 'react';
import {Button, Card} from "react-bootstrap";
import {useNavigate} from "react-router-dom";
import {useDispatch, useSelector} from "react-redux";
import {deleteForum} from "../../redux/forums/forumsSlice";
import { FaTrash } from 'react-icons/fa';

export const ForumListElement = ({forum}) => {
    const navigate = useNavigate();
    const isAdmin = useSelector(state => state.auth.isAdmin);
    const dispatch = useDispatch();

    const navigateToForum = () => {
        navigate(`/forum/${forum.id}`);
    }

    function handleDeleteForum(event) {
        event.stopPropagation();
        dispatch(deleteForum(forum.id))
    }

    function renderDeleteButton() {
        if(isAdmin) {
            return <Button variant="outline-secondary" className="delete-button" onClick={handleDeleteForum} disabled={!isAdmin}><FaTrash /></Button>
        }
    }

    return <Card className='list-item-card' onClick={navigateToForum}>
        <Card.Body>
            {forum.subject}
            {renderDeleteButton()}
        </Card.Body>
    </Card>
}