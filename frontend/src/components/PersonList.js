import React, { useEffect, useState } from 'react';
import PersonTable from './PersonTable';

function PersonList() {
    const [persons, setPersons] = useState([]);

    useEffect(() => {
        fetch('/api/persons')
            .then(response => response.json())
            .then(data => setPersons(data))
            .catch(error => console.error('Error:', error));
    }, []);

    return (
        <div>
            <h2>Person List</h2>
            <PersonTable persons={persons} />
        </div>
    );
}

export default PersonList;
