import React, {useState} from 'react';
import {Button, Form} from 'react-bootstrap';

const AuthorForm = (props) => {
    const [author, setAuthor] = useState({
        id: props.id ? props.author.id : '',
        firstName: props.author ? props.author.firstName : '',
        lastName: props.author ? props.author.lastName : ''
    });

    const [errorMsg, setErrorMsg] = useState('');
    const { id, firstName, lastName } = author;

    const handleOnSubmit = (event) => {
        event.preventDefault();
        const values = [firstName, lastName];
        let errorMsg = '';

        const allFieldsFilled = values.every((field) => {
            const value = `${field}`.trim();
            return value !== '' && value !== '0';
        });

        if (allFieldsFilled) {
            const author = {
                id,
                firstName,
                lastName
            };
            props.handleOnSubmit(author);
        } else {
            errorMsg = 'Please fill out all the fields.';
        }
        setErrorMsg(errorMsg);
    };

    const handleInputChange = (event) => {
        const { name, value } = event.target;

        setAuthor((prevState) => ({
            ...prevState,
            [name]: value
        }));
    };

    return (
        <div className="main-form m-5">
            {errorMsg && <p className="errorMsg">{errorMsg}</p>}
            <Form onSubmit={handleOnSubmit}>
                <Form.Group controlId="id" className="mt-2">
                    <Form.Label>Id</Form.Label>
                    <Form.Control
                        className="input-control"
                        type="text"
                        name="id"
                        value={id}
                        placeholder="Enter first name of author"
                        onChange={handleInputChange}
                    />
                </Form.Group>
                <Form.Group controlId="firstName" className="mt-2">
                    <Form.Label>First Name</Form.Label>
                    <Form.Control
                        className="input-control"
                        type="text"
                        name="firstName"
                        value={firstName}
                        placeholder="Enter first name of author"
                        onChange={handleInputChange}
                    />
                </Form.Group>
                <Form.Group controlId="lastName" className="mt-2">
                    <Form.Label>Last name</Form.Label>
                    <Form.Control
                        className="input-control"
                        type="text"
                        name="lastName"
                        value={lastName}
                        placeholder="Enter name of author"
                        onChange={handleInputChange}
                    />
                </Form.Group>

                <hr className="my-4"/>

                <Button variant="primary" type="submit" className="submit-btn">
                    Submit
                </Button>
            </Form>
        </div>
    );
};

export default AuthorForm;