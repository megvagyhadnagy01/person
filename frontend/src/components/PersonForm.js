import React, { useState } from 'react';

function PersonForm() {
    const [person, setPerson] = useState({
        name: '',
        birthDate: '',
        birthPlace: '',
        motherMaidenName: '',
        socialSecurityNumber: '',
        taxId: '',
        email: '',
        addresses: [{ zip: '', city: '', street: '', houseNumber: '' }],
        phoneNumbers: ['']
    });

    const handleChange = (e) => {
        const { name, value } = e.target;
        setPerson({
            ...person,
            [name]: value
        });
    };

    const handleAddressChange = (index, e) => {
        const { name, value } = e.target;
        const addresses = [...person.addresses];
        addresses[index][name] = value;
        setPerson({
            ...person,
            addresses
        });
    };

    const handlePhoneNumberChange = (index, e) => {
        const { value } = e.target;
        const phoneNumbers = [...person.phoneNumbers];
        phoneNumbers[index] = value;
        setPerson({
            ...person,
            phoneNumbers
        });
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        // Send person data to the backend API
        fetch('/api/persons', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(person)
        }).then(response => response.json()).then(data => {
            console.log('Success:', data);
        }).catch((error) => {
            console.error('Error:', error);
        });
    };

    const addAddress = () => {
        setPerson({
            ...person,
            addresses: [...person.addresses, { zip: '', city: '', street: '', houseNumber: '' }]
        });
    };

    const addPhoneNumber = () => {
        setPerson({
            ...person,
            phoneNumbers: [...person.phoneNumbers, '']
        });
    };

    return (
        <form onSubmit={handleSubmit}>
            <input name="name" value={person.name} onChange={handleChange} placeholder="Name" required />
            <input name="birthDate" value={person.birthDate} onChange={handleChange} type="date" placeholder="Birth Date" required />
            <input name="birthPlace" value={person.birthPlace} onChange={handleChange} placeholder="Birth Place" required />
            <input name="motherMaidenName" value={person.motherMaidenName} onChange={handleChange} placeholder="Mother's Maiden Name" required />
            <input name="socialSecurityNumber" value={person.socialSecurityNumber} onChange={handleChange} placeholder="SSN" required />
            <input name="taxId" value={person.taxId} onChange={handleChange} placeholder="Tax ID" required />
            <input name="email" value={person.email} onChange={handleChange} type="email" placeholder="Email" required />

            <h4>Addresses</h4>
            {person.addresses.map((address, index) => (
                <div key={index}>
                    <input name="zip" value={address.zip} onChange={(e) => handleAddressChange(index, e)} placeholder="ZIP" required />
                    <input name="city" value={address.city} onChange={(e) => handleAddressChange(index, e)} placeholder="City" required />
                    <input name="street" value={address.street} onChange={(e) => handleAddressChange(index, e)} placeholder="Street" required />
                    <input name="houseNumber" value={address.houseNumber} onChange={(e) => handleAddressChange(index, e)} placeholder="House Number" required />
                </div>
            ))}
            <button type="button" onClick={addAddress}>Add Address</button>

            <h4>Phone Numbers</h4>
            {person.phoneNumbers.map((phoneNumber, index) => (
                <div key={index}>
                    <input name="phoneNumber" value={phoneNumber} onChange={(e) => handlePhoneNumberChange(index, e)} placeholder="Phone Number" required />
                </div>
            ))}
            <button type="button" onClick={addPhoneNumber}>Add Phone Number</button>

            <button type="submit">Submit</button>
        </form>
    );
}

export default PersonForm;