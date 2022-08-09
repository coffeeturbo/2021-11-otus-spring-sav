import React from 'react';
import {BrowserRouter, Redirect, Route, Switch} from 'react-router-dom';
import Header from '../components/Header';
import AddAuthor from '../components/author/AddAuthor';
import AuthorsList from '../components/author/AuthorsList';
import EditAuthor from "../components/author/EditAuthor";

const AppRouter = () => {
    return (
        <BrowserRouter>
            <div>
                <Header />
                <div className="main-content">
                    <Switch>
                        <Route component={AuthorsList} path="/" exact={true} />
                        <Route component={AddAuthor} path="/add" />
                        <Route component={EditAuthor} path="/edit/:id" />
                        <Route component={() => <Redirect to="/" />} />
                    </Switch>
                </div>
            </div>
        </BrowserRouter>
    );
};

export default AppRouter;