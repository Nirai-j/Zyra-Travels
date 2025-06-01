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

-- Insert sample destinations
INSERT INTO destinations (name, description, country, region, city, image_url, featured_image_url, is_featured, is_active)
VALUES ('Bali', 'Beautiful island known for its forested volcanic mountains, iconic rice paddies, beaches and coral reefs', 'Indonesia', 'Southeast Asia', 'Denpasar', 'https://example.com/images/bali.jpg', 'https://example.com/images/bali-featured.jpg', true, true)
ON CONFLICT (name) DO NOTHING;

INSERT INTO destinations (name, description, country, region, city, image_url, featured_image_url, is_featured, is_active)
VALUES ('Paris', 'City of lights known for its cafe culture and iconic landmarks', 'France', 'Europe', 'Paris', 'https://example.com/images/paris.jpg', 'https://example.com/images/paris-featured.jpg', true, true)
ON CONFLICT (name) DO NOTHING;

INSERT INTO destinations (name, description, country, region, city, image_url, featured_image_url, is_featured, is_active)
VALUES ('Santorini', 'Stunning island known for its whitewashed, cubiform houses', 'Greece', 'Europe', 'Thira', 'https://example.com/images/santorini.jpg', 'https://example.com/images/santorini-featured.jpg', true, true)
ON CONFLICT (name) DO NOTHING;

-- Insert sample travel packages
INSERT INTO travel_packages (name, description, price, duration, thumbnail_url, banner_image_url, group_size_min, group_size_max, is_featured, is_active)
VALUES ('Bali Adventure', 'Experience the beauty of Bali with this 7-day adventure package', 1299.99, 7, 'https://example.com/images/bali-thumb.jpg', 'https://example.com/images/bali-banner.jpg', 4, 12, true, true)
ON CONFLICT (name) DO NOTHING;

INSERT INTO travel_packages (name, description, price, duration, thumbnail_url, banner_image_url, group_size_min, group_size_max, is_featured, is_active)
VALUES ('Paris Getaway', 'Romantic 5-day trip to the city of lights', 1499.99, 5, 'https://example.com/images/paris-thumb.jpg', 'https://example.com/images/paris-banner.jpg', 2, 8, true, true)
ON CONFLICT (name) DO NOTHING;

INSERT INTO travel_packages (name, description, price, duration, thumbnail_url, banner_image_url, group_size_min, group_size_max, is_featured, is_active)
VALUES ('Greek Islands Explorer', 'Explore the beautiful Greek islands including Santorini and Mykonos', 2499.99, 10, 'https://example.com/images/greek-thumb.jpg', 'https://example.com/images/greek-banner.jpg', 6, 15, true, true)
ON CONFLICT (name) DO NOTHING;

-- Link packages to destinations
INSERT INTO package_destinations (package_id, destination_id)
SELECT p.id, d.id FROM travel_packages p, destinations d
WHERE p.name = 'Bali Adventure' AND d.name = 'Bali'
ON CONFLICT ON CONSTRAINT package_destinations_pkey DO NOTHING;

INSERT INTO package_destinations (package_id, destination_id)
SELECT p.id, d.id FROM travel_packages p, destinations d
WHERE p.name = 'Paris Getaway' AND d.name = 'Paris'
ON CONFLICT ON CONSTRAINT package_destinations_pkey DO NOTHING;

INSERT INTO package_destinations (package_id, destination_id)
SELECT p.id, d.id FROM travel_packages p, destinations d
WHERE p.name = 'Greek Islands Explorer' AND d.name = 'Santorini'
ON CONFLICT ON CONSTRAINT package_destinations_pkey DO NOTHING;

-- Add package availabilities
INSERT INTO package_availabilities (travel_package_id, start_date, end_date, capacity, status)
SELECT p.id, '2025-07-15', '2025-07-22', 12, 'AVAILABLE'
FROM travel_packages p WHERE p.name = 'Bali Adventure'
ON CONFLICT DO NOTHING;

INSERT INTO package_availabilities (travel_package_id, start_date, end_date, capacity, status)
SELECT p.id, '2025-08-10', '2025-08-15', 8, 'AVAILABLE'
FROM travel_packages p WHERE p.name = 'Paris Getaway'
ON CONFLICT DO NOTHING;

INSERT INTO package_availabilities (travel_package_id, start_date, end_date, capacity, status)
SELECT p.id, '2025-09-05', '2025-09-15', 15, 'AVAILABLE'
FROM travel_packages p WHERE p.name = 'Greek Islands Explorer'
ON CONFLICT DO NOTHING; 