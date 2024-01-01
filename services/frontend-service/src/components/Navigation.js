// Navigation.js
import React from 'react';
import { Link } from 'react-router-dom';

const Navigation = () => {
  return (
    <nav className="bg-gray-800 p-4">
    <div className="container mx-auto flex justify-between items-center">
      <Link to="/" className="text-white text-2xl font-bold">Cloud Instance Manager</Link>
      <div className="space-x-4">
        <Link to="/instance" className="text-white">Instances</Link>
        <Link to="/lease" className="text-white">Leases</Link>
      </div>
    </div>
  </nav>
  );
};

export default Navigation;
