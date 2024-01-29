import React from 'react';
import { Route, BrowserRouter as Router, Routes } from 'react-router-dom';
import './App.css';
import Lease from './components/Lease';
import Navigation from './components/Navigation';

function App() {
  return (
    <Router>
    <div>
      <Navigation />
      <Routes>
        {/* <Route path="/instance" element={<Instance />} /> */}
        <Route path="/lease" element={<Lease />} />
      </Routes>
    </div>
  </Router>
  );
}

export default App;
