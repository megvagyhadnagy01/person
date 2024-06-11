import React from 'react';

function PersonTable({ persons }) {
    return (
        <table>
            <thead>
            <tr>
                <th>Name</th>
                <th>Birth Date</th>
                <th>Birth Place</th>
                <th>Mother's Maiden Name</th>
                <th>SSN</th>
                <th>Tax ID</th>
                <th>Email</th>
                <th>Addresses</th>
                <th>Phone Numbers</th>
            </tr>
            </thead>
            <tbody>
            {persons.map(person => (
                <tr key={person.id}>
                    <td>{person.name}</td>
                    <td>{person.birthDate}</td>
                    <td>{person.birthPlace}</td>
                    <td>{person.motherMaidenName}</td>
                    <td>{person.socialSecurityNumber}</td>
                    <td>{person.taxId}</td>
                    <td>{person.email}</td>
                    <td>
                        <ul>
                            {person.addresses.map((address, index) => (
                                <li key={index}>{address.zip}, {address.city}, {address.street}, {address.houseNumber}</li>
                            ))}
                        </ul>
                    </td>
                    <td>
                        <ul>
                            {person.phoneNumbers.map((phoneNumber, index) => (
                                <li key={index}>{phoneNumber}</li>
                            ))}
                        </ul>
                    </td>
                </tr>
            ))}
            </tbody>
        </table>
    );
}

export default PersonTable;
