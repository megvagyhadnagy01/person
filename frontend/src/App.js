import React, { useState } from 'react';
import PersonForm from './components/PersonForm';
import PersonList from './components/PersonList';

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
        setPersons((prevPersons) =>
            prevPersons.filter((person) => person.id !== personId)
        );
        setSelectedPerson(null);
    };

    return (
        <div className="App">
            <PersonForm selectedPerson={selectedPerson} onUpdate={handleUpdate} onDelete={handleDelete} />
            <PersonList onSelect={handleSelectPerson} onDelete={handleDelete} />
        </div>
    );
}

export default App;
