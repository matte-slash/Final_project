USE matteo;

CREATE TABLE room (
  id LONG NOT NULL,
  name VARCHAR(50) NOT NULL,
  address VARCHAR(50) NOT NULL,
  total_desk INT NOT NULL,
    PRIMARY KEY (id)
);
