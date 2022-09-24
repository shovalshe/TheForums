import React, {useEffect, useState} from "react";
import {Button, Form, Image, ListGroup} from "react-bootstrap";
import {useDispatch, useSelector} from "react-redux";
import Container from "react-bootstrap/Container";
import {getChatMessagesWithOther, postChatMessage, resetMessageList} from "../../redux/chat/chatSlice";
import {ChatMessageElement} from "./ChatMessageElement";
import {useParams} from "react-router-dom";
import findUserById from "../../utils/findUserById";
import './ChatMessages.css';

export const ChatMessagesListPage = () => {
    const dispatch = useDispatch();
    const messageList = useSelector(state => state.chat.messageList);
    const routeParams = useParams();
    const users = useSelector(state => state.users.userList);
    const user = useSelector(state => state.auth.user);
    const [other, setOther] = useState(null);
    const [me, setMe] = useState(null);

    useEffect(() => {
        dispatch(resetMessageList());
    }, []);

    useEffect(() => {
        if(routeParams.otherId && user && users.length > 0) {
            setMe(findUserById(users, user.sub));
            setOther(findUserById(users, routeParams.otherId));
            if(other)
                dispatch(getChatMessagesWithOther(other.user_id));
        }
    }, [routeParams, me, other,  users]);

    function getChatMessageElements() {
        if(me && other && messageList && messageList.messages)
            return messageList.messages.map(m => <ChatMessageElement message={m} me={me} other={other} key={m.id}/>);
    }

    function getHeader() {
        if(other)
            return <div className='chat-header'>
                <Image src={other.picture} roundedCircle className='image'/><h2>Chat with {other.name}</h2>
            </div>
    }

    function handleSend(event) {
        event.preventDefault();
        dispatch(postChatMessage({
            userId: other.user_id,
            content: event.target[0].value
        }));
        event.target[0].value = "";
    }

    return (
        <Container className="page-container">
            {getHeader()}
            <div className='messages-flex'>
                {getChatMessageElements()}
            </div>
            <div className='send-new-message'>
                <Form onSubmit={handleSend}>
                    <Form.Group className="mb-3" controlId="chat.content">
                        <Form.Control as="input" placeholder='Type a message' required />
                    </Form.Group>
                    <Button variant="primary" type="submit">
                        Send
                    </Button>
                </Form>
            </div>
        </Container>
    );
};