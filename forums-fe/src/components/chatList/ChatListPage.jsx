import React, {useEffect, useState} from "react";
import {Button, ListGroup} from "react-bootstrap";
import {useDispatch, useSelector} from "react-redux";
import Container from "react-bootstrap/Container";
import {fetchMyChats, postChatMessage} from "../../redux/chat/chatSlice";
import {ChatListElement} from "./ChatListElement";
import {BsChatDots} from "react-icons/bs";
import {SendMessageModal} from "./SendMessageModal";

export const ChatsListPage = () => {
    const dispatch = useDispatch();
    const chats = useSelector(state => state.chat.chatList);
    const isAuthenticated = useSelector(state => state.auth.isAuthenticated);
    const [showModal, setShowModal] = useState(false);


    useEffect(() => {
        if(isAuthenticated)
            dispatch(fetchMyChats());
    },[isAuthenticated]);

    function getChatListElements() {
        return chats.map(chat => (<ChatListElement
            chat = {chat}
            key={chat.id}
        />))
    }

    const handleShowModal = () => {
        setShowModal(true);
    }

    const handleCloseModal = (message) => {
        setShowModal(false);
        console.log(message)
        if(message && message.user && message.content) {
            dispatch(postChatMessage({
                userId: message.user.user_id,
                content: message.content
                }));
        }
    }

    return (
        <Container className="page-container">
            <Button onClick={handleShowModal}><BsChatDots /> Send Message</Button>
            <SendMessageModal show={showModal} handleClose={handleCloseModal}/>
            <ListGroup>
                {getChatListElements()}
            </ListGroup>
        </Container>
    );
};