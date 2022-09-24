import React from 'react';
import {Button, Form, Modal} from "react-bootstrap";
import {useDispatch} from "react-redux";
import {createPost} from "../../redux/posts/postsSlice";

export const CreatePostModal = ({show, handleClose, forumId}) => {
    const dispatch = useDispatch();

    function handleSubmit(event) {
        event.preventDefault();
        const post = {
            forumId: forumId,
            title: event.target[0].value,
            content: event.target[1].value
        }
        dispatch(createPost(post));
        handleClose();
    }

    return (
        <>
            <Modal show={show} onHide={handleClose}>
                <Modal.Header closeButton>
                    <Modal.Title>Create Post</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <Form onSubmit={handleSubmit}>
                        <Form.Group className="mb-3" controlId="post.title">
                            <Form.Label>Title</Form.Label>
                            <Form.Control type="text" placeholder="Enter the post's title" required/>
                        </Form.Group>
                        <Form.Group className="mb-3" controlId="post.content">
                            <Form.Label>Content</Form.Label>
                            <Form.Control as="textarea" rows={5} required />
                        </Form.Group>
                        <Button variant="primary" type="submit">
                            Create Post
                        </Button>
                     </Form>
                </Modal.Body>
            </Modal>
        </>
    );
}
