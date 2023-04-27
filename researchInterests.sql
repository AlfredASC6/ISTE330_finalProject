-- Fennell, Kayla 
-- Chen, Steven
-- Franco, Alfred
-- Conte, Jacob
-- Foley, Ben
-- Chuhi, Reg
-- Group 2 
-- ISTE 330
-- 4/14/23

SET SESSION sql_mode = "REAL_AS_FLOAT,PIPES_AS_CONCAT,IGNORE_SPACE,ONLY_FULL_GROUP_BY,TRADITIONAL";

DROP DATABASE IF EXISTS researchInterests;

CREATE DATABASE researchInterests;
USE researchInterests;

CREATE TABLE person(
	username VARCHAR(15) NOT NULL PRIMARY KEY, 
    password VARCHAR(30) NOT NULL, 
    ID INT NOT NULL UNIQUE AUTO_INCREMENT,
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
	abstractID INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(120) UNIQUE,
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
    keytopic VARCHAR(50),
    PRIMARY KEY (facultyID, keytopic),
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
    keytopic VARCHAR(50),
    PRIMARY KEY (studentID, keytopic),
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
    keytopic VARCHAR(50),
    PRIMARY KEY (guestID, keytopic),
    CONSTRAINT guest_keytopics_guest FOREIGN KEY (guestID) REFERENCES guest(guestID)
)ENGINE=InnoDB DEFAULT CHARSET= utf8;

-- TEST DATA 
-- the first professor, student, and guest will match
-- the second professor, student, and guest will match
-- everyone has the same phone number because this is just for testing 
INSERT INTO person (username, password, ID, discriminator) VALUES ("prof1", "password1", 0001, "F");
INSERT INTO person (username, password, ID, discriminator) VALUES ("prof2", "password1", 0002, "F");
INSERT INTO person (username, password, ID, discriminator) VALUES ("student1", "password1", 1001, "S");
INSERT INTO person (username, password, ID, discriminator) VALUES ("student2", "password1", 1002, "S");
INSERT INTO person (username, password, ID, discriminator) VALUES ("guest1", "password1", 2001, "G");
INSERT INTO person (username, password, ID, discriminator) VALUES ("guest2", "password1", 2002, "G");

INSERT INTO department (departmentID, department) VALUES (1, "Computer Science");
INSERT INTO department (departmentID, department) VALUES (2, "Computer Engineering");

INSERT INTO building (buildingCode, buildingName) VALUES ("GOL", "Golisano Hall");
INSERT INTO building (buildingCode, buildingName) VALUES ("KGH", "Kate Gleason Hall");

INSERT INTO faculty (facultyID, fName, lName, email, phoneNum, officePhoneNum, officeNum, buildingCode, departmentID)
	VALUES (0001, "Joe", "Smith", "jsmith@rit.edu", "5851111111", "5851111111", 111, "GOL", 1);
INSERT INTO faculty (facultyID, fName, lName, email, phoneNum, officePhoneNum, officeNum, buildingCode, departmentID)
	VALUES (0002, "Jane", "Brown", "janebrown@rit.edu", "5851111111", "5851111111", 222, "KGH", 2);
    
