<?php
/**
 * Creates connection to the DBB.
 * @return Connection opened.
 */
function connectDB() {
    $server = "";
    $user = "";
    $pass = "";
    $bd = "";

    $connection = new mysqli($server, $user, $pass, $bd);
    
    return $connection;
}

/**
 * Closes the open connection passed as parameter.
 * @param connection $connection Open connection to close.
 * @return Status of the operation.
 */
function disconnectDB($connection) {
    return mysqli_close($connection);
}

/**
 * Checks whether the username given as parameter exists or not in the Database.
 * Escapes the username before sending it to the Database.
 * @param String $username Name to check inside the DBB.
 * @return boolean. True if it does exist.
 */
function checkUsername($connection, $username) {
    $sql = "SELECT username
                FROM users
                WHERE username = '$username'";
    
    $result = mysqli_query($connection, $sql);
    $num_rows = mysqli_num_rows($result);
    
    return $num_rows !== 0;
}

/**
 * Takes the introduced password used by the user, adds the salt and encrypts it. PHP does manage the salt and 
 *  encryption on it's own so it's not needed to save the salt appart in the Database.
 * @param String $password plain text password. Direct input from the user.
 * @return String Salted and encripted password, ready to be saved into the Database.
 */
function password_secure($password) {
    $options = [
        'cost => 11',
    ];      
    return password_hash($password, PASSWORD_BCRYPT, $options);
}

/**
 * Inserts a new user with it's encrypted pwd into the Database.
 * Secured vs SQL Injection.
 * @param String $username Name the user will use to log in.
 * @param String $password Password the user will use to enter in it's account.
 */
function insertUser($connection, $username, $password) {
    $statement = mysqli_prepare($connection, 'INSERT INTO users(username, password) VALUES (?, ?)');
    mysqli_stmt_bind_param($statement, 'ss', $username, $secured_password);
    $secured_password = password_secure($password);
    mysqli_stmt_execute($statement);
    
    mysqli_stmt_close($statement);
}

/**
 * Complete call to create a new user.
 * First it checks if the username exists (if so throws error), then it adds the salt and encrypts the password.
 * Last it adds the new entry to the database.
 * @param type $username
 * @param type $password
 */
function createUser($username, $password) {
    $connection = connectDB();
    mysqli_set_charset($connection, "utf8");
    
    if(checkUsername($connection, $username)) {
        header("HTTP/1.0 404 Not Found");
        disconnectDB($connection);
        exit();
    } else insertUser($connection, $username, $password);
    
    disconnectDB($connection);
}

createUser($_POST['username'], $_POST['password']);