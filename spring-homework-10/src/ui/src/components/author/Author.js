import React from 'react';

const Author = ({
                id,
                firstName,
                lastName
                }) => {

    return (<>
            <a href="{id}">{id}: {firstName} - {lastName}</a>
        </>
    );
};

export default Author;