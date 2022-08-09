import React, {useEffect, useState} from 'react';
import Author from "./Author";

const AuthorsList = () => {
    const [error, setError] = useState(null);
    const [isLoaded, setIsLoaded] = useState(false);
    const [users, setUsers] = useState([]);

    useEffect(() => {

        fetch("http://localhost:8080/api/v1/authors")
            .then(res => res.json())
            .then(
                (data) => {
                    setIsLoaded(true);
                    setUsers(data);
                },
                (error) => {
                    setIsLoaded(true);
                    setError(error);
                }
            )
    }, [])
    if (error) {
        return <div>Error: {error.message}</div>;
    } else if (!isLoaded) {
        return <div>Loading...</div>;
    } else {
        return (
            <>
                <h2>List of books</h2>
                <ul>
                    {users.map((author) => (
                        <li key={author.id}>
                            <Author  {...author} />
                        </li>
                    ))}
                </ul>
            </>
        );
    }
};

export default AuthorsList;