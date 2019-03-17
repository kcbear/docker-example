insert into account (balance_date, currency, name, number, opening_available_balance, type, id) values ('2019-02-01', 'SGD', 'SG1Saving', '1001', '100', '0', '101');
insert into account (balance_date, currency, name, number, opening_available_balance, type, id) values ('2019-02-02', 'AUD', 'AU1Saving', '1002', '200', '0', '102');
insert into account (balance_date, currency, name, number, opening_available_balance, type, id) values ('2019-02-03', 'SGD', 'SG2Saving', '1003', '300', '0', '103');
insert into account (balance_date, currency, name, number, opening_available_balance, type, id) values ('2019-03-01', 'SGD', 'SG3Saving', '1004', '400', '0', '104');
insert into account (balance_date, currency, name, number, opening_available_balance, type, id) values ('2019-04-01', 'SGD', 'SG4Current', '1005', '500', '1', '105');

insert into transaction (account_id, amount, currency, narrative, type, value_date, id) values ('101', '100', 'SGD', 'account opening', '1', '2019-02-01', '101');
insert into transaction (account_id, amount, currency, narrative, type, value_date, id) values ('102', '200', 'AUD', 'account opening', '1', '2019-02-01', '102');
insert into transaction (account_id, amount, currency, narrative, type, value_date, id) values ('103', '300', 'SGD', 'account opening', '1', '2019-02-01', '103');
insert into transaction (account_id, amount, currency, narrative, type, value_date, id) values ('104', '400', 'SGD', 'account opening', '1', '2019-02-01', '104');
insert into transaction (account_id, amount, currency, narrative, type, value_date, id) values ('105', '500', 'SGD', 'account opening', '1', '2019-02-01', '105');

insert into transaction (account_id, amount, currency, narrative, type, value_date, id) values ('101', '50', 'SGD', 'bill expense', '0', '2019-02-01', '106');
insert into transaction (account_id, amount, currency, narrative, type, value_date, id) values ('101', '50', 'SGD', 'deposit at counter', '1', '2019-02-01', '107');
insert into transaction (account_id, amount, currency, narrative, type, value_date, id) values ('101', '10', 'SGD', 'food', '0', '2019-02-01', '108');
insert into transaction (account_id, amount, currency, narrative, type, value_date, id) values ('101', '10', 'SGD', 'deposit at ATM', '1', '2019-02-01', '109');