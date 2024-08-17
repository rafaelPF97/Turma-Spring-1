-- Criação do usuário 'hbstudent' com host '%'
CREATE USER 'hbstudent'@'%' IDENTIFIED BY 'hbstudent';

-- Garantindo todos os privilégios ao usuário 'hbstudent'
GRANT ALL PRIVILEGES ON *.* TO 'hbstudent'@'%';

FLUSH PRIVILEGES;