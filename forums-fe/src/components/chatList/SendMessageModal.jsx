import React, {useEffect, useState} from 'react';
import {Button, Form, Modal} from "react-bootstrap";
import {useSelector} from "react-redux";

export const SendMessageModal = ({show, handleClose}) => {
    const [other, setOther] = useState(null);
    const [recipients, setRecipients] = useState([]);
    const users = useSelector(state => state.users.userList);
    const me = useSelector(state => state.auth.user);
    useEffect(() => {
        setRecipients(users.filter(u => u.user_id !== me.sub));
    }, [users, me, other]);

    useEffect(() => {
        if(recipients.length > 0 && !other) {
            setOther(recipients[0].name);
        }
    }, [recipients]);

    function handleSubmit(event) {
        event.preventDefault();
        const message = {
            user: users.find(u => u.name === other),
            content: event.target[1].value,
            other: other
        }
        handleClose(message);
    }

    return (
        <>
            <Modal show={show} onHide={handleClose}>
                <Modal.Header closeButton>
                    <Modal.Title>Send a New Message</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <Form onSubmit={handleSubmit}>
                        <Form.Group className="mb-3" controlId="user-select">
                            <Form.Label>To</Form.Label>
                            <Form.Control
                                as="select"
                                value={other}
                                onChange={e => {
                                    setOther(e.target.value);
                                }}
                            >
                                {recipients.map(r => <option key={r.user_id} value={r.name}>{r.name}</option>)}
                            </Form.Control>
                        </Form.Group>
                        <Form.Group className="mb-3" controlId="post.content">
                            <Form.Label>Content</Form.Label>
                            <Form.Control as="textarea" rows={5} required />
                        </Form.Group>
                        <Button variant="primary" type="submit">
                            Send Message
                        </Button>
                     </Form>
                </Modal.Body>
            </Modal>
        </>
    );
}
