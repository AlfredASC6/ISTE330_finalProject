-- Fennell, Kayla 
-- Group 2 
-- ISTE 330
-- 4/9/23

SET SESSION sql_mode = "REAL_AS_FLOAT,PIPES_AS_CONCAT,IGNORE_SPACE,ONLY_FULL_GROUP_BY,TRADITIONAL";

DROP DATABASE IF EXISTS researchInterests;

CREATE DATABASE researchInterests;
USE researchInterests;

CREATE TABLE person(
	username VARCHAR(15) NOT NULL PRIMARY KEY, 
    password VARCHAR(30) NOT NULL, 
    ID INT NOT NULL UNIQUE,
    discriminator VARCHAR(1)
)ENGINE=InnoDB DEFAULT CHARSET= utf8;

CREATE TABLE department(
	departmentID INT NOT NULL PRIMARY KEY,
    department VARCHAR(50)
)ENGINE=InnoDB DEFAULT CHARSET= utf8;

CREATE TABLE building(
	buildingCode VARCHAR(3) NOT NULL PRIMARY KEY,
    buildingName VARCHAR(50)
)ENGINE=InnoDB DEFAULT CHARSET= utf8;

CREATE TABLE faculty(
	facultyID INT NOT NULL PRIMARY KEY,
    fName VARCHAR(30),
    lName VARCHAR(30),
    email VARCHAR(30),
    phoneNum VARCHAR(10),
    officePhoneNum VARCHAR(10),
    officeNum INT(4),
    buildingCode VARCHAR(3),
    departmentID INT,
    CONSTRAINT faculty_person_fk FOREIGN KEY (facultyID) REFERENCES person(ID),
    CONSTRAINT faculty_department_fk FOREIGN KEY (departmentID) REFERENCES department(departmentID),
    CONSTRAINT faculty_building_fk FOREIGN KEY (buildingCode) REFERENCES building(buildingCode)
)ENGINE=InnoDB DEFAULT CHARSET= utf8;

CREATE TABLE abstract(
	abstractID INT NOT NULL PRIMARY KEY,
    abstract MEDIUMTEXT
)ENGINE=InnoDB DEFAULT CHARSET= utf8;

CREATE TABLE faculty_abstract(
	facultyID INT NOT NULL,
    abstractID INT NOT NULL,
    PRIMARY KEY (facultyID, abstractID),
    CONSTRAINT faculty_abstract_faculty_fk FOREIGN KEY (facultyID) REFERENCES faculty(facultyID),
    CONSTRAINT faculty_abstract_abstract_fk FOREIGN KEY (abstractID) REFERENCES abstract(abstractID)
)ENGINE=InnoDB DEFAULT CHARSET= utf8;

CREATE TABLE faculty_keytopics(
	facultyID INT NOT NULL,
    keytopicID INT AUTO_INCREMENT,
    keytopic VARCHAR(50),
    PRIMARY KEY (facultyID, keytopicID),
    UNIQUE (keytopicID),
    CONSTRAINT faculty_keytopics_faculty FOREIGN KEY (facultyID) REFERENCES faculty(facultyID)
)ENGINE=InnoDB DEFAULT CHARSET= utf8;

CREATE TABLE student(
	studentID INT NOT NULL PRIMARY KEY,
    fName VARCHAR(30),
    lName VARCHAR(30),
    email VARCHAR(30),
    phoneNum VARCHAR(10),
    departmentID INT,
    CONSTRAINT student_person_fk FOREIGN KEY (studentID) REFERENCES person(ID),
    CONSTRAINT student_department_fk FOREIGN KEY (departmentID) REFERENCES department(departmentID)
)ENGINE=InnoDB DEFAULT CHARSET= utf8;

