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
 * 
 * @param type $username
 * @param type $password
 * @return int
 */
function check($username, $password) {
    $connection = connectDB();
    mysqli_set_charset($connection, "utf8");
    $result;
    
    if(!checkUsername) {
        $result = 0;
    } else {
        $passwordFromDB = getEncryptedPwd($connection, $username);
        $equal = password_verify($password, $passwordFromDB['password']);
        $result = $equal ? 1 : -1;
    }
    
    return $result;
}

echo check($_POST['username'], $_POST['password']);