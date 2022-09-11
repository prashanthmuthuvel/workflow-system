INSERT INTO role (id, role_name) VALUES
  (1, 'student'),
  (2, 'professor'),
  (3, 'chair'),
  (4, 'graduate_coordinator'),
  (5, 'human_resource'),
  (6, 'finance_officer'),
  (7, 'graduate_funding_officer');
  
INSERT INTO user (id, first_name, last_name, email_id, role_id) VALUES
  (1, 'priyadarshini', 'eswaran', 'ronallloooriya@gmail.com', 1),
  (2, 'prashanth', 'muthuvel', 'pmuthuve@lakeheadu.ca', 1),
  (3, 'zubair', 'fadlullah', 'Zubair.Fadlullah@lakeheadu.ca', 2),
  (4, 'vijay', 'mago', 'vijay@lakeheadu.ca', 2),
  (5, 'hosam', 'el-ocla', 'hosam@lakeheadu.ca', 3),
  (6, 'admin', ' ', 'admin@lakeheadu.ca', 4),
  (7, 'jinan', 'fiaidhi', 'jinan@lakeheadu.ca', 2),
  (8, 'sabah', 'mohammed', 'sabah@lakeheadu.ca', 2),
  (9, 'udhay', 'rajendran', 'udhay@lakeheadu.ca', 1),
  (10, 'john', 'christopher', 'john@lakeheadu.ca', 1);
  
INSERT INTO login (id, username, password, user_id) VALUES
  (1, 'priya@lakeheadu.ca', 'welcome', 1),
  (2, 'prashanth@lakeheadu.ca', 'welcome', 2),
  (3, 'zubai@lakeheadu.ca', 'welcome', 3),
  (4, 'vijay', 'welcome', 4),
  (5, 'hosam@lakeheadu.ca', 'welcome', 5),
  (6, 'admin@lakeheadu.ca', 'welcome', 6),
  (7, 'jinan@lakeheadu.ca', 'welcome', 7),
  (8, 'sabah@lakeheadu.ca', 'welcome', 8),
  (9, 'udhay@lakeheadu.ca', 'welcome', 9),
  (10, 'john@lakeheadu.ca', 'welcome', 10);