CREATE TABLE student_keytopics(
	studentID INT NOT NULL,
    keytopicID INT NOT NULL AUTO_INCREMENT,
    keytopic VARCHAR(50),
    PRIMARY KEY (studentID, keytopicID),
    UNIQUE (keytopicID),
    CONSTRAINT student_keytopics_student FOREIGN KEY (studentID) REFERENCES student(studentID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE guest(
	guestID INT NOT NULL PRIMARY KEY,
    fName VARCHAR(30),
    lName VARCHAR(30),
    company VARCHAR(30),
    email VARCHAR(30),
    phoneNum VARCHAR(10),
    CONSTRAINT guest_person_fk FOREIGN KEY (guestID) REFERENCES person(ID)
)ENGINE=InnoDB DEFAULT CHARSET= utf8;

CREATE TABLE guest_keytopics(
	guestID INT NOT NULL,
    keytopicID INT NOT NULL AUTO_INCREMENT,
    keytopic VARCHAR(50),
    PRIMARY KEY (guestID, keytopicID),
    UNIQUE (keytopicID),
    CONSTRAINT guest_keytopics_guest FOREIGN KEY (guestID) REFERENCES guest(guestID)
)ENGINE=InnoDB DEFAULT CHARSET= utf8;

-- TEST DATA 
-- the first professor, student, and guest will match
-- the second professor, student, and guest will match
-- everyone has the same phone number because this is just for testing 
INSERT INTO person (username, password, ID) VALUES ("prof1", "password1", 0001);
INSERT INTO person (username, password, ID) VALUES ("prof2", "password1", 0002);
INSERT INTO person (username, password, ID) VALUES ("student1", "password1", 1001);
INSERT INTO person (username, password, ID) VALUES ("student2", "password1", 1002);
INSERT INTO person (username, password, ID) VALUES ("guest1", "password1", 2001);
INSERT INTO person (username, password, ID) VALUES ("guest2", "password1", 2002);

INSERT INTO department (departmentID, department) VALUES (1, "Computer Science");
INSERT INTO department (departmentID, department) VALUES (2, "Computer Engineering");

INSERT INTO building (buildingCode, buildingName) VALUES ("GOL", "Golisano Hall");
INSERT INTO building (buildingCode, buildingName) VALUES ("KGH", "Kate Gleason Hall");

INSERT INTO faculty (facultyID, fName, lName, email, phoneNum, officePhoneNum, officeNum, buildingCode, departmentID)
	VALUES (0001, "Joe", "Smith", "jsmith@rit.edu", "5851111111", "5851111111", 111, "GOL", 1);
INSERT INTO faculty (facultyID, fName, lName, email, phoneNum, officePhoneNum, officeNum, buildingCode, departmentID)
	VALUES (0002, "Jane", "Brown", "janebrown@rit.edu", "5851111111", "5851111111", 222, "KGH", 2);
    
INSERT INTO abstract (abstractID, abstract) VALUES (1, "Test abstract 1");
INSERT INTO abstract (abstractID, abstract) VALUES (2, "Test abstract 2");

INSERT INTO faculty_abstract (facultyID, abstractID) VALUES (0001, 1);
INSERT INTO faculty_abstract (facultyID, abstractID) VALUES (0002, 2);

INSERT INTO faculty_keytopics (facultyID, keytopicID, keytopic) VALUES (0001, 1, "Java Applications");
INSERT INTO faculty_keytopics (facultyID, keytopicID, keytopic) VALUES (0002, 2, "Computer Architecture");

INSERT INTO student (studentID, fName, lName, email, phoneNum, departmentID)
	VALUES (1001, "Olivia", "Richardson", "oliviarich@rit.edu", "5851111111", "1");
INSERT INTO student (studentID, fName, lName, email, phoneNum, departmentID)
	VALUES (1002, "Zack", "Green", "zgreenh@rit.edu", "5851111111", "2");
    
INSERT INTO student_keytopics (studentID, keytopicID, keytopic) VALUES (1001, 1, "Java Applications");
INSERT INTO student_keytopics (studentID, keytopicID, keytopic) VALUES (1002, 2, "Computer Architecture");

INSERT INTO guest (guestID, fName, lName, company, email, phoneNum)
	VALUES (2001, "Tom", "Tompson", "Oracle", "tom@gmail.com", "5851111111");
INSERT INTO guest (guestID, fName, lName, company, email, phoneNum)
	VALUES (2002, "Rachel", "Johnson", "L3Harris", "rjohnson@gmail.com", "5851111111");
    
INSERT INTO guest_keytopics (guestID, keytopicID, keytopic) VALUES (2001, 1, "Java Applications");
INSERT INTO guest_keytopics (guestID, keytopicID, keytopic) VALUES (2002, 2, "Computer Architecture");

-- Stored Procedure for add and updating keytopics
-- add_faculty_keytopic needs:
-- faculty ID, keytopic ID, and keytopic

DROP PROCEDURE IF EXISTS add_faculty_keytopic;
DELIMITER //
CREATE PROCEDURE add_faculty_keytopic(
IN varfacultyID INT, 
IN varkeytopicID INT, 
IN varkeytopic VARCHAR(255)
)
BEGIN
    DECLARE existingID INT;

    SELECT facultyID INTO existingID
    FROM faculty_keytopics
    WHERE keytopicID = varkeytopicID AND facultyID = varfacultyID;

  IF existingID IS NULL THEN
    INSERT INTO faculty_keytopics (facultyID, keytopicID, keytopic)
    VALUES (varfacultyID, varkeytopicID, varkeytopic);
  ELSE
    UPDATE faculty_keytopics
    SET keytopic = varkeytopic
    WHERE facultyID = varfacultyID AND keytopicID = varkeytopicID;
  END IF;
END //
DELIMITER ;

-- Stored Procedure for deleting keytopics
-- delete_faculty_keytopic needs:
-- faculty ID and keytopic ID

DROP PROCEDURE IF EXISTS delete_faculty_keytopic;
DELIMITER //
CREATE PROCEDURE delete_faculty_keytopic(
IN varkeytopicID INT,
IN varfacultyID INT
)
BEGIN
    DELETE FROM faculty_keytopics
    WHERE keytopicID = varkeytopicID AND facultyID = varfacultyID;
END //
DELIMITER ;

-- Stored Procedure for searching for keytopics
-- KeytopicID needs:
-- Keytopic ID

DROP PROCEDURE IF EXISTS match_keytopic;
DELIMITER //
CREATE PROCEDURE match_keytopic(
IN varKeytopicID INT
)
BEGIN
    SELECT 
        faculty_keytopics.keytopicID AS Faculty_TopicID, faculty_keytopics.keytopic AS Faculty_Topic,
        student_keytopics.keytopicID AS Student_TopicID, student_keytopics.keytopic AS Student_Topic,
        guest_keytopics.keytopicID AS Guest_TopicID, guest_keytopics.keytopic AS Guest_Topic
    FROM 
        faculty_keytopics
    INNER JOIN 
        student_keytopics
    ON 
        faculty_keytopics.keytopicID = student_keytopics.keytopicID
    INNER JOIN
        guest_keytopics
    ON
        faculty_keytopics.keytopicID = guest_keytopics.keytopicID
    WHERE
        faculty_keytopics.keytopicID = varKeytopicID;
END //
DELIMITER ;

SHOW PROCEDURE STATUS WHERE db Like "research%"; 