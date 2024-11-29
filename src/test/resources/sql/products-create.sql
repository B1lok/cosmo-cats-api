-- Insert testing data for the 'product' table
INSERT INTO product (name, description, price, stock_quantity, category_id, wearer)
VALUES
    ('Cat Star Toy', 'A fun toy for cats to play with', 9.99, 100, 1, 0),
    ('Kitty Star Bed', 'A cozy bed for kittens to sleep in', 29.99, 50, 2, 1),
    ('Cat Star Scratcher', 'Durable scratching post for cats', 19.99, 75, 1, 0),
    ('Kitty Star Blanket', 'Soft blanket for kittens', 15.99, 60, 2, 1),
    ('Cat Star Food', 'Healthy food for adult cats', 25.49, 200, 3, 0),
    ('Kitty Star Treats', 'Delicious treats for kittens', 7.99, 150, 3, 1);
