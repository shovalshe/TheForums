import React from 'react';
import {Button, Form, Modal} from "react-bootstrap";
import {useDispatch} from "react-redux";
import {createPost, createPostComment} from "../../redux/posts/postsSlice";

export const CreateCommentModal = ({show, handleClose, forumId, postId}) => {
    const dispatch = useDispatch();

    function handleSubmit(event) {
        event.preventDefault();
        const comment = {
            forumId: forumId,
            postId: postId,
            content: event.target[0].value,
        }
        dispatch(createPostComment(comment));
        handleClose();
    }

    return (
        <>
            <Modal show={show} onHide={handleClose}>
                <Modal.Header closeButton>
                    <Modal.Title>Add comment</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <Form onSubmit={handleSubmit}>
                        <Form.Group className="mb-3" controlId="comment.content">
                            <Form.Label>Content</Form.Label>
                            <Form.Control as="textarea" rows={5} required />
                        </Form.Group>
                        <Button variant="primary" type="submit">
                            Add Comment
                        </Button>
                     </Form>
                </Modal.Body>
            </Modal>
        </>
    );
}
