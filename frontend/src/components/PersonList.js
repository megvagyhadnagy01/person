import React, { useEffect, useState } from 'react';
import PersonTable from './PersonTable';

function PersonList({ onSelect, onDelete }) {
    const [persons, setPersons] = useState([]);

    useEffect(() => {
        fetch('http://localhost:8082/api/persons')
            .then(response => response.json())
            .then(data => setPersons(data))
            .catch(error => console.error('Error:', error));
    }, []);

    return (
        <div>
            <h2>Person List</h2>
            <PersonTable persons={persons} onSelect={onSelect} onDelete={onDelete} />
        </div>
    );
}

export default PersonList;
