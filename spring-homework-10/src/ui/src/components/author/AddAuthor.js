import React, {useState} from 'react';
import AuthorForm from './AuthorForm';

const AddAuthor = () => {

    const [putResult, setPutResult] = useState(null);

    const baseURL = "http://localhost:8080/api";

    const fortmatResponse = (res) => {
        return JSON.stringify(res, null, 2);
    }

    const  handleOnSubmit = (book) => {
        console.log(book);
        try {
            const res =  fetch(`${baseURL}/v1/authors/`, {
                method: "post",
                headers: {
                    "Content-Type": "application/json",
                    "x-access-token": "token-value",
                },
                body: JSON.stringify(book),
            });
            if (!res.ok) {
                const message = `An error has occured: ${res.status} - ${res.statusText}`;
                throw new Error(message);
            }
            console.log(res);
            const data = res.json();
            const result = {
                status: res.status + "-" + res.statusText,
                headers: { "Content-Type": res.headers.get("Content-Type") },
                data: data,
            };
            console.log(result);
            setPutResult(fortmatResponse(result));
        } catch (err) {
            setPutResult(err.message);
        }


    };

    return (
        <React.Fragment>
            <AuthorForm handleOnSubmit={handleOnSubmit} />
        </React.Fragment>
    );
};

export default AddAuthor;