-- Insert sample persons
INSERT INTO persons (id, name, birth_date, birth_place, mother_maiden_name, social_security_number, tax_id, email)
VALUES (1, 'John Doe', '1980-01-01', 'New York', 'Jane Smith', '123456789', '9876543210', 'john.doe@example.com');

INSERT INTO persons (id, name, birth_date, birth_place, mother_maiden_name, social_security_number, tax_id, email)
VALUES (2, 'Jane Doe', '1990-02-02', 'Los Angeles', 'Anna Johnson', '987654321', '1234567890', 'jane.doe@example.com');

-- Insert sample addresses for persons
INSERT INTO address (id, postal_code, city, street, house_number, person_id)
VALUES (1, '10001', 'New York', '5th Avenue', '1', 1);

INSERT INTO address (id, postal_code, city, street, house_number, person_id)
VALUES (2, '90001', 'Los Angeles', 'Sunset Boulevard', '123', 2);

-- Insert sample phone numbers for persons
INSERT INTO phone_number (id, number, person_id)
VALUES (1, '555-1234', 1);

INSERT INTO phone_number (id, number, person_id)
VALUES (2, '555-5678', 2);
