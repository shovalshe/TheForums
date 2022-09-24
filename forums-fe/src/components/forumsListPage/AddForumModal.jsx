import React from 'react';
import {Button, Form, Modal} from "react-bootstrap";
import {useDispatch} from "react-redux";
import {createForum} from "../../redux/forums/forumsSlice";

export const AddForumModal = ({show, handleClose}) => {
    const dispatch = useDispatch();

    const handleSubmit = event => {
        event.preventDefault();
        dispatch(createForum(event.target[0].value));
        handleClose();
    }

    return (
        <>
            <Modal show={show} onHide={handleClose}>
                <Modal.Header closeButton>
                    <Modal.Title>Add forum</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <Form onSubmit={handleSubmit}>
                        <Form.Group className="mb-3" controlId="form.forumName">
                            <Form.Label>Subject</Form.Label>
                            <Form.Control
                                type="text"
                                placeholder="Forum subject"
                                required
                            />
                        </Form.Group>
                        <Button variant="primary" type="submit">
                            Create Forum
                        </Button>
                    </Form>
                </Modal.Body>
            </Modal>
        </>
    );
}
