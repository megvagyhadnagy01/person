import React, { useEffect, useState } from 'react';
import PersonTable from './PersonTable';

function PersonList({ onSelect, onDelete }) {
    const [persons, setPersons] = useState([]);
    const [page, setPage] = useState(0);
    const [size, setSize] = useState(10);
    const [totalPages, setTotalPages] = useState(0);

    useEffect(() => {
        fetch(`http://localhost:8082/api/persons?page=${page}&size=${size}`)
            .then(response => response.json())
            .then(data => {
                setPersons(data.content);
                setTotalPages(data.totalPages);
            })
            .catch(error => console.error('Error:', error));
    }, [page, size]);

    const handleNextPage = () => {
        if (page < totalPages - 1) {
            setPage(page + 1);
        }
    };

    const handlePreviousPage = () => {
        if (page > 0) {
            setPage(page - 1);
        }
    };

    return (
        <div>
            <h2>Person List</h2>
            <PersonTable persons={persons} onSelect={onSelect} onDelete={onDelete} />
            <div>
                <button onClick={handlePreviousPage} disabled={page === 0}>Previous</button>
                <span>Page {page + 1} of {totalPages}</span>
                <button onClick={handleNextPage} disabled={page === totalPages - 1}>Next</button>
            </div>
        </div>
    );
}

export default PersonList;
