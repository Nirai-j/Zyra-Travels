-- Insert default roles
INSERT INTO roles (name) VALUES ('ROLE_USER') 
ON CONFLICT ON CONSTRAINT roles_name_key DO NOTHING;

INSERT INTO roles (name) VALUES ('ROLE_ADMIN') 
ON CONFLICT ON CONSTRAINT roles_name_key DO NOTHING;

-- Insert default payment methods
INSERT INTO payment_methods (name, description, active) 
VALUES ('Credit Card', 'Payment using credit card', true) 
ON CONFLICT ON CONSTRAINT payment_methods_name_key DO NOTHING;

INSERT INTO payment_methods (name, description, active) 
VALUES ('Debit Card', 'Payment using debit card', true) 
ON CONFLICT ON CONSTRAINT payment_methods_name_key DO NOTHING;

INSERT INTO payment_methods (name, description, active) 
VALUES ('Bank Transfer', 'Direct bank transfer payment', true) 
ON CONFLICT ON CONSTRAINT payment_methods_name_key DO NOTHING; 