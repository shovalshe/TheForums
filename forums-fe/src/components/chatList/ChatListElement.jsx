import React, {useEffect, useState} from 'react';
import {Card} from "react-bootstrap";
import '../../App.css';
import {useNavigate} from "react-router-dom";
import {useSelector} from "react-redux";

export const ChatListElement = ({chat}) => {
    const navigate = useNavigate();
    const user = useSelector(state => state.auth.user);
    const [other, setOther] = useState("");
    const users = useSelector(state => state.users.userList);

    useEffect(() => {
        if(users.length > 0) {
            let otherParticipant;
            if(chat.participant1Id === user.sub) {
                otherParticipant = users.find(u => u.user_id === chat.participant2Id)
            } else {
                otherParticipant = users.find(u => u.user_id === chat.participant1Id)
            }
            setOther(otherParticipant);
        }
    }, [other, users])

    const navigateToChat = () => {
        navigate(`/chat/${other.user_id}`);
    }

    return <Card className='list-item-card' onClick={navigateToChat}>
        <Card.Body>
            With: {other.name}
            {/*{renderDeleteButton()}*/}
        </Card.Body>
    </Card>
}