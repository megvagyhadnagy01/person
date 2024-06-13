import React, { useState } from 'react';
import PersonForm from './components/PersonForm';
import PersonList from './components/PersonList';
import './App.css';

function App() {
    const [persons, setPersons] = useState([]); // Ensure this is an empty array
    const [selectedPerson, setSelectedPerson] = useState(null);

    const handleSelectPerson = (person) => {
        setSelectedPerson(person);
    };

    const handleUpdate = (updatedPerson) => {
        setPersons((prevPersons) =>
            prevPersons.map((person) =>
                person.id === updatedPerson.id ? updatedPerson : person
            )
        );
        setSelectedPerson(null);
    };
    const handleDelete = (personId) => {
        fetch(`http://localhost:8082/api/persons/${personId}`, {
            method: 'DELETE',
            credentials: 'include'
        })
            .then(response => {
                if (!response.ok) {
                    return response.json().then(error => {
                        console.error('Error:', error);
                        throw new Error('Network response was not ok ' + response.statusText);
                    });
                }
                setPersons((prevPersons) => prevPersons.filter((person) => person.id !== personId));
                setSelectedPerson(null);
            })
            .catch((error) => {
                console.error('Error:', error);
            });
    };


    return (
        <div className="App">
            <PersonForm selectedPerson={selectedPerson} onUpdate={handleUpdate} onDelete={handleDelete} />
            <PersonList onSelect={handleSelectPerson} onDelete={handleDelete} />
        </div>
    );
}

export default App;
