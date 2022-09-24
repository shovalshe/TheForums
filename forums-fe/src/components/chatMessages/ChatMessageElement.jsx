
import React from 'react';
import "./ChatMessages.css";
import {Button, Image} from "react-bootstrap";
import {FaTrash} from "react-icons/fa";
import {useDispatch} from "react-redux";
import {deleteChatMessage} from "../../redux/chat/chatSlice";

export const ChatMessageElement = ({message, me, other}) => {
    const dispatch = useDispatch();


    const getPointClass = () => {
        if(isItMe()) {
            return 'left-point';
        }
        return 'right-point';
    }

    const getDialogClass = () => {
        if(isItMe()) {
            return 'dialog-me';
        }
        return 'dialog-other';
    }

    const getSubtitleClass = () => {
        if(isItMe()) {
            return 'me-subtitle';
        }
        return 'other-subtitle';
    }

    function handleDeleteMessage() {
        dispatch(deleteChatMessage({
            messageId: message.id,
            otherId: other.user_id
        }));
    }

    function isItMe() {
        return message && message.fromUserId === me.user_id;
    }

    function showDeleteButton() {
        if(isItMe())
            return <Button variant="outline-secondary" className="delete-button" onClick={handleDeleteMessage}><FaTrash /></Button>
    }

    return <div className={getDialogClass()}>
        <div className={getSubtitleClass()}>{message.time}</div>
        <div  className={getSubtitleClass()}>{message.fromUserId === me.user_id ? me.name : other.name} Says:</div>
            {message.content}
            <div className={getPointClass()} />
        {showDeleteButton()}
    </div>
}