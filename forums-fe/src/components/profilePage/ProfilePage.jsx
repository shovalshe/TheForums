import React from 'react';
import {useSelector} from "react-redux";
import {Card} from "react-bootstrap";
import CardHeader from "react-bootstrap/CardHeader";
import './ProfilePage.css';

export const ProfilePage = () => {
    const user = useSelector(state => state.auth.user);
    const isAdmin = useSelector(state => state.auth.isAdmin);
    return <Card id='profile-card'>
        <CardHeader>
            Profile
        </CardHeader>
        <Card.Body>
            <div>Full name: {user.name}</div>
            <div>Email: {user.email}</div>
            <div>Administrator privileges: {isAdmin ? "Yes" : "No"}</div>
        </Card.Body>
    </Card>
}