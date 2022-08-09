import React from 'react';
import {NavLink} from 'react-router-dom';

const Header = () => {
    return (
        <header>
            <h1>Book Lib </h1>
            <hr />
            <div className="navbar navbar-dark bg-dark">
                <ul className="navbar-nav me-auto mb-2 mb-lg-0">
                    <li className="nav-item">
                        <NavLink to="/" className="nav-link" activeClassName="active" exact>Books List</NavLink>
                    </li>
                    <li className="nav-item">
                        <NavLink to="/add" className="nav-link" activeClassName="active">Add Book</NavLink>
                    </li>
                </ul>
            </div>
        </header>
    );
};

export default Header;