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
 * Gets the encrypted user's password stored in the database.
 * @param MysqliObject $connection Connection established with the database.
 * @param String $username Username from which we want to get it's password.
 * @return String Encrypted password.
 */
function getEncryptedPwd($connection, $username) {
    mysqli_set_charset($connection, "utf8");
    
    $sql = "SELECT password
                FROM users
                WHERE username = '$username'";
    
    $result = mysqli_query($connection, $sql);
    return mysqli_fetch_assoc($result);
}

/**
 * Executes a query to delete the user from the system.
 * @param MysqliObject $connection Connection established with the database.
 * @param String $username Username whose account we want to delete.
 */
function deleteAction($connection, $username) {
    $statement = mysqli_prepare($connection, 'DELETE FROM users WHERE username = ?');
    mysqli_stmt_bind_param($statement, 's', $username);

    mysqli_stmt_execute($statement);    
    mysqli_stmt_close($statement);
}

/**
 * Complete function to call. 
 * First it checks if the username exists in the database.
 * If it does, checks if the encrypted password's value, matches with the stored one.
 * If it also does, deletes the account.
 * If neither of them, throws error.
 * @param String $username username of the account we want to delete.
 * @param type $password password typed by the user, will encrypt it and compare it to the stored one.
 */
function deleteUser($username, $password) {
    $connection = connectDB();
    mysqli_set_charset($connection, "utf8");
    
    if(!checkUsername($connection, $username)) {
        header("HTTP/1.0 404 Not Found");
        disconnectDB($connection);
        exit();
    } else {
        $passwordFromDB = getEncryptedPwd($connection, $username);
        $equal = password_verify($password, $passwordFromDB['password']);
        
        if($equal) {
            deleteAction($connection, $username);
            disconnectDB($connection);
        } else {
            header("HTTP/1.0 Wrong password or username");
            disconnectDB($connection);
            exit();
        }

        disconnectDB($connection);
    }
}

deleteUser($_POST['username'], $_POST['password']);