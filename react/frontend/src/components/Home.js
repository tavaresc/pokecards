/**
 * Created by claudia on 20/07/17.
 * Home builds the structure of the initial homepage.
 */
import React from 'react';
import Header from './Header';
import Navbar from './Navbar';
import Main from './Main';
import About from './About';
import Footer from './Footer';

const Home = () => (
    <div>
        <Navbar />
        <Header />
        <Main />
        <About />
        <Footer />
    </div>
);

export default Home;