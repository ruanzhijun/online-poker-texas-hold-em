<?php

/**
 * Creates connection to the DBB.
 * @return Connection opened.
 */
function connectDB() {
    $server = "mysql.hostinger.es";
    $user = "u213982572_mario";
    $pass = "1Z0lCYOlA8akS6zselkZ";
    $bd = "u213982572_poker";

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
function checkUsername($username) {
    $connection = connectDB();
    mysqli_set_charset($connection, "utf8");
    
    $sql = "SELECT username
                FROM users
                WHERE username = '$username'";
    
    $result = mysqli_query($connection, $sql);
    $num_rows = mysqli_num_rows($result);
    
    disconnectDB($connection);
    
    return $num_rows !== 0;
}

if(checkUsername($_POST['username'])) { // If it exists, throws error.
    header("HTTP/1.0 404 Not Found");
    exit();
}