INSERT INTO abstract (abstractID, title, abstract) VALUES (1, "Introduction to Computing and Programming in PYTHON – A
													Multimedia Approach", "The programming language used in this book is Python. Python
													has been described as “executable pseudo-code.” I have found
													that both computer science majors and non majors can learn
													Python. Since Python is actually used for communications tasks
													(e.g., Web site Development), it’s relevant language for an in
													introductory computing course. The specific dialect of Python
													used in this book is Jython. Jython is Python. The differences
													between Python (normally implemented in C) and Jython
													(which is implemented in Java) are akin to the differences
													between any two language implementations (e.g., Microsoft vs.
													GNU C++ implementations).");
INSERT INTO abstract (abstractID, title, abstract) VALUES (2, "C through Design", "This book presents ‘standard’ C, i.e., code that compiles cleanly
													with a compiler that meets the ANSI C standard. This book has
													over 90 example programs that illustrate the topics of each
													chapters. In addition complete working programs are
													developed fully, from design to program output. This book is
													filled with Antibugging Notes (the stress traps to be avoided),
													and Quick Notes, that emphasize important points to be
													remembered.");

INSERT INTO faculty_abstract (facultyID, abstractID) VALUES (0001, 1);
INSERT INTO faculty_abstract (facultyID, abstractID) VALUES (0002, 2);

INSERT INTO faculty_keytopics (facultyID, keytopic) VALUES (0001, "Java Applications");
INSERT INTO faculty_keytopics (facultyID, keytopic) VALUES (0002, "Computer Architecture");

INSERT INTO student (studentID, fName, lName, email, phoneNum, departmentID)
	VALUES (1001, "Olivia", "Richardson", "oliviarich@rit.edu", "5851111111", "1");
INSERT INTO student (studentID, fName, lName, email, phoneNum, departmentID)
	VALUES (1002, "Zack", "Green", "zgreenh@rit.edu", "5851111111", "2");
    
INSERT INTO student_keytopics (studentID, keytopic) VALUES (1001, "Java Applications");
INSERT INTO student_keytopics (studentID, keytopic) VALUES (1002, "Computer Architecture");

INSERT INTO guest (guestID, fName, lName, company, email, phoneNum)
	VALUES (2001, "Tom", "Tompson", "Oracle", "tom@gmail.com", "5851111111");
INSERT INTO guest (guestID, fName, lName, company, email, phoneNum)
	VALUES (2002, "Rachel", "Johnson", "L3Harris", "rjohnson@gmail.com", "5851111111");
    
INSERT INTO guest_keytopics (guestID, keytopic) VALUES (2001, "Java Applications");
INSERT INTO guest_keytopics (guestID, keytopic) VALUES (2002, "Computer Architecture");

-- Stored Procedure for adding and updating keytopics
-- add_faculty_keytopic needs:
-- faculty ID and keytopic

DROP PROCEDURE IF EXISTS add_faculty_keytopic;
DELIMITER //
CREATE PROCEDURE add_faculty_keytopic(
IN varfacultyID INT,
IN varkeytopic VARCHAR(50)
)
BEGIN
    DECLARE existingKeytopic VARCHAR(50);

    SELECT keytopic INTO existingKeytopic
    FROM faculty_keytopics
    WHERE keytopic = varkeytopic AND facultyID = varfacultyID;

    IF existingKeytopic IS NULL THEN
        INSERT INTO faculty_keytopics (facultyID, keytopic)
        VALUES (varfacultyID, varkeytopic);
    END IF;
END //
DELIMITER ;

-- Stored Procedure for deleting keytopics for faculty
-- delete_faculty_keytopic needs:
-- faculty ID and keytopic

DROP PROCEDURE IF EXISTS delete_faculty_keytopic;
DELIMITER //
CREATE PROCEDURE delete_faculty_keytopic(
IN varkeytopic VARCHAR(50),
IN varfacultyID INT
)
BEGIN
    DELETE FROM faculty_keytopics
    WHERE keytopic = varkeytopic AND facultyID = varfacultyID;
END //
DELIMITER ;

-- Stored Procedure for adding keytopics for students
-- add_student_keytopic needs:
-- student ID and keytopic

DROP PROCEDURE IF EXISTS add_student_keytopic;
DELIMITER //
CREATE PROCEDURE add_student_keytopic(
IN varstudentID INT,
IN varkeytopic VARCHAR(50)
)
BEGIN
    DECLARE existingKeytopic VARCHAR(50);

    SELECT keytopic INTO existingKeytopic
    FROM student_keytopics
    WHERE keytopic = varkeytopic AND studentID = varstudentID;

    IF existingKeytopic IS NULL THEN
        INSERT INTO student_keytopics (studentID, keytopic)
        VALUES (varstudentID, varkeytopic);
    END IF;
END //
DELIMITER ;

-- Stored Procedure for deleting keytopics for students
-- delete_student_keytopic needs:
-- student ID and keytopic

DROP PROCEDURE IF EXISTS delete_student_keytopic;
DELIMITER //
CREATE PROCEDURE delete_student_keytopic(
IN varkeytopic VARCHAR(50),
IN varstudentID INT
)
BEGIN
    DELETE FROM student_keytopics
    WHERE keytopic = varkeytopic AND studentID = varstudentID;
END //
DELIMITER ;

-- Stored Procedure for adding keytopics for guests
-- add_guest_keytopic needs:
-- guest ID and keytopic

DROP PROCEDURE IF EXISTS add_guest_keytopic;
DELIMITER //
CREATE PROCEDURE add_guest_keytopic(
IN varguestID INT,
IN varkeytopic VARCHAR(50)
)
BEGIN
    DECLARE existingKeytopic VARCHAR(50);

    SELECT keytopic INTO existingKeytopic
    FROM guest_keytopics
    WHERE keytopic = varkeytopic AND guestID = varguestID;

    IF existingKeytopic IS NULL THEN
        INSERT INTO guest_keytopics (guestID, keytopic)
        VALUES (varguestID, varkeytopic);
    END IF;
END //
DELIMITER ;

-- Stored Procedure for deleting keytopics for guests
-- delete_guest_keytopic needs:
-- guest ID and keytopic

DROP PROCEDURE IF EXISTS delete_guest_keytopic;
DELIMITER //
CREATE PROCEDURE delete_guest_keytopic(
IN varkeytopic VARCHAR(50),
IN varguestID INT
)
BEGIN
    DELETE FROM guest_keytopics
    WHERE keytopic = varkeytopic AND guestID = varguestID;
END //
DELIMITER ;

-- Stored Procedure for inserting abstracts for faculty 
-- insert_abstract needs:
-- abstract's title, abstract's text, and faculty ID

DROP PROCEDURE IF EXISTS insert_abstract;
DELIMITER //
CREATE PROCEDURE insert_abstract(
	IN abstractTitle VARCHAR(120),
    IN abstractText MEDIUMTEXT,
    IN facID INT
)
BEGIN

	DECLARE id INT;
    
	INSERT INTO abstract (title, abstract) VALUES (abstractTitle, abstractText);
    
    SELECT abstractID INTO id 
		FROM abstract
        WHERE title LIKE abstractTitle;
        
	INSERT INTO faculty_abstract (facultyID, abstractID) VALUES (facID, id);
END //
DELIMITER ;

-- Stored Procedure for deleting abstracts for faculty 
-- delete_abstract needs:
-- abstract's title and faculty ID

DROP PROCEDURE IF EXISTS delete_abstract;
DELIMITER //
CREATE PROCEDURE delete_abstract(
	IN abstractTitle VARCHAR(120),
    IN facID INT
)
BEGIN

	DECLARE id INT;
    
    SELECT abstractID INTO id 
		FROM abstract
        WHERE title LIKE abstractTitle;
        
	DELETE FROM faculty_abstract 
		WHERE facultyID = facID AND abstractID = id;
	DELETE FROM abstract 
		WHERE abstractID = id;
	
END //
DELIMITER ;

-- Stored Procedure for updating abstracts for faculty 
-- update_abstract needs:
-- abstract's old title, abstract's new title, abstract's old text, and abstract's new text

DROP PROCEDURE IF EXISTS update_abstract;
DELIMITER //
CREATE PROCEDURE update_abstract(
	IN oldTitle VARCHAR(120),
    IN newTitle VARCHAR(120),
    IN oldAbstract MEDIUMTEXT,
    IN newAbstract MEDIUMTEXT
)
BEGIN

	DECLARE id INT;
    
    SELECT abstractID INTO id 
		FROM abstract
        WHERE title LIKE oldTitle;
        
	UPDATE abstract 
		SET title = newTitle, abstract = newAbstract
        WHERE title = oldTitle AND abstract = oldAbstract;
	
END //
DELIMITER ;

-- Stored Procedure for matching interests for people
-- match_keytopic needs:
-- person's ID and discriminator 

DROP PROCEDURE IF EXISTS match_keytopic;
DELIMITER //
CREATE PROCEDURE match_keytopic(
  IN varID INT,
  IN discriminator VARCHAR(1)
)
BEGIN
  IF discriminator = 'F' THEN
    SELECT 
      student_keytopics.keytopic AS Student_Topic,
      s.studentID AS Student_ID,
      s.fName AS Student_First_Name, s.lName AS Student_Last_Name,
      s.email AS Student_Email, s.phoneNum AS Student_PhoneNum,
      s.departmentID AS Student_DepartmentID
    FROM 
      faculty_keytopics
    INNER JOIN 
      student_keytopics
    ON 
      faculty_keytopics.keytopic = student_keytopics.keytopic
    INNER JOIN
      student s ON s.studentID = student_keytopics.studentID
    WHERE
      faculty_keytopics.facultyID = varID;

  ELSEIF discriminator = 'S' THEN
    SELECT 
      faculty_keytopics.keytopic AS Faculty_Topic,
      guest_keytopics.keytopic AS Guest_Topic,
      f.fName AS Faculty_First_Name, f.lName AS Faculty_Last_Name,
      f.email AS Faculty_Email, f.phoneNum AS Faculty_PhoneNum,
      f.officePhoneNum AS Faculty_OfficePhoneNum, f.officeNum AS Faculty_OfficeNum,
      f.buildingCode AS Faculty_BuildingCode, f.departmentID AS Faculty_DepartmentID,
      g.fName AS Guest_First_Name, g.lName AS Guest_Last_Name,
      g.email AS Guest_Email, g.phoneNum AS Guest_PhoneNum
    FROM 
      faculty_keytopics
    INNER JOIN 
      student_keytopics
    ON 
      faculty_keytopics.keytopic = student_keytopics.keytopic
    INNER JOIN
      faculty AS f ON f.facultyID = faculty_keytopics.facultyID
    INNER JOIN
      guest_keytopics
    ON
      faculty_keytopics.keytopic = guest_keytopics.keytopic
    INNER JOIN
      guest AS g ON g.guestID = guest_keytopics.guestID
    WHERE
      student_keytopics.studentID = varID;

  ELSEIF discriminator = 'G' THEN
    SELECT 
      faculty_keytopics.keytopic AS Faculty_Topic,
      student_keytopics.keytopic AS Student_Topic,
      f.facultyID AS Faculty_ID,
      f.fName AS Faculty_First_Name, f.lName AS Faculty_Last_Name,
      f.email AS Faculty_Email, f.phoneNum AS Faculty_PhoneNum,
      f.officePhoneNum AS Faculty_OfficePhoneNum, f.officeNum AS Faculty_OfficeNum,
      f.buildingCode AS Faculty_BuildingCode, f.departmentID AS Faculty_DepartmentID,
      s.studentID AS Student_ID,
      s.fName AS Student_First_Name, s.lName AS Student_Last_Name,
      s.email AS Student_Email, s.phoneNum AS Student_PhoneNum,
      s.departmentID AS Student_DepartmentID
    FROM 
      faculty_keytopics
    INNER JOIN 
      student_keytopics
    ON 
      faculty_keytopics.keytopic = student_keytopics.keytopic
    INNER JOIN
      faculty f ON f.facultyID = faculty_keytopics.facultyID
    INNER JOIN
      student s ON s.studentID = student_keytopics.studentID
    WHERE
      guest_keytopics.guestID = varID;
    END IF;
END //
DELIMITER ;

SHOW PROCEDURE STATUS WHERE db Like "research%"; 
